package com.web.read.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.read.common.bean.ReadResult;
import com.web.read.rest.service.RedisService;

/**
 * @Title   RedisController.java
 * <p>Description:</p>
 * <p>Company: </p>
 * @Package com.web.read.rest.controller
 * @Author  Administrator
 * @Date    2018年3月28日下午12:18:38
 * @version v1.0
 */
@Controller
@RequestMapping("/cache/sync")
public class RedisController {

	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/content/{contetnCategoryId}")
	public ReadResult contentCacheSync(@PathVariable long contetnCategoryId){
		ReadResult result = redisService.syncContent(contetnCategoryId);
		return result;
	}
}
