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
 * <p>描述: ExtMenu的实现类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 12/10/2003
 *
 */

public final class ExtMenu
implements Serializable {
	
	private final static String DB_USER = BaseConfig.getSettingByName("db_user"); /*系统数据库用户*/
	/**
	 * 构建
	 */
	public ExtMenu() {
	}
	
	
	/**
	 * 得到ExtMenu的实现HTML。
	 *@param 
	 * @return HTML。
	 */
	public static HashMap getExtMenuHtml(String stMenuTable,String strHtmlid) throws RemoteException {
		HashMap map = new HashMap();   
		StringBuffer rt = new StringBuffer();
		try {
			
			
			DBBase core = DBBaseRemote.getDBBase();

			if ((stMenuTable.indexOf(".")<0)&&(stMenuTable.equals("")==false)&&(stMenuTable!=null)&&(stMenuTable.indexOf("select")<0)&&(stMenuTable.indexOf("SELECT")<0))
			{
				stMenuTable=DB_USER+"."+stMenuTable;
			}
			
			if ((stMenuTable.indexOf("select")>=0)||(stMenuTable.indexOf("SELECT")>=0))  stMenuTable= " ("+stMenuTable+") A ";
			
			ArrayList list = core.selectSQL(stMenuTable,"select * from "+stMenuTable);
			
			String menuname="";
			StringBuffer tmp=new StringBuffer();
			
			String menuid="";
			//String oldmenuid="";
			String parentid;
			String bootid="0";
			
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					BaseObject menu = (BaseObject)list.get(i);
					
					parentid=BaseObject.toString(menu, "FNB_PARENTID");
					
					if (parentid.equals(bootid)){
						menuname=BaseObject.toString(menu, "FVC_NAME");
						if (menuname==null) menuname="";
						
						//oldmenuid=menuid;
						menuid=BaseObject.toString(menu, "FNB_ID");
						
						StringBuffer stmp = new StringBuffer();
						tmp=getSubMenu(stmp, list,menuid,strHtmlid);
						
						//if (oldmenuid.equals("")==false)
						//{
						//	rt.append("    tb"+strHtmlid+".add('-');\n");
							
						//}
						
						rt.append("    var menu"+menuid+" = new Ext.menu.Menu({\n");
						rt.append("        id: 'Menu"+menuid+"'");
						
						//System.out.print("tmp:"+tmp+"\n");
						
						if (!tmp.toString().equals("")&&(tmp!=null))
						{
							rt.append(",\n        items: [\n");
							rt.append(tmp);
							rt.append("        ]\n");
						}
						rt.append("    });\n");
						rt.append("    tb"+strHtmlid+".add({\n");
						rt.append("            cls: 'x-btn-text-icon bmenu',\n");
						
						rt.append("            text:'"+menuname+"',\n");
						rt.append("            menu: menu"+menuid+"  \n");
						rt.append("        });\n");
						
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
	public static StringBuffer getSubMenu(StringBuffer subrt,ArrayList menulist,String parentmenuid,String strHtmlid)throws RemoteException{
		
		
		String menuname="";
		String onclick="";
		String menuid="";
		String oldmenuid="";
		String disabled="";
		
		StringBuffer tmp=new StringBuffer();
		String parentid="";
		
		
		if(menulist.size()>0){
			for (int i = 0; i < menulist.size(); i++) {
				BaseObject menu = (BaseObject)menulist.get(i);
				parentid=BaseObject.toString(menu, "FNB_PARENTID");
				
				if (parentid.equals(parentmenuid)){
					
					menuname=BaseObject.toString(menu, "FVC_NAME");
					if (menuname==null) menuname="";
					
					oldmenuid=menuid;
					menuid=BaseObject.toString(menu, "FNB_ID");
					
					onclick=BaseObject.toString(menu, "FVC_ONCLICK");
					if (onclick==null) onclick="";
					if (onclick.equals("")) onclick="function test(){alert('请输入菜单事件!')};";
					
					disabled=BaseObject.toString(menu, "CR_DISABLED");
					
					StringBuffer stmp = new StringBuffer();
					
					tmp=getSubMenu(stmp, menulist,menuid,strHtmlid);
					
					if (oldmenuid.equals("")==false)
					{
						subrt.append(",\n");
					}
					
					if (menuname.equals("-"))
					{
						subrt.append(" '-' \n");
						
					}
					else
					{
						subrt.append("                 {\n");
						subrt.append("                   text: '"+menuname+"',\n");
						
						if ((disabled!=null)&&disabled.equals("1")) {
							subrt.append("                   disabled: true,\n");
						}
						
						if ((onclick!=null)&&(!(onclick.equals("")))&&(!(onclick.equals("null"))))
						{
							subrt.append("                   handler: function(){"+onclick+";}");
						}
						
						//System.out.print("tmp1:"+tmp+"\n");
						
						if (!tmp.toString().equals("")&&(tmp!=null))
						{
							subrt.append(",\n                 menu: {\n");
							subrt.append("                    items: [\n");
							subrt.append(tmp);
							subrt.append("                    ]\n");
							subrt.append("                     }\n");
						}
						subrt.append("                 }\n");
					}
				}
			}
		}
		
		//Log.debug("\n"+subrt.toString());
		return subrt;
		
	}	  
	
	
}
