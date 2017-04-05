package com.changhong.data.upload.service;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhang on 2017/4/1.
 * 该类用于为文件生成签名，检测文件是否存在(包括文件名和文件内容的对比)
 */
@Service
public class MD5SignatureService {
    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',  '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    protected static MessageDigest messagedigest = null;
    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("MD5SignatureService messagedigest初始化失败");
            e.printStackTrace();
        }
    }
    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            char c0 = hexDigits[(bytes[l] & 0xf0) >> 4];
            char c1 = hexDigits[bytes[l] & 0xf];
            stringbuffer.append(c0);
            stringbuffer.append(c1);
        }
        return stringbuffer.toString();
    }
    public String getSignature(byte[] fileData){//获取文件内容的MD5签名
        messagedigest.update(fileData);
        String fileSignature=bufferToHex(messagedigest.digest());
        return fileSignature;
    }
    public boolean checkSignature(String Signature){
        String[] signatureArr=Signature.split("/");
        String filename=signatureArr[0];
        String MD5Signature=signatureArr[1];
        String  sql="";                            //数据库查询
       /* if(){

        }else{

        }*/
        return false;
    }
}
