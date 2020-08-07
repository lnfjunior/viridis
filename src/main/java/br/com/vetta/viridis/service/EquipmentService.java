package br.com.vetta.viridis.service;

import br.com.vetta.viridis.model.Equipment;
import org.springframework.data.domain.Example;

import java.util.List;

public interface EquipmentService {

	Equipment get(Long id);

	List<Equipment> getAll(Example example);

	Equipment save(Equipment equipment);

	void delete(Long id);

	void update(Long id, Equipment equipment);

}
