<%@ page contentType="text/html; charset=GBK" %>
<%@page import="java.net.URL"%>
<%@page import="promos.base.objects.*"%>
<%@page import="promos.com.base.*"%>

<html>
<head>
<title>&nbsp;Promos Development Studio</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>


<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%
String jump=request.getParameter("jump");
if (jump!=null) jump=jump.toString();
if (jump==null) jump="";

if (jump.equals("")) response.sendRedirect("/promos/core/CoreServlet.do?method=showFromCore&AppName=LOGIN_PROMOS");

if (jump.equals("1")) response.sendRedirect("/promos/core/MainServlet.do?method=showFromCore&JsRep=1&HtmlRep=1&AppName=BUSI_MI_REPORT_VIEW&ListDataWhere=(INFOTYPE%20in%20(1,2,4)%20and%20datediff(dd,SUBMITTIME,getdate())::0)&INFOTYPE=1&isday=0");


%>
</body>
</html>