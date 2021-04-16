/**
 * 
 */
package promos.design.servlet;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import promos.base.ejb.Base;
import promos.base.ejb.BaseRemote;
import promos.base.ejb.DBBase;
import promos.base.ejb.DBBaseRemote;
import promos.base.objects.BaseObject;
import promos.base.objects.BasePrint;
import promos.base.objects.BaseConfig;
import promos.base.objects.Constants;
import promos.base.objects.User;




/**
 *
 * <p>
 * 标题: DesignServlet
 * </p>
 * <p>
 * 描述: 显示设计主界面Servlet
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


public class DesignMainServlet extends HttpServlet {
	
	private final static String DB_USER = BaseConfig.getSettingByName("db_user"); /*系统数据库用户*/
	
	public void init() throws ServletException {
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		response.setContentType("text/html; charset=GBK");
		
		request.setCharacterEncoding("GBK");
		
		if (BaseConfig.getInstance()==null)
		{
			String strPath = request.getRequestURL().toString();
			strPath = strPath.substring(0,strPath.indexOf(request.getContextPath()));

			URL url = new URL(strPath+"/promos/promos.xml");
			BaseConfig procs = BaseConfig.getInstance(url);
		    BasePrint baseprint = BasePrint.getInstance();
		}
		
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute(User.SESSION_USER);
		if (user==null) 
		{
			request.setAttribute(Constants.WEB_HTML_STRING,"<script>alert('请登录系统!');location='/promos/core/index.jsp';</script>");
			javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		String rolelist=user.getRoleidlist();
		String design=","+user.getDesignRole()+",";
		String owner=","+user.getOwnerRole()+",";
		
		if (((rolelist.indexOf(design)>0)&&(design.equals("")==false))&&(((rolelist.indexOf(owner)>0)&&(owner.equals("")==false))))
		{
			request.setAttribute(Constants.WEB_HTML_STRING,"<script>alert('"+Constants.ERROR_NOPURVIEW+"');location='/promos/core/index.jsp';</script>");
			javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		request.setAttribute("COMPONENTCONT", getDesignCom(request, response));
		
		javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/main.jsp");
		dispatcher.forward(request, response);
		
	}
	
	public String getDesignCom(HttpServletRequest request, HttpServletResponse response)	throws RemoteException {
		
		String sReturnHtml="";
		
		DBBase dbc = DBBaseRemote.getDBBase();
		
		ArrayList grouplist =dbc.selectSQL(DB_USER+"."+Constants.PARAMETER_COMPONENT_TABLENAME,"select distinct FVC_GROUP as FVC_GROUP,FNB_GPORDER from "+DB_USER+"."+Constants.PARAMETER_COMPONENT_TABLENAME+" where FVC_GROUP is not null and FVC_ISDESIGN='1' order by FNB_GPORDER");
		
		
		Base Obase = BaseRemote.getBase();
		
		int grouplength=grouplist.size();
		
		BaseObject groupbaseobj = null;
		String grouphtml="";
		if (grouplist != null &&  grouplength> 0) {
			for (int j=0; j < grouplength; j++) {
				groupbaseobj = (BaseObject) grouplist.get(j);
				grouphtml="  <div id=\"panel"+j+"\" align='center'><div align='center'><div align='center'>"+BaseObject.toString(groupbaseobj, "FVC_GROUP")+"</div></div><div><div class=\"text-content\">";
				
				
				String std="";
				String sHtml="";
				
				ArrayList sublist = dbc.selectSQL(DB_USER+"."+Constants.PARAMETER_COMPONENT_TABLENAME,"select * from "+DB_USER+"."+Constants.PARAMETER_COMPONENT_TABLENAME+" where FVC_ISDESIGN='1' and FVC_GROUP='"+BaseObject.toString(groupbaseobj, "FVC_GROUP")+"' order by FVC_COMTYPE,FNB_ORDER");
				int sublength=sublist.size();
				BaseObject subbaseobj = null;
				
				if (sublist != null &&  sublength> 0) {
					//sHtml=sHtml+Obase.getHtmlCode("TR","","","",Obase.getHtmlCode("TD","",""," colspan=5 height=\"1\" bgcolor=\"E1E1E1\"","<div align=\"center\"></div>", request), request);
					for (int ii=0; ii < sublength; ii++) {
						subbaseobj = (BaseObject) sublist.get(ii);
						std=std+Obase.getHtmlCode("TD","","","","<IMG align='absmiddle' title=\""+BaseObject.toString(subbaseobj, "FVC_CNAME")+"\" style='CURSOR:hand' onclick=\"jh_adddesigncom('"+BaseObject.toString(subbaseobj, "FVC_TYPE")+"');\" src='"+BaseObject.toString(subbaseobj, "FVC_COMICO")+"' >", request);
						ii++;
						if (ii >= sublength)
						{
							sHtml=sHtml+Obase.getHtmlCode("TR","","","",std, request);
							std="";
							break;
						}
						subbaseobj = (BaseObject) sublist.get(ii);
						std=std+Obase.getHtmlCode("TD","","","","<IMG  align='absmiddle' title=\""+BaseObject.toString(subbaseobj, "FVC_CNAME")+"\" style='CURSOR:hand' onclick=\"jh_adddesigncom('"+BaseObject.toString(subbaseobj, "FVC_TYPE")+"');\" src='"+BaseObject.toString(subbaseobj, "FVC_COMICO")+"' >", request);
						
						ii++;
						if (ii >= sublength)
						{
							sHtml=sHtml+Obase.getHtmlCode("TR","","","",std, request);
							std="";
							break;
						}
						subbaseobj = (BaseObject) sublist.get(ii);
						std=std+Obase.getHtmlCode("TD","","","","<IMG  align='absmiddle' title=\""+BaseObject.toString(subbaseobj, "FVC_CNAME")+"\" style='CURSOR:hand' onclick=\"jh_adddesigncom('"+BaseObject.toString(subbaseobj, "FVC_TYPE")+"');\" src='"+BaseObject.toString(subbaseobj, "FVC_COMICO")+"' >", request);
						
						ii++;
						if (ii >= sublength)
						{
							sHtml=sHtml+Obase.getHtmlCode("TR","","","",std, request);
							std="";
							break;
						}
						subbaseobj = (BaseObject) sublist.get(ii);
						std=std+Obase.getHtmlCode("TD","","","","<IMG  align='absmiddle' title=\""+BaseObject.toString(subbaseobj, "FVC_CNAME")+"\" style='CURSOR:hand' onclick=\"jh_adddesigncom('"+BaseObject.toString(subbaseobj, "FVC_TYPE")+"');\" src='"+BaseObject.toString(subbaseobj, "FVC_COMICO")+"' >", request);
						
						ii++;
						if (ii >= sublength)
						{
							sHtml=sHtml+Obase.getHtmlCode("TR","","","",std, request);
							std="";
							break;
						}
						subbaseobj = (BaseObject) sublist.get(ii);
						std=std+Obase.getHtmlCode("TD","","","","<IMG  align='absmiddle' title=\""+BaseObject.toString(subbaseobj, "FVC_CNAME")+"\" style='CURSOR:hand' onclick=\"jh_adddesigncom('"+BaseObject.toString(subbaseobj, "FVC_TYPE")+"');\" src='"+BaseObject.toString(subbaseobj, "FVC_COMICO")+"' >", request);
						
						sHtml=sHtml+Obase.getHtmlCode("TR","","","",std, request);
						sHtml=sHtml+Obase.getHtmlCode("TR","","","",Obase.getHtmlCode("TD","",""," colspan=5 height=\"1\" bgcolor=\"E1E1E1\"","<div align=\"center\"></div>", request), request);
						std="";
					}
				}
				sHtml=sHtml+Obase.getHtmlCode("TR","","","",Obase.getHtmlCode("TD","",""," colspan=5 height=\"1\" bgcolor=\"E1E1E1\"","<div align=\"center\"></div>", request), request);
				
				
				sHtml=Obase.getHtmlCode("TABLE","",""," border=\"0\" width=\"100%\" cellspacing=\"4\" cellpadding=\"0\" bgcolor=\"#FFFFFF\"",sHtml, request);
				
				sReturnHtml=sReturnHtml+"\n\n"+grouphtml+sHtml+"  </div></div></div>\n";
				
			}
		}
		
		grouphtml="<script type=\"text/javascript\">\n";
		grouphtml=grouphtml+"Ext.BLANK_IMAGE_URL = 'ext/resources/images/default/s.gif';\n";
		grouphtml=grouphtml+"Ext.onReady(function() {\n";
		grouphtml=grouphtml+"  var acc = new Ext.ux.Accordion('com-ct', {fitHeight: true});\n";
		
		if (grouplist != null &&  grouplength> 0) {
			for (int j=0; j < grouplength; j++) {
				groupbaseobj = (BaseObject) grouplist.get(j);
				grouphtml=grouphtml+"  var panel"+j+" = acc.add(new Ext.ux.InfoPanel('panel"+j+"', {}));\n";
			}
		}
		grouphtml=grouphtml+"});\n</script>\n";
		
		
		sReturnHtml="<div id=\"east-div\" style=\"visibility: hidden;\">\n<div id=\"com-ct\" style=\"width:100%;height:480px;border:3px double #fafafa\">"+sReturnHtml+"</div>\n</div>\n\n"+grouphtml+"\n";		
		
		return sReturnHtml;				
	}		
	
	
}
