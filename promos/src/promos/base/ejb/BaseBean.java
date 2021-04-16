package promos.base.ejb;
import java.util.*;
import java.lang.reflect.Method;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.servlet.http.*;

import java.rmi.RemoteException;

import promos.base.objects.BaseConfig;
import promos.base.objects.BaseObject;
import promos.base.objects.BasePrint;
import promos.base.objects.Cache;
import promos.base.objects.Constants;
import promos.base.objects.PromosDate;
import promos.base.objects.User;
import promos.base.objects.ExpressTool;

import promos.custom.ejb.CustomRe;
import promos.custom.ejb.CustomReRemote;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * <p>
 * 标题: Bean BaseBean
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

public class BaseBean extends DBBaseBean implements Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	SessionContext sessionContext;
	
	private final static int DESIGN_MODEL = BaseConfig.getIntOfSettingByName("run_model"); /*1:设计开发模式不影响开发的表进行缓存,2:进行缓存操作但只对表(T_APPMAIN),3：全面进行缓存操作(T_APPMAI,T_APPCOMP等)*/
	private final static String DB_USER = BaseConfig.getSettingByName("db_user"); /*系统数据库用户*/
	private final static String DB_SYS = BaseConfig.getSettingByName("db_sys"); /*系统数据库类型*/
	
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
	
	public boolean ifExefuntion(String sIfExp,String ExpValues, HttpServletRequest request) throws RemoteException {
		if (sIfExp==null) return true;
		if (sIfExp.equals("")) return true;
		
		if ((ExpValues!=null)&&(ExpValues.equals("")==false)) return (boolean) exeFuntion(sIfExp,ExpValues,request);

		sIfExp=replHtmlWihtPara(sIfExp,request);
		
		return ExpressTool.assExpressbool(sIfExp);

	}
	
	public Object exeFuntion(String methodname,String motheparvalues, HttpServletRequest request) throws RemoteException {
		Object rto =null;
		try
		{
			if (methodname==null) return "ERROR:方法名不能为空!";
			if (methodname.equals(""))  return "ERROR:方法名不能为空!";
			if (methodname.equals(Constants.FUNCTION_EXESQL_PRONAME))
			{
				boolean result=false;
				try
				{
				  result=this.executeSQL(motheparvalues);
				  //System.out.println("result1:"+result);
				} catch (Exception e) {
					e.printStackTrace();
		            throw new EJBException(e.toString());
				}
				
				if (result==false) rto="ERROR:执行SQL["+motheparvalues+"]出错!\n";
				else rto="true";
				return rto;
			}
			
			int index=methodname.lastIndexOf(".");
			if (index<0)
			{
				rto="ERROR:方法名["+methodname+"]定义错误!\n";
				return rto;
			}
			String interfacename=methodname.substring(0,index);
			methodname=methodname.substring(index+1);
			
			
			try
			{
				Class cls = Class.forName(interfacename) ;
				
				Method methlist[] = cls.getDeclaredMethods();
				int leng=methlist.length;
				for (int i = 0; i<leng;i++){
					Method m = methlist[i];
					if (m.getName().equals(methodname))
					{
						//BasePrint.println("name = " + m.getName());
						//BasePrint.println("decl class = " + m.getDeclaringClass());
						Class pvec[] = m.getParameterTypes();
						int len=pvec.length;
						Class[] paraTypes = new Class[len];
						Object[] paraValues= new Object[len];
						if (motheparvalues==null) motheparvalues="";
						
						if (motheparvalues.equals("")==false)
						{
							motheparvalues=motheparvalues+":MOTHEPARAEND";
							if (motheparvalues.indexOf(";")>=0) paraValues=motheparvalues.split(";");
							else paraValues=motheparvalues.split("#");
							String sTMP=paraValues[paraValues.length-1].toString();
							paraValues[paraValues.length-1]=sTMP.substring(0,sTMP.length()-13);
						}
						for (int j = 0; j<len;j++)
						{
							//BasePrint.println("param #" + j + "" + pvec[j]);
							
							if (pvec[j].toString().indexOf("java.lang.String")>=0)
							{
								paraTypes[j]=String.class;
							}
							if (pvec[j].equals("int"))
							{
								paraTypes[j]=int.class;
								paraValues[j]=Integer.valueOf(paraValues[j].toString());
								
							}
							if (pvec[j].toString().indexOf("HttpServletRequest")>=0)
							{
								paraTypes[j]=HttpServletRequest.class;
								paraValues[j]=(HttpServletRequest) request ;
							}
							if (pvec[j].toString().indexOf("BaseObject")>=0)
							{
								paraTypes[j]=BaseObject.class;
								if (paraValues[j].toString().equals(Constants.COM_CURRENT_BASEOBJECT))
								{
									paraValues[j]=(BaseObject) request.getAttribute(Constants.COM_CURRENT_BASEOBJECT);
								}
								if (paraValues[j].toString().equals(Constants.PARAMETER_ATTRIBUTEDATA))
								{
									paraValues[j]=(BaseObject) request.getAttribute(Constants.PARAMETER_ATTRIBUTEDATA);
								}
								
							}
							if (pvec[j].toString().indexOf("ArrayList")>=0)
							{
								paraTypes[j]=BaseObject.class;
								if (paraValues[j].toString().equals(Constants.COM_CURRENT_PARALIST))
								{
									paraValues[j]=(ArrayList) request.getAttribute(Constants.COM_CURRENT_PARALIST);
								}
							}
							
						}
						//BasePrint.println("”return type = " + m.getReturnType());
						
						Object Obj = (Object)cls.newInstance() ;
						Method method = cls.getMethod(methodname, paraTypes) ;
						rto = method.invoke(Obj, paraValues);
						break;
						//BasePrint.println("GetMethod:"+rto.toString());
					}
				}		
			} catch (Exception e) {
				e.printStackTrace();
	            throw new EJBException("ERROR:"+e.toString());
			}
			return rto;
		} catch (Exception e) {
			e.printStackTrace();
            throw new EJBException("ERROR:"+e.toString());
		}
	}	

	public String replHtmlWihtPara(String sHtml,HttpServletRequest request) throws RemoteException {
		if (sHtml.equals("")) return sHtml;
		if (sHtml.indexOf("<%=")<0)  return sHtml;

		HttpSession session = request.getSession();
		sHtml=sHtml.replaceAll("<%=SESSION_ID%>",session.getId());
		
		sHtml=sHtml.replaceAll("<%=DB_USER%>",DB_USER);
		sHtml=sHtml.replaceAll("<%=DB_SYS%>",DB_SYS);
		
		sHtml=reValuesWihtPara(sHtml,request);
		if (sHtml.indexOf("<%=")<0)  return sHtml;
		
		String if_reconstans=getValueFromRequest(Constants.PARAMETER_IFREPLACECONSTANS,"0",request);
		if (if_reconstans.equals("1"))
		{
			sHtml=reValuesWihtSysConstans(getValueFromRequest("AppName","0",request),sHtml,request);
			if (sHtml.indexOf("<%=")<0)  return sHtml;
		}
		
		sHtml=reValuesWihtField(sHtml,(BaseObject)request.getAttribute(Constants.PARAMETER_ATTRIBUTEDATA),true);
		if (sHtml.indexOf("<%=")<0)  return sHtml;
		
		CustomRe Cure = CustomReRemote.getCustomRe();
		sHtml=Cure.reCustomPara(sHtml,request);
		
		return sHtml;
	}
	public String reValuesWihtPara(String sHtml,HttpServletRequest request) throws RemoteException {
		if (sHtml.equals("")) return sHtml;
		if (sHtml.indexOf("<%=")<0)  return sHtml;
		Enumeration app_enum = request.getParameterNames();
		String para_name="";
		String val_tmp="";
		while (app_enum.hasMoreElements()) {
			para_name = (String) app_enum.nextElement();
			val_tmp=getValueFromRequest(para_name,"",request);
			sHtml=sHtml.replaceAll("<%="+para_name+"%>",val_tmp);
		}		
		return sHtml;
	}	
	public String reValuesWihtSysConstans(String AppName,String sHtml,HttpServletRequest request) throws RemoteException {
		try
		{
			if (sHtml.equals("")) return sHtml;
			if (sHtml.indexOf("<%=")<0)  return sHtml;
			
			System.out.println("AppName:"+AppName);
			
			if ((AppName!=null)&&(AppName.equals("")==false)){
				ArrayList list = getSysConstansList(request);
				int length=list.size();
				BaseObject baseobj = null;
				if (list != null &&  length> 0) {
					for (int i=0; i < length; i++) {
						baseobj = (BaseObject) list.get(i);
						sHtml=sHtml.replaceAll("<%="+BaseObject.toString(baseobj, "FVC_CODE")+"%>",BaseObject.toString(baseobj, "FVC_VALUE"));
					}
				}
			}
			
			
			
			return sHtml;
		} catch (Exception e) {
			e.printStackTrace();
			return sHtml;
		}
	}	
	public String reValuesWihtField(String sHtml,BaseObject baseobj ,boolean ifNullRs) throws RemoteException {
		if (sHtml.equals("")) return sHtml;
		if (sHtml.indexOf("<%=")<0)  return sHtml;
		if (baseobj != null) {
			int length=baseobj.getFieldCount();
			ArrayList list = baseobj.getFieldName();
			String sTMP="";
			for (int i=0; i < length; i++) {
				sTMP=BaseObject.toString(baseobj, list.get(i).toString());
				//BasePrint.println("sTMP:"+sTMP);
				//BasePrint.println("field:"+list.get(i).toString());
				if (sTMP.equals("")) 
				{
					if (ifNullRs) sHtml=sHtml.replaceAll("<%="+list.get(i).toString()+"%>","");
				}
				else
				{
					sTMP = sTMP.replaceAll("\\$", "RDS_CHAR_DOLLAR");
					sHtml=sHtml.replaceAll("<%="+list.get(i).toString()+"%>",sTMP);
					sHtml = sHtml.replaceAll("RDS_CHAR_DOLLAR", "\\$");
				}
			}
		}
		return sHtml;
	}	
	/**
	 * 根据HTML格式类型找到对应的代码
	 *
	 * @String TabStype  要显示的格式类型
	 */
	public String getHtmlCode(BaseObject bComObject,String sTabStype,String sOtherStyle,String sCont,HttpServletRequest request) throws RemoteException {
		String s_TabHtml="";
		String isDesign=getValueFromRequest(Constants.PARAMETER_IS_DESIGN,"0",request);
		Cache cache = Cache.getInstance();
		BaseObject htmlobj=(BaseObject)cache.getCache(Constants.CACHE_HTMLCODE_TAG,sTabStype);
		
		if (htmlobj==null){
			ArrayList list = this.selectSQL(DB_USER+"."+Constants.PARAMETER_COMPONENT_TABLENAME,"select * from "+DB_USER+"."+Constants.PARAMETER_COMPONENT_TABLENAME+" order by FNB_ID");
			int length=list.size();
			BaseObject baseobj = null;
			if (list != null &&  length> 0) {
				for (int i=0; i < length; i++) {
					baseobj = (BaseObject) list.get(i);
					cache.setCache(Constants.CACHE_HTMLCODE_TAG,BaseObject.toString(baseobj, "FVC_TYPE"),baseobj);
				}
			}
		}
		
		htmlobj=(BaseObject)cache.getCache(Constants.CACHE_HTMLCODE_TAG,sTabStype);
		if (htmlobj!=null){
			String htmlcode="";
			if (isDesign.equals("1")) htmlcode=BaseObject.toString(htmlobj, "FVC_DESIGNCODE");
			else  htmlcode=BaseObject.toString(htmlobj, "FVC_HTMLCODE");
			
			
			String sOthertype="";

			if ((bComObject!=null)&&(BaseObject.toString(bComObject, "FVC_ISBASE").equals(Constants.COM_COMTYPE_STYLE)==true))
			{
				/*style*/
				
				if (BaseObject.toString(bComObject, "FVC_BORDER").equals("")==false)
				{
					sOthertype=sOthertype+"; border:"+BaseObject.toString(bComObject, "FVC_BORDER");
				}
				
				if (BaseObject.toString(bComObject, "FVC_ALIGN").equals("")==false)
				{
					sOthertype=sOthertype+"; ALIGN:"+BaseObject.toString(bComObject, "FVC_ALIGN");
				}
				
				if (BaseObject.toString(bComObject, "FVC_VALIGN").equals("")==false)
				{
					sOthertype=sOthertype+"; VALIGN:"+BaseObject.toString(bComObject, "FVC_VALIGN");
				}
				
				if (BaseObject.toString(bComObject, "FVC_HEIGHT").equals("")==false)
				{
					sOthertype=sOthertype+"; HEIGHT:"+BaseObject.toString(bComObject, "FVC_HEIGHT");
				}
				if (BaseObject.toString(bComObject, "FVC_WIDTH").equals("")==false)
				{
					sOthertype=sOthertype+"; WIDTH:"+BaseObject.toString(bComObject, "FVC_WIDTH");
				}
				if (BaseObject.toString(bComObject, "FVC_TOP").equals("")==false)
				{
					sOthertype=sOthertype+"; TOP:"+BaseObject.toString(bComObject, "FVC_TOP");
				}
				if (BaseObject.toString(bComObject, "FVC_LEFT").equals("")==false)
				{
					sOthertype=sOthertype+"; LEFT:"+BaseObject.toString(bComObject, "FVC_LEFT");
				}
				if (BaseObject.toString(bComObject, "FVC_BACKCOLOR").equals("")==false)
				{
					sOthertype=sOthertype+" ;background:"+BaseObject.toString(bComObject, "FVC_BACKCOLOR");
				}
				if (BaseObject.toString(bComObject, "FVC_DISPLAY").equals("1")==true)
				{
					sOthertype=sOthertype+"; display:none";
				}
				if (BaseObject.toString(bComObject, "FVC_CURSOR").equals("")==false)
				{
					sOthertype=sOthertype+"; CURSOR:"+BaseObject.toString(bComObject, "FVC_CURSOR");
				}
				if (BaseObject.toString(bComObject, "FVC_POSITION").equals("")==false)
				{
					sOthertype=sOthertype+"; position:"+BaseObject.toString(bComObject, "FVC_POSITION");
				}
				if (BaseObject.toString(bComObject, "FVC_DIVINDEX").equals("")==false)
				{
					sOthertype=sOthertype+"; Z-INDEX:"+BaseObject.toString(bComObject, "FVC_DIVINDEX");
				}

				if (BaseObject.toString(htmlobj, "FVC_STYLE").equals("")==false)
				{
					sOthertype=sOthertype+"; "+BaseObject.toString(htmlobj, "FVC_STYLE");
				}
				
				
				//System.out.println("sOthertype:"+sOthertype);
				
			}
			
			if ((bComObject!=null)&&(BaseObject.toString(bComObject, "FVC_STYLE").equals("")==false))
			{
				sOthertype=sOthertype+"; "+BaseObject.toString(bComObject, "FVC_STYLE");
			}

			if (sOthertype.equals("")==false)
			{
				sOthertype=" style='"+sOthertype+"'";
			}
			

			//System.out.println("sTabStype:"+sTabStype);
			//System.out.println("FVC_TYPE:"+BaseObject.toString(htmlobj, "FVC_TYPE"));
			
			s_TabHtml = BaseObject.toString(htmlobj, "FVC_TAGSTAR")+sOtherStyle+htmlcode+sOthertype+BaseObject.toString(htmlobj, "FVC_TAGMID")+sCont+BaseObject.toString(htmlobj, "FVC_TAGEND");
			
			//System.out.println("s_TabHtml:"+s_TabHtml);
			
		}
		else
		{
			BasePrint.println(sTabStype+":没有被定义!\n");
		}
		
		return s_TabHtml;
	}	
	/**
	 * 根据HTML格式类型找到对应的代码
	 *
	 * @String sID    要显示的HTML语言ID号
	 * @String sName  要显示的HTML语言名称
	 * @String TabStype  要显示的格式类型
	 */
	public String getHtmlCode(BaseObject bComObject,String sTabStype,String sID,String sName,String sOther,String sCont,HttpServletRequest request) throws RemoteException {
		String sOtherStyle="";
		if (sID.equals("")==false)sOtherStyle=" ID="+sID;
		if (sName.equals("")==false) sOtherStyle=sOtherStyle+" NAME='"+sName+"'";
		sOtherStyle=sOtherStyle+sOther;
		return getHtmlCode(bComObject,sTabStype,sOtherStyle,sCont,request);
	}
	/**
	 * 根据HTML格式类型找到对应的代码
	 *
	 * @String sID    要显示的HTML语言ID号
	 * @String sName  要显示的HTML语言名称
	 * @String TabStype  要显示的格式类型
	 */
	public String getHtmlCode(String sTabStype,String sID,String sName,String sOther,String sCont,HttpServletRequest request) throws RemoteException {
		String sOtherStyle="";
		if (sID.equals("")==false)sOtherStyle=" ID="+sID;
		if (sName.equals("")==false) sOtherStyle=sOtherStyle+" NAME='"+sName+"'";
		sOtherStyle=sOtherStyle+sOther;
		return getHtmlCode(null,sTabStype,sOtherStyle,sCont,request);
	}
	/**
	 * 为显示表单元素添加DIV
	 *
	 * @String sCont        要包括的DIV的对象
	 * @String sID             ID
	 * @String sPosition       对齐类型
	 * @String sVisibility     可视
	 * @String sHeight         高
	 * @String sWidth          宽
	 * @String sLeft           左边
	 * @String sTop            右边
	 * @String sOther          其它功能
	 */
	public String getDivHtmlCode(String sID,String sPosition,String sVisibility,String sHeight,String sWidth,String sLeft,String sTop,String sOtherType,String sOther,String sCont,HttpServletRequest request) throws RemoteException {
		String sHtml="";
		if (sID.equals("")==false) {sHtml=sHtml+" id="+sID;}
		if ((sPosition+sVisibility+sHeight+sWidth+sLeft+sTop+sOtherType).equals("")==false){
			sHtml=sHtml+" style='";
			if (sPosition.equals("")==false) {sHtml=sHtml+" ;POSITION:"+sPosition;}
			if (sVisibility.equals("")==false) {sHtml=sHtml+" ;VISIBILITY:"+sVisibility;}
			if (sHeight.equals("")==false) {sHtml=sHtml+" ;HEIGHT:"+sHeight;}
			if (sWidth.equals("")==false) {sHtml=sHtml+" ;WIDTH:"+sWidth;}
			if (sTop.equals("")==false) {sHtml=sHtml+" ;TOP:"+sTop;}
			if (sLeft.equals("")==false) {sHtml=sHtml+" ;LEFT:"+sLeft;}
			if (sOtherType.equals("")==false) {sHtml=sHtml+" ;"+sOtherType;}
			sHtml=sHtml+";' ";
		}
		return getHtmlCode(null,"DIV",sHtml+" "+sOther,sCont,request);
	}
	/**
	 * 根据HTML格式类型找到对应的代码
	 *
	 * @String TabStype  要显示的格式类型
	 */
	public String getHtmlCode(String sTabStype,HttpServletRequest request) throws RemoteException {
		return getHtmlCode(null,sTabStype,"","",request);
	}
	/**
	 * 添加空行或空格等
	 *
	 * @int nCount            行数
	 */
	public String getWebAddBr(String sTAG,int nlength){
		String sHtml="";
		for (int i=0; i < nlength; i++) {
			sHtml = sHtml + sTAG;
		}
		return sHtml;
	}
	/**
	 * 添加Form
	 *
	 * @String sWebCode       要添加Form的对象
	 * @String sName          Name
	 * @String sAction        Action
	 * @String sTarget        Target
	 */
	public String getWebAddForm(String sCont,String sName,String sAction,String sTarget,HttpServletRequest request) throws RemoteException {
		String sHtml=" method=\"post\" ";
		if (sName.equals("")==false) {sHtml=sHtml+" name="+sName;}
		if (sAction.equals("")==false) {sHtml=sHtml+" action=\""+sAction+"\" ";}
		if (sTarget.equals("")==false) {sHtml=sHtml+" target=\""+sTarget+"\" ";}
		
		return getHtmlCode(null,"FORM",sHtml,sCont,request);
	}
	/**
	 * 根据表单元素类型显示数据
	 *
	 * @BaseObject bComObject    表单元素BaseObject
	 * @BaseObject bDataObject   数据BaseObject
	 */
	public String getBaseComHtml(BaseObject bComObject,BaseObject bDataObject,HttpServletRequest request)throws RemoteException {
		String sHtml="";
		String sOthertype="";
		
		String scomtype=BaseObject.toString(bComObject, "FVC_TYPE");
		String sHtmlName=getComName(bComObject);
		
		String sDefaultValue=replHtmlWihtPara(BaseObject.toString(bComObject, "FVC_DEFAULTVALUE"),request);
		String sIsView=getValueFromRequest(Constants.PARAMETER_ISVIEW_NAME,"0",request);
		String sTmpVaule="";
		
		if (bDataObject!=null)
		{
			sTmpVaule=BaseObject.toString(bDataObject, sHtmlName);
			if ((BaseObject.toString(bComObject, "FVC_DATATYPE").equals("DATE")==true)&&(sTmpVaule.equals("")==false)) sTmpVaule=PromosDate.checkErrorDateString(sTmpVaule,PromosDate.YYYY_MM_DD_HH_MI);
			sTmpVaule=sTmpVaule.replaceAll(" 00:00:00","").replaceAll(" 00:00","");
		}
		else
		{
			if (sDefaultValue.equals("")==false)
			{
				sTmpVaule=sDefaultValue;
			}
		}
		
		try
		{
			if ((scomtype.equals("RADIO")==true)||(scomtype.equals("CHECKBOX")==true))
			{
				if ((sDefaultValue.equals("1")==true)||sTmpVaule.equals("1")==true){
					sOthertype=sOthertype+" checked ";
				}
			}

			if (BaseObject.toString(bComObject, "FVC_COMCLASS").equals("")==false)
			{
				sOthertype=sOthertype+" styleClass='"+BaseObject.toString(bComObject, "FVC_COMCLASS")+"'";
			}

			if ((BaseObject.toString(bComObject, "FVC_MAXLEN").equals("")==false)&&(BaseObject.toString(bComObject, "FVC_MAXLEN").equals("0")==false))
			{
				sOthertype=sOthertype+" MAXLENGTH="+BaseObject.toString(bComObject, "FVC_MAXLEN");
			}


			/*style*/
			sOthertype=sOthertype+" style='";
			
			if (BaseObject.toString(bComObject, "FVC_BORDER").equals("")==false)
			{
				sOthertype=sOthertype+"; border:"+BaseObject.toString(bComObject, "FVC_BORDER");
			}
			
			if (BaseObject.toString(bComObject, "FVC_ALIGN").equals("")==false)
			{
				sOthertype=sOthertype+"; ALIGN:"+BaseObject.toString(bComObject, "FVC_ALIGN");
			}
			
			if (BaseObject.toString(bComObject, "FVC_VALIGN").equals("")==false)
			{
				sOthertype=sOthertype+"; VALIGN:"+BaseObject.toString(bComObject, "FVC_VALIGN");
			}
			
			if (BaseObject.toString(bComObject, "FVC_HEIGHT").equals("")==false)
			{
				sOthertype=sOthertype+"; HEIGHT:"+BaseObject.toString(bComObject, "FVC_HEIGHT");
			}
			if (BaseObject.toString(bComObject, "FVC_WIDTH").equals("")==false)
			{
				sOthertype=sOthertype+"; WIDTH:"+BaseObject.toString(bComObject, "FVC_WIDTH");
			}
			if ((BaseObject.toString(bComObject, "FVC_BACKCOLOR").equals("")==false)||(sIsView.equals("1"))||(getValueFromRequest(Constants.COM_PROPERTY_READONLY,"",request).indexOf(BaseObject.toString(bComObject, "FVC_HTMLID"))>=0)||(getValueFromRequest(Constants.COM_PROPERTY_NOTENABLED,"",request).indexOf(BaseObject.toString(bComObject, "FVC_HTMLID"))>=0))
			{
				if ((((getValueFromRequest(Constants.COM_PROPERTY_READONLY,"",request).indexOf(sHtmlName)>=0)&&(sHtmlName.equals("")==false))||(sIsView.equals("1"))||((getValueFromRequest(Constants.COM_PROPERTY_NOTENABLED,"",request).indexOf(sHtmlName)>=0))&&(sHtmlName.equals("")==false))&&(scomtype.equals("IMG")==false)&&(getNotPurViewCom(request).indexOf(sHtmlName)<0))
				{
					sOthertype = sOthertype + "; background:WhiteSmoke";
				}
				else
				{
					if (BaseObject.toString(bComObject, "FVC_BACKCOLOR").equals("")==false)  sOthertype=sOthertype+" ;background:"+BaseObject.toString(bComObject, "FVC_BACKCOLOR");
				}
			}
			
			
			
			if ((BaseObject.toString(bComObject, "FVC_DISPLAY").equals("1")==true)||(getValueFromRequest(Constants.COM_PROPERTY_NOTDISPLAY,"",request).indexOf(sHtmlName)>=0)||((sIsView.equals("1"))&&(scomtype.equals("IMG")==true)&&(getNotPurViewCom(request).indexOf(sHtmlName)<0)))
			{
				sOthertype=sOthertype+"; display:none";
			}
			if (BaseObject.toString(bComObject, "FVC_CURSOR").equals("")==false)
			{
				sOthertype=sOthertype+"; CURSOR:"+BaseObject.toString(bComObject, "FVC_CURSOR");
			}
			if (BaseObject.toString(bComObject, "FVC_POSITION").equals("")==false)
			{
				sOthertype=sOthertype+"; position:"+BaseObject.toString(bComObject, "FVC_POSITION");
			}
			
			sOthertype=sOthertype+"'";
			
			
			if ((BaseObject.toString(bComObject, "FVC_ENABLED").equals("1")==true)||((getValueFromRequest(Constants.COM_PROPERTY_NOTENABLED,"",request).indexOf(sHtmlName)>=0)&&(sHtmlName.equals("")==false))||((sIsView.equals("1"))&&(getNotPurViewCom(request).indexOf(sHtmlName)<0)))
			{
				sOthertype=sOthertype+" disabled ";
			}
			
			if (((BaseObject.toString(bComObject, "FVC_READONLY").equals("1")==true)||((getValueFromRequest(Constants.COM_PROPERTY_READONLY,"",request).indexOf(sHtmlName)>=0))&&(sHtmlName.equals("")==false))&&(getNotPurViewCom(request).indexOf(sHtmlName)<0))
			{
				sOthertype=sOthertype+" readonly ";
			}
			
			
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
				String sOption="<option value=\"\"></option>";
				if (BaseObject.toString(bComObject, "FVC_LISTDATASOURCE").equals("")==false)
				{
					ArrayList list=null;
					
					String sDataSource=BaseObject.toString(bComObject, "FVC_LISTDATASOURCE");
					sDataSource=replHtmlWihtPara(sDataSource,request);

					if ((sDataSource.indexOf("SELECT")>=0)||(sDataSource.indexOf("select")>=0))
					{
						list = this.selectSQL(sDataSource,sDataSource);
					}
					else
					{
						list = this.selectSQL(sDataSource,"select * from "+sDataSource+" where FVC_GROUPCODE='"+getComName(bComObject)+"'");
					}
					
					int length=list.size();
					BaseObject baseobj =null;
					if (list != null &&  length> 0) {
						for (int i=0; i < length; i++) {
							baseobj = (BaseObject) list.get(i);
							if (sTmpVaule.equals(BaseObject.toString(baseobj, "FVC_CODE"))==true)
							{
								sOption=sOption+" <option selected value=\""+BaseObject.toString(baseobj, "FVC_CODE")+"\">"+BaseObject.toString(baseobj, "FVC_VALUE")+"</option>";
							}
							else
							{
								sOption=sOption+" <option value=\""+BaseObject.toString(baseobj, "FVC_CODE")+"\">"+BaseObject.toString(baseobj, "FVC_VALUE")+"</option>";
							}
						}
					}
				}
				sHtml=getHtmlCode(bComObject,BaseObject.toString(bComObject, "FVC_TYPE"),BaseObject.toString(bComObject, "FVC_HTMLID"),sHtmlName,sOthertype+" "+BaseObject.toString(bComObject, "FVC_OTHER"),sOption,request);
				return sHtml;
			}
			if (scomtype.equals("LABLE"))
			{
				sHtml=getHtmlCode(bComObject,scomtype,BaseObject.toString(bComObject, "FVC_HTMLID"),sHtmlName," "+BaseObject.toString(bComObject, "FVC_OTHER"),BaseObject.toString(bComObject, "FVC_TITLENAME"),request);    	   
				return sHtml;
			}			
			sHtml=getHtmlCode(bComObject,scomtype,BaseObject.toString(bComObject, "FVC_HTMLID"),sHtmlName,sOthertype+" "+BaseObject.toString(bComObject, "FVC_OTHER"),"",request);    	   
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
	public String getBaseComHtml(BaseObject bComBaseObject,ArrayList lComParalist,BaseObject bDataObject,String sGetdataWhere,HttpServletRequest request)throws RemoteException {
		String sHtml="";
		
		String sComType=BaseObject.toString(bComBaseObject, "FVC_TYPE");
		String sIsBase=BaseObject.toString(bComBaseObject, "FVC_ISBASE");
		String sHtmlName=getComName(bComBaseObject);
		String sDefaultValue=BaseObject.toString(bComBaseObject, "FVC_DEFAULTVALUE");
		String sTmpVaule="";
		
		if (bDataObject!=null)
		{
			sTmpVaule=BaseObject.toString(bDataObject, sHtmlName);
			if ((BaseObject.toString(bComBaseObject, "FVC_DATATYPE").equals("DATE")==true)&&(sTmpVaule.equals("")==false)) sTmpVaule=PromosDate.checkErrorDateString(sTmpVaule,PromosDate.YYYY_MM_DD_HH_MI);
			sTmpVaule=sTmpVaule.replaceAll(" 00:00:00","").replaceAll(" 00:00","");
		}
		else
		{
			if (sDefaultValue.equals("")==false)
			{
				sTmpVaule=sDefaultValue;
			}
		}
		
		if (sIsBase.equals("1")) sHtml=getBaseComHtml(bComBaseObject,bDataObject,request);   
		else sHtml=getHtmlCode(bComBaseObject,sComType,BaseObject.toString(bComBaseObject, "FVC_OTHER"),"",request);
		
		sHtml=sHtml.replaceAll("<%="+Constants.COM_PARA_VALUE_NAME+"%>",sTmpVaule);
		
		if (sHtml.indexOf("<%=")<0)  return sHtml;
		if (lComParalist!=null)
		{
			int length=lComParalist.size();
			BaseObject baseobj=null;
			String sPareType="";
			String sParaName="";
			String sParaValue="";
			String sParaIsFun="";
			String sTmp="";
			for (int i=0; i < length; i++) {
				baseobj = (BaseObject) lComParalist.get(i);
				sPareType=BaseObject.toString(baseobj, "FVC_TYPE");
				
				if (sPareType.equals(sComType))
				{
					sParaName=BaseObject.toString(baseobj, "FVC_NAME");
					sParaValue=BaseObject.toString(baseobj, "FVC_VALUE");
					sParaIsFun=BaseObject.toString(baseobj, "FVC_ISFUN");
					
					if (sParaName.equals(Constants.COM_PARA_READONLY_NAME)) sTmp=getPurViewDisabled(sHtmlName,"READONLY",Constants.COM_PROPERTY_READONLY,request);
					if (sParaName.equals(Constants.COM_PARA_DISABLED_NAME)) sTmp=getPurViewDisabled(sHtmlName,"DISABLED",Constants.COM_PROPERTY_NOTENABLED,request);
					if (sParaName.equals(Constants.COM_PARA_BACKGROUND_NAME)) sTmp=getPurViewDisabled(sHtmlName,"WHITESMOKE",Constants.COM_PROPERTY_READONLY,request);
					if (sParaName.equals(Constants.COM_PARA_DISPLAY_NAME)) sTmp=getPurViewDisabled(sHtmlName,"NONE",Constants.COM_PROPERTY_NOTDISPLAY,request);
					
					if (sTmp.equals("")==false) {sParaName=sTmp;sTmp="";}
					
					if (sParaIsFun.equals("1"))
					{
						request.setAttribute(Constants.COM_CURRENT_BASEOBJECT,bComBaseObject);
						String sTMP=sParaValue;
						sTMP=reValuesWihtField(sTMP,bComBaseObject,true);
						sTMP=replHtmlWihtPara(sTMP,request);
						Object obj=this.exeFuntion(BaseObject.toString(baseobj,"FVC_FUNNAME"),sTMP,request);
						HashMap map = new HashMap();   
						map=(HashMap)obj;
						if (map!=null)
						{
							Iterator iter = map.keySet().iterator();
							while (iter.hasNext()) {
								sParaName = (String) iter.next();
								sParaValue=map.get(sParaName).toString();
								
								if (sParaValue.equals("")) 
								{
									sHtml=sHtml.replaceAll(sParaName+"=\"<%="+sParaName+"%>\";","");
									sHtml=sHtml.replaceAll(sParaName+"=<%="+sParaName+"%>;","");
									
									sHtml=sHtml.replaceAll(sParaName+"=\"<%="+sParaName+"%>\"","");
									sHtml=sHtml.replaceAll(sParaName+"=<%="+sParaName+"%>","");
									
									sHtml=sHtml.replaceAll(sParaName+":<%="+sParaName+"%>;","");
									sHtml=sHtml.replaceAll(sParaName+":\"<%="+sParaName+"%>\";","");
									sHtml=sHtml.replaceAll(sParaName+":<%="+sParaName+"%>","");
									sHtml=sHtml.replaceAll(sParaName+":\"<%="+sParaName+"%>\"","");
									sHtml=sHtml.replaceAll("\"<%="+sParaName+"%>\"","");
									sHtml=sHtml.replaceAll("<%="+sParaName+"%>","");
								}
								else
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
					else
					{
						
						if (sParaValue.equals("")) 
						{
							sHtml=sHtml.replaceAll(sParaName+"=\"<%="+sParaName+"%>\";","");
							sHtml=sHtml.replaceAll(sParaName+"=<%="+sParaName+"%>;","");
							
							sHtml=sHtml.replaceAll(sParaName+"=\"<%="+sParaName+"%>\"","");
							sHtml=sHtml.replaceAll(sParaName+"=<%="+sParaName+"%>","");
							
							sHtml=sHtml.replaceAll(sParaName+":<%="+sParaName+"%>;","");
							sHtml=sHtml.replaceAll(sParaName+":\"<%="+sParaName+"%>\";","");
							sHtml=sHtml.replaceAll(sParaName+":<%="+sParaName+"%>","");
							sHtml=sHtml.replaceAll(sParaName+":\"<%="+sParaName+"%>\"","");
							sHtml=sHtml.replaceAll("\"<%="+sParaName+"%>\"","");
							sHtml=sHtml.replaceAll("<%="+sParaName+"%>","");
						}
						else
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
		}
		if (Constants.COM_ISFUNCTION_NAMELIST.indexOf(sComType)>=0)
		{
			sHtml=sHtml.replaceAll(Constants.COM_EXTENCOM_CONNAME,getExtendComHtml(bComBaseObject,lComParalist,bDataObject,sGetdataWhere,request));
		}
		
		
		sHtml=reValuesWihtField(sHtml,bComBaseObject,true);
		return sHtml;
		
	}	
	/**
	 * 得到非输入类表单元素HTML
	 *
	 * @String session           session
	 * @BaseObject bComObject    表单元素BaseObject
	 */
	public String getExtendComHtml(BaseObject bComObject,ArrayList lComParalist,BaseObject bDataObject,String sGetdataWhere,HttpServletRequest request)throws RemoteException {
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
				int length=list.size();
				if (list != null &&  length> 0) {
					for (int i=0; i < length; i++) {
						baseobj = (BaseObject) list.get(i);
						String sDisplay=BaseObject.toString(baseobj, "FVC_DISPLAY");
						String sComName=getComName(baseobj);
						if (sComName.equals("")) sComName="0";
						if ((sDisplay.equals("1")==true)||(getValueFromRequest(Constants.COM_PROPERTY_NOTDISPLAY,"",request).indexOf(sComName)>=0)) sDisplay="hidden";
						if (sDisplay.equals("hidden")==false)
						{
							String s_DivScroll="";
							String sDivIndex="";
							String sPosition="absolute";
							
							
							if (BaseObject.toString(baseobj, "FVC_IFDIV").equals("1"))
							{

								s_DivScroll=BaseObject.toString(baseobj, "FVC_IFDIVSCROLL");
								if (s_DivScroll.equals("1")==true) s_DivScroll=" OVERFLOW: scroll;";
								else s_DivScroll="";
								
								sPosition=BaseObject.toString(baseobj, "FVC_POSITION");
								if (sPosition.equals("")==true) sPosition="absolute";
																
								s_comhtml=s_comhtml+this.getDivHtmlCode("DIV"+sComName,sPosition,sDisplay,BaseObject.toString(baseobj, "FVC_HEIGHT"),BaseObject.toString(baseobj, "FVC_WIDTH"),BaseObject.toString(baseobj, "FVC_LEFT"),BaseObject.toString(baseobj, "FVC_TOP"),s_DivScroll+sDivIndex," valign='center' ",getBaseComHtml(baseobj,lComParalist,bDataObject,sGetdataWhere,request),request);
								
							}
							else
							{
								s_comhtml=s_comhtml+BaseObject.toString(baseobj, "FVC_TITLENAME")+getBaseComHtml(baseobj,lComParalist,bDataObject,sGetdataWhere,request);
							}

							
							
							//s_comhtml=s_comhtml+getBaseComHtml(baseobj,lComParalist, bDataObject,sGetdataWhere,request);
						}
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
	
	/**
	 * 得到不用权限控制的表单元素
	 *
	 * @ArrayList comlist    表单元素ArrayList
	 * @String sSource    内容
	 */
	public String getNotPurViewCom(HttpServletRequest request)throws RemoteException {
		if (request.getAttribute("NotPurViewCom")!=null){
			return request.getAttribute("NotPurViewCom").toString();
		}
		
		ArrayList acomlist = null;
		
		acomlist= getComListByCondition(request,Constants.COMP_WHERE_IFNOTPURVIEW,"");
		
		String sTmpVaule="";
		if (acomlist!=null)
		{
			int comlength=acomlist.size();
			BaseObject combaseobj=null;
			for (int i = 0; i < comlength; i++) {
				combaseobj = (BaseObject) acomlist.get(i);
				sTmpVaule=sTmpVaule+getComName(combaseobj)+",";
			}
		}
		if (sTmpVaule.length()>0) sTmpVaule=sTmpVaule.substring(0,sTmpVaule.length()-1);
		request.setAttribute("NotPurViewCom",sTmpVaule);
		return sTmpVaule;
	}
	/**
	 * 根据表单元素类型显示数据
	 *
	 * @BaseObject bComObject    表单元素BaseObject
	 * @BaseObject bDataObject   数据BaseObject
	 */
	public String getPurViewDisabled(String sHtmlName,String sValue,String sPurAct,HttpServletRequest request)throws RemoteException {
		if (((getValueFromRequest(Constants.PARAMETER_ISVIEW_NAME,"0",request).equals("1"))||(getValueFromRequest(sPurAct,"",request).indexOf(sHtmlName)>=0))&&(getNotPurViewCom(request).indexOf(sHtmlName)<0))
		{
			return sValue;
		}
		else
		{
			return "";
		}
	}	
	/**
	 * 取业务的主要参数BaseObject
	 *
	 *
	 * @String sAppName
	 */
	public BaseObject getAppMainBaseObject(HttpServletRequest request)throws RemoteException {
		BaseObject baseobj = null;
		String sAppName=getAppName(request);
		
		if (DESIGN_MODEL==1)
		{
			if (request.getAttribute("AppMainBaseObject")==null){
				ArrayList list = this.selectSQL(DB_USER+"."+"T_APPMAIN","FVC_APPNAME='"+sAppName+"'","");
				if (list.size()>0)
				{
					baseobj = (BaseObject) list.get(0);
					request.setAttribute("AppMainBaseObject", baseobj);
				}
			}
			else
			{
				baseobj=(BaseObject)request.getAttribute("AppMainBaseObject");
			}
			
			return baseobj;
		}
		else
		{
			
			Cache cache = Cache.getInstance();
			BaseObject mainobj=(BaseObject)cache.getCache(Constants.APPMAIN_BASEOBJECT_TAG,sAppName);
			
			if (mainobj==null){
				ArrayList list = this.selectSQL(DB_USER+"."+"T_APPMAIN","FVC_APPNAME='"+sAppName+"'","");
				if (list.size()>0)
				{
					BaseObject tmpobj = (BaseObject) list.get(0);
					cache.setCache(Constants.APPMAIN_BASEOBJECT_TAG,sAppName,tmpobj);
				}			
			}
			
			return (BaseObject)cache.getCache(Constants.APPMAIN_BASEOBJECT_TAG,sAppName);
			
		}
	}
	
	/**
	 * 缓存T_FUNCTION表
	 *
	 *
	 * @HttpServletRequest request
	 */
	public ArrayList getFunctionList(HttpServletRequest request)throws RemoteException {
		ArrayList returnlist = new ArrayList();
		
		Cache cache = Cache.getInstance();
		returnlist=(ArrayList)cache.getCache(Constants.APP_T_FUNCTION_LIST,"");
		
		if (returnlist==null){
			returnlist = this.selectSQL(DB_USER+"."+"T_FUNCTION","","FNB_ORDER");
			cache.setCache(Constants.APP_T_FUNCTION_LIST,"",returnlist);
		}
		
		return returnlist;
	}
	
	
	/**
	 * 缓存T_SYSCONSTANS表
	 *
	 *
	 * @HttpServletRequest request
	 */
	public ArrayList getSysConstansList(HttpServletRequest request)throws RemoteException {
		String sAppName=getAppName(request);
		ArrayList returnlist = new ArrayList();
		
		Cache cache = Cache.getInstance();
		ArrayList sysconlist=(ArrayList)cache.getCache(Constants.APP_T_SYSCONSTANS_LIST,"");
		
		if (sysconlist==null){
			ArrayList list = this.selectSQL(DB_USER+"."+"T_SYSCONSTANS","","FVC_APPNAME");
			cache.setCache(Constants.APP_T_SYSCONSTANS_LIST,"",list);
			sysconlist=list;
		}
		
		if (sysconlist!=null){
			if (sysconlist.size()>0)
			{
				
				for (int i=0; i < sysconlist.size(); i++) {
					BaseObject tmpobj= (BaseObject) sysconlist.get(i);
					if (BaseObject.toString(tmpobj, "FVC_APPNAME").equals(sAppName))
					{
						returnlist.add(tmpobj);
					}
				}
			}			
		}
		
		return returnlist;
	}
	
	
	/**
	 * 缓存T_APPCOMP表
	 *
	 *
	 * @String sAppName
	 */
	public ArrayList getAppComList(HttpServletRequest request)throws RemoteException {
		String sAppName=getAppName(request);
		String strTABLE_APPCOMP=getAppMainParameter("FVC_TABLE_APPCOMP",request);
		if ((strTABLE_APPCOMP.indexOf(".")<0)&&(strTABLE_APPCOMP.equals("")==false)&&(strTABLE_APPCOMP!=null)&&(strTABLE_APPCOMP.indexOf("select")<0)&&(strTABLE_APPCOMP.indexOf("SELECT")<0))
		{
			strTABLE_APPCOMP=DB_USER+"."+strTABLE_APPCOMP;
		}
		
		
		if (DESIGN_MODEL<=2)
		{
			if (request.getAttribute(Constants.APP_COMPENT_LIST_TAG+sAppName)==null){
				ArrayList list = this.selectSQL(AnalyseDataSource(strTABLE_APPCOMP),"FVC_APPNAME='"+sAppName+"'","FVC_SHOWORDER,FVC_TOP,FVC_LEFT");
				request.setAttribute(Constants.APP_COMPENT_LIST_TAG+sAppName, list);
				return list;
			}
			else
			{
				ArrayList list=(ArrayList)request.getAttribute(Constants.APP_COMPENT_LIST_TAG+sAppName);
				return list;
			}			
		}
		else
		{
			Cache cache = Cache.getInstance();
			ArrayList appcomlist=(ArrayList)cache.getCache(Constants.APP_COMPENT_LIST_TAG,sAppName);
			
			if (appcomlist==null){
				appcomlist = this.selectSQL(AnalyseDataSource(strTABLE_APPCOMP),"FVC_APPNAME='"+sAppName+"'","FVC_SHOWORDER,FVC_TOP,FVC_LEFT");
				cache.setCache(Constants.APP_COMPENT_LIST_TAG,sAppName,appcomlist);
			}
			
			return appcomlist;
			
		}
	}	
	
	/**
	 * 缓存T_APPCOMP表
	 *
	 *
	 * @String sAppName
	 */
	public ArrayList getComListByCondition(HttpServletRequest request,String strWhere,String strValue)throws RemoteException {
		ArrayList returnlist=new ArrayList();
		
		ArrayList allcomlist=getAppComList(request);
		
		if (allcomlist==null) return allcomlist;
		
		if ((strWhere.equals(""))||(strWhere==null)) 
		{
			return allcomlist;
		}
		
		if (strWhere.equals(Constants.COMP_WHERE_CONTAINER_ISNULL))  // FVC_CONTAINER is null
		{
			if (allcomlist.size()>0)
			{
				BaseObject tmpobj = null;
				for (int i=0; i < allcomlist.size(); i++) {
					tmpobj = (BaseObject) allcomlist.get(i);
					if (BaseObject.toString(tmpobj, "FVC_CONTAINER").equals(""))
					{
						returnlist.add(tmpobj);
					}
				}			
			}
			
		}
		
		if (strWhere.equals(Constants.COMP_WHERE_TYLE_ISDATASOURCE))  //FVC_TYPE='DATA_SOURCE'
		{
			if (allcomlist.size()>0)
			{
				BaseObject tmpobj = null;
				for (int i=0; i < allcomlist.size(); i++) {
					tmpobj = (BaseObject) allcomlist.get(i);
					if (BaseObject.toString(tmpobj, "FVC_TYPE").equals("DATA_SOURCE"))
					{
						returnlist.add(tmpobj);
					}
				}			
			}
		}
		
		if (strWhere.equals(Constants.COMP_WHERE_TYLE_ISDATASOURCE_BYNAME))  //FVC_TYPE='DATA_SOURCE' and FVC_HTMLNAME='指定值'
		{
			if (allcomlist.size()>0)
			{
				BaseObject tmpobj = null;
				for (int i=0; i < allcomlist.size(); i++) {
					tmpobj = (BaseObject) allcomlist.get(i);
					if ((BaseObject.toString(tmpobj, "FVC_TYPE").equals("DATA_SOURCE"))&&(BaseObject.toString(tmpobj, "FVC_HTMLNAME").equals(getValueFromRequest("APP_DATASOURCE_COM_NAME","",request))))
					{
						returnlist.add(tmpobj);
					}
				}			
			}
		}
		
		if (strWhere.equals(Constants.COMP_WHERE_IFNOTPURVIEW))  //FVC_IFNOTPURVIEW=1
		{
			if (allcomlist.size()>0)
			{
				BaseObject tmpobj = null;
				for (int i=0; i < allcomlist.size(); i++) {
					tmpobj = (BaseObject) allcomlist.get(i);
					if (BaseObject.toString(tmpobj, "FVC_IFNOTPURVIEW").equals("1"))
					{
						returnlist.add(tmpobj);
					}
				}			
			}
		}
		
		if (strWhere.equals(Constants.COMP_WHERE_FIELDCHECKSTRING))  //生成页面验证字串
		{
			if (allcomlist.size()>0)
			{
				BaseObject tmpobj = null;
				String snotcheck=getValueFromRequest(Constants.COM_PROPERTY_NOTCHECK,"",request);
				for (int i=0; i < allcomlist.size(); i++) {
					tmpobj = (BaseObject) allcomlist.get(i);
					if (("'SELECT','INPUT','CHECKBOX','TEXTAREA','RADIO','SELECTEDIT'").indexOf(BaseObject.toString(tmpobj, "FVC_TYPE"))>=0)
					{
							if (snotcheck.indexOf(BaseObject.toString(tmpobj, "FVC_HTMLNAME"))<0)
							{
								returnlist.add(tmpobj);
							}
					}
				}			
			}
		}
		
		if (strWhere.equals(Constants.COMP_WHERE_CONTAINER_BYVALUE))  //FVC_CONTAINER = 指定值;
		{
			if (allcomlist.size()>0)
			{
				BaseObject tmpobj = null;
				for (int i=0; i < allcomlist.size(); i++) {
					tmpobj = (BaseObject) allcomlist.get(i);
					if (BaseObject.toString(tmpobj, "FVC_CONTAINER").equals(strValue))
					{
						returnlist.add(tmpobj);
					}
				}			
			}
		}

		//System.out.println("strValue:"+strValue);
		
		if (strWhere.equals(Constants.COMP_WHERE_COMTYPEISINPUT))  //trim(FVC_FIELDNAME) is not null and trim(FVC_COMTYPE)='"+Constants.COM_TYPE_INPUT
		{
			if (allcomlist.size()>0)
			{
				BaseObject tmpobj = null;
				for (int i=0; i < allcomlist.size(); i++) {
					tmpobj = (BaseObject) allcomlist.get(i);
					if ((BaseObject.toString(tmpobj, "FVC_FIELDNAME").trim().equals("")==false)&&(BaseObject.toString(tmpobj, "FVC_COMTYPE").equals(Constants.COM_TYPE_INPUT))&&(BaseObject.toString(tmpobj, "FVC_DATASOURCE").equals(strValue)))
					{
						returnlist.add(tmpobj);
					}
				}			
			}
		}
		
		return returnlist;
	}

	
	/**
	 * 缓存T_COMPURVIEW表
	 *
	 *
	 * @String sAppName
	 */
	public ArrayList getComPurViewList(HttpServletRequest request)throws RemoteException {
		String sAppName=getAppName(request);
		String strTABLE_COMPURVIEW=DB_USER+".T_COMPURVIEW";
		
		if (DESIGN_MODEL<=2)
		{
			if (request.getAttribute(Constants.COM_PURVIEW_LIST_TAG+sAppName)==null){
				ArrayList list = this.selectSQL(AnalyseDataSource(strTABLE_COMPURVIEW),"FVC_APPNAME='"+sAppName+"'","");
				request.setAttribute(Constants.COM_PURVIEW_LIST_TAG+sAppName, list);
				return list;
			}
			else
			{
				ArrayList list=(ArrayList)request.getAttribute(Constants.COM_PURVIEW_LIST_TAG+sAppName);
				return list;
			}			
		}
		else
		{
			Cache cache = Cache.getInstance();
			ArrayList compurlist=(ArrayList)cache.getCache(Constants.COM_PURVIEW_LIST_TAG,sAppName);
			
			if (compurlist==null){
				compurlist = this.selectSQL(AnalyseDataSource(strTABLE_COMPURVIEW),"FVC_APPNAME='"+sAppName+"'","");
				cache.setCache(Constants.COM_PURVIEW_LIST_TAG,sAppName,compurlist);
			}
			return compurlist;
		}
	}	
	
	/**
	 * 按条件取T_COMPURVIEW表数据
	 *
	 *
	 * @String sAppName
	 */
	public ArrayList getPurViewListByCondition(HttpServletRequest request,String strWhere,User user,String sFlowID,String sFlowStep)throws RemoteException {
		ArrayList returnlist=new ArrayList();
		
		ArrayList allcomlist=getComPurViewList(request);
		
		if (allcomlist==null) return allcomlist;
		
		if ((strWhere.equals(""))||(strWhere==null)) 
		{
			return allcomlist;
		}
		
		if (strWhere.equals(Constants.COMPURVIEW_WHERE_MAINBASE))  //(FVC_ROLEID in ("+user.getRoleidlist()+") or FVC_USERID='"+user.getUserID()+"'  or FVC_DEPTD='"+user.getUserDeptId()+"') and FNB_FLOWID is null and FNB_FLOWSTEPID is null";
		{
			String sRoleidlist=user.getRoleidlist();
			
			
			if (sRoleidlist==null) sRoleidlist="";
			String sUserId=user.getUserID();
			if (sUserId==null) sUserId="";
			
			String sUserDeptId=user.getUserDeptId();
			if (sUserDeptId==null) sUserDeptId="";
			if (allcomlist.size()>0)
			{
				BaseObject tmpobj = null;
				boolean isAcc=false;
				for (int i=0; i < allcomlist.size(); i++) {
					tmpobj = (BaseObject) allcomlist.get(i);
					if ((BaseObject.toString(tmpobj, "FNB_FLOWID").equals(""))||(BaseObject.toString(tmpobj, "FNB_FLOWID").equals("0")))
					{
						//System.out.println("sRoleidlist:"+sRoleidlist);

						if ((sRoleidlist.indexOf(BaseObject.toString(tmpobj, "FVC_ROLEID"))>=0)&&(sRoleidlist.equals("")==false)) isAcc=true;

						if ((BaseObject.toString(tmpobj, "FVC_USERID").equalsIgnoreCase(sUserId))&&(sUserId.equals("")==false)) isAcc=true;

						if ((BaseObject.toString(tmpobj, "FVC_DEPTD").equalsIgnoreCase(sUserDeptId))&&(sUserDeptId.equals("")==false)) isAcc=true;
						
						if (isAcc)
						{
							returnlist.add(tmpobj);
						}

						isAcc=false;
					}
				}			
			}
			
		}

		if (strWhere.equals(Constants.COMPURVIEW_WHERE_FLOWSTEP))  //(FVC_ROLEID in ("+user.getRoleidlist()+") or FVC_USERID='"+user.getUserID()+"'  or FVC_DEPTD='"+user.getUserDeptId()+"') and ((FNB_FLOWID is null and FNB_FLOWSTEPID is null) or (FNB_FLOWID="+sFlowID+" and FNB_FLOWSTEPID="+sFlowStep+"))";
		{
			String sRoleidlist=user.getRoleidlist();
			String sUserId=user.getUserID();
			String sUserDeptId=user.getUserDeptId();
			if (allcomlist.size()>0)
			{
				BaseObject tmpobj = null;
				for (int i=0; i < allcomlist.size(); i++) {
					tmpobj = (BaseObject) allcomlist.get(i);
					if ((BaseObject.toString(tmpobj, "FNB_FLOWID").equals(""))||(BaseObject.toString(tmpobj, "FNB_FLOWID").equals("0")))
					{
						if ((sRoleidlist.indexOf(BaseObject.toString(tmpobj, "FVC_ROLEID"))>=0)||(BaseObject.toString(tmpobj, "FVC_USERID").equalsIgnoreCase(sUserId))||(BaseObject.toString(tmpobj, "FVC_DEPTD").equalsIgnoreCase(sUserDeptId)))
						{
						  returnlist.add(tmpobj);
						}
					}
					else
					{
						if ((BaseObject.toString(tmpobj, "FNB_FLOWID").equalsIgnoreCase(sFlowID))&&(BaseObject.toString(tmpobj, "FNB_FLOWSTEPID").equalsIgnoreCase(sFlowStep)))
						{
							if ((sRoleidlist.indexOf(BaseObject.toString(tmpobj, "FVC_ROLEID"))>=0)||(BaseObject.toString(tmpobj, "FVC_USERID").equalsIgnoreCase(sUserId))||(BaseObject.toString(tmpobj, "FVC_DEPTD").equalsIgnoreCase(sUserDeptId)))
							{
							  returnlist.add(tmpobj);
							}
						}
					}
				}			
			}
			
		}

		return returnlist;
	}	
	/**
	 * 取数据源BaseObject
	 *
	 *
	 * @HttpServletRequest request
	 */
	public BaseObject getDataSoreceBaseObject(HttpServletRequest request)throws RemoteException {
		/******取数据源的BaseObject******/
		BaseObject baseobj = null;
		
		String sdatasource=getValueFromRequest("APP_DATASOURCE_COM_NAME","",request);
		
		if ((sdatasource.equals("")==false)&&(sdatasource!=null))
		{
			if (request.getAttribute("APP_DATASOURCE_COM_BASE")!=null){
				baseobj=(BaseObject)request.getAttribute("APP_DATASOURCE_COM_BASE");
				if (BaseObject.toString(baseobj, "FVC_HTMLNAME").equals(sdatasource))
				{
					return baseobj;
				}
			}
			
			ArrayList list = getComListByCondition(request,Constants.COMP_WHERE_TYLE_ISDATASOURCE_BYNAME,sdatasource);
			if (list!=null)
			{
				if (list.size()>0)
				{
					baseobj = (BaseObject) list.get(0);
					request.setAttribute("APP_DATASOURCE_COM_BASE", baseobj);
					return baseobj;
				}
			}
		}
		
		if (request.getAttribute("APP_DATASOURCE_COM_BASE")==null){
			ArrayList list = getComListByCondition(request,Constants.COMP_WHERE_TYLE_ISDATASOURCE,"");
			if (list!=null)
			{
				if (list.size()>0)
				{
					baseobj = (BaseObject) list.get(0);
					request.setAttribute("APP_DATASOURCE_COM_BASE", baseobj);
					return baseobj;
				}
			}
		}
		else
		{
			baseobj=(BaseObject)request.getAttribute("APP_DATASOURCE_COM_BASE");
		}
		
		return baseobj;
	}
	
	/**
	 * 取数据源的主要参数
	 *
	 * @String sAppName
	 * @String sField 参数字段名
	 */
	public String getDataSourceParameter(String sField,HttpServletRequest request)throws RemoteException {
		BaseObject baseobj =getDataSoreceBaseObject(request);
		if (baseobj!=null)return BaseObject.toString(baseobj, sField);
		else return "";
	}
	
	
	/**
	 * 取业务的主要参数
	 *
	 * @String sAppName
	 * @String sField 参数字段名
	 */
	public String getAppMainParameter(String sField,HttpServletRequest request)throws RemoteException {
		return BaseObject.toString(getAppMainBaseObject(request), sField);
	}
	/**
	 * 取AppID
	 *
	 * @param response
	 * @return @throws
	 *         Exception
	 */
	public String getAppName(HttpServletRequest request){
		String sAppName="0";
		if ((request.getParameter(Constants.PARAMETER_APPID_NAME)!=null)&&(request.getParameter(Constants.PARAMETER_APPID_NAME).toString().equals("")==false)){
			sAppName=request.getParameter(Constants.PARAMETER_APPID_NAME).toString();
		}
		if ((sAppName.equals("0")==true)||(sAppName.equals("")==true)){
			if ((request.getAttribute(Constants.PARAMETER_APPID_NAME)!=null)&&(request.getAttribute(Constants.PARAMETER_APPID_NAME).toString().equals("")==false)){
				sAppName=request.getAttribute(Constants.PARAMETER_APPID_NAME).toString();
			}
		}
		if ((sAppName.equals("0")==false)||(sAppName.equals("")==false)){
			request.setAttribute(Constants.PARAMETER_APPID_NAME, sAppName);
		}
		return sAppName;
	}
	/**
	 * 从request中取参数
	 * @String sValueName  参数名
	 * @String sInitValue  初始值
	 *
	 * @String sAppName
	 */
	public String getValueFromRequest(String sValueName,String sInitValue,HttpServletRequest request) throws RemoteException {
		String sTmpValue=request.getParameter(sValueName);
		if (sTmpValue!=null){
			sTmpValue=sTmpValue.toString();
		}
		if (sTmpValue==null) sTmpValue="";
		if (sTmpValue.equals("")){
			Object obj=request.getAttribute(sValueName);
			if (obj!=null){
				sTmpValue=obj.toString();
			}
		}
		if (sTmpValue==null) sTmpValue="";
		if (sTmpValue.equals("")){
			sTmpValue=sInitValue;
		}
		if (sTmpValue==null) sTmpValue="";
		//if (sTmpValue.equals("")==false){
		//	request.setAttribute(sValueName, sTmpValue);
		//}
		return sTmpValue;
	}

	/**
	 * 从request中取参数
	 * @String sValueName  参数名
	 * @String sInitValue  初始值
	 *
	 * @String sAppName
	 */
	public String getValueFromAttribute(String sValueName,String sInitValue,HttpServletRequest request) throws RemoteException {
		String sTmpValue="";
		if (sTmpValue.equals("")){
			Object obj=request.getAttribute(sValueName);
			if (obj!=null){
				sTmpValue=obj.toString();
			}
		}
		if (sTmpValue==null) sTmpValue="";
		if (sTmpValue.equals("")){
			sTmpValue=sInitValue;
		}
		if (sTmpValue==null) sTmpValue="";
		return sTmpValue;
	}
	
	public String getCheckValueString(String sNotnullCom,String sNotCheckCom,HttpServletRequest request) throws RemoteException {
		if (getValueFromRequest(Constants.PARAMETER_ISVIEW_NAME,"0",request).equals("1")) return "\n function checkcomvalue(){return true;}";
		
		String sCheckString="";
		sNotnullCom=sNotnullCom.replaceAll("'","");
		
		ArrayList prolist =getComListByCondition(request,Constants.COMP_WHERE_FIELDCHECKSTRING,"");
		
		BaseObject baseobj = null;
		String sDatatype="";
		String sTmp="";
		String fieldname="";
		if (prolist != null) {
			int length=prolist.size();
			for (int i = 0; i < length; i++) {
				baseobj = (BaseObject) prolist.get(i);
				fieldname=BaseObject.toString(baseobj, "FVC_FIELDNAME");
				
				if (fieldname.equals("")==false)
				{
					sCheckString=sCheckString+"["+fieldname+",";
					sDatatype="''";
					sTmp=BaseObject.toString(baseobj, "FVC_DATATYPE");
					if (sTmp.equalsIgnoreCase("INT")) sDatatype="INT";
					if (sTmp.equalsIgnoreCase("EMAIL")) sDatatype="EMAIL";
					if (sTmp.equalsIgnoreCase("ZIPCODE")) sDatatype="ZIPCODE";
					if (sTmp.equalsIgnoreCase("TIME")) sDatatype="TIME";
					if (sTmp.equalsIgnoreCase("NUMBER")) sDatatype="NUMBER";
					if (sTmp.equalsIgnoreCase("DATE")) sDatatype="DATE";
					if (sTmp.equalsIgnoreCase("CARDNUM")) sDatatype="CARDNUM";
					if (sTmp.equalsIgnoreCase("FORCHAR")) sDatatype="FORCHAR";
					sCheckString=sCheckString+sDatatype+",";
					if ((sNotnullCom.indexOf(BaseObject.toString(baseobj, "FVC_HTMLNAME"))>=0)||(BaseObject.toString(baseobj, "FVC_IFNULL").equals("1"))) sCheckString=sCheckString+"0,";
					else  sCheckString=sCheckString+"1,";
					if ((BaseObject.toString(baseobj, "FVC_MAXLEN").equals("")==false)&&(BaseObject.toString(baseobj, "FVC_MAXLEN").equals("0")==false))  sCheckString=sCheckString+BaseObject.toString(baseobj, "FVC_MAXLEN")+"]";
					else sCheckString=sCheckString+"'']";
				}
			}
		}
		
		sCheckString="\n function checkcomvalue(){if (Value_CheckOut(\""+sCheckString+"\")){return true;}else{return false;}}";
		return sCheckString;
	}
	/**
	 * 得到页面JS
	 *
	 * @param request
	 * @return @throws
	 *         Exception
	 */
	public String getWebJs(HttpServletRequest request) throws RemoteException {
		try {
			String webjs=getAppMainParameter("FVC_JAVASCRIPT",request);;
			webjs=webjs+"\n"+getValueFromRequest(Constants.COM_PROPERTY_OTHER,"",request);
			webjs=webjs+"\n"+getCheckValueString(getValueFromRequest(Constants.COM_PROPERTY_NOTNULL,"",request),getValueFromRequest(Constants.COM_PROPERTY_NOTCHECK,"",request),request);

			String strMsg=getValueFromRequest(Constants.PARAMETER_RETURN_MSG,"",request);
			if (strMsg.equals("")==false)
			{
				strMsg=replHtmlWihtPara(strMsg,request);
				webjs=webjs+"\n"+java.net.URLDecoder.decode(strMsg,"GBK");
			}
			
			webjs=this.getHtmlCode(null,"JAVASCRIPT","",webjs,request);
			
			return webjs;
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR:"+e.toString();
		}
	}
	
	public ArrayList getAppComResource() throws RemoteException {
		Cache cache = Cache.getInstance();
		ArrayList Paralist=(ArrayList)cache.getCache(Constants.COM_T_COMRESOURCE_LIST,"");
		
		if (Paralist==null){
			Paralist = this.selectSQL(DB_USER+"."+"T_COMRESOURCE","","FN_NO");
			int length=Paralist.size();
			if (Paralist != null &&  length> 0) {
				cache.setCache(Constants.COM_T_COMRESOURCE_LIST,"",Paralist);
			}
		}
		return Paralist;
	}
	
	
	public String addComResource(String sCode,HttpServletRequest request) throws RemoteException {
		ArrayList resourcelist=getAppComResource();
		
		ArrayList allcomlist=getAppComList(request);
		String stype="";
		if (allcomlist!=null)
		{
			if (allcomlist.size()>0)
			{
				BaseObject tmpobj = null;
				for (int i=0; i < allcomlist.size(); i++) {
					tmpobj = (BaseObject) allcomlist.get(i);
					
					if (stype.indexOf(BaseObject.toString(tmpobj, "FVC_TYPE"))<0)
					{
						stype=stype+","+BaseObject.toString(tmpobj, "FVC_TYPE");
					}
				}			
			}
		}
		
		int length=resourcelist.size();
		BaseObject baseobj = null;
		String sResource="";
		String sTmp="";
		if (resourcelist != null &&  length> 0) {
			for (int i = 0; i < length; i++) {
				baseobj = (BaseObject) resourcelist.get(i);
				if (stype.indexOf(BaseObject.toString(baseobj, "FVC_TYPE"))>=0)
				{
					sTmp=BaseObject.toString(baseobj, "FVC_RESOURCE");
					if (sTmp.equals("")==false)
					{
						if (sResource.indexOf(sTmp)<0)	sResource=sResource+"\n"+sTmp;
					}
				}
			}
		}
		if (sResource.equals("")==false)
		{
			sCode=sResource+"\n"+sCode;
		}
		
		return sCode;		
	}
	
	public String AnalyseDataSource(String sMainDataSource) throws RemoteException {
		if ((sMainDataSource.indexOf(".")<0)&&(sMainDataSource.equals("")==false)&&(sMainDataSource!=null)&&(sMainDataSource.indexOf("select")<0)&&(sMainDataSource.indexOf("SELECT")<0))
		{
			sMainDataSource=DB_USER+"."+sMainDataSource;
		}
		return sMainDataSource;
	}	
	
	public String[] SplitDataSource(String sMainDataSource) throws RemoteException {
		String[] dbarray= {"","",""};
		
		if (sMainDataSource.indexOf(".")<0) 
		{
			dbarray[1]=DB_USER;
			dbarray[2]=sMainDataSource;
		}
		
		String[] sTmp=sMainDataSource.split("[.]");
		
		if (sTmp.length==1)
		{ 
			dbarray[1]=DB_USER;
			dbarray[2]=sTmp[0];
		}
		if (sTmp.length==2){
			dbarray[1]=sTmp[0];
			dbarray[2]=sTmp[1];
		}
		if (sTmp.length==3){
			dbarray[0]=sTmp[0];
			dbarray[1]=sTmp[1];
			dbarray[2]=sTmp[2];
		}
		
		return dbarray;
	}	
	
	
	
	public String getTableFields(String sMainDataSource) throws RemoteException {
		if (DESIGN_MODEL==1)
		{
			return getTableFieldsFromDbsys(sMainDataSource);
		}
		else
		{
			Cache cache = Cache.getInstance();
			String sFields=(String)cache.getCache(Constants.COMDB_TABLE_FIELDLIST_STRING,sMainDataSource);
			
			if (sFields==null){
				sFields=getTableFieldsFromDbsys(sMainDataSource);
				cache.setCache(Constants.COMDB_TABLE_FIELDLIST_STRING,sMainDataSource,sFields);
			}
			return sFields;
		}
		
	}
	public ArrayList iniReturnParameter(HttpServletRequest request) throws RemoteException {
		ArrayList returnlist=null;
		try{
			returnlist=(ArrayList)request.getAttribute(Constants.CORE_PARAMETER_NAME);
			if (returnlist!=null){
				returnlist.clear();
			}
			else
			{
				returnlist=new ArrayList();
			}
			for (int i = 0; i < Constants.CORE_PARAMETER_COUNT; i++) {
				returnlist.add(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnlist;
	}
	public void showWebError(String sErrorCode,String sReturnWebUrl,HttpServletRequest request) throws RemoteException {
		//BasePrint.println("Begin showWebError");
		String sHtml=this.getHtmlCode("ERRORWEB",request);
		sHtml=sHtml.replaceAll("<%=COMPONENTCONT%>",sErrorCode);
		
		if (sReturnWebUrl.equals("")) sReturnWebUrl="javascript:history.back();";
		BasePrint.println("RETURN_WEB_URL:"+sReturnWebUrl);
		sHtml=sHtml.replaceAll("<%=RETURN_WEB_URL%>",sReturnWebUrl);
		
		request.setAttribute(Constants.WEB_HTML_STRING, sHtml);
		
		
		/******取业务的主要参数******/
		String sWebModel=getAppMainParameter("FVC_WEBMODELRUL",request);
		if (sWebModel.equals("")){
			sWebModel=Constants.PARAMETER_DEFAULT_WEBMODEL;
		}
		/**************************/
		
		
		/******设定返回参数列表的各项参数值******/
		ArrayList Core_returnlist= iniReturnParameter(request);
		
		Core_returnlist.set(Constants.CORE_PARAMETER_RETURNURL,sWebModel);
		Core_returnlist.set(Constants.CORE_PARAMETER_DATAOBJECT,request.getAttribute(Constants.PARAMETER_ATTRIBUTEDATA));
		request.setAttribute(Constants.CORE_PARAMETER_NAME,Core_returnlist);
		/*************************************/
		
		//BasePrint.println("End showWebByHtml");
		return;
		
	}
	/**
	 * 重新初始化
	 *
	 * @return @throws
	 *         RemoteException
	 */
	public void initCache(HttpServletRequest request) throws RemoteException {
		try
		{
			Cache cache = Cache.getInstance();
			cache.destroy();
			cache = Cache.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
			showWebError(e.toString(),"javascript:history.back()",request);
		}
	}
	public String getComName(BaseObject bComObject) throws RemoteException {
		String sComName=BaseObject.toString(bComObject, "FVC_FIELDNAME");
		if (sComName.equals("")==false) return sComName;
		else return BaseObject.toString(bComObject, "FVC_HTMLNAME");
	}
	public String getComDefaultHeigth(String sComHeight,String sType) throws RemoteException {
		if (sComHeight==null) sComHeight="";
		if (sComHeight.equals(""))
		{
			if (sType.equals("LABLE"))	sComHeight="9";
			if (sType.equals("INPUT"))	sComHeight="12";
			if (sType.equals("TEXTAREA"))	sComHeight="28";
		}
		if (sComHeight.equals(""))	sComHeight="12";
		return sComHeight;
	}
	public String getComDefaultWidth(String sComWidth,String sType) throws RemoteException {
		if (sComWidth==null) sComWidth="";
		if (sComWidth.equals(""))
		{
			if (sType.equals("LABLE"))	sComWidth="60";
			if (sType.equals("INPUT"))	sComWidth="150";
			if (sType.equals("TEXTAREA"))	sComWidth="150";
		}
		if (sComWidth.equals(""))	sComWidth="100";
		return sComWidth;
	}	
	public String addAppDefaultHtml(String sCode,HttpServletRequest request) throws RemoteException {
		String sAppName=getAppName(request);

		String sWebAct="";
		Object obj=request.getAttribute(Constants.PARAMETER_WEBACT);
		if (obj!=null){
			sWebAct=obj.toString();
		}
		if (sWebAct.equals("")) sWebAct=getValueFromRequest(Constants.PARAMETER_WEBACT,Constants.PARAMETER_WEBACT_EDIT,request);
		
		String sWhere="";
		obj=request.getAttribute(Constants.PARAMETER_GETDATAWHERE);
		if (obj!=null){
			sWhere=obj.toString();
		}
		if (sWhere.equals("")) sWhere=getValueFromRequest(Constants.PARAMETER_GETDATAWHERE,"1<>1",request);

		String sHRe=getValueFromRequest(Constants.PARAMETER_IFREPLACEHTML,"1",request);
		String sJRe=getValueFromRequest(Constants.PARAMETER_IFREPLACEJS,"1",request);
		String sBackUrl=request.getRequestURI()+"?"+request.getQueryString();
		String sHiInput="<table border=0 height=0 cellSpacing=0 cellPadding=0><tr><td>\n<INPUT NAME=\"ON_ERROR_URL\" ID=\"ON_ERROR_URL\" type=\"hidden\" value=\""+sBackUrl+"\">\n";
		sHiInput=sHiInput+"<INPUT NAME=\"RETURN_URL\" ID=\"RETURN_URL\" type=\"hidden\">\n";
		sHiInput=sHiInput+"<INPUT NAME=\"RETURN_MSG\" ID=\"RETURN_MSG\" type=\"hidden\">\n";
		sHiInput=sHiInput+"<INPUT NAME=\"APP_DATASOURCE_COM_NAME\" ID=\"APP_DATASOURCE_COM_NAME\" type=\"hidden\">\n";
		sHiInput=sHiInput+"<INPUT NAME=\"APP_UPDATE_FIELD\" ID=\"APP_UPDATE_FIELD\" type=\"hidden\">\n";
		sHiInput=sHiInput+"<INPUT NAME=\"APP_NOT_UPDATE_FIELD\" ID=\"APP_NOT_UPDATE_FIELD\" type=\"hidden\">\n";
		sHiInput=sHiInput+"<INPUT NAME=\"IsView\" ID=\"IsView\" type=\"hidden\">\n</td></tr></table>\n";
		sCode=sHiInput+sCode;

		String s_ifpurview=this.getAppMainParameter("FNB_IFPURVIEW", request);
		if ((s_ifpurview.equals(""))||s_ifpurview==null) s_ifpurview="0";
		if (s_ifpurview.equals("1"))
		{
			sCode=getWebAddForm(sCode,"MyForm",Constants.PARAMETER_MAIN_ACTIONNAME+".do?method=saveToCore&"+Constants.PARAMETER_WEBACT+"="+sWebAct+"&"+Constants.PARAMETER_IFREPLACEJS+"="+sJRe+"&"+Constants.PARAMETER_IFREPLACEHTML+"="+sHRe+"&"+Constants.PARAMETER_APPID_NAME+"="+sAppName+"&"+Constants.PARAMETER_GETDATAWHERE+"="+sWhere,"_self",request);
		}
		else
		{
			sCode=getWebAddForm(sCode,"MyForm",Constants.PARAMETER_CORE_ACTIONNAME+".do?method=saveToCore&"+Constants.PARAMETER_WEBACT+"="+sWebAct+"&"+Constants.PARAMETER_IFREPLACEJS+"="+sJRe+"&"+Constants.PARAMETER_IFREPLACEHTML+"="+sHRe+"&"+Constants.PARAMETER_APPID_NAME+"="+sAppName+"&"+Constants.PARAMETER_GETDATAWHERE+"="+sWhere,"_self",request);
		}
		
		sCode=sCode+this.getWebAddBr("\n",1)+"<TABLE><TR><TD><IFRAME NAME=Hiddenframe ID=Hiddenframe style=\"display:none\" src=\"\"></IFRAME></TD></TR></TABLE>"+this.getWebAddBr("\n",3);
		return sCode;		
	}	
	public String withSpecialChar(String sCode) throws RemoteException {
		sCode=sCode.replaceAll("\\%","\\\\%");
		sCode=sCode.replaceAll("\\=","\\\\=");
		sCode=sCode.replaceAll("\"","\\\\\"");
		sCode=sCode.replaceAll("'","\'");
		return sCode;
	}	
	public ArrayList getAppComPara(HttpServletRequest request) throws RemoteException {
		Cache cache = Cache.getInstance();
		ArrayList Paralist=(ArrayList)cache.getCache(Constants.COM_T_COMPARA_LIST,"");
		
		if (Paralist==null){
			Paralist = this.selectSQL(DB_USER+"."+"T_COMPARA","","");
			int length=Paralist.size();
			if (Paralist != null &&  length> 0) {
				cache.setCache(Constants.COM_T_COMPARA_LIST,"",Paralist);
			}
		}
		return Paralist;
	}
	
	
	public String getTableFieldsFromDbsys(String sMainDataSource) throws RemoteException {
		String sFields="";
		String dbname="";
		String dbuser="";
		String tablename="";
		
		if (sMainDataSource.indexOf(".")<0) 
		{
			dbuser=DB_USER;
			tablename=sMainDataSource;
		}
		String[] sTmp=sMainDataSource.split("[.]");
		
		if (sTmp.length==1)
		{ 
			dbuser=DB_USER;
			tablename=sTmp[0];
		}
		if (sTmp.length==2){
			dbuser=sTmp[0];
			tablename=sTmp[1];
		}
		if (sTmp.length==3){
			dbname=sTmp[0];
			dbuser=sTmp[1];
			tablename=sTmp[2];
		}
		if (dbname.equals("")==false) dbname=dbname+".";
		if (dbuser.equals("")==false) dbuser=dbuser+".";
		
		ArrayList list=null;
		try
		{
			if (DB_SYS.equalsIgnoreCase("oracle"))
			{
				list =selectSQL("sys.dba_tab_columns","select COLUMN_NAME from sys.dba_tab_columns where UPPER(OWNER)=UPPER('"+dbuser.replaceAll("[.]","")+"') and  UPPER(TABLE_NAME)=UPPER('"+tablename+"')");
			}
			
			if (DB_SYS.equalsIgnoreCase("sybase"))
			{
				list =selectSQL(dbname+"dbo.syscolumns","select col.name as COLUMN_NAME from "+dbname+"dbo.sysobjects as tab,"+dbname+"dbo.syscolumns as col where col.id = tab.id and  tab.name = '"+tablename+"'");
			}
			
			if (DB_SYS.equalsIgnoreCase("mysql"))
			{
				list =selectSQL(DB_USER+".v_tablefield","select COLUMN_NAME from "+DB_USER+".v_tablefield as tab  where tab.TABLE_NAME = '"+tablename+"'");
			}
			

			if (DB_SYS.equalsIgnoreCase("mssql"))
			{
				list =selectSQL(DB_USER+".v_tablefield","select COLUMN_NAME from "+DB_USER+".v_tablefield as tab  where tab.TABLE_NAME = '"+tablename+"'");
			}
			
			
			int length=list.size();
			BaseObject baseobj=null;
			if (list != null &&  length> 0) {
				for (int i=0; i < length; i++) {
					baseobj = (BaseObject) list.get(i);
					sFields=sFields+","+BaseObject.toString(baseobj, "COLUMN_NAME");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return sFields;
		}
		
		if (sFields == null) sFields="";
		
		return sFields;
		
	}
	
	public BaseObject getAppDataObj(HttpServletRequest request,String sWhere) throws RemoteException {
		ArrayList datalist =null;
		BaseObject databaseobj = null;
		
		/******取业务的主要参数******/
		String sMainDataSource=replHtmlWihtPara(getDataSourceParameter( "FVC_DATASOURCE",request),request);
		sMainDataSource=AnalyseDataSource(sMainDataSource);
		String sKeyField=getDataSourceParameter("FVC_FIELDNAME",request);
		/******************/
		
		/******取数据******/
		if (request.getAttribute(Constants.PARAMETER_ATTRIBUTEDATA)==null)
		{
			if (sMainDataSource.equals("")==false)
			{
				if (DB_SYS.equalsIgnoreCase("sybase"))
				{
					if ((sMainDataSource.indexOf("select")>=0)||(sMainDataSource.indexOf("SELECT")>=0))
					{
						datalist = this.selectSQL(sMainDataSource,"set rowcount 1 select * from ("+sMainDataSource+") A where "+sWhere+"  set rowcount 0");
					}
					else
					{
						datalist = this.selectSQL(sMainDataSource,"set rowcount 1 select * from "+sMainDataSource+" A where "+sWhere+"  set rowcount 0");
					}
				}
				
				if (DB_SYS.equalsIgnoreCase("oracle"))
				{
					if ((sMainDataSource.indexOf("select")>=0)||(sMainDataSource.indexOf("SELECT")>=0))
					{
						datalist = this.selectSQL("("+sMainDataSource+") A ",sWhere+" and rownum=1","");
					}
					else
					{
						datalist = this.selectSQL(sMainDataSource+" A ",sWhere+" and rownum=1","");
					}
				}

				if (DB_SYS.equalsIgnoreCase("mysql"))
				{
					if ((sMainDataSource.indexOf("select")>=0)||(sMainDataSource.indexOf("SELECT")>=0))
					{
						datalist = this.selectSQL("("+sMainDataSource+") limit 1 A ",sWhere,"");
					}
					else
					{
						datalist = this.selectSQL(sMainDataSource+" limit 1 A ",sWhere,"");
					}
				}

				if (DB_SYS.equalsIgnoreCase("mssql"))
				{
					if ((sMainDataSource.indexOf("select")>=0)||(sMainDataSource.indexOf("SELECT")>=0))
					{
						//datalist = this.selectSQL("("+sMainDataSource+") A ",sWhere,"");
						datalist = this.selectSQL(sMainDataSource,"select top 1 * from ("+sMainDataSource+") A where "+sWhere);
					}
					else
					{
						//datalist = this.selectSQL(sMainDataSource+" A ",sWhere,"");
						datalist = this.selectSQL(sMainDataSource,"select top 1 * from "+sMainDataSource+" A where "+sWhere);
					}
				}
				
				
				
				if (datalist.size()<=0){
					datalist=null;
				}
				else
				{
					databaseobj = (BaseObject) datalist.get(0);
					request.setAttribute(Constants.BUSINESS_KEYVALUE_NAME,BaseObject.toString(databaseobj,sKeyField));
				}
				request.setAttribute(Constants.PARAMETER_ATTRIBUTEDATA,databaseobj);
			}			
			
		}
		else
		{
			databaseobj=(BaseObject)(request.getAttribute(Constants.PARAMETER_ATTRIBUTEDATA));
		}
		/******************/
		
		return databaseobj;
	}	
	public long getSequenceValue(String sName) throws RemoteException {
		long lvalue=-1;	
		
		if (DB_SYS.equalsIgnoreCase("oracle"))
		{
			lvalue=getSeqNextValue(sName);
		}
		
		if (DB_SYS.equalsIgnoreCase("sybase"))
		{
			
			Connection conn = null;
			CallableStatement cstmt = null;
			try {
				conn = getConnection();
				String callSQL = "{call "+AnalyseDataSource(sName)+"(?)}";
				cstmt = conn.prepareCall(callSQL);
				cstmt.registerOutParameter(1, java.sql.Types.INTEGER); 
				cstmt.execute();
				
				lvalue = cstmt.getInt(1);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (cstmt != null) {
						cstmt.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		if (DB_SYS.equalsIgnoreCase("mysql"))
		{
		    Connection con = null;
		    Statement stmt = null;
		    ResultSet sqlrs = null;
		    
		    
		    
			String dbuser="";
			String tablename="";
			
			if (sName.indexOf(".")<0) 
			{
				dbuser=DB_USER;
				tablename=sName;
			}
			String[] sTmp=sName.split("[.]");
			
			if (sTmp.length==1)
			{ 
				dbuser=DB_USER;
				tablename=sTmp[0];
			}
			if (sTmp.length==2){
				dbuser=sTmp[0];
				tablename=sTmp[1];
			}
		    

		    try {

		      //得到连接
		      con = this.getConnection();
		      stmt = con.createStatement();

		      //查询指定的数据
		      sqlrs = stmt.executeQuery("select "+dbuser+".next_seqval('" + tablename + "')");

		      if (sqlrs.next()) {
		        return sqlrs.getLong(1);
		      }

		    }
		    catch (Exception e) {
		      e.printStackTrace();
		    }
		    finally {

		      if (sqlrs != null) {
		        try {
		          sqlrs.close();
		        }
		        catch (Exception ignore) {
		        }
		      }

		      if (stmt != null) {
		        try {
		          stmt.close();
		        }
		        catch (Exception ignore) {
		        }
		      }

		      if (con != null) {
		        try {
		          con.close();
		        }
		        catch (Exception ignore) {
		        }
		      }

		    }
		}
		
		if (DB_SYS.equalsIgnoreCase("mssql"))
		{
		    Connection con = null;
		    Statement stmt = null;
		    ResultSet sqlrs = null;
		    
		    
		    
			String dbuser="";
			String tablename="";
			
			if (sName.indexOf(".")<0) 
			{
				dbuser=DB_USER;
				tablename=sName;
			}
			String[] sTmp=sName.split("[.]");
			
			if (sTmp.length==1)
			{ 
				dbuser=DB_USER;
				tablename=sTmp[0];
			}
			if (sTmp.length==2){
				dbuser=sTmp[0];
				tablename=sTmp[1];
			}
		    

		    try {

		      //得到连接
		      con = this.getConnection();
		      stmt = con.createStatement();

		      //查询指定的数据
		      sqlrs = stmt.executeQuery("select NEXT VALUE for "+dbuser+"." + tablename);

		      if (sqlrs.next()) {
		        return sqlrs.getLong(1);
		      }

		    }
		    catch (Exception e) {
		      e.printStackTrace();
		    }
		    finally {

		      if (sqlrs != null) {
		        try {
		          sqlrs.close();
		        }
		        catch (Exception ignore) {
		        }
		      }

		      if (stmt != null) {
		        try {
		          stmt.close();
		        }
		        catch (Exception ignore) {
		        }
		      }

		      if (con != null) {
		        try {
		          con.close();
		        }
		        catch (Exception ignore) {
		        }
		      }

		    }
		}		
		

		return lvalue;
	}	
	public String getStringtoDate(String sTmpValue) throws RemoteException {
		if (DB_SYS.equalsIgnoreCase("oracle"))
		{
			return "to_date('"+sTmpValue+"','"+PromosDate.getDateFormat(sTmpValue)+"')";
		}
		if (DB_SYS.equalsIgnoreCase("sybase"))
		{
			return "CONVERT(DATETIME,'"+sTmpValue+"')";
		}
		
		if (DB_SYS.equalsIgnoreCase("mysql"))
		{
			return "str_to_date('"+PromosDate.getDateFormat(sTmpValue)+"','"+sTmpValue+"')";
		}

		if (DB_SYS.equalsIgnoreCase("mssql"))
		{
			return "CONVERT(DATETIME,'"+sTmpValue+"')";
		}
		
		return "'"+sTmpValue+"'";
	}
	
	public boolean login(HttpServletRequest request) throws RemoteException {
	    try {

			HttpSession session = request.getSession();
			String login_name=request.getParameter("USER_NAME");
			String pass_word=request.getParameter("PASS_WORD");
			String project_name=request.getParameter("DEVUSER");
			
			DBBase dbc = DBBaseRemote.getDBBase();
			
			ArrayList userlist =dbc.selectSQL(DB_USER+"."+"T_USER","select * from "+DB_USER+"."+"T_USER where LOGIN_CODE='"+login_name+"' and LOGIN_PASS='"+pass_word+"' and (SYS_FLAG='"+project_name+"'  or SYS_FLAG is null or SYS_FLAG='')");
			BaseObject baseobj = null;
			if (userlist != null &&  userlist.size()> 0) {
				baseobj = (BaseObject) userlist.get(0);
	
				User user = new User();
				
				user.setUserName(BaseObject.toString(baseobj, "USER_NAME"));
				user.setUserID(BaseObject.toString(baseobj, "USER_ID"));
				user.setUserCode(BaseObject.toString(baseobj, "LOGIN_CODE"));
				user.setUserDeptId(BaseObject.toString(baseobj, "DEPT_ID"));
				user.setUserDeptName(BaseObject.toString(baseobj, "DEPT_NAME"));
	
	
				ArrayList rolelist =dbc.selectSQL(DB_USER+"."+"T_ROLE_USER","select * from promos.t_role a,promos.t_role_user b where a.ROLE_ID=b.ROLE_ID and b.USER_ID=" +BaseObject.toString(baseobj, "USER_ID")+" and (a.SYS_FLAG='"+project_name+"'  or a.SYS_FLAG is null or a.SYS_FLAG='')");
				String srolelist="";
				int rolelength=rolelist.size();
				BaseObject rolebaseobj = null;
				if (rolelist != null &&  rolelength> 0) {
					for (int i=0; i < rolelength; i++) {
						rolebaseobj = (BaseObject) rolelist.get(i);
						srolelist=srolelist+BaseObject.toString(rolebaseobj, "ROLE_ID")+",";
					}
				}
				if (srolelist.length()>0) srolelist=","+srolelist;
				user.setRoleidlist(srolelist);
				
				user.setDesignRole(dbc.getValue("select top 1 ROLE_ID from "+DB_USER+".T_ROLE where UPPER(ROLE_NAME)='"+Constants.SYS_ROLE_DESIGNER+"'"));
				user.setOwnerRole(dbc.getValue("select top 1 ROLE_ID from "+DB_USER+".T_ROLE where UPPER(ROLE_NAME)='"+Constants.SYS_ROLE_OWNER+"'"));
				
				
				session.setAttribute(User.SESSION_USER,user);
				
				return true;
			}
			else
			{
				return false;
			}
	    }
	    catch (Exception e) 
	    {
	       e.printStackTrace();
	       return false;
		}
	}
	
	
}