package promos.design.ejb;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.servlet.http.*;

import promos.base.objects.BaseConfig;
import promos.base.objects.BaseObject;
import promos.base.objects.Cache;
import promos.base.objects.Constants;
import promos.base.objects.User;
import promos.core.ejb.CoreBean;

/**
 *
 * <p>
 * 标题: Bean MainBean
 * </p>
 * <p>
 * 描述: 系统公用类Bean
 * </p>
 * <p>
 * 版权: Copyright (c) 2003-2004
 * </p>
 * <p>
 * 公司: 广州升域传媒有限公司
 * </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 08/30/2003
 *
 */
@Stateless

public class DesignBean extends CoreBean implements Design {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String DB_USER = BaseConfig.getSettingByName("db_user"); /*系统数据库用户*/
	
	SessionContext sessionContext;
	
	//系统公用类数据源的JNDI名称
	/**
	 * <p>
	 * Bean容器自己根据需要创建Bean实例
	 * </p>
	 *
	 * @throws CreateException
	 */
	public void ejbCreate() throws CreateException {
	}
	/**
	 * <p>
	 * Bean容器自己根据需要删除Bean实例
	 * </p>
	 *
	 */
	public void ejbRemove() {
	}
	/**
	 * 重新得到被串行化的Bean实例
	 *
	 */
	public void ejbActivate() {
	}
	/**
	 * 串行化Bean实例，释放资源
	 *
	 */
	public void ejbPassivate() {
	}
	/**
	 * 得到上下文环境对象，Bean利用它与容器通信，执行功能
	 *
	 * @param sessionContext
	 */
	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}	
	
	/**
	 * 显示页面设计界面
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return @throws
	 *         Exception
	 */
	public void showDesignWeb(HttpServletRequest request) throws RemoteException {
		
		BaseObject baseobj =null;
		
		/******取业务的主要参数******/
		String sDesignWeb=Constants.PARAMETER_DESIGNT_WEBMODEL;
		/******************/
		
		request.setAttribute(Constants.PARAMETER_IS_DESIGN,"1");
		request.setAttribute(Constants.PARAMETER_ISVIEW_NAME,"1");
		
		/******显示页面******/
		ArrayList list = null;
		list =this.getComListByCondition(request,"","");
		//this.selectSQL(getAppMainParameter("FVC_TABLE_APPCOMP",request),"FVC_APPNAME='"+sAppName+"'", "FVC_SHOWORDER");
		ArrayList Paralist = getAppComPara(request);
		Cache cache = Cache.getInstance();
		
		
		int length=list.size();
		String s_comhtml="";
		String s_DivScroll="";
		String s_ComHeight="";
		String sDivIndex="";
		//String sDivseljs="\n";
		BaseObject htmlobj=null;
		if (list != null &&  length> 0) {
			for (int i=0; i < length; i++) {
				baseobj = (BaseObject) list.get(i);
				htmlobj=(BaseObject)cache.getCache(Constants.CACHE_HTMLCODE_TAG,BaseObject.toString(baseobj, "FVC_TYPE"));
				s_ComHeight=BaseObject.toString(baseobj, "FVC_HEIGHT");
				s_DivScroll=BaseObject.toString(baseobj, "FVC_IFDIVSCROLL");
				if (s_DivScroll.equals("1")==true) s_DivScroll=" OVERFLOW: scroll;";
				else s_DivScroll="";
				sDivIndex=BaseObject.toString(baseobj, "FVC_DIVINDEX");
				if (sDivIndex.equals("")==false) sDivIndex=" Z-INDEX:"+sDivIndex;
				//s_comhtml=s_comhtml+getDivHtmlCode("DIV"+BaseObject.toString(baseobj, "FVC_HTMLID"),"absolute","",s_ComHeight,BaseObject.toString(baseobj, "FVC_WIDTH"),BaseObject.toString(baseobj, "FVC_LEFT"),BaseObject.toString(baseobj, "FVC_TOP"),"CURSOR:hand; "+s_DivScroll+sDivIndex,"onmousedown='drags(this);' onmouseup='drag_up();' onmousemove='move();' onDblclick='"+BaseObject.toString(htmlobj, "FVC_ONDBCLICK")+"'  valign='center' ",getBaseComHtmlForDesign(baseobj,Paralist, request),request);
				s_comhtml=s_comhtml+getDivHtmlCode("DIV"+BaseObject.toString(baseobj, "FVC_HTMLID"),"absolute","",s_ComHeight,BaseObject.toString(baseobj, "FVC_WIDTH"),BaseObject.toString(baseobj, "FVC_LEFT"),BaseObject.toString(baseobj, "FVC_TOP"),"CURSOR:hand; "+s_DivScroll+sDivIndex,"onmousedown='drags(this);' onDblclick='"+BaseObject.toString(htmlobj, "FVC_ONDBCLICK")+"'  valign='center' ",getBaseComHtmlForDesign(baseobj,Paralist, request),request);
				//sDivseljs=sDivseljs+"var DIV"+BaseObject.toString(baseobj, "FVC_HTMLID")+" = new Ext.Resizable('DIV"+BaseObject.toString(baseobj, "FVC_HTMLID")+"', { transparent:true,draggable:true,dynamic:true,width: 0,height: 0,minWidth:1,minHeight:1});\n";
			}
		}
		
		String sHRe=getValueFromRequest(Constants.PARAMETER_IFREPLACEHTML,"0",request);
		String  sJRe=getValueFromRequest(Constants.PARAMETER_IFREPLACEJS,"0",request);
		if ((sHRe.equals("1"))||(sJRe.equals("1")))
		{
			System.out.println("here1:sHRe=1");
			s_comhtml=replHtmlWihtPara(s_comhtml,request);
		}
		
		s_comhtml=addComResource(s_comhtml,request);
		
		s_comhtml=getWebAddForm(s_comhtml,"MyForm","/promos/core/DesignServlet.do?method=","_self",request);
		/******************/
		
		
		/******页面的JS******/
		//BasePrint.println("start getWebJs");
		String js=getWebJs(request);
		if ((js.equals(""))||js==null) js="         ";
		if (js.substring(0,6).equals("ERROR:"))
		{
			js=js.substring(6);
			showWebError(js,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
		}
		//BasePrint.println("end getWebJs");
		/******************/
		
		
		//sDivseljs=this.getHtmlCode(null,"JAVASCRIPT","",sDivseljs,request);
		
		showWebByHtml(s_comhtml,js,request);
		
		ArrayList core_returnlist= (ArrayList)request.getAttribute(Constants.CORE_PARAMETER_NAME);
		
		core_returnlist.set(Constants.CORE_PARAMETER_RETURNURL,sDesignWeb);
		request.setAttribute(Constants.CORE_PARAMETER_NAME,core_returnlist);
		
		
		
	}
	
	
	
	
	/**
	 * 得到非输入类表单元素HTML
	 *
	 * @String session           session
	 * @BaseObject bComObject    表单元素BaseObject
	 */
	public String getExtendComHtml(BaseObject bComObject,ArrayList lComParalist,HttpServletRequest request)throws RemoteException {
		String s_Tmphtml="";
		try
		{
			String comtype=BaseObject.toString(bComObject, "FVC_TYPE");
			
			if (comtype.equals("CONTAINER")==true)
			{
				/******组装容器内容******/
				BaseObject baseobj=null;
				String s_comhtml="";
				ArrayList list = getComListByCondition(request,Constants.COMP_WHERE_CONTAINER_BYVALUE,getComName(bComObject));
				//this.selectSQL( getAppMainParameter("FVC_TABLE_APPCOMP", request),"FVC_CONTAINER='"+getComName(bComObject)+"' and FVC_APPNAME='"+BaseObject.toString(bComObject, "FVC_APPNAME")+"'","to_number(FVC_LEFT)");
				int length=list.size();
				if (list != null &&  length> 0) {
					for (int i=0; i < length; i++) {
						baseobj = (BaseObject) list.get(i);
						//BasePrint.println("FVC_HTMLID:"+BaseObject.toString(baseobj, "FVC_HTMLID"));
						s_comhtml=s_comhtml+getBaseComHtmlForDesign(baseobj,lComParalist, request);
					}
				}
				return s_comhtml;
			}
			return s_Tmphtml;
		} catch (Exception e) {
			e.printStackTrace();
			return s_Tmphtml;
		}
		
	}
	
	
	public String reComDisegnConstans(String sHtml,BaseObject comobj) throws RemoteException {
		if (sHtml.equals("")) return sHtml;
		if (sHtml.indexOf("<%=")<0)  return sHtml;
		
		Cache cache = Cache.getInstance();
		ArrayList disegnConlist=(ArrayList)cache.getCache(Constants.DESIGN_HTMLCON_TAG,"COMDESIGNCONLIST");
		
		if (disegnConlist==null){
			ArrayList list = this.selectSQL(AnalyseDataSource("T_COMDESIGNCON"),"","");
			cache.setCache(Constants.DESIGN_HTMLCON_TAG,"COMDESIGNCONLIST",list);
		}
		
		disegnConlist=(ArrayList)cache.getCache(Constants.DESIGN_HTMLCON_TAG,"COMDESIGNCONLIST");
		if (disegnConlist!=null){
			String comtype=BaseObject.toString(comobj, "FVC_TYPE");
			int length=disegnConlist.size();
			BaseObject baseobj = null;
			if (disegnConlist != null &&  length> 0) {
				for (int i=0; i < length; i++) {
					baseobj = (BaseObject) disegnConlist.get(i);
					if (BaseObject.toString(baseobj, "FVC_TYPE").equals(comtype))
					{
						sHtml=sHtml.replaceAll("<%="+BaseObject.toString(baseobj, "FVC_CODE")+"%>",BaseObject.toString(baseobj, "FVC_VALUE"));
					}
				}
			}
		}
		
		return sHtml;
	}
	
	
	/**
	 * 根据表单元素类型显示数据
	 *
	 * @BaseObject bComObject    表单元素BaseObject
	 * @BaseObject bDataObject   数据BaseObject
	 */
	public String getBaseComHtmlForDesign(BaseObject bComObject,HttpServletRequest request)throws RemoteException {
		String sHtml="";
		String sOthertype="";
		
		String scomtype=BaseObject.toString(bComObject, "FVC_TYPE");
		String sDefaultValue=BaseObject.toString(bComObject, "FVC_DEFAULTVALUE");
		String sIsView="1";
		String sTmpVaule="";
		
		
		try
		{
			if ((scomtype.equals("RADIO")==true)||(scomtype.equals("CHECKBOX")==true))
			{
				if ((sDefaultValue.equals("1")==true)||sTmpVaule.equals("1")==true){
					sOthertype=sOthertype+" checked ";
				}
			}
			if (BaseObject.toString(bComObject, "FVC_BORDER").equals("")==false)
			{
				sOthertype=sOthertype+" border='"+BaseObject.toString(bComObject, "FVC_BORDER")+"'";
			}
			if (BaseObject.toString(bComObject, "FVC_COMCLASS").equals("")==false)
			{
				sOthertype=sOthertype+" styleClass='"+BaseObject.toString(bComObject, "FVC_COMCLASS")+"'";
			}
			
			if (BaseObject.toString(bComObject, "FVC_ALIGN").equals("")==false)
			{
				sOthertype=sOthertype+" ALIGN='"+BaseObject.toString(bComObject, "FVC_ALIGN")+"'";
			}
			
			if (BaseObject.toString(bComObject, "FVC_VALIGN").equals("")==false)
			{
				sOthertype=sOthertype+" VALIGN='"+BaseObject.toString(bComObject, "FVC_VALIGN")+"'";
			}
			
			if (BaseObject.toString(bComObject, "FVC_MAXLEN").equals("")==false)
			{
				sOthertype=sOthertype+" MAXLENGTH="+BaseObject.toString(bComObject, "FVC_MAXLEN");
			}
			
			/*style*/
			sOthertype=sOthertype+" style='";
			if (BaseObject.toString(bComObject, "FVC_HEIGHT").equals("")==false)
			{
				sOthertype=sOthertype+" ;HEIGHT:"+BaseObject.toString(bComObject, "FVC_HEIGHT");
			}
			if (BaseObject.toString(bComObject, "FVC_WIDTH").equals("")==false)
			{
				sOthertype=sOthertype+" ;WIDTH:"+BaseObject.toString(bComObject, "FVC_WIDTH");
			}
			if ((BaseObject.toString(bComObject, "FVC_BACKCOLOR").equals("")==false)||(sIsView.equals("1"))||(getValueFromRequest(Constants.COM_PROPERTY_READONLY,"", request).indexOf(getComName(bComObject))>=0)||(getValueFromRequest(Constants.COM_PROPERTY_NOTENABLED,"", request).indexOf(getComName(bComObject))>=0))
			{
				if (((getValueFromRequest(Constants.COM_PROPERTY_READONLY,"", request).indexOf(getComName(bComObject))>=0)||(sIsView.equals("1"))||(getValueFromRequest(Constants.COM_PROPERTY_NOTENABLED,"", request).indexOf(getComName(bComObject))>=0))&&(scomtype.equals("IMG")==false)&&(getNotPurViewCom(request).indexOf(getComName(bComObject))<0))
				{
					sOthertype = sOthertype + " ;background:WhiteSmoke";
				}
				else
				{
					if (BaseObject.toString(bComObject, "FVC_BACKCOLOR").equals("")==false)  sOthertype=sOthertype+" ;background:"+BaseObject.toString(bComObject, "FVC_BACKCOLOR");
				}
			}
			if (BaseObject.toString(bComObject, "FVC_CURSOR").equals("")==false)
			{
				sOthertype=sOthertype+" ;CURSOR:"+BaseObject.toString(bComObject, "FVC_CURSOR");
			}
			else
			{
				sOthertype=sOthertype+" ;CURSOR:hand";
			}
			sOthertype=sOthertype+"'";
			
			sOthertype=sOthertype+" readonly=true ";
			
			
			/*事件*/
			if ((scomtype.equals("RADIO")==true)||(scomtype.equals("CHECKBOX")==true))
			{
				if (BaseObject.toString(bComObject, "FVC_ONCLICK").equals("")==false)
				{
					sOthertype=sOthertype+" onclick='javascript:{if (this.checked) this.value=\"1\"; else this.value=\"0\";"+BaseObject.toString(bComObject, "FVC_ONCLICK")+";}'";
				}
				else
				{
					sOthertype=sOthertype+" onclick='javascript:{if (this.checked) this.value=\"1\"; else this.value=\"0\";}'";
				}
			}
			else
			{
				if (BaseObject.toString(bComObject, "FVC_ONCLICK").equals("")==false)
				{
					sOthertype=sOthertype+" onclick='"+BaseObject.toString(bComObject, "FVC_ONCLICK")+";'";
				}
			}
			
			if (BaseObject.toString(bComObject, "FVC_ONDBCLICK").equals("")==false)
			{
				sOthertype=sOthertype+" onDbclick='"+BaseObject.toString(bComObject, "FVC_ONDBCLICK")+";'";
			}
			if (BaseObject.toString(bComObject, "FVC_ONBLUE").equals("")==false)
			{
				sOthertype=sOthertype+" onblur='"+BaseObject.toString(bComObject, "FVC_ONBLUE")+";'";
			}
			if (BaseObject.toString(bComObject, "FVC_ONCHANGE").equals("")==false)
			{
				sOthertype=sOthertype+" onchange='"+BaseObject.toString(bComObject, "FVC_ONCHANGE")+";'";
			}
			if (BaseObject.toString(bComObject, "FVC_ONFOCUS").equals("")==false)
			{
				sOthertype=sOthertype+" onfocus='"+BaseObject.toString(bComObject, "FVC_ONFOCUS")+";'";
			}
			if (BaseObject.toString(bComObject, "FVC_ONKEYDOWN").equals("")==false)
			{
				sOthertype=sOthertype+" onkeydown='"+BaseObject.toString(bComObject, "FVC_ONKEYDOWN")+";'";
			}
			
			if (BaseObject.toString(bComObject, "FVC_ONKEYUP").equals("")==false)
			{
				sOthertype=sOthertype+" onkeyup='"+BaseObject.toString(bComObject, "FVC_ONKEYUP")+";'";
			}
			if (BaseObject.toString(bComObject, "FVC_ONMOUSEOUT").equals("")==false)
			{
				sOthertype=sOthertype+" onmouseout='"+BaseObject.toString(bComObject, "FVC_ONMOUSEOUT")+";'";
			}
			if (BaseObject.toString(bComObject, "FVC_ONMOUSEMOVE").equals("")==false)
			{
				sOthertype=sOthertype+" onmousemove='"+BaseObject.toString(bComObject, "FVC_ONMOUSEMOVE")+";'";
			}
			if (BaseObject.toString(bComObject, "FVC_ONMOUSEDOWN").equals("")==false)
			{
				sOthertype=sOthertype+" onmousedown='"+BaseObject.toString(bComObject, "FVC_ONMOUSEDOWN")+";'";
			}
			if (BaseObject.toString(bComObject, "FVC_ONMOUSEUP").equals("")==false)
			{
				sOthertype=sOthertype+" onmouseup='"+BaseObject.toString(bComObject, "FVC_ONMOUSEUP")+";'";
			}
			
			if (scomtype.equals("SELECT"))
			{
				sHtml=getHtmlCode(BaseObject.toString(bComObject, "FVC_TYPE"),BaseObject.toString(bComObject, "FVC_HTMLID"),getComName(bComObject),sOthertype+" "+BaseObject.toString(bComObject, "FVC_OTHER"),"", request);
				return sHtml;
			}
			if (scomtype.equals("LABLE"))
			{
				sHtml=getHtmlCode(scomtype,BaseObject.toString(bComObject, "FVC_HTMLID"),getComName(bComObject)," "+BaseObject.toString(bComObject, "FVC_OTHER"),BaseObject.toString(bComObject, "FVC_TITLENAME"), request);    	   
				return sHtml;
			}			
			sHtml=getHtmlCode(scomtype,BaseObject.toString(bComObject, "FVC_HTMLID"),getComName(bComObject),sOthertype+" "+BaseObject.toString(bComObject, "FVC_OTHER"),"", request);    	   
			return sHtml;
		} catch (Exception e) {
			e.printStackTrace();
			return sHtml;
		}
	}
	
	
	/**
	 * 根据表单元素类型显示数据
	 *
	 * @BaseObject bComObject    表单元素BaseObject
	 * @BaseObject bDataObject   数据BaseObject
	 */
	public String getBaseComHtmlForDesign(BaseObject bComBaseObject,ArrayList lComParalist,HttpServletRequest request)throws RemoteException {
		String sHtml="";
		
		
		String sComType=BaseObject.toString(bComBaseObject, "FVC_TYPE");
		String sIsBase=BaseObject.toString(bComBaseObject, "FVC_ISBASE");
		String sDefaultValue=BaseObject.toString(bComBaseObject, "FVC_DEFAULTVALUE");
		String sTmpVaule="";
		
		sTmpVaule=sDefaultValue;
		
		if (sIsBase.equals("1")) sHtml=getBaseComHtmlForDesign(bComBaseObject, request);   
		else sHtml=getHtmlCode(bComBaseObject,sComType,BaseObject.toString(bComBaseObject, "FVC_OTHER"),"", request);
		
		
		if (sTmpVaule.equals("")==false) sHtml=sHtml.replaceAll("<%="+Constants.COM_PARA_VALUE_NAME+"%>",sTmpVaule);
		
		
		if (sHtml.indexOf("<%=")<0)  return sHtml;
		if (lComParalist!=null)
		{
			int length=lComParalist.size();
			BaseObject baseobj=null;
			String sPareType="";
			String sParaName="";
			String sParaValue="";
			String sParaIsFun="";
			for (int i=0; i < length; i++) {
				baseobj = (BaseObject) lComParalist.get(i);
				sPareType=BaseObject.toString(baseobj, "FVC_TYPE");
				
				if (sPareType.equals(sComType))
				{
					sParaName=BaseObject.toString(baseobj, "FVC_NAME");
					sParaValue=BaseObject.toString(baseobj, "FVC_VALUE");
					sParaIsFun=BaseObject.toString(baseobj, "FVC_ISFUN");
					
					if (sParaIsFun.equals("1"))
					{
						request.setAttribute(Constants.COM_CURRENT_BASEOBJECT,bComBaseObject);
						//BasePrint.println(sParaValue);
						//BasePrint.println(replHtmlWihtPara(reValuesWihtField(sParaValue,bComBaseObject)));
						String sTMP=sParaValue;
						sTMP=replHtmlWihtPara(reValuesWihtField(sTMP,bComBaseObject,false), request);
						sTMP=reComDisegnConstans(sTMP,bComBaseObject);
						sTMP=reValuesWihtField(sTMP,bComBaseObject,true);
						Object obj=this.exeFuntion(BaseObject.toString(baseobj,"FVC_FUNNAME"),sTMP, request);
						
						HashMap map = new HashMap();   
						map=(HashMap)obj;
						if (map!=null)
						{
							Iterator iter = map.keySet().iterator();
							while (iter.hasNext()) {
								sParaName = (String) iter.next();
								sParaValue=map.get(sParaName).toString();
								
								if (sParaValue.equals("")==false) 
								{
									sHtml=sHtml.replaceAll(sParaName+"=\"<%="+sParaName+"%>\";",sParaName+"=\""+sParaValue+"\";");
									sHtml=sHtml.replaceAll(sParaName+"=<%="+sParaName+"%>;",sParaName+":"+sParaValue+";");
									
									sHtml=sHtml.replaceAll(sParaName+"=\"<%="+sParaName+"%>\"",sParaName+"=\""+sParaValue+"\"");
									sHtml=sHtml.replaceAll(sParaName+"=<%="+sParaName+"%>",sParaName+":"+sParaValue);
									
									sHtml=sHtml.replaceAll(sParaName+":<%="+sParaName+"%>",sParaName+":"+sParaValue);
									sHtml=sHtml.replaceAll(sParaName+":\"<%="+sParaName+"%>\"",sParaName+":\""+sParaValue+"\"");
									sHtml=sHtml.replaceAll("\"<%="+sParaName+"%>\"","\""+sParaValue+"\"");
									sHtml=sHtml.replaceAll("<%="+sParaName+"%>",sParaValue);
								}								
							}
						}
						
						sParaName = "";
						sParaValue="";							
					}
					
					if (sParaValue.equals("")==false) 
					{
						sHtml=sHtml.replaceAll(sParaName+"=\"<%="+sParaName+"%>\";",sParaName+"=\""+sParaValue+"\";");
						sHtml=sHtml.replaceAll(sParaName+"=<%="+sParaName+"%>;",sParaName+":"+sParaValue+";");
						
						sHtml=sHtml.replaceAll(sParaName+"=\"<%="+sParaName+"%>\"",sParaName+"=\""+sParaValue+"\"");
						sHtml=sHtml.replaceAll(sParaName+"=<%="+sParaName+"%>",sParaName+":"+sParaValue);
						
						sHtml=sHtml.replaceAll(sParaName+":<%="+sParaName+"%>",sParaName+":"+sParaValue);
						sHtml=sHtml.replaceAll(sParaName+":\"<%="+sParaName+"%>\"",sParaName+":\""+sParaValue+"\"");
						sHtml=sHtml.replaceAll("\"<%="+sParaName+"%>\"","\""+sParaValue+"\"");
						sHtml=sHtml.replaceAll("<%="+sParaName+"%>",sParaValue);
					}
					
				}
				
			}
		}
		if (Constants.COM_ISFUNCTION_NAMELIST.indexOf(sComType)>=0)
		{
			String sTMP=getExtendComHtml(bComBaseObject,lComParalist, request);
			if (sTMP.equals("")==false) sHtml=sHtml.replaceAll(Constants.COM_EXTENCOM_CONNAME,sTMP);
		}
		
		sHtml=reValuesWihtFieldDesign(sHtml,bComBaseObject);
		sHtml=reComDisegnConstans(sHtml,bComBaseObject);
		sHtml=reValuesWihtFieldDesign(sHtml,bComBaseObject);
		return sHtml;
	}
	
	
	public String reValuesWihtFieldDesign(String sHtml,BaseObject baseobj) throws RemoteException {
		if (sHtml.equals("")) return sHtml;
		if (sHtml.indexOf("<%=")<0)  return sHtml;
		if (baseobj != null) {
			int length=baseobj.getFieldCount();
			ArrayList list = baseobj.getFieldName();
			String sTMP="";
			for (int i=0; i < length; i++) {
				sTMP=BaseObject.toString(baseobj, list.get(i).toString());
				if (sTMP.equals("")==false) sHtml=sHtml.replaceAll("<%="+list.get(i).toString()+"%>",sTMP);
			}
		}
		return sHtml;
	}	
	
	
	
	/**
	 * 更新页面元素位置
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return @throws
	 *         Exception
	 */
	public void updateComponentPos(HttpServletRequest request) throws RemoteException {
		String sActType="";
		//BasePrint.println("updateComponentPos:here");
		if ((request.getParameter("ActType")!=null)&&(request.getParameter("ActType").toString().equals("")==false)){
			sActType=request.getParameter("ActType").toString();
		}
		String sAppName=getAppName(request);

		String strTABLE_APPCOMP=getAppMainParameter("FVC_TABLE_APPCOMP",request);
		if ((strTABLE_APPCOMP.indexOf(".")<0)&&(strTABLE_APPCOMP.equals("")==false)&&(strTABLE_APPCOMP!=null)&&(strTABLE_APPCOMP.indexOf("select")<0)&&(strTABLE_APPCOMP.indexOf("SELECT")<0))
		{
			strTABLE_APPCOMP=DB_USER+"."+strTABLE_APPCOMP;
		}
		
		
		try {
			
			if (sActType.equals("ADD")){
				String sAddComType=request.getParameter("AddComType");
				String sSeq=""+this.getSequenceValue(Constants.PARAMETER_SEQ_NAME);
				String sSQL= "insert into "+strTABLE_APPCOMP+"(FVC_APPNAME, FVC_TYPE, FVC_HTMLID, FVC_HTMLNAME, FVC_TITLENAME, FVC_DEFAULTVALUE, FVC_HEIGHT, FVC_WIDTH, FVC_TOP, FVC_LEFT, FVC_BACKCOLOR, FVC_ONCLICK, FVC_ONDBCLICK, FVC_ONBLUE, FVC_ONCHANGE, FVC_ONFOCUS, FVC_ONKEYDOWN, FVC_ONKEYUP, FVC_ONMOUSEOUT, FVC_ONMOUSEMOVE, FVC_ONMOUSEDOWN, FVC_ONMOUSEUP, FVC_DATATYPE, FVC_DATASOURCE, FVC_SHOWORDER, FVC_IFDIV, FVC_BORDER, FVC_CURSOR, FVC_IFLIST, FVC_LISTWIDTH, FNB_LISTORDER, FVC_LISTDATASOURCE, FVC_DISPLAY, FVC_URL, FVC_READONLY, FVC_ENABLED, FVC_IFNULL, FVC_LISTONCLICK, FVC_IFDIVSCROLL, FVC_COMCLASS, FVC_IFJG, FVC_LISTSORTFIELD, FVC_CONTAINER, FVC_IFNOTPURVIEW, FVC_COMTYPE, FVC_ALIGN, FVC_VALIGN, FVC_FIELDNAME, FVC_IFSCROLL, FNB_ID, FVC_ISBASE, FVC_OTHER, FVC_MAXLEN, FVC_DIVINDEX, FVC_OBJECTNAME) " +
				"select '"+sAppName+"', FVC_TYPE, 'jh"+sSeq+"', 'jh"+sSeq+"', FVC_TITLENAME, FVC_DEFAULTVALUE, FVC_HEIGHT, FVC_WIDTH, FVC_TOP, FVC_LEFT, FVC_BACKCOLOR, FVC_ONCLICK, FVC_ONDBCLICK, FVC_ONBLUE, FVC_ONCHANGE, FVC_ONFOCUS, FVC_ONKEYDOWN, FVC_ONKEYUP, FVC_ONMOUSEOUT, FVC_ONMOUSEMOVE, FVC_ONMOUSEDOWN, FVC_ONMOUSEUP, FVC_DATATYPE, FVC_DATASOURCE, FVC_SHOWORDER, FVC_IFDIV, FVC_BORDER, FVC_CURSOR, FVC_IFLIST, FVC_LISTWIDTH, FNB_LISTORDER, FVC_LISTDATASOURCE, FVC_DISPLAY, FVC_URL, FVC_READONLY, FVC_ENABLED, FVC_IFNULL, FVC_LISTONCLICK, FVC_IFDIVSCROLL, FVC_COMCLASS, FVC_IFJG, FVC_LISTSORTFIELD, FVC_CONTAINER, FVC_IFNOTPURVIEW, FVC_COMTYPE, FVC_ALIGN, FVC_VALIGN, FVC_FIELDNAME, FVC_IFSCROLL, "+sSeq+", FVC_ISBASE, FVC_OTHER, FVC_MAXLEN, FVC_DIVINDEX, FVC_OBJECTNAME  from "+DB_USER+".T_INITCOM where FVC_TYPE='"+sAddComType+"'";
				this.executeSQL(sSQL);
				
				return;
			}
			
			String[] sHtmlID;
			if ((request.getParameter("COMHTMLID")!=null)&&(request.getParameter("COMHTMLID").toString().equals("")==false)){
				sHtmlID=request.getParameter("COMHTMLID").toString().split(",");
			}
			else
			{
				sHtmlID=null;
			}
			
			String[] sTop;
			if ((request.getParameter("COMTOP")!=null)&&(request.getParameter("COMTOP").toString().equals("")==false)){
				sTop=request.getParameter("COMTOP").toString().split(",");
			}
			else
			{
				sTop=null;
			}
			
			String[] sLeft;
			if ((request.getParameter("COMLEFT")!=null)&&(request.getParameter("COMLEFT").toString().equals("")==false)){
				sLeft=request.getParameter("COMLEFT").toString().split(",");
			}
			else
			{
				sLeft=null;
			}
			
			
			String[] sHeight;
			if ((request.getParameter("COMHEIGHT")!=null)&&(request.getParameter("COMHEIGHT").toString().equals("")==false)){
				sHeight=request.getParameter("COMHEIGHT").toString().split(",");
			}
			else
			{
				sHeight=null;
			}
			
			String[] sWidth;
			if ((request.getParameter("COMWIDTH")!=null)&&(request.getParameter("COMWIDTH").toString().equals("")==false)){
				sWidth=request.getParameter("COMWIDTH").toString().split(",");
			}
			else
			{
				sWidth=null;
			}
			
			
			
			if (sActType.equals("COPY")){
				String sSelectID=request.getParameter("COMHTMLID").toString();
				sSelectID=sSelectID.replaceAll(",","','");
				sSelectID="'"+sSelectID+"'";
				String sSQL="";
				String strFromAppNAme=request.getParameter("FromAppName").toString();
				
				
				long sID;
				String stop="";
				String sleft="";
				
				ArrayList list=this.selectSQL(strTABLE_APPCOMP,"select * from "+strTABLE_APPCOMP+" where FVC_APPNAME='"+strFromAppNAme+"' and FVC_HTMLID in ("+sSelectID+")");
				if (list!=null)
				{
					int leng=list.size();
					BaseObject baseobj;
					for (int i=0; i < leng; i++) {
						baseobj = (BaseObject) list.get(i);
						sID=this.getSequenceValue(Constants.PARAMETER_SEQ_NAME);
						
						sSQL="insert into "+strTABLE_APPCOMP+"(FVC_TYPE, FVC_HTMLID, FVC_APPNAME, FNB_ID, FVC_HTMLNAME, FVC_TITLENAME, FVC_DEFAULTVALUE, FVC_HEIGHT, FVC_WIDTH, FVC_TOP, FVC_LEFT, FVC_BACKCOLOR, FVC_ONCLICK, FVC_ONDBCLICK, FVC_ONBLUE, FVC_ONCHANGE, FVC_ONFOCUS, FVC_ONKEYDOWN, FVC_ONKEYUP, FVC_ONMOUSEOUT, FVC_ONMOUSEMOVE, FVC_ONMOUSEDOWN, FVC_ONMOUSEUP, FVC_DATATYPE, FVC_DATASOURCE, FVC_SHOWORDER, FVC_IFDIV, FVC_BORDER, FVC_CURSOR, FVC_IFLIST, FVC_LISTWIDTH, FNB_LISTORDER, FVC_LISTDATASOURCE, FVC_DISPLAY, FVC_URL, FVC_READONLY, FVC_ENABLED, FVC_IFNULL, FVC_LISTONCLICK, FVC_IFDIVSCROLL, FVC_COMCLASS, FVC_IFJG, FVC_LISTSORTFIELD, FVC_CONTAINER, FVC_IFNOTPURVIEW, FVC_COMTYPE, FVC_ALIGN, FVC_VALIGN, FVC_FIELDNAME, FVC_IFSCROLL, FVC_ISBASE, FVC_OTHER, FVC_MAXLEN, FVC_DIVINDEX, FVC_OBJECTNAME) ";
						if (strFromAppNAme.equals(sAppName))
						{
							stop=BaseObject.toString(baseobj, "FVC_TOP");
							stop=stop.toUpperCase().replaceAll("PX","");
							stop=stop.replaceAll("[^0-9]", "");
							if (stop==null) stop="";
							if (stop.equals("")) stop="0";
							if ((stop.equals("")==false)&&(stop.indexOf("%")<0)) stop=String.valueOf(Integer.parseInt(stop)+10);
							sleft=BaseObject.toString(baseobj, "FVC_LEFT");
							sleft=sleft.toUpperCase().replaceAll("PX","");
							if (sleft==null) sleft="";
							if (sleft.equals("")) sleft="0";
							sleft=sleft.replaceAll("[^0-9]", "");
							if ((sleft.equals("")==false)&&(sleft.indexOf("%")<0)) sleft=String.valueOf(Integer.parseInt(sleft)+10);
							
							sSQL=sSQL+" select FVC_TYPE,'jh"+sID+"', '"+sAppName+"', "+sID+", FVC_HTMLNAME, FVC_TITLENAME, FVC_DEFAULTVALUE, FVC_HEIGHT, FVC_WIDTH, '"+stop+"', '"+sleft+"', FVC_BACKCOLOR, FVC_ONCLICK, FVC_ONDBCLICK, FVC_ONBLUE, FVC_ONCHANGE, FVC_ONFOCUS, FVC_ONKEYDOWN, FVC_ONKEYUP, FVC_ONMOUSEOUT, FVC_ONMOUSEMOVE, FVC_ONMOUSEDOWN, FVC_ONMOUSEUP, FVC_DATATYPE, FVC_DATASOURCE, FVC_SHOWORDER, FVC_IFDIV, FVC_BORDER, FVC_CURSOR, FVC_IFLIST, FVC_LISTWIDTH, FNB_LISTORDER, FVC_LISTDATASOURCE, FVC_DISPLAY, FVC_URL, FVC_READONLY, FVC_ENABLED, FVC_IFNULL, FVC_LISTONCLICK, FVC_IFDIVSCROLL, FVC_COMCLASS, FVC_IFJG, FVC_LISTSORTFIELD, FVC_CONTAINER, FVC_IFNOTPURVIEW, FVC_COMTYPE, FVC_ALIGN, FVC_VALIGN, FVC_FIELDNAME, FVC_IFSCROLL, FVC_ISBASE, FVC_OTHER, FVC_MAXLEN, FVC_DIVINDEX, FVC_OBJECTNAME from "+strTABLE_APPCOMP+" where FVC_APPNAME ='"+strFromAppNAme + "' and FVC_HTMLID='"+BaseObject.toString(baseobj, "FVC_HTMLID")+"'";
						}
						else
						{
							sSQL=sSQL+" select FVC_TYPE,'jh"+sID+"', '"+sAppName+"', "+sID+", FVC_HTMLNAME, FVC_TITLENAME, FVC_DEFAULTVALUE, FVC_HEIGHT, FVC_WIDTH, FVC_TOP, FVC_LEFT, FVC_BACKCOLOR, FVC_ONCLICK, FVC_ONDBCLICK, FVC_ONBLUE, FVC_ONCHANGE, FVC_ONFOCUS, FVC_ONKEYDOWN, FVC_ONKEYUP, FVC_ONMOUSEOUT, FVC_ONMOUSEMOVE, FVC_ONMOUSEDOWN, FVC_ONMOUSEUP, FVC_DATATYPE, FVC_DATASOURCE, FVC_SHOWORDER, FVC_IFDIV, FVC_BORDER, FVC_CURSOR, FVC_IFLIST, FVC_LISTWIDTH, FNB_LISTORDER, FVC_LISTDATASOURCE, FVC_DISPLAY, FVC_URL, FVC_READONLY, FVC_ENABLED, FVC_IFNULL, FVC_LISTONCLICK, FVC_IFDIVSCROLL, FVC_COMCLASS, FVC_IFJG, FVC_LISTSORTFIELD, FVC_CONTAINER, FVC_IFNOTPURVIEW, FVC_COMTYPE, FVC_ALIGN, FVC_VALIGN, FVC_FIELDNAME, FVC_IFSCROLL, FVC_ISBASE, FVC_OTHER, FVC_MAXLEN, FVC_DIVINDEX, FVC_OBJECTNAME from "+strTABLE_APPCOMP+" where FVC_APPNAME ='"+strFromAppNAme + "' and FVC_HTMLID='"+BaseObject.toString(baseobj, "FVC_HTMLID")+"'";
						}
						this.executeSQL(sSQL);

					}
				}			
				return;
			}
			
			//BasePrint.println("sActType:"+sActType);
			if (sActType.equals("UPDATE")){
				int leng=sHtmlID.length;
				for (int j = 0; j<leng;j++)
				{
					String sSQL= "update "+strTABLE_APPCOMP+" set FVC_TOP='"+sTop[j]+"', FVC_LEFT='"+sLeft[j]+"' where FVC_APPNAME='" + sAppName + "' and FVC_HTMLID='" + sHtmlID[j]+"'";
					this.executeSQL(sSQL);
				}
				return;
			}
			
			
			//BasePrint.println("sActType:"+sActType);
			if (sActType.equals("RESIZE")){
				int leng=sHtmlID.length;
				for (int j = 0; j<leng;j++)
				{
					String sSQL= "update "+strTABLE_APPCOMP+" set FVC_HEIGHT='"+sHeight[j]+"', FVC_WIDTH='"+sWidth[j]+"' where FVC_APPNAME='" + sAppName + "' and FVC_HTMLID='" + sHtmlID[j]+"'";
					this.executeSQL(sSQL);
				}
				return;
			}
			
			if (sActType.equals("DELETE")){
				String sSelectID=request.getParameter("COMHTMLID").toString();
				sSelectID=sSelectID.replaceAll(",","','");
				sSelectID="'"+sSelectID+"'";
				this.executeSQL("delete from "+strTABLE_APPCOMP+" where FVC_HTMLID in ("+sSelectID+") and FVC_APPNAME='"+sAppName+"'");
				
				return;
			}
			
			if (sActType.equals("LEFTSNAPE")){
				String sSelectID=request.getParameter("COMHTMLID").toString();
				sSelectID=sSelectID.replaceAll(",","','");
				sSelectID="'"+sSelectID+"'";
				String startcom="";
				if (sSelectID.indexOf(",")>=0)	startcom=sSelectID.substring(0,sSelectID.indexOf(","));
				else startcom=sSelectID;
				String sleft=this.getValue("select FVC_LEFT from "+strTABLE_APPCOMP+" where FVC_HTMLID="+startcom+" and FVC_APPNAME='"+sAppName+"'");
				this.executeSQL("update "+strTABLE_APPCOMP+" set FVC_ALIGN='LEFT',FVC_LEFT='"+sleft+"' where FVC_HTMLID in ("+sSelectID+") and FVC_APPNAME='"+sAppName+"'");
				return;
			}
			
			if (sActType.equals("RIGHTSNAPE")){
				String sSelectID=request.getParameter("COMHTMLID").toString();
				sSelectID=sSelectID.replaceAll(",","','");
				sSelectID="'"+sSelectID+"'";
				String startcom="";
				if (sSelectID.indexOf(",")>=0)	startcom=sSelectID.substring(0,sSelectID.indexOf(","));
				else startcom=sSelectID;
				String sleft=this.getValue("select FVC_LEFT from "+strTABLE_APPCOMP+" where FVC_HTMLID="+startcom+" and FVC_APPNAME='"+sAppName+"'");
				String swidht=this.getValue("select FVC_WIDTH from "+strTABLE_APPCOMP+" where FVC_HTMLID="+startcom+" and FVC_APPNAME='"+sAppName+"'");
				
				if (swidht.indexOf("%")>0) return;
				
				sleft=sleft.toUpperCase().replaceAll("PX","");
				swidht=swidht.toUpperCase().replaceAll("PX","");
				swidht=swidht.replaceAll("[^0-9]", "");
				if ((sleft.equals("")==false)&&(swidht.equals("")==false)) sleft=String.valueOf(Integer.parseInt(sleft)+Integer.parseInt(swidht));
				
				int leng=sHtmlID.length;
				String swidhttmp;
				String slefttmp="";
				for (int j = 0; j<leng;j++)
				{
					swidhttmp=this.getValue("select FVC_WIDTH from "+strTABLE_APPCOMP+" where FVC_HTMLID='"+sHtmlID[j]+"' and FVC_APPNAME='"+sAppName+"'");
					
					if (swidht.indexOf("%")<0)
					{
						swidhttmp=swidhttmp.toUpperCase().replaceAll("PX","");
						if (swidhttmp.equals("")==false)
						{
							slefttmp=String.valueOf(Integer.parseInt(sleft)-Integer.parseInt(swidhttmp));
							this.executeSQL("update "+strTABLE_APPCOMP+" set FVC_ALIGN='RIGHT', FVC_LEFT='"+slefttmp+"' where FVC_HTMLID ='"+sHtmlID[j]+"' and FVC_APPNAME='"+sAppName+"'");
							
						}
					}
				}
				return;
			}
			
			if (sActType.equals("TOPSNAPE")){
				String sSelectID=request.getParameter("COMHTMLID").toString();
				sSelectID=sSelectID.replaceAll(",","','");
				sSelectID="'"+sSelectID+"'";
				String startcom="";
				if (sSelectID.indexOf(",")>=0)	startcom=sSelectID.substring(0,sSelectID.indexOf(","));
				else startcom=sSelectID;
				String stop=this.getValue("select FVC_TOP from "+strTABLE_APPCOMP+" where FVC_HTMLID="+startcom+" and FVC_APPNAME='"+sAppName+"'");
				this.executeSQL("update "+strTABLE_APPCOMP+" set FVC_TOP='"+stop+"' where FVC_HTMLID in ("+sSelectID+") and FVC_APPNAME='"+sAppName+"'");
				return;
			}
			
			
			if (sActType.equals("SAMEWIDTH")){
				String sSelectID=request.getParameter("COMHTMLID").toString();
				sSelectID=sSelectID.replaceAll(",","','");
				sSelectID="'"+sSelectID+"'";
				String startcom="";
				if (sSelectID.indexOf(",")>=0)	startcom=sSelectID.substring(0,sSelectID.indexOf(","));
				else startcom=sSelectID;
				String swidht=this.getValue("select FVC_WIDTH from "+strTABLE_APPCOMP+" where FVC_HTMLID="+startcom+" and FVC_APPNAME='"+sAppName+"'");
				this.executeSQL("update "+strTABLE_APPCOMP+" set FVC_WIDTH='"+swidht+"' where FVC_HTMLID in ("+sSelectID+") and FVC_APPNAME='"+sAppName+"'");
				return;
			}
			
			if (sActType.equals("SAMEHEIGHT")){
				String sSelectID=request.getParameter("COMHTMLID").toString();
				sSelectID=sSelectID.replaceAll(",","','");
				sSelectID="'"+sSelectID+"'";
				String startcom="";
				if (sSelectID.indexOf(",")>=0)	startcom=sSelectID.substring(0,sSelectID.indexOf(","));
				else startcom=sSelectID;
				
				String sheight=this.getValue("select FVC_HEIGHT from "+strTABLE_APPCOMP+" where FVC_HTMLID="+startcom+" and FVC_APPNAME='"+sAppName+"'");
				this.executeSQL("update "+strTABLE_APPCOMP+" set FVC_HEIGHT='"+sheight+"' where FVC_HTMLID in ("+sSelectID+") and FVC_APPNAME='"+sAppName+"'");
				
				return;
			}
			
			if (sActType.equals("EVENSPACEY")){
				String sSelectID=request.getParameter("COMHTMLID").toString();
				sSelectID=sSelectID.replaceAll(",","','");
				sSelectID="'"+sSelectID+"'";
				String sTopComPos="";
				int nComPos=0;
				String sBottomComHeight="";
				String sBottomComPos="";
				String sComheight="0";
				int nAllSpace=0;
				int nSpace=0;
				
				String sSUMHEIGHT=this.getValue("select sum(HEIGHT) as SUMHEIGHT  from (select  case when FVC_HEIGHT is null or FVC_HEIGHT='' then case when FVC_TYPE='INPUT' then 12 else case when FVC_TYPE='LABLE' then 15 else 12 end end else FVC_HEIGHT end as HEIGHT  from "+strTABLE_APPCOMP+" where FVC_HTMLID in ("+sSelectID+") and FVC_APPNAME='"+sAppName+"') A");
				
				ArrayList list=this.selectSQL(strTABLE_APPCOMP,"select * from "+strTABLE_APPCOMP+" where FVC_HTMLID in ("+sSelectID+") and FVC_APPNAME='"+sAppName+"'");
				if (list!=null)
				{
					int leng=list.size();
					if (leng>2)
					{
						
						int topleng=sTop.length;
						int[] nTop= {10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000};
						for (int j = 0; j<topleng;j++)
						{
							sTop[j]=sTop[j].replaceAll("px", "");
							sTop[j]=sTop[j].replaceAll("PX", "");
							sTop[j]=sTop[j].replaceAll("[^0-9]", "");
							nTop[j]=Integer.valueOf(sTop[j]).intValue();
						}
						Arrays.sort(nTop);
						
						ArrayList rAL = new ArrayList();
						String stmp="";
						BaseObject tmpbaseobj;
						for (int i = 0; i<topleng;i++)
						{
							for (int ii = 0; ii<topleng;ii++)
							{
								if ((Integer.valueOf(sTop[ii]).intValue()==nTop[i])&&(stmp.indexOf(sHtmlID[ii])<0))
								{
									stmp=stmp+","+sHtmlID[ii];
									for (int iii=0; iii < leng; iii++) {
										tmpbaseobj = (BaseObject) list.get(iii);
										if (BaseObject.toString(tmpbaseobj, "FVC_HTMLID").equals(sHtmlID[ii]))
										{
											rAL.add(tmpbaseobj);
										}
									}
								}
							}
						}
						
						list=rAL;					
						
						
						
						BaseObject baseobj = (BaseObject) list.get(0);
						sTopComPos=BaseObject.toString(baseobj, "FVC_TOP");
						sTopComPos=sTopComPos.replaceAll("px", "").replaceAll("PX", "");
						sTopComPos=sTopComPos.replaceAll("[^0-9]", "");
						nComPos=Integer.valueOf(sTopComPos).intValue();
						baseobj = (BaseObject) list.get(leng-1);
						sBottomComHeight=BaseObject.toString(baseobj, "FVC_HEIGHT");
						sBottomComHeight=getComDefaultHeigth(sBottomComHeight,BaseObject.toString(baseobj, "FVC_TYPE"));
						sBottomComHeight=sBottomComHeight.replaceAll("px", "").replaceAll("PX", "");
						
						sBottomComPos=BaseObject.toString(baseobj, "FVC_TOP");
						sBottomComPos=sBottomComPos.replaceAll("px", "").replaceAll("PX", "");
						
						nAllSpace=Integer.valueOf(sBottomComPos).intValue()-Integer.valueOf(sTopComPos).intValue()+Integer.valueOf(sBottomComHeight).intValue();
						nSpace=nAllSpace-Integer.valueOf(sSUMHEIGHT).intValue();
						nSpace=(nSpace/(leng-1));
						for (int i=1; i < leng; i++) {
							baseobj = (BaseObject) list.get(i-1);
							sComheight=BaseObject.toString(baseobj, "FVC_HEIGHT");
							sComheight=sComheight.replaceAll("px", "").replaceAll("PX", "");
							sComheight=sComheight.replaceAll("[^0-9]", "");
							sComheight=getComDefaultHeigth(sComheight,BaseObject.toString(baseobj, "FVC_TYPE"));
							nComPos=nComPos+Integer.valueOf(sComheight).intValue()+nSpace;
							baseobj = (BaseObject) list.get(i);
							this.executeSQL("update "+strTABLE_APPCOMP+" set FVC_TOP='"+nComPos+"' where FVC_HTMLID in ('"+BaseObject.toString(baseobj, "FVC_HTMLID")+"') and FVC_APPNAME='"+sAppName+"'");
						}
					}
				}
				
				return;
			}
			
			if (sActType.equals("EVENSPACEX")){
				String sSelectID=request.getParameter("COMHTMLID").toString();
				sSelectID=sSelectID.replaceAll(",","','");
				sSelectID="'"+sSelectID+"'";
				String sTopComPos="";
				int nComPos=0;
				String sBottomComHeight="";
				String sBottomComPos="";
				String sComheight="0";
				int nAllSpace=0;
				int nSpace=0;
				
				String sSUMHEIGHT=this.getValue("select sum(WIDTH) as SUMWIDTH  from (select  case when FVC_WIDTH is null or FVC_WIDTH='' then case when FVC_TYPE='INPUT' then 12 else case when FVC_TYPE='LABLE' then 9 else case when FVC_TYPE='TEXTAREA' then 28 else 12 end end end else FVC_WIDTH end as WIDTH  from "+strTABLE_APPCOMP+" where FVC_HTMLID in ("+sSelectID+") and FVC_APPNAME='"+sAppName+"') A");
				
				ArrayList list=this.selectSQL(strTABLE_APPCOMP,"select * from "+strTABLE_APPCOMP+" where FVC_HTMLID in ("+sSelectID+") and FVC_APPNAME='"+sAppName+"'");
				if (list!=null)
				{
					int leng=list.size();
					if (leng>2)
					{
						
						
						int leftleng=sLeft.length;
						int[] nLeft= {10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000,10000};
						for (int j = 0; j<leftleng;j++)
						{
							nLeft[j]=Integer.valueOf(sLeft[j].replaceAll("[^0-9]", "")).intValue();
						}
						Arrays.sort(nLeft);
						
						ArrayList rAL = new ArrayList();
						String stmp="";
						BaseObject tmpbaseobj;
						for (int i = 0; i<leftleng;i++)
						{
							for (int ii = 0; ii<leftleng;ii++)
							{
								if ((Integer.valueOf(sLeft[ii].replaceAll("[^0-9]", "")).intValue()==nLeft[i])&&(stmp.indexOf(sHtmlID[ii])<0))
								{
									stmp=stmp+","+sHtmlID[ii];
									for (int iii=0; iii < leng; iii++) {
										tmpbaseobj = (BaseObject) list.get(iii);
										if (BaseObject.toString(tmpbaseobj, "FVC_HTMLID").equals(sHtmlID[ii]))
										{
											rAL.add(tmpbaseobj);
										}
									}
								}
							}
						}
						
						list=rAL;					
						
						BaseObject baseobj = (BaseObject) list.get(0);
						sTopComPos=BaseObject.toString(baseobj, "FVC_LEFT");
						nComPos=Integer.valueOf(sTopComPos.replaceAll("[^0-9]", "")).intValue();
						baseobj = (BaseObject) list.get(leng-1);
						sBottomComHeight=BaseObject.toString(baseobj, "FVC_WIDTH");
						sBottomComHeight=getComDefaultWidth(sBottomComHeight,BaseObject.toString(baseobj, "FVC_TYPE"));
						
						sBottomComPos=BaseObject.toString(baseobj, "FVC_LEFT");
						
						nAllSpace=Integer.valueOf(sBottomComPos.replaceAll("[^0-9]", "")).intValue()-Integer.valueOf(sTopComPos.replaceAll("[^0-9]", "")).intValue()+Integer.valueOf(sBottomComHeight.replaceAll("[^0-9]", "")).intValue();
						nSpace=nAllSpace-Integer.valueOf(sSUMHEIGHT.replaceAll("[^0-9]", "")).intValue();
						nSpace=(nSpace/(leng-1));
						for (int i=1; i < leng; i++) {
							baseobj = (BaseObject) list.get(i-1);
							sComheight=BaseObject.toString(baseobj, "FVC_WIDTH");
							sComheight=getComDefaultWidth(sComheight,BaseObject.toString(baseobj, "FVC_TYPE"));
							nComPos=nComPos+Integer.valueOf(sComheight).intValue()+nSpace;
							baseobj = (BaseObject) list.get(i);
							this.executeSQL("update "+strTABLE_APPCOMP+" set FVC_LEFT='"+nComPos+"' where FVC_HTMLID in ('"+BaseObject.toString(baseobj, "FVC_HTMLID")+"') and FVC_APPNAME='"+sAppName+"'");
						}
					}
				}
				
				return;
			}
			
		}
		catch (Exception e) {
			//sessionContext.setRollbackOnly();
			e.printStackTrace();
			throw new EJBException(e.toString());
		}
		
	}
	
	
	public void designFormCopy(HttpServletRequest request) throws RemoteException {
		String sFromAppName=request.getParameter("FromAppName").toString();
		String sToAppName=request.getParameter("ToAppName").toString();
		String sToClass=request.getParameter("ToClass").toString();
		long sID=this.getSequenceValue(Constants.PARAMETER_SEQ_NAME);
		String strTABLE_APPCOMP=this.getValue("select FVC_TABLE_APPCOMP from "+DB_USER+".T_APPMAIN where FVC_APPNAME in ('" +sFromAppName + "')");
		if ((strTABLE_APPCOMP.indexOf(".")<0)&&(strTABLE_APPCOMP.equals("")==false)&&(strTABLE_APPCOMP!=null)&&(strTABLE_APPCOMP.indexOf("select")<0)&&(strTABLE_APPCOMP.indexOf("SELECT")<0))
		{
			strTABLE_APPCOMP=DB_USER+"."+strTABLE_APPCOMP;
		}
		
		try {
			
			/******保存数据******/
			if ((sFromAppName.equals("")==false)&&(sToAppName.equals("")==false)){
				HttpSession session = request.getSession();
				User user= (User) session.getAttribute(User.SESSION_USER);
				
				String sSQL="insert into  "+DB_USER+".T_APPMAIN(FNB_ID,FNB_CLASSID,FVC_APPNAME, FVC_FORMNAME, FVC_WEBMODELRUL, FNB_FLOWID, FVC_IFFLOW, FVC_TABLE_APPCOMP,DESIGN_USER_ID)" +
				" select " +sID + " as FNB_ID, "+sToClass+" as FNB_CLASSID ,'"+sToAppName+"' as FVC_APPNAME,  '"+sToAppName+"' as FVC_FORMNAME, FVC_WEBMODELRUL, FNB_FLOWID, FVC_IFFLOW, FVC_TABLE_APPCOMP," +user.getUserID()+
				" from  "+DB_USER+".T_APPMAIN where FVC_APPNAME in ('" +sFromAppName + "')";
				this.executeSQL(sSQL);
				
				String sJs=this.getValue("select FVC_JAVASCRIPT from  "+DB_USER+".T_APPMAIN where FVC_APPNAME='"+sFromAppName+"'");
				sJs=sJs.replaceAll("'","''");
				
				sSQL="update  "+DB_USER+".T_APPMAIN set FVC_JAVASCRIPT='"+sJs+"' where FVC_APPNAME='"+sToAppName+"'";
				this.executeSQL(sSQL);
				
				
				ArrayList list=this.selectSQL(strTABLE_APPCOMP,"select * from "+strTABLE_APPCOMP+" where FVC_APPNAME ='"+sFromAppName + "'");
				if (list!=null)
				{
					int leng=list.size();
					BaseObject baseobj;
					for (int i=0; i < leng; i++) {
						baseobj = (BaseObject) list.get(i);
						sID=this.getSequenceValue(Constants.PARAMETER_SEQ_NAME);
						
						sSQL="insert into "+strTABLE_APPCOMP+"(FVC_TYPE, FVC_HTMLID, FVC_APPNAME, FNB_ID, FVC_HTMLNAME, FVC_TITLENAME, FVC_DEFAULTVALUE, FVC_HEIGHT, FVC_WIDTH, FVC_TOP, FVC_LEFT, FVC_BACKCOLOR, FVC_ONCLICK, FVC_ONDBCLICK, FVC_ONBLUE, FVC_ONCHANGE, FVC_ONFOCUS, FVC_ONKEYDOWN, FVC_ONKEYUP, FVC_ONMOUSEOUT, FVC_ONMOUSEMOVE, FVC_ONMOUSEDOWN, FVC_ONMOUSEUP, FVC_DATATYPE, FVC_DATASOURCE, FVC_SHOWORDER, FVC_IFDIV, FVC_BORDER, FVC_CURSOR, FVC_IFLIST, FVC_LISTWIDTH, FNB_LISTORDER, FVC_LISTDATASOURCE, FVC_DISPLAY, FVC_URL, FVC_READONLY, FVC_ENABLED, FVC_IFNULL, FVC_LISTONCLICK, FVC_IFDIVSCROLL, FVC_COMCLASS, FVC_IFJG, FVC_LISTSORTFIELD, FVC_CONTAINER, FVC_IFNOTPURVIEW, FVC_COMTYPE, FVC_ALIGN, FVC_VALIGN, FVC_FIELDNAME, FVC_IFSCROLL, FVC_ISBASE, FVC_OTHER, FVC_MAXLEN, FVC_DIVINDEX, FVC_OBJECTNAME,FVC_STYLE) ";
						sSQL=sSQL+" select FVC_TYPE,'jh"+sID+"', '"+sToAppName+"', "+sID+", FVC_HTMLNAME, FVC_TITLENAME, FVC_DEFAULTVALUE, FVC_HEIGHT, FVC_WIDTH, FVC_TOP, FVC_LEFT, FVC_BACKCOLOR, FVC_ONCLICK, FVC_ONDBCLICK, FVC_ONBLUE, FVC_ONCHANGE, FVC_ONFOCUS, FVC_ONKEYDOWN, FVC_ONKEYUP, FVC_ONMOUSEOUT, FVC_ONMOUSEMOVE, FVC_ONMOUSEDOWN, FVC_ONMOUSEUP, FVC_DATATYPE, FVC_DATASOURCE, FVC_SHOWORDER, FVC_IFDIV, FVC_BORDER, FVC_CURSOR, FVC_IFLIST, FVC_LISTWIDTH, FNB_LISTORDER, FVC_LISTDATASOURCE, FVC_DISPLAY, FVC_URL, FVC_READONLY, FVC_ENABLED, FVC_IFNULL, FVC_LISTONCLICK, FVC_IFDIVSCROLL, FVC_COMCLASS, FVC_IFJG, FVC_LISTSORTFIELD, FVC_CONTAINER, FVC_IFNOTPURVIEW, FVC_COMTYPE, FVC_ALIGN, FVC_VALIGN, FVC_FIELDNAME, FVC_IFSCROLL, FVC_ISBASE, FVC_OTHER, FVC_MAXLEN, FVC_DIVINDEX, FVC_OBJECTNAME,FVC_STYLE from "+strTABLE_APPCOMP+" where FVC_APPNAME ='"+sFromAppName + "' and FVC_HTMLID='"+BaseObject.toString(baseobj, "FVC_HTMLID")+"'";
						
						this.executeSQL(sSQL);
					}
				}
				
				request.setAttribute("ToAppName",sToAppName);
				
			}
		}
		catch (Exception e) {
			sessionContext.setRollbackOnly();
			e.printStackTrace();
			throw new EJBException(e.toString());
		}
		
		return ;
	}
	
	
	public boolean deleteapp(HttpServletRequest request) throws RemoteException{
		String sFVC_APPNAME=request.getParameter("AppName");
		try
		{
			String strTABLE_APPCOMP = this.getValue("select FVC_TABLE_APPCOMP as value from "+DB_USER+".T_APPMAIN where FVC_APPNAME = '" +sFVC_APPNAME + "'");
			if ((strTABLE_APPCOMP.indexOf(".")<0)&&(strTABLE_APPCOMP.equals("")==false)&&(strTABLE_APPCOMP!=null)&&(strTABLE_APPCOMP.indexOf("select")<0)&&(strTABLE_APPCOMP.indexOf("SELECT")<0))
			{
				strTABLE_APPCOMP=DB_USER+"."+strTABLE_APPCOMP;
			}
			
			if (strTABLE_APPCOMP.equals("")==false){
				String sSQL="delete from "+strTABLE_APPCOMP+" where FVC_APPNAME ='" +sFVC_APPNAME + "'"; 
				this.executeSQL(sSQL);	
			}

			String sSQL="delete from  "+DB_USER+".T_COMPURVIEW where FVC_APPNAME ='" +sFVC_APPNAME + "'"; 
			this.executeSQL(sSQL);	
			
			String sT_SYSCONSTANS="delete from "+DB_USER+".T_SYSCONSTANS where FVC_APPNAME ='" +sFVC_APPNAME + "'"; 
			this.executeSQL(sT_SYSCONSTANS);	

			String sT_FUNCTION="delete from "+DB_USER+".T_FUNCTION where FVC_APPNAME ='" +sFVC_APPNAME + "'"; 
			this.executeSQL(sT_FUNCTION);	
			
			this.executeSQL("delete from "+AnalyseDataSource("T_APPMAIN")+"  where FVC_APPNAME ='" +sFVC_APPNAME + "'");		
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new EJBException(e.toString());
		}
		return true;
	}
	
	public boolean saveComProperty(HttpServletRequest request) throws RemoteException {
		try {
			String strFVC_TABLE_APPCOMP=this.getValue("select FVC_TABLE_APPCOMP from "+AnalyseDataSource("T_APPMAIN")+" where FVC_APPNAME='"+request.getParameter("DesignAppName")+"'");
			if ((strFVC_TABLE_APPCOMP.indexOf(".")<0)&&(strFVC_TABLE_APPCOMP.equals("")==false)&&(strFVC_TABLE_APPCOMP!=null)&&(strFVC_TABLE_APPCOMP.indexOf("select")<0)&&(strFVC_TABLE_APPCOMP.indexOf("SELECT")<0))
			{
				strFVC_TABLE_APPCOMP=DB_USER+"."+strFVC_TABLE_APPCOMP;
			}
			
			String sSQL="";
			if (request.getParameter("FVC_HTMLNAME")!=null)
				sSQL=sSQL+" FVC_HTMLNAME='"+request.getParameter("FVC_HTMLNAME").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_TITLENAME")!=null)
				sSQL=sSQL+" FVC_TITLENAME='"+request.getParameter("FVC_TITLENAME").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_DEFAULTVALUE")!=null)
				sSQL=sSQL+" FVC_DEFAULTVALUE='"+request.getParameter("FVC_DEFAULTVALUE").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_HEIGHT")!=null)
				sSQL=sSQL+" FVC_HEIGHT='"+request.getParameter("FVC_HEIGHT").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_WIDTH")!=null)
				sSQL=sSQL+" FVC_WIDTH='"+request.getParameter("FVC_WIDTH").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_TOP")!=null)
				sSQL=sSQL+" FVC_TOP='"+request.getParameter("FVC_TOP").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_LEFT")!=null)
				sSQL=sSQL+" FVC_LEFT='"+request.getParameter("FVC_LEFT").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_BACKCOLOR")!=null)
				sSQL=sSQL+" FVC_BACKCOLOR='"+request.getParameter("FVC_BACKCOLOR").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_ONCLICK")!=null)
				sSQL=sSQL+" FVC_ONCLICK='"+request.getParameter("FVC_ONCLICK").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_ONDBCLICK")!=null)
				sSQL=sSQL+" FVC_ONDBCLICK='"+request.getParameter("FVC_ONDBCLICK").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_ONBLUE")!=null)
				sSQL=sSQL+" FVC_ONBLUE='"+request.getParameter("FVC_ONBLUE").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_ONCHANGE")!=null)
				sSQL=sSQL+" FVC_ONCHANGE='"+request.getParameter("FVC_ONCHANGE").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_ONFOCUS")!=null)
				sSQL=sSQL+" FVC_ONFOCUS='"+request.getParameter("FVC_ONFOCUS").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_ONKEYDOWN")!=null)
				sSQL=sSQL+" FVC_ONKEYDOWN='"+request.getParameter("FVC_ONKEYDOWN").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_ONKEYUP")!=null)
				sSQL=sSQL+" FVC_ONKEYUP='"+request.getParameter("FVC_ONKEYUP").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_ONMOUSEOUT")!=null)
				sSQL=sSQL+" FVC_ONMOUSEOUT='"+request.getParameter("FVC_ONMOUSEOUT").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_ONMOUSEMOVE")!=null)
				sSQL=sSQL+" FVC_ONMOUSEMOVE='"+request.getParameter("FVC_ONMOUSEMOVE").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_ONMOUSEDOWN")!=null)
				sSQL=sSQL+" FVC_ONMOUSEDOWN='"+request.getParameter("FVC_ONMOUSEDOWN").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_ONMOUSEUP")!=null)
				sSQL=sSQL+" FVC_ONMOUSEUP='"+request.getParameter("FVC_ONMOUSEUP").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_DATATYPE")!=null)
				sSQL=sSQL+" FVC_DATATYPE='"+request.getParameter("FVC_DATATYPE").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_DATASOURCE")!=null)
				sSQL=sSQL+" FVC_DATASOURCE='"+request.getParameter("FVC_DATASOURCE").toString().replaceAll("[']","''")+"',";
			if ((request.getParameter("FVC_SHOWORDER")!=null)&&(request.getParameter("FVC_SHOWORDER").toString().equals("")==false))
				sSQL=sSQL+" FVC_SHOWORDER="+request.getParameter("FVC_SHOWORDER")+",";
			if (request.getParameter("FVC_IFDIV")!=null)
				sSQL=sSQL+" FVC_IFDIV='"+request.getParameter("FVC_IFDIV").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_BORDER")!=null)
				sSQL=sSQL+" FVC_BORDER='"+request.getParameter("FVC_BORDER").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_CURSOR")!=null)
				sSQL=sSQL+" FVC_CURSOR='"+request.getParameter("FVC_CURSOR").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_IFLIST")!=null)
				sSQL=sSQL+" FVC_IFLIST='"+request.getParameter("FVC_IFLIST").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_LISTWIDTH")!=null)
				sSQL=sSQL+" FVC_LISTWIDTH='"+request.getParameter("FVC_LISTWIDTH").toString().replaceAll("[']","''")+"',";
			if ((request.getParameter("FNB_LISTORDER")!=null)&&(request.getParameter("FNB_LISTORDER").toString().equals("")==false))
				sSQL=sSQL+" FNB_LISTORDER="+request.getParameter("FNB_LISTORDER")+",";
			if (request.getParameter("FVC_LISTDATASOURCE")!=null)
				sSQL=sSQL+" FVC_LISTDATASOURCE='"+request.getParameter("FVC_LISTDATASOURCE").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_DISPLAY")!=null)
				sSQL=sSQL+" FVC_DISPLAY='"+request.getParameter("FVC_DISPLAY").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_URL")!=null)
				sSQL=sSQL+" FVC_URL='"+request.getParameter("FVC_URL").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_READONLY")!=null)
				sSQL=sSQL+" FVC_READONLY='"+request.getParameter("FVC_READONLY").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_ENABLED")!=null)
				sSQL=sSQL+" FVC_ENABLED='"+request.getParameter("FVC_ENABLED").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_IFNULL")!=null)
				sSQL=sSQL+" FVC_IFNULL='"+request.getParameter("FVC_IFNULL").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_LISTONCLICK")!=null)
				sSQL=sSQL+" FVC_LISTONCLICK='"+request.getParameter("FVC_LISTONCLICK").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_IFDIVSCROLL")!=null)
				sSQL=sSQL+" FVC_IFDIVSCROLL='"+request.getParameter("FVC_IFDIVSCROLL").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_COMCLASS")!=null)
				sSQL=sSQL+" FVC_COMCLASS='"+request.getParameter("FVC_COMCLASS").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_IFJG")!=null)
				sSQL=sSQL+" FVC_IFJG='"+request.getParameter("FVC_IFJG").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_LISTSORTFIELD")!=null)
				sSQL=sSQL+" FVC_LISTSORTFIELD='"+request.getParameter("FVC_LISTSORTFIELD").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_CONTAINER")!=null)
				sSQL=sSQL+" FVC_CONTAINER='"+request.getParameter("FVC_CONTAINER").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_IFNOTPURVIEW")!=null)
				sSQL=sSQL+" FVC_IFNOTPURVIEW='"+request.getParameter("FVC_IFNOTPURVIEW").toString().replaceAll("[']","''")+"',";
			if ((request.getParameter("FVC_COMTYPE")!=null)&&(request.getParameter("FVC_COMTYPE").toString().equals("")==false))
				sSQL=sSQL+" FVC_COMTYPE="+request.getParameter("FVC_COMTYPE")+",";
			if (request.getParameter("FVC_ALIGN")!=null)
				sSQL=sSQL+" FVC_ALIGN='"+request.getParameter("FVC_ALIGN").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_VALIGN")!=null)
				sSQL=sSQL+" FVC_VALIGN='"+request.getParameter("FVC_VALIGN").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_FIELDNAME")!=null)
				sSQL=sSQL+" FVC_FIELDNAME='"+request.getParameter("FVC_FIELDNAME").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_IFSCROLL")!=null)
				sSQL=sSQL+" FVC_IFSCROLL='"+request.getParameter("FVC_IFSCROLL").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_ISBASE")!=null)
				sSQL=sSQL+" FVC_ISBASE='"+request.getParameter("FVC_ISBASE").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_OTHER")!=null)
				sSQL=sSQL+" FVC_OTHER='"+request.getParameter("FVC_OTHER").toString().replaceAll("[']","''")+"',";
			if ((request.getParameter("FVC_MAXLEN")!=null)&&(request.getParameter("FVC_MAXLEN").toString().equals("")==false))
				sSQL=sSQL+" FVC_MAXLEN="+request.getParameter("FVC_MAXLEN")+",";
			if (request.getParameter("FVC_DIVINDEX")!=null)
				sSQL=sSQL+" FVC_DIVINDEX='"+request.getParameter("FVC_DIVINDEX").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_OBJECTNAME")!=null)
				sSQL=sSQL+" FVC_OBJECTNAME='"+request.getParameter("FVC_OBJECTNAME").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_STYLE")!=null)
				sSQL=sSQL+" FVC_STYLE='"+request.getParameter("FVC_STYLE").toString().replaceAll("[']","''")+"',";
			if (request.getParameter("FVC_POSITION")!=null)
				sSQL=sSQL+" FVC_POSITION='"+request.getParameter("FVC_POSITION").toString().replaceAll("[']","''")+"',";
			
			if (sSQL.length()>0) 
			{
				sSQL=sSQL.substring(0,sSQL.length()-1);
			
			sSQL=" update "+strFVC_TABLE_APPCOMP+" set "+sSQL+" where FVC_APPNAME='"+request.getParameter("DesignAppName")+"' and FVC_HTMLID='"+request.getParameter("FVC_HTMLID")+"'";
			this.executeSQL(sSQL);
			}
			
		}
		catch (Exception e) {
			sessionContext.setRollbackOnly();
			e.printStackTrace();
			throw new EJBException(e.toString());
		}
		
		return true;
	}		
	
	
	public void runtestsql() throws RemoteException {
		int  rs = -1;
		try {
			//如果SQL语句执行成功，返回TRUE
			String sqlString="INSERT INTO T_USER ( ID, LOGIN_CODE, CNAME, IDNO, PASSWORD ) VALUES (3928, 'huwx', 'dd', '232323', 'huwx')";
			System.out.println("sqlString:"+sqlString);
			this.executeSQL(sqlString);
			System.out.println("rs:"+rs);
			sqlString="INSERT INTO T_USER ( ID, LOGIN_CODE1, CNAME, IDNO, PASSWORD ) VALUES (3929, 'huwx', 'ccc', '343434', 'huwx')";
			System.out.println("sqlString:"+sqlString);
			this.executeSQL(sqlString);
			System.out.println("rs:"+rs);
		}
		catch (Exception e) {
			sessionContext.setRollbackOnly();
			if (e.getClass().isInstance(new SQLException())) {
				e.printStackTrace();
				throw new EJBException(e.toString());
			}
			else {
				e.printStackTrace();
			}
		}
	}		
	
}