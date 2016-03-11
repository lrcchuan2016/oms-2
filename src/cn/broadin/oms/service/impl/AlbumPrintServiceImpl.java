package cn.broadin.oms.service.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


import cn.broadin.oms.util.Tools;


/**
 * 相册冲印处理类
 * @author huchanghuan
 *
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AlbumPrintServiceImpl {
	
	private BufferedImage bi = null;
	private Graphics2D g = null;
	//private final static String defaultUrl ="http://cdn.meijx.cn/images/4f10ad4d99af29992202e744ac096ed41444362125.png";
	

	/**
	 * 遍历json数据
	 */
	public File JsonTraverse(JSONObject json,String albumName){
		int fw = json.getJSONObject("resolution").getInt("@w");
		int fh = json.getJSONObject("resolution").getInt("@h");
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		ZipOutputStream zip = null;
		File zipFile = new File(contextPath+"/FileResource/"+albumName+".zip");
		try {
			zip = new ZipOutputStream(new FileOutputStream(zipFile));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		JSONArray jaPages = json.getJSONArray("pages");
		int len = jaPages.size();
		for(int i=0;i<len;i++){
			JSONObject joPage = jaPages.getJSONObject(i);
			String pageName = joPage.getString("@name");
			//由背景图片url生成BufferedImage，并设置画笔属性
			String backgroundUrl = joPage.getJSONObject("background").getString("@url");
			File bgFile = new File(Tools.getUrlFile(backgroundUrl));
			BufferedImage bgUrl = setGraphics2D(bgFile);
			//获得的fields竟然有数组也有对象
			Object objFields = joPage.get("fields");
			if(objFields instanceof net.sf.json.JSONObject){
				JSONObject joField = ((JSONObject)objFields).getJSONObject("field");
				drawPic(joField,bgUrl,fw,fh);
			}else{
				JSONArray jaFields = (JSONArray)objFields;
				int length = jaFields.size();
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, bgUrl.getWidth(), bgUrl.getHeight());
				for(int j=0;j<length;j++){
					JSONObject joField = jaFields.getJSONObject(j);
					drawPic(joField,bgUrl,fw,fh);
				}
			}
			g.drawImage(bgUrl, 0, 0,bgUrl.getWidth(),bgUrl.getHeight(), null);
			//绘饰品
			Object objDecorates = joPage.get("decorates");
			if(objDecorates instanceof net.sf.json.JSONObject){
				JSONObject joDecorate = ((JSONObject)objDecorates).getJSONObject("decorate");
				drawDecorate(joDecorate,bgUrl,fw,fh);
			}else{
				JSONArray jaDecorates = (JSONArray)objDecorates;
				int length = jaDecorates.size();
				for(int j=0;j<length;j++){
					JSONObject joDecorate = jaDecorates.getJSONObject(j);
					drawDecorate(joDecorate,bgUrl,fw,fh);
				}
			}
			//绘图结束
			g.dispose();
			try {
				File file = new File(contextPath+"/FileResource/"+pageName+".jpg");
				System.out.println(file.getAbsolutePath());
				ImageIO.write(bi, "jpg", file);	//生成图片到指定文件
				file = setDpi(file);			//设置图片DPI（300）
				Tools.getZip(zip, file);		//放入压缩文件
				file.delete();
				bgFile.delete();
				//break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			zip.finish();
			zip.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return zipFile;
	}

	/**
	 * 设置画布相关的画笔属性
	 * @param backgroundUrl
	 * @return
	 */
	private BufferedImage setGraphics2D(File backgroundFile) {
		BufferedImage bgUrl = null;
		try {
			bgUrl = ImageIO.read(backgroundFile);
			bi = new BufferedImage(bgUrl.getWidth(), bgUrl.getHeight(), BufferedImage.TYPE_INT_RGB);
			g = (Graphics2D) bi.getGraphics();
			g.setColor(new Color(255,255,255));   //前面三位表颜色，后面一位表透明度,选填
			g.fillRect(0, 0, bgUrl.getWidth(), bgUrl.getHeight());
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return bgUrl;
	}

	/**
	 * 绘各个图片域中的图片
	 * @param joField
	 * @param bgUrl
	 * @param fw
	 * @param fh
	 * @param contextPath 
	 */
	private void drawPic(JSONObject jo, BufferedImage bgUrl, int fw, int fh) {
		JSONObject joField = null;
		JSONObject joResource = null;
		try{
			//没有pic节点，跳过此图片域
			joField = jo.getJSONObject("pic");
			joResource = joField.getJSONObject("resource");
		}catch (Exception e) {
			return;
		}
		
		if(joResource.containsKey("@url")){
		String url = joResource.getString("@url");
		if(!"".equals(url) && !"null".equals(url) && null != url && url.startsWith("http")){ 
			
			//画布（背景图）与xml文件宽高属性比
			double sw = (bgUrl.getWidth()*1.0)/fw;
			double sh = (bgUrl.getHeight()*1.0)/fh;
			//各个属性
			double x = joField.getDouble("@x");
			double y = joField.getDouble("@y");
			double w = joField.getDouble("@w");
			double h = joField.getDouble("@h");
			double r = joField.getDouble("@r");
			double width = joResource.getDouble("@w");
			double height = joResource.getDouble("@h");
			double fieldWidth = jo.getDouble("@w");
			double fieldHeight = jo.getDouble("@h");	
			double fieldX = jo.getDouble("@x");
			double fieldY = jo.getDouble("@y");
			
			
			//图片真实方向的宽高（PS：防止拍摄方向的差异带来真实宽高异位）
			File srcFile = new File(Tools.getUrlFile(url+"@2o"));
			BufferedImage img = null;
			try {
				img = ImageIO.read(srcFile);
				System.out.println(url+"@2o");
				width = img.getWidth();
				height = img.getHeight();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				srcFile.delete();
			}
				
			
			//原图与xml文件pic的w之间的比率
			double pw = width/w; double ph = height/h;

			//旋转以及强制宽或高缩略
			double pX = fieldX-x>0?fieldX:x;
			double pY = fieldY-y>0?fieldY:y;
			double pWidth = 0;
			double pHeight = 0;
			
			//截取后图片实际宽高
			if(fieldX+fieldWidth>x+w) pWidth = x+w-pX;
			else pWidth = fieldX+fieldWidth-pX;
			
			if(fieldY+fieldHeight>y+h) pHeight = y+h-pY;
			else pHeight = fieldY+fieldHeight-pY;
			
			//旋转裁剪的参数
			r = r<0?360+r:r;
			int cutX = (int)Math.round((fieldX-x<0?0:fieldX-x)*pw);
			int cutY = (int)Math.round((fieldY-y<0?0:fieldY-y)*ph);
			int cutW = (int)Math.round(pw*pWidth);
			int cutH = (int)Math.round(ph*pHeight);
			img = this.shearRotateImage(img, cutX, cutY, cutW, cutH, r);
			
			//自适应方向的原图片（防止拍摄方向带来的问题）
			//BufferedImage img = null;
			//img = componseImage(url, width, height);
			
//			try {
//				File f = componseImage(url, width, height);
//				img = ImageIO.read(f);
//				System.out.println("原图宽高："+img.getWidth()+":"+img.getHeight());
//				f.delete();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			//在画布上画上图片
            g.drawImage(img, (int)Math.round(pX*sw), (int)Math.round(pY*sh),(int)Math.round(pWidth*sw),(int)Math.round(pHeight*sh), null);
            img = null;
		}
		}
	}
	
	/**
	 * 绘制饰品
	 * @param joDecorate
	 * @param bgUrl
	 * @param fw
	 * @param fh
	 */
	private void drawDecorate(JSONObject joDecorate, BufferedImage bgUrl,
			int fw, int fh) {
		JSONObject joResource = joDecorate.getJSONObject("resource");
		String url = joResource.getString("@url");
		if(!"".equals(url) && !"null".equals(url) && null != url && url.startsWith("http:")){
			BufferedImage img = null;
			double sw = (bgUrl.getWidth()*1.0)/fw;
			double sh = (bgUrl.getHeight()*1.0)/fh;
			double w = joDecorate.getDouble("@w");
			double h = joDecorate.getDouble("@h");
			double x = joDecorate.getDouble("@x");
			double y = joDecorate.getDouble("@y");
			double r = joDecorate.getDouble("@r");
			double width = joResource.getDouble("@w");
			double height = joResource.getDouble("@h");
			
			if(width == 0 || width == 0.0 || height == 0 || height == 0.0){
				BufferedImage srcImg = null;
				try {
					srcImg = ImageIO.read(new URL(url));
					width = srcImg.getWidth();
					height = srcImg.getHeight();
				} catch (IOException e) {
					e.printStackTrace();
				}
				srcImg = null;
			}
			//原图与xml文件pic的w之间的比率
			if(r<0) r += 360;
			if(width>height){
				url += "@"+(int)Math.round(w) + "w_2o_2e"+"_"+ (int)Math.round(r) + "r.png";
			} else{ 
				url += "@"+(int)Math.round(h) + "h_2o_2e"+"_"+ (int)Math.round(r) + "r.png";
			}
			try {
				img = ImageIO.read(new URL(url));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(img, (int)Math.round(x*sw), (int)Math.round(y*sh),(int)Math.round(w*sw),(int)Math.round(h*sh), null);
			img = null;
		}
	}
	
	/**
	 * 设置图片DPI（300）
	 * @param file
	 * @return
	 */
	private  File setDpi(File file) {
        FileOutputStream fos = null;
		try {
			//读取要设置像素的图片文件
			//file = Tools.formatPic(file, "png");
			BufferedImage image = ImageIO.read(file);  
			fos = new FileOutputStream(file);  
			JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fos); 
			//获取关于图片参数的对象
			JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(image); 
			//设置的图片参数
			jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);  
			jpegEncodeParam.setXDensity(300);  
			jpegEncodeParam.setYDensity(300); 
			jpegEncodeParam.setQuality(0.8f, true);
			jpegEncoder.encode(image, jpegEncodeParam);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}  
		return file;
	}
	
	
	/**
	 * 图片文件格式转换
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unused")
	private  File formatPic(File file,String format){
		if(file.exists()){
			BufferedImage srcImg = null;
			BufferedImage destImg = null;
			try {
				srcImg = ImageIO.read(file);
				destImg = new BufferedImage(srcImg.getWidth(), srcImg.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics g = destImg.getGraphics();
				g.drawImage(srcImg, 0, 0, null);
				g.dispose();
				ImageIO.write(destImg, format, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			srcImg = null;
			destImg = null;
		}
		return file;
	}
	
	/**
	 * 裁剪
	 * @param srcPath
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	public BufferedImage shear(String srcPath,double x,double y,double w,double h){  
        FileInputStream fis = null;  
        ImageInputStream iis = null;  
        BufferedImage bi = null;
        try {  
            // 读取图片文件  
            fis = new FileInputStream(srcPath);  
  
            Iterator<ImageReader> it = ImageIO  
                    .getImageReadersByFormatName("jpg");  
  
            ImageReader reader = it.next();  
  
            // 获取图片流  
            iis = ImageIO.createImageInputStream(fis);  
  
            reader.setInput(iis, true);  
            ImageReadParam param = reader.getDefaultReadParam();  
            Rectangle rect = new Rectangle((int)Math.round(x), (int)Math.round(y), (int)Math.round(w), (int)Math.round(h));  
  
            // 提供一个 BufferedImage，将其用作解码像素数据的目标。  
            param.setSourceRegion(rect);  
            bi = reader.read(0, param);  
  
            // 保存新图片  
            ImageIO.write(bi, "jpg", new File(srcPath));  
        } catch (IOException e) {
			e.printStackTrace();
		} finally {  
            try {
				if (fis != null)  
				    fis.close();  
				if (iis != null)  
					iis.flush();
				    iis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
        }  
  
        return bi;
    }  
	
	/**
	 * 缩放
	 */
	public BufferedImage scale(BufferedImage image,double toWidth,double toHeight){
		//生成指定宽高的图片
		int width = (int)Math.round(toWidth);
		int height = (int)Math.round(toHeight);
 		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics2D g = (Graphics2D) img.createGraphics();
		g.drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
		g.dispose();
		return img;
	}
	
	/**
	 * 对图片进行裁剪旋转操作
	 * @param srcImage
	 * @param cutX
	 * @param cutY
	 * @param cutW
	 * @param cutH
	 * @param r
	 * @return
	 */
	public BufferedImage shearRotateImage(BufferedImage srcImage,int cutX,int cutY,int cutW,int cutH,double r){
		BufferedImage img = null;
		try {
			//先旋转
			
				img = Thumbnails.of(srcImage)
						.size(srcImage.getWidth(), srcImage.getHeight())
						.rotate(r)
						.outputFormat("png")
						.outputQuality(0.8f)
						.keepAspectRatio(false)
						.asBufferedImage();
				
			double cutRateX = img.getWidth()*1.0/srcImage.getWidth();
			double cutRateY = img.getHeight()*1.0/srcImage.getHeight();

			//按照旋转后尺寸裁剪
			img = Thumbnails.of(img)
			.sourceRegion((int)(cutX*cutRateX), (int)(cutY*cutRateY), (int)(cutW*cutRateX), (int)(cutH*cutRateY))
			.size((int)(cutW*cutRateX), (int)(cutH*cutRateY))
			.outputFormat("png")
			.outputQuality(0.8f)
			.keepAspectRatio(false)
			.asBufferedImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	/**
	 * 组成图片的URL
	 * @param path
	 * @param srcWidth
	 * @param srcHeight
	 * @param w
	 * @param h
	 * @return
	 */
	public File componseImage(String path,double srcWidth,double srcHeight){
		int W = (int)srcWidth;
		int H = (int)srcHeight;
		StringBuffer sb = new StringBuffer(path);
		
		sb.append("@");
		//sb.append("1280W");
		sb.append("2o");
		File f = new File(Tools.getUrlFile(sb.toString()));
		
		System.out.println(sb.toString());
		
		try {
			Thumbnails.of(f).size(W, H).toFile(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(f.getPath());
			
		return f;
	}
}
