package com.donotfreezesoftware.mqttevents.Acurite_5n1;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pconroy
 */
public class Acurite_5n1_WindRainEvent extends Acurite_5n1_Event 
{
    //
    //  This MUST be static, or GSON just enters an infinite loop
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( Acurite_5n1_WindRainEvent.class );    

    //
    //
    //  Looks like the Acurite sends 3 identical messages with sequence numbers changing
    //  from 0 to 1 to 2. Other than the sequence number, the payload is identical:
    //      {"time" : "2022-04-04T09:42:13", "protocol" : 40, "model" : "Acurite-5n1", "message_type" : 49, "id" : 3713, "channel" : "C", "sequence_num" : 0, "battery_ok" : 1, "wind_avg_mi_h" : 1.650, "wind_dir_deg" : 270.000, "rain_in" : 2.170, "mic" : "CHECKSUM", "mod" : "ASK", "freq" : 433.947, "rssi" : -1.978, "snr" : 25.695, "noise" : -27.673}
    //      {"time" : "2022-04-04T09:42:13", "protocol" : 40, "model" : "Acurite-5n1", "message_type" : 49, "id" : 3713, "channel" : "C", "sequence_num" : 1, "battery_ok" : 1, "wind_avg_mi_h" : 1.650, "wind_dir_deg" : 270.000, "rain_in" : 2.170, "mic" : "CHECKSUM", "mod" : "ASK", "freq" : 433.947, "rssi" : -1.978, "snr" : 25.695, "noise" : -27.673}
    //      {"time" : "2022-04-04T09:42:13", "protocol" : 40, "model" : "Acurite-5n1", "message_type" : 49, "id" : 3713, "channel" : "C", "sequence_num" : 2, "battery_ok" : 1, "wind_avg_mi_h" : 1.650, "wind_dir_deg" : 270.000, "rain_in" : 2.170, "mic" : "CHECKSUM", "mod" : "ASK", "freq" : 433.947, "rssi" : -1.978, "snr" : 25.695, "noise" : -27.673}

    @SerializedName("wind_avg_mi_h")    protected float windSpeed;
    @SerializedName("wind_dir_deg")     protected float windDirection;
    @SerializedName("rain_in")          protected float rainInches;

    public Acurite_5n1_WindRainEvent() 
    {
        log.trace( "Created event Acurite_5n1_WindRainEvent" );
    }

    public static Acurite_5n1_WindRainEvent fromJson( String jsonMessage )
    {
        Gson    gson = new Gson();
        try {
            Acurite_5n1_WindRainEvent  event = null;
            event = gson.fromJson( jsonMessage, Acurite_5n1_WindRainEvent.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a Acurite_5n1_WindRainEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }
    
    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(float windDirection) {
        this.windDirection = windDirection;
    }

    public float getRainInches() {
        return rainInches;
    }

    public void setRainInches(float rainInches) {
        this.rainInches = rainInches;
    }
}
