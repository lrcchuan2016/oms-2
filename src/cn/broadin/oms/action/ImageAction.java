package cn.broadin.oms.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.broadin.oms.service.ImageService;

import com.opensymphony.xwork2.ActionSupport;

@Controller("imageAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ImageAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private String inputPath;
	private String savePath;

	@Resource
	private ImageService imageService;
	
	public String getInputPath() {
		return inputPath;
	}
	
	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}
	
	public String getSavePath() {
		return savePath;
	}
	
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	/**
	 * 请求图片资源
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public InputStream getImageFile() throws Exception {
		if (null != inputPath && inputPath.split("\\.").length > 1) {
			byte[] buffer = imageService.getImageById(inputPath.split("\\.")[0]).getImage();
			return new ByteArrayInputStream(buffer);
		}
		return null;
	}
}
