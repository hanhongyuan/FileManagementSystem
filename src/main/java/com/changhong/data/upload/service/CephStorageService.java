package com.changhong.data.upload.service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.StringUtils;
import com.changhong.data.upload.entity.bucketContentEntity;
import com.changhong.data.upload.entity.bucketEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/4/5.
 */
@Service
public class CephStorageService {

    protected static  AmazonS3 amazonS3=null;
    static{
        String accessKey = "";
        String secretKey = "";
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        amazonS3=new AmazonS3Client(credentials,clientConfig);
        amazonS3.setEndpoint("192.168.1.248:7248");//This creates a connection so that you can interact with the server.
    }

    /**
     * 创建目录
     * @param bucketname
     * @return
     */
    public String createBucket(String bucketname){
        Bucket bucket =amazonS3.createBucket(bucketname);
        System.out.println(amazonS3.toString());
        return  bucketname;
    }

    /**
     * 查询已经创建的目录
     * @return
     */
    public List<bucketEntity> getBuckets(){
        List<bucketEntity> list =new ArrayList<bucketEntity>();
        bucketEntity bucketEntity=new bucketEntity();
        List<Bucket> buckets = amazonS3.listBuckets();
        for (Bucket bucket : buckets) {

            bucketEntity.setBucketName(bucket.getName());
            bucketEntity.setCreationDate(StringUtils.fromDate(bucket.getCreationDate()));
            list.add(bucketEntity);
            System.out.println(bucket.getName() + "\t" +
                    StringUtils.fromDate(bucket.getCreationDate()));
        }
        return list;
    }

    /**
     *根据指定的目录查询文件
     * @param bucketName
     * @return
     */
    public  List<bucketContentEntity> getFiles(String bucketName){
        bucketContentEntity bucketContentEntity=new bucketContentEntity();
        List<bucketContentEntity> list=new ArrayList<bucketContentEntity>();
        ObjectListing objects = amazonS3.listObjects(bucketName);
        do {
            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                    bucketContentEntity.setFileName(objectSummary.getKey());
                    bucketContentEntity.setFileSize(objectSummary.getSize());
                    bucketContentEntity.setModifyDate(StringUtils.fromDate(objectSummary.getLastModified()));
                    list.add(bucketContentEntity);
                System.out.println(objectSummary.getKey() + "\t" +
                        objectSummary.getSize() + "\t" +
                        StringUtils.fromDate(objectSummary.getLastModified()));
            }
            objects = amazonS3.listNextBatchOfObjects(objects);
        } while (objects.isTruncated());
        return list;
    }

    /**
     * 单条数据的存储
     * @param path
     * @param data
     * @return
     */
    public boolean saveData(String path,byte[] data){
        String[] paths=path.split("/");
        String bucketName="";
        PutObjectResult putObjectResult=null;
        for(int i=0;i<paths.length-1;i++){
            bucketName+=paths[i]+"/";
        }
        String fileName=paths[paths.length-1];
        try{
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        putObjectResult=amazonS3.putObject(bucketName,fileName , input, new ObjectMetadata());
        return putObjectResult.isRequesterCharged();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("写入ceph时出错");
        }
    }
}
