package cn.broadin.oms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.ChannelDataDAO;
import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.ChannelInstallBean;
import cn.broadin.oms.model.ChannelUserBean;
import cn.broadin.oms.service.DataStatisticService;
import cn.broadin.oms.util.TerminalTypeConst;

@Service
public class DataStatisticServiceImpl implements DataStatisticService {
	@Resource
	private ChannelDataDAO channelDataDao;
	@Resource
	private DAO dao;
	
	@Override
	public ChannelUserBean findChannelNewUser(String arg0, int arg1,int arg2) {
		List<Object[]> list = channelDataDao.selectUserCountOfChannel(arg0, arg1, arg2);
		ChannelUserBean channelUserBean = new ChannelUserBean();
		int otherCount = 0;
		for(int i=0;i<list.size();i++){
			Object[] tempArray = list.get(i);
			int count = 0;
			String terminalTypeId = "";
			if(tempArray[0]!=null) count = Integer.parseInt(tempArray[0].toString());
			if(tempArray[1]!=null) terminalTypeId = tempArray[1].toString();
			if(terminalTypeId.equals(TerminalTypeConst.TERMINAL_TYPE1)){		//机顶盒
				channelUserBean.setStbUser(count);
			}else if(terminalTypeId.equals(TerminalTypeConst.TERMINAL_TYPE8)){	//智能电视
				channelUserBean.setSmartTvUser(count);
			}else if(terminalTypeId.equals(TerminalTypeConst.TERMINAL_TYPE10)){	//应用商店
				channelUserBean.setAppstoreUser(count);
			}else if(terminalTypeId.equals(TerminalTypeConst.TERMINAL_TYPE9)){	//广电双模
				channelUserBean.setDvbUser(count);
			}else{
				otherCount += count;
			}
		}
		channelUserBean.setOtherUser(otherCount);
		channelUserBean.setMonth(arg1);
		channelUserBean.setYear(arg2);
		return channelUserBean;
	}

	@Override
	public ChannelInstallBean findChannelInstall(String arg0, int arg1, int arg2) {
		List<Object[]> list = channelDataDao.selectInstallCountOfChannel(arg0, arg1, arg2);
		ChannelInstallBean channelInstallBean = new ChannelInstallBean();
		int otherCount = 0;
		for(int i=0;i<list.size();i++){
			Object[] tempArray = list.get(i);
			int count = 0;
			String terminalTypeId = "";
			if(tempArray[0]!=null) count = Integer.parseInt(tempArray[0].toString());
			if(tempArray[1]!=null) terminalTypeId = tempArray[1].toString();
			if(terminalTypeId.equals(TerminalTypeConst.TERMINAL_TYPE1)){		//机顶盒
				channelInstallBean.setStbInstall(count);
			}else if(terminalTypeId.equals(TerminalTypeConst.TERMINAL_TYPE8)){	//智能电视
				channelInstallBean.setSmartTvInstall(count);
			}else if(terminalTypeId.equals(TerminalTypeConst.TERMINAL_TYPE10)){	//应用商店
				channelInstallBean.setAppstoreInstall(count);
			}else if(terminalTypeId.equals(TerminalTypeConst.TERMINAL_TYPE9)){	//广电双模
				channelInstallBean.setDvbInstall(count);
			}else{
				otherCount += count;
			}
		}
		channelInstallBean.setOtherInstall(otherCount);
		channelInstallBean.setMonth(arg1);
		channelInstallBean.setYear(arg2);
		return channelInstallBean;
	}

	@Override
	public Object findUserDistribution(String sql) {
		
		return dao.executeSql(sql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> executeSql(String sql) {
		
		return (List<Object[]>) dao.executeSql(sql);
	}
}
