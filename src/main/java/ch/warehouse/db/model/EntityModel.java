package ch.warehouse.db.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class EntityModel {

    @Id
    @Type(type = "uuid-char")
    @Column(updatable = false, nullable = false, columnDefinition = "CHAR(40)")
    UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }
}
