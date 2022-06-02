package com.donotfreezesoftware.mqttevents.LaCrosse_WS2310;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pconroy
 */
public class WS2310_TemperatureEvent extends LaCrosse_WS2310_Event 
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( WS2310_TemperatureEvent.class );    

    //
    // {"time" : "2022-04-05T11:04:34", "protocol" : 34, "model" : "LaCrosse-WS2310", "id" : 28, "temperature_F" : 58.820, "mod" : "ASK", "freq" : 434.022, "rssi" : -5.488, "snr" : 24.615, "noise" : -30.103}
    //
    
    @SerializedName("temperature_F")    protected float temperature;

    public WS2310_TemperatureEvent() 
    {
       log.trace( "Created event WS2310_TemperatureEvent" );
    }

    public static WS2310_TemperatureEvent fromJson (String jsonMessage)
    {
        Gson    gson = new Gson();
        try {
            WS2310_TemperatureEvent  event = null;
            event = gson.fromJson( jsonMessage, WS2310_TemperatureEvent.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a WS2310_TemperatureEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    
}
