package com.donotfreezesoftware.mqttevents;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.slf4j.LoggerFactory;

/**
 *  { "topic": "GDCTL/STATUS", "dateTime": "2022-02-21T11:59:28", 
 *      "door": {"door_id": 1, "state": "CLOSED", "state_datetime": "2022-02-21T11:59:18-0900", 
 *      "last_command": "CLOSE", "last_command_datetime": "2022-02-21 11:30:13.390037" } }

 * @author pconroy
 */
public class GarageDoorStatusEvent extends MQTTMessage_POJO
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( GarageDoorStatusEvent.class );    

    
    public class    DoorStatus {
        @SerializedName("door_id")              int         door_id;
        @SerializedName("state")                String      state;
        @SerializedName("state_datetime")       String      state_datetime;
        @SerializedName("last_command")         String      last_command;
        @SerializedName("last_command_datetime")    String  last_command_datetime;
        
    }
    @SerializedName("door")         DoorStatus  door;
    
    
    // -------------------------------------------------------------------------
    //  Create a simple Constructor so the superclass fields get initalized
    //  by Gson deserialization
    public  GarageDoorStatusEvent ()
    {
        super();
    }
    
    // -------------------------------------------------------------------------
    public static GarageDoorStatusEvent fromJson (String jsonMessage )
    {
        Gson    gson = new Gson();
        try {
            GarageDoorStatusEvent  anEvent = null;
            anEvent = gson.fromJson( jsonMessage, GarageDoorStatusEvent.class );
            anEvent.setCreatedOn( System.currentTimeMillis() );
            anEvent.setJsonPayload( jsonMessage );
            
            return anEvent;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a GarageDoorStatusEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }
}
