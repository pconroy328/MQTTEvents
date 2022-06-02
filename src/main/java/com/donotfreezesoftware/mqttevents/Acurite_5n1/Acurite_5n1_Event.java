package com.donotfreezesoftware.mqttevents.Acurite_5n1;

import com.donotfreezesoftware.mqttevents.RTL_433_Event;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pconroy
 */
public class Acurite_5n1_Event extends RTL_433_Event 
{
    //
    //  This MUST be static, or GSON just enters an infinite loop
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( Acurite_5n1_Event.class );    
    
    /* {
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
    }*/
    @SerializedName("message_type")     protected int  message_type;
    @SerializedName("channel")          protected String  channel;
    @SerializedName("sequence_num")     protected int sequence_num;
    @SerializedName("battery_ok")       protected int battery_ok;
    @SerializedName("mic")              protected String  mic;
    

    public Acurite_5n1_Event() 
    {
        log.trace( "Created event Acurite_5n1_Event" );
    }
    
    // -------------------------------------------------------------------------
    public static Acurite_5n1_Event fromJson( String jsonMessage )
    {
        Gson    gson = new Gson();
        try {
            Acurite_5n1_Event  event = null;
            event = gson.fromJson( jsonMessage, Acurite_5n1_Event.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a Acurite_5n1_Event Object (" + jsonMessage + ")", ex);
            return null;
        }
    }
    

    public int getMessage_type() {
        return message_type;
    }

    public void setMessage_type(int message_type) {
        this.message_type = message_type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getSequence_num() {
        return sequence_num;
    }

    public void setSequence_num(int sequence_num) {
        this.sequence_num = sequence_num;
    }

    public int getBattery_ok() {
        return battery_ok;
    }

    public void setBattery_ok(int battery_ok) {
        this.battery_ok = battery_ok;
    }

    public String getMic() {
        return mic;
    }

    public void setMic(String mic) {
        this.mic = mic;
    }    
}
