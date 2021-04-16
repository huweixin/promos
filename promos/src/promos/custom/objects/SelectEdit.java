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
 * <p>标题: 类 Tree </p>
 * <p>描述: Tree的实现类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 12/10/2003
 *
 */

public final class SelectEdit
    implements Serializable {

  /**
   * 构建
   */
  public SelectEdit() {
  }

  
  /**
   * 得到ExtTree的实现HTML。
   *@param 
   * @return 机构信息。
   */
  public static HashMap getSelectEditCon(String sDataSource,String sHTMLID,String sHeigth,String sTop,String sWidth,String sLeft) throws RemoteException {
	HashMap map = new HashMap();   
    try {

		DBBase core = DBBaseRemote.getDBBase();
		
		ArrayList list =null;
		if ((sDataSource.indexOf("select")>=0)||(sDataSource.indexOf("SELECT")>=0))
		{
		   list = core.selectSQL(sDataSource,"select * from ("+sDataSource+") A");
		}
		else
		{
			list = core.selectSQL(sDataSource,"select * from "+sDataSource);
		}
		
		String datadiv="";
		if (list != null && list.size() > 0) {
			int length=list.size();
			if (length>500) length=500;
			int i=0;
			
			if ((sHeigth==null)||(sHeigth.equals(""))) sHeigth="12";
			sTop=sTop.replaceAll("px", "").replaceAll("PX", "");
			if ((sTop==null)||(sTop.equals(""))) sTop="0";
			int nTop=Integer.valueOf(sTop).intValue()+Integer.valueOf(sHeigth).intValue()+8;
			int nHeight=Integer.valueOf(sHeigth).intValue()*15;
			
			datadiv=datadiv+"<DIV id=\"SELECT"+sHTMLID+"\" onmouseover=\"MM_showHideLayers('SELECT"+sHTMLID+"','','show')\" onmouseout=\"MM_showHideLayers('SELECT"+sHTMLID+"','','hide')\" style=\"Z-INDEX: 1; LEFT: "+sLeft+";TOP:"+nTop+";VISIBILITY: hidden; width:"+sWidth+";height: "+nHeight+"; POSITION: absolute; overflow:auto;\">";
			datadiv=datadiv+"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#666666\">\n  ";
			BaseObject baseobj =null;
			String sTmpValue="";
			for (i=0; i < length; i++) {
				baseobj = (BaseObject) list.get(i);
				sTmpValue=baseobj.getFieldValue().get(0).toString();
				sTmpValue=sTmpValue.replaceAll("'","’");
				datadiv=datadiv+"<tr align=center style=\"cursor:hand\" onclick=\"getvalue"+sHTMLID+"('"+sTmpValue+"')\">\n      <td onmouseover=\"this.style.backgroundColor='whitesmoke';\" onmouseout=\"this.style.backgroundColor='#ffffff';\" height=\"18\" align=\"center\" bgcolor=\"#FFFFFF\">"+sTmpValue+"</td>\n  </tr>\n";
			}
			datadiv=datadiv+"</table>\n\n\n";
		}
	    map.put("COMPONENTCONT",datadiv);
		return map;
    }
    catch (Exception e) {
      e.printStackTrace();
      map.put("COMPONENTCONT","");
  	  return map;
    }
	}

}
