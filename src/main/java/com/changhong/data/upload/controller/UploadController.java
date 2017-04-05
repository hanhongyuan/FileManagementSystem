package com.changhong.data.upload.controller;

import com.changhong.data.upload.entity.SingleuploadResponse;
import com.changhong.data.upload.service.MD5SignatureService;
import com.changhong.data.upload.service.QueryStringAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lenovo on 2017/3/30
 */
@Controller
@RequestMapping("/file")
@ResponseBody
public class UploadController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private QueryStringAnalysisService queryStringAnalysisService;
    @Autowired
    private MD5SignatureService md5SignatureService;
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public SingleuploadResponse upload(HttpServletRequest request) throws IllegalStateException, IOException {
        Map<String,String> params=new HashMap<String, String>();
        SingleuploadResponse singleuploadResponse=new SingleuploadResponse();
        try{
        params=queryStringAnalysisService.analysis(request);
        System.out.println("+++");
        }catch (RuntimeException e){
                e.printStackTrace();
                singleuploadResponse.setCode("415");
        }
       long startTime=System.currentTimeMillis();
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request)){
            //将request变成多部分request
        MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
        Iterator iter=multiRequest.getFileNames();
        System.out.println(iter.next().toString());
        String filename=null;
        String saveSql="insert into uploadfiles(filename,path,user,method,ctime,data) values(?,?,?,?,?,?)";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        int count=0;
        int num=0;
        while(iter.hasNext())
            {
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                System.out.println(file.getSize());
                if(file!=null)
                {
                    count++;
                    filename=file.getOriginalFilename();
                    System.out.println(filename);
                    byte[] fileBytes=file.getBytes();
                    String MD5=md5SignatureService.getSignature(fileBytes);
                    String ctime=sdf.format(date);
                    Object[] param={filename,params.get("path")+"/"+filename,params.get("user"),params.get("method"),ctime,fileBytes};
                     num+=jdbcTemplate.update(saveSql,param);
                    singleuploadResponse.setCtime(ctime);
                    singleuploadResponse.setPath(params.get("path")+"/"+filename);
                    singleuploadResponse.setMd5(MD5);
                    if(count==num){
                        singleuploadResponse.setCode("200");
                        return singleuploadResponse;
                    }
                    else{
                        singleuploadResponse.setCode("415");

                        return singleuploadResponse;
                    }

                }

            }

        }
        long  endTime=System.currentTimeMillis();
        System.out.println("上传运行时间："+String.valueOf(endTime-startTime)+"ms");
        System.out.println(singleuploadResponse);
        return singleuploadResponse;
    }


}
