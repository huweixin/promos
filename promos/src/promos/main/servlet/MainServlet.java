package promos.main.servlet;
/**
 * author 胡维新 DATE 2004-1-15
 */
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.ejb.CreateException;
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
import promos.main.ejb.Main;
import promos.main.ejb.MainRemote;


public class MainServlet extends HttpServlet {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=GBK");
		String strMethod=request.getParameter("method");
		
		strMethod=java.net.URLDecoder.decode(strMethod,"GBK");

		if (BaseConfig.getInstance()==null)
		{
			String strPath = request.getRequestURL().toString();
			strPath = strPath.substring(0,strPath.indexOf(request.getContextPath()));

			URL url = new URL(strPath+"/promos/promos.xml");
			BaseConfig procs = BaseConfig.getInstance(url);
		    BasePrint baseprint = BasePrint.getInstance();
		}
		
		strMethod=strMethod.toString();

		if (strMethod==null) strMethod="";
		
		if (strMethod.equals("showFromCore"))
		{
			try {
				showFromCore(request,response);
			} catch (CreateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}

		if (strMethod.equals("saveToCore"))
		{
			saveToCore(request,response);
			return;
		}
		
		Base Base = BaseRemote.getBase();
		Base.showWebError("请输入方法名！","javascript:history.back()", request);
		
		javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(Constants.PARAMETER_DEFAULT_WEBMODEL);
		dispatcher.forward(request, response);
		
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
	public void showFromCore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,CreateException {

		String sReturnUrl="";
		try {

			Main main = MainRemote.getMain();
			System.out.println("Mainservlet:showFromCore");
			main.showFromCore(request);
			ArrayList main_returnlist=(ArrayList)request.getAttribute(Constants.CORE_PARAMETER_NAME);
			
			sReturnUrl=main_returnlist.get(Constants.CORE_PARAMETER_RETURNURL).toString();
			
			BasePrint.println("sReturnUrl:"+sReturnUrl);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(sReturnUrl);
		dispatcher.forward(request, response);
		
		
		
	}
	
	/**
	 * 保存页面数据
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return @throws
	 *         Exception
	 */
	public void saveToCore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Mainservlet:saveToCore");
		Main main = MainRemote.getMain();

		try
		{
			boolean rel=main.saveToCore(Constants.PARAMETER_POSTTYPE_SAVEANDRETURN, request);

			ArrayList core_returnlist=(ArrayList)request.getAttribute(Constants.CORE_PARAMETER_NAME);
			
			String sReturnUrl=core_returnlist.get(Constants.CORE_PARAMETER_RETURNURL).toString();
			
			if ((request.getParameter("RETURN_URL")!=null)&&rel)
			{
				if (request.getParameter("RETURN_URL").toString().equals("")==false)
				{
					Base base = BaseRemote.getBase();
					String sUrl=base.replHtmlWihtPara(base.getValueFromRequest(Constants.PARAMETER_RETURN_URL,"",request),request);
					response.sendRedirect(sUrl);
					return;
				}
			}
			
			javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(sReturnUrl);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			
			ArrayList core_returnlist=(ArrayList)request.getAttribute(Constants.CORE_PARAMETER_NAME);
			
			if((core_returnlist!=null)&&(core_returnlist.size()>0))
			{
				String sReturnUrl=core_returnlist.get(Constants.CORE_PARAMETER_RETURNURL).toString();
				javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(sReturnUrl);
				dispatcher.forward(request, response);
			}
			else
			{
				request.setAttribute(Constants.WEB_HTML_STRING, "<script>alert('操作失败!');</script>");
				javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/promos/core/core.jsp");
				dispatcher.forward(request, response);
			}
		}		
		
	}
	
}
