package com.changhong.data.upload.controller;

import com.changhong.data.upload.entity.BatchUploadResponse;
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
import java.util.*;

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
    public List<BatchUploadResponse> batchUpload(HttpServletRequest request) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String set = "set global max_allowed_packet = 2*1024*1024*1024*1024*10";
        jdbcTemplate.execute(set);
        List<BatchUploadResponse> list = new ArrayList<BatchUploadResponse>();
        String sql = "insert into uploadfiles(filename,path,user,method,ctime,data) values(?,?,?,?,?,?)";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        MultipartFile file =null;
        int num=0;
        for(int j=0;j<files.size();++j){
            file=files.get(j);
            if(!file.isEmpty()){
                num+=1;
            }
        }
        String MD5 = null;
        params = queryStringAnalysisService.analysis(request);
        int q = 0;

        if(num>=1) {
            for (int i = 0; i < files.size(); ++i) {
                file = files.get(i);
                if (!file.isEmpty()) {
                    try {
                        byte[] bytes = file.getBytes();
                        String fileName = file.getOriginalFilename();
                        BatchUploadResponse batchUploadResponse = new BatchUploadResponse();
                       batchUploadResponse.setFilename(fileName);//响应的当前文件
                        date = new Date(System.currentTimeMillis());
                        String ctime = sdf.format(date);
                        batchUploadResponse.setCtime(ctime);//响应的创建时间
                        MD5 = md5SignatureService.getSignature(bytes);//获取文件的MD5签名
                        batchUploadResponse.setMD5(MD5);//响应当前文件的MD5
                        System.out.println("文件名：" + fileName);
                        System.out.println("生成的MD5:" + MD5);
                        batchUploadResponse.setPath(params.get("path") + "/" + fileName);//响应当前文件存储路径
                        Object[] param = {fileName, params.get("path") + "/" + fileName, params.get("user"), params.get("method"),
                                ctime, bytes};
                        q = jdbcTemplate.update(sql, param);
                        if (q == 1) {
                            batchUploadResponse.setCode("200");
                        } else {
                            batchUploadResponse.setCode("415");
                        }
                       list.add(batchUploadResponse);
                        for(int x=0;x<list.size();x++){
                            System.out.println(list.get(x));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            for(int x=0;x<list.size();x++){
                System.out.println(list.get(x));
            }
                return list;
        }else{
            throw new RuntimeException("Please upload at least one file!");
        }
    }
}