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
import promos.base.objects.Constants;

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

public final class DbGrid
implements Serializable {
	private final static String DB_USER = BaseConfig.getSettingByName("db_user"); /*系统数据库用户*/
	private final static String DB_SYS = BaseConfig.getSettingByName("db_sys"); /*系统数据库类型*/
	
	/**
	 * 构建
	 */
	public DbGrid() {
	}
	
	
	/**
	 * 得到ExtTree的实现HTML。
	 *@param 
	 * @return 机构信息。
	 */
	/**
	 * @param sMainDataSource
	 * @param sKeyField
	 * @param sListOrderField
	 * @param sSortDesc
	 * @param sIsView
	 * @param sPageIndexEX
	 * @param sPageShowNumberEX
	 * @param sGetdataWhere
	 * @param stAppName
	 * @param strHtmlName
	 * @param strHtmlid
	 * @param sInitPageNum
	 * @return
	 * @throws RemoteException
	 */
	public static HashMap getDBGridHtml(String sMainDataSource,String sKeyField,String sListOrderField,String sSortDesc,String sIsView,String sPageIndexEX,String sPageShowNumberEX,String sGetdataWhere,String sListdataWhere,String stAppName,String strHtmlName,String strHtmlid,String sInitPageNum) throws RemoteException {
		HashMap map = new HashMap();   
		try {
			String strComTable=DB_USER+"。T_APPCOMP";
			
			DBBase core = DBBaseRemote.getDBBase();
			
			strComTable=core.getValue("select FVC_TABLE_APPCOMP from "+DB_USER+".T_APPMAIN where FVC_APPNAME='"+stAppName+"'");
			
			if ((strComTable.indexOf(".")<0)&&(strComTable.equals("")==false)&&(strComTable!=null)&&(strComTable.indexOf("select")<0)&&(strComTable.indexOf("SELECT")<0))
			{
				strComTable=DB_USER+"."+strComTable;
			}

			if ((sMainDataSource.indexOf(".")<0)&&(sMainDataSource.equals("")==false)&&(sMainDataSource!=null)&&(sMainDataSource.indexOf("select")<0)&&(sMainDataSource.indexOf("SELECT")<0))
			{
				sMainDataSource=DB_USER+"."+sMainDataSource;
			}
			
			String s_DataListTitle="<td width='26' bgcolor='#E7E7E7' align='center'> <INPUT ID='"+strHtmlid+"_SELECTED' type='hidden'> <input type='checkbox' name='CheckBoxAll"+strHtmlid+"' onclick='jh_checkall"+strHtmlid+"(this)' style='border:0'></td>\n";
			String s_DataListData="";
			String s_DataListFoot="";
			String s_Tmphtml="";
			
			
			/*组装列表标题*/
			
			ArrayList comlist = core.selectSQL(strComTable,"select * from "+strComTable+" where FVC_APPNAME='"+stAppName+"' AND FVC_CONTAINER='"+strHtmlName+"' and FVC_FIELDNAME is not null order by FNB_LISTORDER");
			
			int comlength=comlist.size();
			BaseObject combaseobj =null;
			String sTmpTitle="";
			String sfieldlist="*";
			if (comlist != null &&  comlength> 0) {
				String sDisPlay="";
				String sWidth="";
				sfieldlist="";
				for (int i = 0; i < comlength; i++) {
					combaseobj = (BaseObject) comlist.get(i);
					sTmpTitle=BaseObject.toString(combaseobj, "FVC_TITLENAME");
					sWidth=BaseObject.toString(combaseobj, "FVC_LISTWIDTH");
					sfieldlist=sfieldlist+","+BaseObject.toString(combaseobj, "FVC_FIELDNAME");
					if ((sWidth.equals("0"))||(sWidth.equals("0px"))||(sWidth.equals("0%"))) sDisPlay=" display:none ";  else sDisPlay="";
					s_DataListTitle=s_DataListTitle+"<TD  ID='COLID"+BaseObject.toString(combaseobj, "FVC_FIELDNAME")+"' bgcolor:'#E7E7E7'  style=\""+sDisPlay+"\" align='center' width='"+BaseObject.toString(combaseobj, "FVC_LISTWIDTH")+"'>"+sTmpTitle+"</TD>\n";
				}
				s_DataListTitle="<TR align='center' bgcolor='#E7E7E7'>"+s_DataListTitle+"</TR>";
				sfieldlist=sfieldlist.substring(1);
			}
			/**************/
			
			/******取分页参数******/
			if (sPageIndexEX.equals("<%=PageIndexEX"+strHtmlid+"%>")) sPageIndexEX="1";
			if (sPageIndexEX.equals("")) sPageIndexEX="1";
			if (Integer.valueOf(sPageIndexEX).intValue()<=0) sPageIndexEX="1";

			if (sPageShowNumberEX.equals("<%=PageShowNumberEX"+strHtmlid+"%>")) sPageShowNumberEX=sInitPageNum;
			if (sPageShowNumberEX.equals("")) sPageShowNumberEX="12";
			
			BasePrint.println("sPageIndexEX:"+sPageIndexEX+"\n");
			BasePrint.println("sPageShowNumberEX:"+sPageShowNumberEX+"\n");
			String sRowCountEX="0";
			int nPageCountEX=1;
			String sSELECTCOMEX="";
			/******************/
			
			
			/*组装数据*/
			comlength=comlist.size();
			if (sListdataWhere.equals("")==false)
			{
				sGetdataWhere=sListdataWhere;
			}
			sGetdataWhere=sGetdataWhere.replaceAll("::","=");
			if (sGetdataWhere.equals("<%=GetdataWhere%>")) sGetdataWhere="1<>1";
			if (sGetdataWhere.equals("")) sGetdataWhere="1<>1";
			String sSQL="";
			
			ArrayList datalist =null;
			int nStarRow=0;
			int nEndRow=0;
			
			sMainDataSource=sMainDataSource.replaceAll("<%=DB_USER%>",DB_USER);
			if ((sMainDataSource.equals("")==false))
			{
				
				
				
				
				/**********sybase数据库时组装数据***************/
				if (DB_SYS.equalsIgnoreCase("sybase"))
				{
					
					if ((sMainDataSource.indexOf("select")>=0)||(sMainDataSource.indexOf("SELECT")>=0))  sMainDataSource= " ("+sMainDataSource+") A ";
					
					if (sGetdataWhere.equals("")==false) 
						sRowCountEX=core.getValue("select count(*) as RowCountEX from "+sMainDataSource+" where "+sGetdataWhere);
					else
						sRowCountEX=core.getValue("select count(*) as RowCountEX from "+sMainDataSource);
					
					nPageCountEX=Integer.valueOf(sRowCountEX).intValue()/Integer.valueOf(sPageShowNumberEX).intValue();
					if (nPageCountEX*Integer.valueOf(sPageShowNumberEX).intValue()!=Integer.valueOf(sRowCountEX).intValue()) nPageCountEX=nPageCountEX+1;
					if ((Integer.valueOf(sPageIndexEX).intValue()>nPageCountEX)&&(nPageCountEX>0)) sPageIndexEX=String.valueOf(nPageCountEX);
					nEndRow=Integer.valueOf(sPageIndexEX).intValue()*Integer.valueOf(sPageShowNumberEX).intValue();
					nStarRow=(Integer.valueOf(sPageIndexEX).intValue()-1)*Integer.valueOf(sPageShowNumberEX).intValue();

					if(sfieldlist.equals(sKeyField)==false) sfieldlist=sKeyField+","+sfieldlist;
					sSQL="select "+sfieldlist+" from "+sMainDataSource+" ";
					if (sGetdataWhere.equals("")==false) sSQL=sSQL+" where "+sGetdataWhere;
					
					if (sListOrderField.equals("")==false)
					{
						sSQL=sSQL+" order by "+sListOrderField+" "+sSortDesc;
					}
					sSQL=" set rowcount "+String.valueOf(nEndRow)+" "+sSQL+"  set rowcount 0";
					
					
					datalist = core.selectSQL(sMainDataSource,sSQL);
					
					
					if (datalist != null) {
						
						int datalength=datalist.size();
						
						String sTmpVaule="";
						String sColor="";
						String sDisPlay="";
						String sWidth="";
						String sListClick="";
						String sDataTmp="";
						BaseObject databaseobj =null;
						for (int i = nStarRow; i < datalength; i++) {
							databaseobj = (BaseObject) datalist.get(i);
							sColor=BaseObject.toString(databaseobj,"COLOR");
							sDataTmp="<TD bgcolor='#FFFFFF'><INPUT TYPE='CHECKBOX' name='CheckBoxAllData'  onclick=\"javascript:jh_itembyid"+strHtmlid+"(this,'"+BaseObject.toString(databaseobj,sKeyField)+"')\"></TD>";
							if (comlist != null &&  comlength> 0) {
								for (int ii = 0; ii < comlength; ii++) {
									combaseobj = (BaseObject) comlist.get(ii);
									sTmpVaule=BaseObject.toString(databaseobj, BaseObject.toString(combaseobj, "FVC_FIELDNAME"));
									if (sTmpVaule.equals("")) sTmpVaule="&nbsp";
									sWidth=BaseObject.toString(combaseobj, "FVC_LISTWIDTH");
									if ((sWidth.equals("0"))||(sWidth.equals("0px"))||(sWidth.equals("0%"))) sDisPlay=" display:none "; else sDisPlay="";
									sListClick=BaseObject.toString(combaseobj, "FVC_LISTONCLICK");
									sListClick=sListClick.replaceAll("<%="+Constants.PARAMETER_ISVIEW_NAME+"%>",sIsView);
									if (sListClick.indexOf("<%=")>=0){
										for (int iii = 0; iii < comlength; iii++) {
											sListClick=sListClick.replaceAll("<%="+BaseObject.toString(combaseobj, "FVC_FIELDNAME")+"%>",BaseObject.toString(databaseobj,BaseObject.toString(combaseobj, "FVC_FIELDNAME")));
										}
									}

									sListClick=sListClick.replaceAll("<%="+sKeyField+"%>",BaseObject.toString(databaseobj,sKeyField));

									
									if (sListClick.equals("")==false)
									{
										sDataTmp=sDataTmp+"<TD ID='"+String.valueOf(i)+BaseObject.toString(combaseobj, "FVC_FIELDNAME")+"' bgcolor='#FFFFFF' style=\""+sDisPlay+"\"><A href=\"javascript:"+sListClick+"\">"+sTmpVaule+"&nbsp;"+"</A></TD>";
									}
									else
									{
										sDataTmp=sDataTmp+"<TD ID='"+String.valueOf(i)+BaseObject.toString(combaseobj, "FVC_FIELDNAME")+"' bgcolor='#FFFFFF' style=\""+sDisPlay+"\"><font color='"+sColor+"'>"+sTmpVaule+"&nbsp;</font></TD>";
									}
								}
							}
							s_DataListData=s_DataListData+"<TR align='center'>"+sDataTmp+"</TR>";
						}
					}
				}
				/**********sybase数据库时组装数据结束***************/
				
				
				/**********oracle数据库时组装数据***************/
				if (DB_SYS.equalsIgnoreCase("oracle"))
				{
					
					if (sGetdataWhere.equals("")==false) 
						sRowCountEX=core.getValue("select count(*) as RowCountEX from "+sMainDataSource+" where "+sGetdataWhere);
					else
						sRowCountEX=core.getValue("select count(*) as RowCountEX from "+sMainDataSource);
					
					nPageCountEX=Integer.valueOf(sRowCountEX).intValue()/Integer.valueOf(sPageShowNumberEX).intValue();
					if (nPageCountEX*Integer.valueOf(sPageShowNumberEX).intValue()!=Integer.valueOf(sRowCountEX).intValue()) nPageCountEX=nPageCountEX+1;
					if ((Integer.valueOf(sPageIndexEX).intValue()>nPageCountEX)&&(nPageCountEX>0)) sPageIndexEX=String.valueOf(nPageCountEX);
					nEndRow=Integer.valueOf(sPageIndexEX).intValue()*Integer.valueOf(sPageShowNumberEX).intValue();
					nStarRow=(Integer.valueOf(sPageIndexEX).intValue()-1)*Integer.valueOf(sPageShowNumberEX).intValue();

					
					if(sfieldlist.equals(sKeyField)==false) sfieldlist=sKeyField+","+sfieldlist;
					sSQL="select "+sfieldlist+" from ("+sMainDataSource+")";
					if (sGetdataWhere.equals("")==false) sSQL=sSQL+" where "+sGetdataWhere;
					
					if (sListOrderField.equals("")==false)
					{
						sSQL=sSQL+" order by "+sListOrderField+" "+sSortDesc;
					}
					sSQL="select * from ( select ROWNUM as TEMP_ROWNUM, TEMP_TABLE.* from ("+sSQL+") TEMP_TABLE  where rownum <= "+String.valueOf(nEndRow)+") where TEMP_ROWNUM >= "+String.valueOf(nStarRow);
					
					datalist = core.selectSQL(sMainDataSource,sSQL);
					
					if (datalist != null) {
						int datalength=datalist.size();
						
						String sTmpVaule="";
						String sColor="";
						String sDisPlay="";
						String sWidth="";
						String sListClick="";
						String sDataTmp="";
						BaseObject databaseobj =null;
						for (int i = 0; i < datalength; i++) {
							databaseobj = (BaseObject) datalist.get(i);
							sColor=BaseObject.toString(databaseobj,"COLOR");
							sDataTmp="<TD bgcolor='#FFFFFF'><INPUT TYPE='CHECKBOX' name='CheckBoxAllData'  onclick=\"javascript:jh_itembyid"+strHtmlid+"(this,'"+BaseObject.toString(databaseobj,sKeyField)+"')\"></TD>";
							if (comlist != null &&  comlength> 0) {
								for (int ii = 0; ii < comlength; ii++) {
									combaseobj = (BaseObject) comlist.get(ii);
									sTmpVaule=BaseObject.toString(databaseobj, BaseObject.toString(combaseobj, "FVC_FIELDNAME"));
									if (sTmpVaule.equals("")) sTmpVaule="&nbsp";
									sWidth=BaseObject.toString(combaseobj, "FVC_LISTWIDTH");
									if ((sWidth.equals("0"))||(sWidth.equals("0px"))||(sWidth.equals("0%"))) sDisPlay=" display:none "; else sDisPlay="";
									sListClick=BaseObject.toString(combaseobj, "FVC_LISTONCLICK");
									sListClick=sListClick.replaceAll("<%="+Constants.PARAMETER_ISVIEW_NAME+"%>",sIsView);
									if (sListClick.indexOf("<%=")>=0){
										for (int iii = 0; iii < comlength; iii++) {
											sListClick=sListClick.replaceAll("<%="+BaseObject.toString(combaseobj, "FVC_FIELDNAME")+"%>",BaseObject.toString(databaseobj,BaseObject.toString(combaseobj, "FVC_FIELDNAME")));
										}
									}
									
									sListClick=sListClick.replaceAll("<%="+sKeyField+"%>",BaseObject.toString(databaseobj,sKeyField));

									if (sListClick.equals("")==false)
									{
										sDataTmp=sDataTmp+"<TD ID='"+String.valueOf(i)+BaseObject.toString(combaseobj, "FVC_FIELDNAME")+"' bgcolor='#FFFFFF' style=\""+sDisPlay+"\"><A href=\"javascript:"+sListClick+"\">"+sTmpVaule+"&nbsp;"+"</A></TD>";
									}
									else
									{
										sDataTmp=sDataTmp+"<TD ID='"+String.valueOf(i)+BaseObject.toString(combaseobj, "FVC_FIELDNAME")+"' bgcolor='#FFFFFF' style=\""+sDisPlay+"\"><font color='"+sColor+"'>"+sTmpVaule+"&nbsp;</font></TD>";
									}
								}
							}
							s_DataListData=s_DataListData+"<TR align='center'>"+sDataTmp+"</TR>";
						}
					}
					
				}				
				/**********oracle数据库时组装数据结束***************/
				
				/**********mysql数据库时组装数据***************/
				if (DB_SYS.equalsIgnoreCase("mysql"))
				{
					
					if (sGetdataWhere.equals("")==false) 
						sRowCountEX=core.getValue("select count(*) as RowCountEX from "+sMainDataSource+" where "+sGetdataWhere);
					else
						sRowCountEX=core.getValue("select count(*) as RowCountEX from "+sMainDataSource);
					
					nPageCountEX=Integer.valueOf(sRowCountEX).intValue()/Integer.valueOf(sPageShowNumberEX).intValue();
					if (nPageCountEX*Integer.valueOf(sPageShowNumberEX).intValue()!=Integer.valueOf(sRowCountEX).intValue()) nPageCountEX=nPageCountEX+1;
					if ((Integer.valueOf(sPageIndexEX).intValue()>nPageCountEX)&&(nPageCountEX>0)) sPageIndexEX=String.valueOf(nPageCountEX);
					nEndRow=Integer.valueOf(sPageIndexEX).intValue()*Integer.valueOf(sPageShowNumberEX).intValue();
					nStarRow=(Integer.valueOf(sPageIndexEX).intValue()-1)*Integer.valueOf(sPageShowNumberEX).intValue();

					
					if(sfieldlist.equals(sKeyField)==false) sfieldlist=sKeyField+","+sfieldlist;
					sSQL="select "+sfieldlist+" from ("+sMainDataSource+")";
					if (sGetdataWhere.equals("")==false) sSQL=sSQL+" where "+sGetdataWhere;
					
					if (sListOrderField.equals("")==false)
					{
						sSQL=sSQL+" order by "+sListOrderField+" "+sSortDesc;
					}

					sSQL="select * from ("+sSQL+") TEMP_TABLE limit "+nStarRow+","+sPageShowNumberEX;
					
					datalist = core.selectSQL(sMainDataSource,sSQL);
					
					if (datalist != null) {
						int datalength=datalist.size();
						
						String sTmpVaule="";
						String sColor="";
						String sDisPlay="";
						String sWidth="";
						String sListClick="";
						String sDataTmp="";
						BaseObject databaseobj =null;
						for (int i = 0; i < datalength; i++) {
							databaseobj = (BaseObject) datalist.get(i);
							sColor=BaseObject.toString(databaseobj,"COLOR");
							sDataTmp="<TD bgcolor='#FFFFFF'><INPUT TYPE='CHECKBOX' name='CheckBoxAllData'  onclick=\"javascript:jh_itembyid"+strHtmlid+"(this,'"+BaseObject.toString(databaseobj,sKeyField)+"')\"></TD>";
							if (comlist != null &&  comlength> 0) {
								for (int ii = 0; ii < comlength; ii++) {
									combaseobj = (BaseObject) comlist.get(ii);
									sTmpVaule=BaseObject.toString(databaseobj, BaseObject.toString(combaseobj, "FVC_FIELDNAME"));
									if (sTmpVaule.equals("")) sTmpVaule="&nbsp";
									sWidth=BaseObject.toString(combaseobj, "FVC_LISTWIDTH");
									if ((sWidth.equals("0"))||(sWidth.equals("0px"))||(sWidth.equals("0%"))) sDisPlay=" display:none "; else sDisPlay="";
									sListClick=BaseObject.toString(combaseobj, "FVC_LISTONCLICK");
									sListClick=sListClick.replaceAll("<%="+Constants.PARAMETER_ISVIEW_NAME+"%>",sIsView);
									if (sListClick.indexOf("<%=")>=0){
										for (int iii = 0; iii < comlength; iii++) {
											sListClick=sListClick.replaceAll("<%="+BaseObject.toString(combaseobj, "FVC_FIELDNAME")+"%>",BaseObject.toString(databaseobj,BaseObject.toString(combaseobj, "FVC_FIELDNAME")));
										}
									}
									
									sListClick=sListClick.replaceAll("<%="+sKeyField+"%>",BaseObject.toString(databaseobj,sKeyField));

									if (sListClick.equals("")==false)
									{
										sDataTmp=sDataTmp+"<TD ID='"+String.valueOf(i)+BaseObject.toString(combaseobj, "FVC_FIELDNAME")+"' bgcolor='#FFFFFF' style=\""+sDisPlay+"\"><A href=\"javascript:"+sListClick+"\">"+sTmpVaule+"&nbsp;"+"</A></TD>";
									}
									else
									{
										sDataTmp=sDataTmp+"<TD ID='"+String.valueOf(i)+BaseObject.toString(combaseobj, "FVC_FIELDNAME")+"' bgcolor='#FFFFFF' style=\""+sDisPlay+"\"><font color='"+sColor+"'>"+sTmpVaule+"&nbsp;</font></TD>";
									}
								}
							}
							s_DataListData=s_DataListData+"<TR align='center'>"+sDataTmp+"</TR>";
						}
					}
					
				}				
				/**********mysql数据库时组装数据结束***************/
				
				/**********mysql数据库时组装数据***************/
				if (DB_SYS.equalsIgnoreCase("mssql"))
				{
					
					if ((sMainDataSource.indexOf("select")<0)&&(sMainDataSource.indexOf("SELECT")<0))
					{
						sSQL="select count(*) as RowCountEX from "+sMainDataSource;
					}
					else
					{
						sSQL="select count(*) as RowCountEX from ("+sMainDataSource+") A ";
					}
					if (sGetdataWhere.equals("")==false) sSQL=sSQL+" where "+sGetdataWhere;
					
					sRowCountEX=core.getValue(sSQL);
					sSQL="";
					
					nPageCountEX=Integer.valueOf(sRowCountEX).intValue()/Integer.valueOf(sPageShowNumberEX).intValue();
					if (nPageCountEX*Integer.valueOf(sPageShowNumberEX).intValue()!=Integer.valueOf(sRowCountEX).intValue()) nPageCountEX=nPageCountEX+1;
					if ((Integer.valueOf(sPageIndexEX).intValue()>nPageCountEX)&&(nPageCountEX>0)) sPageIndexEX=String.valueOf(nPageCountEX);
					nEndRow=Integer.valueOf(sPageIndexEX).intValue()*Integer.valueOf(sPageShowNumberEX).intValue();
					nStarRow=(Integer.valueOf(sPageIndexEX).intValue()-1)*Integer.valueOf(sPageShowNumberEX).intValue();

					
					if(sfieldlist.equals(sKeyField)==false) sfieldlist=sKeyField+","+sfieldlist;
					
					if ((sMainDataSource.indexOf("select")<0)&&(sMainDataSource.indexOf("SELECT")<0))
					{
						sSQL="select "+sfieldlist+" from "+sMainDataSource;
					}
					else
					{
						sSQL="select "+sfieldlist+" from ("+sMainDataSource+") A ";
					}
					if (sGetdataWhere.equals("")==false) sSQL=sSQL+" where "+sGetdataWhere;
					if (sListOrderField.equals("")==false) sSQL=sSQL+"  order by "+sListOrderField+" "+sSortDesc+" offset "+nStarRow+" row fetch next "+sPageShowNumberEX+" row only";
					
					
					
					datalist = core.selectSQL(sMainDataSource,sSQL);
					
					if (datalist != null) {
						int datalength=datalist.size();
						
						String sTmpVaule="";
						String sColor="";
						String sDisPlay="";
						String sWidth="";
						String sListClick="";
						String sDataTmp="";
						BaseObject databaseobj =null;
						for (int i = 0; i < datalength; i++) {
							databaseobj = (BaseObject) datalist.get(i);
							sColor=BaseObject.toString(databaseobj,"COLOR");
							sDataTmp="<TD bgcolor='#FFFFFF'><INPUT TYPE='CHECKBOX' name='CheckBoxAllData'  onclick=\"javascript:jh_itembyid"+strHtmlid+"(this,'"+BaseObject.toString(databaseobj,sKeyField)+"')\"></TD>";
							if (comlist != null &&  comlength> 0) {
								for (int ii = 0; ii < comlength; ii++) {
									combaseobj = (BaseObject) comlist.get(ii);
									sTmpVaule=BaseObject.toString(databaseobj, BaseObject.toString(combaseobj, "FVC_FIELDNAME"));
									if (sTmpVaule.equals("")) sTmpVaule="&nbsp";
									sWidth=BaseObject.toString(combaseobj, "FVC_LISTWIDTH");
									if ((sWidth.equals("0"))||(sWidth.equals("0px"))||(sWidth.equals("0%"))) sDisPlay=" display:none "; else sDisPlay="";
									sListClick=BaseObject.toString(combaseobj, "FVC_LISTONCLICK");
									sListClick=sListClick.replaceAll("<%="+Constants.PARAMETER_ISVIEW_NAME+"%>",sIsView);
									if (sListClick.indexOf("<%=")>=0){
										for (int iii = 0; iii < comlength; iii++) {
											sListClick=sListClick.replaceAll("<%="+BaseObject.toString(combaseobj, "FVC_FIELDNAME")+"%>",BaseObject.toString(databaseobj,BaseObject.toString(combaseobj, "FVC_FIELDNAME")));
										}
									}
									
									sListClick=sListClick.replaceAll("<%="+sKeyField+"%>",BaseObject.toString(databaseobj,sKeyField));

									if (sListClick.equals("")==false)
									{
										sDataTmp=sDataTmp+"<TD ID='"+String.valueOf(i)+BaseObject.toString(combaseobj, "FVC_FIELDNAME")+"' bgcolor='#FFFFFF' style=\""+sDisPlay+"\"><A href=\"javascript:"+sListClick+"\">"+sTmpVaule+"&nbsp;"+"</A></TD>";
									}
									else
									{
										sDataTmp=sDataTmp+"<TD ID='"+String.valueOf(i)+BaseObject.toString(combaseobj, "FVC_FIELDNAME")+"' bgcolor='#FFFFFF' style=\""+sDisPlay+"\"><font color='"+sColor+"'>"+sTmpVaule+"&nbsp;</font></TD>";
									}
								}
							}
							s_DataListData=s_DataListData+"<TR align='center'>"+sDataTmp+"</TR>";
						}
					}
					
				}				
				/**********mysql数据库时组装数据结束***************/				
				

			}
			
			/**************/
			
			
			/**********分页功能*********/
			s_DataListFoot="\n<table width='<%=FVC_WIDTH%>'  height='24' borderColor=#7c7c7c cellSpacing=0 borderColorDark=white cellPadding=4 border=1><tr  bgcolor='#ACCDFF'><td>\n<table width='100%'>    " +
			"\n<tr>     \n<td>     \n<script language='javascript'>document.onkeydown = SubmitPress ; function SubmitPress(){if(event.keyCode == 13){var theInputNumber = document.MyForm.elements['PageShowNumberEX"+strHtmlid+"'].value; if(isNaN(theInputNumber)){alert(\"请输入数字!\");return false;}else if(parseFloat(theInputNumber) == 0){alert('请输入大于零的数字!');return false;}else{document.MyForm.action=window.location;document.MyForm.target=\"_self\";document.MyForm.submit();}} }function SubmitButton(index){ document.MyForm.elements['PageIndexEX"+strHtmlid+"'].value=index;document.MyForm.action=window.location;document.MyForm.target=\"_self\";document.MyForm.submit();}function SubmitList(){document.MyForm.elements['PageIndexEX"+strHtmlid+"'].value=document.MyForm.elements['PageShowListEX'].selectedIndex+1;document.MyForm.action=window.location;document.MyForm.target=\"_self\";document.MyForm.submit();}</script><input type='hidden' name='PageIndexEX"+strHtmlid+"'> 第 <%=PageIndexEX%> / <%=PageCountEX%> 页  每页  <input type='text' name='PageShowNumberEX"+strHtmlid+"' size='2' maxlength='2' value='<%=PageShowNumberEX%>'> 条  <a href='javascript:SubmitButton(0)'> 首页 </a>  <a href='javascript:SubmitButton(<%=PageIndexEX%>-1)'> 上一页 </a>  <a href='javascript:SubmitButton(<%=PageIndexEX%>+1)'> 下一页 </a>  <a href='javascript:SubmitButton(<%=PageCountEX%>)'> 未页 </a>  页码 <%=SELECTCOMEX%> 共 <%=RowCountEX%> 条" +
			"    </td>    </tr>   </table></td></tr></table>";
			s_DataListFoot=s_DataListFoot.replaceAll("<%=PageIndexEX%>",sPageIndexEX);
			s_DataListFoot=s_DataListFoot.replaceAll("<%=PageCountEX%>",String.valueOf(nPageCountEX));
			s_DataListFoot=s_DataListFoot.replaceAll("<%=PageShowNumberEX%>",sPageShowNumberEX);
			s_DataListFoot=s_DataListFoot.replaceAll("<%=RowCountEX%>",sRowCountEX);
			
			for (int i = 1; i < (nPageCountEX+1); i++) {
				if (sPageIndexEX.equals(String.valueOf(i))==true){
					sSELECTCOMEX=sSELECTCOMEX+"<option value='"+String.valueOf(i)+"' selected >"+String.valueOf(i)+"</option>\n";
				}
				else
				{
					sSELECTCOMEX=sSELECTCOMEX+"<option value='"+String.valueOf(i)+"' >"+String.valueOf(i)+"</option>\n";
				}
			}
			sSELECTCOMEX="<SELECT ID=\"PageShowListEX\""+strHtmlid+" onchange='SubmitList()'>\n"+sSELECTCOMEX+"</SELECT>";
			
			s_DataListFoot=s_DataListFoot.replaceAll("<%=SELECTCOMEX%>",sSELECTCOMEX);
			/*************************/	
			
			s_Tmphtml=s_DataListTitle+s_DataListData+s_DataListFoot;
			
			map.put("COMPONENTCONT",s_Tmphtml.toString());
			return map;
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
