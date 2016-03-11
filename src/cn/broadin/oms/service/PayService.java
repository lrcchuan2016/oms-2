package cn.broadin.oms.service;

import java.util.List;

import cn.broadin.oms.model.ProductBean;
import cn.broadin.oms.model.TransactionBean;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

public interface PayService {

	/**
	 * 条件查找商品并分页
	 * @param class1
	 * @param clist
	 * @param i
	 * @param b
	 * @param str
	 * @param page
	 * @return
	 */
	public PaginationBean productList(Class<ProductBean> class1, List<ConditionBean> clist,
			Integer i, boolean b, String str, PaginationBean page);

	/**
	 * 添加商品
	 * @param product
	 * @return
	 */
	public ProductBean addProduct(ProductBean product);

	/**
	 * 根据ID查找商品
	 * @param productId
	 * @return
	 */
	public ProductBean findProductById(String productId);

	/**
	 * 更新商品
	 * @param product
	 * @return
	 */
	public ProductBean updateProduct(ProductBean product);

	public Object delProductById(String id);

	/**
	 * 条件查找分页
	 * @param class1
	 * @param object
	 * @param object2
	 * @param b
	 * @param object3
	 * @param object4
	 * @return
	 */
	public PaginationBean orderList(Class<TransactionBean> class1, List<ConditionBean> conditions,
			Integer i, boolean b, String str, PaginationBean pagination);

	/**
	 * 根据id查找订单信息
	 * @param id
	 * @return
	 */
	public TransactionBean findOrderById(String id);

	/**
	 * 更新订单信息
	 * @param order
	 * @return
	 */
	public TransactionBean updateOrder(TransactionBean order);

	/**
	 * 
	 * @param hql
	 * @return
	 */
	public Object executeSql(String hql);

}
