package com.donotfreezesoftware.mqttevents.Acurite_5n1;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pconroy
 */
public class Acurite_5n1_TemperatureEvent extends Acurite_5n1_Event 
{
   //
    //  This MUST be static, or GSON just enters an infinite loop
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( Acurite_5n1_TemperatureEvent.class );    
    
    
    /*
        "time":"2022-04-04T09:23:10",
        "protocol":40,
        "model":"Acurite-5n1",
        "message_type":56,
        "id":3977,
        "channel":"A",
        "sequence_num":2,
        "battery_ok":1,
        "wind_avg_mi_h":0.000,
        "temperature_F":50.900,
        "humidity":50,
        "mic":"CHECKSUM",
        "mod":"ASK",
        "freq":433.941,
        "rssi":-11.515,
        "snr":12.117,
        "noise":-23.632   
*/
    //
    //  Looks like the Acurite sends 3 identical messages with sequence numbers changing
    //  from 0 to 1 to 2. Other than the sequence number, the payload is identical:
    //      {"time" : "2022-04-04T09:38:10", "protocol" : 40, "model" : "Acurite-5n1", "message_type" : 56, "id" : 3713, "channel" : "C", "sequence_num" : 0, "battery_ok" : 1, "wind_avg_mi_h" : 1.136, "temperature_F" : 49.500, "humidity" : 50, "mic" : "CHECKSUM", "mod" : "ASK", "freq" : 433.947, "rssi" : -1.695, "snr" : 23.045, "noise" : -24.741}
    //      {"time" : "2022-04-04T09:38:10", "protocol" : 40, "model" : "Acurite-5n1", "message_type" : 56, "id" : 3713, "channel" : "C", "sequence_num" : 1, "battery_ok" : 1, "wind_avg_mi_h" : 1.136, "temperature_F" : 49.500, "humidity" : 50, "mic" : "CHECKSUM", "mod" : "ASK", "freq" : 433.947, "rssi" : -1.695, "snr" : 23.045, "noise" : -24.741}
    //      {"time" : "2022-04-04T09:38:10", "protocol" : 40, "model" : "Acurite-5n1", "message_type" : 56, "id" : 3713, "channel" : "C", "sequence_num" : 2, "battery_ok" : 1, "wind_avg_mi_h" : 1.136, "temperature_F" : 49.500, "humidity" : 50, "mic" : "CHECKSUM", "mod" : "ASK", "freq" : 433.947, "rssi" : -1.695, "snr" : 23.045, "noise" : -24.741}

    //
    // I think I'll just grab ONE of these as the actual data event
    @SerializedName("wind_avg_mi_h")    protected float windSpeed;
    @SerializedName("temperature_F")    protected float outsideTemperature;
    @SerializedName("humidity")         protected float outsideHumidity;

    
    
    public Acurite_5n1_TemperatureEvent() 
    {
        log.trace( "Created event Acurite_5n1_TemperatureEvent" );
    }

    // -------------------------------------------------------------------------
    public static Acurite_5n1_TemperatureEvent fromJson( String jsonMessage )
    {
        Gson    gson = new Gson();
        try {
            Acurite_5n1_TemperatureEvent  event = null;
            event = gson.fromJson( jsonMessage, Acurite_5n1_TemperatureEvent.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a Acurite_5n1_TemperatureEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }
    
    
    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getOutsideTemperature() {
        return outsideTemperature;
    }

    public void setOutsideTemperature(float outsideTemperature) {
        this.outsideTemperature = outsideTemperature;
    }

    public float getOutsideHumidity() {
        return outsideHumidity;
    }

    public void setOutsideHumidity(float outsideHumidity) {
        this.outsideHumidity = outsideHumidity;
    }    
}
