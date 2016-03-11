package cn.broadin.oms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.broadin.oms.dao.DAO;
import cn.broadin.oms.model.AlbumBean;
import cn.broadin.oms.model.AlbumMediaBean;
import cn.broadin.oms.model.AlbumRecommendBean;
import cn.broadin.oms.model.Command;
import cn.broadin.oms.service.AlbumService;
import cn.broadin.oms.util.ConditionBean;
import cn.broadin.oms.util.PaginationBean;
import cn.broadin.oms.util.ServiceResultBean;

/**
 * 相册业务实现类
 * @author Administrator
 *
 */
@Service
public class AlbumServiceImpl implements AlbumService {

	@Resource
	private DAO dao;

	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean pageAlbum(Class<AlbumBean> album,
			List<ConditionBean> clist, Integer object2, boolean b,
			String string, PaginationBean pBean) {
		if(null != pBean){
			pBean = (PaginationBean) dao.select(album, clist, object2, b, string, pBean);
		}else{
			List<AlbumBean> albums = (List<AlbumBean>) dao.select(album, clist, object2, b, string, pBean);
			pBean = new PaginationBean(0, 1, albums.size(), albums);
		}
		return pBean;
	}

	@Override
	public AlbumBean selectById(String id) {
		
		return (AlbumBean) dao.selectById(AlbumBean.class, id);
	}

	@Override
	public boolean update(AlbumBean alBean) {
		if(null!=dao.update(alBean)){
			Command c = new Command("album:update", alBean.getId(), null, null);
			dao.insert(c);
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(AlbumBean alBean) {
		if(null!=dao.delete(alBean)){
			Command c = new Command("album:delete", alBean.getId(), null, null);
			dao.insert(c);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationBean pageList(Class<AlbumMediaBean> class1,
			List<ConditionBean> clist, Integer i, boolean b, String string,
			PaginationBean paginationBean) {
		
		if(null != paginationBean){
			paginationBean = (PaginationBean) dao.select(class1, clist, i, b, string, paginationBean);
		}else{
			List<AlbumMediaBean> ams = (List<AlbumMediaBean>) dao.select(class1, clist, i, b, string, paginationBean);
			paginationBean = new PaginationBean(0, 1, ams.size(), ams);
		}
		
		return paginationBean;
	}

	@Override
	public boolean deleteAlbumMedia(AlbumMediaBean albumMediaBean) {
		if(null!=dao.delete(albumMediaBean)){
			Command com = new Command("album_media:delete", albumMediaBean.getId(), null, null);
			dao.insert(com);
			return true;
		}
		return false;
	}

	@Override
	public ServiceResultBean insertAlbumMedia(AlbumMediaBean albumMediaBean) {
		ServiceResultBean srb = new ServiceResultBean();
		if(null!=dao.insert(albumMediaBean)){
			Command com = new Command("album_media:add", albumMediaBean.getId(), null, null);
			dao.insert(com);
			return srb;
		}
		srb.setSuccess(false);
		return srb;
	}

	@Override
	public AlbumMediaBean updateAlbumMedia(AlbumMediaBean albumMedia) {
		
		Command com = new Command("album_media:update", albumMedia.getId(), null, null);
		if(null != dao.insert(com)){
			return albumMedia;
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AlbumBean> selectByCondition(Class<?> arg0,List<ConditionBean> arg1,Integer arg2,Boolean arg3,String arg4,PaginationBean arg5) {
		
		return (List<AlbumBean>) dao.select(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public AlbumRecommendBean findARecommendById(String album_id) {
		
		return (AlbumRecommendBean) dao.selectById(AlbumRecommendBean.class, album_id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer findMaxWeight() {
		String sql = "select max(weight) from album_recommend";
		List<Object> os = (List<Object>) dao.executeSql(sql);
		Integer weight = null;
		if(null != os && 0 != os.size()){
			Object  o = os.get(0);
			if(null != o){
				weight = (Integer)o;
			}
		}
		return weight;
	}

	@Override
	public AlbumRecommendBean insertAlbumRecommend(
			AlbumRecommendBean albumRecommend) {
		Object o = dao.insert(albumRecommend);
		if(null != o){
			Command com = new Command("album_recommend:add", albumRecommend.getAlbum_id(), null, null);
			dao.insert(com);
			return albumRecommend;
		}
		return null;
	}

	@Override
	public AlbumRecommendBean updateAlbumRecommend(
			AlbumRecommendBean albumRecommend) {
		AlbumRecommendBean aRecommend = (AlbumRecommendBean) dao.update(albumRecommend);
		if(null != aRecommend){
			Command com = new Command("album_recommend:update", albumRecommend.getAlbum_id(), null, null);
			dao.insert(com);
			return aRecommend;
		}
		return null;
	}

	@Override
	public AlbumRecommendBean delAlbumRecommend(
			AlbumRecommendBean albumRecommend) {
		AlbumRecommendBean aRecommend = (AlbumRecommendBean) dao.delete(albumRecommend);
		if(null != aRecommend){
			Command com = new Command("album_recommend:delete", albumRecommend.getAlbum_id(), null, null);
			dao.insert(com);
			return aRecommend;
		}
		return null;
	}


}
