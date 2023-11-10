package com.lcwd.rating.controller;

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

import com.lcwd.rating.entities.Rating;
import com.lcwd.rating.service.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

	@Autowired
	private RatingService ratingService;
	
	@PostMapping
	public ResponseEntity<?> saveRating(@RequestBody Rating rating){
		rating.setRatingId(UUID.randomUUID().toString());
		return new ResponseEntity<>(ratingService.create(rating),HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllRatings(){
		return new ResponseEntity<>(ratingService.getRatings(),HttpStatus.OK);
	}
	
	@GetMapping("/users")
	public ResponseEntity<?> getRatingByUserId(@RequestParam String userId){
		return new ResponseEntity<>(ratingService.getRatingByUserId(userId),HttpStatus.OK);
	}
	
	@GetMapping("/hotels")
	public ResponseEntity<?> getRatingByHotelId(@RequestParam String hotelId){
		return new ResponseEntity<>(ratingService.getratingByHotelId(hotelId),HttpStatus.OK);
	}
}
