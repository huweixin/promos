<%@ page contentType="text/html; charset=GBK" %>
<html>
	
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>Promos</title>

    <link rel="stylesheet" type="text/css" href="Ext/2/resources/css/ext-all.css" />

  	<script type="text/javascript" src="Ext/2/adapter/yui/yui-utilities.js"></script>
	  <script type="text/javascript" src="Ext/2/adapter/yui/ext-yui-adapter.js"></script>
    <script type="text/javascript" src="Ext/2/ext-all.js"></script>
    <script type="text/javascript" src="Ext/2/ext-all-debug.js"></script>

    <script type="text/javascript" src="Ext/2/Ext.ux.InfoPanel.js"></script>
    <script type="text/javascript" src="Ext/2/Ext.ux.Accordion.js"></script>

    <script type="text/javascript" src="js/simple.js"></script>
	<script type="text/javascript" src="js/publicfunction.js"></script>
</head>

<body>
	
<div id="north-div">
    <!-- div id="title-bar" align="right" valign="middle" class="x-toolbar" style="border-width:0 0px 0px;height:35;">
    	Promos Development Studio&nbsp;&nbsp;
    </div-->
  <div id="x-taskbar"  class="x-toolbar" style="border-width:0 0px 0px">
  </div>
</div>


<%
    String COMPONENTCONT="<div id=\"east-div\">\n<div id=\"acc-ct\" style=\"width:100%;height:480px;border:3px double #fafafa\">\n</div>\n</div>\n";
		if (request.getAttribute("COMPONENTCONT")!=null) COMPONENTCONT=request.getAttribute("COMPONENTCONT").toString();
%>


<%=COMPONENTCONT%>


<div id="west-div">
  <iframe src="/promos/core/CoreServlet.do?method=showFromCore&AppName=EXT_APP_TREE&JsRep=1&HtmlRep=1&devuser=<%=request.getParameter("devuser")%>" id="pclass" width="100%" height="100%" border="0" style="border:0px;" autoScroll=true></iframe>
</div>
<div id="center-div"><div id="x-desktop"></div></div>


<div id="center-div-web" style="border:0px;width:100%;height:100%;border:0px;autoScroll:true">
  <iframe id="webframe" src="" style="border:0px;width:100%;height:100%;border:0px;autoScroll:true"></iframe>
</div>

<div id="center-div-jsp" style="border:0px;width:100%;height:100%;border:0px;autoScroll:true">
  <iframe id="jspframe" src="" style="border:0px;width:100%;height:100%;border:0px;autoScroll:true"></iframe>
</div>

<div id="center-div-pro" style="border:0px;width:100%;height:100%;border:0px;autoScroll:true">
  <iframe id="proframe" src="" style="border:0px;width:100%;height:100%;border:0px;autoScroll:true"></iframe>
</div>

<div id="center-div-fun" style="border:0px;width:100%;height:100%;border:0px;autoScroll:true">
  <iframe id="funframe" src="" style="border:0px;width:100%;height:100%;border:0px;autoScroll:true"></iframe>
</div>

<div id="center-div-con" style="border:0px;width:100%;height:100%;border:0px;autoScroll:true">
  <iframe id="conframe" src="" style="border:0px;width:100%;height:100%;border:0px;autoScroll:true"></iframe>
</div>


<div id="south-div">
  <div id="x-windows">
    <div id="x-winlist"><div id="spacer"></div></div>
    <div class="x-clear"></div>
  </div>
</div>

<div id="InputApp" style="visibility: hidden;"></div>
<script type="text/javascript">
function showmsgappname()
{		
  Ext.MessageBox.show({
     title: '输入表单元素代码',
     msg: '输入复制表单元素代码:\n(注意不能同名!)',
     width:300,
     height:40,
     buttons: Ext.MessageBox.OKCANCEL,
     multiline: true,
     fn: msgclick,
     animEl: 'InputApp'
 });
}
function msgclick(btn,text)
{
	if (btn=='ok')
	{
		document.all.webframe.src="/promos/core/DesignServlet.do?method=designFormCopy&FromAppName="+copyid+"&ToAppName="+text+"&ToClass="+streeid;
	}
}    
</script>

<script language="JavaScript" type="text/JavaScript">
var sSelectID="";
var streeid="";
var copyid="";
var copycomapp="";
var copycomlist="";

function jh_new(){
  if ((sSelectID=="")&&(streeid!=""))
  {
	  document.all.webframe.src="/promos/core/CoreServlet.do?method=showFromCore&AppName=CORE_APP_NEW&WebAct=ADD&JsRep=1&HtmlRep=1&CLASSID="+streeid+"&devuser=<%=request.getParameter("devuser")%>";
  }
  else
  {
    alert("请先选择新增表单的节点!");
  }
}

function jh_testrun(){
	

  if (sSelectID!="")
  {
  var pop=window.open('/promos/core/CoreServlet.do?method=showFromCore&AppName='+sSelectID,'','top=0, left=0');
  pop.resizeTo(screen.width,screen.height);
  }
  else
  {
    alert("请先选择表单!");
  }
}

function jh_editform(){
  if (sSelectID!="")
  {
  var pop=window.open('/promos/core/DesignServlet.do?method=showDesignWeb&AppName='+sSelectID,'_blank','=768, width=1024, top=0, left=0,toolbar=yes, menubar=yes,resizable=yes,location=yes, status=yes');
  pop.resizeTo(screen.width,screen.height);
  }
  else
  {
    alert("请先选择表单!");
  }
}


function jh_deleteByID()
{
  if (sSelectID=="")
  {
    alert("请先选择表单元素!");
    return;
  }
 
  if (confirm("你确定要删除该表单元素吗？"))
  {
	  document.all.webframe.src="/promos/core/DesignServlet.do?method=deleteapp&AppName="+sSelectID+"&devuser=<%=request.getParameter("devuser")%>";
  }
}

var copyid="";
function jh_formcopy(){
  if (sSelectID!="")
  {
    copyid=sSelectID;
    jh_formpaste();
  }
  else
  {
    alert("请先选择表单!");
  }
  }

function jh_formpaste(){
    if (copyid!="")
    {
    	if (streeid!="")
    	{
        showmsgappname();
      }
      else
    	{
        alert("请选择要复制到的节点!");
    	}
    }
    else
    {
        alert("请先复制表单!");
    }
}


function jh_clearcash(){
  window.showModalDialog("/promos/core/CoreServlet.do?method=initCache",'清除缓存','dialogHeight=500px;dialogWidth=550px,toolbar=no, menubar=no,resizable=no,location=no, status=no');
}	
	
function jh_adddesigncom(comtype)
{
	if (document.all["center-div-web"].style.visibility=="visible") {
		jh_getIframByid('webframe').jh_adddesigncom(comtype);
	}
else
	{
		alert("请转到页面窗再添加!");
	}
}
</script>


</body>
</html>