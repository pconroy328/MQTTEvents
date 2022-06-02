package com.donotfreezesoftware.mqttevents;
    
import com.donotfreezesoftware.mqttevents.Acurite_5n1.Acurite_5n1_Event;
import com.donotfreezesoftware.mqttevents.Acurite_5n1.Acurite_5n1_TemperatureEvent;
import com.donotfreezesoftware.mqttevents.Acurite_5n1.Acurite_5n1_WindRainEvent;
import com.donotfreezesoftware.mqttevents.LaCrosse_WS2310.WS2310_HumidityEvent;
import com.donotfreezesoftware.mqttevents.LaCrosse_WS2310.WS2310_RainEvent;
import com.donotfreezesoftware.mqttevents.LaCrosse_WS2310.WS2310_TemperatureEvent;
import com.donotfreezesoftware.mqttevents.LaCrosse_WS2310.WS2310_WindEvent;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pconroy
 */
public class RTL_433_Event 
        // this does NOT extend MQTTMessage_POJO because I do not generate these
        //  events. They're created by piping the JSON output of the rtl_433 program
        //  directly into the MQTT Broker
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( RTL_433_Event.class );    
    
    
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
    protected   LocalDateTime   localDateTime;          // convert dateTimeString    
    protected   long            createdOn;              // millis when message was instantiated
    protected   String          jsonPayload;            // the entire JSON message


    @SerializedName("time")             protected String  dateTime;
    @SerializedName("protocol")         protected int     protocol;
    @SerializedName("model")            protected String  model;
    @SerializedName("id")               protected int id;
    @SerializedName("mod")              protected String modulation;
    @SerializedName("freq")             protected float frequency;
    @SerializedName("rssi")             protected float RSSI;
    @SerializedName("snr")              protected float signalToNoiseLevel;
    @SerializedName("noise")            protected float noiseLevel;
    
    //
    // Don't need a superclass constructor. But do need a subclass ctor!
    //  Then Gson seems to know how to fill in the superclass attributes when 
    //  parsing the subclass. You can't do much in here because Gson has called
    //  this ctor before anything has been deserialized
    //
    public  RTL_433_Event ()
    {
        log.trace( "Created event RTL_433_Event" );        
    }

    // -------------------------------------------------------------------------
    //  NB: Class cannote be Abstract for Gson to do it's thing.
    //  
    public static RTL_433_Event fromJson (String jsonMessage)
    {
        Gson    gson = new Gson();
        try {
            RTL_433_Event  event = null;
            event = gson.fromJson( jsonMessage, RTL_433_Event.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );

            //
            // I'm not really very interested in this Super Class event!
            //  I just need to examine the "model" field so I can figure out which
            //  subclass to invoke.
            String  model = event.getModel().toLowerCase().trim();
            if (model.equals( "acurite-5n1" ) ) {
                log.debug( "Acurite-5n1 model detected" );
                event = Acurite_5n1_Event.fromJson( jsonMessage );

                // Wind Events will be message_type 49. Temperature Events will be message_type 56
                int msgType = ((Acurite_5n1_Event) event).getMessage_type();
                if (msgType == 49)
                    event = Acurite_5n1_WindRainEvent.fromJson( jsonMessage );
                else if (msgType == 56)
                    event = Acurite_5n1_TemperatureEvent.fromJson( jsonMessage );
                else {
                    log.error( "Acurite-5n1 Event and msgType is not 49 or 56. json [ " + jsonMessage + "]" );
                    return null;
                }
            } else if (model.equals( "lacrosse-ws2310" ) ) {
                log.debug( "LaCrosse-WS2310 model detected" );
                
                // No way to tell what exact type of event without peeking at the payload
                if (jsonMessage.contains( "temperature_" ) )
                    event = WS2310_TemperatureEvent.fromJson( jsonMessage );
                else if (jsonMessage.contains( "humidity" ) )
                    event = WS2310_HumidityEvent.fromJson( jsonMessage );
                else if (jsonMessage.contains( "wind_dir_deg" ) )
                    event = WS2310_WindEvent.fromJson( jsonMessage );
                else if (jsonMessage.contains( "rain" ) )
                    event = WS2310_RainEvent.fromJson( jsonMessage );
                else {
                    log.error( "Unrecognized WS2310 EVent came in: [" + jsonMessage + "]" );
                    return null;
                }
            }
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a RTL_433_Event Object (" + jsonMessage + ")", ex);
            return null;
        }
    }

    // -------------------------------------------------------------------------

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public String getJsonPayload() {
        return jsonPayload;
    }

    public void setJsonPayload(String jsonPayload) {
        this.jsonPayload = jsonPayload;
    }
    
    public String getDateTimeString() {
        return dateTime;
    }

    public void setDateTimeString(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModulation() {
        return modulation;
    }

    public void setModulation(String modulation) {
        this.modulation = modulation;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public float getRSSI() {
        return RSSI;
    }

    public void setRSSI(float RSSI) {
        this.RSSI = RSSI;
    }

    public float getSignalToNoiseLevel() {
        return signalToNoiseLevel;
    }

    public void setSignalToNoiseLevel(float signalToNoiseLevel) {
        this.signalToNoiseLevel = signalToNoiseLevel;
    }

    public float getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(float noiseLevel) {
        this.noiseLevel = noiseLevel;
    }
    
    // -------------------------------------------------------------------------
    public  void setDateTimeFromString (String dateTimeStr)
    {
        if (dateTimeStr == null) {
            //log.error( "Passed in a null dateTimeString to parse on topic [" + this.getTopic() + "]" );
            localDateTime = LocalDateTime.now();
            return;
        }
                
        try {
            localDateTime = LocalDateTime.parse( dateTimeStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME );
            
        } catch (DateTimeParseException pex) {  try {
            localDateTime = LocalDateTime.parse( dateTimeStr, DateTimeFormatter.ISO_DATE_TIME );
            
        } catch (DateTimeParseException pex2) { try {
            localDateTime = LocalDateTime.parse( dateTimeStr, DateTimeFormatter.ISO_ZONED_DATE_TIME );
            
        } catch (Exception ex) {  } }
            //log.info( "Unknown dateTime formatted string in message payload [" + dateTimeStr + "]" );
            
            List<String> formatStrings = Arrays.asList("yyyy-MM-dd'T'HH:mmX", 
                        "yyyy-MM-dd'T'HH:mm:ss'Z'",   "yyyy-MM-dd'T'HH:mm:ssZ",
                        "yyyy-MM-dd'T'HH:mm:ss",      "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                        "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss", 
                        "MM/dd/yyyy HH:mm:ss",        "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'", 
                        "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS", 
                        "MM/dd/yyyy'T'HH:mm:ssZ",     "MM/dd/yyyy'T'HH:mm:ss", 
                        "yyyy:MM:dd HH:mm:ss",        "yyyyMMdd", "yyyy-MM-dd'T'HH:mmZ");

            for (String formatString : formatStrings) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
                    localDateTime = LocalDateTime.parse(dateTimeStr, formatter);
                    break;
                } catch (DateTimeParseException pex3) {
                    /* do nothing */ ;
                }
            }
            
            //
            // Still no luck?  Just set it to NOW
            if (localDateTime == null) {
                log.error( "Unable to parse this date time string [" + dateTimeStr + "]" );
                localDateTime = LocalDateTime.now();
            }
        }
    }    
}
    