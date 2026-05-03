/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hanaselim.smartcampus.resource;

import com.hanaselim.smartcampus.database.DataStore;
import com.hanaselim.smartcampus.model.SensorReading;
import com.hanaselim.smartcampus.exception.DataNotFoundException;
import com.hanaselim.smartcampus.exception.SensorUnavailableException;
import com.hanaselim.smartcampus.model.Sensor;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * sensor reading resource : sub-resource handling readings for a specific sensor
 * 
 */
public class SensorReadingResource {
    
    private String sensorId;
    
    public SensorReadingResource(String sensorId){
        this.sensorId=sensorId;
    }
    
    //get readings for a sensor
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorReading> getReadings() {
        return DataStore.readings.getOrDefault(sensorId, new ArrayList<>());
    }
    
    //post new reading
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading){
        Sensor sensor = DataStore.sensors.get(sensorId);
        if(sensor == null){
            throw new DataNotFoundException("Sensor with ID "+ sensorId +" not found");
        }
        
        if("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())){
            throw new SensorUnavailableException("Sensor is under maintenance");
        }
        
        DataStore.readings.computeIfAbsent(sensorId, k -> new ArrayList<>()).add(reading);
        sensor.setCurrentValue(reading.getValue());
        return Response.status(Response.Status.CREATED).build();
    }
    
}
