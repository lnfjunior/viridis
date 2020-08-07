package br.com.vetta.viridis.controller;

import br.com.vetta.viridis.model.MaintenanceOrder;
import br.com.vetta.viridis.service.MaintenanceOrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "${front.end.cors}")
@RequestMapping("/api/maintenance-order")
public class MaintenanceOrderController {

	@Autowired
	private MaintenanceOrderService maintenanceOrderService;

	@GetMapping
	public ResponseEntity<List<MaintenanceOrder>> getAll() {
		return ResponseEntity.ok().body(maintenanceOrderService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<MaintenanceOrder> get(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(maintenanceOrderService.get(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation("Saves new maintenance order")
	@ApiResponses({
			@ApiResponse(code = 201, message = "Maintenance order saved successfully"),
			@ApiResponse(code = 400, message = "Erro de validação")
	})
	public MaintenanceOrder save(@RequestBody @Valid MaintenanceOrder maintenanceOrder ){
		return maintenanceOrderService.save(maintenanceOrder);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete( @PathVariable Long id ){
		maintenanceOrderService.delete(id);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update( @PathVariable Long id,
						@RequestBody @Valid MaintenanceOrder maintenanceOrder ){
		maintenanceOrderService.update(id, maintenanceOrder);
	}


}
