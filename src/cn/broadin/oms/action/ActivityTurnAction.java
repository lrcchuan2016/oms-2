package cn.broadin.oms.action;

import it.sauronsoftware.base64.Base64;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.ActivityGiftBean;
import cn.broadin.oms.model.ActivityWinningRecordBean;
import cn.broadin.oms.outerInterface.ActivityTurntableAction;
import cn.broadin.oms.service.ActivityTurntableService;
import cn.broadin.oms.service.impl.AlbumPrintServiceImpl;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.Tools;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 转盘活动
 * @author huchanghuan
 *
 */
@Controller("activityTurnAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ActivityTurnAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> paramMap = new HashMap<String, String>();
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private ActivityGiftBean gift;
	private PaginationBean page;
	
	
	@Resource
	private ActivityTurntableService turntableService;
	@Resource
	private AlbumPrintServiceImpl albumPrintService;
	
	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
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
	
	public ActivityGiftBean getGift() {
		return gift;
	}

	public void setGift(ActivityGiftBean gift) {
		this.gift = gift;
	}

	/**
	 * 获取中奖记录（分未领取，待发奖，已发奖）
	 * @return
	 */
	public String getTurntablWinRecordList(){
		if(null != paramMap.get("isDeliver")){
			int isDeliver = Integer.valueOf(paramMap.get("isDeliver"));
			List<ConditionBean> conditions = new ArrayList<ConditionBean>();
			conditions.add(new ConditionBean("isDeliver", isDeliver, ConditionBean.EQ));
			conditions.add(new ConditionBean("giftId", "0", ConditionBean.NOT_EQ));  //剔除giftId为0的（即谢谢参与项）
			//添加模糊查询
			if(null != paramMap.get("keyWords")){
				String keyWords = "%"+paramMap.get("keyWords")+"%";
				Criterion[] cri = {Restrictions.like("clubNum", keyWords),
						Restrictions.like("account", keyWords),
						Restrictions.like("nickname", keyWords)};
				conditions.add(new ConditionBean(null, cri, ConditionBean.OR));
			}
			page = (PaginationBean) turntableService.findWinningRecord(conditions, null, false, "winningUtc", page);
			resultJson.put("list", page);
		}else resultJson.put("list", page);
		return Action.SUCCESS;
	}
	
	/**
	 * 保存发奖信息
	 * @return
	 */
	public String saveDeliverInfo(){
		if(null != paramMap.get("logistics") 
				&& null != paramMap.get("logistNumber")
				&& null != paramMap.get("id")){
			ActivityWinningRecordBean winRecord = turntableService.findWinningRecordById(paramMap.get("id"));
			if(null != winRecord){
				winRecord.setLogistics(paramMap.get("logistics"));
				winRecord.setLogistNumber(paramMap.get("logistNumber"));
				winRecord.setIsDeliver(2);   //更新状态已发货
				winRecord.setDeliverUtc(Tools.getNowUTC());
				if(null != turntableService.updateWinningRecord(winRecord)){
					resultJson.put("result", "00000000");
					return Action.SUCCESS;
				}
			}
		}
		resultJson.put("result", "00000008");   //参数错误
		return Action.SUCCESS;
	}

	/**
	 * 添加奖品
	 * @return
	 */
	public String addGift(){
		if(null != gift && null != paramMap.get("probability")){
			int probability = (int) (Double.valueOf(paramMap.get("probability"))*ActivityTurntableAction.lotteryBaseNum);
			gift.setCreateUtc(Tools.getNowUTC());
			gift.setId(Tools.getMD5AndUUID(16));
			gift.setModifyUtc(Tools.getNowUTC());
			gift.setProbability(probability);
			
			if(null != turntableService.addGift(gift)){
				resultJson.put("result", "00000000");
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 删除奖品
	 * @return
	 */
	public String delGift(){
		if(null != paramMap.get("id")){
			ActivityGiftBean giftBean = turntableService.findGiftById(paramMap.get("id"));
			if(null != giftBean){
				if(null != turntableService.delGift(giftBean)){
					resultJson.put("result", "00000000");
				}else resultJson.put("result", "00000001");
			}else resultJson.put("result", "00000007");
		}else resultJson.put("result", "00000008");
		return Action.SUCCESS;
	}
	
	/**
	 * 更新奖品
	 * @return
	 */
	public String updateGift(){
		if(null != gift
			&& null != paramMap.get("probability")){
			int probability = (int) (Double.valueOf(paramMap.get("probability"))*ActivityTurntableAction.lotteryBaseNum);
			gift.setModifyUtc(Tools.getNowUTC());
			gift.setProbability(probability);
			
			if(null != turntableService.updateGift(gift)){
				resultJson.put("result", "00000000");
			}else resultJson.put("result", "00000001");
		}else resultJson.put("result", "00000008");
		return Action.SUCCESS;
	}
	
	/**
	 * 查找奖品
	 * @return
	 */
	public String findGiftList(){
		if(null != paramMap.get("keyWords")){
			List<ConditionBean> conditions = new ArrayList<ConditionBean>();
			conditions.add(new ConditionBean("name", "%"+paramMap.get("keyWords")+"%", ConditionBean.LIKE));
			resultJson.put("list", turntableService.findGiftList(conditions, null, false, "createUtc", page));
		}else resultJson.put("list", turntableService.findGiftList(null,null,false,"createUtc",null));
		return Action.SUCCESS;
	}
	
	/**
	 * 修改订单的物流公司和物流单号
	 * @return
	 */
	public String editOrder(){
		if(paramMap.containsKey("logistNumber") 
				&& paramMap.containsKey("logistics")
				&& paramMap.containsKey("id")){
			ActivityWinningRecordBean awr = turntableService.findWinningRecordById(paramMap.get("id"));
			if(null != awr){
				awr.setLogistics(paramMap.get("logistics"));
				awr.setLogistNumber(paramMap.get("logistNumber"));
				if(null != turntableService.updateWinningRecord(awr)){
					resultJson.put("result", "00000000");
					return Action.SUCCESS;
				}
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 合成主题相册奖品
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String concatThemeAlbumPrize(){
		
		if(paramMap.containsKey("prizeId")){
			ActivityWinningRecordBean winningRecord = turntableService.findWinningRecordById(paramMap.get("prizeId"));
			if(null != winningRecord){
				Base64 base64 = new Base64();
				String content = replace(winningRecord.getContent());
				String xml = base64.decode(content,"UTF-8");
				XMLSerializer xmlSerializer = new XMLSerializer();
				JSONObject json = (JSONObject) xmlSerializer.read(xml);
				File f = albumPrintService.JsonTraverse(json, winningRecord.getReceiver());
				
				String abstractPath = "FileResource"+f.getPath().split("FileResource")[1];
				//压缩文件
				resultJson.put("result", "00000000");
				resultJson.put("url", abstractPath);
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	//日历下载
	@SuppressWarnings("unchecked")
	public String downloadCalendar(){
		if(!paramMap.isEmpty() && paramMap.containsKey("prizeId")){
			ActivityWinningRecordBean winningRecord = turntableService.findWinningRecordById(paramMap.get("prizeId"));
			if(null != winningRecord && null != winningRecord.getContent() && 0 !=winningRecord.getContent().trim().length()){
				String xml = Base64.decode(winningRecord.getContent(), "UTF-8");
				try {
					Document document = DocumentHelper.parseText(xml);
					List<Element> elements= document.selectNodes("//media");
					if(null != elements && !elements.isEmpty()){
						String contextPath = ServletActionContext.getServletContext().getRealPath("/");
						ZipOutputStream zip = null;
						File f = new File(contextPath+"FileResource"+File.separator+winningRecord.getName()+winningRecord.getReceiver()+".zip");
						zip = new ZipOutputStream(new FileOutputStream(f));
						
						for (int i=0;i<elements.size();i++) {
							String url = elements.get(i).attributeValue("url");
							if(url.startsWith("http:")){
								File file = new File(Tools.getUrlFile(url));
								File newFile = new File(file.getParent()+File.separator+(i+1)+"."+file.getName().split("\\.")[1]);
								file.renameTo(newFile);
								Tools.getZip(zip, newFile);
								newFile.delete();
							}else continue;
						}
						//关闭zip流
						zip.finish();
						zip.close();
						
						resultJson.put("result", "00000000");
						System.out.println("FileResource"+f.getPath().split("FileResource")[1]);
						resultJson.put("path", "FileResource"+f.getPath().split("FileResource")[1]);
						return Action.SUCCESS;
					}
				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 转盘活动中奖相册原图显示
	 * @return
	 */
	public String downPagePic(){
		if(null != paramMap){
			//int page = Integer.valueOf(paramMap.get("page"))-1;
			String id = paramMap.get("id");
			ActivityWinningRecordBean winningRecord = turntableService.findWinningRecordById(id);
			if(winningRecord != null){
				String xml = Base64.decode(winningRecord.getContent(), "UTF-8");
				XMLSerializer xmlSerializer = new XMLSerializer();
				JSONObject json = (JSONObject) xmlSerializer.read(xml);
				JSONArray jaPages = json.getJSONArray("pages");
				JSONArray array = new JSONArray();
				if(null != jaPages){
					int length = jaPages.size();
					JSONObject jsonObj = new JSONObject();
					for(int j=0;j<length;j++){
						JSONObject jo = (JSONObject) jaPages.get(j);
						String bgUrl = jo.getJSONObject("background").getString("@url");
						jsonObj.element("bgUrl", bgUrl);
						Object objFields = jo.get("fields");
						JSONArray picArr = new JSONArray();
						if(objFields instanceof net.sf.json.JSONObject){
							JSONObject joField = ((JSONObject)objFields).getJSONObject("field");
							JSONObject resource  = joField.getJSONObject("pic").getJSONObject("resource");
							if(resource.containsKey("@url")){
								String picUrl = resource.getString("@url");
								if(null != picUrl && !picUrl.isEmpty()){
									picArr.add(picUrl);
									jsonObj.element("picUrls", picArr);
								}
							}
						}else{
							JSONArray jaFields = (JSONArray)objFields;
							int len = jaFields.size();
							for(int i=0;i<len;i++){
								JSONObject joField = (JSONObject) jaFields.get(i);
								JSONObject resource  = joField.getJSONObject("pic").getJSONObject("resource");
								if(resource.containsKey("@url")){
									String picUrl = resource.getString("@url");
									if(null != picUrl && !picUrl.isEmpty()){
										picArr.add(picUrl);
									}
								}
							}
							jsonObj.element("picUrls", picArr);
						}
						
						//饰品
						Object objDecorates = jo.get("decorates");
						JSONArray decArr = new JSONArray();
						if(objDecorates instanceof net.sf.json.JSONObject){
							JSONObject joDecorate = ((JSONObject)objDecorates).getJSONObject("decorate");
							String decUrl = joDecorate.getJSONObject("resource").getString("@url");
							if(null != decUrl && !decUrl.isEmpty()){
								decArr.add(decUrl);
								jsonObj.element("decorateUrls", decArr);
							}
						}else{
							JSONArray joDecorates = (JSONArray)objDecorates;
							int len = joDecorates.size();
							for(int i=0;i<len;i++){
								JSONObject joDecorate = (JSONObject) joDecorates.get(i);
								String decUrl = joDecorate.getJSONObject("resource").getString("@url");
								if(null != decUrl && !decUrl.isEmpty()){
									decArr.add(decUrl);
								}
							}
							jsonObj.element("decorateUrls", decArr);
						}
						array.add(jsonObj);
					}
				}
				resultJson.put("result", "00000000");
				resultJson.put("urls", array);
				return Action.SUCCESS;
			}
			
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}

	//替换
	private String replace(String content) {
		String str = content.replace("\\", "");
		return str;
	}
}
