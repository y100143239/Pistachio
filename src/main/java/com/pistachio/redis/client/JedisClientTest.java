package com.pistachio.redis.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class Person implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	public String name = "dddxxxxxxd";
}

public class JedisClientTest
{
	public static void main(String[] args)
	{
		JedisClient client = new JedisClient();
		
		client.set("name", new Person());
		System.out.println(((Person)client.getObject("name")).name);
		
		Map<String, String> map = new HashMap<String,String>();
		map.put("name2", "abc");
		client.set("name1", map);
		System.out.println(client.getMap("name1"));
		
		
		client.zadd("set1", "dfsadfas", 1);
		client.zadd("set1", "dd", 2);
		client.zadd("set1", "d", 0);

		System.out.println(client.zrevrange("set1"));
	}
}
