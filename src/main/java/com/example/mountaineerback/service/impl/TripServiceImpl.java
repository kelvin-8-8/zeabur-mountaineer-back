package com.example.mountaineerback.service.impl;

import com.example.mountaineerback.model.dto.TripDTO;
import com.example.mountaineerback.model.entity.Trip;
import com.example.mountaineerback.repository.TripRepository;
import com.example.mountaineerback.service.TripService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TripRepository tripRepository;

    @Override
    public List<TripDTO> getAllTrip() {

        return tripRepository.findAll().stream()
                .map(trip -> modelMapper.map(trip,TripDTO.class))
                .collect(Collectors.toList());
    }


}
