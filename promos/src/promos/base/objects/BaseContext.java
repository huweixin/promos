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
 * <p>标题: 类 BaseContext </p>
 * <p>描述: 基本上下文类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 04/24/2003
 *
 */

public class BaseContext
    implements Serializable {

  /**
   * 构建
   */
  public BaseContext() {
  }

  /**
   * 得到初始上下文
   *
   * @return 初始上下文
   * @throws RemoteException
   * @throws NamingException
   */
  public static Context getInitialContext() throws RemoteException,
      NamingException {
     return new InitialContext();
  }

}
