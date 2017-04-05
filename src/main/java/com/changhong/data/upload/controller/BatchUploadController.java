package com.changhong.data.upload.controller;

import com.changhong.data.upload.entity.batchUploadResponse;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/4/5.
 */
@Controller
@RequestMapping("/file")
@ResponseBody
public class BatchUploadController {
    @Autowired
    private QueryStringAnalysisService queryStringAnalysisService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MD5SignatureService md5SignatureService;
    @RequestMapping(value = "/batchupload", method = RequestMethod.POST)
    public batchUploadResponse batchUpload(HttpServletRequest request) throws IOException {
        Map<String,String> params=new HashMap<String, String>();
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String sql="insert into uploadfiles(filename,path,user,method,ctime,data) values(?,?,?,?,?,?)";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        String ctime=sdf.format(date);
        System.out.println(ctime);
        MultipartFile file = null;
        String fileName=null;
        String MD5=null;
        params=queryStringAnalysisService.analysis(request);
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    fileName=file.getOriginalFilename();
                    MD5=md5SignatureService.getSignature(bytes);//获取文件的MD5签名
                    System.out.println(fileName);
                    System.out.println(MD5);
                    Object[] param={fileName,params.get("path")+"/"+fileName,params.get("user"),params.get("method"),
                    ctime,bytes};
                    jdbcTemplate.update(sql,param);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
            }

        }
        return null;
    }
}



