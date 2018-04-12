package com.web.read.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.read.common.bean.CatResult;
import com.web.read.common.utils.JsonUtils;
import com.web.read.rest.service.ItemCatService;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	
	@RequestMapping(value="/itemcat/all",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemCatList(String callback){
//		System.out.println("ItemCatController  /itemcat/all");
		
		CatResult catResult = itemCatService.getItemCatList();
		
		String json = JsonUtils.objectToJson(catResult);
		String result = callback+"("+json+")";
		return result;
	}
}
