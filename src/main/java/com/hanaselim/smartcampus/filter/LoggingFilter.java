/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hanaselim.smartcampus.filter;

import java.io.IOException;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;


/**
 *
 * global filter that intercepts all incoming requests and outgoing responses to API
 * 
 * used for logging to help debug and monitor
 */
@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter{
    
    //logger used to print messages to server console
    private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class.getName());
    
    //runs before the request reaches any resource, logs http method and requested url
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException{
        LOGGER.info("Request: "+requestContext.getMethod()+" "+requestContext.getUriInfo().getRequestUri());
        
    }
    
    //runs after response is created and logs http status code returned to the client
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException{
        LOGGER.info("Response Status: "+ responseContext.getStatus());
    }
    
}
