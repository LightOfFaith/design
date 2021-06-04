package com.union.design.dao.jpa.repository;

import com.union.design.dao.jpa.entity.BaseEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
@Transactional(readOnly = true)
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} as e where e.id in ?1 and e.deleted = false")
    @Override
    T getOne(ID id);

    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} as e where e.deleted = false")
    @Override
    List<T> findAll();

    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} as e where e.id in ?1 and e.deleted = false")
    @Override
    List<T> findAllById(Iterable<ID> ids);

    @Transactional
    @Modifying
    @Query("update #{#entityName} as e set e.deleted = true where e.id = ?1")
    void logicDeleteById(ID id);

    default void logicDeleteAllById(Iterable<ID> ids) {
        ids.forEach(this::logicDeleteById);
    }

}