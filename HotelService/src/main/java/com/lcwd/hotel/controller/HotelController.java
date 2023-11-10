package com.lcwd.hotel.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.hotel.entities.Hotel;
import com.lcwd.hotel.service.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;
	
	@PostMapping
	public ResponseEntity<?> createHotel(@RequestBody Hotel hotel){
		String id = UUID.randomUUID().toString();
		hotel.setHotelId(id);
		return new ResponseEntity<>(hotelService.create(hotel),HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllHotel(){
		return new ResponseEntity<>(hotelService.getAll(),HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> getHotel(@RequestParam String id){
		return new ResponseEntity<>(hotelService.get(id),HttpStatus.OK);
	}
}
