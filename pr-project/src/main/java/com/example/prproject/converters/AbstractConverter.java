package com.example.prproject.converters;

import com.example.prproject.dto.PageDto;
import com.example.prproject.dto.base.BaseDto;
import com.example.prproject.dto.page.PageRequestDto;
import com.example.prproject.services.entity.BaseService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static org.springframework.core.GenericTypeResolver.resolveTypeArguments;
import static org.springframework.util.CollectionUtils.isEmpty;

public abstract class AbstractConverter<Entity, Dto extends BaseDto> {

    public static final String CHANGE_MODE = "change_mode";

    private static Class[] getParamClasses(Class<? extends AbstractConverter> clazz) {
        return resolveTypeArguments(clazz, AbstractConverter.class);
    }

    public static <Dto extends BaseDto> PageDto<Dto> getPageDto(
            Collection<Dto> result, Long count, PageRequestDto pageRequest) {
        PageDto<Dto> pageDto = new PageDto<>();
        pageDto.setTotalElements(count)
                .setTotalPages(-Math.floorDiv(-count.intValue(), pageRequest.getPageSize()))
                .setContent(result)
                .setPageNumber(pageRequest.getPageNumber())
                .setPageSize(pageRequest.getPageSize())
                .setSortBy(pageRequest.getSortBy())
                .setSortOrder(pageRequest.getSortOrder());
        return pageDto;
    }

    public static <T> PageDto<T> getPageDto(Page<T> page) {
        Sort.Order order = page.getSort().get().findFirst()
                .orElse(BaseService.DEFAULT_SORT);
        PageDto<T> pageDto = new PageDto<>();
        pageDto.setContent(page.getContent())
                .setTotalElements(page.getTotalElements())
                .setTotalPages(page.getTotalPages())
                .setPageNumber(page.getNumber() + 1)
                .setPageSize(page.getSize())
                .setSortBy(order.getProperty())
                .setSortOrder(order.getDirection().name());
        return pageDto;
    }

    @Autowired
    protected ModelMapper modelMapper;


    @PostConstruct
    private void init() {
        modelMapper
                .createTypeMap(getDtoClass(), getEntityClass(), CHANGE_MODE) // sets mode
                .setPropertyCondition(Conditions.isNotNull()); // sets condition to copy those properties from the source object that are not null
    }

    public Entity duplicate(Entity entity) {
        Entity cloneEntity = (Entity) ReflectUtils.newInstance(entity.getClass());
        BeanUtils.copyProperties(entity, cloneEntity);
        return cloneEntity;
    }

    public Entity duplicate(Entity entity, Dto enrichDto) {
        Entity cloneEntity = duplicate(entity);
        modelMapper.map(enrichDto, cloneEntity);
        return cloneEntity;
    }

    public Dto convert(Entity source, Dto target) {
        modelMapper.map(source, target);
        return target;
    }

    public Entity convert(Dto source, Entity target) {
        return convert(source, target, null);
    }

    public Entity convert(Dto source, Entity target, String convertingMode) {
        if (convertingMode == null) {
            modelMapper.map(source, target);
        } else {
            modelMapper.map(source, target, convertingMode);
        }
        return target;
    }

    public Dto convert(Entity entity) {
        return entity == null ? null :
                modelMapper.map(entity, getDtoClass());
    }

    public List<Dto> convertEntityList(List<Entity> entities) {
        if (isEmpty(entities)) {
            return new ArrayList<>(0);
        }
        return entities.stream().map(this::convert).collect(Collectors.toList());
    }

    public Set<Entity> convertDtoList(List<Dto> dtoList) {
        if (isEmpty(dtoList)) {
            return new HashSet<>(0);
        }
        return dtoList.stream().map(this::convert).collect(Collectors.toSet());
    }

    public LinkedHashSet<Dto> convertEntitySet(Set<Entity> entities) {
        if (isEmpty(entities)) {
            return new LinkedHashSet<>(0);
        }
        return entities.stream().map(this::convert).collect(toCollection(LinkedHashSet::new));
    }

    public LinkedHashSet<Entity> convertDtoSet(Set<Dto> dtoList) {
        if (isEmpty(dtoList)) {
            return new LinkedHashSet<>(0);
        }
        return dtoList.stream().map(this::convert).collect(toCollection(LinkedHashSet::new));
    }

    public Entity convert(Dto dto) {
        return convert(dto, (String) null);
    }

    public Entity convert(Dto dto, String convertingMode) {
        return dto == null ? null : (convertingMode == null ?
                modelMapper.map(dto, getEntityClass()) :
                modelMapper.map(dto, getEntityClass(), convertingMode));
    }

    /**
     * Converts only Page object
     *
     * @param page to convert
     * @return PageDto object
     */
    public <T> PageDto<T> convertPage(Page<T> page) {
        return AbstractConverter.getPageDto(page);
    }

    public PageDto<Dto> convert(Collection<Entity> entities, Long count, PageRequestDto pageRequest) {
        return AbstractConverter.getPageDto(entities.stream().map(this::convert).collect(toList()), count, pageRequest);
    }

    public PageDto<Dto> convert(Page<Entity> page) {
        return convertPage(page.map(this::convert));
    }

    private Class<Entity> getEntityClass() {
        return (Class<Entity>) getParamClasses(getClass())[0];
    }

    private Class<Dto> getDtoClass() {
        return (Class<Dto>) getParamClasses(getClass())[1];
    }
}
