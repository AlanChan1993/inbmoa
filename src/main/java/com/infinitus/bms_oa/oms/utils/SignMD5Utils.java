package com.infinitus.bms_oa.oms.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;


public class SignMD5Utils {
	public static void main(String[] args) throws IOException {
		Long tim =  System.currentTimeMillis();
		//String s = "77276a72575bf549d1211e1b8bc2219aapp_key23152314customerIdTestINFformatxmlmethodsingleitem.synchronizesign_methodmd5timestamp2021-03-02 16:23:30v2.0<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><request><actionType>update</actionType><warehouseCode>JXXYPPMT</warehouseCode><ownerCode>2207292794438</ownerCode><supplierCode></supplierCode><supplierName></supplierName><item><adventLifecycle>0</adventLifecycle><approvalNumber></approvalNumber><barCode>200400155;6989820040013</barCode><batchCode></batchCode><batchRemark></batchRemark><brandCode>0036</brandCode><brandName>Kennyswork</brandName><categoryId>080201</categoryId><categoryName>陈列样品</categoryName><color></color><costPrice>0</costPrice><createTime></createTime><englishName></englishName><expireDate></expireDate><extendProps><codeTs></codeTs><itemRecordNo></itemRecordNo><originCountry></originCountry><originCountryCode></originCountryCode><stockUnitCode></stockUnitCode></extendProps><fLRatio>Y</fLRatio><fLUnit>Y</fLUnit><goodsCode>200400155</goodsCode><height>0</height><isBatchMgmt>N</isBatchMgmt><isFragile>N</isFragile><isHazardous>N</isHazardous><isSNMgmt>N</isSNMgmt><isShelfLifeMgmt>N</isShelfLifeMgmt><isSku>Y</isSku><isValid>Y</isValid><itemCode>200400155</itemCode><itemId></itemId><itemName>Molly蒸汽朋克系列陈列品14只装</itemName><itemType>ZC</itemType><length>0</length><lockupLifecycle>0</lockupLifecycle><originAddress></originAddress><packCode></packCode><packageMaterial></packageMaterial><pcs>1</pcs><pricingCategory></pricingCategory><productDate></productDate><purchasePrice>218.68</purchasePrice><rejectLifecycle>0</rejectLifecycle><remark>批量导入</remark><retailPrice>966</retailPrice><safetyStock>0</safetyStock><seasonCode></seasonCode><seasonName></seasonName><shelfLife>0</shelfLife><shortName>Molly蒸汽朋克系列</shortName><size></size><skuProperty>XXL</skuProperty><sLRatio>Y</sLRatio><sLUnit>Y</sLUnit><stockUnit>个</stockUnit><title></title><updateTime></updateTime><volume>0</volume><width>0</width></item></request>77276a72575bf549d1211e1b8bc2219a";
		String s = "ALREMOVEODCESHITOKEN[{\"EXTERKEY\":\"CESHI\",\"LINE\":\"CESHI1\",\"WERKS\":\"HC35\",\"STORERKEY\":\"INF\"}]1620616437800";
		
		String ss =SignMD5Utils.byte2hex(encryptMd5(s));
		System.out.println(tim);
		System.out.println(ss);
	}
	private static final String MD5 = "MD5";

    /**
     * 给TOP请求签名。
     *
     * @param params     所有字符型的TOP请求参数
     * @param secret     签名密钥
     * @param signMethod signMethod 签名方法，目前支持：空（老md5)、md5, hmac_md5三种
     * @return 签名
     */
    public static String signTopRequest(Map<String, String> params, String secret, String signMethod)
            throws IOException {
        return signTopRequest(params, null, secret, signMethod);
    }

    /**
     * 给TOP请求签名。
     *
     * @param params     所有字符型的TOP请求参数
     * @param body       请求主体内容
     * @param secret     签名密钥
     * @param signMethod 签名方法，目前支持：空（老md5)、md5, hmac_md5三种
     * @return 签名
     */
    public static String signTopRequest(Map<String, String> params, String body, String secret, String signMethod)
            throws IOException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        if (MD5.equals(signMethod)) {
            query.append(secret);
        }
        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                query.append(key).append(value);
            }
        }

        // 第三步：把请求主体拼接在参数后面
        if (body != null) {
            query.append(body);
        }

        // 第四步：使用MD5加密
        query.append(secret);
        byte[] bytes = encryptMd5(query.toString());

        // 第五步：把二进制转化为大写的十六进制
        return byte2hex(bytes);
    }

    /**
     * 把字节流转换为十六进制表示方式
     *
     * @param bytes
     * @return
     */
    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    /**
     * 给TOP带body体的请求签名，适用于TOP批量API和奇门API的请求签名
     *
     * @param params 所有字符型的TOP请求参数
     * @param body   请求body体
     * @param secret 签名密钥
     * @return 签名
     */
    public static String signTopRequestWithBody(Map<String, String> params, String body, String secret,
                                                String signMethod) throws IOException {
        return signTopRequest(params, body, secret, signMethod);
    }

    /**
     * 对字符串采用UTF-8编码后，用MD5进行摘要
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] encryptMd5(String data) throws IOException {
        return encryptMd5(data.getBytes("utf-8"));
    }

    /**
     * 对字节流进行MD5摘要。
     */
    public static byte[] encryptMd5(byte[] data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            bytes = md.digest(data);
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }
}
