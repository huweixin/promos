package promos.base.objects;

import java.rmi.RemoteException;
import java.io.Serializable;
import javax.naming.NamingException;

/**
 *
 * <p>����: �� BaseEjbHome </p>
 * <p>����: �����ӿ��� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 04/24/2003
 *
 */

public class BaseEjbHome
    implements Serializable {

  /**
   * ����
   */
  public BaseEjbHome() {
  }

  /**
   * �õ��ӿ�(ԭ)
   *
   * @param path JNDI����
   * @param homeCalss homeClass����
   * @return homeClass��
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
   * �õ��ӿ�(��)
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
