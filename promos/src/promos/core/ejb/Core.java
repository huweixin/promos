package promos.core.ejb;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import promos.base.objects.BaseObject;
import java.rmi.RemoteException;

/**
 *
 * <p>
 * ����: �ӿ� CoreManage
 * </p>
 * <p>
 * ����: ϵͳ������Bean�Ľӿ�
 * </p>
 * </p>
 * ��Ȩ: Copyright (c) 2003-2004
 * </p>
 * <p>
 * ��˾: ��������ý���޹�˾
 * </p>
 * ��������Ȩ����
 *
 * @author ��ά��
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