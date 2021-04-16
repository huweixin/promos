package promos.main.ejb;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
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

public interface Main  {
	public boolean setComProperty(HttpServletRequest request) throws RemoteException;
	public boolean saveToCore(int sReturnType,HttpServletRequest request) throws RemoteException ; 
	public boolean showFromCore(HttpServletRequest request) throws RemoteException ; 
}