package com.donotfreezesoftware.mqttevents.LaCrosse_WS2310;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pconroy
 */
public class WS2310_HumidityEvent extends LaCrosse_WS2310_Event 
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( WS2310_HumidityEvent.class );    
    
    //
    // {"time" : "2022-04-05T11:04:34", "protocol" : 34, "model" : "LaCrosse-WS2310", "id" : 28, "humidity" : 45, "mod" : "ASK", "freq" : 434.014, "rssi" : -5.506, "snr" : 24.877, "noise" : -30.383}
    //
    
    @SerializedName("humidity")    protected float humidity;

    public WS2310_HumidityEvent() 
    {
       log.trace( "Created event WS2310_HumidityEvent" );
    }

    public static WS2310_HumidityEvent fromJson (String jsonMessage)
    {
        Gson    gson = new Gson();
        try {
            WS2310_HumidityEvent  event = null;
            event = gson.fromJson( jsonMessage, WS2310_HumidityEvent.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a WS2310_HumidityEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }
    
    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
}
