package com.donotfreezesoftware.mqttevents.LaCrosse_WS2310;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pconroy
 */
public class WS2310_WindEvent extends LaCrosse_WS2310_Event 
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( WS2310_WindEvent.class );    
    
    //
    // {"time" : "2022-04-05T11:04:35", "protocol" : 34, "model" : "LaCrosse-WS2310", "id" : 28, "wind_avg_m_s" : 0.900, "wind_dir_deg" : 45.000, "mod" : "ASK", "freq" : 434.024, "rssi" : -5.493, "snr" : 23.429, "noise" : -28.922}
    //
    
    @SerializedName("wind_avg_m_s")    protected float windAvgSpeed;
    @SerializedName("wind_dir_deg")    protected float windDirection;
    
    protected   double  windSpeedMPH;
    

    public WS2310_WindEvent() 
    {
       log.trace( "Created event WS2310_WindEvent" );
    }

    public static WS2310_WindEvent fromJson (String jsonMessage)
    {
        Gson    gson = new Gson();
        try {
            WS2310_WindEvent  event = null;
            event = gson.fromJson( jsonMessage, WS2310_WindEvent.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a WS2310_WindEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }
    
    public float getWindAvgSpeed() {
        return windAvgSpeed;
    }

    public double getWindSpeedMPH() {
        return windSpeedMPH;
    }

    public void setWindAvgSpeed(float windAvgSpeed) {
        this.windAvgSpeed = windAvgSpeed;
        this.windSpeedMPH = windAvgSpeed * 2.23694;
    }

    public float getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(float windDirection) {
        this.windDirection = windDirection;
    }

}
