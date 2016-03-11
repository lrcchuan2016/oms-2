package cn.broadin.oms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.ActivityGiftBean;
import cn.broadin.oms.model.ActivityLotteryRecordBean;
import cn.broadin.oms.model.ActivityWinningRecordBean;
import cn.broadin.oms.model.AdResourceBean;
import cn.broadin.oms.model.ClubBean;
import cn.broadin.oms.model.Command;
import cn.broadin.oms.model.UserBean;
import cn.broadin.oms.service.ActivityTurntableService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

/**
 * 转盘业务处理
 * @author Administrator
 *
 */
@Service
public class ActivityTurntableServiceImpl implements ActivityTurntableService{

	@Resource
	private DAO dao;
	
	/**
	 * 获得指定家庭成员的信心及抽奖次数
	 * @param string
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryMemberLotteryNum(String familyId) {
		String sql = "select a.*,alr.lottery_number from (select u.id,uc.user_nickname,u.portrait from user_club uc inner join user u where u.id = uc.user_id and club_id = "+familyId+") a left join activity_lottery_record alr on a.id = alr.user_id";
		List<Object[]> objs = (List<Object[]>) dao.executeSql(sql);
		return objs;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Object[]> queryUserInFamily(String familyId, String userId) {
//		String sql = "select * from user_club where club_id = '"+familyId+"' and user_id = '"+userId+"'";
//		List<Object[]> objs = (List<Object[]>) dao.executeSql(sql);
//		return objs;
//	}

	@SuppressWarnings("unchecked")
	public List<ActivityLotteryRecordBean> findLotteryRecord(
			List<ConditionBean> conditions, Integer i, boolean b, String s,
			PaginationBean page) {

		return (List<ActivityLotteryRecordBean>) dao.select(ActivityLotteryRecordBean.class, conditions, i, b, s, page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActivityLotteryRecordBean findLottery(AdResourceBean activity,
			ClubBean club, UserBean user) {
		
		List<ConditionBean> conditions = new ArrayList<ConditionBean>();
		conditions.add(new ConditionBean("user", user, ConditionBean.EQ));
		conditions.add(new ConditionBean("club", club, ConditionBean.EQ));
		conditions.add(new ConditionBean("activity", activity, ConditionBean.EQ));
		conditions.add(new ConditionBean("startUtc", activity.getStart_utc(), ConditionBean.GE));
		conditions.add(new ConditionBean("startUtc", activity.getEnd_utc(), ConditionBean.LT));
		
		List<ActivityLotteryRecordBean> lotteryRecords = (List<ActivityLotteryRecordBean>) dao.select(ActivityLotteryRecordBean.class, conditions, null, false, null, null);
		if(null != lotteryRecords && !lotteryRecords.isEmpty()) return lotteryRecords.get(0);
		return null;
	}

	@Override
	public ActivityLotteryRecordBean addLotteryRecord(
			ActivityLotteryRecordBean lottery) {
		
		if(null != dao.insert(lottery)){
			Command com = new Command("activity_lottery_record:add", lottery.getId(), null, null);
			dao.insert(com);
			return lottery;
		}
		return null;
	}

	@Override
	public ActivityLotteryRecordBean updateLotteryRecord(
			ActivityLotteryRecordBean lotteryRecord) {
		
		if(null != dao.update(lotteryRecord)){
			Command com = new Command("activity_lottery_record:update", lotteryRecord.getId(), null, null);
			dao.insert(com);
			return lotteryRecord;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityGiftBean> findAllGift() {
		
		return (List<ActivityGiftBean>) dao.select(ActivityGiftBean.class, null, null, false, null, null);
	}

	@Override
	public ActivityWinningRecordBean addWinningRecord(
			ActivityWinningRecordBean winningRecord) {
		if(null != dao.insert(winningRecord)){
			Command com = new Command("activity_winning_record:add", winningRecord.getId(), null, null);
			dao.insert(com);
			return winningRecord;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public PaginationBean findWinningRecord(
			List<ConditionBean> conditions, Integer i, boolean b,
			String column, PaginationBean page) {
		
		if(null != page){
			page = (PaginationBean) dao.select(ActivityWinningRecordBean.class, conditions, i, b, column, page);
		}else{
			List<ActivityWinningRecordBean> records = (List<ActivityWinningRecordBean>) dao.select(ActivityWinningRecordBean.class, conditions, i, b, column, page);
			if(null != records && !records.isEmpty()){
				page = new PaginationBean(0, 1, records.size(), records);
			}
		}
		return page;
	}

	@Override
	public ActivityGiftBean findGiftById(String id) {
		
		return (ActivityGiftBean) dao.selectById(ActivityGiftBean.class, id);
	}

	@Override
	public ActivityWinningRecordBean findWinningRecordById(String id) {
		
		return (ActivityWinningRecordBean) dao.selectById(ActivityWinningRecordBean.class, id);
	}

	@Override
	public ActivityWinningRecordBean updateWinningRecord(ActivityWinningRecordBean wr) {
		
		if(null != dao.update(wr)){
			Command com = new Command("activity_winning_record", wr.getId(), null, null);
			dao.insert(com);
			return wr;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityGiftBean> findGiftByConditions(
			List<ConditionBean> conditions, Integer i, boolean b, String str,
			PaginationBean page) {
		
		return (List<ActivityGiftBean>) dao.select(ActivityGiftBean.class, conditions, i, b, str, page);
	}

	@Override
	public ActivityGiftBean updateGift(ActivityGiftBean gift) {
		if(null != dao.update(gift)){
			Command com = new Command("activity_gift:update", gift.getId(), null, null);	
			dao.insert(com);
			return gift;
		}
		return null;
	}

	@Override
	public ActivityGiftBean addGift(ActivityGiftBean gift) {
		if(null != dao.insert(gift)){
			Command com = new Command("activity_gift:add", gift.getId(), null, null);
			dao.insert(com);
			return gift;
		}
		return null;
	}

	@Override
	public ActivityGiftBean delGift(ActivityGiftBean gift) {
		if(null != dao.delete(gift)){
			Command com = new Command("activity_gift:delete", gift.getId(), null, null);
			dao.insert(com);
			return gift;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean findGiftList(List<ConditionBean> conditions, Integer i,
			boolean b, String str, PaginationBean page) {
		if(null != page){
			page = (PaginationBean) dao.select(ActivityGiftBean.class, conditions, i, b, str, page);
		}else{
			List<ActivityGiftBean> gifts = (List<ActivityGiftBean>) dao.select(ActivityGiftBean.class, conditions, i, b, str, page);
			if(null != gifts && !gifts.isEmpty()){
				page = new PaginationBean(0, 1, gifts.size(), gifts);
			}
		}
		return page;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<ActivityLotteryRecordBean> queryTurnTableRecord(String familyId,
//			String userId) {
//		List<ConditionBean> conditions = new ArrayList<ConditionBean>();
//		conditions.add(new ConditionBean("clubId", familyId, ConditionBean.EQ));
//		conditions.add(new ConditionBean("userId", userId, ConditionBean.EQ));
//		
//		return (List<ActivityLotteryRecordBean>) dao.select(ActivityLotteryRecordBean.class, conditions, null, false, null, null);
//	}
}
