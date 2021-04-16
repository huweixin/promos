package promos.core.servlet;
/**
 * author 胡维新 DATE 2004-1-15
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.ejb.CreateException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import promos.base.ejb.Base;
import promos.base.ejb.BaseRemote;
import promos.base.objects.BaseConfig;
import promos.base.objects.BasePrint;
import promos.base.objects.Constants;
import promos.base.objects.User;
import promos.core.ejb.Core;
import promos.core.ejb.CoreRemote;

public class CoreServlet extends HttpServlet {
	
	
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

		if (request.getParameter("ON_ERROR_URL")!=null)  System.out.println(request.getParameter("ON_ERROR_URL").toString());
		
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
		if (strMethod.equals("setComPurview"))
		{
			try {
				setComPurview(request,response);
			} catch (CreateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		if (strMethod.equals("initCache"))
		{
			initCache(request,response);
			return;
		}

		if (strMethod.equals("union_login"))
		{
			union_login(request,response);
			return;
		}

		if (strMethod.equals("login"))
		{
			login(request,response);
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
	 * @throws CreateException 
	 */
	public void showFromCore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, CreateException {
		String sReturnUrl="";
		try {
			Core core = CoreRemote.getCore();
			core.showFromCore(request);
			ArrayList core_returnlist=(ArrayList)request.getAttribute(Constants.CORE_PARAMETER_NAME);
			
			sReturnUrl=core_returnlist.get(Constants.CORE_PARAMETER_RETURNURL).toString();
			
			BasePrint.println("sReturnUrl:"+sReturnUrl);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(sReturnUrl);
		dispatcher.forward(request, response);
		
	}
	public void setComPurview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, CreateException {
		String sReturnUrl="/core/purview.jsp";
		try {
			request.setAttribute(Constants.PARAMETER_IS_SET_COMPURVIEW,"1");
			Core core = CoreRemote.getCore();
			core.showFromCore(request);

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
		
		Core core = CoreRemote.getCore();
		

		//System.out.println("there:core is get");
		
		try
		{
			boolean rel=core.saveToCore(Constants.PARAMETER_POSTTYPE_SAVEANDRETURN, request);

			//System.out.println("there core is go:"+core);
			
			//if (rel) core.showFromCore();
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
	public void initCache(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Base Base = BaseRemote.getBase();
		try
		{
			Base.initCache(request);
			Base.showWebError("系统缓存重新初始化成功！","javascript:history.back()", request);
			javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
			dispatcher.forward(request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			Base.showWebError(e.toString(),"javascript:window.close()", request);
			javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
			dispatcher.forward(request, response);
		}
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
	public void union_login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream in = request.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(in);
		try{
			User user = (User) ois.readObject();
			
			if (user==null)
			{
				System.out.println("系统登录失败！转入参数对象user为空！");
				response.setStatus(202);
				return;
			}
			else
			{
				HttpSession session = request.getSession();

				if (user.getSessionid().equals(session.getId())==false)
				{
					System.out.println("系统登录失败！session不同步，请清空浏览器缓存再试！");
					response.setStatus(203);
					return;
				}
				
				session.setAttribute(User.SESSION_USER,user);
				response.setStatus(200);
				System.out.println("sessionId:"+session.getId());
				System.out.println("系统登录成功！");
				return;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Base base = BaseRemote.getBase();
		
		String return_type=base.getValueFromRequest("RETURN_TYPE", "", request);
		String return_url=base.getValueFromRequest("RETURN_URL", "", request);
		String on_erroe_url=base.getValueFromRequest("ON_ERROR_URL", "", request);
		
		if ((return_type.equals(""))||(return_url.equals("")))
		{
			request.setAttribute(Constants.WEB_HTML_STRING,"<script>alert('参数错误!');location='"+on_erroe_url+"';</script>");
			javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		return_url=base.replHtmlWihtPara(return_url, request);
		
		
		boolean login=base.login(request);
		
		if (login) {
			if (return_type.equals("1"))
			{
				response.sendRedirect(return_url);
			}else
			if (return_type.equals("2"))
			{
				javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(return_url);
				dispatcher.forward(request, response);
				return;
			}else
			{
				request.setAttribute(Constants.WEB_HTML_STRING,"<script>alert('跳转参数错误!');location='"+on_erroe_url+"';</script>");
				javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}
		else
		{
			request.setAttribute(Constants.WEB_HTML_STRING,"<script>alert('用户名或密码错!');location='"+on_erroe_url+"';</script>");
			javax.servlet.RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/core/core.jsp");
			dispatcher.forward(request, response);
			return;
		}
	}
	
}
