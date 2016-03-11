package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.ClubBean;
import cn.broadin.oms.model.ClubIconBean;
import cn.broadin.oms.model.UserClubBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

/**
 * 群(家庭)业务处理接口
 * @author huchanghuan
 *
 */
public interface ClubService {

	/**
	 * 根据ID查找指定的CLUB
	 * @param familyId
	 * @return
	 */
	public ClubBean selectClubById(String familyId);

	/**
	 * 查找指定条件的user_club
	 * @param class1 
	 * @param clist
	 * @param object 
	 * @param string 
	 * @param b 
	 * @param i 
	 * @return
	 */
	public List<UserClubBean> selectUserClub(Class<UserClubBean> class1, List<ConditionBean> clist, Integer i, boolean b, String string, PaginationBean p);

	/**
	 * 更新实体UserClubBean
	 * @param uc
	 * @return
	 */
	public Object updateUserClub(UserClubBean uc);

	/**
	 * 查询加入的群(包括自己创建的群)
	 * @param class1
	 * @param clist
	 * @return
	 */
	public List<UserClubBean> getuSet(Class<UserClubBean> class1,
			List<ConditionBean> clist);

	/**
	 * 条件查找家庭
	 * @param clist
	 * @param i
	 * @param p
	 * @return
	 */
	public PaginationBean findClub(List<ConditionBean> clist, Integer i,boolean b,String str,
			PaginationBean p);

	/**
	 * 保存家庭头像
	 * @param clubIcon
	 * @return
	 */
	public ClubIconBean addClubIcon(ClubIconBean clubIcon);

	/**
	 * 根据ID删除家庭头像
	 * @param id
	 * @return
	 */
	public boolean DelClubIcon(String id);

	/**
	 * 查找所有家庭头像图标
	 * @return
	 */
	public List<ClubIconBean> IconList();

	/**
	 * 根据ID查找头像
	 * @param string
	 * @return
	 */
	public ClubIconBean findClubIconById(String id);

	/**
	 * 更新头像
	 * @param newClubIcon
	 * @return
	 */
	public ClubIconBean updateClubIcon(ClubIconBean newClubIcon);

	/**
	 * 条件查找头像
	 * @param conditions
	 * @param i
	 * @param b
	 * @param object
	 * @param object2
	 * @return
	 */
	public List<ClubIconBean> findClubIcons(List<ConditionBean> conditions,
			Integer i, boolean b, String str, PaginationBean page);

	

}
