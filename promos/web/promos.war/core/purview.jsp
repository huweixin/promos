<%@ page contentType="text/html; charset=GBK" %>
<%@page import="promos.base.objects.*"%>
<html>
	<%response.setContentType("text/html; charset=GBK");%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>表单元素权限设定</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<script language=javascript src="js/publicfunction.js"></script>
<link rel="stylesheet" type="text/css" href="Ext/2.1/resources/css/ext-all.css" />
<script type="text/javascript" src="Ext/2.1/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="Ext/2.1/ext-all.js"></script>


</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<div id="pro-dlg" class="x-hidden" style="z-index:100;">
    <div class="x-window-header"></div>
    <div id="pro-bd">
       <IFRAME ID=Purviewframe scrolling='no' style='border: 0px none #000000;height:100%;width:100%;' src=""></IFRAME>   
    </div>
</div>


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
	
	String strRETURN_MSG="";
	if (request.getAttribute("RETURN_MSG")!=null)
	{
		strRETURN_MSG=request.getAttribute("RETURN_MSG").toString();
	}
%>

<%=web_html%>
 
</body>

<script language="JavaScript" type="text/JavaScript">

   <%=strRETURN_MSG%>
	
   var oldobj;
   
   function drags(item){
	  if ((event)&&(event.button==0))
	  {

	      if (oldobj)  oldobj.style.background="";
	      if (oldobj)  oldobj.style.border="1px";

	      oldobj=item;
	      
	      if (item)  item.style.background="WhiteSmoke";
	      if (item)  item.style.border="1px dashed";

	  }
    }   
   
	var dialog;
	function setcompurview(item,appname,htmlname,htmlid,)
	{
	   if(!dialog){ // lazy initialize the dialog and only create it once
	     dialog = new Ext.Window({el:"pro-dlg", 
	                        width:1000,
	                        height:500,
	                        shadow:true,
	                        frame:true,
	                        minWidth:300,
	                        minHeight:200,
	                        resizable:false,
	                        constrainHeader:false,
	                        items: new Ext.TabPanel({
	                            el: 'pro-bd',
	                            border:false
	                        }),
	                                
	                        buttons: [{
	                            text: '返回',
	                            handler: pro_close
	                        }]
	                        
	             });
	    }
	    Purviewframe.src="/promos/core/CoreServlet.do?method=showFromCore&AppName=P_COM_ROLE_LIST&GetDataWhere=FNB_COMORACT::1 and FVC_APPNAME='"+appname+"' and FVC_HTMLNAME='"+htmlname+"'&dbuser=<%=request.getParameter("dbuser").toString()%>&sel_appname="+appname+"&sel_htmlname="+htmlname;
	    dialog.show();
	}

	function pro_ok()
	{
		var mb = myBrowser();

		if ("IE" == mb) {
		    document.getElementById("Purviewframe").jh_ok();
		}
		else
		if ("FF" == mb) {
		    document.getElementById("Purviewframe").jh_ok();
		}
		else
		if ("Chrome" == mb) {
		    document.getElementById("Purviewframe").contentWindow.jh_ok();
		}
		else
		if ("Opera" == mb) {
		    document.getElementById("Purviewframe").jh_ok();
		}
		else
		if ("Safari" == mb) {
		    document.getElementById("Purviewframe").jh_ok();
		}
		else
		{
		    document.getElementById("Purviewframe").jh_ok();
		}

	}
	function pro_close()
	{
		Purviewframe.location="//";
		dialog.hide();
	}
	
</script>

<%
	web_html="";
	strRETURN_MSG="";
%>

</html>


