package com.web.read.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.web.read.bean.TbContent;
import com.web.read.bean.TbContentExample;
import com.web.read.bean.TbContentExample.Criteria;
import com.web.read.common.utils.JsonUtils;
import com.web.read.mapper.TbContentMapper;
import com.web.read.rest.dao.JedisClient;
import com.web.read.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("INDEX_CONTENT_REDIS_KEY")
	private String INDEX_CONTENT_REDIS_KEY;

	@Override
	public List<TbContent> getContentList(long contentCategoryId) {

		// 先到缓存中获取一遍，如果有直接返回，如果没有在到数据库中查找
		try {
			String contentsString = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentCategoryId + "");
			if (!StringUtils.isEmpty(contentsString)) {
				List<TbContent> contentRedis = JsonUtils.jsonToList(contentsString, TbContent.class);
				return contentRedis;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		criteria.andCategoryIdEqualTo(contentCategoryId);
		List<TbContent> contents = this.contentMapper.selectByExample(example);

		try {
			// 如果缓存中没有，需要先添加到缓存当中
			String cache = JsonUtils.objectToJson(contents);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentCategoryId + "", cache);
			System.out.println("已经向Redis中添加数据");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contents;
	}
}
