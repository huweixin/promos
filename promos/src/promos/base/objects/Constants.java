package promos.base.objects;
public class Constants{
  private Constants(){} //这个类不能实例化

  public static final String ERROR_NOAPPID = "系统找不到业务定义!";
  public static final String ERROR_NODATASOURCE = "系统业务没有定义数据源!";
  public static final String ERROR_NOFINDDATA = "系统没有找到数据!";
  public static final String ERROR_NOKEYFIELD = "系统业务没有定义关健字!";
  public static final String ERROR_NOSEQ = "系统业务没有定义序列号!";
  public static final String ERROR_NOFLOW = "系统业务没有定义流程!";
  public static final String ERROR_CANNOTDELETE = "删除记录出错!";
  public static final String ERROR_CANNOTDADD = "添加或修改记录出错!";
  public static final String ERROR_NOPURVIEW = "您没有该业务权限!";

  public static final String ERROR_NOVIEWPURVIEW = "您没有该业务查看权限!";
  public static final String ERROR_NOEDITPURVIEW = "您没有该业务修改权限!";
  public static final String ERROR_NOADDPURVIEW = "您没有该业务添加权限!";
  public static final String ERROR_NODELEPURVIEW = "您没有该业务删除权限!";

  public static final String ERROR_NODESIGNPURVIEW = "您没有该业务设计权限!";
  
  public static final String ERROR_APPISPURVIEW = "该业务有权限控制，请使用有权限控制的方法访问!";
  public static final String ERROR_NOCREATETABLE = "该业务数据源在数据库中不存在!";
 

  public static final String FLOW_CURLOG_PUR = "FLOW_CURLOG_PUR";//取当前登录者在流程是否有权限的参数名
  public static final String FLOW_CURSETP_FLOWNAME = "FLOW_CURSETP_FLOWNAME";//取当前角色名的参数名
  public static final String FLOW_CURSETP_READONLYCOM = "FLOW_CURSETP_READONLYCOM";//取当前角色只读表单元素的参数名
  public static final String FLOW_CURSETP_NOTENABLEDCOM = "FLOW_CURSETP_NOTENABLEDCOM";//取当前角色失效表单元素的参数名
  public static final String FLOW_CURSETP_NOTLISTCOM = "FLOW_CURSETP_NOTLISTCOM";//取当前角色失效表单元素的参数名
  public static final String FLOW_CURSETP_NOTNULLCOM = "FLOW_CURSETP_NOTNULLCOM";//取当前角色失效表单元素的参数名
  public static final String FLOW_CURSETP_NOTDISPLAYCOM = "FLOW_CURSETP_NOTDISPLAYCOM";//取当前角色失效表单元素的参数名
  public static final String FLOW_CURSETP_NOTCHECKCOM = "FLOW_CURSETP_NOTCHECKCOM";//取当前角色失效表单元素的参数名
  public static final String FLOW_CURSETP_OTHERPROPERTY = "FLOW_CURSETP_OTHERPROPERTY";//取当前角色失效表单元素的参数名
  public static final String FLOW_FLOWACT_NAME = "FLOW_ACT";//流程当前执行动作的参数名称

  public static final String FLOW_FLOWACT_SEND = "SEND";//流程当前执行动作的参数名称

  public static final String FLOW_FLOWACT_FINISH = "FINISH";//流程当前执行动作的参数名称
  public static final String FLOW_FLOWACT_BACK = "BACK";//流程当前执行动作的参数名称
  public static final String FLOW_FLOWACT_TRANSFERD = "TRANSFER";//流程当前执行动作的参数名称

  
  public static final String COM_PROPERTY_OTHER = "COM_PROPERTY_OTHER";//取当前角色失效表单元素的参数名
  public static final String COM_PROPERTY_READONLY = "COM_PROPERTY_READONLY";//取当前角色只读表单元素的参数名
  public static final String COM_PROPERTY_NOTENABLED = "COM_PROPERTY_NOTENABLED";//取当前角色失效表单元素的参数名
  public static final String COM_PROPERTY_NOTLIST= "COM_PROPERTY_NOTLIST";//取当前角色失效表单元素的参数名
  public static final String COM_PROPERTY_NOTNULL = "COM_PROPERTY_NOTNULL";//取当前角色失效表单元素的参数名
  public static final String COM_PROPERTY_NOTDISPLAY = "COM_PROPERTY_NOTDISPLAY";//取当前角色失效表单元素的参数名
  public static final String COM_PROPERTY_NOTCHECK= "COM_PROPERTY_NOTCHECK";//取当前角色失效表单元素的参数名

  public static final String SYS_PURVIEW_VIEW= "SYS_PURVIEW_VIEW";//取当前角色是否有查看权限
  public static final String SYS_PURVIEW_EDIT= "SYS_PURVIEW_EDIT";//取当前角色是否有修改权限
  public static final String SYS_PURVIEW_ADD= "SYS_PURVIEW_ADD";//取当前角色是否有添加权限
  public static final String SYS_PURVIEW_DELE= "SYS_PURVIEW_DELE";//取当前角色是否有删除权限

  public static final String SYS_DESIGN_ADMIN= "SYS_DESIGN_ADMIN";//取当前角色是否有设计admin权限
  public static final String SYS_OPER_ADMIN= "SYS_OPER_ADMIN";//取当前角色是否有操作admin权限
  public static final String SYS_SELF_DESIGN= "SYS_SELF_DESIGN";//取当前角色是否有设计自己项目权限
  
  public static final String SYS_ROLE_DESIGNER= "DESIGNER";//系统设计admin
  public static final String SYS_ROLE_OWNER= "OWNER";//系统设计owner，只能设计自身创建

  
  public static final String SYS_PURVIEW_COMORACT_COM= "1";//COM权限
  public static final String SYS_PURVIEW_COMORACT_ACT= "2";//系统权限

  
  public static final String COM_LIST_TYPE = "8";
  public static final String COM_CUSTOM_TYPE = "12";
  public static final String COM_CUSTOMPARA_NAME ="COM_CUSTOM_PARA";
  public static final String COM_TYPE_INPUT = "1";

  public static final String COM_COMTYPE_BASE = "1"; //基础控件类型，如INPUT
  public static final String COM_COMTYPE_STYLE = "2";//非基础控件类型，但需要style=""来解析
  public static final String COM_COMTYPE_OTHER = "0";//非基础控件类型，不需要style=""来解析

  
  public static final String COM_PARA_READONLY_NAME = "READONLY";
  public static final String COM_PARA_DISABLED_NAME = "DISABLED";
  public static final String COM_PARA_VALUE_NAME = "VALUE";
  public static final String COM_PARA_BACKGROUND_NAME = "BACKBROUND";
  public static final String COM_PARA_DISPLAY_NAME = "DISPLAY";
 
  public static final String COM_ISFUNCTION_NAMELIST = "CONTAINER";
  public static final String COM_EXTENCOM_CONNAME = "<%=COMPONENTCONT%>";

  public static final String COM_CURRENT_BASEOBJECT = "COM_CURRENT_BASEOBJECT";
  public static final String COM_CURRENT_PARALIST = "COM_CURRENT_PARALIST";

  
  public static final String PARAMETER_DEFAULT_WEBMODEL = "/core/core.jsp";
  public static final String PARAMETER_DESIGNT_WEBMODEL = "/core/design.jsp";
  public static final String PARAMETER_CORE_ACTIONNAME = "/promos/core/CoreServlet";
  public static final String PARAMETER_MAIN_ACTIONNAME = "/promos/core/MainServlet";
  public static final String PARAMETER_DEFAULT_WEBDESIGN = "/core/design.jsp";
  public static final String PARAMETER_APPFIELD_APPKEYNAME = "FNB_APPID";
  public static final String PARAMETER_GETDATAWHERE = "GetDataWhere";//表单数据过滤条件参数名称
  public static final String PARAMETER_LISTDATAWHERE = "ListDataWhere";//列表数据过滤条件参数名称
  public static final String PARAMETER_RETURNDATAWHERE = "ReturnDataWhere";//数据过滤条件参数名称
  public static final String PARAMETER_IFREPLACEHTML = "HtmlRep";//HTML是否用相关参数据内容替换参数名称
  public static final String PARAMETER_IFREPLACEJS = "JsRep";//JS是否用相关参数据内容替换参数名称
  public static final String PARAMETER_IFREPLACECONSTANS = "ConRep";//是否用T_SYSCONSTANS相关参数据内容替换参数名称
  public static final String PARAMETER_WEBACT = "WebAct";//JS是否用相关参数据内容替换参数名称
  public static final String PARAMETER_FLOWACT = "FlowAct";//JS是否用相关参数据内容替换参数名称
  public static final String PARAMETER_WEBACT_DELETE = "DELETE";//JS是否用相关参数据内容替换参数名称
  public static final String PARAMETER_WEBACT_ADD = "ADD";//JS是否用相关参数据内容替换参数名称
  public static final String PARAMETER_WEBACT_EDIT = "EDIT";//JS是否用相关参数据内容替换参数名称
  public static final String PARAMETER_FUN_CACHENAME= "PARAMETER_FUN_CACHENAME";//保存执行函数在REQUEST中的标志名称
  public static final String PARAMETER_RETURN_MSG= "RETURN_MSG";//保存执行函数在REQUEST中的标志名称
  public static final String PARAMETER_RETURN_URL= "RETURN_URL";//保存执行函数在REQUEST中的标志名称
  public static final String PARAMETER_IS_SET_COMPURVIEW= "PARAMETER_IS_SET_COMPURVIEW";//表单元素权限设定标志
  
  public static final String PARAMETER_UPDATE_FIELD= "APP_UPDATE_FIELD";//需更新字段列表，XX,XX,XX
  public static final String PARAMETER_NOT_UPDATE_FIELD= "APP_NOT_UPDATE_FIELD";//不需更新字段列表,XX,XX,XX

  public static final String PARAMETER_COMPONENT_TABLENAME= "V_COMPONENT";//页面表单元素加载表名称
  
  public static final String PARAMETER_IS_DESIGN = "APP_IS_DESIGN";//JS是否用相关参数据内容替换参数名称

  public static final String PARAMETER_ISVIEW_NAME = "IsView";//JS是否用相关参数据内容替换参数名称


  public static final String WEB_HTML_STRING = "WEB_HTML_STRING";//解析后的HTML字符串
  
  public static final String WEB_PAGE_FIX = "WEB_PAGE_FIX";//解析后的显示页是否需要fix屏幕居中
  public static final String WEB_PAGE_HEIGHT = "WEB_PAGE_HEIGHT";//如果需fix屏幕居中，解析后的显示页总高
  public static final String WEB_PAGE_WIDTH = "WEB_PAGE_WIDTH";//如果需fix屏幕居中，解析后的显示页总宽


  public static final int PARAMETER_POSTTYPE_JUSTSAVE = 1;//JS是否用相关参数据内容替换参数名称
  public static final int PARAMETER_POSTTYPE_SAVEANDRETURN = 2;//JS是否用相关参数据内容替换参数名称


  public static final String PARAMETER_APPID_NAME = "AppName";//JS是否用相关参数据内容替换参数名称
 
  public static final String PARAMETER_SEQ_NAME = "APPSEQ";//JS是否用相关参数据内容替换参数名称

  public static final String PARAMETER_ATTRIBUTEDATA = "PARAMETER_ATTRIBUTEDATA";//JS是否用相关参数据内容替换参数名称

  public static final String FUNCTION_TYPE_BEFORSAVE = "1";//保存数据前函数类型
  public static final String FUNCTION_TYPE_BEFORADD = "2";//添加数据前函数类型
  public static final String FUNCTION_TYPE_BEFOREDIT = "3";//修改数据前函数类型
  public static final String FUNCTION_TYPE_BEFORDEL = "4";//删除数据前函数类型

  public static final String FUNCTION_TYPE_AFTERSAVE = "5";//保存数据后函数类型
  public static final String FUNCTION_TYPE_AFTERADD = "6";//添加数据后函数类型
  public static final String FUNCTION_TYPE_AFTEREDIT = "7";//修改数据后函数类型
  public static final String FUNCTION_TYPE_AFTERDEL = "8";//删除数据后函数类型
  
  public static final String FUNCTION_TYPE_BEFORFLOW = "9";//流程执行前函数类型
  public static final String FUNCTION_TYPE_BEFORSEND= "10";//流程送出前函数类型
  public static final String FUNCTION_TYPE_BEFORBACK= "11";//流程回退前函数类型
  public static final String FUNCTION_TYPE_BEFORPAUSE= "12";//流程挂起或暂停前函数类型
  public static final String FUNCTION_TYPE_BEFORFINISH= "13";//流程结束或终止前函数类型
  public static final String FUNCTION_TYPE_BEFORTRANSFERD= "14";//流程转移前函数类型
   
  public static final String FUNCTION_TYPE_AFTERFLOW = "15";//流程执行后函数类型
  public static final String FUNCTION_TYPE_AFTERSEND= "16";//流程送出后函数类型
  public static final String FUNCTION_TYPE_AFTERBACK= "17";//流程回退后函数类型
  public static final String FUNCTION_TYPE_AFTERPAUSE= "18";//流程挂起或暂停后函数类型
  public static final String FUNCTION_TYPE_AFTERFINISH= "19";//流程结束或终止后函数类型
  public static final String FUNCTION_TYPE_AFTERTRANSFERD= "20";//流程转移后函数类型
  
  public static final String FUNCTION_EXESQL_PRONAME = "EXESQL";//执行SQL函数类型
  

  public static final String CORE_PARAMETER_NAME = "CORE_RETURNPARA_LIST";
  public static final int CORE_PARAMETER_COUNT = 10;
  public static final int CORE_PARAMETER_RETURNURL = 1;
  public static final int CORE_PARAMETER_DATAOBJECT = 2;

  public static final String CACHE_HTMLCODE_TAG = "HTMLCODE";

  public static final String DESIGN_HTMLCON_TAG = "DESIGNCONCODE";
  public static final String APP_COMPENT_LIST_TAG = "APP_COMPENT_LIST_TAG";
  public static final String COM_PURVIEW_LIST_TAG = "COM_PURVIEW_LIST_TAG";
  
  
  public static final String BUSINESS_ORGID_NAME = "ORGAN_ID";
 
  public static final String BUSINESS_KEYVALUE_NAME = "KEY_FIELD_VALUE";
  

  
  public static final String COM_T_COMPARA_LIST = "COM_T_COMPARA_LIST";  //缓存T_COMPARA表
  public static final String COM_T_COMRESOURCE_LIST = "COM_T_COMRESOURCE_LIST"; //缓存T_COMRESOURCE表
  public static final String APP_T_FUNCTION_LIST = "APP_T_FUNCTION_LIST"; //缓存T_FUNCTION表
  public static final String APP_T_SYSCONSTANS_LIST = "APP_T_SYSCONSTANS_LIST"; //缓存T_SYSCONSTANS表
  public static final String APPMAIN_BASEOBJECT_TAG = "APPMAIN_BASEOBJECT_TAG"; //缓存T_APPMAIN表
  public static final String COMDB_TABLE_FIELDLIST_STRING = "COMDB_TABLE_FIELDLIST_STRING"; //缓存数据源表字段列表

  
 
  /***************T_APPCOMP表缓存查询条件*********************************/
  public static final String COMP_WHERE_CONTAINER_ISNULL = "COMP_WHERE_CONTAINER_ISNULL"; //FVC_CONTAINER is null;
  public static final String COMP_WHERE_CONTAINER_BYVALUE = "COMP_WHERE_CONTAINER_BYVALUE"; //FVC_CONTAINER = 指定值;
  public static final String COMP_WHERE_TYLE_ISDATASOURCE = "COMP_WHERE_TYLE_ISDATASOURCE"; //FVC_TYPE='DATA_SOURCE'
  public static final String COMP_WHERE_TYLE_ISDATASOURCE_BYNAME = "COMP_WHERE_TYLE_ISDATASOURCE_BYNAME"; //FVC_TYPE='DATA_SOURCE' AND FVC_HTMLNAME=指定值
  public static final String COMP_WHERE_IFNOTPURVIEW = "COMP_WHERE_IFNOTPURVIEW"; //FVC_IFNOTPURVIEW=1
  public static final String COMP_WHERE_FIELDCHECKSTRING = "COMP_WHERE_FIELDCHECKSTRING"; //生成页面验证字串
  public static final String COMP_WHERE_COMTYPEISINPUT = "COMP_WHERE_COMTYPEISINPUT"; //trim(FVC_FIELDNAME) is not null and trim(FVC_COMTYPE)='"+Constants.COM_TYPE_INPUT 保存数据时使用
  /***************T_APPCOMP表缓存查询条件结束*********************************/
 
  /***************T_COMPURVIEW表缓存查询条件*********************************/
  public static final String COMPURVIEW_WHERE_MAINBASE = "COMPURVIEW_WHERE_MAINBASE"; //(FVC_ROLEID in ("+user.getRoleidlist()+") or FVC_USERID='"+user.getUserID()+"'  or FVC_DEPTD='"+user.getUserDeptId()+"') and FNB_FLOWID is null and FNB_FLOWSTEPID is null";
  public static final String COMPURVIEW_WHERE_FLOWSTEP = "COMPURVIEW_WHERE_FLOWSTEP"; //(FVC_ROLEID in ("+user.getRoleidlist()+") or FVC_USERID='"+user.getUserID()+"'  or FVC_DEPTD='"+user.getUserDeptId()+"') and ((FNB_FLOWID is null and FNB_FLOWSTEPID is null) or (FNB_FLOWID="+sFlowID+" and FNB_FLOWSTEPID="+sFlowStep+"))";
  /***************T_COMPURVIEW表缓存查询条件结束*********************************/

   

};