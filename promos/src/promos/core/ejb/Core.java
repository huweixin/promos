package promos.core.ejb;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import promos.base.objects.BaseObject;
import java.rmi.RemoteException;

/**
 *
 * <p>
 * 标题: 接口 CoreManage
 * </p>
 * <p>
 * 描述: 系统公用类Bean的接口
 * </p>
 * </p>
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

public interface Core {
	public String exeFunctionByType(String swhere,HttpServletRequest request) throws RemoteException ;
	public String exeFunctionByBaseObject(BaseObject FuncObj,HttpServletRequest request) throws RemoteException ; 
	public String getHtmlWithAppID(HttpServletRequest request) throws RemoteException ; 
	public boolean showFromCore(HttpServletRequest request) throws RemoteException ; 
	public boolean saveToCore(int sReturnType,HttpServletRequest request) throws RemoteException ; 
	public boolean showWebByHtml(String htmlcode,String sJs,HttpServletRequest request) throws RemoteException ; 
	public boolean exeCustomFunction(String sType,HttpServletRequest request) throws RemoteException ;
}