package com.infinitus.bms_oa.oms.pojo;

import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 阿里请求附带得参数
 * @author hedaoqing
 *
 */
@Data
public class AliParam {
	/**
	 * 请求地址
	 */
	private String url;
	/**
	 * 
	 */
	private String appkey;
	/**
	 * 执行方法
	 */
	private String method;
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 内容格式
	 */
	private String format;
	/**
	 * 签名方法
	 */
	private String signmethod;
	/**
	 * 用户Id
	 */
	private String customerId;
	/**
	 * 请求时间
	 */
	private String timestamp;
	/**
	 * 签名串
	 */
	private String sign;
	/**
	 * 阿里中台提供得固定串
	 */
	private String secret;

	private String __url;

	private String __http_entry;

	
	public String getSignPrefix(){
		return secret+"app_key"+appkey+"customerId"+customerId+"format"+format+"method"+method+"sign_method"+signmethod+"timestamp"+timestamp+"v"+version;
	}
	
	public String getSignSuffix(){
		return secret;
	}
	
	public String getRequestSuffix(){
		try {
			return "app_key=" + appkey + "&customerId=" + customerId + "&format=" + format + "&method=" + method + "&sign_method=" + signmethod + "&timestamp=" + URLEncoder.encode(timestamp, "utf-8") + "&v=" + version + "&sign=" + sign ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
