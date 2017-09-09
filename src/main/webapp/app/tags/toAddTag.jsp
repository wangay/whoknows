<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>pay</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <script src="/bower_components/jquery/dist/jquery.js"></script>
    <script src="/app/tags/toAddTag.js"></script>
</head>

<body>

<form action="/tag/addTag" method="post">

    <h3>使用说明：下面可以支持增加多个tag只要用,隔开，并且name跟rank一一对应</h3><br/>
    <h3>比如 技术,tor  30,30</h3><br/>
    标签名称：<input type="text" id="name" value=""/></br>
标签rank：<input type="text" id="rank" value="30"/></br>
    <input type="button" id="sbutton" value="增加"/>
</form>
</body>
</html>
