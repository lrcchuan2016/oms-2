package cn.broadin.oms.util;


/**
 * 常量定义
 * @author xiejun
 */
public abstract class OSSConst {
	/**
	 * 模版图片上传key头
	 */
	public static final String TEMPLATE_KEY_HEAD = "template/";
	
	/**
	 * 模版组图标上传key头
	 */
	public static final String TEMPLATE_GROUP_KEY_HEAD = "templateGroup/";
	
	/**
	 * 装饰品图标上传key头
	 */
	public static final String DECORATE_KEY_HEAD = "decorate/";
	
	/**
	 * 装饰品组图标上传key头
	 */
	public static final String DECORATE_KEY_GROUP_HEAD = "decorateGroup/";
	
	/**
	 * 音频媒资文件上传key头
	 */
	public static final String MEDIA_MUSIC_KEY_HEAD = "music/";
	
	/**
	 * 视频媒资文件上传key头
	 */
	public static final String MEDIA_VIDEO_KEY_HEAD = "video/";
	
	/**
	 * 普通媒资文本上传key头
	 */
	public static final String MEDIA_COMM_TEXT_KEY_HEAD = "commonText/";
	
	/**
	 * xml媒资文本上传key头
	 */
	public static final String MEDIA_XML_TEXT_KEY_HEAD = "xmlText/";
	
	/**
	 * 照片媒资文件上传key头
	 */
	public static final String MEDIA_PHOTO_KEY_HEAD = "photo/";
	
	/**
	 * 渠道图标文件上传key头
	 */
	public static final String CHANNEL_ICON_KEY_HEAD = "channel/";
	
	/**
	 * 广告(图片/视频)文件上传Key头
	 */
	public static final String AD_RESOURCE_KEY_HEAD = "adResource/";
	
	/**
	 * 版本文件上传Key头
	 */
	public static final String VERSION_KEY_HEAD = "version/";
	/**
	 * 版本截图上传头
	 */
	public static final String VERSION_SCREENSHOT_KEY_HEAD = "versionShot/";
	/**
	 * typeKey为空的时候，把上传的图片传到这个文件夹下
	 */
	public static final String TYPE_KEY_NULL_HEAD = "resource/";
	/**
	 * 阿里云OSS开放存储服务可编辑存储图片接口-域名
	 */
	public static final String ALIYUN_IMAGE_DOMAIN = "http://cdn.meijx.cn/";
	
	/**
	 * 阿里云OSS开放存储服务可编辑存储其它资源接口
	 */
	public static final String ALIYUN_OTHER_DOMAIN = "http://res.meijx.cn/";
}
