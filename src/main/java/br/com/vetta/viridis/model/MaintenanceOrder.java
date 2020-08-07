package br.com.vetta.viridis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Audited
@AuditTable(value = "maintenance_order_audit")
@Data
@Entity
public class MaintenanceOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Maintenence Order must be associated with an existing equipment.")
	@ManyToOne
	private Equipment equipment;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
	@NotNull(message = "Maintenence Order must have a scheduled date.")
	@Column
	private Date scheduledDate;
}
