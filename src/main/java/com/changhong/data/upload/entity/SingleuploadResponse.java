package com.changhong.data.upload.entity;

/**
 * Created by lenovo on 2017/3/31.
 */
public class SingleuploadResponse {
    private String path;
    private String size;
    private String uuid;
    private String ctime;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        return "SingleuploadResponse{" +
                "path='" + path + '\'' +
                ", size='" + size + '\'' +
                ", uuid='" + uuid + '\'' +
                ", ctime='" + ctime + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
