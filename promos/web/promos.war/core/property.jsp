<%@ page contentType="text/html; charset=GBK" %>
<%@page import="promos.base.objects.*"%>
<html>

<head>
<style type="text/css">
.noneside {
	border-top: 0px none;
	border-right: 0px none;
	border-bottom: 0px none;
	border-left: 0px none;
}
.select-style{
  overflow:hidden;
}
.select-style select{
 margin:-2px;
}
table{
	font-size: 12px;
	font-family: "Verdana", "宋体";
}
</style>	
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title></title>
    <link rel="stylesheet" type="text/css" href="Ext/2.1/resources/css/ext-all.css" />
    <script type="text/javascript" src="Ext/2.1/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="Ext/2.1/ext-all.js"></script>
    <script language=javascript src="js/publicfunction.js"></script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="#C7D6E9">
<%=request.getAttribute(Constants.WEB_HTML_STRING).toString()%>


<script type="text/javascript">
	function comvalue_set(item){
		var sid=item.id;
		sid=sid.substring(3,sid.length);
	  window.open("/promos/core/CoreServlet.do?method=showFromCore&AppName=CORE_COM_VALUE_SET&COM_ID="+sid,'属性值编辑','left=500,top=210px,width=456, height=342,toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no,alwaysRaised=yes');
	}
	function SelectField(item){
		var sid=item.id;
		sid=sid.substring(3,sid.length);
	  window.open("/promos/core/CoreServlet.do?method=showFromCore&AppName=PROMOS_FIELD_SELECT&GetDataWhere=1::1&DesignAppName=<%=request.getParameter("DesignAppName")%>&&FVC_HTMLID=<%=request.getParameter("FVC_HTMLID")%>&COM_ID="+sid,'选择字段名','left=500,top=210px,width=500, height=400,toolbar=no, menubar=no, scrollbars=no,resizable=yes,location=no, status=no,alwaysRaised=yes');
	}
	

function jh_ok()
{
  MyForm.action="/promos/core/DesignServlet.do?method=saveComProperty&AppName=<%=request.getParameter("AppName")%>&WebAct=EDIT&DesignAppName=<%=request.getParameter("DesignAppName")%>&FVC_HTMLID=<%=request.getParameter("FVC_HTMLID")%>";
  if (checkcomvalue()) MyForm.submit();
}
</script>
</body>
<%
request.setAttribute(Constants.WEB_HTML_STRING,"");
%>
</html>