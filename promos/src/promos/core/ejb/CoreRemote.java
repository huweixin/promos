package promos.core.ejb;

import java.io.Serializable;

import promos.base.objects.BaseEjbHome;

/**
 *
 * <p>����: �� CoreRemote </p>
 * <p>����: ��Core��EJB��Զ�̽ӿڴ����� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 06/21/2004
 *
 */

public final class CoreRemote
    implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

/**
   * ��������ֹ����̳�
   */
  private CoreRemote() {
  }

  /**
   * �õ��̱�֪ͨ������bean��Զ�̽ӿ�
   *
   * @return Core
   */
  public static Core getCore() {

    //����
	 Core dm = null;
    try {

    	dm = (Core) BaseEjbHome.getHome("java:global/promos/CoreBean!promos.core.ejb.Core", Core.class);

        if(dm == null){
          System.out.print("Now can not look up the JNDI name : "+Core.class.getName());
          return null;
        }
        

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return dm;
  }
}