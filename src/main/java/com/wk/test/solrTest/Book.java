package com.wk.test.solrTest;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class Book implements Serializable {

    @Id
    @Field
    private String id;
    @Field
    private String bookName;
    @Field
    private double bookPrice;
    @Field
    private String bookDescroption;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookDescroption() {
        return bookDescroption;
    }

    public void setBookDescroption(String bookDescroption) {
        this.bookDescroption = bookDescroption;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookPrice=" + bookPrice +
                ", bookDescroption='" + bookDescroption + '\'' +
                '}';
    }
}
