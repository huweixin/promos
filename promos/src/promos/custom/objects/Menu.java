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
 * <p>标题: 类 Menu </p>
 * <p>描述: Menu的实现类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 12/10/2003
 *
 */

public final class Menu
implements Serializable {
	private final static String DB_USER = BaseConfig.getSettingByName("db_user"); /*系统数据库用户*/
	
	/**
	 * 构建
	 */
	public Menu() {
	}
	
	/* (non-Javadoc)
	 /**
	  * 得到菜单
	  * @return
	  */
	public static HashMap getMenuHtml(String stMenuTable,String strHtmlid) throws RemoteException {
		HashMap map = new HashMap();   
		StringBuffer rt = new StringBuffer();
		try {
			
		if (stMenuTable.equals("")) return map;

		stMenuTable=stMenuTable.replaceAll("<%=DB_USER%>",DB_USER);
			
		
		DBBase core = DBBaseRemote.getDBBase();
		ArrayList list = null;
		
		if ((stMenuTable.indexOf("select")>=0)||(stMenuTable.indexOf("SELECT")>=0))
		{
		   list = core.selectSQL(stMenuTable,"select * from ("+stMenuTable+") A");
		}
		else
		{
			list = core.selectSQL(stMenuTable,"select * from ("+stMenuTable+")");
		}
		
		
		rt.append("<script language=\"javascript\">\n");
		rt.append("function createMenu() {\n");
		rt.append("var mb = new MenuBar;\n");
		rt.append("	var tmp;\n");
		
		String menuname="";
		String onclick="";
		String disabled="";
		String mnemonic="";
		String shortcut="";
		StringBuffer tmp=new StringBuffer();
		
		String menuid;
		String parentid;
		String bootid="0";
		
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				BaseObject menu = (BaseObject)list.get(i);
				
				parentid=BaseObject.toString(menu, "FNB_PARENTID");
				
				if (parentid.equals(bootid)){
					menuname=BaseObject.toString(menu, "FVC_NAME");
					menuid=BaseObject.toString(menu, "FNB_ID");
					onclick=BaseObject.toString(menu, "FVC_ONCLICK");
					if ((onclick!=null)&&(onclick.indexOf("javascript:")==0)) onclick=onclick.substring(11);
					if ((onclick!=null)&&(onclick.indexOf("//")==0)) onclick=onclick.substring(2);
					if ((onclick!=null)&&(onclick.indexOf("/")==0)) onclick="openlocation('"+onclick+"','');";
					
					disabled=BaseObject.toString(menu, "CR_DISABLED");
					mnemonic=BaseObject.toString(menu, "FVC_MNEMONIC");
					shortcut=BaseObject.toString(menu, "FVC_SHORTCUT");
					
					StringBuffer stmp = new StringBuffer();
					tmp=getSubMenu(stmp, list,menuid);
					
					
					if (!tmp.equals(""))
					{
						rt.append("var subitem"+menuid+" = new Menu();\n");
						rt.append(tmp);
						rt.append("mb.add(tmp = new MenuButton( \""+menuname+"\",subitem"+menuid+"));\n");
					}
					else
					{
						rt.append("mb.add(tmp = new MenuButton( \""+menuname+"\"));\n");
					}
					
					
					//if ((onclick!=null)&&(!(onclick.equals("")))) rt.append("tmp.action=new Function(\""+onclick+"\");\n");
					if ((disabled!=null)&&disabled.equals("1")) rt.append("tmp.disabled=true;\n");
					if ((mnemonic!=null)&&(!(mnemonic.equals("")))) rt.append("tmp.mnemonic=\""+mnemonic+"\";\n");
					if ((shortcut!=null)&&(!(shortcut.equals("")))) rt.append("tmp.shortcut=\""+shortcut+"\";\n\n");
					
				}
			}
		}
		
		
		rt.append("	return mb.create();\n");
		rt.append("}\n");
		rt.append("menuDiv.appendChild(createMenu());\n");
		//rt.append("var text=\"default\"\n");
		//rt.append("_changeThemes(text);\n");
		//rt.append("_loadTheme();\n");
		rt.append("</script>\n");
		
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
	public static StringBuffer getSubMenu(StringBuffer subrt,ArrayList menulist,String parentmenuid)throws RemoteException{
		String menuname="";
		String onclick="";
		String icourl="";
		String menuid;
		String disabled="";
		String mnemonic="";
		String shortcut="";
		StringBuffer tmp=new StringBuffer();
		String parentid="";
		
		
		if(menulist.size()>0){
			for (int i = 0; i < menulist.size(); i++) {
				BaseObject menu = (BaseObject)menulist.get(i);
				parentid=BaseObject.toString(menu, "FNB_PARENTID");
				
				if (parentid.equals(parentmenuid)){
					
					menuname=BaseObject.toString(menu, "FVC_NAME");
					menuid=BaseObject.toString(menu, "FNB_ID");
					onclick=BaseObject.toString(menu, "FVC_ONCLICK");
					if (onclick==null) onclick="";
					if ((onclick!=null)&&(onclick.indexOf("javascript:")==0)) onclick=onclick.substring(11);
					if ((onclick!=null)&&(onclick.indexOf("//")==0)) onclick=onclick.substring(2);
					if ((onclick!=null)&&(onclick.indexOf("/")==0)) onclick="location='"+onclick+"';";
					onclick=onclick.replaceAll("\"","'");
					icourl=BaseObject.toString(menu, "FVC_ICOURL");
					
					if (icourl==null) icourl="";
					if (icourl.equals("null")) icourl="";
					if (menuname==null) menuname="";
					
					disabled=BaseObject.toString(menu, "CR_DISABLED");
					mnemonic=BaseObject.toString(menu, "FVC_MNEMONIC");
					shortcut=BaseObject.toString(menu, "FVC_SHORTCUT");
					
					StringBuffer stmp = new StringBuffer();
					
					tmp=getSubMenu(stmp, menulist,menuid);
					
					if (!tmp.equals(""))
					{
						subrt.append("var subitem"+menuid+" = new Menu();\n");
						subrt.append(tmp);
						subrt.append("subitem"+parentmenuid+".add(tmp = new MenuItem( \""+menuname+"\",null, \""+icourl+"\", subitem"+menuid+"));\n");
					}
					else
					{
						if (menuname.equals("-"))
							subrt.append("subitem"+parentmenuid+".add(tmp = new MenuSeparator());\n");
						else
							subrt.append("subitem"+parentmenuid+".add(tmp = new MenuItem( \""+menuname+"\",null, \""+icourl+"\"));\n");
					}
					//if ((onclick!=null)&&(!(onclick.equals("")))&&(!(onclick.equals("null")))) subrt.append("tmp.action=new Function(\""+onclick+"\");\n");
					if ((onclick!=null)&&(!(onclick.equals("")))&&(!(onclick.equals("null")))) subrt.append("tmp.action=\""+onclick+"\";\n");
					if ((disabled!=null)&&disabled.equals("1")) subrt.append("tmp.disabled=true;\n");
					if ((mnemonic!=null)&&(!(mnemonic.equals("")))&&(!(mnemonic.equals("null")))) subrt.append("tmp.mnemonic=\""+mnemonic+"\";\n");
					if ((shortcut!=null)&&(!(shortcut.equals("")))&&(!(shortcut.equals("null")))) subrt.append("tmp.shortcut=\""+shortcut+"\";\n\n");
					
					
				}
			}
		}
		
		//Log.debug("\n"+subrt.toString());
		return subrt;
		
	}		
	

	
	
}
