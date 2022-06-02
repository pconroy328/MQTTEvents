package com.donotfreezesoftware.mqtt;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Set;
import net.straylightlabs.hola.dns.Domain;
import net.straylightlabs.hola.sd.Instance;
import net.straylightlabs.hola.sd.Query;
import net.straylightlabs.hola.sd.Service;
import org.slf4j.LoggerFactory;

/**
 *  Instance{name='HomeBroker', addresses=[/xxx.xxx.161.215, /fe80:0:0:0:94b:521d:8937:4220], port=2883, attributes={info={"version":"1","name":"Home","address":"123 MAIN ST","city":"ANYTOWN", "state":"VA","zip":"12345"}}}
 *  Instance{name='Roomba-', addresses=[/xxx.yyy.zzz.190], port=2883, attributes={irobotmcs={"ver":"3","hostname":"Roomba","robotname":"y","ip":"xxx.yyy.zzz.1","mac":"00:","sw":"3","sku":"R69","nc":0,"proto":"mqtt","cap":{"ota":1,"eco":1,"svcConf":1}}}}
 *
 *  Instance Name: HomeBroker
 *  Instance Port:2883
 *  Address Canonical Name: foo.lan
 *  Address Host Name: foo.lan
 *  Address address: xxx.yyy.zzz.215
 *  
 *  Instance Name: HomeMQTTBroker
 *  Instance Port:2883
 *  Address Canonical Name: fe80
 *  Address Host Name: fe80:0
 *  Address address: fe80:0:0
 * 
 *  Instance Name: Roomba-
 *  Instance Port:2883
 *  Address Canonical Name: Roomba-.lan
 *  Address Host Name: Roomba-.lan
 *  Address address: xxx.yyy.zzz.190
 * 
 *  02-Jun-2022 created
 *  @author pconroy
 */
public class BrokerUtils 
{
    private static final org.slf4j.Logger   log = LoggerFactory.getLogger( BrokerUtils.class );    
    
    
    protected   static  String  brokerIPAddress = null;
    protected   static  int     brokerPortNumber = 0;
    protected   static  String  brokerHostName = null;
    
    
    // -------------------------------------------------------------------------
    public static boolean isValidInet4Address(String ip)
    {
        String[] groups = ip.split("\\.");
 
        if (groups.length != 4) {
            return false;
        }
 
        try {
            return Arrays.stream(groups)
                        .filter(s -> s.length() > 1 )
                        .map(Integer::parseInt)
                        .filter(i -> (i >= 0 && i <= 255))
                        .count() == 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // -------------------------------------------------------------------------
    public  static boolean  findBrokerHostIP (String instanceNameMatches)
    {
        log.info( "Looking for an MQTT broker with an instance name that partial matches with [" + instanceNameMatches + "]" );
        
        boolean found = false;
        
        try {
            //
            //  Find all MQTT mDNS records
            Service service = Service.fromName( "_mqtt._tcp.local." );
            Query query = Query.createFor( service, Domain.LOCAL );
            Set<Instance> instances = query.runOnce();
            
            //
            //  Now loop thru all instances and look for one with a name that matches
            //  We also want ONLY the IP V4 instance
            for (Instance instance : instances) {
                Set<InetAddress> addresses = instance.getAddresses();
                
                //
                // An instance could have multiple address - IPV4 and IPV6
                for (InetAddress address : addresses) {
                    //
                    // Look for any match in the instance name - need not be an exact match
                    String  instanceName = instance.getName().toLowerCase();
                    
                    if (instanceName.contains(instanceNameMatches.toLowerCase() )) {
                        // name match, but we want IP V4 matches only, for now...
                        log.debug( "Found an instance match for host: [" + instance.getName() + "]" );
                        
                        if (isValidInet4Address( address.getHostAddress() ) ) {
                            log.info( "Found an instance IPV4 match for host: [" + instance.getName() + "]" );
                            brokerHostName = instance.getName();
                            brokerIPAddress = address.getHostAddress();
                            brokerPortNumber = instance.getPort();
                            found = true;
                        }
                    }
                }
            }
            
        } catch (UnknownHostException e) {
            log.error( "Unknown host Exception: " + e );
        } catch (IOException e) {
            log.error( "IO Exception: " +  e );
        }        
        
        return found;
    }

    public static String getBrokerIPAddress() {
        return brokerIPAddress;
    }

    public static int getBrokerPortNumber() {
        return brokerPortNumber;
    }

    public static String getBrokerHostName() {
        return brokerHostName;
    }

    // -------------------------------------------------------------------------
    public static String createMQTTBrokerURL()
    {
        //  "tcp://foo.local:2883",
        String  url = null;
        if ( (brokerHostName != null) & (brokerIPAddress != null) & (brokerPortNumber != 0)) {
            url = "tcp://" + brokerIPAddress + ":" + brokerPortNumber;
        } else {
            log.error( "Cached broker values are null. Did you forget to call findBrokerHostIP() first?" );
        }
        
        return url;
    }
    
    // -------------------------------------------------------------------------
    public static void test (String args[])
    {
        try {
            Service service = Service.fromName("_mqtt._tcp.local.");
            Query query = Query.createFor(service, Domain.LOCAL);
            Set<Instance> instances = query.runOnce();
            instances.stream().forEach(System.out::println);
            
            for (Instance instance : instances) {
                String userVisibleName = instance.getName();
                Set<InetAddress> addresses = instance.getAddresses();
                int port = instance.getPort();
                String platform = null;
                if (instance.hasAttribute("platform")) {
                    platform = instance.lookupAttribute("platform");
                }
                String text = null;
                if (instance.hasAttribute("txt")) {
                    text = instance.lookupAttribute("txt");
                }
                
                for (InetAddress address : addresses) {
                    System.out.println( "Instance Name: " + instance.getName() );
                    System.out.println( "Instance Port:" + instance.getPort() );
                    System.out.println( "Address Canonical Name: " + address.getCanonicalHostName() );
                    System.out.println( "Address Host Name: " + address.getHostName() );
                    System.out.println( "Address address: " + address.getHostAddress() );
                    if (isValidInet4Address( address.getHostAddress() ) )
                        System.out.println( "             USE THIS ONE" );
                    System.out.println( "Platform: " + platform );
                    System.out.println( "Txt: " + text );
                }
            }
            
        } catch (UnknownHostException e) {
            System.err.println( "Unknown host: " + e );
        } catch (IOException e) {
            System.err.println( "IO error: " +  e );
        }        
    }
}
