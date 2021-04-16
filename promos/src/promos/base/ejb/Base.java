package promos.base.ejb;
import java.util.ArrayList;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;

import promos.base.objects.BaseObject;
import promos.base.objects.User;

import java.rmi.RemoteException;
/**
 *
 * <p>
 * 标题: 接口 CoreManage
 * </p>
 * <p>
 * 描述: 系统公用类Bean的接口
 * </p>
 * </p>
 * 版权: Copyright (c) 2003-2004
 * </p>
 * <p>
 * 公司: 广州升域传媒有限公司
 * </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 06/8/2004
 *
 */
@Local

public interface Base {
	public boolean ifExefuntion(String sIfExp,String ExpValues,HttpServletRequest request) throws RemoteException ;
	public Object exeFuntion(String methodname,String motheparvalues,HttpServletRequest request) throws RemoteException ;
	public String replHtmlWihtPara(String sHtml,HttpServletRequest request) throws RemoteException ;
	public String reValuesWihtSysConstans(String AppID,String sHtml,HttpServletRequest request) throws RemoteException;
	public String reValuesWihtField(String sHtml,BaseObject baseobj,boolean ifNullRs) throws RemoteException ;
	public String reValuesWihtPara(String sHtml,HttpServletRequest request) throws RemoteException;
	public String getHtmlCode(BaseObject bComObject,String sTabStype,String sOtherStyle,String sCont,HttpServletRequest request) throws RemoteException ;
	public String getHtmlCode(String sTabStype,String sID,String sName,String sOther,String sCont,HttpServletRequest request) throws RemoteException ;
	public String getHtmlCode(BaseObject bComObject,String sTabStype,String sID,String sName,String sOther,String sCont,HttpServletRequest request) throws RemoteException ;
	public String getHtmlCode(String sTabStype,HttpServletRequest request) throws RemoteException ;
	public String getDivHtmlCode(String sID,String sPosition,String sVisibility,String sHeight,String sWidth,String sLeft,String sTop,String sOtherType,String sOther,String sCont,HttpServletRequest request) throws RemoteException ;
	public String getWebAddBr(String sTAG,int nlength) throws RemoteException ;
	public String getWebAddForm(String sCont,String sName,String sAction,String sTarget,HttpServletRequest request) throws RemoteException ;
	public String getNotPurViewCom(HttpServletRequest request) throws RemoteException ;
	public BaseObject getAppMainBaseObject(HttpServletRequest request) throws RemoteException ;
	public String getAppMainParameter(String sField,HttpServletRequest request) throws RemoteException ;
	public String getAppName(HttpServletRequest request) throws RemoteException ;
	public String getValueFromRequest(String sValueName,String sInitValue,HttpServletRequest request) throws RemoteException ;
	public String getValueFromAttribute(String sValueName,String sInitValue,HttpServletRequest request) throws RemoteException ;
	public String getCheckValueString(String sNotnullCom,String sNotCheckCom,HttpServletRequest request) throws RemoteException ;
	public ArrayList iniReturnParameter(HttpServletRequest request) throws RemoteException ;
	public String getWebJs(HttpServletRequest request) throws RemoteException ;
	public void showWebError(String sErrorCode,String sReturnWebUrl,HttpServletRequest request) throws RemoteException;
	public void initCache(HttpServletRequest request) throws RemoteException;
	public String getComName(BaseObject bComObject) throws RemoteException;
	public String getComDefaultHeigth(String sComHeight,String sType) throws RemoteException ;
	public String getComDefaultWidth(String sComWidth,String sType) throws RemoteException ;
	public String getBaseComHtml(BaseObject bComObject,BaseObject bDataObject,HttpServletRequest request)throws RemoteException ;
	public String getBaseComHtml(BaseObject bComBaseObject,ArrayList lComParalist,BaseObject bDataObject,String sGetdataWhere,HttpServletRequest request)throws RemoteException;
	public String getExtendComHtml(BaseObject bComObject,ArrayList lComParalist,BaseObject bDataObject,String sGetdataWhere,HttpServletRequest request)throws RemoteException;
	public String addComResource(String sCode,HttpServletRequest request) throws RemoteException;
	public String addAppDefaultHtml(String sCode,HttpServletRequest request) throws RemoteException ;
	public String withSpecialChar(String sCode) throws RemoteException;
	public String getDataSourceParameter(String sField,HttpServletRequest request)throws RemoteException;
	public BaseObject getDataSoreceBaseObject(HttpServletRequest request)throws RemoteException;
	public String getTableFieldsFromDbsys(String sMainDataSource) throws RemoteException;
	public String AnalyseDataSource(String sMainDataSource) throws RemoteException ;
	public String[] SplitDataSource(String sMainDataSource) throws RemoteException ;	
	public BaseObject getAppDataObj(HttpServletRequest request,String sWhere) throws RemoteException;
	public ArrayList getComPurViewList(HttpServletRequest request)throws RemoteException;
	public ArrayList getPurViewListByCondition(HttpServletRequest request,String strWhere,User user,String sFlowID,String sFlowStep)throws RemoteException;
	public boolean login(HttpServletRequest request) throws RemoteException;
	
}