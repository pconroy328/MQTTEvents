/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.donotfreezesoftware.mqttevents;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.slf4j.LoggerFactory;

/**
 * Current Alarms:
 *  Examples:
 *      { "topic":"WS2308/ALARM", "version":"1.1", "dateTime":"2021-07-03T11:52:44-0100" , "alarmMsg":"OUTDOOR WINDSPEED HIGH" , "speed":51.0, "heading":51.0 }
 *
 *      { "topic":"WS2308/ALARM", "version":"1.1", "dateTime":"2021-07-03T11:52:44-0100" , "alarmMsg":"INDOOR HUMIDITY HIGH" , "value":51.0 }
 *      { "topic":"WS2308/ALARM", "version":"1.1", "dateTime":"2021-07-03T11:52:44-0100" , "alarmMsg":"OUTDOOR HUMIDITY HIGH" , "value":51.0 }
 *      { "topic":"WS2308/ALARM", "version":"1.1", "dateTime":"2021-07-03T11:52:44-0100" , "alarmMsg":"INDOOR HUMIDITY LOW" , "value":51.0 }
 *      { "topic":"WS2308/ALARM", "version":"1.1", "dateTime":"2021-07-03T11:52:44-0100" , "alarmMsg":"OUTDOOR HUMIDITY LOW" , "value":51.0 }
 * 
 *      { "topic":"WS2308/ALARM", "version":"1.1", "dateTime":"2021-07-03T11:52:44-0100" , "alarmMsg":"OUTDOOR TEMP HIGH" , "value":100.2 }
 *      { "topic":"WS2308/ALARM", "version":"1.1", "dateTime":"2021-07-03T11:53:13-0100" , "alarmMsg":"INDOOR TEMP HIGH" , "value":51.0 }
 *      { "topic":"WS2308/ALARM", "version":"1.1", "dateTime":"2021-07-03T11:52:44-0100" , "alarmMsg":"OUTDOOR TEMP LOW" , "value":100.2 }
 *      { "topic":"WS2308/ALARM", "version":"1.1", "dateTime":"2021-07-03T11:53:13-0100" , "alarmMsg":"INDOOR TEMP LOW" , "value":51.0 }
 * 
 *      { "topic":"WS2308/ALARM", "version":"1.1", "dateTime":"2021-07-03T12:23:01-0100" , "alarmMsg":"RAIN" , "lastHour":0.06 , "lastDay":0.06 , "total":13.58 }
 * 
 * 
 *  FIX THIS:
 * { "topic":"WS2308/ALARM", "version":"1.1", "dateTime":"2022-02-22T09:44:33-0700" , "alarmMsg":"OUTDOOR HUMIDITY HIGH" , "value":-4.6 }
 * 
 * @author pconroy
 */
public class WeatherAlarmEvent extends MQTTMessage_POJO
{
    //
    //  This MUST be static, or GSON just enters an infinite loop
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( WeatherAlarmEvent.class );    
    
    @SerializedName("alarmMsg")         String  alarmMsg;
    //
    //  Can't a serialized name because the payload changes it
    // @SerializedName("value")            float   value;
    //  Good news is that they are all floats
    float                   value;             // will be there for all alarms
    float                   value2;             // optional, will be there for wind and rain
    float                   value3;             // optional, will be there for rain


    // -------------------------------------------------------------------------
    //  Create a simple Constructor so the superclass fields get initalized
    //  by Gson deserialization
    public  WeatherAlarmEvent ()
    {
        super();
    }

    // -------------------------------------------------------------------------
    public static WeatherAlarmEvent fromJson (String jsonMessage )
    {
        Gson    gson = new Gson();
        try {
            WeatherAlarmEvent  event = null;
            event = gson.fromJson( jsonMessage, WeatherAlarmEvent.class );
            event.setCreatedOn( System.currentTimeMillis() );
            event.setJsonPayload( jsonMessage );
            
            return event;
        } catch (Exception ex) {
            log.error( "Unable to parse json message into a WeatherAlarmEvent Object (" + jsonMessage + ")", ex);
            return null;
        }
    }

    public String getAlarmMsg() {
        return alarmMsg;
    }

    public void setAlarmMsg(String alarmMsg) {
        this.alarmMsg = alarmMsg;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getValue2() {
        // add some null ptr protection
        return value2;
    }

    public void setValue2(float value) {
        this.value2 = (Float.isFinite(value) ? value : Float.NaN);
    }

    public float getValue3() {
        return value3;
    }

    public void setValue3(float value) {
        this.value3 = (Float.isFinite(value) ? value : Float.NaN);
    }

}
