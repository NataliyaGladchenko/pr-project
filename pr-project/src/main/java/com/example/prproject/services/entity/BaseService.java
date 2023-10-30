package com.example.prproject.services.entity;

import com.example.prproject.converters.BaseConverter;
import com.example.prproject.dao.BaseRepo;
import com.example.prproject.domain.base.BaseEntity;
import com.example.prproject.dto.PageDto;
import com.example.prproject.dto.base.BaseDto;
import com.example.prproject.dto.page.PageRequestDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.prproject.converters.AbstractConverter.CHANGE_MODE;
import static io.micrometer.common.util.StringUtils.isEmpty;
import static org.springframework.core.GenericTypeResolver.resolveTypeArguments;
import static org.springframework.data.domain.Sort.Direction.*;
import static org.springframework.data.domain.Sort.by;

public abstract class BaseService<Entity extends BaseEntity, Dto extends BaseDto> extends EntityProcessingService {
    public static final Sort.Order DEFAULT_SORT = Sort.Order.asc("id");

    public static final Sort.Order LAST_SORT = Sort.Order.desc("id");

    public static final String SQL_LIKE = "%";


    private static Class[] getParamClasses(Class<? extends BaseService> clazz) {
        return resolveTypeArguments(clazz, BaseService.class);
    }

    public static Pageable getPageRequest(PageRequestDto pageSearch) {
        return getPageRequest(pageSearch, DEFAULT_SORT);
    }

    public static Pageable getPageRequest(PageRequestDto pageRequest, Sort.Order defaultOrder) {
        Sort sort = by(defaultOrder);
        if (!isEmpty(pageRequest.getSortBy())) {
            Sort.Direction direction = !isEmpty(pageRequest.getSortOrder()) && fromString(pageRequest.getSortOrder()) == DESC ?
                    DESC :
                    ASC;
            sort = direction == ASC ?
                    by(Sort.Order.asc(pageRequest.getSortBy()), defaultOrder) :
                    by(Sort.Order.desc(pageRequest.getSortBy()), defaultOrder);
        }
        return PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize(), sort);
    }


    @PersistenceContext
    protected EntityManager entityManager;


    protected abstract BaseRepo<Entity> getRepo();

    protected abstract BaseConverter<Entity, Dto> getConverter();

    public <E> Optional<E> getFirstElement(Page<E> page) {
        return page.isEmpty() ? Optional.empty() : Optional.of(page.iterator().next());
    }

    public void checkExistById(Integer id) {
        if (!getRepo().existsById(id)) {
            throw new EntityNotFoundException("Can`t find " + getEntityClass().getSimpleName() + " by ID " + id);
        }
    }

    public Entity getEntity(Integer id) {
        return getRepo().findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can`t find " + getEntityClass().getSimpleName() + " by ID " + id)
                );
    }

    public Dto getDto(Integer id) {
        return getConverter().convert(getEntity(id));
    }

    public Page<Entity> getEntityPage(Pageable pageable) {
        return getExistOrThrowException(getRepo().findAll(pageable));

    }

    public Page<Entity> getExistOrThrowException(Page<Entity> page) {
        if (page.isEmpty()) {
            throw new EntityNotFoundException("Can`t find any " + getEntityClass().getSimpleName() +
                    " by provided criteria");
        }
        return page;
    }

    public PageDto<Dto> getPageDto(Pageable pageable) {
        return getConverter().convert(getEntityPage(pageable));
    }

    @Transactional
    public Dto create(Dto dto) {
        return refreshAndConvert(saveAndReturnEntity(dto));
    }

    @Transactional
    public Dto update(Dto dto) {
        return refreshAndConvert(updateAndReturnEntity(dto));
    }

    @Transactional
    public Dto change(Dto dto) {
        return refreshAndConvert(updateAndReturnEntity(dto, CHANGE_MODE));
    }

    public Dto refreshAndConvert(Entity entity) {
        entityManager.flush();
        entityManager.refresh(entity);
        return getConverter().convert(entity);
    }


    public Entity saveAndReturnEntity(Dto dto) {
        return getRepo().save(getConverter().convert(dto));
    }

    public Entity updateAndReturnEntity(Dto dto) {
        return updateAndReturnEntity(dto, null);
    }

    public Entity updateAndReturnEntity(Dto dto, String convertingMode) {
        // checks dto has entity ID
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Updating Entity ID can't be null.");
        }

        return getRepo().save(getConverter().convert(dto, getEntity(dto.getId()), convertingMode));
    }

    public void delete(Integer id) {
        getRepo().deleteById(id);
    }

    private Class<Entity> getEntityClass() {
        return (Class<Entity>) getParamClasses(getClass())[0];
    }
}
