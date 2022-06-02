package com.donotfreezesoftware.mqttevents.LaCrosse_WS2310;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pconroy
 */
public class WS2310_RainEvent extends LaCrosse_WS2310_Event 
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( WS2310_RainEvent.class );    
    
    //
    // {"time" : "2022-04-05T11:04:34", "protocol" : 34, "model" : "LaCrosse-WS2310", "id" : 28, "rain_in" : 19.354, "mod" : "ASK", "freq" : 434.026, "rssi" : -5.487, "snr" : 24.105, "noise" : -29.591}
    //
    
    @SerializedName("rain_in")    protected float rainTotal;

    public WS2310_RainEvent() 
    {
       log.trace( "Created event WS2310_RainEvent" );
    }

    public static WS2310_RainEvent fromJson (String jsonMessage)
    {
        Gson    gson = new Gson();
        try {
            WS2310_RainEvent  event = null;
            event = gson.fromJson( jsonMessage, WS2310_RainEvent.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a WS2310_RainEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }

    public float getRainTotal() {
        return rainTotal;
    }

    public void setRainTotal(float rainTotal) {
        this.rainTotal = rainTotal;
    }

    

}
