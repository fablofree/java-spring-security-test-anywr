package io.anywr.javaspringsecuritytest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;

public abstract class AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1;

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, unique = false)
    @JsonIgnore
    protected String createdBy = "Fablo";

    @CreatedBy
    @Column(name = "created_date", nullable = false)
    @JsonIgnore
    protected Instant createdDate = Instant.now();

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    @JsonIgnore
    protected String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    protected Instant lastModifiedDate = Instant.now();
}
