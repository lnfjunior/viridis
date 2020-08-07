package br.com.vetta.viridis.service.impl;

import br.com.vetta.viridis.model.Equipment;
import br.com.vetta.viridis.repository.EquipmentRepository;
import br.com.vetta.viridis.service.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class EquipmentServiceImpl implements EquipmentService {

	@Autowired
	private EquipmentRepository equipmentRepository;

	@Override
	public Equipment get(Long id) {
		log.info("Retrieving Equipment - id: {}", id);
		return equipmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment not found."));
	}

	@Override
	public List<Equipment> getAll(Example example) {
		log.info("Listing all Equipment");
		return equipmentRepository.findAll(example);
	}

	@Override
	public Equipment save(Equipment equipment) {
		log.info("Save Equipment");
		return equipmentRepository.save(equipment);
	}

	@Override
	public void delete (Long id) {
		log.info("Delete Equipment - id: {}", id);
		equipmentRepository.findById(id)
				.map( equipament -> {
					equipmentRepository.delete( equipament );
					return equipament;
				})
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Equipment not found.") );
	}

	@Override
	public void update (Long id, Equipment equipment) {
		log.info("Update Equipment - id: {}", id);
		equipmentRepository
				.findById(id)
				.map( existingEquipment -> {
					equipment.setId(existingEquipment.getId());
					equipmentRepository.save(equipment);
					return existingEquipment;
				}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"Equipment not found.") );
	}

}
