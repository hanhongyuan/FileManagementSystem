package com.changhong.data.upload.entity;

/**
 * Created by lenovo on 2017/3/30.
 */
public class PathVariableEntity {
    private String method;
    private String user;
    private String path;

    @Override
    public String toString() {
        return "pathVariableEntity{" +
                "method='" + method + '\'' +
                ", user='" + user + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
