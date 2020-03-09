<%--
  Created by IntelliJ IDEA.
  User: 14246
  Date: 2019/3/15
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${now}
<form action="/springboot/user/findUsers" method="post">
    <input type="text" name="currIndex" />
    <input type="text" name="pageSize" />
    <input type="text" name="name" />
    <input type="submit" />
</form>
</body>
</html>
