package promos.design.servlet;
/**
 * author 胡维新 DATE 2004-1-15
 */
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import promos.base.ejb.Base;
import promos.base.ejb.BaseRemote;
import promos.base.ejb.DBBase;
import promos.base.ejb.DBBaseRemote;
import promos.base.objects.BaseConfig;
import promos.base.objects.BaseObject;
import promos.base.objects.BasePrint;
import promos.base.objects.Constants;
import promos.base.objects.User;
import promos.design.ejb.Design;
import promos.design.ejb.DesignRemote;


public class DesignServlet extends HttpServlet {
	
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
		String strMethod=request.getParameter("method");

		if (BaseConfig.getInstance()==null)
		{
			String strPath = request.getRequestURL().toString();
			strPath = strPath.substring(0,strPath.indexOf(request.getContextPath()));

			URL url = new URL(strPath+"/promos/promos.xml");
			BaseConfig procs = BaseConfig.getInstance(url);
		    BasePrint baseprint = BasePrint.getInstance();
		}
		
		if (strMethod==null) strMethod="";
		
		if (strMethod.equals("showDesignWeb"))
		{
			showDesignWeb(request,response);
			return;
		}
		
		if (strMethod.equals("updateComponentPos"))
		{
			updateComponentPos(request,response);
			return;
		}
		if (strMethod.equals("designFormCopy"))
		{
			designFormCopy(request,response);
			return;
		}
		if (strMethod.equals("showComProperty"))
		{
			showComProperty(request,response);
			return;
		}
		if (strMethod.equals("deleteapp"))
		{
			deleteapp(request,response);
			return;
		}
		if (strMethod.equals("saveComProperty"))
		{
			saveComProperty(request,response);
			return;
		}
				
		if (strMethod.equals("runtestsql"))
		{
			runtestsql(request,response);
			return;
		}
		
		Base Base = BaseRemote.getBase();
		Base.showWebError("请输入方法名！","javascript:history.back()", request);
		
		javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(Constants.PARAMETER_DEFAULT_WEBMODEL);
		dispatcher.forward(request, response);
		
	}	
		
	public void runtestsql(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Design design = DesignRemote.getDesign();
		design.runtestsql();
	}	
	public void saveComProperty(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
		Design design = DesignRemote.getDesign();
		design.saveComProperty(request);
		request.setAttribute(Constants.WEB_HTML_STRING, "<script>alert('操作成功!');parent.location.reload();</script>");
		javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
		dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(Constants.WEB_HTML_STRING, "<script>alert('操作失败!');parent.location.reload();</script>");
			javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
			dispatcher.forward(request, response);
		}
		
	}
	
	/**
	 * 显示页面数据
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return @throws
	 *         Exception
	 */
	public void showDesignWeb(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Design design = DesignRemote.getDesign();
		design.showDesignWeb(request);
		ArrayList core_returnlist=(ArrayList)request.getAttribute(Constants.CORE_PARAMETER_NAME);
		
		String sReturnUrl=core_returnlist.get(Constants.CORE_PARAMETER_RETURNURL).toString();
		
		javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(sReturnUrl);
		dispatcher.forward(request, response);
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
	public void updateComponentPos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			Design design = DesignRemote.getDesign();
			//System.out.print("updateComponentPos:start");
			design.updateComponentPos(request);
			//System.out.print("updateComponentPos:end");
			
			if (request.getParameter("RETURN_URL")!=null) 
			{
				if (request.getParameter("RETURN_URL").toString().equals("")==false)
				{
					response.sendRedirect(request.getParameter("RETURN_URL").toString());
					return;
				}
			}
			response.sendRedirect("/promos/core/DesignServlet.do?method=showDesignWeb&AppName="+request.getParameter("AppName").toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(Constants.WEB_HTML_STRING, "<script>alert('操作失败!');parent.location.reload();</script>");
			javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
			dispatcher.forward(request, response);
		}
		
	}
	
	
	
	/**
	 * 复制
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return @throws
	 *         Exception
	 */
	public void designFormCopy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			String sFromAppName=request.getParameter("FromAppName").toString();
			String sToAppName=request.getParameter("ToAppName").toString();
			
			if (sFromAppName.equals(sToAppName)){
				request.setAttribute(Constants.WEB_HTML_STRING, "<script>alert('复制失败!同名错误!');parent.location.reload();</script>");
				
				javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
				dispatcher.forward(request, response);
				return;
			}
			
			
			Design design = DesignRemote.getDesign();
			//System.out.print("updateComponentPos:start");
			design.designFormCopy(request);
			//System.out.print("updateComponentPos:end");
			
			if (request.getParameter("RETURN_URL")!=null) 
			{
				if (request.getParameter("RETURN_URL").toString().equals("")==false)
				{
					response.sendRedirect(request.getParameter("RETURN_URL").toString());
					return;
				}
			}
			
			request.setAttribute(Constants.WEB_HTML_STRING, "<script>alert('复制成功!');parent.location.reload();</script>");
			
			javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(Constants.WEB_HTML_STRING, "<script>alert('复制失败!');parent.location.reload();</script>");
			
			javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
			dispatcher.forward(request, response);
		}
		
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
	public void showComProperty(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sReturnHtml="";
		
		DBBase dbc = DBBaseRemote.getDBBase();
		String strFVC_TABLE_APPCOMP=dbc.getValue("select FVC_TABLE_APPCOMP from "+DB_USER+".T_APPMAIN where FVC_APPNAME='"+request.getParameter("DesignAppName")+"'");
		if ((strFVC_TABLE_APPCOMP.indexOf(".")<0)&&(strFVC_TABLE_APPCOMP.equals("")==false)&&(strFVC_TABLE_APPCOMP!=null)&&(strFVC_TABLE_APPCOMP.indexOf("select")<0)&&(strFVC_TABLE_APPCOMP.indexOf("SELECT")<0))
		{
			strFVC_TABLE_APPCOMP=DB_USER+"."+strFVC_TABLE_APPCOMP;
		}
		
		
		String sSQL="";
		
		sSQL="select * from "+strFVC_TABLE_APPCOMP+" where FVC_HTMLID='"+request.getParameter("FVC_HTMLID")+"'";
		//BasePrint.println("sSQL:"+sSQL);
		
		ArrayList comlist =dbc.selectSQL(DB_USER+"."+"T_APPCOMP",sSQL);
		BaseObject combaseobj = null;
		if (comlist != null &&  comlist.size()> 0) {
			combaseobj = (BaseObject) comlist.get(0);
		}
		else
		{
		}
		
		Base base = BaseRemote.getBase();
		
		sSQL="select distinct FVC_GROUP as FVC_GROUP,FVC_TYPE from "+DB_USER+".T_COMPROEDIT where FVC_GROUP is not null and FVC_TYPE=(select distinct FVC_TYPE from "+strFVC_TABLE_APPCOMP+" where FVC_HTMLID='"+request.getParameter("FVC_HTMLID")+"')  order by FVC_GROUP desc";
		//BasePrint.println("sSQL:"+sSQL);
		
		ArrayList grouplist =dbc.selectSQL(DB_USER+".T_COMPROEDIT",sSQL);
		int grouplength=grouplist.size();
		
		BaseObject groupbaseobj = null;
		String grouphtml="";
		String sHtml="";
		String sSelectSql="";
		String sValue="";
		if (grouplist != null &&  grouplength> 0) {
			for (int j=0; j < grouplength; j++) {
				groupbaseobj = (BaseObject) grouplist.get(j);
				sHtml=sHtml+"  <div id=\"panel"+j+"\" class=\"x-hide-display\" style=\"width:302px;height:420px; overflow:no;overflow-x:hidden;overflow-y:auto;\">\n";
				
				String std="";
				ArrayList list = dbc.selectSQL(DB_USER+"."+"T_COMPROEDIT","select * from "+DB_USER+".T_COMPROEDIT where FVC_TYPE='"+BaseObject.toString(groupbaseobj, "FVC_TYPE")+"' and FVC_GROUP='"+BaseObject.toString(groupbaseobj, "FVC_GROUP")+"' order by FNB_ORDER");
				int length=list.size();
				BaseObject baseobj = null;
				String  sreadonly="";
				if (list != null &&  length> 0) {
					for (int i=0; i < length; i++) {
						baseobj = (BaseObject) list.get(i);
						std=std+"<tr width=\"100%\" bgcolor=\"#ffffff\">\n<td width=\"2%\" bgcolor=\"#ACCDFF\">&nbsp;</td>\n<td width=\"30%\" bgcolor=\"#ffffff\" align=right style=\"word-warp:break-word\">";
						std=std+BaseObject.toString(baseobj, "FVC_TITLE")+"&nbsp;";
						std=std+"</td>\n";
						std=std+"<td width=\"*\" bgcolor=\"#ffffff\" align=left>";
						if (BaseObject.toString(baseobj, "FVC_READYONLY").equals("1")==true) {sreadonly="readonly";}
						else sreadonly="";
						
						sValue=BaseObject.toString(combaseobj, BaseObject.toString(baseobj, "FVC_FIELDNAME"));
						if (BaseObject.toString(baseobj, "FVC_ONCLICK").equals("")==true) {
							sSelectSql=BaseObject.toString(baseobj, "FVC_SELECTSQL");
							if ((sSelectSql.equals("")==false)&&(sreadonly.equals(""))) {
								sSelectSql=base.replHtmlWihtPara(sSelectSql,request);
								sSelectSql=sSelectSql.replaceAll("<%=DB_USER%>",DB_USER);
								
								String sOption="<span class=\"select-style\"> <select ID="+BaseObject.toString(baseobj, "FVC_FIELDNAME")+" NAME='"+BaseObject.toString(baseobj, "FVC_FIELDNAME")+"' height=\"18\" style=\"width:100%\">\n<option value=\"\"></option>\n";
								
								ArrayList sellist=dbc.selectSQL(sSelectSql,sSelectSql);
								
								if (sellist != null) {
									int sellength=sellist.size();
									BaseObject selbaseobj =null;
									for (int ii=0; ii < sellength; ii++) {
										selbaseobj = (BaseObject) sellist.get(ii);
										if (sValue.equals(BaseObject.toString(selbaseobj, "FVC_CODE"))==true)
										{
											sOption=sOption+" <option selected value=\""+BaseObject.toString(selbaseobj, "FVC_CODE")+"\">"+BaseObject.toString(selbaseobj, "FVC_VALUE")+"</option>\n";
										}
										else
										{
											sOption=sOption+" <option value=\""+BaseObject.toString(selbaseobj, "FVC_CODE")+"\">"+BaseObject.toString(selbaseobj, "FVC_VALUE")+"</option>\n";
										}
									}
								}
								sOption=sOption+"\n</select></span>\n";
								std=std+sOption;
							}
							else
							{
								std=std+"<INPUT TYPE='TEXT' "+sreadonly+"  ID="+BaseObject.toString(baseobj, "FVC_FIELDNAME")+" NAME='"+BaseObject.toString(baseobj, "FVC_FIELDNAME")+"' height=\"18\" size=28 class=\"noneside\" value=\""+sValue+"\">";
							}
						}
						else
						{
							std=std+"<INPUT TYPE='TEXT' "+sreadonly+"  ID="+BaseObject.toString(baseobj, "FVC_FIELDNAME")+" NAME='"+BaseObject.toString(baseobj, "FVC_FIELDNAME")+"' height=\"18\" size=22 class=\"noneside\" value=\""+sValue+"\">";
							std=std+"<INPUT TYPE='TEXT' style=\"display:none\"  ID=MEMO"+BaseObject.toString(baseobj, "FVC_FIELDNAME")+" NAME='MEMO"+BaseObject.toString(baseobj, "FVC_FIELDNAME")+"' value=\""+BaseObject.toString(baseobj, "FVC_MEMO")+"\">";
							std=std+"&nbsp;<img ID=IMG"+BaseObject.toString(baseobj, "FVC_FIELDNAME")+" src='../images/min/table.gif' style='cursor:hand' border='0' onClick=\""+BaseObject.toString(baseobj, "FVC_ONCLICK")+";\">";
						}
						
						std=std+"</td>\n";
						std=std+"</tr>\n";
					}
					std="<table width=\"100%\"  height=\"12%\" borderColor=#7c7c7c align=\"left\" cellSpacing=0 borderColorDark=white cellPadding=2 border=1 style=\"table-layout:fixed;overflow:no;overflow-x:hidden;overflow-y:auto;word-break:break-all;\">\n"+std+"</table>\n";
				}
				sHtml=sHtml+std+"</div>\n";
			}
			sHtml="<div id=\"ComPro\" style=\"font-size:12px\">\n"+sHtml+"</div>\n";
			grouphtml="<script type=\"text/javascript\">\n";
			grouphtml=grouphtml+"var tabs = new Ext.TabPanel({renderTo: 'ComPro',activeTab: 0,frame:true,defaults:{autoHeight: true},items:[\n";
			
			if (grouplist != null &&  grouplength> 0) {
				for (int j=0; j < grouplength; j++) {
					groupbaseobj = (BaseObject) grouplist.get(j);
					grouphtml=grouphtml+" {contentEl:'panel"+j+"', title:'"+BaseObject.toString(groupbaseobj, "FVC_GROUP")+"'},\n";
				}
			}
			grouphtml=grouphtml.substring(0,grouphtml.length()-2);
			grouphtml=grouphtml+"]});\n</script>\n";
			
			sReturnHtml=sHtml+"\n"+grouphtml+"\n";		
		}
		
		
		sReturnHtml=base.addAppDefaultHtml(sReturnHtml, request);
		
		sReturnHtml=sReturnHtml+base.getWebJs(request);
		
		request.setAttribute(Constants.WEB_HTML_STRING, sReturnHtml);
		
		
		javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/property.jsp");
		dispatcher.forward(request, response);
	}
	
	public String updateappcomp(String WebAct,String sFVC_APPNAME,String sFVC_OLDAPPNAME) throws RemoteException {
		try
		{
			DBBase core = DBBaseRemote.getDBBase();
			if (WebAct.equals(Constants.PARAMETER_WEBACT_EDIT))
			{
				if (sFVC_OLDAPPNAME.equals("")==false)
					if (sFVC_APPNAME.equals(sFVC_OLDAPPNAME)==false) 
					{
						String strTABLE_APPCOMP=core.getValue("select FVC_TABLE_APPCOMP from T_APPMAIN where FVC_APPNAME ='" +sFVC_APPNAME + "'");
						
						String sSQL="update "+strTABLE_APPCOMP+" set FVC_APPNAME='" +sFVC_APPNAME+"'  where FVC_APPNAME ='" +sFVC_OLDAPPNAME + "'";
						core.executeSQL(sSQL);
						
						return "";
					}
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
	        throw new RemoteException(e.toString());
		}
	}
	
	public void deleteapp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean re=false;
		try
		{
			
			Design design = DesignRemote.getDesign();
			re=design.deleteapp(request); 
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(Constants.WEB_HTML_STRING, "<script>alert('删除失败!');parent.location.reload();</script>");
			
			javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
			dispatcher.forward(request, response);
		}
		
		if (re)
		{
			request.setAttribute(Constants.WEB_HTML_STRING, "<script>alert('删除成功!');parent.location.reload();</script>");
			
			javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
			dispatcher.forward(request, response);
		}
		else
		{
			request.setAttribute(Constants.WEB_HTML_STRING, "<script>alert('删除失败!');parent.location.reload();</script>");
			
			javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
			dispatcher.forward(request, response);
		}
		
	}
	
}
