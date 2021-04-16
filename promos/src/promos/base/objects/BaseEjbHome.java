package promos.base.objects;

import java.rmi.RemoteException;
import java.io.Serializable;
import javax.naming.NamingException;

/**
 *
 * <p>标题: 类 BaseEjbHome </p>
 * <p>描述: 基本接口类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 04/24/2003
 *
 */

public class BaseEjbHome
    implements Serializable {

  /**
   * 构建
   */
  public BaseEjbHome() {
  }

  /**
   * 得到接口(原)
   *
   * @param path JNDI命名
   * @param homeCalss homeClass类型
   * @return homeClass类
   * @throws RemoteException
   * @throws NamingException
   */
  public static Object getHome(String path, Class homeCalss) throws
      RemoteException, NamingException {
    //BasePrint.println("No it look up the JNDI name : "+path);
    Object obj = BaseContext.getInitialContext().lookup(path);
    if(obj == null){
      System.out.print("Now can not look up the JNDI name : "+path);
      return null;
    }
    //Object homeObject = javax.rmi.PortableRemoteObject.narrow(obj, homeCalss);

    return javax.rmi.PortableRemoteObject.narrow(obj, homeCalss);

  }



  /**
   * 得到接口(新)
   * @param path String
   * @param homeCalss Class
   * @throws RemoteException
   * @throws NamingException
   * @return Object

  public static Object getHome(String path, Class homeCalss) throws
    RemoteException, NamingException {

  Object obj = BaseEJBHomeFactory.getInstance().lookupHome(path,homeCalss);
  if(obj == null){
    System.out.print("Now can not look up the JNDI name : "+path);
    return null;
  }

  return obj;

}   */

}
