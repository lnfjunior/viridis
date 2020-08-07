package br.com.vetta.viridis.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class RequestExceptionHandler {

	@ExceptionHandler({ NoSuchElementException.class })
	public ResponseEntity<Object> notFoundError(Exception ex) {

		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> updateConstraintError(ConstraintViolationException ex) {

		log.info(ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Unfilled field(s): " + ex.getConstraintViolations().stream().map(constraint -> constraint.getPropertyPath().toString()).collect(Collectors.joining(", ")) + ".");
	}
}
