package com.web.read.rest.service;

import java.util.List;

import com.web.read.common.bean.CatResult;

public interface ItemCatService {

	/**
	 * 获取商品的分类信息
	 * Description: 
	 * @return
	 */
	CatResult getItemCatList();
	
}
