package cn.broadin.oms.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;


import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;


import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;





import cn.broadin.oms.model.ChannelBean;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Tools {
	
	private static final Logger log = Logger.getLogger(Tools.class);
	
	/**
	 * 获得UUID prefix定义如下： #define ID_TYPE_CHANNEL 'C' #define ID_TYPE_FILE 'F'
	 * #define ID_TYPE_PACKAGE 'P' ##define ID_TYPE_CERTIFACATE 'T' #define
	 * ID_TYPE_IMAGE 'I'
	 */
	public static String getUUIDString(String prefix) {
		String id = UUID.randomUUID().toString();
		id = id.replace("-", "");
		id = prefix + id;
		return id.toUpperCase();
	}
	
	private static String byteArrayToHex(byte[] byteArray) {  
	   // 首先初始化一个字符数组，用来存放每个16进制字符  
	   char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };  
	   // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））  
	   char[] resultCharArray =new char[byteArray.length * 2];  
	   // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去   
	   int index = 0;  
	   for (byte b : byteArray) {  
	      resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];  
	      resultCharArray[index++] = hexDigits[b& 0xf];  
	   }  
	   // 字符数组组合成字符串返回  
	   return new String(resultCharArray); 
	}
	
	public static String fileMD5(File inputFile) throws IOException {  
	      // 缓冲区大小（这个可以抽出一个参数）  
	      int bufferSize = 256 * 1024;  
	      FileInputStream fileInputStream = null;  
	      DigestInputStream digestInputStream = null;  
	      try {
	         // 拿到一个MD5转换器（同样，这里可以换成SHA1）  
	         MessageDigest messageDigest =MessageDigest.getInstance("MD5");  
	         // 使用DigestInputStream  
	         fileInputStream = new FileInputStream(inputFile);  
	         digestInputStream = new DigestInputStream(fileInputStream,messageDigest);  
	         // read的过程中进行MD5处理，直到读完文件  
	         byte[] buffer =new byte[bufferSize];  
	         while (digestInputStream.read(buffer) > 0);  
	         // 获取最终的MessageDigest  
	         messageDigest= digestInputStream.getMessageDigest();  
	         // 拿到结果，也是字节数组，包含16个元素  
	         byte[] resultByteArray = messageDigest.digest();  
	         // 同样，把字节数组转换成字符串  
	         return byteArrayToHex(resultByteArray);  
	      } catch (NoSuchAlgorithmException e) {  
	         return null;  
	      } finally {  
	         try {  
	            digestInputStream.close();  
	         } catch (Exception e) {
	        	e.printStackTrace();
	         }  
	         try {
	            fileInputStream.close();  
	         } catch (Exception e) {
	        	 e.printStackTrace();
	         }
	      }  
	  
	   }  
	
	/**
	 * 获得MD5编码
	 * 
	 * @param source
	 *            字节数组
	 * @return
	 */
	public static String getMD5(byte[] source, int len) {
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source, 0, len);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，所以表示成 16
											// 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节转换成 16
											// 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4
															// 位的数字转换,>>>为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串
			
		} catch (Exception e) {
			log.error("getMD5 error!");
		}
		return s.toUpperCase();
	}
	
	/**
	 * 获取16位随机数，并转成字符串
	 * @return String
	 */
	public static String getRandom(){
		int[] array = new int[16];
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<16;i++){
			array[i] = (int) (Math.random()*10);
			sb.append(String.valueOf(array[i]));
		}
		return sb.toString();
	}
	
	/**
	 * 32位UUID+当前时间
	 * 转为MD5，
	 * @param s
	 * @return
	 */
	public static String getMD5AndUUID(int n){
		String id = UUID.randomUUID().toString();
		id = id.replace("-", "")+Tools.getNowUTC();
		if(n<=32) return Tools.getMD5(id.getBytes(), 32).substring(32-n);
		return Tools.getMD5(id.getBytes(), 32);
	}
	
	/**
	 * 根据格式获取当前时间
	 * 
	 * @return String
	 */
	public static String getNowDateString(String formatStr) {
		SimpleDateFormat sf = new SimpleDateFormat(formatStr);
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		return sf.format(curDate);
	}
	
	/**
	 * 获取当前时间的UTC
	 * 
	 * @return
	 */
	public static long getNowUTC() {
		Calendar cal = Calendar.getInstance();
		return cal.getTimeInMillis();
	}
	
	/**
	 * 时间字符串转UTC
	 * 
	 * @param dateStr
	 * @param timeZoneOffset
	 * @param formatStr
	 * @return
	 */
	public static long string2utc(String dateStr, int timeZoneOffset, String formatStr) {
//		log.info("dateStr:" + dateStr + " timeZoneOffset:" + timeZoneOffset);
		TimeZone timeZone = null;
		if (timeZoneOffset == 0) timeZone = TimeZone.getTimeZone("GMT");
		else if (timeZoneOffset < 0) timeZone = TimeZone.getTimeZone("GMT" + timeZoneOffset);
		else timeZone = TimeZone.getTimeZone("GMT+" + timeZoneOffset);
		SimpleDateFormat sf = new SimpleDateFormat(formatStr);
		sf.setTimeZone(timeZone);
		Date date;
		try {
			date = sf.parse(dateStr);
			return date.getTime();
		} catch (ParseException e) {
			log.error("String2Utc: parse error!");
		}
		return 0;
	}
	
	/**
	 * UTC转当前系统时区字符串
	 * 
	 * @param utcTime
	 *            UTC时间
	 * @param formatStr
	 *            时间格式
	 * @return
	 */
	public static String utc2String(long utcTime, String formatStr) {
		SimpleDateFormat sf = new SimpleDateFormat(formatStr);
		return sf.format(new Date(utcTime));
	}
	
	/**
	 * java List转化为json字符串（json数组）
	 * 
	 * @param list
	 * @return
	 */
	public static String javaListToJsonArray(List<?> list) {
		if (list != null) {
			try {
				return JSONArray.fromObject(list).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * json字符串转化为java List（应确保json字符串是json数组）
	 * 
	 * @param json
	 * @param clas
	 * @return
	 */
	public static List<?> jsonArrayToJavaList(String json, Class<?> clas) {
		if (json != null && !json.isEmpty()) {
			try {
				JSONArray array = JSONArray.fromObject(json);
				return JSONArray.toList(array, clas);
			} catch (Exception e) {
				log.error("jsonArrayToJavaList error!");
			}
		}
		return null;
	}
	
	/**
	 * java 对象转化为json字符串（json对象）
	 * 
	 * @param obj
	 * @return
	 */
	public static String javaBeanToJsonObject(Object obj) {
		if (obj != null) {
			try {
				return JSONObject.fromObject(obj).toString();
			} catch (Exception e) {
				log.error("javaBeanToJsonObject error!");
			}
		}
		return null;
	}
	
	/**
	 * json字符串（对象）转化为java对象
	 * 
	 * @param json
	 * @param clas
	 * @return
	 */
	public static Object jsonObjectToJavaBean(String json, Class<?> clas) {
		if (json != null && !json.isEmpty()) {
			try {
				JSONObject jo = JSONObject.fromObject(json);
				return JSONObject.toBean(jo, clas);
			} catch (Exception e) {
				log.error("jsonObjectToJavaBean error!");
			}
		}
		return null;
	}
	
	public static Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception {
		log.info("invokeMethod:" + methodName);
		Class<?> ownerClass = owner.getClass();
		Class<?>[] argsClass = new Class[args.length];
		
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(owner, args);
	}
	
	/**
	 * 字符串转成double型, 出错返回0
	 * 
	 * @param str
	 * @return
	 */
	public static double string2double(String str) {
		try {
			if (null != str && !str.isEmpty()) return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			log.error(str + " is not a valid double string!");
		}
		return 0;
	}
	
	/**
	 * 字符串转成float型, 出错返回0
	 * 
	 * @param str
	 * @return
	 */
	public static float string2float(String str) {
		try {
			if (null != str && !str.isEmpty()) return Float.parseFloat(str);
		} catch (NumberFormatException e) {
			log.error(str + " is not a valid float string!");
		}
		return 0;
	}
	
	/**
	 * 字符串转成int型, 出错返回0
	 * 
	 * @param str
	 * @return
	 */
	public static int string2int(String str) {
		try {
			if (null != str && !str.isEmpty()) return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			log.error(str + " is not a valid int string!");
		}
		return 0;
	}
	
	/**
	 * 字符串转成long型, 出错返回0
	 * 
	 * @param str
	 * @return
	 */
	public static long string2long(String str) {
		try {
			if (null != str && !str.isEmpty()) return Long.parseLong(str);
		} catch (NumberFormatException e) {
			log.error(str + " is not a valid long string!");
		}
		return 0;
	}
	
	/**
	 * 将文件file转化为字节数组
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] getBytes(File file) {
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	/**
	 * 获取当前时间的年份
	 * @return
	 */
	public static int getDayOfYear(){
		Calendar cal = Calendar.getInstance();
	    int year = cal.get(Calendar.YEAR);
	    return year;
	}
	
	/**
	 * 获取当前时间的月份
	 * @return
	 */
	public static int getDayOfMonth(){
		Calendar cal = Calendar.getInstance();
	    int month = cal.get(Calendar.MONTH) + 1;
	    return month;
	}
	
	public static Date getLastMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime(); 
	}
	
	/**
	 * 判断当前时间是否在这个时间间隔里面
	 * @return
	 */
	public static boolean checkIfEndOfDay(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	    String currentDate = sdf.format(new Date());
	    try {
	    	Date currDate = sdf.parse(currentDate);//当前时间
	    	Date startDate = sdf.parse("23:58:00");
	    	Date endDate = sdf.parse("23:59:59");
	    	if(currDate.after(startDate) && currDate.before(endDate)) return true;
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * 对文件参数进行转码
	 * @param imageData
	 * @return
	 * @throws IOException
	 */
	public static byte[] decode(String imageData){ 
        BASE64Decoder decoder=new BASE64Decoder();
        byte[] bytes=null;
        try {
            bytes=decoder.decodeBuffer(imageData);
            for(int i=0;i<bytes.length;i++){
                 if(bytes[i]<0){//调整异常数据
                	 bytes[i]+=256;
                 }
            }
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return bytes;  
    }
	
	public static boolean saveImageToDisk(byte[] data,String filePath,String imageName) throws IOException{   
        File photoPathFile = new File(filePath);
        if(!photoPathFile.exists()){
        	photoPathFile.mkdir();
        }
        // 写入到文件  
        FileOutputStream outputStream = null;
        try {  
            outputStream=new FileOutputStream(filePath+imageName);
            outputStream.write(data);
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally{  
        	outputStream.close();  
        }
        return true;
	}
	
	/**
	 * 字符串进行BASE64加密成字符
	 * @param xml
	 * @return
	 */
	public static String encodeBASE64(String xml){
		String str = null;
		try {
			str = new BASE64Encoder().encodeBuffer(xml.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * BASE64加密的字符串解密成字节
	 * @param xml
	 * @return
	 */
	public static byte[] decodeBASE64(String xml){
		//String str = null;
		byte[] buf = null;
		try {
			if(null != xml && !"".equals(xml)){
				BASE64Decoder decorder = new BASE64Decoder();
				buf = decorder.decodeBuffer(xml);
				//str = new String(buf,"UTF-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buf;
	}
	
	/**
	 * 二进制转换成文件类型
	 * @param byteArray
	 * @param contentType    （如jpg、jpeg等等）
	 * @return
	 */
	public static File formatToFile(byte[] byteArray,String contentType){
		String path = ServletActionContext.getServletContext().getRealPath("/");
		InputStream in = new ByteArrayInputStream(byteArray);
		File f = new File(path+"FileResource");
		if(!f.exists()) f.mkdirs();
		File file = new File(path+"FileResource",Tools.getUUIDString("TemplateSnap")+"."+contentType);
		FileOutputStream fos = null;
		try {
			if(file.createNewFile()){
				fos = new FileOutputStream(file);
				byte[] buf = new byte[1024];
				int len = 0;
				while(-1 != (len=in.read(buf))){
					fos.write(buf,0,len);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
				fos.flush();
				fos.close();
				//注意用后删除
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		return file;
	}
	
	/**
	 * 获取音频文件的播放时长（单位：ms）
	 * @param file
	 * @return
	 */
	public static int getAudioPlayTime(File file){
		int len = 0;
		try {
			FileInputStream fis = new FileInputStream(file);
			Bitstream bt = new Bitstream(fis);
			Header header = bt.readFrame();
			len = (int) header.total_ms(fis.available());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BitstreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return len;
	}

	/**
	 * List按照指定字段排序
	 * @param class1 
	 * @param channels
	 * @param sortFiled
	 * @param sortMod
	 * @return
	 */
	public static List<ChannelBean> sortList(List<ChannelBean> channels,
			String sortFiled, final String sortMod) {
		//覆盖数量
		for (ChannelBean channelBean : channels) {
			if(null == channelBean.getCoverNum()) channelBean.setCoverNum(0);
			if(null == channelBean.getInstallNum()) channelBean.setInstallNum(0);
			if(null == channelBean.getRegisterNum()) channelBean.setRegisterNum(0);
		}
		if(sortFiled.equals("coverNum")){
			Collections.sort(channels,new Comparator<ChannelBean>() {
				@Override
				public int compare(ChannelBean o1, ChannelBean o2) {
					if(sortMod.equals("desc")) {
						return o1.getCoverNum()<o2.getCoverNum()?1:-1;
					}else{
						return o1.getCoverNum()>o2.getCoverNum()?1:-1;
					}
				}
			});
		//安装率
		}else if(sortFiled.equals("installRate")){
			Collections.sort(channels,new Comparator<ChannelBean>() {
				@Override
				public int compare(ChannelBean o1, ChannelBean o2) {
					if(sortMod.equals("desc")) {
						return o1.getInstallRate()<o2.getInstallRate()?1:-1;
					}else{
						return o1.getInstallRate()>o2.getInstallRate()?1:-1;
					}
				}
			});
		//注册数量
		}else if(sortFiled.equals("registerNum")){
			Collections.sort(channels,new Comparator<ChannelBean>() {
				@Override
				public int compare(ChannelBean o1, ChannelBean o2) {
					if(sortMod.equals("desc")) {
						return o1.getRegisterNum()<o2.getRegisterNum()?1:-1;
					}else{
						return o1.getRegisterNum()>o2.getRegisterNum()?1:-1;
					}
				}
			});
		//转换率
		}else if(sortFiled.equals("conversionRate")){
			Collections.sort(channels,new Comparator<ChannelBean>() {
				@Override
				public int compare(ChannelBean o1, ChannelBean o2) {
					if(sortMod.equals("desc")) {
						return o1.getConversionRate()<o2.getConversionRate()?1:-1;
					}else{
						return o1.getConversionRate()>o2.getConversionRate()?1:-1;
					}
				}
			});
		//增长率
		}else{
			Collections.sort(channels,new Comparator<ChannelBean>() {
				@Override
				public int compare(ChannelBean o1, ChannelBean o2) {
					if(sortMod.equals("desc")) {
						return o1.getGrowthRate()<o2.getGrowthRate()?1:-1;
					}else{
						return o1.getGrowthRate()>o2.getGrowthRate()?1:-1;
					}
				}
			});
		}
		
		return channels;
	}
	
	/**
	 * 压缩文件
	 * @param zip
	 * @param f
	 * @return
	 * @throws IOException
	 */
	public static String getZip(ZipOutputStream zip,File f){
		
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			zip.putNextEntry(new ZipEntry(f.getName()));
			//zip.setComment("hello");
			byte[] buf = new byte[2048];
			int len = 0;
			while(-1!=(len=in.read(buf))){
				zip.write(buf, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * xml字符串转json
	 * @return
	 */
	public static String xml2Json(String xml){
		XMLSerializer xmlSerializer = new XMLSerializer();
		JSON json = xmlSerializer.read(xml);
		String sJson = json.toString().replaceAll("@", "");
		return sJson;
	}
	
	/**
	 * 获得本周某天时间戳
	 * @param weekday
	 * @return
	 */
	public static long getTimeInMillis(int weekday){
		Calendar cal = Calendar.getInstance();
		//获取某天在本周是第几
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(dayOfWeek == 0) dayOfWeek = 7;
		//减完dayOfWeek,获得上周星期天+weekday，即为星期（weekday）
		cal.add(Calendar.DATE, weekday-dayOfWeek);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return cal.getTimeInMillis();
	}
	
	/**
	 * 获得路径为path的图片文件
	 * @param path
	 * @return
	 */
	public static String getUrlFile(String path){
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		InputStream is = null;
		OutputStream fos = null;
		BufferedImage bi = null;
		File f = null;
		try {
			URL url= new URL(path);
			String[] sPath = url.getFile().split("/");
			is = url.openStream();
			if(path.contains("@")){
				f = new File(contextPath+"/FileResource/"+sPath[sPath.length-1].split("@")[0]);
				path = path.split("@")[0];
			}else{
				f = new File(contextPath+"/FileResource/"+sPath[sPath.length-1]);
			}
			if(!f.exists()){
				bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
				ImageIO.write(bi, path.substring(path.lastIndexOf(".")-1), f);
			}
			fos = new FileOutputStream(f);
			byte[] buf = new byte[1024];
			int len = 0;
			while(-1 != (len=is.read(buf))){
				fos.write(buf,0,len);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
				fos.flush();
				fos.close();
				bi = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f.getPath();
	}
	
	/**
	 * get请求
	 */
	public static String requestGet(String uri){
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(uri);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
