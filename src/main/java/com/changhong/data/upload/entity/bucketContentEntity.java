package com.changhong.data.upload.entity;

/**
 * Created by lenovo on 2017/4/5.
 */
public class bucketContentEntity {
    private String fileName;
    private long fileSize;
    private String modifyDate;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return "bucketContentEntity{" +
                "fileName='" + fileName + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                '}';
    }
}
