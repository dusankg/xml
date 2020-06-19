package com.example.vehicleservice.controller;

import com.example.vehicleservice.dto.ShowVehicleDTO;
import com.example.vehicleservice.dto.VehicleDTO;
import com.example.vehicleservice.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.vehicleservice.service.VehicleService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @GetMapping()
    public ResponseEntity<Set<ShowVehicleDTO>> showVehicles(){
        Set<Vehicle> vehicles = vehicleService.showVehicles();
        Set<ShowVehicleDTO> return_vehicles = new HashSet<>();
        for (Vehicle v: vehicles){
            return_vehicles.add(new ShowVehicleDTO(v));
        }
        return new ResponseEntity<>(return_vehicles, HttpStatus.OK);
    }

    @GetMapping("/{id1}")
    public ResponseEntity<VehicleDTO> getVehicleDetails(@PathVariable("id1") String id1){
        try{
            return vehicleService.getVehicleDetails(Long.parseLong(id1));
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity<String> createVehicle(@RequestBody VehicleDTO vehicle){
        return vehicleService.createVehicle(vehicle);

    }
}