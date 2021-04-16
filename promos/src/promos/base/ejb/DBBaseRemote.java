package promos.base.ejb;

import java.io.Serializable;

import promos.base.objects.BaseEjbHome;
import promos.core.ejb.Core;


/**
 *
 * <p>����: �� ComRemote </p>
 * <p>����: ��com��EJB��Զ�̽ӿڴ����� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 11/27/2003
 *
 */

public final class DBBaseRemote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ��������ֹ����̳�
	 */
	private DBBaseRemote() {
	}

	/**
	 * �õ����ݿ��������bean��Զ�̽ӿ�
	 *
	 * @return DBBase
	 */
	public static DBBase getDBBase() {

		//����
		DBBase dm = null;
		try {

	    	dm = (DBBase) BaseEjbHome.getHome("java:global/promos/DBBaseBean!promos.base.ejb.DBBase", DBBase.class);

	        if(dm == null){
	          System.out.print("Now can not look up the JNDI name : "+DBBase.class.getName());
	          return null;
	        }
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dm;
	}

}