package com.connectfour.miscellaneous;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;

public class JedisWrapper {
	static ObjectMapper mapper = new ObjectMapper();
	public static void setJedis(String key, Object value) throws Exception
	{
		Jedis jedis = new Jedis("localhost");
		jedis.set(key, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value));
		jedis.close();
	}
	
	public static void setJedis(String key, String value, int expiry) throws Exception
	{
		Jedis jedis = new Jedis("localhost");
		jedis.set(key, value);
		if (expiry > 0)
			jedis.expire(key, expiry);
		jedis.close();
	}

}
