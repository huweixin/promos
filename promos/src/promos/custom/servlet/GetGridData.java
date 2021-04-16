package promos.custom.servlet;
/**
 * author 胡维新 DATE 2004-1-15
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import promos.base.ejb.DBBase;
import promos.base.ejb.DBBaseRemote;
import promos.base.objects.BaseConfig;
import promos.base.objects.BaseObject;
import promos.base.objects.BasePrint;
import promos.base.objects.Constants;


public class GetGridData extends HttpServlet {
	private final static String DB_SYS = BaseConfig.getSettingByName("db_sys"); /*系统数据库类型*/
	private final static String DB_USER = BaseConfig.getSettingByName("db_user"); /*系统数据库用户*/
	
	public void init() throws ServletException {
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//BasePrint.println("doGet:\n\n");
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		response.setContentType("text/html; charset=GBK");
		//request.setCharacterEncoding("GBK");
		request.setCharacterEncoding("utf-8");
		
		DBBase dbc = DBBaseRemote.getDBBase();
		
		//java.util.Iterator key = request.getParameterMap().keySet().iterator();
		//String str = "";
		//while (key.hasNext()) {
		//	str = (String) key.next();
		//	BasePrint.println("key:"+str+"="+request.getParameter(str).toString()+"\n");
		//}
		
		try{
			String strBuff="";
			String strjson="";
			String strSQL="";
			String strWhere="(1=1) ";
			
			
			String if_fieldkey=request.getParameter("if_fieldkey");
			if (if_fieldkey!=null){
				if_fieldkey=if_fieldkey.toString();
			}
			if (if_fieldkey==null) if_fieldkey="0";
			
			String strFieldList="";
			if (request.getParameter("FieldList")!=null) strFieldList = request.getParameter("FieldList").toString();
			
			String strDbSource="";
			if (request.getParameter("DataSource")!=null) strDbSource = request.getParameter("DataSource").toString();

			
			//String strcomid="";
			//if (request.getParameter("comid")!=null) strcomid = request.getParameter("comid").toString();
			
			String strFieldName="";
			if (request.getParameter("fieldName")!=null) strFieldName = request.getParameter("fieldName").toString();
			String strFieldValue="";
			if (request.getParameter("fieldValue")!=null) strFieldValue = request.getParameter("fieldValue").toString();
			if ((strFieldValue.equals("")==false)&&(strFieldName.equals("")==false)) strWhere=strWhere+" and "+strFieldName+" like '%"+strFieldValue+"%'";
			
			String strFilterGroup="";
			if (request.getParameter("filterGroup")!=null) strFilterGroup = request.getParameter("filterGroup").toString();
			//BasePrint.println("strFilterGroup:"+strFilterGroup);
			if (strFilterGroup.equals("")==false) strWhere=strWhere+" and ("+strFilterGroup+")";
			//BasePrint.println("strWhere:"+strWhere);
			
			
			/****************添加传入过滤条件********************/
			String sGetdataWhere=getValueFromRequest(Constants.PARAMETER_LISTDATAWHERE,"",request);
			if (sGetdataWhere.equals(""))
			{
				sGetdataWhere=getValueFromRequest(Constants.PARAMETER_GETDATAWHERE,"",request);
			}
			
			if (sGetdataWhere.equals("")==false)
			{
				sGetdataWhere=sGetdataWhere.replaceAll("::","=");
				sGetdataWhere=sGetdataWhere.replaceAll("::","=");
				sGetdataWhere=reValuesWihtPara(sGetdataWhere,request);
			}
			else
			{
				sGetdataWhere="1<>1";
			}
			strWhere=strWhere+" and ("+sGetdataWhere+")";
			/****************添加传入过滤条件结束********************/
			
			String strSort="";
			if (request.getParameter("sort")!=null) strSort = request.getParameter("sort").toString();
			String strDir=" desc";
			if (request.getParameter("dir")!=null) strDir = request.getParameter("dir").toString();
			strDir=strDir.toUpperCase();
			if (strDir.equals("DESC")==false&&strDir.equals("ASC")==false) strDir=" desc";
			
			
			
			int start =0;
			int pageSize =20;
			if (request.getParameter("start")!=null) start = Integer.valueOf(request.getParameter("start").toString()).intValue();
			if (request.getParameter("limit")!=null) pageSize = Integer.valueOf(request.getParameter("limit").toString()).intValue();
			
			
			BasePrint.println("start:"+start);
			BasePrint.println("pageSize:"+pageSize);
			
			String rowcount="0";
			
			strDbSource=strDbSource.replaceAll("<%=DB_USER%>",DB_USER);
			if ((strDbSource.indexOf(".")<0)&&(strDbSource.equals("")==false)&&(strDbSource!=null)&&(strDbSource.indexOf("select")<0)&&(strDbSource.indexOf("SELECT")<0))
			{
				strDbSource=DB_USER+"."+strDbSource;
			}
			
			
			if (DB_SYS.equalsIgnoreCase("sybase"))
			{
				
				if ((strDbSource.indexOf("select")>=0)||(strDbSource.indexOf("SELECT")>=0))  strDbSource= " ("+strDbSource+") A ";
				
				strSQL ="select count(*) from "+strDbSource+" where "+strWhere;
				rowcount=dbc.getValue(strSQL);
				
				String tmpFieldList=strFieldList;
				if (if_fieldkey.equals("1")==true)
				{
					tmpFieldList="*";
				}
				
				strSQL =" set rowcount "+String.valueOf(start+pageSize)+" select "+tmpFieldList+" from "+strDbSource+" where "+strWhere;
				if (strSort.equals("")==false) strSQL=strSQL+"  order by "+strSort+" "+strDir+" set rowcount 0";
				
				
				ArrayList datalist =dbc.selectSQL(strSQL,strSQL);
				int length=datalist.size();
				String[] fieldList=strFieldList.split(",");
				int len=fieldList.length;
				BaseObject baseobj = null;
				if (datalist != null &&  length> 0) {
					for (int j=start; j < length; j++) {
						baseobj = (BaseObject) datalist.get(j);
						
						int tmplen=baseobj.getFieldCount()-1;
						if (tmplen>len) tmplen=len;
						
						strjson=strjson+"{";
						for (int i=0; i < tmplen; i++) {
							strjson=strjson+"\""+fieldList[i]+"\":\"";
							if (i==(tmplen-1))
							{
								if (if_fieldkey.equals("1")==true)
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, Integer.valueOf(fieldList[i]).intValue()))+"\"";
								}
								else
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, fieldList[i]))+"\"";
								}
							}
							else
							{
								if (if_fieldkey.equals("1")==true)
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, Integer.valueOf(fieldList[i]).intValue()))+"\",";
								}
								else
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, fieldList[i]))+"\",";
								}
							}
						}
						strjson=strjson+"},";
						
					}
				}
			}
			
			if (DB_SYS.equalsIgnoreCase("oracle"))
			{
				
				strSQL ="select count(*) from ("+strDbSource+") where "+strWhere;
				rowcount=dbc.getValue(strSQL);
				
				String tmpFieldList=strFieldList;
				if (if_fieldkey.equals("1")==true)
				{
					tmpFieldList="*";
				}

				strSQL ="select "+tmpFieldList+" from ("+strDbSource+") where "+strWhere;
				if (strSort.equals("")==false) strSQL=strSQL+"  order by "+strSort+" "+strDir;
				
				
				strSQL = "select * from ( select ROWNUM as TEMP_ROWNUM, TEMP_TABLE.* from ("+strSQL+") TEMP_TABLE  where rownum < "+String.valueOf(start+pageSize)+") where TEMP_ROWNUM >= "+String.valueOf(start);
				
				ArrayList datalist =dbc.selectSQL(strSQL,strSQL);
				int length=datalist.size();
				String[] fieldList=strFieldList.split(",");
				int len=fieldList.length;
				BaseObject baseobj = null;
				if (datalist != null &&  length> 0) {
					for (int j=0; j < length; j++) {
						baseobj = (BaseObject) datalist.get(j);
						
						int tmplen=baseobj.getFieldCount()-1;
						if (tmplen>len) tmplen=len;

						strjson=strjson+"{";
						for (int i=0; i < tmplen; i++) {
							strjson=strjson+"\""+fieldList[i]+"\":\"";
							if (i==(tmplen-1))
							{
								if (if_fieldkey.equals("1")==true)
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, Integer.valueOf(fieldList[i]).intValue()))+"\"";
								}
								else
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, fieldList[i]))+"\"";
								}
							}
							else
							{
								if (if_fieldkey.equals("1")==true)
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, Integer.valueOf(fieldList[i]).intValue()))+"\",";
								}
								else
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, fieldList[i]))+"\",";
								}
							}
						}
						strjson=strjson+"},";
						
					}
				}
			}
			

			if (DB_SYS.equalsIgnoreCase("mysql"))
			{
				
				strSQL ="select count(*) from ("+strDbSource+") where "+strWhere;
				rowcount=dbc.getValue(strSQL);
				
				String tmpFieldList=strFieldList;
				if (if_fieldkey.equals("1")==true)
				{
					tmpFieldList="*";
				}

				strSQL ="select "+tmpFieldList+" from ("+strDbSource+") where "+strWhere;
				if (strSort.equals("")==false) strSQL=strSQL+"  order by "+strSort+" "+strDir;
				
				strSQL="select * from ("+strSQL+") TEMP_TABLE limit "+start+","+pageSize;
				
				ArrayList datalist =dbc.selectSQL(strSQL,strSQL);
				int length=datalist.size();
				String[] fieldList=strFieldList.split(",");
				int len=fieldList.length;
				BaseObject baseobj = null;
				if (datalist != null &&  length> 0) {
					for (int j=0; j < length; j++) {
						baseobj = (BaseObject) datalist.get(j);
						
						int tmplen=baseobj.getFieldCount();
						if (tmplen>len) tmplen=len;

						strjson=strjson+"{";
						for (int i=0; i < tmplen; i++) {
							strjson=strjson+"\""+fieldList[i]+"\":\"";
							if (i==(tmplen-1))
							{
								if (if_fieldkey.equals("1")==true)
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, Integer.valueOf(fieldList[i]).intValue()))+"\"";
								}
								else
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, fieldList[i]))+"\"";
								}
							}
							else
							{
								if (if_fieldkey.equals("1")==true)
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, Integer.valueOf(fieldList[i]).intValue()))+"\",";
								}
								else
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, fieldList[i]))+"\",";
								}
							}
						}
						strjson=strjson+"},";
						
					}
				}
			}
			
			if (DB_SYS.equalsIgnoreCase("mssql"))
			{
				if ((strDbSource.indexOf("select")<0)&&(strDbSource.indexOf("SELECT")<0))
				{
					strSQL ="select count(*) from "+strDbSource+" where "+strWhere;
				}
				else
				{
					strSQL ="select count(*) from ("+strDbSource+") A where "+strWhere;
				}
				
				rowcount=dbc.getValue(strSQL);
				
				String tmpFieldList=strFieldList;
				if (if_fieldkey.equals("1")==true)
				{
					tmpFieldList="*";
				}

				if ((strDbSource.indexOf("select")<0)&&(strDbSource.indexOf("SELECT")<0))
				{
					strSQL ="select "+tmpFieldList+" from "+strDbSource+" where "+strWhere;
				}
				else
				{
					strSQL ="select "+tmpFieldList+" from ("+strDbSource+") A where "+strWhere;
				}
				
				if (strSort.equals("")==false) strSQL=strSQL+"  order by "+strSort+" "+strDir;
				
				strSQL=strSQL+" offset "+start+" row fetch next "+pageSize+" row only";			
				
				
				//strSQL="select top "+pageSize+" * from ( SELECT ROW_NUMBER() OVER (ORDER BY "+strSort+" "+strDir+") AS RowNumber,* FROM "+strDbSource+") as A WHERE RowNumber > "+start+" and "+strWhere+"  order by "+strSort+" "+strDir;
				

				ArrayList datalist =dbc.selectSQL(strSQL,strSQL);
				int length=datalist.size();
				String[] fieldList=strFieldList.split(",");
				int len=fieldList.length;
				BaseObject baseobj = null;
				if (datalist != null &&  length> 0) {
					for (int j=0; j < length; j++) {
						baseobj = (BaseObject) datalist.get(j);
						
						int tmplen=baseobj.getFieldCount();
						if (tmplen>len) tmplen=len;

						strjson=strjson+"{";
						for (int i=0; i < tmplen; i++) {
							strjson=strjson+"\""+fieldList[i]+"\":\"";
							if (i==(tmplen-1))
							{
								if (if_fieldkey.equals("1")==true)
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, Integer.valueOf(fieldList[i]).intValue()))+"\"";
								}
								else
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, fieldList[i]))+"\"";
								}
							}
							else
							{
								if (if_fieldkey.equals("1")==true)
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, Integer.valueOf(fieldList[i]).intValue()))+"\",";
								}
								else
								{
									strjson=strjson+withSpecialChar(BaseObject.toString(baseobj, fieldList[i]))+"\",";
								}
							}
						}
						strjson=strjson+"},";
						
					}
				}
			}			
			
			
			String cb = request.getParameter("callback");   
			
			if (cb != null) {
				response.setContentType("text/javascript; charset=GBK");
				
				strBuff=strBuff+cb + "(";
				strBuff=strBuff+ "{\"totalCount\":\""+rowcount+"\",\"dataList\":[";
				if (Integer.valueOf(rowcount).intValue()>0) strBuff=strBuff+ strjson.substring(0,strjson.lastIndexOf(","));
				strBuff=strBuff+ "]});";
				
			} else {
				response.setContentType("application/x-json; charset=GBK");
				strBuff=strBuff+ "{\"totalCount\":\""+rowcount+"\",\"dataList\":[";
				if (Integer.valueOf(rowcount).intValue()>0) strBuff=strBuff+ strjson.substring(0,strjson.lastIndexOf(","));
				strBuff=strBuff+ "]}";
				
			}
			
			//System.out.println("strBuff:\n\n"+strBuff+"\n\n\n");
			
			PrintWriter out = response.getWriter();
			out.print(strBuff);
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public String withSpecialChar(String sCode) throws RemoteException {
		sCode=sCode.replaceAll("\\>","\\\\>");
		sCode=sCode.replaceAll("\\<","\\\\<");
		sCode=sCode.replaceAll("\\%","\\\\%");
		sCode=sCode.replaceAll("\\=","\\\\=");
		sCode=sCode.replaceAll("\"","”");
		sCode=sCode.replaceAll("'","‘");

		sCode=sCode.replaceAll("\f","");
		sCode=sCode.replaceAll("\n","");
		sCode=sCode.replaceAll("\r","");
		sCode=sCode.replaceAll("\t","");

		//sCode=sCode.replaceAll("展","<font color=red>展<\\/font>");
		
		
		
		return sCode;
	}	
	
	public String reValuesWihtPara(String sHtml,HttpServletRequest request) throws RemoteException {
		if (sHtml.equals("")) return sHtml;
		if (sHtml.indexOf("<%=")<0)  return sHtml;
		Enumeration app_enum = request.getParameterNames();
		String para_name="";
		String val_tmp="";
		while (app_enum.hasMoreElements()) {
			para_name = (String) app_enum.nextElement();
			val_tmp=getValueFromRequest(para_name,"",request);
			sHtml=sHtml.replaceAll("<%="+para_name+"%>",val_tmp);
		}		
		return sHtml;
	}	
	/**
	 * 从request中取参数
	 * @String sValueName  参数名
	 * @String sInitValue  初始值
	 *
	 * @String sAppName
	 */
	public String getValueFromRequest(String sValueName,String sInitValue,HttpServletRequest request) throws RemoteException {
		String sTmpValue=request.getParameter(sValueName);
		if (sTmpValue!=null){
			sTmpValue=sTmpValue.toString();
		}
		if (sTmpValue==null) sTmpValue="";
		if (sTmpValue.equals("")){
			Object obj=request.getAttribute(sValueName);
			if (obj!=null){
				sTmpValue=obj.toString();
			}
		}
		if (sTmpValue==null) sTmpValue="";
		if (sTmpValue.equals("")){
			sTmpValue=sInitValue;
		}
		if (sTmpValue==null) sTmpValue="";
		if (sTmpValue.equals("")==false){
			request.setAttribute(sValueName, sTmpValue);
		}
		return sTmpValue;
	}	
}
