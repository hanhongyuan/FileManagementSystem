package com.changhong.data.upload.service;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/3/31.
 */
@Service
public class QueryStringAnalysisService {
    public Map<String,String> analysis(HttpServletRequest request){
        HttpRequest reques=null;
        String query=request.getQueryString();
        Map<String,String> params=new HashMap<String, String>();
        if(query==null){
            throw new RuntimeException("Query String Can Not Be Empty");
        }else{
            try {
                String[] querys = query.split("&");
                for (int i = 0; i < querys.length; i++) {
                    String[] string = querys[i].split("=");
                    params.put(string[string.length - 2], string[string.length - 1]);
                    System.out.println("querys:" + querys[i]);
                }
                System.out.println(params.get("method"));
                System.out.println(params.get("user"));
                System.out.println(params.get("path"));
            }catch(ArrayIndexOutOfBoundsException e){
                throw new RuntimeException("query String format exception");
            }
            if(params.containsValue(null)||params.containsKey(null)){
                throw new RuntimeException("QueryString parameters are required");
            }
            return params;
        }


    }
}
