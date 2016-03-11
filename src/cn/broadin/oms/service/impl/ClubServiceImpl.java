package cn.broadin.oms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.ClubBean;
import cn.broadin.oms.model.ClubIconBean;
import cn.broadin.oms.model.Command;
import cn.broadin.oms.model.UserClubBean;
import cn.broadin.oms.service.ClubService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

@Service
public class ClubServiceImpl implements ClubService{

	@Resource
	private DAO dao;

	@Override
	public ClubBean selectClubById(String familyId) {
		
		return (ClubBean) dao.selectById(ClubBean.class, familyId);
	}

	@Override
	public Object updateUserClub(UserClubBean uc) {
		if(null!=dao.update(uc)){
			Command com = new Command("user_club:update",uc.getIdx()+"", null, null);
			dao.insert(com);
			return uc;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserClubBean> getuSet(Class<UserClubBean> class1,
			List<ConditionBean> clist) {
		
		return (List<UserClubBean>) dao.select(class1, clist, null, false, null, null);
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<UserClubBean> selectUserClub(Class<UserClubBean> class1,
			List<ConditionBean> clist, Integer i, boolean b, String string,
			PaginationBean p) {
		
		return (List<UserClubBean>) dao.select(class1, clist, i, b, string, p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean findClub(List<ConditionBean> clist, Integer i,boolean b,String str,
			PaginationBean p) {
		
		if(null != p){
			p = (PaginationBean) dao.select(ClubBean.class, clist, i, b, str, p);
		}else{
			List<ClubBean> clubs = (List<ClubBean>) dao.select(ClubBean.class, clist, i, b, str, p);
			if(null != clubs){
				p = new PaginationBean(0, clubs.size(), clubs.size(), clubs);
			}
		}
		return p;
	}

	@Override
	public ClubIconBean addClubIcon(ClubIconBean clubIcon) {
		if(null != dao.insert(clubIcon)){
			Command com = new Command("club_icon:add", clubIcon.getId(), null, null);
			dao.insert(com);
			return clubIcon;
		}
		return null;
	}

	@Override
	public boolean DelClubIcon(String id) {
		List<String> ids = new ArrayList<String>();
		ids.add(id);
		if(null != dao.delete(ClubIconBean.class, "id", ids)){
			Command com = new Command("club_icon:delete", id, null, null);
			dao.insert(com);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClubIconBean> IconList() {
		
		return (List<ClubIconBean>) dao.select(ClubIconBean.class, null, null, true, "createUtc", null);
	}

	@Override
	public ClubIconBean findClubIconById(String id) {
		
		return (ClubIconBean) dao.selectById(ClubIconBean.class, id);
	}

	@Override
	public ClubIconBean updateClubIcon(ClubIconBean newClubIcon) {
		
			if(null != dao.update(newClubIcon)){
				Command com = new Command("club_icon:update", newClubIcon.getId(), null, null);
				dao.insert(com);
				return newClubIcon;
			}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClubIconBean> findClubIcons(List<ConditionBean> conditions,
			Integer i, boolean b, String str, PaginationBean page) {
		
		return (List<ClubIconBean>) dao.select(ClubIconBean.class,conditions, i, b, str, page);
	}

	
}
