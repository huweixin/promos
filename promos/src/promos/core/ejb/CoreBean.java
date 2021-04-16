package promos.core.ejb;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.servlet.http.*;

import java.rmi.RemoteException;

import promos.base.ejb.BaseBean;
import promos.base.ejb.DBBase;
import promos.base.ejb.DBBaseRemote;
import promos.base.objects.BaseObject;
import promos.base.objects.BasePrint;
import promos.base.objects.Constants;
import promos.base.objects.User;


/**
 *
 * <p>
 * ����: Bean CoreBean
 * </p>
 * <p>
 * ����: ϵͳ������Bean
 * </p>
 * <p>
 * ��Ȩ: Copyright (c) 2003-2004
 * </p>
 * <p>
 * ��˾: ��������ý���޹�˾
 * </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 08/30/2003
 *
 */
@Stateless

public class CoreBean extends BaseBean implements Core {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	SessionContext sessionContext;
	
	/**
	 * <p>
	 * Bean�����Լ�������Ҫ����Beanʵ��
	 * </p>
	 *
	 * @throws CreateException
	 */
	public void ejbCreate() throws CreateException {
	}
	
	/**
	 * <p>
	 * Bean�����Լ�������Ҫɾ��Beanʵ��
	 * </p>
	 *
	 */
	public void ejbRemove() {
	}
	/**
	 * ���µõ������л���Beanʵ��
	 *
	 */
	public void ejbActivate() {
	}
	/**
	 * ���л�Beanʵ�����ͷ���Դ
	 *
	 */
	public void ejbPassivate() {
	}
	/**
	 * �õ������Ļ�������Bean������������ͨ�ţ�ִ�й���
	 *
	 * @param sessionContext
	 */
	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}	
	
	

	public String exeFunctionByType(String sType,HttpServletRequest request) throws RemoteException {
		ArrayList list=getFunctionList(request);
		String sError="";
		int length=list.size();
		BaseObject obj;
		if (list != null &&  length> 0) {
			String sAppName=getAppName(request);
			for (int i=0; i < length; i++) {
				obj=(BaseObject)list.get(i);
				if ((BaseObject.toString(obj, "FVC_TYPE").equals(sType))&&(BaseObject.toString(obj, "FVC_APPNAME").equals(sAppName)))
				{
					sError=exeFunctionByBaseObject(obj,request);
					if (sError.equals("")==false) return sError;
				}
			}
			
		}
		return sError;
	}	
	public String exeFunctionByBaseObject(BaseObject FuncObj,HttpServletRequest request) throws RemoteException {
		String methodname="";
		String motheparvalues="";
			if (ifExefuntion(BaseObject.toString(FuncObj, "FVC_EXPRESS"),BaseObject.toString(FuncObj, "FVC_EXPVALUES"),request))
			{
				methodname=BaseObject.toString(FuncObj, "FVC_METHODNAME");
				
				motheparvalues=replHtmlWihtPara(BaseObject.toString(FuncObj, "FVC_PARVALUES"),request);
				
				Object otr=exeFuntion(methodname,motheparvalues,request);
				if (otr==null) otr="";
				BasePrint.println("ִ�з���"+methodname+"����ֵΪ:"+otr.toString());
				if (otr.toString().indexOf("ERROR:")==0)
				{
					return "ִ�з���["+methodname+"]����,�������ֵΪ["+motheparvalues+"]!\n"+otr.toString().substring(6);
				}
				
			}
			return "";
	}
	
	
	
	
	
	/**
	 * �õ���ǰҳ��HTML
	 *
	 * @return @throws
	 *         Exception
	 */
	public String getHtmlWithAppID(HttpServletRequest request) throws RemoteException {
		
		String s_comhtml="";
		try
		{
			String s_is_set_compurview=getValueFromAttribute(Constants.PARAMETER_IS_SET_COMPURVIEW,"0",request);
			
			String sWhere="";
			Object obj=request.getAttribute(Constants.PARAMETER_GETDATAWHERE);
			if (obj!=null){
				sWhere=obj.toString();
			}
			if (sWhere.equals("")) sWhere=getValueFromRequest(Constants.PARAMETER_GETDATAWHERE,"1<>1",request);
			sWhere=sWhere.replaceAll("::","=");
			
			/******ȡ����******/
			BaseObject databaseobj = getAppDataObj(request,sWhere);
			/******************/
			
			
			/******��װҳ��******/
			BaseObject baseobj=null;
			
			ArrayList list =getComListByCondition(request,Constants.COMP_WHERE_CONTAINER_ISNULL,"");
			//this.selectSQL(  getAppMainParameter("FVC_TABLE_APPCOMP",request),"FVC_CONTAINER is null and FVC_APPNAME='"+sAppName+"'","FVC_SHOWORDER");
			
			ArrayList Paralist = getAppComPara(request);
			
			int length=list.size();
			String s_DivScroll="";
			String sDisplay="";
			String sDivIndex="";
			String sComName="";
			String sPosition="absolute";
						
			if (list != null &&  length> 0) {
				for (int i=0; i < length; i++) {
					baseobj = (BaseObject) list.get(i);
					sComName=getComName(baseobj);
					if (sComName.equals("")) sComName="0";
					sDisplay=BaseObject.toString(baseobj, "FVC_DISPLAY");
					if ((sDisplay.equals("1")==true)||((getValueFromRequest(Constants.COM_PROPERTY_NOTDISPLAY,"",request).indexOf(sComName)>=0)&&(getNotPurViewCom(request).indexOf(sComName)<0))) sDisplay="hidden";
					sDivIndex=BaseObject.toString(baseobj, "FVC_DIVINDEX");
					if (sDivIndex.equals("")==false) sDivIndex=" Z-INDEX:"+sDivIndex;
					
					
					
					if ((BaseObject.toString(baseobj, "FVC_IFDIV").equals("1"))||(s_is_set_compurview.equals("1")))
					{

						s_DivScroll=BaseObject.toString(baseobj, "FVC_IFDIVSCROLL");
						if (s_DivScroll.equals("1")==true) s_DivScroll=" OVERFLOW: scroll;";
						else s_DivScroll="";
						
						sPosition=BaseObject.toString(baseobj, "FVC_POSITION");
						if (sPosition.equals("")==true) sPosition="absolute";
						
						String str_Cont=" valign='center' ";
						
						if (s_is_set_compurview.equals("1")) 
						{
							s_DivScroll=s_DivScroll+" CURSOR:hand;";
							str_Cont=str_Cont+" onmousedown=\"drags(this);\" onDblclick=\"setcompurview(this,'"+BaseObject.toString(baseobj, "FVC_APPNAME")+"','"+BaseObject.toString(baseobj, "FVC_HTMLNAME")+"','"+BaseObject.toString(baseobj, "FVC_HTMLID")+"')\"";
						}
						
						
						s_comhtml=s_comhtml+this.getDivHtmlCode("DIV"+BaseObject.toString(baseobj, "FVC_HTMLID"),sPosition,sDisplay,BaseObject.toString(baseobj, "FVC_HEIGHT"),BaseObject.toString(baseobj, "FVC_WIDTH"),BaseObject.toString(baseobj, "FVC_LEFT"),BaseObject.toString(baseobj, "FVC_TOP"),s_DivScroll+sDivIndex,str_Cont,getBaseComHtml(baseobj,Paralist,databaseobj,sWhere,request),request);
						
					}
					else
					{
						s_comhtml=s_comhtml+BaseObject.toString(baseobj, "FVC_TITLENAME")+getBaseComHtml(baseobj,Paralist,databaseobj,sWhere,request);
					}
				}
			}
			return s_comhtml;
		} catch (Exception e) {
			e.printStackTrace();
			return s_comhtml;
		}
	}
	
	
	/**
	 * ��ʾҳ��
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return @throws
	 *         Exception
	 */
	public boolean showFromCore(HttpServletRequest request) throws RemoteException {
		String s_comhtml=" ";
		try
		{
			String s_is_set_compurview=getValueFromAttribute(Constants.PARAMETER_IS_SET_COMPURVIEW,"0",request);
			/******ϵͳȨ���ж�******/
			String s_ifpurview=this.getAppMainParameter("FNB_IFPURVIEW", request);
			if ((s_ifpurview.equals(""))||s_ifpurview==null) s_ifpurview="0";
			if ((s_ifpurview.equals("1"))&&(s_is_set_compurview.equals("1")==false))
			{
				String sys_view=getValueFromAttribute(Constants.SYS_PURVIEW_VIEW,"0",request);
				String sys_edit=getValueFromAttribute(Constants.SYS_PURVIEW_EDIT,"0",request);
				String sys_add=getValueFromAttribute(Constants.SYS_PURVIEW_ADD,"0",request);
				String sys_dele=getValueFromAttribute(Constants.SYS_PURVIEW_DELE,"0",request);
	
				String sys_design_admin=getValueFromAttribute(Constants.SYS_DESIGN_ADMIN,"0",request);
				String sys_oper_admin=getValueFromAttribute(Constants.SYS_OPER_ADMIN,"0",request);
				String sys_self_design=getValueFromAttribute(Constants.SYS_SELF_DESIGN,"0",request);
				
				if ((sys_view.equals("0"))&&(sys_edit.equals("0"))&&(sys_add.equals("0"))&&(sys_dele.equals("0"))&&(sys_oper_admin.equals("0")&&(sys_design_admin.equals("0"))&&(sys_self_design.equals("0")))){
					showWebError(Constants.ERROR_NOPURVIEW,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
					return false;
				}
			}
			/******************/
			

			
			/******ҳ���HTML******/
			//BasePrint.println("start getHtmlWithAppID");
			s_comhtml=getHtmlWithAppID(request);
			if ((s_comhtml.equals(""))||s_comhtml==null) s_comhtml="        ";
			//BasePrint.println("end getHtmlWithAppID");
			if (s_comhtml.substring(0,6).equals("ERROR:"))
			{
				s_comhtml=s_comhtml.substring(6);
				showWebError(s_comhtml,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
				return false;
			}
			/******************/
			
			
			s_comhtml=addComResource(s_comhtml,request);
			
			s_comhtml=addAppDefaultHtml(s_comhtml,request);
			
			/******ҳ���JS******/
			//BasePrint.println("start getWebJs");
			String js=getWebJs(request);
			if ((js.equals(""))||js==null) js="         ";
			if (js.substring(0,6).equals("ERROR:"))
			{
				js=js.substring(6);
				showWebError(js,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
				return false;
			}
			//BasePrint.println("end getWebJs");
			/******************/
			
			
			//BasePrint.println("Start showWebByHtml");
			return showWebByHtml(s_comhtml,js,request);
		} catch (Exception e) {
	    	
			e.printStackTrace();
            throw new EJBException(e.toString());
		}
		
	}
	
	
	
	
	/**
	 * ��������
	 *
	 * @param request
	 * @return @throws
	 *         Exception
	 */
	public boolean saveToCore(int sReturnType,HttpServletRequest request) throws RemoteException {
		
		String sWhere="";
		String sHRe=getValueFromRequest(Constants.PARAMETER_IFREPLACEHTML,"1",request);
		try
		{
			sWhere=getValueFromRequest(Constants.PARAMETER_GETDATAWHERE,"1<>1",request);
			sWhere=sWhere.replaceAll("::","=");
			String sWebAct=getValueFromRequest(Constants.PARAMETER_WEBACT,"",request);
			
			if ((sWebAct.equals(Constants.PARAMETER_WEBACT_DELETE)==false)&&(sWebAct.equals(Constants.PARAMETER_WEBACT_ADD)==false)&&(sWebAct.equals(Constants.PARAMETER_WEBACT_EDIT)==false))
			{
				showWebError(Constants.ERROR_NOAPPID+sWebAct,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
				return false;
			}
			
			/******ϵͳȨ���ж�******/
			String s_ifpurview=this.getAppMainParameter("FNB_IFPURVIEW", request);
			if ((s_ifpurview.equals(""))||s_ifpurview==null) s_ifpurview="0";
			System.out.println("s_ifpurview:"+s_ifpurview);
			if (s_ifpurview.equals("1"))
			{
				String sys_edit=getValueFromAttribute(Constants.SYS_PURVIEW_EDIT,"0",request);
				String sys_add=getValueFromAttribute(Constants.SYS_PURVIEW_ADD,"0",request);
				String sys_dele=getValueFromAttribute(Constants.SYS_PURVIEW_DELE,"0",request);

				String sys_design_admin=getValueFromAttribute(Constants.SYS_DESIGN_ADMIN,"0",request);
				String sys_oper_admin=getValueFromAttribute(Constants.SYS_OPER_ADMIN,"0",request);
				String sys_self_design=getValueFromAttribute(Constants.SYS_SELF_DESIGN,"0",request);
				
				//System.out.println("sys_dele:"+sys_dele);

				if ((sWebAct.equals(Constants.PARAMETER_WEBACT_DELETE))&&(sys_dele.equals("0"))&&(sys_oper_admin.equals("0")&&(sys_design_admin.equals("0"))&&(sys_self_design.equals("0")))){
					showWebError(Constants.ERROR_NODELEPURVIEW,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
					return false;
				}

				if ((sWebAct.equals(Constants.PARAMETER_WEBACT_ADD))&&(sys_add.equals("0"))&&(sys_oper_admin.equals("0"))&&(sys_design_admin.equals("0"))&&(sys_self_design.equals("0"))){
					showWebError(Constants.ERROR_NOADDPURVIEW,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
					return false;
				}

				if ((sWebAct.equals(Constants.PARAMETER_WEBACT_EDIT))&&(sys_edit.equals("0"))&&(sys_oper_admin.equals("0"))&&(sys_design_admin.equals("0"))&&(sys_self_design.equals("0"))){
					showWebError(Constants.ERROR_NOEDITPURVIEW,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
					return false;
				}
				
			}
			/******************/
			
			
			
			/******ȡҵ�����Ҫ����******/
			String sMainDataSource=replHtmlWihtPara(getDataSourceParameter( "FVC_DATASOURCE",request),request);
			sMainDataSource=AnalyseDataSource(sMainDataSource);
			if ((sMainDataSource==null)||(sMainDataSource.equals("")==true)){
				showWebError(Constants.ERROR_NODATASOURCE,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
				return false;
			}
			String sKeyField=getDataSourceParameter("FVC_FIELDNAME",request);
			if ((sKeyField==null)||(sKeyField.equals("")==true)){
				showWebError(Constants.ERROR_NOKEYFIELD,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
				return false;
			}
			String sAppSeq=getDataSourceParameter("FVC_OBJECTNAME",request);
			if ((sAppSeq==null)||(sAppSeq.equals("")==true)){
				showWebError(Constants.ERROR_NOSEQ,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
				return false;
			}
			String sIfFlow=BaseObject.toString(getAppMainBaseObject(request), "FVC_IFFLOW");
			if ((sIfFlow==null)||(sIfFlow.equals("")==true)) sIfFlow="0";
			/******************/
			
			/******ִ�б�������ǰ�Զ������******/
			if (!exeCustomFunction(Constants.FUNCTION_TYPE_BEFORSAVE,request)) 
			{
		    	
				showWebError("ִ�б�������ǰ�Զ�����̳���!",getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
	            throw new EJBException("ִ�б�������ǰ�Զ�����̳���!");
			}
			/**********************************/
			
			/******��������******/
			
			/******ɾ��******/
			if (sWebAct.equals(Constants.PARAMETER_WEBACT_DELETE)){
				
				/******ִ��ɾ��ǰ�Զ������******/
				if (!exeCustomFunction(Constants.FUNCTION_TYPE_BEFORDEL,request))			
				{
			    	
					showWebError("ִ��ɾ��ǰ�Զ�����̳���!",getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
		            throw new EJBException("ִ��ɾ��ǰ�Զ�����̳���!");
				}

				/**********************************/
				
				try
				{
					this.executeSQL("delete from "+sMainDataSource+"  where "+sWhere);
				} catch (Exception e) {
			    	
					showWebError(Constants.ERROR_CANNOTDELETE,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
					request.setAttribute(Constants.PARAMETER_GETDATAWHERE,sWhere);
					e.printStackTrace();
		            throw new EJBException(Constants.ERROR_CANNOTDELETE);
				}
				
				/******ִ��ɾ�����Զ������******/
				if (!exeCustomFunction(Constants.FUNCTION_TYPE_AFTERDEL,request)) 			
				{
			    	
					showWebError("ִ��ɾ�����Զ�����̳���!",getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
		            throw new EJBException("ִ��ɾ�����Զ�����̳���!");
				}

				/**********************************/
				
			}
			/*****************/
			
			
			/******��ӻ��޸�******/
			if ((sWebAct.equals(Constants.PARAMETER_WEBACT_EDIT))||(sWebAct.equals(Constants.PARAMETER_WEBACT_ADD)))
			{
				
				BaseObject baseobj=null;
				ArrayList list = getComListByCondition(request,Constants.COMP_WHERE_COMTYPEISINPUT,getDataSourceParameter( "FVC_HTMLNAME",request));
				//this.selectSQL(getAppMainParameter("FVC_TABLE_APPCOMP",request),"select distinct trim(FVC_FIELDNAME) as FVC_FIELDNAME,FVC_TYPE,FVC_DATATYPE from "+getAppMainParameter("FVC_TABLE_APPCOMP",request)+" where trim(FVC_FIELDNAME) is not null and trim(FVC_COMTYPE)='"+Constants.COM_TYPE_INPUT+"' and FVC_APPNAME='"+sAppName+"'");
				String sFieldList=getTableFields(sMainDataSource);
				if (sFieldList.equals(""))
				{
			    	
					showWebError(Constants.ERROR_NOCREATETABLE,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
					request.setAttribute(Constants.PARAMETER_GETDATAWHERE,sWhere);
		            throw new EJBException(Constants.ERROR_NOCREATETABLE);
				}
				
				String sUpdateField=getValueFromRequest(Constants.PARAMETER_UPDATE_FIELD,"",request);
				String sNotUpdateField=getValueFromRequest(Constants.PARAMETER_NOT_UPDATE_FIELD,"",request);
				
				
				
				int length=list.size();
				System.out.println("Ҫ�޸��ֶθ���:"+length);
				String sTmpValue="";
				String sFieldName="";
				String sFields="";
				String sValues="";
				String sUpdatesql="";
				String sDataType="";
				if (list != null &&  length> 0) {
					for (int i=0; i < length; i++) {
						baseobj = (BaseObject) list.get(i);
						sFieldName=BaseObject.toString(baseobj, "FVC_FIELDNAME");
						if (((sUpdateField.equals(""))||(sUpdateField.indexOf(sFieldName)>=0))&&((sNotUpdateField.indexOf(sFieldName)<0)||(sNotUpdateField.equals(""))))
						{
							if ((request.getParameter(sFieldName)==null)&&(BaseObject.toString(baseobj, "FVC_TYPE").equals("CHECKBOX")==true)){
								if (sFieldList.indexOf(sFieldName)<0)
								{
							    	
									showWebError("�ֶ�["+sFieldName+"]�ڱ�["+sMainDataSource+"]�в�����!",getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
									request.setAttribute(Constants.PARAMETER_GETDATAWHERE,sWhere);
						            throw new EJBException("�ֶ�["+sFieldName+"]�ڱ�["+sMainDataSource+"]�в�����!");
								}
								sTmpValue="0";
								sFields=sFields+","+sFieldName;
								sValues=sValues+",'"+sTmpValue+"'";
								sUpdatesql=sUpdatesql+","+sFieldName+"='"+sTmpValue+"'";
							}
							if ((request.getParameter(sFieldName)!=null)&&(sFieldName.equals(sKeyField)==false)&&(sFieldName.equals(Constants.PARAMETER_APPFIELD_APPKEYNAME)==false)){
								if (sFieldList.indexOf(sFieldName)<0)
								{
							    	
									showWebError("�ֶ�["+sFieldName+"]�ڱ�["+sMainDataSource+"]�в�����!",getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
									request.setAttribute(Constants.PARAMETER_GETDATAWHERE,sWhere);
						            throw new EJBException("�ֶ�["+sFieldName+"]�ڱ�["+sMainDataSource+"]�в�����!");
								}
								sTmpValue=request.getParameter(sFieldName).toString();
								sTmpValue=sTmpValue.replaceAll("'","''");
								sDataType=BaseObject.toString(baseobj, "FVC_DATATYPE");
								//BasePrint.println("sDataType:"+sDataType);
								if ((sDataType.trim().equalsIgnoreCase("DATE")==true)||(sDataType.trim().equalsIgnoreCase("TIME")==true))
								{
									sFields=sFields+","+sFieldName;
									sValues=sValues+","+getStringtoDate(sTmpValue);
									sUpdatesql=sUpdatesql+","+sFieldName+"="+getStringtoDate(sTmpValue);
								}
								else
								if ((sDataType.equals("INT")==true)||(sDataType.equals("NUMBER")==true))
								{
									if (sTmpValue.equals("")) sTmpValue="null";
									sFields=sFields+","+sFieldName;
									sValues=sValues+","+sTmpValue;
									sUpdatesql=sUpdatesql+","+sFieldName+"="+sTmpValue;
								}
								else
								{
									sFields=sFields+","+sFieldName;
									sValues=sValues+",'"+sTmpValue+"'";
									sUpdatesql=sUpdatesql+","+sFieldName+"='"+sTmpValue+"'";
								}
							}
						}
					}
				}
				
				if ((sFields.length()>0)&&(sFields.indexOf(",")==0)) sFields=sFields.substring(1);
				if ((sValues.length()>0)&&(sValues.indexOf(",")==0)) sValues=sValues.substring(1);
				if ((sUpdatesql.length()>0)&&(sUpdatesql.indexOf(",")==0)) sUpdatesql=sUpdatesql.substring(1);
				
				
				
				/******���******/
				if (sWebAct.equals(Constants.PARAMETER_WEBACT_ADD)){
					
					/******ִ�����ǰ�Զ������******/
					if (!exeCustomFunction(Constants.FUNCTION_TYPE_BEFORADD,request)) 			
					{
				    	
						showWebError("ִ�����ǰ�Զ�����̳���!",getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
			            throw new EJBException("ִ�����ǰ�Զ�����̳���!");
					}

					/**********************************/
					
					String sTmpID=String.valueOf(this.getSequenceValue(sAppSeq));
					
					sFields=sFields+","+sKeyField;
					sValues=sValues+",'"+sTmpID+"'";
					
					if (sHRe.equals("1")){sValues = replHtmlWihtPara(sValues,request);}
					
					if ((sFields.equals("")==false)&&(sValues.equals("")==false))
					{
					try
					{
						this.executeSQL("insert into "+sMainDataSource+"("+sFields+") values("+sValues+")");
						sWhere=sKeyField+"="+sTmpID;
					} catch (Exception e) {
				    	
						showWebError(Constants.ERROR_CANNOTDADD,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
						request.setAttribute(Constants.PARAMETER_GETDATAWHERE,sWhere);
			            throw new EJBException(Constants.ERROR_CANNOTDADD);
					}
					}
					
					/******ִ����Ӻ��Զ������******/
					if (!exeCustomFunction(Constants.FUNCTION_TYPE_AFTERADD,request)) 					
					{
				    	
						showWebError("ִ����Ӻ��Զ�����̳���!",getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
			            throw new EJBException("ִ����Ӻ��Զ�����̳���!");
					}

					/**********************************/
					
				}
				/*****************/
				
			
				
				/******�޸�******/
				if (sWebAct.equals(Constants.PARAMETER_WEBACT_EDIT))
				{
					/******ִ���޸�ǰ�Զ������******/
					if (!exeCustomFunction(Constants.FUNCTION_TYPE_BEFOREDIT,request)) 					
					{
				    	
						showWebError("ִ���޸�ǰ�Զ�����̳���!",getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
			            throw new EJBException("ִ���޸�ǰ�Զ�����̳���!");
					}

					/**********************************/
					
					System.out.println(sKeyField+":"+(request.getParameter(sKeyField)!=null));

					if ((request.getParameter(sKeyField)!=null)&&(sFieldList.indexOf(sKeyField)>0)){
						String sKeyValue=request.getParameter(sKeyField).toString();
						if (sKeyValue.equals("")==false)
						{
							sUpdatesql=sUpdatesql+","+sKeyField+"='"+sKeyValue+"'";
						}
					}
					
					System.out.println("ִ��:"+sWebAct);
					System.out.println("sHRe:"+sHRe);
					
					if (sHRe.equals("1")){sUpdatesql = replHtmlWihtPara(sUpdatesql,request);}
	
					
					if ((sUpdatesql.equals("")==false)&&(length>0)) 
					{
					try
					{
						this.executeSQL(" update "+sMainDataSource+" set "+sUpdatesql+" where "+sWhere);
					} catch (Exception e) {
				    	
						showWebError(Constants.ERROR_CANNOTDADD,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
						request.setAttribute(Constants.PARAMETER_GETDATAWHERE,sWhere);
			            throw new EJBException(Constants.ERROR_CANNOTDADD);
					}
					}
					
					/******ִ���޸ĺ��Զ������******/
					if (!exeCustomFunction(Constants.FUNCTION_TYPE_AFTEREDIT,request)) 					
					{
				    	
						showWebError("ִ���޸ĺ��Զ�����̳���!",getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
			            throw new EJBException("ִ���޸ĺ��Զ�����̳���!");
					}


					/**********************************/
					
					
				}
				/*****************/
				
			}
			
			/*****************/
			
			/******ȡ����******/
			BaseObject databaseobj = getAppDataObj(request,sWhere);
			/******************/
			
			
			/******ִ�б������ݺ��Զ������******/
			if (!exeCustomFunction(Constants.FUNCTION_TYPE_AFTERSAVE,request)) 
			{
		    	
				showWebError("ִ�б������ݺ��Զ�����̳���!",getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
	            throw new EJBException("ִ�б������ݺ��Զ�����̳���!");
			}

			/**********************************/
			
			/*
			String strMsg=getValueFromRequest(Constants.PARAMETER_RETURN_MSG,"",request);
			if (strMsg.equals("")==false)
			{
				strMsg=replHtmlWihtPara(strMsg,request);
			}
			request.setAttribute("RETURN_MSG",strMsg);
			*/
			

			request.setAttribute(Constants.PARAMETER_GETDATAWHERE,sWhere);
			
			if (sWebAct.equals(Constants.PARAMETER_WEBACT_ADD)){
				request.setAttribute(Constants.PARAMETER_WEBACT,Constants.PARAMETER_WEBACT_EDIT);
			}
			
			//BasePrint.println("sReturnType:"+sReturnType);
			//BasePrint.println((sReturnType==Constants.PARAMETER_POSTTYPE_SAVEANDRETURN));
			if(sReturnType==Constants.PARAMETER_POSTTYPE_SAVEANDRETURN)
			{
				if (request.getParameter("RETURN_URL")!=null) 
				{
					if (request.getParameter("RETURN_URL").toString().equals("")==false)
					{
						/******�趨���ز����б�ĸ������ֵ******/
						ArrayList core_returnlist= iniReturnParameter(request);
						core_returnlist.set(Constants.CORE_PARAMETER_RETURNURL,replHtmlWihtPara(request.getParameter("RETURN_URL").toString(),request));
						core_returnlist.set(Constants.CORE_PARAMETER_DATAOBJECT,request.getAttribute(Constants.PARAMETER_ATTRIBUTEDATA));
						request.setAttribute(Constants.CORE_PARAMETER_NAME,core_returnlist);
						/*************************************/
						return true;
					}
				}
				
				return showFromCore(request);
			};
			return true;
			
	    }
	    catch (Exception e) {
			request.setAttribute(Constants.PARAMETER_GETDATAWHERE,sWhere);
			e.printStackTrace();
			showWebError(e.toString(),getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
            throw new EJBException(e.toString());
	    }
			
	}
	
	/**
	 * @param htmlcode
	 * @param sHRe
	 * @param sJre
	 * @throws Exception
	 */
	public boolean showWebByHtml(String htmlcode,String sJs,HttpServletRequest request) throws RemoteException {
		try {
			
			//BasePrint.println("Begin showWebByHtml");
			String sHRe=getValueFromRequest(Constants.PARAMETER_IFREPLACEHTML,"1",request);
			String  sJRe=getValueFromRequest(Constants.PARAMETER_IFREPLACEJS,"1",request);
			//BasePrint.println("1");
			if ((sHRe.equals("1"))&&(sJRe.equals("1")))
			{
				htmlcode=htmlcode+this.getWebAddBr("\n",3)+sJs;
				htmlcode=replHtmlWihtPara(htmlcode,request);
				sJs="";
				sJRe="0";
				sHRe="0";
			}
			if (sHRe.equals("1")) htmlcode=replHtmlWihtPara(htmlcode,request);
			if (sJRe.equals("1")) sJs=replHtmlWihtPara(sJs,request);
			
			htmlcode=htmlcode+"\n\n\n"+sJs;
			
			
			request.setAttribute(Constants.WEB_HTML_STRING, htmlcode);
			
			
			/******��ҵ�����Ҫ����******/
			String web_page_fix=getAppMainParameter("FVC_WEBFIX",request);
			String web_height=getAppMainParameter("FVC_WEBHEIGHT",request);
			String web_width=getAppMainParameter("FVC_WEBWIDTH",request);
			
			request.setAttribute(Constants.WEB_PAGE_FIX,web_page_fix);
			request.setAttribute(Constants.WEB_PAGE_HEIGHT,web_height);
			request.setAttribute(Constants.WEB_PAGE_WIDTH,web_width);
			/**************************/

			
			/******ȡҵ�����Ҫ����******/
			String sWebModel=getAppMainParameter("FVC_WEBMODELRUL",request);
			if (sWebModel.equals("")){
				sWebModel=Constants.PARAMETER_DEFAULT_WEBMODEL;
			}
			/**************************/
			
			
			
			/******�趨���ز����б�ĸ������ֵ******/
			ArrayList core_returnlist= iniReturnParameter(request);
			//BasePrint.println("7");
			
			core_returnlist.set(Constants.CORE_PARAMETER_RETURNURL,sWebModel);
			core_returnlist.set(Constants.CORE_PARAMETER_DATAOBJECT,request.getAttribute(Constants.PARAMETER_ATTRIBUTEDATA));
			request.setAttribute(Constants.CORE_PARAMETER_NAME,core_returnlist);
			/*************************************/
			
			//BasePrint.println("End showWebByHtml");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			showWebError(e.toString(),"javascript:history.back();",request);
			return false;
		}
	}	
	
	public boolean exeCustomFunction(String sType,HttpServletRequest request) throws RemoteException {
		String result=this.exeFunctionByType(sType,request);
		BasePrint.println("result:"+result);
		if (result.equals("")==false)
		{
			showWebError(result,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
			return false;
		}
		return true;
	}

}
