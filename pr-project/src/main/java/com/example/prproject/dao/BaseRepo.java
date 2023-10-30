package com.example.prproject.dao;

import com.example.prproject.domain.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;


@NoRepositoryBean
public interface BaseRepo<Entity extends BaseEntity> extends JpaRepository<Entity, Integer>,
        JpaSpecificationExecutor<Entity> {
}
