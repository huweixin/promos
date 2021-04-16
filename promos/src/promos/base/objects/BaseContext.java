package promos.base.objects;

//import java.util.Properties;
import java.rmi.RemoteException;
import java.io.Serializable;

import javax.naming.Context;
import javax.naming.NamingException;
//import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * <p>����: �� BaseContext </p>
 * <p>����: ������������ </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 04/24/2003
 *
 */

public class BaseContext
    implements Serializable {

  /**
   * ����
   */
  public BaseContext() {
  }

  /**
   * �õ���ʼ������
   *
   * @return ��ʼ������
   * @throws RemoteException
   * @throws NamingException
   */
  public static Context getInitialContext() throws RemoteException,
      NamingException {
     return new InitialContext();
  }

}
