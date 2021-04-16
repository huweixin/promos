package promos.custom.objects;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import promos.base.ejb.DBBase;
import promos.base.ejb.DBBaseRemote;
import promos.base.objects.BaseConfig;
import promos.base.objects.BaseObject;

/**
 *
 * <p>����: �� Tree </p>
 * <p>����: Tree��ʵ���� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 12/10/2003
 *
 */

public final class Tree
    implements Serializable {

	private final static String DB_USER = BaseConfig.getSettingByName("db_user"); /*ϵͳ���ݿ��û�*/
	
  /**
   * ����
   */
  public Tree() {
  }

  
  /**
   * �õ�ExtTree��ʵ��HTML��
   *@param 
   * @return ������Ϣ��
   */
  public static HashMap getTreeHtml(String sTreeDataSource) throws RemoteException {
	HashMap map = new HashMap();   
    try {

		DBBase core = DBBaseRemote.getDBBase();
		
		if (sTreeDataSource.equals("")) return map;

		sTreeDataSource=sTreeDataSource.replaceAll("<%=DB_USER%>",DB_USER);
		
		
		ArrayList list =null;
		String s_html="";
		if (sTreeDataSource.equals("")==false)
		{
			if ((sTreeDataSource.indexOf("select")>=0)||(sTreeDataSource.indexOf("SELECT")>=0))
			{
			   list = core.selectSQL(sTreeDataSource,"select * from ("+sTreeDataSource+") A");
			}
			else
			{
				list = core.selectSQL(sTreeDataSource,"select * from "+sTreeDataSource);
			}
			
			if (list != null && list.size() > 0) {
				int length=list.size();
				int i=0;
				BaseObject baseobj =null;
				for (i=0; i < length; i++) {
					baseobj = (BaseObject) list.get(i);
					s_html=s_html+"addItem(\""+BaseObject.toString(baseobj, "FNB_ID")+"\",\""+BaseObject.toString(baseobj, "FNB_PARENTID")+"\",\"<a href=javascript:SetItem('"+BaseObject.toString(baseobj, "FVC_ONCLICK")+"');>"+BaseObject.toString(baseobj, "FVC_NAME")+"</a>\",\"\",\"\");";
				}
			}
		}
	    map.put("COMPONENTCONT",s_html);
		return map;
    }
    catch (Exception e) {
      e.printStackTrace();
      map.put("COMPONENTCONT","");
  	  return map;
    }
	}

}
