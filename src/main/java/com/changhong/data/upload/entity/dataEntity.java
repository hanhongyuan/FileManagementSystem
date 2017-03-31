package com.changhong.data.upload.entity;

/**
 * Created by lenovo on 2017/3/30.
 */
public class dataEntity {
        private String filename;
        private byte[] fileBytes;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
}
