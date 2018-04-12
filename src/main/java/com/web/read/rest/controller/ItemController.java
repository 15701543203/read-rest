package com.web.read.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.read.common.bean.ReadResult;
import com.web.read.rest.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/info/{itemId}",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public ReadResult getItemBaseInfo(@PathVariable long itemId){
		ReadResult result = itemService.getItemBaseInfo(itemId);
		return result;
	}
	
	
	@RequestMapping(value="/desc/{itemId}",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public ReadResult getItemDescInfo(@PathVariable long itemId){
		ReadResult result = itemService.getItemDescription(itemId);
		return result;
	}
	
	
	@RequestMapping(value="/param/{itemId}",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public ReadResult getItemParamItemInfo(@PathVariable long itemId){
		ReadResult result = itemService.getItemParam(itemId);
		return result;
	}
}
