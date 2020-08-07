package br.com.vetta.viridis.controller;

import br.com.vetta.viridis.model.Equipment;
import br.com.vetta.viridis.service.EquipmentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/equipment")
@CrossOrigin(origins = "${front.end.cors}")
@Api("Api Equipament")
public class EquipmentController {

	private EquipmentService equipmentService;

	public EquipmentController( EquipmentService equipmentService ) {
		this.equipmentService = equipmentService;
	}

	@GetMapping
	@ApiOperation("Get all equipments")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of equipment found"),
			@ApiResponse(code = 403, message = "Not authorized")
	})
	public List<Equipment> find(Equipment filter) {
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withStringMatcher(
						ExampleMatcher.StringMatcher.CONTAINING );

		org.springframework.data.domain.Example example = Example.of(filter, matcher);
		return equipmentService.getAll(example);
	}

	@GetMapping("/{id}")
	@ApiOperation("Get equipment details")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Found equipment"),
			@ApiResponse(code = 403, message = "Not authorized"),
			@ApiResponse(code = 404, message = "Equipment not found for the given ID")
	})
	public Equipment get(@PathVariable @ApiParam("Equipment Id") Long id) {
		return equipmentService.get(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation("Saves new equipment")
	@ApiResponses({
			@ApiResponse(code = 201, message = "Equipment saved successfully"),
			@ApiResponse(code = 403, message = "Not authorized"),
			@ApiResponse(code = 400, message = "Erro de validação")
	})
	public Equipment save( @RequestBody @Valid Equipment equipment ){
		return equipmentService.save(equipment);
	}

	@DeleteMapping("{id}")
	@ApiOperation("Delete equipment")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Equipment deleted"),
			@ApiResponse(code = 403, message = "Not authorized")
	})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete( @PathVariable Long id ){
		equipmentService.delete(id);
	}

	@PutMapping("{id}")
	@ApiOperation("Update equipment")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Equipment updated"),
			@ApiResponse(code = 403, message = "Not authorized")
	})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update( @PathVariable Long id,
						@RequestBody @Valid Equipment equipment ){
		equipmentService.update(id, equipment);
	}
}
