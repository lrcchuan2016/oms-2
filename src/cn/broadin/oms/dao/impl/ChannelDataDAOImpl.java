package cn.broadin.oms.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.broadin.oms.dao.ChannelDataDAO;

@Repository("channelDataDaoImpl")
public class ChannelDataDAOImpl implements ChannelDataDAO {
	@Resource
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> selectUserCountOfChannel(String arg0, int arg1,
			int arg2) {
		String queryDate = arg2+"-"+arg1;
		String sql  = "select count(*) t,register_term_type rt from user u where channel_id = "+arg0+
				" and "+queryDate+" like date(FROM_UNIXTIME(register_utc/1000,'%Y-%m')) group by register_term_type";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		return (List<Object[]>)query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> selectInstallCountOfChannel(String arg0, int arg1,
			int arg2) {
		String queryDate = arg2+"-"+arg1;
		String sql  = "select count(*),client_type from l_log where partner_code = "+arg0+
				" and "+queryDate+" like date(FROM_UNIXTIME(register_utc/1000,'%Y-%m')) group by client_type";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		return (List<Object[]>)query.list();
	}

}
