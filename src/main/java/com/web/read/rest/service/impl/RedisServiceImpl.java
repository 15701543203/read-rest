package com.web.read.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.web.read.common.bean.ReadResult;
import com.web.read.common.utils.ExceptionUtil;
import com.web.read.rest.dao.JedisClient;
import com.web.read.rest.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	private JedisClient jedisClient;
	
	@Value("INDEX_CONTENT_REDIS_KEY")
	private String INDEX_CONTENT_REDIS_KEY;
	
	@Override
	public ReadResult syncContent(long contetnCategoryId) {
		
		try {
			jedisClient.hdel(INDEX_CONTENT_REDIS_KEY,contetnCategoryId+"");
		} catch (Exception e) {
			e.printStackTrace();
			return ReadResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return ReadResult.ok();
	}

}
