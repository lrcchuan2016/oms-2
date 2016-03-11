package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.ActivityGiftBean;
import cn.broadin.oms.model.ActivityLotteryRecordBean;
import cn.broadin.oms.model.ActivityWinningRecordBean;
import cn.broadin.oms.model.AdResourceBean;
import cn.broadin.oms.model.ClubBean;
import cn.broadin.oms.model.UserBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;


/**
 * 
 * @author 
 *
 */

public interface ActivityTurntableService {

	/**
	 * 获得指定家庭成员的信心及抽奖次数
	 * @param string
	 * @return
	 */
	public List<Object[]> queryMemberLotteryNum(String familyId);

	/**
	 * 查询用户是否在此家庭中
	 * @param string
	 * @param string2
	 * @return
	 */
//	public List<Object[]> queryUserInFamily(String string, String string2);

//	/**
//	 * 查询抽奖记录
//	 * @param familyId
//	 * @param userId
//	 * @return
//	 */
//	public List<ActivityLotteryRecordBean> queryTurnTableRecord(String familyId,
//			String userId);

	/**
	 * 查用户抽奖记录
	 * @param conditions
	 * @param object4 
	 * @param object3 
	 * @param b 
	 * @param object2 
	 * @param i 
	 * @return
	 */
	public List<ActivityLotteryRecordBean> findLotteryRecord(
			List<ConditionBean> conditions, Integer i, boolean b, String s , PaginationBean page);

	/**
	 * 
	 * @param activity
	 * @param club
	 * @param user
	 * @return
	 */
	public ActivityLotteryRecordBean findLottery(AdResourceBean activity,
			ClubBean club, UserBean user);

	/**
	 * 创建抽奖记录
	 * @param lottery
	 * @return
	 */
	public ActivityLotteryRecordBean addLotteryRecord(ActivityLotteryRecordBean lottery);

	/**
	 * 更新抽奖记录
	 * @param lotteryRecord
	 * @return
	 */
	public ActivityLotteryRecordBean updateLotteryRecord(ActivityLotteryRecordBean lotteryRecord);

	/**
	 * 查询所有奖品
	 * @return
	 */
	public List<ActivityGiftBean> findAllGift();

	/**
	 * 添加中奖记录
	 * @param winningRecord
	 * @return
	 */
	public ActivityWinningRecordBean addWinningRecord(ActivityWinningRecordBean winningRecord);

	/**
	 * 查询中奖记录
	 * @param object
	 * @param object2
	 * @param b
	 * @param object3
	 * @param object4
	 * @return
	 */
	public PaginationBean findWinningRecord(List<ConditionBean> conditions,
			Integer i, boolean b, String column, PaginationBean page);

	/**
	 * 通过ID查找奖品
	 * @param id
	 * @return
	 */
	public ActivityGiftBean findGiftById(String id);

	/**
	 * 通过ID查找中奖记录
	 * @param string
	 * @return
	 */
	public ActivityWinningRecordBean findWinningRecordById(String string);

	/**
	 * 更新中奖记录信息
	 * @param wr
	 * @return
	 */
	public ActivityWinningRecordBean updateWinningRecord(ActivityWinningRecordBean wr);

	/**
	 * 条件查找礼物列表
	 * @param conditions
	 * @param object
	 * @param b
	 * @param object2
	 * @param object3
	 * @return
	 */
	public List<ActivityGiftBean> findGiftByConditions(
			List<ConditionBean> conditions, Integer i, boolean b,
			String str, PaginationBean page);

	/**
	 * 更新奖品
	 * @param gift
	 */
	public ActivityGiftBean updateGift(ActivityGiftBean gift);

	/**
	 * 添加奖品
	 * @param gift
	 * @return
	 */
	public ActivityGiftBean addGift(ActivityGiftBean gift);

	/**
	 * 删除奖品
	 * @param gift
	 * @return
	 */
	public ActivityGiftBean delGift(ActivityGiftBean gift);

	
	public PaginationBean findGiftList(List<ConditionBean> conditions, Integer i, boolean b,
			String str, PaginationBean page);

	
		
}
