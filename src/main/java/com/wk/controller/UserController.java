package com.wk.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.wk.entity.User;
import com.wk.service.UserService;
import com.wk.test.solrTest.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("用户管理")
@RestController
@SuppressWarnings("all")
@Scope("prototype")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userServiceImpl;
    @Autowired
    private CloudSolrClient cloudSolrClient;

    @ApiOperation(value = "获取用户详细信息",notes = "根据url的id来获取用户的详细信息")
    @ApiImplicitParam(name = "id",value = "用户ID",required = true,dataType ="Long",paramType = "path")
    @GetMapping("findUserById/{id}")
    public User findUserById(@PathVariable(value = "id") Integer id){
        return userServiceImpl.findUserById(id);
    }

    @ApiOperation(value = "获取全部用户列表",notes = "获取全部用户列表")
    @PostMapping("findAllUsers")
    public List<User> findAllUsers(){
        return userServiceImpl.findAllUsers();
    }

    @ApiOperation(value = "删除用户",notes = "根据url的id来删除用户")
    @ApiImplicitParam(name = "id",value = "用户ID",required = true,dataType ="Long",paramType = "path")
    @DeleteMapping(value = "deleteUserById/{id}")
    public Integer deleteUserById(@PathVariable(value = "id") Integer id){
        return userServiceImpl.deleteUserById(id);
    }

    @ApiOperation(value = "新建用户",notes = "根据user对象来创建用户")
    @ApiImplicitParam(name = "user",value = "用户",required = true,dataType ="User")
    @PostMapping(value = "insertUser")
    public Integer insertUser(@RequestBody User user){
        return userServiceImpl.insertUser(user);
    }

    @ApiOperation(value = "修改用户",notes = "根据user对象来修改用户")
    @ApiImplicitParam(name = "user",value = "用户",required = true,dataType ="User")
    @PostMapping(value = "updateUser")
    public Integer updateUser(@RequestBody User user){
        return  userServiceImpl.updateUser(user);
    }

    @ApiOperation(value = "分页条件查询用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currIndex",value = "当前页数",required = true,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示数",required = true,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "name",value = "姓名",dataType = "String",paramType = "query")
    })
    @PostMapping("findUsers")
    public List<User> findUsers( Integer currIndex, Integer pageSize,String name){
        Map map = new HashMap<>();
        map.put("name",name);
        PageHelper.startPage(currIndex,pageSize);
        List<User> users =  userServiceImpl.findUsers(map);
        return users;
    }

    @PostMapping("findSolr")
    public List<Book> findSolr(@RequestBody Map map) throws IOException, SolrServerException {
        String bookDescroption = (String) map.get("bookDescroption");
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("bookDescroption:"+bookDescroption);
        //高亮显示
        solrQuery.setHighlight(true);
        //设置高亮显示的域
        solrQuery.addHighlightField("bookDescroption");
        //高亮显示的前缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        //高亮显示的后缀
        solrQuery.setHighlightSimplePost("</font>");
        solrQuery.setFilterQueries("bookPrice:[10 TO 15]");
        solrQuery.setStart(0);
        solrQuery.setRows(20);
        solrQuery.addSort("id", SolrQuery.ORDER.asc);
        cloudSolrClient.setDefaultCollection("c2");
        QueryResponse queryResponse = cloudSolrClient.query(solrQuery);
        if(queryResponse!=null){
            List<Book> books = new ArrayList<>();
            SolrDocumentList solrDocuments = queryResponse.getResults();
            Map<String, Map<String, List<String>>> highLightMap = queryResponse.getHighlighting();
            for (SolrDocument solrDocument:solrDocuments){
                List<String> highLights = highLightMap.get(solrDocument.get("id")).get("bookDescroption");
                if(!CollectionUtils.isEmpty(highLights)){
                    solrDocument.setField("bookDescroption",highLights.get(0));
                    Book book = JSONObject.parseObject(JSONObject.toJSONString(solrDocument),Book.class);
                    books.add(book);
                }
            }
            //List<Book> books = queryResponse.getBeans(Book.class);
            return books;
        }
        return null;
    }
}
