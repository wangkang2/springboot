package com.wk.test.solrTest;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SolrTest {


//    @Autowired
//    private SolrClient solrClient;
    @Autowired
    private CloudSolrClient cloudSolrClient;

    @Test
    public void addBook(){

        //此次默认使用的一个地址的集合，若有多个，则在代码中
        //HttpSolrClient solrClient = new HttpSolrClient.Builder("http://127.0.0.1:8983/solr/myCore/").build();

        Book book = new Book();
        book.setId("2");
        book.setBookName("solr从入门到放弃");
        book.setBookPrice(14.00);
        book.setBookDescroption("成都人民出版社出版的关于solr开发的书籍");
        try {
            cloudSolrClient.setDefaultCollection("c2");
            cloudSolrClient.addBean(book,10000);
            cloudSolrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateBook(){
        //此次默认使用的一个地址的集合，若有多个，则在代码中
        //HttpSolrClient solrClient = new HttpSolrClient.Builder("http://127.0.0.1:8983/solr/myCore/").build();

        Book book = new Book();
        book.setId("1");
        //book.setBookName("solr其实还挺好学");
        book.setBookPrice(13.00);
        try {
            cloudSolrClient.setDefaultCollection("c2");
            cloudSolrClient.addBean(book,10000);
            cloudSolrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deteleBook(){
        try {
            cloudSolrClient.setDefaultCollection("c2");
            //cloudSolrClient.deleteByQuery("bookName:solr从入门到放弃");
            cloudSolrClient.deleteById("2");
            cloudSolrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryBook(){
        try {
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.setQuery("*:*");
            solrQuery.setFilterQueries("bookPrice:[10 TO 15]");
            solrQuery.setStart(0);
            solrQuery.setRows(20);
            solrQuery.addSort("id", SolrQuery.ORDER.asc);
            cloudSolrClient.setDefaultCollection("c2");
            QueryResponse queryResponse = cloudSolrClient.query(solrQuery);
            if(queryResponse!=null){
                List<Book> books = queryResponse.getBeans(Book.class);
                System.out.println(books);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
