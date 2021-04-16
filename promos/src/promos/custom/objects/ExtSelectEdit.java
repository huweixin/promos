package promos.custom.objects;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import promos.base.ejb.DBBase;
import promos.base.ejb.DBBaseRemote;
import promos.base.objects.BaseObject;

/**
 *
 * <p>标题: 类 ExtTree </p>
 * <p>描述: ExtTree的实现类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 12/10/2003
 *
 */

public final class ExtSelectEdit
    implements Serializable {

  /**
   * 构建
   */
  public ExtSelectEdit() {
  }

  
  /**
   * 得到ExtTree的实现HTML。
   *@param 
   * @return 机构信息。
   */
	
	public HashMap getExtSelectEditCon(String sSQL,String sTmpVaule) throws  RemoteException {
		HashMap map = new HashMap();   
		String sOption="";
		try
		{
			DBBase core = DBBaseRemote.getDBBase();
			if (sSQL.equals("")==false)
			{

				ArrayList list=core.selectSQL("",sSQL);
				
				int length=list.size();
				BaseObject baseobj =null;
				if (list != null &&  length> 0) {
					for (int i=0; i < length; i++) {
						baseobj = (BaseObject) list.get(i);
						if (sTmpVaule!=null)
						{
							if (sTmpVaule.equals(BaseObject.toString(baseobj, "FVC_CODE"))==true)
							{
								sOption=sOption+" <option selected value=\""+BaseObject.toString(baseobj, "FVC_CODE")+"\">"+BaseObject.toString(baseobj, "FVC_VALUE")+"</option>";
							}
							else
							{
								sOption=sOption+" <option value=\""+BaseObject.toString(baseobj, "FVC_CODE")+"\">"+BaseObject.toString(baseobj, "FVC_VALUE")+"</option>";
							}
						}
						else
						{
							sOption=sOption+" <option value=\""+BaseObject.toString(baseobj, "FVC_CODE")+"\">"+BaseObject.toString(baseobj, "FVC_VALUE")+"</option>";
						}
					}
				}
			}				
				
		    map.put("COMPONENTCONT",sOption.toString());
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		    map.put("COMPONENTCONT","");
			return map;
		}
	}
	
}
