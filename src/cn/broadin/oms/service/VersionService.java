package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.ScreenshotBean;
import cn.broadin.oms.model.VersionSoftBean;
import cn.broadin.oms.model.VersionTerminalBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

public interface VersionService {

	/**
	 * 新增终端软件
	 * @param tSoft
	 * @return
	 */
	public ServiceResultBean terminalAdd(VersionTerminalBean tSoft);

	/**
	 * 根据ID查找实体
	 * @param obj
	 * @param id
	 * @return
	 */
	public Object findById(String s, String id);

	/**
	 * 条件查找分页
	 * @param class1
	 * @param object
	 * @param object2
	 * @param b
	 * @param string
	 * @param pagination
	 * @return
	 */
	public PaginationBean findPage(Class<?> class1,
			List<ConditionBean> clist, Integer i, boolean b, String string,
			PaginationBean pagination);

	/**
	 * 更新软件终端实体
	 * @param tSoft
	 * @return
	 */
	public ServiceResultBean terminalUpdate(VersionTerminalBean tSoft);

	/**
	 * 删除
	 * @param obj
	 * @return
	 */
	public ServiceResultBean Del(Object obj);

	/**
	 * 新增软件版本
	 * @param softVersion
	 * @return
	 */
	public ServiceResultBean softVersionAdd(VersionSoftBean softVersion);

	/**
	 * 批量新增截图
	 * @param screenshots
	 * @return
	 */
	public ServiceResultBean screenShotAdd(List<ScreenshotBean> screenshots);

	/**
	 * 更新软件版本信息
	 * @param softVersion
	 * @return
	 */
	public ServiceResultBean softVersionUpdate(VersionSoftBean softVersion);

	/**
	 * 获取版本
	 * @param class1
	 * @param object
	 * @param i
	 * @param b
	 * @param string
	 * @param pagination
	 * @return
	 */
	public List<VersionSoftBean> pageList(Class<VersionSoftBean> class1,
			List<ConditionBean> clist, int i, boolean b, String string,
			PaginationBean pagination);

}
