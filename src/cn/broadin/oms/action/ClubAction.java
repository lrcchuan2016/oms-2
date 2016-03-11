package cn.broadin.oms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.ClubIconBean;
import cn.broadin.oms.model.UserBean;
import cn.broadin.oms.service.ClubService;
import cn.broadin.oms.service.UserService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.Tools;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller("clubAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClubAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> param = new HashMap<String, String>();
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private PaginationBean page;
	
	@Resource
	private ClubService clubService;
	@Resource
	private UserService userService;
	
	public Map<String, String> getParam() {
		return param;
	}
	public void setParam(Map<String, String> param) {
		this.param = param;
	}
	public Map<String, Object> getResultJson() {
		return resultJson;
	}
	public void setResultJson(Map<String, Object> resultJson) {
		this.resultJson = resultJson;
	}
	public PaginationBean getPage() {
		return page;
	}
	public void setPage(PaginationBean page) {
		this.page = page;
	}
	/**
	 * 查找所有家庭头像
	 * @return
	 */
	public String ClubIconList(){
		List<ClubIconBean> clubIcons = clubService.IconList();
		resultJson.put("result", "00000000");
		resultJson.put("clubIcons", clubIcons);
		return Action.SUCCESS;
	}
	
	/**
	 * 保存家庭头像
	 * @return
	 */
	public String AddClubIcon(){
		String url = param.get("url");
		if(null != url){
			ClubIconBean clubIcon = new ClubIconBean(Tools.getMD5AndUUID(16),url,0,Tools.getNowUTC());
			if(null != clubService.addClubIcon(clubIcon)){
				resultJson.put("rewsult", "00000000");
				resultJson.put("clubIcon", clubIcon);
			}else resultJson.put("result", "00000001");
		}else resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 删除家庭头像
	 * @return
	 */
	public String DelIcon(){
		String id = param.get("id");
		if(null != id){
			if(clubService.DelClubIcon(id))
			resultJson.put("result", "00000000");
			else resultJson.put("result", "00000001");
		}else resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}

	/**
	 *家庭列表显示
	 * @return
	 */
	public String clubList(){
		if(null != page){
			if(null != param.get("keyWord")){
				String keyWord = "%"+param.get("keyWord")+"%";
				//模糊查询（家庭号或创建人手机号或家庭呢称）
				List<ConditionBean> conditions = new ArrayList<ConditionBean>();
				conditions.add(new ConditionBean("account", keyWord, ConditionBean.LIKE));
				List<UserBean> users = userService.selectByAccount(UserBean.class, conditions, null, false, "register_utc", null);
				//清除条件
				conditions.clear();
				conditions.add(new ConditionBean("numId", keyWord, ConditionBean.LIKE));
				conditions.add(new ConditionBean("nickname", keyWord, ConditionBean.LIKE));
				if(null != users && !users.isEmpty()){
					for (UserBean user : users) {
						conditions.add(new ConditionBean("creater", user, ConditionBean.EQ));
					}
				}
				page = clubService.findClub(conditions, 1, false, "create_utc", page);
				resultJson.put("list", page);
			}else resultJson.put("list", clubService.findClub(null, null, false, "create_utc", page));
		}else resultJson.put("list", null);
		return Action.SUCCESS;
	}
	
	/**
	 * 家庭头像排序
	 * @return
	 */
	public String sortIcon(){
		//oldId (原位置的id)  newId（拖动的Id）
		if(param.containsKey("ids")
				&& param.containsKey("flag")){
			String[] ids = param.get("ids").split(",");
			String flag = param.get("flag");
			
			Map<String, ClubIconBean> iconMap = new HashMap<String, ClubIconBean>();
			List<ConditionBean> conditions = new ArrayList<ConditionBean>();
			for (String id : ids) {
				conditions.add(new ConditionBean("id", id, ConditionBean.EQ));
			}
			List<ClubIconBean> icons = clubService.findClubIcons(conditions,1,false,null,null);
			if(null != icons && !icons.isEmpty()){
				for (ClubIconBean clubIconBean : icons) {
					iconMap.put(clubIconBean.getId(), clubIconBean);
				}
			}
			
			//改变
			long newElementUtc = 0;
			int len = ids.length;
			if("0".equals(flag)){
				newElementUtc = iconMap.get(ids[0]).getCreateUtc();
				for (int i = 0; i < len; i++) {
					ClubIconBean icon = iconMap.get(ids[i]);
					if((i+1)<len){
						icon.setCreateUtc(iconMap.get(ids[i+1]).getCreateUtc());
					}else icon.setCreateUtc(newElementUtc);
					
					if(null == clubService.updateClubIcon(icon)){
						resultJson.put("result", "00000001");
						return Action.SUCCESS;
					}
				}
			}else if("1".equals(flag)){
				newElementUtc = iconMap.get(ids[len-1]).getCreateUtc();
				for (int i = len-1; i >=0; i--) {
					ClubIconBean icon = iconMap.get(ids[i]);
					if((i-1)>=0){
						icon.setCreateUtc(iconMap.get(ids[i-1]).getCreateUtc());
					}else icon.setCreateUtc(newElementUtc);
					
					if(null == clubService.updateClubIcon(icon)){
						resultJson.put("result", "00000001");
						return Action.SUCCESS;
					}
				}
			}else{
				resultJson.put("result", "00000002");
				resultJson.put("tip", "error param");
				return Action.SUCCESS;
			}
			resultJson.put("result", "00000000");
			return Action.SUCCESS;
			
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
}
