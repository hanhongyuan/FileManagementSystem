package com.changhong.data.upload.entity;

/**
 * Created by lenovo on 2017/3/31.
 */
public class SingleuploadResponse {
    private String path;
    private String size;
    private String md5;
    private String ctime;
    private String code;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SingleuploadResponse{" +
                "path='" + path + '\'' +
                ", size='" + size + '\'' +
                ", md5='" + md5 + '\'' +
                ", ctime='" + ctime + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}