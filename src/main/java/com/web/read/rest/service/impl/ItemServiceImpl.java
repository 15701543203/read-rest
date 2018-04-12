package com.web.read.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.web.read.bean.TbItem;
import com.web.read.bean.TbItemDesc;
import com.web.read.bean.TbItemParamItem;
import com.web.read.bean.TbItemParamItemExample;
import com.web.read.bean.TbItemParamItemExample.Criteria;
import com.web.read.common.bean.ReadResult;
import com.web.read.common.utils.JsonUtils;
import com.web.read.mapper.TbItemDescMapper;
import com.web.read.mapper.TbItemMapper;
import com.web.read.mapper.TbItemParamItemMapper;
import com.web.read.rest.dao.JedisClient;
import com.web.read.rest.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;

	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	@Autowired
	private JedisClient jedisClient;

	@Override
	public ReadResult getItemBaseInfo(long itemId) {

	  	// 获取redis中商品信息
		String jsonFromRedis = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");

		try {
			if (!StringUtils.isEmpty(jsonFromRedis)) {
				TbItem item = JsonUtils.jsonToPojo(jsonFromRedis, TbItem.class);
				return ReadResult.ok(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 使用商品id查询商品的具体信息
		TbItem item = itemMapper.selectByPrimaryKey(itemId);

		try {
			// 写入redis
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(item));

			// 设置key的过期时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ReadResult.ok();
	}

	@Override
	public ReadResult getItemDescription(long itemId) {

		try {
			// 从redis中获取到商品信息
			String jsonFromRedis = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");

			if (!StringUtils.isEmpty(jsonFromRedis)) {
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(jsonFromRedis, TbItemDesc.class);
				return ReadResult.ok(itemDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

		try {
			// 添加到redis
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(itemDesc));
			// 设置过去时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ReadResult.ok(itemDesc);
	}

	@Override
	public ReadResult getItemParam(long itemId) {

		try {
			// 从redis中获取商品信息
			String jsonFromRedis = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");

			if (!StringUtils.isEmpty(jsonFromRedis)) {
				TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(jsonFromRedis, TbItemParamItem.class);
				return ReadResult.ok(itemParamItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 查询规格参数
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> itemParamList = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (itemParamList != null && itemParamList.size() > 0) {
			TbItemParamItem paramItem = itemParamList.get(0);
			try {
				// 向redis中添加数据
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
				// 设置过期时间
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return ReadResult.build(400, "找不到此商品规格参数");

	}

}
