package promos.main.ejb;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.servlet.http.*;

import java.rmi.RemoteException;

import promos.base.objects.*;
import promos.core.ejb.CoreBean;


/**
 *
 * <p>
 * 标题: Bean CoreBean
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

public class MainBean extends CoreBean implements Main {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	SessionContext sessionContext;

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
	
	public boolean saveToCore(int sReturnType,HttpServletRequest request) throws RemoteException {
		/******系统权限判断******/
		String s_is_set_compurview=getValueFromAttribute(Constants.PARAMETER_IS_SET_COMPURVIEW,"0",request);
		String s_ifpurview=this.getAppMainParameter("FNB_IFPURVIEW", request);
		if ((s_ifpurview.equals(""))||s_ifpurview==null) s_ifpurview="0";
		if ((s_ifpurview.equals("1"))&&(s_is_set_compurview.equals("1")==false))
		{
			if (setComProperty(request)==false) 
			{
				showWebError(Constants.ERROR_NOPURVIEW,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
				return false;
			}
		}
		/******************/

		boolean re=super.saveToCore(Constants.PARAMETER_POSTTYPE_JUSTSAVE,request);

		if (!re) return re;
		if (sReturnType==Constants.PARAMETER_POSTTYPE_SAVEANDRETURN)
		{
		  return super.showFromCore(request);
		}
		 return re;
	}
	public boolean showFromCore(HttpServletRequest request) throws RemoteException {
		
		/******系统权限判断******/
		String s_is_set_compurview=getValueFromAttribute(Constants.PARAMETER_IS_SET_COMPURVIEW,"0",request);
		String s_ifpurview=this.getAppMainParameter("FNB_IFPURVIEW", request);
		if ((s_ifpurview.equals(""))||s_ifpurview==null) s_ifpurview="0";
		if ((s_ifpurview.equals("1"))&&(s_is_set_compurview.equals("1")==false))
		{
			if (setComProperty(request)==false) 
			{
				showWebError(Constants.ERROR_NOPURVIEW,getValueFromRequest("ON_ERROR_URL","javascript:history.back()",request),request);
				return false;
			}
		}
		/******************/
		
		
		return super.showFromCore(request);
	}
	
	public boolean setComProperty(HttpServletRequest request) throws RemoteException{
		ArrayList prolist=null;
		
		String com_ReadonlyCom=getValueFromRequest(Constants.COM_PROPERTY_READONLY,"",request);
		String com_NotEnabledCom=getValueFromRequest(Constants.COM_PROPERTY_NOTENABLED,"",request);
		String com_NotListCom=getValueFromRequest(Constants.COM_PROPERTY_NOTLIST,"",request);
		String com_NotNullCom=getValueFromRequest(Constants.COM_PROPERTY_NOTNULL,"",request);
		String com_NotDisplayCom=getValueFromRequest(Constants.COM_PROPERTY_NOTDISPLAY,"",request);
		String com_other=getValueFromRequest(Constants.COM_PROPERTY_OTHER,"",request);
		

		String sys_view=getValueFromAttribute(Constants.SYS_PURVIEW_VIEW,"0",request);
		String sys_edit=getValueFromAttribute(Constants.SYS_PURVIEW_EDIT,"0",request);
		String sys_add=getValueFromAttribute(Constants.SYS_PURVIEW_ADD,"0",request);
		String sys_dele=getValueFromAttribute(Constants.SYS_PURVIEW_DELE,"0",request);

		String sys_design_admin=getValueFromAttribute(Constants.SYS_DESIGN_ADMIN,"0",request);
		String sys_oper_admin=getValueFromAttribute(Constants.SYS_OPER_ADMIN,"0",request);
		String sys_self_design=getValueFromAttribute(Constants.SYS_SELF_DESIGN,"0",request);
		
		System.out.println("sys_dele:"+sys_dele);
		
		String sPropertyValue="";
		String scomoract=Constants.SYS_PURVIEW_COMORACT_COM;
		boolean isCan=false;
		try
		{
			HttpSession session = request.getSession();
			User user= (User) session.getAttribute(User.SESSION_USER);
			
			System.out.println("user is null:"+(user==null));
			
			
			if (user==null) return false;
			
			prolist = getPurViewListByCondition(request,Constants.COMPURVIEW_WHERE_MAINBASE,user,"","");
			int length=prolist.size();
			
			System.out.println("prolist:"+prolist.size());
			
			BaseObject baseobj = null;
			if (prolist != null &&  length> 0) {
				for (int i = 0; i < length; i++) {
					baseobj = (BaseObject) prolist.get(i);
					
					scomoract=BaseObject.toString(baseobj, "FNB_COMORACT");
					if (scomoract.equals(Constants.SYS_PURVIEW_COMORACT_COM))
					{
						sPropertyValue=BaseObject.toString(baseobj, "FVC_OTHERPROPERTY");
						if (sPropertyValue.equals("")==false)
						{
							if (getComName(baseobj).equals("")==false)
							{
								com_other="{if (document.all."+getComName(baseobj)+"){document.all."+getComName(baseobj)+"."+sPropertyValue+"}};"+com_other;
							}
							else
							{
								com_other="{"+sPropertyValue+"};"+com_other;
							}
						}
						
						sPropertyValue=BaseObject.toString(baseobj, "FVC_DISPLAY");
						if (sPropertyValue.equals("1"))
						{
							com_NotDisplayCom=com_NotDisplayCom+",'"+getComName(baseobj)+"'";
						}
						sPropertyValue=BaseObject.toString(baseobj, "FVC_ENABLED");
						if (sPropertyValue.equals("1"))
						{
							com_NotEnabledCom=com_NotEnabledCom+",'"+getComName(baseobj)+"'";
							
						}
						sPropertyValue=BaseObject.toString(baseobj, "FVC_IFLIST");
						if (sPropertyValue.equals("1"))
						{
							com_NotListCom=com_NotListCom+",'"+getComName(baseobj)+"'";
							
						}
						sPropertyValue=BaseObject.toString(baseobj, "FVC_IFNULL");
						if (sPropertyValue.equals("1"))
						{
							com_NotNullCom=com_NotNullCom+",'"+getComName(baseobj)+"'";
							
						}
						sPropertyValue=BaseObject.toString(baseobj, "FVC_READONLY");
						if (sPropertyValue.equals("1"))
						{
							com_ReadonlyCom=com_ReadonlyCom+",'"+getComName(baseobj)+"'";
						}
					}
					
					//System.out.println("scomoract:"+scomoract);
					if (scomoract.equals(Constants.SYS_PURVIEW_COMORACT_ACT))
					{
						if (sys_view.equals("0")) sys_view=BaseObject.toString(baseobj, "FNB_IFVIEW");
						if (sys_edit.equals("0")) sys_edit=BaseObject.toString(baseobj, "FNB_IFEDIT");
						if (sys_add.equals("0")) sys_add=BaseObject.toString(baseobj, "FNB_IFADD");
						if (sys_dele.equals("0")) sys_dele=BaseObject.toString(baseobj, "FNB_IFDEL");
						if (sys_design_admin.equals("0")) sys_design_admin=BaseObject.toString(baseobj, "FNB_DESIADMIN");
						if (sys_oper_admin.equals("0")) sys_oper_admin=BaseObject.toString(baseobj, "FNB_OPERADMIN");
						if (sys_self_design.equals("0")) sys_self_design=BaseObject.toString(baseobj, "FNB_SELFDESIGN");
					}
					
				}
			}
			
			if (com_ReadonlyCom.indexOf(",")==0) com_ReadonlyCom=com_ReadonlyCom.substring(1);
			if (com_NotEnabledCom.indexOf(",")==0) com_NotEnabledCom=com_NotEnabledCom.substring(1);
			if (com_NotListCom.indexOf(",")==0) com_NotListCom=com_NotListCom.substring(1);
			if (com_NotNullCom.indexOf(",")==0) com_NotNullCom=com_NotNullCom.substring(1);
			if (com_NotDisplayCom.indexOf(",")==0) com_NotDisplayCom=com_NotDisplayCom.substring(1);
			if (com_other.length()>0) com_other=com_other.substring(0,com_other.length()-1);
			
			request.setAttribute(Constants.COM_PROPERTY_OTHER,com_other);
			request.setAttribute(Constants.COM_PROPERTY_READONLY,com_ReadonlyCom);
			request.setAttribute(Constants.COM_PROPERTY_NOTENABLED,com_NotEnabledCom);
			request.setAttribute(Constants.COM_PROPERTY_NOTLIST,com_NotListCom);
			request.setAttribute(Constants.COM_PROPERTY_NOTNULL,com_NotNullCom);
			request.setAttribute(Constants.COM_PROPERTY_NOTDISPLAY,com_NotDisplayCom);
			
			request.setAttribute(Constants.SYS_PURVIEW_VIEW,sys_view);
			request.setAttribute(Constants.SYS_PURVIEW_EDIT,sys_edit);
			request.setAttribute(Constants.SYS_PURVIEW_ADD,sys_add);
			request.setAttribute(Constants.SYS_PURVIEW_DELE,sys_dele);
			request.setAttribute(Constants.SYS_DESIGN_ADMIN,sys_design_admin);
			request.setAttribute(Constants.SYS_OPER_ADMIN,sys_oper_admin);
			request.setAttribute(Constants.SYS_SELF_DESIGN,sys_self_design);
			
			String sNotCheckCom="";
			if (com_ReadonlyCom.equals("")==false) sNotCheckCom=com_ReadonlyCom;
			if (com_NotDisplayCom.equals("")==false) sNotCheckCom=sNotCheckCom+","+com_NotDisplayCom;
			if (com_NotEnabledCom.equals("")==false) sNotCheckCom=sNotCheckCom+","+com_NotEnabledCom;
			if ((sNotCheckCom.length()>0)&&(sNotCheckCom.substring(0,1).equals(",")==true)) sNotCheckCom=sNotCheckCom.substring(1);
			request.setAttribute(Constants.COM_PROPERTY_NOTCHECK,sNotCheckCom);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		if (sys_view.equals("1")||sys_edit.equals("1")||sys_add.equals("1")||sys_dele.equals("1")||sys_oper_admin.equals("1")||sys_self_design.equals("1")||sys_design_admin.equals("1"))
		{
			isCan=true;
		}
		
		return isCan;
	}	
		

}
