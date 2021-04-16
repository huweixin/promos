<%@ page contentType="text/html; charset=GBK" %>
<%@page import="promos.base.objects.*"%>
<html>
<head>
<title>&nbsp;Promos Development Studio</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="css/style.css" rel="stylesheet" type="text/css">
<script language=javascript src="js/publicfunction.js"></script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%
 	String web_html=request.getAttribute(Constants.WEB_HTML_STRING).toString();
	request.setAttribute(Constants.WEB_HTML_STRING,"");

	Object web_page_fix=request.getAttribute(Constants.WEB_PAGE_FIX);
	if (web_page_fix!=null){
		web_page_fix=web_page_fix.toString();
	}
	else
	{
		web_page_fix="0";
	}
	
	if (web_page_fix.equals("1")==true)
	{
		Object swebh=request.getAttribute(Constants.WEB_PAGE_HEIGHT);
		Object swebw=request.getAttribute(Constants.WEB_PAGE_WIDTH);
		if ((swebw!=null)||(swebh!=null))
		{
			if (swebw==null) swebw="0";
			if (swebh==null) swebh="0";
			
			int web_height=Integer.valueOf(swebh.toString()).intValue();
			int web_width=Integer.valueOf(swebw.toString()).intValue();
				web_html="<div style=\"position:fixed;_position:absolute;z-index:1;top:50%;left:50%;margin:-"+(web_height/2)+"px 0 0 -"+(web_width/2)+"px;width:"+web_width+"px;height:"+web_height+"px;border:0px;line-height:0px;text-align:center;overflow:hidden;\">\n"+web_html+"\n</div>";
		}
	}
	
%>

<%=web_html%>
 
</body>

<%
	web_html="";
%>

<style type="text/css">
.x-grid3-hd-row TD {
font-size: 12px;/*改变表头字体大小*/
}
.x-grid3-row TD {
font-size: 12px;/*改变行内容字体大小*/
LINE-HEIGHT: 8px;
}
</style>

</html>


