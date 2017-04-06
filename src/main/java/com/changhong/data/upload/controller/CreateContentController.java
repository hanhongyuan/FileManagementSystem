package com.changhong.data.upload.controller;

import com.changhong.data.upload.service.CephStorageService;
import com.changhong.data.upload.service.QueryStringAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/3/31.
 */
@Controller
@RequestMapping("/file")
@ResponseBody
public class CreateContentController {
    @Autowired
    private QueryStringAnalysisService queryStringAnalysisService;
    @Autowired
    private CephStorageService cephStorageService;
    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public void createContent(HttpServletRequest request){
        Map<String,String> params=new HashMap<String, String>();
        params=queryStringAnalysisService.analysis(request);
        String bucketname=params.get("bucketname");
        cephStorageService.createBucket(bucketname);
    }



}
