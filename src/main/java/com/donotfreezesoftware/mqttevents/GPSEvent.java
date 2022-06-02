package com.donotfreezesoftware.mqttevents;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.slf4j.LoggerFactory;

/**
 *  Changed GPS Payload in June of 2021. Added HDOP and VDOP.
 *      {   "topic":"GPS","version":"1.1", "dateTime":"2021-06-30T12:08:00-0900", 
 *          "mode":"3D", "latitude":xxx.020852, "longitude":xxx.088016, "altitude":xxx.10, 
 *          "speed":0.0, "track":-100.0, "climb":0.0, 
 *          "GDOP":"GOOD", "HDOP":"EXCELLENT", "VDOP":"EXCELLENT", 
 *          "distance":0.00, "geohash":"abcdefg"}
 * @author pconroy
 */
public class GPSEvent extends MQTTMessage_POJO
{
    //
    //  This MUST be static, or GSON just enters an infinite loop
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( GPSEvent.class );    
    
    //
    // Remember topic, version, dateTime will be in Superclass
    //
    @SerializedName("mode")         String  mode;
    @SerializedName("latitude")     float   latitude;
    @SerializedName("longitude")    float   longitude;            
    @SerializedName("altitude")     float   altitude;
    
    @SerializedName("speed")        float   speed;
    @SerializedName("track")        float   track;
    @SerializedName("climb")        float   climb;
    
    @SerializedName("GDOP")         String  GDOP;
    @SerializedName("HDOP")         String  HDOP;
    @SerializedName("VDOP")         String  VDOP;
    
    @SerializedName("distance")     float   distance;
    @SerializedName("geohash")      String  geohash;
    
    // -------------------------------------------------------------------------
    //  Create a simple Constructor so the superclass fields get initalized
    //  by Gson deserialization
    public  GPSEvent ()
    {
        super();
    }

    // -------------------------------------------------------------------------
    public static GPSEvent fromJson (String jsonMessage )
    {
        Gson    gson = new Gson();
        try {
            GPSEvent  event = null;
            event = gson.fromJson( jsonMessage, GPSEvent.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a GPSEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }
    
    //
    // Don't forget getters and settors or you'll see an error like:
    //  Exception in thread "main" java.lang.RuntimeException: com.espertech.esper.compiler.client.EPCompileException: 
    //  Failed to validate filter expression 'gpse.distance=0': Failed to resolve property 'gpse.distance' to a 
    //  stream or nested property in a stream [@name('gpse_rvhome') SELECT * FROM GPSEvent gpse WHERE gpse.distance=0]

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getTrack() {
        return track;
    }

    public void setTrack(float track) {
        this.track = track;
    }

    public float getClimb() {
        return climb;
    }

    public void setClimb(float climb) {
        this.climb = climb;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getGDOP() {
        return GDOP;
    }

    public void setGDOP(String GDOP) {
        this.GDOP = GDOP;
    }

    public String getHDOP() {
        return HDOP;
    }

    public void setHDOP(String HDOP) {
        this.HDOP = HDOP;
    }
    
    public String getVDOP() {
        return VDOP;
    }

    public void setVDOP(String VDOP) {
        this.VDOP = VDOP;
    }
    
    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }
    
    
}
