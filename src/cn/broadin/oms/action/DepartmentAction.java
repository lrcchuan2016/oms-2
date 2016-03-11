package cn.broadin.oms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.DepartmentBean;
import cn.broadin.oms.service.DepartmentService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller("departmentAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DepartmentAction extends ActionSupport{
	private static final long serialVersionUID = 6255231334266592612L;
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private List<DepartmentBean> departList;
	private Map<String, String> paramMap = new HashMap<String, String>();
	private String tipMsg;
	@Resource
	private DepartmentService departmentService;
	
	public String getTipMsg() {
		return tipMsg;
	}
	
	public Map<String, Object> getResultJson() {
		return resultJson;
	}
	
	public List<DepartmentBean> getDepartList() {
		return departList;
	}

	public void setDepartList(List<DepartmentBean> departList) {
		this.departList = departList;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	
	/**
	 * 查找所有部门
	 * @return
	 * @throws Exception
	 */
	public String findDeparts() throws Exception{
		resultJson.put("list", departmentService.findAll());
		return Action.SUCCESS;
	}
	
	/**
	 * 添加部门
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		if(departList!=null && departList.size()!=0){
			DepartmentBean bean = departList.get(0);
			if(departmentService.add(bean)){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "添加部门成功.");
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "添加部门失败.");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","添加部门失败，未能传入有效信息");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除部门
	 */
	public String del() throws Exception{
		if(departList!=null && departList.size()!=0){
			List<Integer> ids = new ArrayList<Integer>();
			for(DepartmentBean bean : departList) ids.add(bean.getId());
			if(departmentService.del(ids)){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "删除成功.");
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "删除失败.");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","删除失败，未能传入有效信息");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 编辑部门
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		if(departList!=null && departList.size()!=0){
			DepartmentBean bean = departList.get(0);
			if(departmentService.edit(bean)){
				resultJson.put("result", "00000000");
				resultJson.put("tip", "编辑部门成功.");
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("tip", "编辑部门失败.");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("tip","编辑部门失败，未能传入有效信息");
		}
		return Action.SUCCESS;
	} 
}
