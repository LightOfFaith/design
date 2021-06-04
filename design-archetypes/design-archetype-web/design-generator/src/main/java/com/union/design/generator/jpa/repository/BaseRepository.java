package com.union.design.generator.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import javax.transaction.Transactional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    @Modifying
    @Transactional
    @Query("update #{#entityName} as e set e.deleted = true where e.id = ?1")
    void logicDeleteById(ID id);

    @Transactional
    default void logicDeleteAllById(Iterable<ID> ids) {
        ids.forEach(this::logicDeleteById);
    }

}