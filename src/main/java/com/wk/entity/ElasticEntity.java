package com.wk.entity;

/**
 * 泛型，es入参实体类
 * @param <T>
 */
public class ElasticEntity<T> {
    private String id;
    private T data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
