package cn.broadin.oms.dao.impl;

/**
 * 公共数据库操作实现类
 * 
 * @author zhoudeming
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;

@SuppressWarnings("deprecation")
@Repository("dao")
public class DAOImpl implements DAO {
	
	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public Object insert(Object arg0) {
		Session session = sessionFactory.getCurrentSession();
		try {
			return session.save(arg0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Object insert(List<ConditionBean> arg0, Object arg1) {
		Session session = sessionFactory.getCurrentSession();
		try {
			List<?> result = (List<?>) select(arg1.getClass(), arg0, 0, null, null, null);
			if (result.size() == 0) { return session.save(arg1); }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Object insert(List<?> arg0) {
		Session session = sessionFactory.getCurrentSession();
		try {
			List<Object> ids = new ArrayList<Object>();
			for (int i = 0; i < arg0.size(); i++) {
				ids.add(session.save(arg0.get(i)));
				if (i % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			session.flush();
			session.clear();
			return ids;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Object update(Object arg0) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.update(arg0);
			return arg0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Object update(List<ConditionBean> arg0, Object arg1) {
		try {
			Session session = sessionFactory.getCurrentSession();
			List<?> result = (List<?>) select(arg1.getClass(), arg0, 0, null, null, null);
			if (result.size() == 0) {
				session.update(arg1);
				return arg1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Object update(Class<?> arg0, Map<String, ?> arg1, String arg2, Object arg3) {
		Session session = sessionFactory.getCurrentSession();
		try {
			String setStr = "";
			for (Entry<String, ?> entry : arg1.entrySet()) {
				if (entry.getValue() instanceof String) {
					setStr += "," + entry.getKey() + "='" + entry.getValue() + "'";
				} else {
					setStr += "," + entry.getKey() + "=" + entry.getValue();
				}
			}
			return session.createQuery("update " + arg0.getName() + " set " + setStr.substring(1) + " where " + arg2 + " =:id").setParameter("id", arg3).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Object update(Class<?> arg0, Map<String, ?> arg1, String arg2, List<?> arg3) {
		Session session = sessionFactory.getCurrentSession();
		try {
			String setStr = "";
			for (Entry<String, ?> entry : arg1.entrySet()) {
				if (entry.getValue() instanceof String) {
					setStr += "," + entry.getKey() + "='" + entry.getValue() + "'";
				} else {
					setStr += "," + entry.getKey() + "=" + entry.getValue();
				}
			}
			return session.createQuery("update " + arg0.getName() + " set " + setStr.substring(1) + " where " + arg2 + " in (:id)").setParameterList("id", arg3).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Object delete(Object arg0) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.delete(arg0);
			return arg0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Object delete(Class<?> arg0, String arg1, List<?> arg2) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.createQuery("delete " + arg0.getName() + " where " + arg1 + " in(:id)").setParameterList("id", arg2).executeUpdate();
			return arg2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Object clear(Class<?> arg0) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.createQuery("delete " + arg0.getName()).executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Object select(Class<?> arg0) {
		Session session = sessionFactory.getCurrentSession();
		try {
			return session.createQuery("from " + arg0.getName()).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Object selectById(Class<?> arg0, Serializable arg1) {
		Session session = sessionFactory.getCurrentSession();
		try {
			return session.get(arg0, arg1, LockMode.UPGRADE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Object select(Class<?> arg0, List<ConditionBean> arg1, Integer arg2, Boolean arg3, String arg4, PaginationBean arg5) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cri = session.createCriteria(arg0);
		if (null != arg1 && arg1.size() > 0) {
			Criterion[] criterions = new Criterion[arg1.size()];
			for (int i = 0; i < arg1.size(); i++) {
				ConditionBean condition = arg1.get(i);
				Criterion criterion = null;
				switch (condition.getRelation()) {
					case ConditionBean.EQ:
						criterion = Restrictions.eq(condition.getKey(), condition.getValue());
						break;
					case ConditionBean.NOT_EQ:
						criterion = Restrictions.ne(condition.getKey(), condition.getValue());
						break;
					case ConditionBean.GE:
						criterion = Restrictions.ge(condition.getKey(), condition.getValue());
						break;
					case ConditionBean.GT:
						criterion = Restrictions.gt(condition.getKey(), condition.getValue());
						break;
					case ConditionBean.LE:
						criterion = Restrictions.le(condition.getKey(), condition.getValue());
						break;
					case ConditionBean.LT:
						criterion = Restrictions.lt(condition.getKey(), condition.getValue());
						break;
					case ConditionBean.LIKE:
						criterion = Restrictions.ilike(condition.getKey(), condition.getValue());
						break;
					case ConditionBean.IN:
						criterion = Restrictions.in(condition.getKey(), (List<Object>) condition.getValue());
						break;
					case ConditionBean.NOT_IN:
						criterion = Restrictions.not(Expression.in(condition.getKey(), (List<Object>) condition.getValue()));
						break;
					case ConditionBean.IS_EMPTY:
						criterion = Restrictions.isEmpty(condition.getKey());
						break;
					case ConditionBean.IS_NOT_EMPTY:
						criterion = Restrictions.isNotEmpty(condition.getKey());
						break;
					case ConditionBean.IS_NULL:
						criterion = Restrictions.isNull(condition.getKey());
						break;
					case ConditionBean.IS_NOT_NULL:
						criterion = Restrictions.isNotNull(condition.getKey());
						break;
					//注意与或之间的嵌套
					case ConditionBean.AND:
						criterion = Restrictions.and((Criterion[])condition.getValue());
						break;
					case ConditionBean.OR:
						criterion = Restrictions.or((Criterion[])condition.getValue());
						break;
				}
				criterions[i] = criterion;
			}
			if (null == arg2 || arg2 == 0) {
				cri.add(Restrictions.and(criterions));
			} else {
				cri.add(Restrictions.or(criterions));
			}
		}
		if (null != arg4 && !arg4.isEmpty()) cri.addOrder(arg3 ? Order.asc(arg4) : Order.desc(arg4));
		try {
			if (null != arg5) {
				Long totalCount = (Long) cri.setProjection(Projections.rowCount()).uniqueResult();
				cri.setProjection(null);
				List<Object> result = cri.setFirstResult(arg5.getStartIndex()).setMaxResults(arg5.getPageSize()).list();
				arg5.setTotalCount(totalCount == null ? 0 : totalCount.intValue());
				arg5.setRecords(result);
				return arg5;
			} else {
				return (List<Object>) cri.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object select(Class<?> arg0, Map<String,String> arg1, boolean arg2) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cri = session.createCriteria(arg0);
		if(arg2){		//如果已关联邮箱
			cri.add(
				Restrictions.and(
					Restrictions.or(
						Restrictions.eq("account", arg1.get("account")),
						Restrictions.eq("email", arg1.get("email"))
					),
					Restrictions.eq("password",arg1.get("password"))
				)
			);
		}else{
			cri.add(Restrictions.and(Restrictions.eq("account", arg1.get("account")),Restrictions.eq("password",arg1.get("password"))));
		}
		try {
			return (List<Object>) cri.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Object executeHql(String arg0) {
		if (null != arg0 && !arg0.isEmpty()) {
			Session session = sessionFactory.getCurrentSession();
			try {
				
				Query query = session.createQuery(arg0);
				if (arg0.startsWith("select")) {
					if (arg0.indexOf("limit") > 0) {
						int s = arg0.indexOf("limit ") + 6, e = 0;
						String limitValueStr = arg0.substring(s);
						e = limitValueStr.indexOf(" ");
						if (e > s) limitValueStr = limitValueStr.substring(s, e);
						String[] limitValues = limitValueStr.split(",");
						if (limitValues.length == 2) query.setFirstResult(Integer.parseInt(limitValues[0])).setMaxResults(Integer.parseInt(limitValues[1]));
					}
					return query.list();
				} else return query.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Object executeSql(String sql) {
		if(null != sql && !sql.isEmpty()){
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createSQLQuery(sql);
			try{
				if(sql.startsWith("select")){
					return query.list();
				}else return query.executeUpdate();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
