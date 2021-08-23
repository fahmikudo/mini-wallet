package io.fahmikudo.wallet.util.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseModel implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 36)
    protected String id;

    @Column(name = "created_at", updatable = false)
    protected Date createdAt;

    @Version
    @Column(name = "updated_at")
    protected Date updatedAt;

    @Column(name = "is_deleted")
    protected Boolean isDeleted;

    @Column(name = "created_by", length = 36)
    protected String createdBy;

    @Column(name = "updated_by", length = 36)
    protected String updatedBy;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = new Date();
        this.isDeleted = false;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }

}
