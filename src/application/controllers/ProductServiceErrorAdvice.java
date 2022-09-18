package application.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import application.exceptions.DatabaseEmptyException;
import application.exceptions.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;

//@ControllerAdvice
@Slf4j
public class ProductServiceErrorAdvice {

	@ExceptionHandler({ProductNotFoundException.class})
		public ResponseEntity<String> handlerProductNotFoundException(ProductNotFoundException e){
		return error(HttpStatus.NOT_FOUND, e);
	}

	private ResponseEntity<String> error(HttpStatus status, Exception e) {
		log.error("Exception: ", e);
		return ResponseEntity.status(status).body(e.getMessage());
	}
	
	@ExceptionHandler({DatabaseEmptyException.class})
	public ResponseEntity<String> handlerDatabaseEmptyException(DatabaseEmptyException e){
	return error(HttpStatus.NOT_FOUND, e);
}


	
}
