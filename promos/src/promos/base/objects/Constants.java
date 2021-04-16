package promos.base.objects;
public class Constants{
  private Constants(){} //����಻��ʵ����

  public static final String ERROR_NOAPPID = "ϵͳ�Ҳ���ҵ����!";
  public static final String ERROR_NODATASOURCE = "ϵͳҵ��û�ж�������Դ!";
  public static final String ERROR_NOFINDDATA = "ϵͳû���ҵ�����!";
  public static final String ERROR_NOKEYFIELD = "ϵͳҵ��û�ж���ؽ���!";
  public static final String ERROR_NOSEQ = "ϵͳҵ��û�ж������к�!";
  public static final String ERROR_NOFLOW = "ϵͳҵ��û�ж�������!";
  public static final String ERROR_CANNOTDELETE = "ɾ����¼����!";
  public static final String ERROR_CANNOTDADD = "��ӻ��޸ļ�¼����!";
  public static final String ERROR_NOPURVIEW = "��û�и�ҵ��Ȩ��!";

  public static final String ERROR_NOVIEWPURVIEW = "��û�и�ҵ��鿴Ȩ��!";
  public static final String ERROR_NOEDITPURVIEW = "��û�и�ҵ���޸�Ȩ��!";
  public static final String ERROR_NOADDPURVIEW = "��û�и�ҵ�����Ȩ��!";
  public static final String ERROR_NODELEPURVIEW = "��û�и�ҵ��ɾ��Ȩ��!";

  public static final String ERROR_NODESIGNPURVIEW = "��û�и�ҵ�����Ȩ��!";
  
  public static final String ERROR_APPISPURVIEW = "��ҵ����Ȩ�޿��ƣ���ʹ����Ȩ�޿��Ƶķ�������!";
  public static final String ERROR_NOCREATETABLE = "��ҵ������Դ�����ݿ��в�����!";
 

  public static final String FLOW_CURLOG_PUR = "FLOW_CURLOG_PUR";//ȡ��ǰ��¼���������Ƿ���Ȩ�޵Ĳ�����
  public static final String FLOW_CURSETP_FLOWNAME = "FLOW_CURSETP_FLOWNAME";//ȡ��ǰ��ɫ���Ĳ�����
  public static final String FLOW_CURSETP_READONLYCOM = "FLOW_CURSETP_READONLYCOM";//ȡ��ǰ��ɫֻ����Ԫ�صĲ�����
  public static final String FLOW_CURSETP_NOTENABLEDCOM = "FLOW_CURSETP_NOTENABLEDCOM";//ȡ��ǰ��ɫʧЧ��Ԫ�صĲ�����
  public static final String FLOW_CURSETP_NOTLISTCOM = "FLOW_CURSETP_NOTLISTCOM";//ȡ��ǰ��ɫʧЧ��Ԫ�صĲ�����
  public static final String FLOW_CURSETP_NOTNULLCOM = "FLOW_CURSETP_NOTNULLCOM";//ȡ��ǰ��ɫʧЧ��Ԫ�صĲ�����
  public static final String FLOW_CURSETP_NOTDISPLAYCOM = "FLOW_CURSETP_NOTDISPLAYCOM";//ȡ��ǰ��ɫʧЧ��Ԫ�صĲ�����
  public static final String FLOW_CURSETP_NOTCHECKCOM = "FLOW_CURSETP_NOTCHECKCOM";//ȡ��ǰ��ɫʧЧ��Ԫ�صĲ�����
  public static final String FLOW_CURSETP_OTHERPROPERTY = "FLOW_CURSETP_OTHERPROPERTY";//ȡ��ǰ��ɫʧЧ��Ԫ�صĲ�����
  public static final String FLOW_FLOWACT_NAME = "FLOW_ACT";//���̵�ǰִ�ж����Ĳ�������

  public static final String FLOW_FLOWACT_SEND = "SEND";//���̵�ǰִ�ж����Ĳ�������

  public static final String FLOW_FLOWACT_FINISH = "FINISH";//���̵�ǰִ�ж����Ĳ�������
  public static final String FLOW_FLOWACT_BACK = "BACK";//���̵�ǰִ�ж����Ĳ�������
  public static final String FLOW_FLOWACT_TRANSFERD = "TRANSFER";//���̵�ǰִ�ж����Ĳ�������

  
  public static final String COM_PROPERTY_OTHER = "COM_PROPERTY_OTHER";//ȡ��ǰ��ɫʧЧ��Ԫ�صĲ�����
  public static final String COM_PROPERTY_READONLY = "COM_PROPERTY_READONLY";//ȡ��ǰ��ɫֻ����Ԫ�صĲ�����
  public static final String COM_PROPERTY_NOTENABLED = "COM_PROPERTY_NOTENABLED";//ȡ��ǰ��ɫʧЧ��Ԫ�صĲ�����
  public static final String COM_PROPERTY_NOTLIST= "COM_PROPERTY_NOTLIST";//ȡ��ǰ��ɫʧЧ��Ԫ�صĲ�����
  public static final String COM_PROPERTY_NOTNULL = "COM_PROPERTY_NOTNULL";//ȡ��ǰ��ɫʧЧ��Ԫ�صĲ�����
  public static final String COM_PROPERTY_NOTDISPLAY = "COM_PROPERTY_NOTDISPLAY";//ȡ��ǰ��ɫʧЧ��Ԫ�صĲ�����
  public static final String COM_PROPERTY_NOTCHECK= "COM_PROPERTY_NOTCHECK";//ȡ��ǰ��ɫʧЧ��Ԫ�صĲ�����

  public static final String SYS_PURVIEW_VIEW= "SYS_PURVIEW_VIEW";//ȡ��ǰ��ɫ�Ƿ��в鿴Ȩ��
  public static final String SYS_PURVIEW_EDIT= "SYS_PURVIEW_EDIT";//ȡ��ǰ��ɫ�Ƿ����޸�Ȩ��
  public static final String SYS_PURVIEW_ADD= "SYS_PURVIEW_ADD";//ȡ��ǰ��ɫ�Ƿ������Ȩ��
  public static final String SYS_PURVIEW_DELE= "SYS_PURVIEW_DELE";//ȡ��ǰ��ɫ�Ƿ���ɾ��Ȩ��

  public static final String SYS_DESIGN_ADMIN= "SYS_DESIGN_ADMIN";//ȡ��ǰ��ɫ�Ƿ������adminȨ��
  public static final String SYS_OPER_ADMIN= "SYS_OPER_ADMIN";//ȡ��ǰ��ɫ�Ƿ��в���adminȨ��
  public static final String SYS_SELF_DESIGN= "SYS_SELF_DESIGN";//ȡ��ǰ��ɫ�Ƿ�������Լ���ĿȨ��
  
  public static final String SYS_ROLE_DESIGNER= "DESIGNER";//ϵͳ���admin
  public static final String SYS_ROLE_OWNER= "OWNER";//ϵͳ���owner��ֻ�����������

  
  public static final String SYS_PURVIEW_COMORACT_COM= "1";//COMȨ��
  public static final String SYS_PURVIEW_COMORACT_ACT= "2";//ϵͳȨ��

  
  public static final String COM_LIST_TYPE = "8";
  public static final String COM_CUSTOM_TYPE = "12";
  public static final String COM_CUSTOMPARA_NAME ="COM_CUSTOM_PARA";
  public static final String COM_TYPE_INPUT = "1";

  public static final String COM_COMTYPE_BASE = "1"; //�����ؼ����ͣ���INPUT
  public static final String COM_COMTYPE_STYLE = "2";//�ǻ����ؼ����ͣ�����Ҫstyle=""������
  public static final String COM_COMTYPE_OTHER = "0";//�ǻ����ؼ����ͣ�����Ҫstyle=""������

  
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
  public static final String PARAMETER_GETDATAWHERE = "GetDataWhere";//�����ݹ���������������
  public static final String PARAMETER_LISTDATAWHERE = "ListDataWhere";//�б����ݹ���������������
  public static final String PARAMETER_RETURNDATAWHERE = "ReturnDataWhere";//���ݹ���������������
  public static final String PARAMETER_IFREPLACEHTML = "HtmlRep";//HTML�Ƿ�����ز����������滻��������
  public static final String PARAMETER_IFREPLACEJS = "JsRep";//JS�Ƿ�����ز����������滻��������
  public static final String PARAMETER_IFREPLACECONSTANS = "ConRep";//�Ƿ���T_SYSCONSTANS��ز����������滻��������
  public static final String PARAMETER_WEBACT = "WebAct";//JS�Ƿ�����ز����������滻��������
  public static final String PARAMETER_FLOWACT = "FlowAct";//JS�Ƿ�����ز����������滻��������
  public static final String PARAMETER_WEBACT_DELETE = "DELETE";//JS�Ƿ�����ز����������滻��������
  public static final String PARAMETER_WEBACT_ADD = "ADD";//JS�Ƿ�����ز����������滻��������
  public static final String PARAMETER_WEBACT_EDIT = "EDIT";//JS�Ƿ�����ز����������滻��������
  public static final String PARAMETER_FUN_CACHENAME= "PARAMETER_FUN_CACHENAME";//����ִ�к�����REQUEST�еı�־����
  public static final String PARAMETER_RETURN_MSG= "RETURN_MSG";//����ִ�к�����REQUEST�еı�־����
  public static final String PARAMETER_RETURN_URL= "RETURN_URL";//����ִ�к�����REQUEST�еı�־����
  public static final String PARAMETER_IS_SET_COMPURVIEW= "PARAMETER_IS_SET_COMPURVIEW";//��Ԫ��Ȩ���趨��־
  
  public static final String PARAMETER_UPDATE_FIELD= "APP_UPDATE_FIELD";//������ֶ��б�XX,XX,XX
  public static final String PARAMETER_NOT_UPDATE_FIELD= "APP_NOT_UPDATE_FIELD";//��������ֶ��б�,XX,XX,XX

  public static final String PARAMETER_COMPONENT_TABLENAME= "V_COMPONENT";//ҳ���Ԫ�ؼ��ر�����
  
  public static final String PARAMETER_IS_DESIGN = "APP_IS_DESIGN";//JS�Ƿ�����ز����������滻��������

  public static final String PARAMETER_ISVIEW_NAME = "IsView";//JS�Ƿ�����ز����������滻��������


  public static final String WEB_HTML_STRING = "WEB_HTML_STRING";//�������HTML�ַ���
  
  public static final String WEB_PAGE_FIX = "WEB_PAGE_FIX";//���������ʾҳ�Ƿ���Ҫfix��Ļ����
  public static final String WEB_PAGE_HEIGHT = "WEB_PAGE_HEIGHT";//�����fix��Ļ���У����������ʾҳ�ܸ�
  public static final String WEB_PAGE_WIDTH = "WEB_PAGE_WIDTH";//�����fix��Ļ���У����������ʾҳ�ܿ�


  public static final int PARAMETER_POSTTYPE_JUSTSAVE = 1;//JS�Ƿ�����ز����������滻��������
  public static final int PARAMETER_POSTTYPE_SAVEANDRETURN = 2;//JS�Ƿ�����ز����������滻��������


  public static final String PARAMETER_APPID_NAME = "AppName";//JS�Ƿ�����ز����������滻��������
 
  public static final String PARAMETER_SEQ_NAME = "APPSEQ";//JS�Ƿ�����ز����������滻��������

  public static final String PARAMETER_ATTRIBUTEDATA = "PARAMETER_ATTRIBUTEDATA";//JS�Ƿ�����ز����������滻��������

  public static final String FUNCTION_TYPE_BEFORSAVE = "1";//��������ǰ��������
  public static final String FUNCTION_TYPE_BEFORADD = "2";//�������ǰ��������
  public static final String FUNCTION_TYPE_BEFOREDIT = "3";//�޸�����ǰ��������
  public static final String FUNCTION_TYPE_BEFORDEL = "4";//ɾ������ǰ��������

  public static final String FUNCTION_TYPE_AFTERSAVE = "5";//�������ݺ�������
  public static final String FUNCTION_TYPE_AFTERADD = "6";//������ݺ�������
  public static final String FUNCTION_TYPE_AFTEREDIT = "7";//�޸����ݺ�������
  public static final String FUNCTION_TYPE_AFTERDEL = "8";//ɾ�����ݺ�������
  
  public static final String FUNCTION_TYPE_BEFORFLOW = "9";//����ִ��ǰ��������
  public static final String FUNCTION_TYPE_BEFORSEND= "10";//�����ͳ�ǰ��������
  public static final String FUNCTION_TYPE_BEFORBACK= "11";//���̻���ǰ��������
  public static final String FUNCTION_TYPE_BEFORPAUSE= "12";//���̹������ͣǰ��������
  public static final String FUNCTION_TYPE_BEFORFINISH= "13";//���̽�������ֹǰ��������
  public static final String FUNCTION_TYPE_BEFORTRANSFERD= "14";//����ת��ǰ��������
   
  public static final String FUNCTION_TYPE_AFTERFLOW = "15";//����ִ�к�������
  public static final String FUNCTION_TYPE_AFTERSEND= "16";//�����ͳ���������
  public static final String FUNCTION_TYPE_AFTERBACK= "17";//���̻��˺�������
  public static final String FUNCTION_TYPE_AFTERPAUSE= "18";//���̹������ͣ��������
  public static final String FUNCTION_TYPE_AFTERFINISH= "19";//���̽�������ֹ��������
  public static final String FUNCTION_TYPE_AFTERTRANSFERD= "20";//����ת�ƺ�������
  
  public static final String FUNCTION_EXESQL_PRONAME = "EXESQL";//ִ��SQL��������
  

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
  

  
  public static final String COM_T_COMPARA_LIST = "COM_T_COMPARA_LIST";  //����T_COMPARA��
  public static final String COM_T_COMRESOURCE_LIST = "COM_T_COMRESOURCE_LIST"; //����T_COMRESOURCE��
  public static final String APP_T_FUNCTION_LIST = "APP_T_FUNCTION_LIST"; //����T_FUNCTION��
  public static final String APP_T_SYSCONSTANS_LIST = "APP_T_SYSCONSTANS_LIST"; //����T_SYSCONSTANS��
  public static final String APPMAIN_BASEOBJECT_TAG = "APPMAIN_BASEOBJECT_TAG"; //����T_APPMAIN��
  public static final String COMDB_TABLE_FIELDLIST_STRING = "COMDB_TABLE_FIELDLIST_STRING"; //��������Դ���ֶ��б�

  
 
  /***************T_APPCOMP�����ѯ����*********************************/
  public static final String COMP_WHERE_CONTAINER_ISNULL = "COMP_WHERE_CONTAINER_ISNULL"; //FVC_CONTAINER is null;
  public static final String COMP_WHERE_CONTAINER_BYVALUE = "COMP_WHERE_CONTAINER_BYVALUE"; //FVC_CONTAINER = ָ��ֵ;
  public static final String COMP_WHERE_TYLE_ISDATASOURCE = "COMP_WHERE_TYLE_ISDATASOURCE"; //FVC_TYPE='DATA_SOURCE'
  public static final String COMP_WHERE_TYLE_ISDATASOURCE_BYNAME = "COMP_WHERE_TYLE_ISDATASOURCE_BYNAME"; //FVC_TYPE='DATA_SOURCE' AND FVC_HTMLNAME=ָ��ֵ
  public static final String COMP_WHERE_IFNOTPURVIEW = "COMP_WHERE_IFNOTPURVIEW"; //FVC_IFNOTPURVIEW=1
  public static final String COMP_WHERE_FIELDCHECKSTRING = "COMP_WHERE_FIELDCHECKSTRING"; //����ҳ����֤�ִ�
  public static final String COMP_WHERE_COMTYPEISINPUT = "COMP_WHERE_COMTYPEISINPUT"; //trim(FVC_FIELDNAME) is not null and trim(FVC_COMTYPE)='"+Constants.COM_TYPE_INPUT ��������ʱʹ��
  /***************T_APPCOMP�����ѯ��������*********************************/
 
  /***************T_COMPURVIEW�����ѯ����*********************************/
  public static final String COMPURVIEW_WHERE_MAINBASE = "COMPURVIEW_WHERE_MAINBASE"; //(FVC_ROLEID in ("+user.getRoleidlist()+") or FVC_USERID='"+user.getUserID()+"'  or FVC_DEPTD='"+user.getUserDeptId()+"') and FNB_FLOWID is null and FNB_FLOWSTEPID is null";
  public static final String COMPURVIEW_WHERE_FLOWSTEP = "COMPURVIEW_WHERE_FLOWSTEP"; //(FVC_ROLEID in ("+user.getRoleidlist()+") or FVC_USERID='"+user.getUserID()+"'  or FVC_DEPTD='"+user.getUserDeptId()+"') and ((FNB_FLOWID is null and FNB_FLOWSTEPID is null) or (FNB_FLOWID="+sFlowID+" and FNB_FLOWSTEPID="+sFlowStep+"))";
  /***************T_COMPURVIEW�����ѯ��������*********************************/

   

};