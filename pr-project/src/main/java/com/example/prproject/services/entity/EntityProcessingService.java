package com.example.prproject.services.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.example.prproject.services.entity.BaseService.DEFAULT_SORT;
import static org.springframework.data.domain.Sort.by;

@Service
public abstract class EntityProcessingService {

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int defaultBatchSize;

    Long processEntitiesPageByPage(Function<Pageable, Page> selectPageFunction,
                                   Consumer<Page> processEntityConsumer) {
        Pageable pageable = PageRequest.of(0, defaultBatchSize, by(DEFAULT_SORT));
        Page entityPage;
        do {
            entityPage = selectPageFunction.apply(pageable);
            processEntityConsumer.accept(entityPage);

            pageable = entityPage.nextPageable();
        } while (entityPage.hasNext());

        return entityPage.getTotalElements();
    }
}
