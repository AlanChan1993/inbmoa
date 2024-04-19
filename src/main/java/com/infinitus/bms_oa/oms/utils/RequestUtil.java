package com.infinitus.bms_oa.oms.utils;

import com.alibaba.fastjson.JSON;
import com.infinitus.bms_oa.oms.excetion.BMSException;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Component
public class RequestUtil {
	public String doPost(String urlparma,Map<String,Object> map){
		OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;
        try{
            URL url = new URL(urlparma);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            //发送POST请求必须设置为true
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            //获取输出流
            out = new OutputStreamWriter(conn.getOutputStream());
            String jsonStr = JSON.toJSONString(map);
            out.write(jsonStr);
            out.flush();
            out.close();
            //取得输入流，并使用Reader读取
            if (200 == conn.getResponseCode()){
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;
                while ((line = in.readLine()) != null){
                    result.append(line);
                }
            }else{
                System.out.println("ResponseCode is an error code:" + conn.getResponseCode()+"，消息:"+conn.getResponseMessage());
                throw new BMSException("请求错误:代码-"+conn.getResponseCode()+"消息:"+conn.getResponseMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BMSException(e.getMessage());
        }finally {
            try{
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
        return result.toString();
	}
	
	public String doPost(String url,String param,String username,String password,String bj){
		BufferedReader br = null;
		InputStream in = null;
		String result = "";
		try {
			HttpClient client = HttpClients.createDefault();
			HttpPost request = new HttpPost(url);
			request.setHeader("Content-type", "text/xml;charset=utf-8");
			UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
			request.addHeader(new BasicScheme().authenticate(creds, request, null));
			ByteArrayEntity entity = new ByteArrayEntity(param.getBytes("UTF-8"));
			request.setEntity(entity);
			HttpResponse response = client.execute(request);
			in = response.getEntity().getContent();
			br = new BufferedReader(new InputStreamReader(
					in));
		    String lines;
		    while ((lines = br.readLine()) != null) {
		        lines = new String(lines.getBytes(), "utf-8");
		        result+=lines;
		    }
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
