package promos.custom.ejb;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.servlet.http.*;

import java.rmi.RemoteException;
import java.sql.Date;

import promos.base.ejb.Base;
import promos.base.ejb.BaseRemote;
import promos.base.ejb.DBBaseBean;
import promos.base.objects.*;
import promos.core.ejb.Core;
import promos.core.ejb.CoreBean;
import promos.core.ejb.CoreRemote;


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

public class CustomReBean extends DBBaseBean implements CustomRe {
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
	
	public String reCustomPara(String sHtml,HttpServletRequest request) throws RemoteException {
		if (sHtml.equals("")) return sHtml;
		if (sHtml.indexOf("<%=")<0)  return sHtml;
		
		
		/************ʹ�õ�¼����Ϣ�滻**********************/
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(User.SESSION_USER);
		
		String sTmp="";
		if (user!=null)
		{
			sTmp=user.getUserID();
			if((sTmp!=null)&&(sTmp.equals("")==false)) sHtml=sHtml.replaceAll("<%=USER_ID%>",String.valueOf(sTmp));

			sTmp=user.getUserCode();
			if((sTmp!=null)&&(sTmp.equals("")==false)) sHtml=sHtml.replaceAll("<%=USER_CODE%>",sTmp.toString());             

			sTmp=user.getUserName();
			if((sTmp!=null)&&(sTmp.equals("")==false)) sHtml=sHtml.replaceAll("<%=USER_NAME%>",sTmp.toString());             

			sTmp=user.getUserAddress();
			if((sTmp!=null)&&(sTmp.equals("")==false)) sHtml=sHtml.replaceAll("<%=USER_ADDRESS%>",sTmp.toString());             
			
			sTmp=user.getUserPhone();
			if((sTmp!=null)&&(sTmp.equals("")==false)) sHtml=sHtml.replaceAll("<%=USER_PHONE%>",sTmp.toString());             

			sTmp=user.getUserMobil();
			if((sTmp!=null)&&(sTmp.equals("")==false)) sHtml=sHtml.replaceAll("<%=USER_MOBIL%>",sTmp.toString());             

			sTmp=user.getUserDeptId();
			if((sTmp!=null)&&(sTmp.equals("")==false)) sHtml=sHtml.replaceAll("<%=USER_DEPTID%>",sTmp.toString());             

			sTmp=user.getUserDeptName();
			if((sTmp!=null)&&(sTmp.equals("")==false)) sHtml=sHtml.replaceAll("<%=USER_DEPTNAME%>",sTmp.toString());             

			sTmp=String.valueOf(user.getUserStatus());
			if((sTmp!=null)&&(sTmp.equals("")==false)) sHtml=sHtml.replaceAll("<%=USER_STATUS%>",sTmp.toString());             

			sTmp=user.getUserCardId();
			if((sTmp!=null)&&(sTmp.equals("")==false)) sHtml=sHtml.replaceAll("<%=USER_CARDID%>",sTmp.toString());             

			sTmp=user.getUserIdenCard();
			if((sTmp!=null)&&(sTmp.equals("")==false)) sHtml=sHtml.replaceAll("<%=USER_IDENCARD%>",sTmp.toString());             

			sTmp=user.getWebsite();
			if((sTmp!=null)&&(sTmp.equals("")==false)) sHtml=sHtml.replaceAll("<%=USER_WEBSITE%>",sTmp.toString());             

			sTmp=user.getRoleidlist();
			if((sTmp!=null)&&(sTmp.equals("")==false)) sHtml=sHtml.replaceAll("<%=USER_ROLEIDLIST%>",sTmp.toString());             

			sTmp=user.getDesignRole();
			if((sTmp!=null)&&(sTmp.equals("")==false)) sHtml=sHtml.replaceAll("<%=DESIGN_ROLE_ID%>",sTmp.toString());             
			
		}
		
		/************ʹ�õ�¼����Ϣ�滻����**********************/
		
		if (sHtml.indexOf("<%=")<0)  return sHtml;

		sHtml=sHtml.replaceAll("<%=Date%>",String.valueOf(System.currentTimeMillis()));
		
		Base base = BaseRemote.getBase();
		sHtml=sHtml.replaceAll("<%=AppName%>",base.getValueFromRequest("AppName","-1",request));
		sHtml=sHtml.replaceAll("<%=AppKeyField%>",base.getDataSourceParameter("FVC_FIELDNAME",request));
		sHtml=sHtml.replaceAll("<%=GetDataWhere%>",base.getValueFromRequest(Constants.PARAMETER_GETDATAWHERE,base.getValueFromAttribute(Constants.PARAMETER_GETDATAWHERE,"1<>1",request),request));
		sHtml=sHtml.replaceAll("<%=ListDataWhere%>",base.getValueFromRequest(Constants.PARAMETER_LISTDATAWHERE,base.getValueFromRequest(Constants.PARAMETER_GETDATAWHERE,"",request),request));
		sHtml=sHtml.replaceAll("<%="+Constants.PARAMETER_RETURNDATAWHERE+"%>",base.getValueFromRequest(Constants.PARAMETER_RETURNDATAWHERE,"1<>1",request));
		sHtml=sHtml.replaceAll("<%=EditAppID%>",base.getAppMainParameter("FVC_EDITAPPNAME",request));
		sHtml=sHtml.replaceAll("<%=ListAppID%>",base.getAppMainParameter("FVC_LISTAPPNAME",request));
		
		return sHtml;
	}
}
