package cn.broadin.oms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import cn.broadin.oms.model.RecycleBean;
import cn.broadin.oms.service.RecycleService;
import cn.broadin.oms.util.ConditionBean;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller("recycleAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RecycleAction extends ActionSupport{
	private static final long serialVersionUID = 6247993221301612444L;
	private int recycleType = -1;
	private List<RecycleBean> recycleList;
	private String tipMsg;
	private String input;
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	
	@Resource
	private RecycleService recycleService;
	
	public String getTipMsg() {
		return tipMsg;
	}
	
	public Map<String, Object> getResultJson() {
		return resultJson;
	}

	public String getInput() {
		return input;
	}
	
	public int getRecycleType() {
		return recycleType;
	}

	public void setRecycleType(int recycleType) {
		this.recycleType = recycleType;
	}
	
	public List<RecycleBean> getRecycleList() {
		return recycleList;
	}

	public void setRecycleList(List<RecycleBean> recycleList) {
		this.recycleList = recycleList;
	}

	/**
	 * 查找回收站数据
	 * @return
	 * @throws Exception
	 */
	public String findRecycleList() throws Exception{
		List<RecycleBean> rbs = new ArrayList<RecycleBean>();
		if(recycleType!=-1){
			if(recycleType!=2){
				List<ConditionBean> conditions = new ArrayList<ConditionBean>();
				conditions.add(new ConditionBean("type",recycleType,ConditionBean.EQ));
				rbs = recycleService.findAll(conditions);
			}else{
				rbs = recycleService.findAll(null);
			}
		}
		resultJson.put("list", rbs);
		return Action.SUCCESS;
	}
	
	/**
	 * 删除实体recycle，恢复其它
	 * @return
	 * @throws Exception
	 */
	public String recover() throws Exception{
		if(recycleList !=null && recycleList.size() != 0){
			RecycleBean bean = recycleList.get(0);
			List<Integer> ids = new ArrayList<Integer>();
			ids.add(bean.getId());
			if(recycleService.recover(ids, bean.getType(),bean.getRefId())){
				this.resultJson.put("result", "00000000");
				this.resultJson.put("tip", "恢复成功");
			}else{
				this.resultJson.put("result", "00000001");
				this.resultJson.put("tip", "恢复失败");
			}
		}else{
			this.resultJson.put("result", "00000001");
			this.resultJson.put("tip", "恢复失败，未能传入有效信息");
		}
		
		return Action.SUCCESS;
	}
	
	/**
	 * 永久删除
	 * @return
	 */
	public String del(){
		if(null != recycleList && 0 != recycleList.size()){
			RecycleBean recycle = recycleList.get(0);
			if(null != recycleService.del(recycle)){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "删除成功！");
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "删除失败！");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip", "删除失败，未能传入有效的参数");
		}
		return Action.SUCCESS;
	}
}
