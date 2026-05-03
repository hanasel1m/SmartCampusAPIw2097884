
package com.hanaselim.smartcampus.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * room model, represents physical room in the smart campus
 * each room can have multiple sensors linked to it
 */
public class Room {

    private String id; // unique room id
    private String name; // room name
    private int capacity; // maximum room capacity
    private List<String> sensorIds = new ArrayList<>(); //list of sensor ids in this room

    //default constructor required for json deserialisation
    public Room() {

    }

    public Room(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<String> getSensorIds() {
        return sensorIds;
    }

    public void setSensorIds(List<String> sensorIds) {
        this.sensorIds = sensorIds;
    }
}
