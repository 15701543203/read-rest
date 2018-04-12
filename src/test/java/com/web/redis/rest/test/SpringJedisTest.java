package com.web.redis.rest.test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
/**
 * 单元测试都没有问题
 * @Title   SpringJedisTest.java
 * <p>Description:</p>
 * <p>Company: </p>
 * @Package com.web.redis.rest.test
 * @Author  Administrator
 * @Date    2018年3月27日下午4:03:48
 * @version v1.0
 */
public class SpringJedisTest {
	/**
	 * 单机版测试
	 * <p>
	 * Title: testSpringJedisSingle
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	@Test
	public void testSpringJedisSingle() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
		Jedis jedis = pool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		jedis.close();
		pool.close();
	}
	
	/**
	 * 集群版测试
	 * @throws IOException 
	 */
	@Test
	public void testSpringJedisCluster() throws IOException {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster jedisCluster =  (JedisCluster) applicationContext.getBean("redisClient");
		String string = jedisCluster.get("key1");
		System.out.println(string);
		jedisCluster.close();
	}

	
}
