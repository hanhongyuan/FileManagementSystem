package com.changhong.data.upload.controller;

import com.changhong.data.upload.entity.dataEntity;
import com.changhong.data.upload.service.QueryStringAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/4/6.
 */
@Controller
@RequestMapping("/file")
@ResponseBody
public class DownloadnController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private QueryStringAnalysisService queryStringAnalysisService;
    @RequestMapping("/download")
    public File downloadFile(HttpServletRequest request){
        Map<String,String> params=new HashMap<String, String>();
        params=queryStringAnalysisService.analysis(request);
        String sql="select filename,data from uploadfiles where path=? and user=?";
        dataEntity dataEntity=new dataEntity();
        Object[] param={params.get("path"),params.get("user")};
        byte[] bytes=null;
        List<Map<String,Object>> items=jdbcTemplate.queryForList(sql,param);

        for(int i=0;i<items.size();i++){
            dataEntity.setFilename((String)items.get(i).get("filename"));
            dataEntity.setFileBytes((byte[])items.get(i).get("data"));
        }
        File newfile=this.BetyToFile(dataEntity.getFilename(),dataEntity.getFileBytes());

        return newfile;
    }

    public  File BetyToFile( String filename,byte[] bytes)
    {
        File file = new File(filename);
        BufferedOutputStream stream = null;
        FileOutputStream fstream = null;

        try {
            fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
                if (null != fstream) {
                    fstream.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return file;
    }

}

