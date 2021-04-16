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

public final class ExtTree
    implements Serializable {
	private final static String DB_USER = BaseConfig.getSettingByName("db_user"); /*系统数据库用户*/

  /**
   * 构建
   */
  public ExtTree() {
  }

  
  /**
   * 得到ExtTree的实现HTML。
   *@param 
   * @return 机构信息。
   */
  public static HashMap getExtTreeHtml(String stTreeTable,String strHtmlid,String ifexpand) throws RemoteException {
	HashMap map = new HashMap();   
	StringBuffer rt = new StringBuffer();
    try {

		DBBase core = DBBaseRemote.getDBBase();
		
		if (stTreeTable.equals("")) return map;

		stTreeTable=stTreeTable.replaceAll("<%=DB_USER%>",DB_USER);
		ArrayList list =null;
		if ((stTreeTable.indexOf("select")>=0)||(stTreeTable.indexOf("SELECT")>=0))
		{
		   list = core.selectSQL(stTreeTable,"select * from ("+stTreeTable+") A");
		}
		else
		{
			list = core.selectSQL(stTreeTable,"select * from "+stTreeTable);
		}
		
		String treename="";
		String onclick="";
		StringBuffer tmp=new StringBuffer();
		
		String treeid;
		String parentid;
		String bootid="0";
		String strValue="";

		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				BaseObject tree = (BaseObject)list.get(i);
				
				parentid=BaseObject.toString(tree, "FNB_PARENTID");
				
				if (parentid.equals(bootid)){
					treename=BaseObject.toString(tree, "FVC_NAME");
					if (treename==null) treename="";
					
					treeid=BaseObject.toString(tree, "FNB_ID");
					strValue=BaseObject.toString(tree, "FVC_VALUE");
					if (strValue==null) strValue="";
					
					ifexpand=BaseObject.toString(tree, "IFEXPAND");
					if (ifexpand.equals("1"))  ifexpand="true";
					else  ifexpand="false";
					

					onclick=BaseObject.toString(tree, "FVC_ONCLICK");
					if (onclick==null) onclick="";
					if (onclick.equals("")) onclick="function test(){alert('请输入单击事件!')};";

					
					StringBuffer stmp = new StringBuffer();
					tmp=getSubTree(stmp, list,treeid,strHtmlid,ifexpand);
					
					
					if (!tmp.equals(""))
					{
						rt.append("var subitem"+treeid+" = new Tree.TreeNode({\n");
						rt.append("      allowDrag:true,\n");
						rt.append("      allowDrop:true,\n");
						rt.append("      leaf:false,\n");
						rt.append("      expanded:"+ifexpand+",\n");
						rt.append("      id:'"+treeid+"', \n");
						rt.append("      value:'"+strValue+"',\n");
						rt.append("      text: '"+treename+"'\n");
						rt.append("    });\n");

						if ((onclick!=null)&&(!(onclick.equals("")))&&(!(onclick.equals("null"))))
						{
						  rt.append("subitem"+treeid+".on('click', function treefun"+strHtmlid+treeid+"(event,text){"+onclick+"});\n");
						}

						rt.append(tmp);

						rt.append("root"+strHtmlid+".appendChild(subitem"+treeid+");\n");
						
					}
					else
					{
						rt.append("root"+strHtmlid+".appendChild(subitem"+treeid+");\n");

						if ((onclick!=null)&&(!(onclick.equals("")))&&(!(onclick.equals("null"))))
						{
						  rt.append("subitem"+treeid+".on('click', function treefun"+strHtmlid+treeid+"(event,text){"+onclick+"});\n");
						}
					}
					
				}
			}
		}
	    map.put("COMPONENTCONT",rt.toString());
		return map;
    }
    catch (Exception e) {
      e.printStackTrace();
      map.put("COMPONENTCONT","");
  	  return map;
    }
	}
	
	/**
	 * 得到子菜单
	 * @return
	 */
	public static StringBuffer getSubTree(StringBuffer subrt,ArrayList treelist,String parenttreeid,String strHtmlid,String ifexpand)throws RemoteException{


		String treename="";
		String onclick="";
		String treeid;

		StringBuffer tmp=new StringBuffer();
		String parentid="";
		String strValue="";
		
		
		if(treelist.size()>0){
			for (int i = 0; i < treelist.size(); i++) {
				BaseObject tree = (BaseObject)treelist.get(i);
				parentid=BaseObject.toString(tree, "FNB_PARENTID");
				
				if (parentid.equals(parenttreeid)){
					
					treename=BaseObject.toString(tree, "FVC_NAME");
					if (treename==null) treename="";

					treeid=BaseObject.toString(tree, "FNB_ID");
					strValue=BaseObject.toString(tree, "FVC_VALUE");
					if (strValue==null) strValue="";

					ifexpand=BaseObject.toString(tree, "IFEXPAND");
					if (ifexpand.equals("1"))  ifexpand="true";
					else  ifexpand="false";
					
					
					onclick=BaseObject.toString(tree, "FVC_ONCLICK");
					if (onclick==null) onclick="";
					if (onclick.equals("")) onclick="function test(){alert('请输入单击事件!')};";
					
					StringBuffer stmp = new StringBuffer();
					
					tmp=getSubTree(stmp, treelist,treeid,strHtmlid,ifexpand);
					
					if (!tmp.equals(""))
					{
						subrt.append("var subitem"+treeid+" = new Tree.TreeNode({\n");
						subrt.append("      allowDrag:true,\n");
						subrt.append("      allowDrop:true,\n");
						subrt.append("      leaf:false,\n");
						subrt.append("      expanded:"+ifexpand+",\n");
						subrt.append("      id:'"+treeid+"', \n");
						subrt.append("      value:'"+strValue+"',\n");
						subrt.append("      text: '"+treename+"'\n");
						subrt.append("    });\n");

						if ((onclick!=null)&&(!(onclick.equals("")))&&(!(onclick.equals("null"))))
						{
						  subrt.append("subitem"+treeid+".on('click', function treefun"+strHtmlid+treeid+"(event,text){"+onclick+"});\n");
						}

						subrt.append(tmp);

						subrt.append("subitem"+parenttreeid+".appendChild(subitem"+treeid+");\n");
						
					}
					else
					{
						subrt.append("subitem"+parenttreeid+".appendChild(subitem"+treeid+");\n");

						if ((onclick!=null)&&(!(onclick.equals("")))&&(!(onclick.equals("null"))))
						{
							  subrt.append("subitem"+treeid+".on('click', function treefun"+strHtmlid+treeid+"(event,text){"+onclick+"});\n");
						}
					}

					

				}
			}
		}
		
		//Log.debug("\n"+subrt.toString());
		return subrt;
		
	}	  


}
