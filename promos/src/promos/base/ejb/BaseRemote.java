package promos.base.ejb;

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

public final class BaseRemote
    implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
   * ��������ֹ����̳�
   */
  private BaseRemote() {
  }

  /**
   * �õ��̱�֪ͨ������bean��Զ�̽ӿ�
   *
   * @return Base
   */
  public static Base getBase() {

    //����
    Base dm = null;
    try {

    	dm = (Base) BaseEjbHome.getHome("java:global/promos/BaseBean!promos.base.ejb.Base", Base.class);
	
	    if(dm == null){
	      System.out.print("Now can not look up the JNDI name : "+Base.class.getName());
	      return null;
	    }
      
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return dm;
  }
}