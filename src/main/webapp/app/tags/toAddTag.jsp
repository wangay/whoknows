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

    <h3>ʹ��˵�����������֧�����Ӷ��tagֻҪ��,����������name��rankһһ��Ӧ</h3><br/>
    <h3>���� ����,tor  30,30</h3><br/>
    ��ǩ���ƣ�<input type="text" id="name" value=""/></br>
��ǩrank��<input type="text" id="rank" value="30"/></br>
    <input type="button" id="sbutton" value="����"/>
</form>
</body>
</html>
