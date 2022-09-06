package com.restaurant.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Data
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public abstract class AbstractClass implements Serializable {

    private static final long serialVersionUID = -539883735468480037L;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonIgnore
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = true)
    @JsonIgnore
    private Instant updatedAt;
}
