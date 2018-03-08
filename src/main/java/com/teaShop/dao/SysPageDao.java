package com.teaShop.dao;

import com.teaShop.bean.SysPage;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SysPageDao {

	/**
	 * 根据用户名获得用户信息
	 * 
	 * @param phone
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
	 * 获取所有可用菜单
	 * 
	 * @return
	 */
	public List<SysPage> getAllPage();

	/**
	 * 获取可以绑定给用户的菜单
	 * 
	 * @return
	 */
	public List<SysPage> getUserPage();

	/**
	 * 根据 ids 查询菜单信息
	 * 
	 * @param ids
	 * @return
	 */
	public List<SysPage> getUserPageByIds(List<Integer> ids);

	/**
	 * 通过levelCode 获取菜单信息
	 * @param levelCode
	 * @return
	 */
	public List<SysPage> getPageByLevelcode(List<String> levelCode);

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	public int addPage(SysPage page);

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	public int removePage(int id);

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @return
	 */
	public int editPage(SysPage page);

	/**
	 * 根据id获取
	 * 
	 * @param id
	 * @return
	 */
	public SysPage getPageById(SysPage bean);

	/**
	 * 获取同一级目录下是否有相同的名称
	 * 
	 * @param bean
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
