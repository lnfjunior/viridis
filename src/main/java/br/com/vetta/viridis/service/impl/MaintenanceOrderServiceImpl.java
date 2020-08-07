package br.com.vetta.viridis.service.impl;

import br.com.vetta.viridis.model.MaintenanceOrder;
import br.com.vetta.viridis.repository.MaintenanceOrderRepository;
import br.com.vetta.viridis.service.MaintenanceOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class MaintenanceOrderServiceImpl implements MaintenanceOrderService {

	@Autowired
	private MaintenanceOrderRepository maintenanceOrderRepository;

	@Override
	public MaintenanceOrder get(Long id) {

		log.info("Retrieving Maintenance Order - id: {}", id);
		return maintenanceOrderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Maintenance Order not found."));

	}

	@Override
	public List<MaintenanceOrder> getAll() {

		log.info("Listing all Maintenance Orders...");
		return maintenanceOrderRepository.findAll();

	}

	@Override
	public MaintenanceOrder save(MaintenanceOrder maintenanceOrder) {
		log.info("Save Maintenance Order");
		return maintenanceOrderRepository.save(maintenanceOrder);
	}

	@Override
	public void delete (Long id) {
		log.info("Delete Maintenance Order - id: {}", id);
		maintenanceOrderRepository.findById(id)
				.map( maintenanceOrder -> {
					maintenanceOrderRepository.delete( maintenanceOrder );
					return maintenanceOrder;
				})
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Maintenance Order not found.") );
	}

	@Override
	public void update (Long id, MaintenanceOrder maintenanceOrder) {
		log.info("Update Maintenance Order - id: {}", id);
		maintenanceOrderRepository
				.findById(id)
				.map( existingMaintenanceOrder -> {
					maintenanceOrder.setId(existingMaintenanceOrder.getId());
					maintenanceOrderRepository.save(maintenanceOrder);
					return existingMaintenanceOrder;
				}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"Maintenance Order not found.") );
	}


}
