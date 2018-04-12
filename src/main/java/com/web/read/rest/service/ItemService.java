package com.web.read.rest.service;

import com.web.read.common.bean.ReadResult;

public interface ItemService {

	/**
	 * 查询商品基本信息
	 * @param itemId
	 * @return
	 */
	ReadResult getItemBaseInfo(long itemId);
	
	/**
	 * 获取商品的描述信息
	 * @param itemId
	 * @return
	 */
	ReadResult getItemDescription(long itemId);
	
	/**
	 * 获取商品的规格信息
	 * @param itemId
	 * @return
	 */
	ReadResult getItemParam(long itemId);
}
