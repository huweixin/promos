package promos.design.ejb;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.ejb.Local;
import javax.servlet.http.*;

import promos.base.objects.BaseObject;

/**
 *
 * <p>
 * ����: �ӿ� DesignManage
 * </p>
 * <p>
 * ����: ϵͳ������Bean�Ľӿ�
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