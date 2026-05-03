/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hanaselim.smartcampus.resource;

import com.hanaselim.smartcampus.database.DataStore;
import com.hanaselim.smartcampus.exception.DataNotFoundException;
import com.hanaselim.smartcampus.exception.RoomNotEmptyException;
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
 * @author Dell
 */
@Path("/rooms")
public class RoomResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Room> getAllRooms() {
        return DataStore.rooms.values();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoom(Room room) {
        DataStore.rooms.put(room.getId(), room);
        return Response.status(Response.Status.CREATED).entity(room).build();
        
    }

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
