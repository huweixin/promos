<%@ page contentType="text/html; charset=GBK" %>
<%@page import="promos.base.objects.*"%>
<html>
	<%response.setContentType("text/html; charset=GBK");%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>表单元素设计</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<script language=javascript src="js/publicfunction.js"></script>
<link rel="stylesheet" type="text/css" href="Ext/2.1/resources/css/ext-all.css" />
<script type="text/javascript" src="Ext/2.1/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="Ext/2.1/ext-all.js"></script>


</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" background=/promos/images/Designbg.gif>
<div id="ie5menu" style="position:absolute; left:-200px; top:-100px; width:70px; z-index:100; layer-background-color: #4B7EB6; border: 1px none #000000; visibility: hidden;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" onMouseOut="MM_showHideLayers('ie5menu','','hide')" onMouseOver="MM_showHideLayers('ie5menu','','show')">
    <tr>
      <td bgcolor="#4B7EB6" rowspan="20" width="3"><div align="center"></div></td>
      <td height="1" width="1" bgcolor="#4B7EB6"> <div align="center"></div></td>
      <td height="50" rowspan="20" bgcolor="#4B7EB6" width="1"><div align="center"></div></td>
    </tr>

  　<tr><td align="center" height="17" onClick="MenuSelect('全选')" onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#E1E1E1'">全　选</td></tr>
    <tr><td bgcolor="E1E1E1"> <div align="center"></div></td></tr>
   
    <tr><td align="center" height="17" onClick="MenuSelect('复制')" onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#E1E1E1'">复　制</td></tr>
    <tr><td bgcolor="E1E1E1"> <div align="center"></div></td></tr>

    <tr><td align="center" height="17" onClick="MenuSelect('粘贴')" onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#E1E1E1'">粘　贴</td></tr>
    <tr><td bgcolor="E1E1E1"> <div align="center"></div></td></tr>


   <tr><td align="center" height="17" onClick="MenuSelect('左对齐')" onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#E1E1E1'">左对齐</td></tr>
   <tr><td align="center" height="17" onClick="MenuSelect('右对齐')" onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#E1E1E1'">右对齐</td></tr>
   <tr><td align="center" height="17" onClick="MenuSelect('上对齐')" onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#E1E1E1'">上对齐</td></tr>
    <tr><td bgcolor="E1E1E1"> <div align="center"></div></td></tr>
   <tr><td align="center" height="17" onClick="MenuSelect('横平均')" onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#E1E1E1'">横平均</td></tr>
   <tr><td align="center" height="17" onClick="MenuSelect('竖平均')" onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#E1E1E1'">竖平均</td></tr>
   <tr><td bgcolor="E1E1E1"> <div align="center"></div></td></tr>
   
   <tr><td align="center" height="17" onClick="MenuSelect('等高')" onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#E1E1E1'">等&nbsp;&nbsp;高</td></tr>
   <tr><td align="center" height="17" onClick="MenuSelect('等宽')" onMouseOut="this.style.backgroundColor='#ffffff'" onMouseOver="this.style.backgroundColor='#E1E1E1'">等&nbsp;&nbsp;宽</td></tr>
   
   <tr> <td height="" bgcolor="3E72C0"> <div align="center"></div></td></tr>

  </table>
</div>

<%=request.getAttribute(Constants.WEB_HTML_STRING).toString()%>


<div id="pro-dlg" class="x-hidden" style="z-index:100;">
    <div class="x-window-header"></div>
    <div id="pro-bd">
       <IFRAME ID=Propertyframe scrolling='no' style='border: 0px none #000000;height:120%;width:100%;' src=""></IFRAME>   
    </div>
</div>


<script language="JavaScript" type="text/JavaScript">
var doc = document;

function jh_ok(){
MyForm.submit();
}
function jh_back(){
windows.close();
}

var dragapproved=false;
var allselect="";
var canmove="";

var comcount=250;

var fro=new Array(comcount);

var oldTop= new Array(comcount);
var oldLeft= new Array(comcount);
var oldobj= new Array(comcount);
var froID= new Array(comcount);
var froPL= new Array(comcount);
var froPT= new Array(comcount);
var froX="";
var froY="";
ie5menu.style.visibility="hidden";

//移动鼠标
function move(){
  if (!canmove) return;
  var posx=event.clientX-froX;
  var posy=event.clientY-froY;
  if (event.button==0&&dragapproved){
    commove(posx,posy);
  }
  return false;
}

function commove(x,y){
    if (canmove){
       for(var i=0;i<comcount;i++){
         if (fro[i])
         {
	          if (isCharsInBag(froPL[i],"0123456789px")&&(froPL[i]!=''))
	          {
		        fro[i].style.left=(parseInt(froPL[i])+x)+"px";
	          }
	          else
	          {
	    	    fro[i].style.left=x+"px";
	          }
	          if (isCharsInBag(froPT[i],"0123456789px")&&(froPT[i]!=''))
	          {
	           fro[i].style.top=(parseInt(froPT[i])+y)+"px";
	          }
	          else
	          {
	           fro[i].style.top=y+"px";
	          }

          }
         else
         {
           break;
         }
       }
  }
}

function focuscommove(x,y){
    if (canmove){
       for(var i=0;i<comcount;i++){
         if (fro[i])
         {
	          if (isCharsInBag(froPL[i],"0123456789px")&&(froPL[i]!=''))
	          {
		        fro[i].style.left=(parseInt(froPL[i])+x)+"px";
	          }
	          else
	          {
	    	    fro[i].style.left=x+"px";
	          }
	          if (isCharsInBag(froPT[i],"0123456789px")&&(froPT[i]!=''))
	          {
	           fro[i].style.top=(parseInt(froPT[i])+y)+"px";
	          }
	          else
	          {
	           fro[i].style.top=y+"px";
	          }
         }
         else
         {
           break;
         }
       }
  }
}

//按下鼠标
function drags(item){

//  if (document.body.innerHTML=="")
//      return;

  if ((event)&&(event.button==0))
  {
     if (allselect==false)
     {
       if (canmove==false)
       {
        var basic = new Ext.Resizable(item.id, {
                transparent:true,
                draggable:false,
                dynamic:true,
                minWidth:1,
                minHeight:1
        });
       	
       for(var i=0;i<comcount;i++){
         if (fro[i])
         {
           fro[i].style.background="";
           fro[i].style.border="1px";
           fro[i]=null;
         }
         else
         {
           break;
         }
       }
      oldobj[0]=item;
      if (item)  item.style.background="WhiteSmoke";
      if (item)  item.style.border="1px dashed";
      fro[0]=item;
      froID[0]=fro[0].id;
      froID[0]=froID[0].substring(3);
      froPL[0]=fro[0].style.left;
      froPT[0]=fro[0].style.top;
      oldLeft[0]=fro[0].style.left;
      oldTop[0]=fro[0].style.top;
      }
    }
    else
    {
      var i=0;
      for(i=0;i<comcount;i++)
      {
       if (!(fro[i])) break;
       if (fro[i].id==item.id)
       {
        if (canmove)  break;
        else
        {
         if (fro[i])  fro[i].style.background="";
         if (fro[i])  fro[i].style.border="1px";
         for(var j=i;j<comcount;j++)
         {
          fro[j]=fro[j+1];
          if (fro[j+1])
          {
          froID[j]=fro[j+1].id;
          froID[j]=froID[j+1].substring(3);
          froPL[j]=fro[j+1].style.left;
          froPT[j]=fro[j+1].style.top;
          oldLeft[j]=fro[j+1].style.left;
          oldTop[j]=fro[j+1].style.top;
          }
          else break;
          }
          froX=event.clientX;
          froY=event.clientY;
          return;
        }
        
       }
      }
  
      if (item)  item.style.background="WhiteSmoke";
      if (item)  item.style.border="1px dashed";
      fro[i]=item;
      froID[i]=fro[i].id;
      froID[i]=froID[i].substring(3);
      froPL[i]=fro[i].style.left;
      froPT[i]=fro[i].style.top;
      oldLeft[i]=fro[i].style.left;
      oldTop[i]=fro[i].style.top;
    }
    dragapproved=true;
    froX=event.clientX;
    froY=event.clientY;
    
	doc.onmousemove = function(e) {
		move(e);
	};
	doc.onmouseup = function() {
		doc.onmousemove = null;
		doc.onmouseup = drag_up();
	};
    
  }
}
//松开鼠标
function drag_up(){
  dragapproved=false;
  if (!canmove) return;
  if (event.button==0)
  {
    drag_com();
  }
}

function drag_com(){
  var selectid="";
  var comtop="";
  var comleft="";
        for(var i=0;i<comcount;i++){
         if (fro[i])
         {
          if ((oldTop[i]!=fro[i].style.top)||(oldLeft[i]!=fro[i].style.left))
          {
           if (selectid=="") selectid=froID[i]; else selectid=selectid+","+froID[i];
           if (comtop=="") comtop=fro[i].style.top; else comtop=comtop+","+fro[i].style.top;
           if (comleft=="") comleft=fro[i].style.left; else comleft=comleft+","+fro[i].style.left;
          }
         }
         else
         {
           break;
         }
  
       }
    if (selectid!="")
    {
        self.location="/promos/core/DesignServlet.do?method=updateComponentPos&ActType=UPDATE&AppName=<%=request.getAttribute("AppName")%>"+"&COMHTMLID="+selectid+"&COMTOP="+comtop+"&COMLEFT="+comleft;
    }
 
}


function documentonkeydown(){
  var nkey=event.keyCode;
  var states="";
//alert(nkey);
  if (nkey==18)  //shift
  {
    canmove=true;
  }
  if (nkey==17)  //ctrl
  {
    allselect=true;
  }
  
 if (canmove) states=" 表单元素锁:关  ";
 else   states=" 表单元素锁:开  ";
 if (allselect) states=states+"多选:开    ";
 else   states=states+"多选:关    ";
 window.status =states;
 
 }
function documentonkeyup(){
  var nkey=event.keyCode;
  var states="";
  if (nkey==18)  //shift
  {
    drag_com();
    canmove=false;
  }
  if (nkey==17)  //ctrl
  {
    allselect=false;
  }
  
   
  if (nkey==46)
  {
    deletecom();
  }

  if (nkey==37){ focuscommove(-1,0);}
  if (nkey==38){ focuscommove(0,-1);}
  if (nkey==39){ focuscommove(1,0);}  
  if (nkey==40){ focuscommove(0,1);}
  
  if (nkey==27){ disselect();}
 
 
 if (canmove) states=" 表单元素锁:关  ";
 else   states=" 表单元素锁:开  ";
 if (allselect) states=states+"多选:开    ";
 else   states=states+"多选:关    ";
 window.status =states;
}

function documenonmouseup(){
  //document.all.listcomdiv.style.visibility="hidden";
  if (!canmove) return;
  if (event.button==0){drag_com();}
}

function disselect()
{
  for(var i=0;i<comcount;i++){
         if (fro[i])
         {
           fro[i].style.background="";
           fro[i].style.border="1px";
           fro[i]=null;
         }
         else
         {
           break;
         }
  }
}

function deletecom(){
    if (froID!="")
    {
      if (confirm("你确定要删除该对象吗？"))
      {
      changecom('DELETE');
     }
    }
}

function changecom(sactr){
  if (froID!="")
  {
      var sid=getcomid();
      if (sid!="")  self.location="/promos/core/DesignServlet.do?method=updateComponentPos&ActType="+sactr+"&AppName=<%=request.getAttribute("AppName")%>"+"&COMHTMLID="+sid+"&COMLEFT="+getcomleft()+"&COMTOP="+getcomtop();
  }
}

function comresize(nwidth,nheight)
{
  if ((froID!="")&&canmove)
  {
      var sTmpw=nwidth-2; if (sTmpw<0) sTmpw=0;  	
      var sTmph=nheight-4; if (sTmph<0) sTmph=0;  	
      var sid=froID[0];
      if (sid!="")  self.location="/promos/core/DesignServlet.do?method=updateComponentPos&ActType=RESIZE&AppName=<%=request.getAttribute("AppName")%>"+"&COMHTMLID="+sid+"&COMHEIGHT="+sTmph+"&COMWIDTH="+sTmpw;
  }
}

function getcomid(){
    var sid="";
    for(var i=0;i<comcount;i++){
         if (fro[i])
         {
           if (sid=="") sid=froID[i]; else sid=sid+","+froID[i];
         }
         else
         {
           break;
         } 
    }
   return sid;
}

function getcomtop(){
    var comtop="";
    for(var i=0;i<comcount;i++){
         if (fro[i])
         {
           if (comtop=="") comtop=fro[i].style.top; else comtop=comtop+","+fro[i].style.top;
         }
         else
         {
           break;
         } 
    }
   return comtop;
}

function getcomleft(){
    var comleft="";
    for(var i=0;i<comcount;i++){
         if (fro[i])
         {
           if (comleft=="") comleft=fro[i].style.left; else comleft=comleft+","+fro[i].style.left;
         }
         else
         {
           break;
         } 
    }
   return comleft;
}

function changeproperty1(){
  var　strurl="/promos/core/DesignServlet.do?method=showComProperty&JsRep=1&AppName=CORE_COM_PARA_EDIT"+"&GetDataWhere=FVC_APPNAME::'<%=request.getAttribute("AppName")%>' and FVC_HTMLID::'"+froID[0]+"'&DesignAppName=<%=request.getParameter("AppName")%>";
  var pop=window.open(strurl,'_blank','left=150,width=605, height=690,top=60,toolbar=no, menubar=no, scrollbars=no,resizable=yes,location=no, status=no,alwaysRaised=yes');
  //pop.resizeTo(screen.width,screen.height);
}
document.onkeyup=documentonkeyup;
document.onkeydown=documentonkeydown;
document.onmouseup=documenonmouseup;

function MM_showHideLayers()
{
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3)
    if ((obj=MM_findObj(args[i]))!=null)
    {
      v=args[i+2];
      if (obj.style)
      {
        obj=obj.style; v=(v=='show')?'visible':(v='hide')?'hidden':v;
      }
      obj.visibility=v;
    }
}

function MM_findObj(n, d)
{ //v4.0
  var p,i,x;
  if(!d) d=document;
  if((p=n.indexOf("?"))>0&&parent.frames.length)
  {
    d=parent.frames[n.substring(p+1)].document;
    n=n.substring(0,p);
  }
  if(!(x=d[n])&&d.all) x=d.all[n];
  for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n); return x;
}

var display_url=0
function showmenuie5()
{
  var rightedge=document.body.clientWidth-event.clientX
  var bottomedge=document.body.clientHeight-event.clientY
  if (rightedge<ie5menu.offsetWidth)
  {
    ie5menu.style.left=event.clientX-ie5menu.offsetWidth;
   }
  else
  {
    ie5menu.style.left=event.clientX;
   }



  if (bottomedge>ie5menu.offsetHeight)
    ie5menu.style.top=event.clientY+document.body.scrollTop;
  else
    ie5menu.style.top=event.clientY-ie5menu.offsetHeight+document.body.scrollTop;


  ie5menu.style.visibility="visible";
  return false
}

function hidemenuie5()
{
  ie5menu.style.visibility="hidden";
}
function highlightie5()
{
  if (event.srcElement.className=="menuitems")
  {
    event.srcElement.style.backgroundColor="highlight";
    event.srcElement.style.color="white";
    if (display_url==1) window.status=event.srcElement.url;
  }
}

function lowlightie5()
{
    event.srcElement.style.backgroundColor="";
    event.srcElement.style.color="black";
}
document.oncontextmenu=showmenuie5;
if (document.all&&window.print) document.body.onclick=hidemenuie5;

function MenuSelect(sType)
{
  if (sType=="左对齐")
  {
    changecom("LEFTSNAPE");
  }
  if (sType=="右对齐")
  {
    changecom("RIGHTSNAPE");
  }
  if (sType=="上对齐")
  {
    changecom("TOPSNAPE");
  }
  if (sType=="等高")
  {
    changecom("SAMEHEIGHT");
  }
  if (sType=="等宽")
  {
    changecom("SAMEWIDTH");
  }
  
  if (sType=="竖平均")
  {
    changecom("EVENSPACEY");
  }
  if (sType=="横平均")
  {
    changecom("EVENSPACEX");
  }
  
  if (sType=="复制")
  {
  	copycom();
  }
  if (sType=="粘贴")
  {
  	pastecom();
  }
}
function copycom(){
  if (froID!="")
  {
      var sid=getcomid();
      if (sid!="")
      {
         parent.copycomapp="<%=request.getAttribute("AppName")%>";
         parent.copycomlist=sid;
    		 alert("已经复制表单元素!");
      }
    else
    	{
    		alert("请先选择要复制的表单元素!");
    	}
  }
  else
  {
     alert("请先选择要复制的表单元素!");
  }
}
function pastecom(){
	if ((parent.copycomapp!="")&&(parent.copycomlist!=""))
	{
      self.location="/promos/core/DesignServlet.do?method=updateComponentPos&ActType=COPY&AppName=<%=request.getAttribute("AppName")%>"+"&COMHTMLID="+parent.copycomlist+"&FromAppName="+parent.copycomapp;
	}
  else
	{
     alert("请先选择表单元素复制!")
	}
}
function jh_adddesigncom(comtype)
{
  if (comtype!="")
  {
     self.location="/promos/core/DesignServlet.do?method=updateComponentPos&ActType=ADD&AppName=<%=request.getAttribute("AppName")%>"+"&AddComType="+comtype;
  }
}


 if (canmove) states=" 表单元素锁:关  ";
 else   states=" 表单元素锁:开  ";
 if (allselect) states=states+"多选:开    ";
 else   states=states+"多选:关    ";
 window.status =states;

var dialog;
function changeproperty()
{
   if(!dialog){ // lazy initialize the dialog and only create it once
     dialog = new Ext.Window({el:"pro-dlg", 
                        width:325,
                        height:503,
                        shadow:true,
                        frame:true,
                        minWidth:150,
                        minHeight:200,
                        resizable:false,
                        constrainHeader:false,
                        items: new Ext.TabPanel({
                            el: 'pro-bd',
                            border:false
                        }),
                                
                        buttons: [{
                            text:'确定',
                            handler: pro_ok
                        },{
                            text: '返回',
                            handler: pro_close
                        }]
                        
             });
    }
    Propertyframe.src="/promos/core/DesignServlet.do?method=showComProperty&JsRep=1&WebAct=<%=Constants.PARAMETER_WEBACT_EDIT%>&AppName=CORE_COM_PARA_EDIT"+"&GetDataWhere=FVC_APPNAME::'<%=request.getAttribute("AppName")%>' and FVC_HTMLID::'"+froID[0]+"'&DesignAppName=<%=request.getParameter("AppName")%>&FVC_HTMLID="+froID[0];
    //Propertyframe.location="/promos/core/DesignServlet.do?method=showComProperty&JsRep=1&AppName=CORE_COM_PARA_EDIT&FVC_HTMLID="+froID[0]+"&DesignAppName=<%=request.getParameter("AppName")%>";
    dialog.show();
}
function pro_ok()
{
	var mb = myBrowser();

	if ("IE" == mb) {
	    document.getElementById("Propertyframe").jh_ok();
	}
	else
	if ("FF" == mb) {
	    document.getElementById("Propertyframe").jh_ok();
	}
	else
	if ("Chrome" == mb) {
	    document.getElementById("Propertyframe").contentWindow.jh_ok();
	}
	else
	if ("Opera" == mb) {
	    document.getElementById("Propertyframe").jh_ok();
	}
	else
	if ("Safari" == mb) {
	    document.getElementById("Propertyframe").jh_ok();
	}
	else
	{
	    document.getElementById("Propertyframe").jh_ok();
	}


	
}
function pro_close()
{
	Propertyframe.location="//";
	dialog.hide();
}

<%
 String strRETURN_MSG="";
  if (request.getAttribute("RETURN_MSG")!=null)
  {
    strRETURN_MSG=request.getAttribute("RETURN_MSG").toString();
  }
%>

<%=strRETURN_MSG%>
	
</script>
<%
request.setAttribute(Constants.WEB_HTML_STRING,"");
%>

</body>
</html>


