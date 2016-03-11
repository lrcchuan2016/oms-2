package cn.broadin.oms.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.service.impl.CommonServiceImpl;
import cn.broadin.oms.util.OSSConst;
import cn.broadin.oms.util.Tools;

import com.aliyun.oss.OSSClient;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 资源管理文件(模版，装饰品图片)上传
 * 本类可扩展上传其他文件
 * 扩展字段 typeKey
 * @author xiejun
 */

@Controller("resourceImageAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ResourceImageAction extends ActionSupport{
	private static final long serialVersionUID = 5097645219887819872L;
	private File image;
	private String imageFileName;
	private String imageContentType;
	private String typeKey;
	private Map<String, Object> resultJson = new HashMap<String, Object>();
	private String input;
	
	@Resource
	private CommonServiceImpl commService;
	
	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	
	public String getInput() {
		return input;
	}
	
	public Map<String, Object> getResultJson() {
		return resultJson;
	}

	public String getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
	}
	
	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	/**
	 * 上传到阿里云的图片
	 * @return
	 * @throws Exception
	 */
	public String uploadOSSImage() throws Exception{
		if(this.image !=null && this.imageContentType != null){
			this.imageContentType = this.imageFileName.split("\\.")[1];
			OSSClient oc = commService.createOSSClient();
			String ossUpKey = "",keyFoot = "";
			String md5_str = Tools.fileMD5(this.image);
			if(this.typeKey!=null){
				if(this.typeKey.equals("template")){
					keyFoot = Tools.getUUIDString("T");
					ossUpKey = OSSConst.TEMPLATE_KEY_HEAD+keyFoot;
				}else if(this.typeKey.equals("templateGroup")){
					keyFoot = Tools.getUUIDString("G");
					ossUpKey = OSSConst.TEMPLATE_GROUP_KEY_HEAD+keyFoot;
				}else if(this.typeKey.equals("decorate")){
					keyFoot = Tools.getUUIDString("D");
					ossUpKey = OSSConst.DECORATE_KEY_HEAD+keyFoot;
				}else if(this.typeKey.equals("decorateGroup")){
					keyFoot = Tools.getUUIDString("C");
					ossUpKey = OSSConst.DECORATE_KEY_GROUP_HEAD+md5_str;
				}else if(this.typeKey.equals("music")){
					keyFoot = Tools.getUUIDString("M");
					ossUpKey = OSSConst.MEDIA_MUSIC_KEY_HEAD+md5_str;
				}else if(this.typeKey.equals("channel")){
					keyFoot = Tools.getUUIDString("C");
					ossUpKey = OSSConst.CHANNEL_ICON_KEY_HEAD+keyFoot;
				}else if(this.typeKey.equals("ad")){
					keyFoot = Tools.getUUIDString("A");
					ossUpKey = OSSConst.AD_RESOURCE_KEY_HEAD+keyFoot;
				}else if(this.typeKey.equals("ver")){
					keyFoot = Tools.getUUIDString("ver");
					ossUpKey = OSSConst.VERSION_KEY_HEAD+keyFoot;
				}else if(this.typeKey.equals("vs")){
					keyFoot = Tools.getUUIDString("vs");
					ossUpKey = OSSConst.VERSION_SCREENSHOT_KEY_HEAD+keyFoot;
				}
			}else{
				ossUpKey = OSSConst.TYPE_KEY_NULL_HEAD+Tools.getUUIDString("N");
			}
			String fileName = ossUpKey+"."+this.imageContentType;
			String eTag = commService.uploadSingleFile(oc, fileName, this.image);
			System.out.println(OSSConst.ALIYUN_IMAGE_DOMAIN+fileName);
			if(eTag.equals(md5_str)){
				resultJson.put("result", "00000000");
				resultJson.put("productKey", keyFoot); 
				resultJson.put("fileName", this.imageFileName);
				//获得图片的高宽
				if(imageContentType.equals("jpg") 
						|| imageContentType.equals("jpeg") 
						|| imageContentType.equals("png") 
						|| imageContentType.equals("bmp")
						|| imageContentType.equals("gif")){
					InputStream in = new ByteArrayInputStream(Tools.getBytes(image));
					BufferedImage buf = ImageIO.read(in);
					int width = buf.getWidth();
					int height = buf.getHeight();
					resultJson.put("width", width);
					resultJson.put("height", height);
					resultJson.put("imageUrl", OSSConst.ALIYUN_IMAGE_DOMAIN+fileName);
				}else if(imageContentType.equals("m4a") 
						|| imageContentType.equals("mp3")
						|| imageContentType.equals("avi")
						|| imageContentType.equals("mp4")){
					resultJson.put("duration", Tools.getAudioPlayTime(image));
					resultJson.put("imageUrl", OSSConst.ALIYUN_OTHER_DOMAIN+fileName);
				}
			}else{
				resultJson.put("result", "00000001");
				resultJson.put("imageUrl", "");
			}
		}else{
			resultJson.put("result", "00000001");
			resultJson.put("imageUrl", "");
		}
		return Action.SUCCESS;
	}

	
	
	
}
