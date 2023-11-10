package com.lcwd.user.service.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;
import com.lcwd.user.service.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody User user){
		String id = UUID.randomUUID().toString();
		user.setUserId(id);
		User result = userService.saveUser(user);
		return new ResponseEntity<>(result,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllUsers(){
		return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
	}
	
	int retryCount = 1;
	@GetMapping("/get")
	//@CircuitBreaker(name="ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
	//@Retry(name="ratingHotelRetry",fallbackMethod = "ratingHotelFallback")
	@RateLimiter(name="ratingHotelLimiter", fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getUser(@RequestParam String userId) throws ResourceNotFoundException{
		logger.info("Retry count: {}", retryCount);
		retryCount++;
		User user = userService.getUser(userId);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
		logger.info("falback service is executed because another service is down"+ ex.getMessage());
		User user = new User("1234","dummy", "dummy@gmail.com", "this user is created because some services are down");
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
}
