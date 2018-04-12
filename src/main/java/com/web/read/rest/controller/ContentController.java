package com.web.read.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.read.bean.TbContent;
import com.web.read.common.bean.ReadResult;
import com.web.read.common.utils.ExceptionUtil;
import com.web.read.rest.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping(value="/list/{contentCategoryId}")
	@ResponseBody
	public ReadResult getContentList(@PathVariable long contentCategoryId){
		System.out.println("read-rest8081 getContentList");
		try {
			List<TbContent> contentList = contentService.getContentList(contentCategoryId);
			return ReadResult.ok(contentList);
		} catch (Exception e) {
			e.printStackTrace();
			return ReadResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
