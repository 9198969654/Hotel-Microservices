package com.lcwd.user.service.services.Impl;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        //generate unique userid
        String randomUserId =  UUID.randomUUID().toString();
       user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {

        //get User from Database from the userRepository
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User With given id is not found on server !!" +userId));

        //fetch rating of the  Above UserFrom RATING SERVICE
        //http://localhost:8083/ratings/users/2754ebb2-db41-4c24-b633-1434656bbdbf
        Rating[] ratingsOfUser =   restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{} ", ratingsOfUser);

       List<Rating> ratings =  Arrays.stream(ratingsOfUser).toList();

       List<Rating> ratingList =  ratings.stream().map(rating -> {
            //api call to hotel Service to Get the Hotel
            //http://localhost:8082/hotels/1766726b-58c2-4fd3-bb48-1d2d8633f36c
            // ResponseEntity<Hotel> forEntity =  restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
         Hotel hotel =  hotelService.getHotel(rating.getHotelId());
            // logger.info("response status code: {}", forEntity.getStatusCode());
            //set the hotel to creating
           rating.setHotel(hotel);
            //return the rating
            return rating;
        }).collect(Collectors.toList());
        user.setRatings(ratingList);


    return user;

    }
}
