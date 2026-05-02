/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hanaselim.smartcampus.database;

import com.hanaselim.smartcampus.model.Room;
import com.hanaselim.smartcampus.model.Sensor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Dell
 */
public class DataStore {
    public static Map<String, Room> rooms = new HashMap<>();
    public static Map<String, Sensor> sensors = new HashMap<>();
}
