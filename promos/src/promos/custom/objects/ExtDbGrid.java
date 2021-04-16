package promos.custom.objects;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import promos.base.ejb.DBBase;
import promos.base.ejb.DBBaseRemote;
import promos.base.objects.BaseConfig;
import promos.base.objects.BaseObject;
import promos.base.objects.BasePrint;

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

public final class ExtDbGrid
    implements Serializable {
	private final static String DB_USER = BaseConfig.getSettingByName("db_user"); /*系统数据库用户*/

  /**
   * 构建
   */
  public ExtDbGrid() {
  }

  
  /**
   * 得到ExtTree的实现HTML。
   *@param 
   * @return 机构信息。
   */
  public static HashMap getExtDBGridHtml(String stAppName,String strHtmlid,String strHtmlName) throws RemoteException {
	HashMap map = new HashMap();   
	StringBuffer rt = new StringBuffer();
	StringBuffer cols = new StringBuffer();
	StringBuffer sfilter = new StringBuffer();
	StringBuffer sfieldlist = new StringBuffer();
    try {
		String strComTable=DB_USER+"。T_APPCOMP";

		DBBase core = DBBaseRemote.getDBBase();
		
		strComTable=core.getValue("select FVC_TABLE_APPCOMP from "+DB_USER+".T_APPMAIN where FVC_APPNAME='"+stAppName+"'");

		if ((strComTable.indexOf(".")<0)&&(strComTable.equals("")==false)&&(strComTable!=null)&&(strComTable.indexOf("select")<0)&&(strComTable.indexOf("SELECT")<0))
		{
			strComTable=DB_USER+"."+strComTable;
		}

		ArrayList list = core.selectSQL(strComTable,"select * from "+strComTable+" where FVC_APPNAME='"+stAppName+"' AND FVC_CONTAINER='"+strHtmlName+"' order by FNB_LISTORDER");
		
		if(list.size()>0){
			
			BaseObject obj = null;
			String cwidth="";
			
			for (int i = 0; i < list.size(); i++) {
				obj = (BaseObject)list.get(i);
				
				rt.append(",\n{\n");
				rt.append("	name:'"+BaseObject.toString(obj, "FVC_FIELDNAME")+"',\n");
				rt.append("	mapping:'"+BaseObject.toString(obj, "FVC_FIELDNAME")+"',\n");
				rt.append("	type:'string'\n");
				rt.append("}");

				cols.append(",\n{\n");
				cols.append("	header:'"+BaseObject.toString(obj, "FVC_TITLENAME")+"',\n");
				cols.append("	dataIndex:'"+BaseObject.toString(obj, "FVC_FIELDNAME")+"',\n");
                
				cwidth=BaseObject.toString(obj, "FVC_LISTWIDTH");
				if (cwidth.indexOf("%")>0) cwidth="Ext.get(\"Grid"+strHtmlName+"\").getWidth()*"+cwidth.replaceAll("[%]","/100");
				
				if (BaseObject.toString(obj, "FVC_IFLIST").equals("1"))
		        {
					cols.append("	width:0,\n");
					cols.append("	hidden: true,\n");
		        }
		        else
		        {
					cols.append("	width:"+cwidth+",\n");
		        }
				if (BaseObject.toString(obj, "FVC_LISTONCLICK").equals("")==false)
		        {
					cols.append("	renderer:"+BaseObject.toString(obj, "FVC_LISTONCLICK")+",\n");
		        }
				cols.append("	sortable:'1'\n");
				cols.append("}");
				

				sfilter.append(",\n new Ext.menu.CheckItem({ text : '");
				sfilter.append(BaseObject.toString(obj, "FVC_TITLENAME")+"', value : '");
				sfilter.append(BaseObject.toString(obj, "FVC_FIELDNAME")+"', checked : false, group : 'filter', checkHandler : onFilterField})");
		
				
				sfieldlist.append(","+BaseObject.toString(obj, "FVC_FIELDNAME"));
				
			}
			if (rt.length()>0)
			{
			  map.put("FIELDCOMPONENTCONT",rt.toString().substring(1));
			}
			else
			{
		      map.put("FIELDCOMPONENTCONT","");
			}
			
			if (cols.length()>0)
			{
			  map.put("COLSCOMPONENTCONT",cols.toString().substring(1));
			}
			else
			{
		      map.put("COLSCOMPONENTCONT","");
			}

			if (sfilter.length()>0)
			{
			  map.put("KEYSERACHCOMPONENTCONT",sfilter.toString());
			}
			else
			{
		      map.put("KEYSERACHCOMPONENTCONT","");
			}

			if (sfieldlist.length()>0)
			{
			  map.put("FIELDLIST","&FieldList="+sfieldlist.toString().substring(1));
			}
			else
			{
		      map.put("FIELDLIST","&FieldList=");
			}
			
			String strCumSerach=core.getValue("select FVC_DEFAULTVALUE from "+strComTable+" where FVC_APPNAME='"+stAppName+"' AND FVC_HTMLID='"+strHtmlid+"'");
			if ((strCumSerach.equals("")==false)&&(strCumSerach.equals("0")==false)&&(strCumSerach.equals(null)==false))
			{
			strCumSerach=strCumSerach.replaceAll("[}][=][{]","\", value : \"");
			//BasePrint.println("strCumSerach:"+strCumSerach+"\n");
			strCumSerach=strCumSerach.replaceAll("[{]",",\n new Ext.menu.CheckItem({ text : \"");
			//BasePrint.println("strCumSerach:"+strCumSerach+"\n");
			strCumSerach=strCumSerach.replaceAll("[}]","\", checked : false, group : \"scfilter"+strHtmlid+"\", checkHandler : onFilterGroup})\n");
			//BasePrint.println("strCumSerach:"+strCumSerach+"\n");

			  map.put("CUSSERACHCOMPONENTCONT",strCumSerach.toString());
			}
			else
			{
			  map.put("CUSSERACHCOMPONENTCONT","");
			}
		}
		return map;

    }
    catch (Exception e) {
      e.printStackTrace();
    }
	  return map;
	}
}
