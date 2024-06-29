package com.xidian.carpool.wscore;

import cn.hutool.json.JSONObject;
import org.springframework.web.socket.WebSocketSession;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class MessageDispatcher {

	private Map<String, Object> wsBeans = new HashMap();
	private Map<String, Method> wsMethods = new HashMap();


	public void addMethod(String url, Object bean, Method method){
		wsBeans.put(url, bean);
		wsMethods.put(url, method);
	}

	/**
	 * 接口分发
	 * @param url
	 * @param parameter
	 * @param session
	 * @return
	 */
	public Object dispatch(String url, Map parameter, WebSocketSession session){

		Object bean = wsBeans.get(url);
		Method method = wsMethods.get(url);
		if(bean != null && method != null){
			try{
				Object[] args = new Object[method.getParameterCount()];
				Class<?>[] argTypes = method.getParameterTypes();
				//按类型进行参数匹配，目前可自动传入WebSocketSession、Map(cn.hutool.json.JSONObject)类型参数
				//不匹配参数则为null
				for(int i=0; i<args.length; i++){
					if(argTypes[i].equals(Map.class) || argTypes[i].equals(JSONObject.class)){
						args[i] = parameter;
					}
					else if(argTypes[i].equals(WebSocketSession.class)){
						args[i] = session;
					}
				}

				if(args.length == 0){
					return method.invoke(bean);
				}
				else{
					return method.invoke(bean, args);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}


	public JSONObject dispatch(JSONObject json, WebSocketSession session){
		Object response = dispatch(String.valueOf(json.get("url")), json.getJSONObject("params"), session);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("data", response);
		return jsonObj;
	}
}
