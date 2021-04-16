package promos.main.ejb;

import java.io.Serializable;

import promos.base.objects.BaseEjbHome;
import promos.core.ejb.Core;

/**
 *
 * <p>����: �� MainRemote </p>
 * <p>����: ��Main��EJB��Զ�̽ӿڴ����� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 06/21/2004
 *
 */

public final class MainRemote
    implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
   * ��������ֹ����̳�
   */
  private MainRemote() {
  }

  /**
   * �õ��̱�֪ͨ������bean��Զ�̽ӿ�
   *
   * @return Main
   */
  public static Main getMain() {

    //����
	  Main dm = null;
    try {
      
	  	dm = (Main) BaseEjbHome.getHome("java:global/promos/MainBean!promos.main.ejb.Main", Main.class);
	
	    if(dm == null){
	      System.out.print("Now can not look up the JNDI name : "+Main.class.getName());
	      return null;
	    }
      

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return dm;
  }
}