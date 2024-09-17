package com.sb.maps.entities;

import java.io.Serializable;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    @JsonIgnore
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    private OffsetDateTime createdDate = OffsetDateTime.now();

    @LastModifiedBy
    @Column(name="last_modified_by", length = 50)
    @JsonIgnore
    private String lastModifiedBy;

    @UpdateTimestamp
    @Column(name = "last_modified_date")
    @JsonIgnore
    private OffsetDateTime lastModifiedDate = OffsetDateTime.now();
}