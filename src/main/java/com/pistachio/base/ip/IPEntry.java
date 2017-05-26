package com.pistachio.base.ip;

/**
 * one piece of IP range record, not only include nations and location but also start IP and end IP
 * 
 * @author swallow
 */
public class IPEntry
{
    public String beginIp;
    
    public String endIp;
    
    public String country;
    
    public String area;
    
    /**
     * constructor
     */
    
    public IPEntry()
    {
        beginIp = endIp = country = area = "";
    }
    
    public String toString()
    {
        return this.area + "  " + this.country + "IP range:" + this.beginIp + "-" + this.endIp;
    }
}
