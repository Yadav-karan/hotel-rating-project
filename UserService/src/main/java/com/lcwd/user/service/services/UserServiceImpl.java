package com.lcwd.user.service.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;
import com.lcwd.user.service.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with id: "+userId+" not found!!"));
		Rating[] ratingOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users?userId="+userId, Rating[].class);
		if(ratingOfUser.length != 0) {
			
			List<Rating> ratings = Arrays.stream(ratingOfUser).toList();
			
			List<Rating> ratingList = ratings.stream().map(r->{
				Hotel hotel = hotelService.getHotel(r.getHotelId());
				r.setHotel(hotel);
				return r;
			}).collect(Collectors.toList());
			
			user.setRatings(ratingList);
		}
		return user;
	}

}
