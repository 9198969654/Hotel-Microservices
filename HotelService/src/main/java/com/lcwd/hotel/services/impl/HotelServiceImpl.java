package com.lcwd.hotel.services.impl;

import com.lcwd.hotel.entities.Hotel;
import com.lcwd.hotel.entities.repositories.HotelRepository;
import com.lcwd.hotel.excptions.ResourceNotFoundException;
import com.lcwd.hotel.services.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;



    @Override
    public Hotel create(Hotel hotel) {
       String hotelId =  UUID.randomUUID().toString();
       hotel.setId(hotelId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel get(String id) {
        return hotelRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("hotel with given id not found !! "));
    }
}
