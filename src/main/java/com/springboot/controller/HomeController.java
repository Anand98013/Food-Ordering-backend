package com.springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class HomeController {

	@GetMapping
	public ResponseEntity<String> HomeController() {
		return new ResponseEntity<>("Welcome to food deliverly project", HttpStatus.OK);
	}
}
