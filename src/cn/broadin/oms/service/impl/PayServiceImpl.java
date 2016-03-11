package cn.broadin.oms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.Command;
import cn.broadin.oms.model.ProductBean;
import cn.broadin.oms.model.TransactionBean;
import cn.broadin.oms.service.PayService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

@Service
public class PayServiceImpl implements PayService {
	
	@Resource
	private DAO dao;

	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean productList(Class<ProductBean> class1,
			List<ConditionBean> clist, Integer i, boolean b, String str,
			PaginationBean page) {
		if(null != page)
			page = (PaginationBean) dao.select(class1, clist, i, b, str, page);
		else{
			List<ProductBean> products = (List<ProductBean>) dao.select(class1, clist, i, b, str, page);
			page = new PaginationBean(0, 1, products.size(), products);
		}
		return page;
	}

	@Override
	public ProductBean addProduct(ProductBean product) {
		if(null != dao.insert(product)){
			Command com = new Command("product:add", product.getId(), null, null);
			dao.insert(com);
			return product;
		}
		return null;
	}

	@Override
	public ProductBean findProductById(String productId) {
		
		return (ProductBean) dao.selectById(ProductBean.class, productId);
	}

	@Override
	public ProductBean updateProduct(ProductBean product) {
		if(null != dao.update(product)){
			Command com = new Command("product:update", product.getId(), null, null);
			dao.insert(com);
			return product;
		}
		return null;
	}

	@Override
	public Object delProductById(String id) {
		List<String> ids = new ArrayList<String>();
		ids.add(id);
		Object o = dao.delete(ProductBean.class, "id", ids);
		if(null != o){
			Command com = new Command("product:delete", id, null, null);
			dao.insert(com);
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean orderList(Class<TransactionBean> class1,
			List<ConditionBean> conditions, Integer i, boolean b, String str,
			PaginationBean pagination) {
		if(null != pagination){
			pagination = (PaginationBean) dao.select(class1, conditions, i, b, str, pagination);
		}else{
			List<TransactionBean> transactions = (List<TransactionBean>) dao.select(class1, conditions, i, b, str, pagination);
			if(null != transactions && 0 != transactions.size()){
				pagination = new PaginationBean(0, 1, transactions.size(), transactions);
			}
		}
		return pagination;
	}

	@Override
	public TransactionBean findOrderById(String id) {
		
		return (TransactionBean) dao.selectById(TransactionBean.class, id);
	}

	@Override
	public TransactionBean updateOrder(TransactionBean order) {
		
		if(null != dao.update(order)){
			Command com = new Command("transaction:update", order.getId(), null, null);
			dao.insert(com);
			return order;
		}
		return null;
	}

	@Override
	public Object executeSql(String sql) {
		
		return dao.executeSql(sql);
	}
	
	
}
