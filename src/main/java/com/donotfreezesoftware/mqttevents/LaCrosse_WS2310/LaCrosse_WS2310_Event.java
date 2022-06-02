package com.donotfreezesoftware.mqttevents.LaCrosse_WS2310;

import com.donotfreezesoftware.mqttevents.RTL_433_Event;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pconroy
 */
public abstract class LaCrosse_WS2310_Event extends RTL_433_Event 
{
      //
    //  This MUST be static, or GSON just enters an infinite loop
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( LaCrosse_WS2310_Event.class );    

    
    /*
    {"time" : "2022-04-05T11:04:34", "protocol" : 34, "model" : "LaCrosse-WS2310", "id" : 28, "temperature_F" : 58.820, "mod" : "ASK", "freq" : 434.022, "rssi" : -5.488, "snr" : 24.615, "noise" : -30.103}
    {"time" : "2022-04-05T11:04:34", "protocol" : 34, "model" : "LaCrosse-WS2310", "id" : 28, "humidity" : 45, "mod" : "ASK", "freq" : 434.014, "rssi" : -5.506, "snr" : 24.877, "noise" : -30.383}
    {"time" : "2022-04-05T11:04:34", "protocol" : 34, "model" : "LaCrosse-WS2310", "id" : 28, "rain_in" : 19.354, "mod" : "ASK", "freq" : 434.026, "rssi" : -5.487, "snr" : 24.105, "noise" : -29.591}
    {"time" : "2022-04-05T11:04:35", "protocol" : 34, "model" : "LaCrosse-WS2310", "id" : 28, "wind_avg_m_s" : 0.900, "wind_dir_deg" : 45.000, "mod" : "ASK", "freq" : 434.024, "rssi" : -5.493, "snr" : 23.429, "noise" : -28.922}

    */
    
    public LaCrosse_WS2310_Event()
    {
        log.trace( "Created event LaCrosse_WS2310_Event" );        
    }
    
}
