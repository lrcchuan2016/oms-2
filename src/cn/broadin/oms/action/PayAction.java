package cn.broadin.oms.action;

import it.sauronsoftware.base64.Base64;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.model.ProductBean;
import cn.broadin.oms.model.TransactionBean;
import cn.broadin.oms.model.UserBean;
import cn.broadin.oms.service.PayService;
import cn.broadin.oms.service.UserService;
import cn.broadin.oms.service.impl.expressQueryImpl;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.Tools;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 支付控制器
 * @author huchanghuan
 *
 */
@Controller("payAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PayAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private PayService payService;
	@Resource
	private UserService userService;
	@Resource
	private expressQueryImpl expressQuery;
	
	private Map<String, String> paramMap = new HashMap<String, String>();
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private List<ConditionBean> conditions = new ArrayList<ConditionBean>();
	private String success;
	private PaginationBean page;
	private ProductBean product;
	private String startSaleUtc;
	private String endSaleUtc;
	private double price;
	
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
	public ProductBean getProduct() {
		return product;
	}
	public void setProduct(ProductBean product) {
		this.product = product;
	}
	public String getStartSaleUtc() {
		return startSaleUtc;
	}
	public void setStartSaleUtc(String startSaleUtc) {
		this.startSaleUtc = startSaleUtc;
	}
	public String getEndSaleUtc() {
		return endSaleUtc;
	}
	public void setEndSaleUtc(String endSaleUtc) {
		this.endSaleUtc = endSaleUtc;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	
	/**
	 * 商品列表
	 * @return
	 */
	public String productList(){
		if(null != paramMap.get("keyWords")){
			String keyWords = paramMap.get("keyWords");
			conditions.add(new ConditionBean("name","%"+keyWords+"%",ConditionBean.LIKE));
			resultJson.put("list", payService.productList(ProductBean.class, conditions, null, false, "startSaleUtc", page));
			
		}else resultJson.put("list", payService.productList(ProductBean.class,null,null,false,"startSaleUtc",page));
		return Action.SUCCESS;
	}

	/**
	 * 添加商品
	 * @return
	 */
	public String addProduct(){
		if(null != product && null != startSaleUtc && null != endSaleUtc && 0 != price){
			product.setId(Tools.getMD5AndUUID(32));
			product.setCategoryId(0);
			product.setPrice((int)Math.round(price*100));  //价格数据库是以分为单位
			product.setThumbnail("");
			product.setDiscount(100-product.getDiscount());
			product.setStartSaleUtc(Tools.string2utc(startSaleUtc.replace("T", " "), 8, "yyyy-MM-dd HH:mm"));
			product.setEndSaleUtc(Tools.string2utc(endSaleUtc.replace("T", " "), 8, "yyyy-MM-dd HH:mm"));
			if(null != payService.addProduct(product)){
				resultJson.put("result", "00000000");
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 更新商品
	 * @return
	 */
	public String updateProduct(){
		if(null != product && null != startSaleUtc && null != endSaleUtc && 0 != price){
			product.setPrice((int)Math.round(price*100));  //价格数据库是以分为单位
			product.setDiscount(100-product.getDiscount());
			product.setStartSaleUtc(Tools.string2utc(startSaleUtc.replace("T", " "), 8, "yyyy-MM-dd HH:mm"));
			product.setEndSaleUtc(Tools.string2utc(endSaleUtc.replace("T", " "), 8, "yyyy-MM-dd HH:mm"));
			if(null != payService.updateProduct(product)){
				resultJson.put("result", "00000000");
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	/**
	 * 查找商品
	 * @return
	 */
	public String findProduct(){
		if(null != paramMap && null != paramMap.get("id")){
			String productId = paramMap.get("id");
			product = payService.findProductById(productId);
			if(null != product){
				resultJson.put("result", "00000000");
				resultJson.put("product", product);
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 下架商品
	 * @return
	 */
	public String delProduct(){
		if(null != paramMap && null != paramMap.get("id")){
			String id = paramMap.get("id");
			if(null != payService.delProductById(id)){
				resultJson.put("result", "00000000");
			}else resultJson.put("result", "00000001");
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 订单查询列表(所有订单，支持模糊查询)
	 * @return
	 */
	public String orderList(){
		if(0 != paramMap.keySet().size()){
			String keyWords = paramMap.get("keyWords");
			String orderBy = paramMap.get("orderBy");
			if(null != keyWords && 0 != keyWords.length()){
				conditions.add(new ConditionBean("id", "%"+keyWords+"%", ConditionBean.LIKE));
				conditions.add(new ConditionBean("recvPhone", "%"+keyWords+"%", ConditionBean.LIKE));
			}
			page = payService.orderList(TransactionBean.class, conditions, 1, false, orderBy, page);
			resultJson.put("list", page);
		}else resultJson.put("list", null);
		return Action.SUCCESS;
	}
	
	/**
	 * 条件查找订单列表（不支持额外关键字搜索）
	 * @return
	 */
	public String conditionOrderList(){
		if(0 != paramMap.keySet().size()){
			String orderBy = paramMap.get("orderBy");
			String column = paramMap.get("column");
			int value = Integer.valueOf(paramMap.get("value"));
			conditions.add(new ConditionBean(column, value, ConditionBean.EQ));
			page = payService.orderList(TransactionBean.class, conditions, 1, false, orderBy, page);
			resultJson.put("list", page);
		}else resultJson.put("list", null);
		return Action.SUCCESS;
	}
	
	/**
	 * 根据ID获取订单信息
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String getOrderInfo(){
		if(null != paramMap.get("id")){
			String id = paramMap.get("id");
			TransactionBean order = payService.findOrderById(id);
			if(null != order){
				String coverUrl = null;
				try {
					Base64 base64 = new Base64();
					String xml = base64.decode(order.getContent(), "UTF-8");
					Document document = DocumentHelper.parseText(xml);
					Element element = (Element) document.selectNodes("//cover").get(0);
					coverUrl = element.getText();
				} catch (RuntimeException e) {
					e.printStackTrace();
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				resultJson.put("result", "00000000");
				resultJson.put("order", order);
				resultJson.put("coverUrl", coverUrl);
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 根据ID订单发货
	 * @return
	 */
	public String devliverOrder(){
		if(null != paramMap.get("id") 
				&& null != paramMap.get("orderId") 
				&& null != paramMap.get("logistics")){
			String id = paramMap.get("id");
			String orderId = paramMap.get("orderId");
			String logistics = paramMap.get("logistics");
			TransactionBean order = payService.findOrderById(id);
			if(null != order){
				order.setLogstNumber(orderId);
				order.setLogistics(logistics);
				order.setDeliverState(2);  //更改状态为2表已发货
				order.setDeliverUtc(Tools.getNowUTC());
				if(null != payService.updateOrder(order)){
					resultJson.put("result", "00000000");
					return Action.SUCCESS;
				}
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 获取订单发货信息
	 * @return
	 */
	public String getOrderTrackInfo(){
		if(null != paramMap.get("type") && null != paramMap.get("postid") ){
			String type = paramMap.get("type");
			String postid = paramMap.get("postid");
			try {
				String result = expressQuery.getOrderTracesByJson(type, postid);
				JSONObject jo = JSONObject.fromObject(result);
				resultJson.put("result", "00000000");
				resultJson.put("data", jo);
				return Action.SUCCESS;
			} catch (Exception e) {
				resultJson.put("result", "00000002");
				resultJson.put("tip", "execetion");
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 更改发货信息
	 * @return
	 */
	public String editDevierInfo(){
		if(null != paramMap.get("id") 
				&& null != paramMap.get("orderId") 
				&& null != paramMap.get("logistics")){
			String id = paramMap.get("id");
			String orderId = paramMap.get("orderId");
			String logistics = paramMap.get("logistics");
			TransactionBean order = payService.findOrderById(id);
			if(null != order){
				order.setLogstNumber(orderId);
				order.setLogistics(logistics);
				if(null != payService.updateOrder(order)){
					resultJson.put("result", "00000000");
					resultJson.put("order", order);
					return Action.SUCCESS;
				}
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 获取待发货 数量
	 * @return
	 */
	public String getDeliverNum(){
		String sql = "select count(deliver_state) from transaction where deliver_state = 1";
		Object deliverNum = payService.executeSql(sql);
		if(null != deliverNum){
			resultJson.put("result", "00000000");
			resultJson.put("deliverNum", deliverNum);
		}else resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	/**
	 * 根据id获得用户信息
	 * @return
	 */
	public String getUserInfo(){
		if(null != paramMap.get("id")){
			String id = paramMap.get("id");
			UserBean user = userService.selectById(UserBean.class, id);
			if(null != user){
				resultJson.put("result", "00000000");
				resultJson.put("user", user);
				return Action.SUCCESS;
			}
		}
		resultJson.put("result", "00000001");
		return Action.SUCCESS;
	}
	
	
}
