package com.lcwd.user.service.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lcwd.user.service.entities.Hotel;

@Component
@FeignClient(name="HOTEL-SERVICE")
public interface HotelService {

	@GetMapping("/hotels/get")
	Hotel getHotel(@RequestParam String id);
}
