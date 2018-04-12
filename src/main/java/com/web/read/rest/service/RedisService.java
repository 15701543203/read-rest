package com.web.read.rest.service;

import com.web.read.common.bean.ReadResult;

public interface RedisService {
	/**
	 * redis中缓存同步
	 * Description: 当read-manager更改商品信息的时候，删除缓存中的旧内容
	 * @param contetnCategoryId
	 * @return
	 */
	ReadResult syncContent(long contetnCategoryId);
}
