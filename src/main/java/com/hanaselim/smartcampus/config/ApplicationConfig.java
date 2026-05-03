/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hanaselim.smartcampus.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
/**
 * bootstraps the jax-rs application
 * application path defines base URI for all endpoints
 * all resources accessible under : http://localhost:8080/SmartCampusAPIw2097884/api/v1
 * 
 * 
 */
@ApplicationPath("/api/v1")
public class ApplicationConfig  extends Application {
    //no code needed as jax-rs handles config automatically
}
