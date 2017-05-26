package com.pistachio.base.ip;

public class Location
{
    private String country;
    
    private String province;
    
    private String city;
    
    public Location(String country, String province, String city)
    {
        super();
        this.country = country;
        this.province = province;
        this.city = city;
    }
    
    public Location()
    {
        
    }
    public Location(String province, String city)
    {
        this.country = "China";
        this.province = province;
        this.city = city;
    }
    
    public String getCountry()
    {
        return country;
    }
    
    public void setCountry(String country)
    {
        this.country = country;
    }
    
    public String getProvince()
    {
        return province;
    }
    
    public void setProvince(String province)
    {
        this.province = province;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    @Override
    public String toString()
    {
        StringBuilder location = new StringBuilder();
        location.append(country);
        location.append("/");
        location.append(province);
        location.append("/");
        location.append(city);
        return location.toString();
    }
    
}
