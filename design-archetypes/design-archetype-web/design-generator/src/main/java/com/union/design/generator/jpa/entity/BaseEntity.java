package com.union.design.generator.jpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity implements Persistable<Long> {

    @Transient
    private boolean isNew = true;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    private Long id;
    @Column(name = "`create_time`", updatable = false)
    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    @Column(name = "`modify_time`")
    private Date modifyTime;
    @Column(name = "`is_deleted`")
    private Boolean deleted;

    public BaseEntity(Long id, Date createTime, Date modifyTime, Boolean deleted) {
        this.id = id;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.deleted = deleted;
    }

    @Override
    public Long getId() {
        return id;
    }

    @java.beans.Transient
    @Override
    public boolean isNew() {
        return Objects.isNull(getId());
    }

}