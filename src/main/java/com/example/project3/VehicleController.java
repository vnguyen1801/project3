package com.example.project3;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.io.IOException;
import java.util.List;
import jakarta.persistence.TypedQuery;

@RestController
public class VehicleController {

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private vehicleDao vehicleDao;
    @RequestMapping(value = "/addVehicle",method = RequestMethod.POST)

    public Vehicle addVehicle(@RequestBody Vehicle vehicle)throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        vehicleDao.create(vehicle);
        return vehicle;
    }

    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable int id)throws IOException{
        vehicleDao.getById(id);
        return vehicleDao.getById(id);

    }

    @RequestMapping(value = "/updateVehicle", method = RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        // Log the incoming vehicle for debugging
        System.out.println("Updating vehicle: " + newVehicle);

        Vehicle existingVehicle = vehicleDao.getById(newVehicle.getId());
        if (existingVehicle == null) {
            throw new RuntimeException("Vehicle not found with id: " + newVehicle.getId());
        }

        // Update fields of existing vehicle
        existingVehicle.setMakeModel(newVehicle.getMakeModel());
        existingVehicle.setYear(newVehicle.getYear());
        existingVehicle.setRetailPrice(newVehicle.getRetailPrice());

        vehicleDao.update(existingVehicle);
        return existingVehicle;
    }
    @RequestMapping(value = "deleteVehicle/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") int id)throws IOException{
        Vehicle existingVehicle = vehicleDao.getById(id);

        if (existingVehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Vehicle not found with id: " + id);
        }

        vehicleDao.delete(id); // Delete the vehicle using the DAO
        return ResponseEntity.ok("Vehicle with id: " + id + " has been deleted.");

    }
    @RequestMapping(value = "/getLatestVehicles",method = RequestMethod.GET)
    public List<Vehicle> getLatestVehicles() throws IOException {
        return vehicleDao.getLatestVehicles(10);

    }

}
