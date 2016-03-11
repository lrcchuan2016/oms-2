package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.TemplateBean;
import cn.broadin.oms.model.TemplateGroupBean;
import cn.broadin.oms.model.TemplateMediaBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

/**
 * 模版管理接口类
 * @author XieJun
 *
 */
public interface TemplateService {
	
	/**
	 * 条件查找模版列表
	 * @param arg0
	 * 				条件集合
	 * @param arg1
	 * 				分页对象
	 * @return
	 */
	public PaginationBean findTPage(List<ConditionBean> arg0,Integer arg1,boolean arg2,String arg3,PaginationBean arg4);
	
	/**
	 * 条件查找模版分组列表
	 * @param arg0
	 * 				条件集合
	 * @param arg1
	 * 				分页对象
	 * @return
	 */
	public PaginationBean findTGPage(List<ConditionBean> arg0,PaginationBean arg1);
	
	/**
	 * 通过ID查找模版类
	 * @param arg0
	 * 				主键ID
	 * @return
	 */
	public TemplateBean findTemplateById(String arg0);
	
	/**
	 * 通过ID查找模版分组类
	 * @param arg0
	 * 				主键ID
	 * @return
	 */
	public TemplateGroupBean findTemplateGroupById(String arg0);
	
	/**
	 * 模版添加
	 * @param arg0
	 * 				模版对象
	 * @return
	 */
	public ServiceResultBean TemplateAdd(TemplateBean arg0);
	
	/**
	 * 模版分组添加
	 * @param arg0
	 * 				模版分组对象
	 * @return
	 */
	public ServiceResultBean TemplateGroupAdd(TemplateGroupBean arg0);
	
	/**
	 * 模版编辑
	 * @param arg0
	 * 				模版对象
	 * @return
	 */
	public ServiceResultBean TemplateEdit(TemplateBean arg0);
	
	/**
	 * 模版分组编辑
	 * @param arg0
	 * 	模版分组对象
	 * @return
	 */
	public ServiceResultBean TemplateGroupEdit(TemplateGroupBean arg0);
	
	/**
	 * 删除模版
	 * @param arg0
	 * 				主键ID集合
	 * @param arg1
	 * 				图片对应key
	 * @return
	 */
	public ServiceResultBean TemplateDel(List<String> arg0, String arg1);
	
	/**
	 * 删除模版分组
	 * @param arg0
	 * 				主键ID集合
	 * @param arg1
	 * 				图片对应key
	 * @return
	 */
	public ServiceResultBean TemplateGroupDel(List<String> arg0, String arg1);
	
//	/**
//	 * 更改对应ID模版的主键值
//	 * @param arg0
//	 * 				原主键值
//	 * @param arg1
//	 * 				修改主键值
//	 * @return
//	 */
//	public ServiceResultBean alterTemplatePrimaryKey(String arg0, String arg1);
//	
//	/**
//	 * 更改模版分组的主键值
//	 * @param arg0
//	 * 				原主键值
//	 * @param arg1
//	 * 				修改主键值
//	 * @return
//	 */
//	public ServiceResultBean alterTemplateGroupPrimaryKey(String arg0, String arg1);

	/**
	 * 新增实体TemplateMediaBean
	 * @param tMedia
	 * @return
	 */
	public ServiceResultBean TemplateMediaAdd(TemplateMediaBean tMedia);

	/**
	 * 根据ID删除实体
	 * @param tMedia
	 * @param id
	 * @param list
	 * @return
	 */
	public ServiceResultBean delTemplateMedia(Class<TemplateMediaBean> tMedia,
			String id, List<String> list);

	/**
	 * 根据ID查找实体
	 * @param templateMediaId
	 * @return
	 */
	public TemplateMediaBean findTemplateMedia(String templateMediaId);

	/**
	 * 条件查找template_media
	 * @param clist
	 * @param object
	 * @return
	 */
	public List<TemplateMediaBean> findTMedia(List<ConditionBean> clist,
			PaginationBean p);

	/**
	 * 更新templateMedia
	 * @param tm
	 * @return
	 */
	public TemplateMediaBean TemplateMediaUpdate(TemplateMediaBean tm);

	/**
	 * 更新模板的信息
	 * @param newTemplate
	 * @return
	 */
	public TemplateBean TemplateUpdate(TemplateBean newTemplate);

}
