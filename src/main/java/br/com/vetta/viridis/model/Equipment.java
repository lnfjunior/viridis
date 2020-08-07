package br.com.vetta.viridis.model;

import lombok.Data;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Audited
@AuditTable(value = "equipment_audit")
@Data
@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Equipment must have name.")
    @Column
    private String name;

    public Equipment withId(Long id) {
        this.id = id;
        return this;
    }

    public Equipment withName(String name) {
        this.name = name;
        return this;
    }
}
