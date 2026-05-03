/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hanaselim.smartcampus.resource;

import com.hanaselim.smartcampus.database.DataStore;
import com.hanaselim.smartcampus.exception.DataNotFoundException;
import com.hanaselim.smartcampus.exception.RoomNotEmptyException;
import com.hanaselim.smartcampus.model.ErrorMessage;
import com.hanaselim.smartcampus.model.Room;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import javax.ws.rs.core.Response;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


/**
 *
 * room resource : handles all operations related to rooms
 */
@Path("/rooms")
public class RoomResource {

    //returns all rooms
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Room> getAllRooms() {
        return DataStore.rooms.values();
    }

    // creates a new room
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoom(Room room) {
        
        //validate body
        if (room == null) {
            ErrorMessage error = new ErrorMessage(
                    "Room body is missing",
                    400,
                    "Invalid input"
            );
            return Response.status(Response.Status.BAD_REQUEST).entity(error).type(MediaType.APPLICATION_JSON).build();
        }
        
        //validate id
        if (room.getId() == null || room.getId().isEmpty()) {
            ErrorMessage error = new ErrorMessage(
                    "Room ID is required",
                    400,
                    "Invalid input"
            );
            
            return Response.status(Response.Status.BAD_REQUEST).entity(error).type(MediaType.APPLICATION_JSON).build();
        }
        
        //check duplicate
        if (DataStore.rooms.containsKey(room.getId())){
            ErrorMessage error = new ErrorMessage(
                    "Room already exists",
                    409,
                    "Duplicate resource"
            );
            
            return Response.status(Response.Status.CONFLICT).entity(error).type(MediaType.APPLICATION_JSON).build();
        }
        
        //validate name
        if (room.getName() == null || room.getName().isEmpty()) {
            ErrorMessage error = new ErrorMessage(
                    "Room name is required",
                    400,
                    "Invalid input"
            );
            
            return Response.status(Response.Status.BAD_REQUEST).entity(error).type(MediaType.APPLICATION_JSON).build();
        }
        
        //validae capacity
        if (room.getCapacity() <= 0) {
            ErrorMessage error = new ErrorMessage(
                    "Capacity must be greater than 0",
                    400,
                    "Invalid input"
            );
            return Response.status(Response.Status.BAD_REQUEST).entity(error).type(MediaType.APPLICATION_JSON).build();
        }
        
        DataStore.rooms.put(room.getId(), room);
        return Response.status(Response.Status.CREATED).entity(room).header("Location", "/api/v1/rooms/" + room.getId()).build();
        
    }

    //returns room by id
    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoom(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);

        if (room == null) {
            throw new DataNotFoundException("Room with ID " + roomId + " not found");
        }
        return room;
    }
    
    // deletes room if it has no sensors
    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId){
        Room room = DataStore.rooms.get(roomId);
        
        if(room == null){
            throw new DataNotFoundException("Room with ID " + roomId + " not found");
        }
        
        if(!room.getSensorIds().isEmpty()){
            throw new RoomNotEmptyException("Room cannot be deleted. Sensors are still assigned.");
        }
        
        DataStore.rooms.remove(roomId);
        return Response.status(Response.Status.NO_CONTENT).build(); 
    }
}
