package ch.evel.warehouse.db.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class EntityModel {

    @Id
    @Type(type = "uuid-char")
    @JsonView(DataTablesOutput.View.class)
    @Column(updatable = false, nullable = false, columnDefinition = "CHAR(40)")
    protected UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
