package com.web.read.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.read.bean.TbItemCat;
import com.web.read.bean.TbItemCatExample;
import com.web.read.bean.TbItemCatExample.Criteria;
import com.web.read.common.bean.CatNode;
import com.web.read.common.bean.CatResult;
import com.web.read.mapper.TbItemCatMapper;
import com.web.read.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public CatResult getItemCatList() {
		CatResult catResult = new CatResult();

		// 查询分类列表
		catResult.setData(getCatList(0));

		return catResult;
	}

	private List<?> getCatList(long parentId) {
		//创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat>list = itemCatMapper.selectByExample(example);
		//返回值list
		List resultList = new ArrayList<>();
		//向list中添加节点
		for(TbItemCat tbItemCat :list){
			if(tbItemCat.getIsParent()){
				CatNode catNode = new CatNode();
				if(parentId==0){
					catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				}else{
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/product/"+tbItemCat.getId()+".html");
				catNode.setItem(getCatList(tbItemCat.getId()));
				resultList.add(catNode);
			}else{
				resultList.add("/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName());
			}
		}
		
		return resultList;
	}

}
