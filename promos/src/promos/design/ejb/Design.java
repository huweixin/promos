package promos.design.ejb;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.ejb.Local;
import javax.servlet.http.*;

import promos.base.objects.BaseObject;

/**
 *
 * <p>
 * 标题: 接口 DesignManage
 * </p>
 * <p>
 * 描述: 系统公用类Bean的接口
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
 * @version 1.0 06/8/2004
 *
 */
@Local

public interface Design {
	public void showDesignWeb(HttpServletRequest request) throws RemoteException ;
	public void updateComponentPos(HttpServletRequest request) throws RemoteException ;
	public String reComDisegnConstans(String sHtml,BaseObject comobj) throws RemoteException ; 
	
	public String getBaseComHtmlForDesign(BaseObject bComObject,HttpServletRequest request)throws RemoteException ;
	public String getBaseComHtmlForDesign(BaseObject bComBaseObject,ArrayList lComParalist,HttpServletRequest request)throws RemoteException ;
	public String getExtendComHtml(BaseObject bComObject,ArrayList lComParalist,HttpServletRequest request)throws RemoteException ;
	public void designFormCopy(HttpServletRequest request) throws RemoteException ;
	public String reValuesWihtFieldDesign(String sHtml,BaseObject baseobj) throws RemoteException ;

	public String addComResource(String sCode,HttpServletRequest request) throws RemoteException ;
	public boolean deleteapp(HttpServletRequest request) throws RemoteException;
	public boolean saveComProperty(HttpServletRequest request) throws RemoteException ;
	public void runtestsql() throws RemoteException;
}