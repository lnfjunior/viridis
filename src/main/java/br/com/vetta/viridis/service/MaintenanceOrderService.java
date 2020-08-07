package br.com.vetta.viridis.service;

import br.com.vetta.viridis.model.MaintenanceOrder;

import java.util.List;

public interface MaintenanceOrderService {

	MaintenanceOrder get(Long id);

	List<MaintenanceOrder> getAll();

	MaintenanceOrder save(MaintenanceOrder maintenanceOrder);

	void delete(Long id);

	void update(Long id, MaintenanceOrder maintenanceOrder);


}
