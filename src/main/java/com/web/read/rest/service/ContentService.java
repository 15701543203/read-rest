package com.web.read.rest.service;

import java.util.List;

import com.web.read.bean.TbContent;

public interface ContentService {

	/**
	 * 根据内容分类id查询内容分类列表
	 * Description: 
	 * @param contentId
	 * @return
	 */
	List<TbContent> getContentList(long contentCategoryId);
	
}
