package com.teaShop.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.teaShop.bean.SysPage;


public interface SysPageService {

	/**
	 * 获取全部用户信息
	 * 
	 * @return
	 */
	public List<SysPage> blurryPage(SysPage page);

	public int blurryPageCount(SysPage page);

	/**
	 * 获取所有父级菜单
	 * 
	 * @return
	 */
	public List<SysPage> getAllPrePage();

	/**
	 * 添加
	 * 
	 * @param user
	 * @return
	 */
	public int addPage(SysPage page);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean removePage(int id);

	/**
	 * 更新
	 * 
	 * @param
	 * @return
	 */
	public JSONObject editPage(SysPage page);

	/**
	 * 根据id获取用户
	 * 
	 * @param id
	 * @return
	 */
	public SysPage getPageById(SysPage bean);

	/**
	 * 根据id获取用户
	 * 
	 * @param id
	 * @return
	 */
	public Integer getPageByName(SysPage bean);
	
	/**
	 * 根据pre_page_id获取同一等级下的最大的levelcode
	 * 
	 * @param pre_page_id
	 * @return
	 */
	public String getLevelCodeByPrePageId(SysPage bean);
	
	/**
	 * 根据page_id获取levelcode
	 * 
	 * @param pre_page_id
	 * @return
	 */
	public String getLevelCodeByPageId(SysPage bean);
	
	
	/**
	 * 根据page_id查询prepageid
	 * 
	 * @param page_id
	 * @return 
	 */
	public Integer getPrePageIdById(int pageId);
	/**
	 * 根据pre_page_id查询是否有子菜单在使用中
	 * 
	 * @param page_id
	 * @return 
	 */
	public Integer getUserPageByPrePageId(SysPage bean);
	
	/**
	 * 根据pre_page_id查询是否有子菜单在使用中
	 * 
	 * @param page_id
	 * @return 
	 */
	public Integer getRolePageByPrePageId(SysPage bean);
}
