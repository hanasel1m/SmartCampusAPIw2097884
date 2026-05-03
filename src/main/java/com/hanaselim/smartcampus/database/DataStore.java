/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hanaselim.smartcampus.database;

import com.hanaselim.smartcampus.model.Room;
import com.hanaselim.smartcampus.model.Sensor;
import com.hanaselim.smartcampus.model.SensorReading;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * data storage for application, replaces database
 * all data stored using java collections
 * static vars ensure shared state across requests
 * 
 */
public class DataStore {
    // stores all rooms (room id -> room)
    public static Map<String, Room> rooms = new HashMap<>();
    
    // stores all sensors (sensor id -> sensor)
    public static Map<String, Sensor> sensors = new HashMap<>();
    
    //stores readings per sensor (sensor id -> list of readings.)
    public static Map<String, List<SensorReading>> readings = new HashMap<>();
}
