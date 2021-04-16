package promos.base.ejb;

import java.rmi.RemoteException;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Clob;
import java.sql.Blob;

import java.util.ArrayList;
import java.math.BigDecimal;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.CreateException;
import javax.sql.DataSource;

import promos.base.objects.BaseContext;
import promos.base.objects.BaseDataType;
import promos.base.objects.BaseException;
import promos.base.objects.BaseObject;
import promos.base.objects.BasePrint;
import promos.base.objects.BaseConfig;
import promos.base.objects.FieldError;
import promos.base.objects.FormatTools;
import promos.base.objects.PromosDate;



/**
 *
 * <p>����: Bean DBBaseBean </p>
 * <p>����: ����Bean </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 04/22/2003
 *
 */
@Stateless

public class DBBaseBean implements DBBase {

  SessionContext sessionContext;
  
  private static String dataSourceName="";
  private final static String DB_USER = BaseConfig.getSettingByName("db_user"); /*ϵͳ���ݿ��û�*/

  /**
   * Bean�����Լ�������Ҫ����Beanʵ��
   *
   * @throws CreateException
   */
  static {
	    setDataJndi();
  }
  private static void setDataJndi() {
	    if (dataSourceName.equals(""))
	    {
	      dataSourceName= BaseConfig.getJNDIByName("DSPromos");
		  //BasePrint.println("dataSourceName:"+dataSourceName);
	    }
  }
  public void ejbCreate() throws CreateException {

  }

  /**
   * <p>Bean�����Լ�������Ҫɾ��Beanʵ�� </p>
   *
   */

  public void ejbRemove() {

  }

  /**
   * ���µõ������л���Beanʵ��
   *
   */

  public void ejbActivate() {

  }

  /**
   * ���л�Beanʵ�����ͷ���Դ
   *
   */
  public void ejbPassivate() {

  }

  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
}

  /**
   * �õ�����Դ
   *
   * @param  dataSourceName ����ԴJNDI����
   * @return ����Դ
   * @throws RemoteException
   */
  public DataSource getDataSource() throws RemoteException {

    DataSource ds = null;

    try {
      ds = (DataSource)(BaseContext.getInitialContext().lookup(dataSourceName));
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return ds;
  }

  /**
   * �õ�����
   *
   * @param dataSourceName ����ԴJNDI����
   * @return ����
   * @throws RemoteException
   */
  public Connection getConnection() throws RemoteException {

    Connection con = null;

    try {
      DataSource ds = getDataSource();
      con = ds.getConnection();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return con;
  }

  /**
   * �õ�ָ�������ͼ�Ľṹ�������������ͺ��ַ�����
   *
   *
   * @param strDataSource ����Դ��JNDI����
   * @param strTableName �����ͼ������
   * @return �����ͼ�Ļ���
   * @throws RemoteException
   */
  public BaseObject getSimpleFieldByName(String strTableName) throws
      RemoteException {
    return getTableFieldByName(strTableName, true);
  }

  /**
   * �õ�ָ�������ͼ�Ľṹ
   *
   *
   * @param strDataSource ����Դ��JNDI����
   * @param strTableName �����ͼ������
   * @return �����ͼ�Ļ���
   * @throws RemoteException
   */
  public BaseObject getTableByName(String strTableName) throws
      RemoteException {
    return getTableFieldByName(strTableName, false);
  }

  public FieldError checkField(BaseObject baseObj) throws RemoteException {
    BaseObject meta = getTableFieldByName(baseObj.getTableName(), false);
    ArrayList aliasList = baseObj.getFieldAlias();
    ArrayList typeList = baseObj.getFieldType();

    for (int i = 0; i < aliasList.size(); i++) {
      String alias = (String)aliasList.get(i);
      BaseDataType type = (BaseDataType)typeList.get(i);
      if (type.getDataType() == BaseDataType.STRING) {
        String strValue = baseObj.toString(alias);

        int maxLenth = meta.getFieldLength(alias);
        if (strValue.getBytes().length > maxLenth) {
          FieldError field = new FieldError();
          field.alias = alias;
          field.dataType = BaseDataType.STRING;
          field.len = maxLenth;
          field.errorCode = FieldError.VALUE_TOO_LARGER;
          return field;
        }
      }
    }

    return null;
  }

  //�õ�ָ�������ͼ�Ľṹ
  private BaseObject getTableFieldByName(String strTableName, boolean isSimple) throws
      RemoteException {

    BaseObject rs = new BaseObject();

    if (strTableName == null || strTableName.compareTo("") == 0) {
      return rs;
    }

    //�õ�ָ������
    Connection con = null;
    Statement stmt = null;
    ResultSet sqlrs = null;

    //����ֶ�����
    String sqlString = "  select * from " + strTableName + " where rownum=1 ";

    //��ʾSQL���
    BasePrint.print("getTableFieldByName : ", sqlString, true);

    try {

      //�õ�����
      con = this.getConnection();
      stmt = con.createStatement();

      //��ѯָ��������
      sqlrs = stmt.executeQuery(sqlString);

      //�õ������ͼ�Ľṹ
      rs = this.getTableFieldByResultSet(sqlrs, isSimple);

      //��ӱ����ͼ����
      rs.setTableName(strTableName);

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {

      if (sqlrs != null) {
        try {
          sqlrs.close();
        }
        catch (Exception ignore) {
        }
      }

      if (stmt != null) {
        try {
          stmt.close();
        }
        catch (Exception ignore) {
        }
      }

      if (con != null) {
        try {
          con.close();
        }
        catch (Exception ignore) {
        }
      }

    }

    return rs;

  }

  /**
   * �õ�����ֶ����ơ���ѯʱ���ֶα������ֶ����ͺ��ֶ�����
   *
   * @param rs ��ѯ���ݼ�
   * @return ����������
   * @throws RemoteException
   */
  public BaseObject getTableByResultSet(ResultSet rs) throws RemoteException {

    //����������
    BaseObject obj = getTableFieldByResultSet(rs, false);

    return obj;
  }

  //�õ��ֶνṹ
  private BaseObject getTableFieldByResultSet(ResultSet rs, boolean isSimple) throws
      RemoteException {

    //����������
    BaseObject obj = new BaseObject();

    try {

      //�õ���ṹ
      ResultSetMetaData rsmd = rs.getMetaData();

      //���ȵõ��ֶ���
      int fieldCount = rsmd.getColumnCount();

      //�õ��ֶ�����
      int i = 1;
      String fieldName = "";
      String fieldTypeName = "";
      //int fieldTypeMark = 0;
      int fieldLength = 0;
      int fieldPrecision = 0;
      String fieldAlias = "";

      BaseDataType fieldType = new BaseDataType();
      Object fieldValue = new Object();

      for (i = 1; i <= fieldCount; i++) {

        //�õ��ֶ�����
        fieldName = rsmd.getColumnName(i);

        //�õ��ֶ���������
        fieldTypeName = rsmd.getColumnTypeName(i).toUpperCase().trim();

        //�õ��ֶ������Ƿ����С��λ
        //fieldTypeMark = rsmd.getScale(i);

        fieldLength = rsmd.getColumnDisplaySize(i);

        //fieldPrecision = rsmd.getPrecision(i);

        //�����ֶ��������Ƶõ�������������
        //BasePrint.println(fieldName + "   " + fieldTypeName + "  i" + i);
        fieldType = BaseDataType.getBaseDataType(fieldTypeName);

        //�õ��ֶα���
        fieldAlias = rsmd.getColumnLabel(i).toUpperCase().trim();

        //�õ�Ĭ�ϵ��ֶ�ֵ
        fieldValue = BaseDataType.getDefaultValue(fieldType.getDataType());

        //��������һ�������
        if (isSimple) {
          int intFieldType = fieldType.getDataType();
          if (intFieldType == BaseDataType.INTEGER ||
              intFieldType == BaseDataType.NUMBER ||
              intFieldType == BaseDataType.STRING) {
            //���
            obj.addField(fieldName, fieldAlias, fieldType, fieldValue,
                         fieldLength, fieldPrecision);

          }

        }
        else {

          //���
          obj.addField(fieldName, fieldAlias, fieldType, fieldValue,
                       fieldLength, fieldPrecision);
        }

        //����
        fieldName = new String("");
        fieldTypeName = new String("");
        fieldAlias = new String();
        fieldValue = new Object();

      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return obj;
  }

  /**
   * ��ѯ��¼
   *
   * <p>������ʹ���������£�</p>
   * <p>1�������ڵ������ͼ��ѯ��</p>
   * <p>2�������ڼ������жϲ�ѯ��</p>
   * <p>3���б��е�ÿ������֮���� AND ��ϵ��</p>
   * <p>4���б��е�ÿһ��������֮���� OR ��ϵ��</p>
   * <p>5�������ж��޶��������б��е�ÿ������֮���� AND ��ϵ��</p>
   * <p>6��������ؼ�¼������Ϊ-1�������ز�ѯ�����������ݡ�</p>
   * <p>7��������ؼ�¼����������0�������ز�ѯ���ķ�ҳ���ݡ�
   * ��ֹ�����ǣ�( selectIndex+1 , selectIndex+selectNumber )</p>
   * <p>8�������ѯ���Ϊ0����ѯ��¼�����Ϊ1����ѯ��¼��������
   * ����������SELECT_ALL_NUMBER��</p>
   *
   * @param dataSourceName DataSource������
   * @param tableName ��������ͼ������
   * @param al ��ѯSQL����
   * @param strStatus ��ѯSQL������޶�����
   * @param selectIndex ���ؼ�¼������
   * @param selectNumber ���ؼ�¼������
   * @param status ��ѯ��¼���ǲ�ѯ������־
   * @return ��ѯ��¼�б�
   * @throws RemoteException
   */
  public ArrayList selectSQL(String tableName,
                             ArrayList al, String strStatus, int selectIndex,
                             int selectNumber, int status) throws
      RemoteException {

    return this.selectSQL(tableName, " * ", al, strStatus,
                          selectIndex, selectNumber, status);

  }

  /**
   * ��ѯ��¼
   *
   * <p>������ʹ���������£�</p>
   * <p>1�������ڵ������ͼ��ѯ��</p>
   * <p>2�������ڼ������жϲ�ѯ��</p>
   * <p>3���б��е�ÿ������֮���� AND ��ϵ��</p>
   * <p>4���б��е�ÿһ��������֮���� OR ��ϵ��</p>
   * <p>5�������ж��޶��������б��е�ÿ������֮���� AND ��ϵ��</p>
   * <p>6��������ؼ�¼������Ϊ-1�������ز�ѯ�����������ݡ�</p>
   * <p>7��������ؼ�¼����������0�������ز�ѯ���ķ�ҳ���ݡ�
   * ��ҳ���ݵ���ֹ�����ǣ�( selectIndex+1 , selectIndex+selectNumber )</p>
   * <p>8�������ѯ���Ϊ0����ѯ��¼�����Ϊ1����ѯ��¼��������
   * ��¼������������SELECT_ALL_NUMBER��</p>
   *
   * @param dataSourceName DataSource������
   * @param tableName ��������ͼ������
   * @param strFields ָ����ѯ���ֶ�
   * @param al ��ѯSQL����
   * @param strStatus ��ѯSQL������޶�����
   * @param selectIndex ���ؼ�¼������
   * @param selectNumber ���ؼ�¼������
   * @param status ��ѯ��¼���ǲ�ѯ������־
   * @return ��ѯ��¼�б�
   * @throws RemoteException
   */
  public ArrayList selectSQL(String tableName,
                             String strFields, ArrayList al, String strStatus,
                             int selectIndex, int selectNumber, int status) throws
      RemoteException {

    StringBuffer sqlString = new StringBuffer("");

    if (status == 1) {
      //��ѯ��¼����
      sqlString.append(" select count(1) as SELECT_ALL_NUMBER from ");
      sqlString.append(tableName);
    }
    else if (selectNumber == -1) {
      //��ѯȫ����¼
      sqlString.append(" select ");
      sqlString.append(strFields);
      sqlString.append(" from ");
      sqlString.append(tableName);
    }
    else {
      //��ѯ��ҳָ����¼
      sqlString.append(" select ");
      sqlString.append(strFields);
      sqlString.append(" from ");
      sqlString.append(
          " ( select ROWNUM as tempRowNumIndex , tempTableName.*  from ");
      sqlString.append(tableName);
      sqlString.append(" tempTableName ");
    }

    //�ж��Ƿ�����޶�����
    int checkAllSize = 0;
    int addSize = 0;

    if (al != null) {
      checkAllSize = al.size();
      for (int i1 = 0; i1 < checkAllSize; i1++) {
        addSize += ((BaseObject)(al.get(i1))).getFieldCount();
      }
    }

    if (strStatus.compareTo("") != 0 || addSize > 0 ||
        (status != 1 && selectNumber != -1)) {
      //���ڲ�ѯ����ʱ
      sqlString.append(" where ");

    }

    //ѭ���ж��Ƿ�����޶�����
    //���޶������б��У������б�ֵBaseObjectȡ OR ���б�ֵ֮��ȡ AND
    //������( ... or ... ) and ( ... or ... ) and ( ... or ... )

    if (checkAllSize > 0) {

      for (int i = 0; i < checkAllSize; i++) {

        //ȡ���б�ֵBaseObject
        BaseObject obj = (BaseObject)(al.get(i));
        int objSize = obj.getFieldCount();

        //����趨����
        if (objSize > 0) {

          //�޶��б���֮����� AND ģʽ
          if (i > 0) {
            sqlString.append(" and ");
          }

          sqlString.append(" ( ");

          //ѭ���趨�б�ֵ��ȡ OR
          ArrayList nameAL = obj.getFieldName();
          ArrayList typeAL = obj.getFieldType();
          ArrayList valueAL = obj.getFieldValue();
          int typeIndex = 0;

          for (int j = 0; j < objSize; j++) {

            if (j > 0) {
              sqlString.append(" or ");
            }

            //�ж�����
            typeIndex = ((BaseDataType)(typeAL.get(j))).getDataType();
            switch (typeIndex) {
              case BaseDataType.INVALID:

                //������Ч����
                break;
              case BaseDataType.INTEGER:
                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" = ");
                sqlString.append(((Integer)(valueAL.get(j))).intValue());
                break;
              case BaseDataType.NUMBER:
                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" = ");
                sqlString.append(((BigDecimal)(valueAL.get(j))).toString());
                break;
              case BaseDataType.STRING:
                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" = '");
                sqlString.append((String)(valueAL.get(j)));
                sqlString.append("' ");
                break;
              case BaseDataType.DATE:

                //��ʽ��TO_DATE('2003-04-22','yyyy-MM-DD')
                Date tempDate = (Date)(valueAL.get(j));
                sqlString.append("TO_DATE(");
                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(",'");
                sqlString.append(PromosDate.SHORTFORMAT);
                sqlString.append("')");

                sqlString.append(" = ");
                sqlString.append("TO_DATE('");
                sqlString.append(PromosDate.toShortFormatString(tempDate));
                sqlString.append("','");
                sqlString.append(PromosDate.SHORTFORMAT);
                sqlString.append("')");

                break;

            }

          }

          sqlString.append(" ) ");
        }

      }
    }

    //������������
    int searchFirstIndex = selectIndex * selectNumber;

    //����ĩ������
    int searchLastIndex = searchFirstIndex + selectNumber;

    //��ѯ��ҳָ����¼
    if (status != 1 && selectNumber > -1) {

      //����Ƿ�����޶�����
      if (addSize > 0) {
        sqlString.append(" and ROWNUM <= ");
      }
      else {
        sqlString.append(" ROWNUM <= ");
      }

      sqlString.append(searchLastIndex);

      //�����϶����޶�����
      if (strStatus.compareTo("") != 0) {

        //���û�в�ѯ����
        sqlString.append(" and ( ");
        sqlString.append(strStatus);
        sqlString.append(" ) ");

      }

      sqlString.append(" ) where tempRowNumIndex > ");
      sqlString.append(searchFirstIndex);

    }
    else {

      //�����϶����޶�����
      if (strStatus.compareTo("") != 0) {

        //���û�в�ѯ����
        if (addSize > 0) {
          sqlString.append(" and ( ");
          sqlString.append(strStatus);
          sqlString.append(" ) ");
        }
        else {
          sqlString.append(strStatus);
        }

      }
    }

    //��ʾ��ѯ���
    String sql = sqlString.toString();
    BasePrint.println(" selectSQL : "+sql);

    return this.selectSQL(tableName, sqlString.toString());

  }

  /**
   * ��ѯ��¼
   *
   * <p>������ʹ���������£�</p>
   * <p>1�������ڵ������ͼ��ѯ��</p>
   * <p>2�������ڼ������жϲ�ѯ��</p>
   * <p>3���б��е�ÿ������֮���� AND ��ϵ��</p>
   * <p>5�������ж��޶��������б��е�ÿ������֮���� AND ��ϵ��</p>
   * <p>6��������ؼ�¼������Ϊ-1�������ز�ѯ�����������ݡ�</p>
   * <p>7��������ؼ�¼����������0�������ز�ѯ���ķ�ҳ���ݡ�
   * ��ֹ�����ǣ�( selectIndex+1 , selectIndex+selectNumber )</p>
   * <p>8�������ѯ���Ϊ0����ѯ��¼�����Ϊ1����ѯ��¼��������
   * ����������SELECT_ALL_NUMBER��</p>
   *
   * @param dataSourceName DataSource������
   * @param tableName ��������ͼ������
   * @param al ��ѯSQL����
   * @param strStatus ��ѯSQL������޶�����
   * @param strSort ��ѯSQL����������
   * @param selectIndex ���ؼ�¼������
   * @param selectNumber ���ؼ�¼������
   * @param status ��ѯ��¼���ǲ�ѯ������־
   * @return ��ѯ��¼�б�
   * @throws RemoteException
   */
  public ArrayList selectSQLEX(String tableName,
                               ArrayList al, String strStatus, String strSort,
                               int selectIndex, int selectNumber, int status) throws
      RemoteException {

    return this.selectSQLEX(tableName, " * ", al, strStatus,
                            strSort, selectIndex, selectNumber, status);

  }

  /**
   * ��ѯ��¼
   *
   * <p>������ʹ���������£�</p>
   * <p>1�������ڵ������ͼ��ѯ��</p>
   * <p>2�������ڼ������жϲ�ѯ��</p>
   * <p>3���б��е�ÿ������֮���� AND ��ϵ��</p>
   * <p>5�������ж��޶��������б��е�ÿ������֮���� AND ��ϵ��</p>
   * <p>6��������ؼ�¼������Ϊ-1�������ز�ѯ�����������ݡ�</p>
   * <p>7��������ؼ�¼����������0�������ز�ѯ���ķ�ҳ���ݡ�
   * ��ҳ���ݵ���ֹ�����ǣ�( selectIndex+1 , selectIndex+selectNumber )</p>
   * <p>8�������ѯ���Ϊ0����ѯ��¼�����Ϊ1����ѯ��¼��������
   * ��¼������������SELECT_ALL_NUMBER��</p>
   *
   * @param dataSourceName DataSource������
   * @param tableName ��������ͼ������
   * @param strFields ָ����ѯ���ֶ�
   * @param al ��ѯSQL����
   * @param strStatus ��ѯSQL������޶�����
   * @param strSort ��ѯSQL����������
   * @param selectIndex ���ؼ�¼������
   * @param selectNumber ���ؼ�¼������
   * @param status ��ѯ��¼���ǲ�ѯ������־
   * @return ��ѯ��¼�б�
   * @throws RemoteException
   */
  public ArrayList selectSQLEX(String tableName,
                               String strFields, ArrayList al, String strStatus,
                               String strSort, int selectIndex,
                               int selectNumber, int status) throws
      RemoteException {

    StringBuffer sqlString = new StringBuffer("");

    if (status == 1) {
      //��ѯ��¼����
      sqlString.append(" select count(1) as SELECT_ALL_NUMBER from ");
      sqlString.append(tableName);
    }
    else if (selectNumber == -1) {
      //��ѯȫ����¼
      sqlString.append(" select ");
      sqlString.append(strFields);
      sqlString.append(" from ");
      sqlString.append(tableName);
    }
    else {
      //��ѯ��ҳָ����¼
      sqlString.append(" select ");
      sqlString.append(strFields);
      sqlString.append(" from ");
      sqlString.append(
          " ( select ROWNUM as tempRowNumIndex , tempTableName.*  from ");
      sqlString.append(tableName);
      sqlString.append(" tempTableName ");
    }

    //�ж��Ƿ�����޶�����
    int checkAllSize = 0;
    int addSize = 0;

    if (al != null) {
      checkAllSize = al.size();
      for (int i1 = 0; i1 < checkAllSize; i1++) {
        addSize += ((BaseObject)(al.get(i1))).getFieldCount();
      }
    }

    if (strStatus.compareTo("") != 0 || addSize > 0 ||
        (status != 1 && selectNumber != -1)) {
      //���ڲ�ѯ����ʱ
      sqlString.append(" where ");

    }

    //ѭ���ж��Ƿ�����޶�����
    //���޶������б��У������б�ֵBaseObjectȡ BaseObject��1��ֵ ���б�ֵ֮��ȡ AND
    //������( ... (=/>/</>=/<=/<>/like) (or/and)  ... ) and ( ... )

    if (checkAllSize > 0) {

      for (int i = 0; i < checkAllSize; i++) {

        //ȡ���б�ֵBaseObject
        BaseObject obj = (BaseObject)(al.get(i));
        int objSize = obj.getFieldCount();

        //����趨����
        if (objSize > 0) {

          //�޶��б���֮����� AND ģʽ
          if (i > 0) {
            sqlString.append(" and ");
          }

          sqlString.append(" ( ");

          //ѭ���趨�б�ֵ��ȡ OR
          ArrayList nameAL = obj.getFieldName();
          ArrayList typeAL = obj.getFieldType();
          ArrayList valueAL = obj.getFieldValue();
          int typeIndex = 0;

          //����ֵ�ʹ���ģʽ
          for (int j = 0; j < objSize; j += 2) {

            //�ж�����
            typeIndex = ((BaseDataType)(typeAL.get(j))).getDataType();
            switch (typeIndex) {
              case BaseDataType.INVALID:

                //������Ч����
                break;
              case BaseDataType.INTEGER:

                if (j > 0) {
                  sqlString.append(" or ");
                }

                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                sqlString.append(" ");
                sqlString.append(((Integer)(valueAL.get(j))).intValue());
                break;
              case BaseDataType.NUMBER:

                if (j > 0) {
                  sqlString.append(" or ");
                }

                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                sqlString.append(" ");
                sqlString.append(((BigDecimal)(valueAL.get(j))).toString());
                break;
              case BaseDataType.STRING:

                if (j > 0) {
                  sqlString.append(" or ");
                }

                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                sqlString.append(" '");
                sqlString.append((String)(valueAL.get(j)));
                sqlString.append("' ");
                break;
              case BaseDataType.DATE:

                if (j > 0) {
                  sqlString.append(" and ");
                }

                //��ʽ��TO_DATE('2003-04-22','yyyy-MM-DD')
                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                Date tempDate = (Date)(valueAL.get(j));
                sqlString.append(" TO_DATE('");
                sqlString.append(PromosDate.toShortFormatString(tempDate));
                sqlString.append("','");
                sqlString.append(PromosDate.SHORTFORMAT);
                sqlString.append("')");

                break;

            }

          }

          sqlString.append(" ) ");
        }

      }
    }

    //������������
    int searchFirstIndex = selectIndex * selectNumber;

    //����ĩ������
    int searchLastIndex = searchFirstIndex + selectNumber;

    //��ѯ��ҳָ����¼
    if (status != 1 && selectNumber > -1) {

      //����Ƿ�����޶�����
      if (addSize > 0) {
        sqlString.append(" and ROWNUM <= ");
      }
      else {
        sqlString.append(" ROWNUM <= ");
      }

      sqlString.append(searchLastIndex);

      //�����϶����޶�����
      if (strStatus.compareTo("") != 0) {

        //����в�ѯ����
        sqlString.append(" and ( ");
        sqlString.append(strStatus);
        sqlString.append(" ) ");

      }

      //��������������
      if (strSort.compareTo("") != 0) {
        sqlString.append(" order by ");
        sqlString.append(strSort);
      }

      sqlString.append(" ) where tempRowNumIndex > ");
      sqlString.append(searchFirstIndex);

    }
    else {

      //�����϶����޶�����
      if (strStatus.compareTo("") != 0) {

        //���û�в�ѯ����
        if (addSize > 0) {
          sqlString.append(" and ( ");
          sqlString.append(strStatus);
          sqlString.append(" ) ");
        }
        else {
          sqlString.append(strStatus);
        }

      }

      //��������������
      if (strSort.compareTo("") != 0) {
        sqlString.append(" order by ");
        sqlString.append(strSort);
      }

    }

    //��ʾ��ѯ���
    String sql = sqlString.toString();
    BasePrint.print(" selectSQLEX : ", sql, true);

    return this.selectSQL(tableName, sqlString.toString());

  }

  /**
   * ��ѯ��¼,������е�����
   *
   * <p>������ʹ���������£�</p>
   * <p>1�������ڵ������ͼ��ѯ��</p>
   * <p>2�������ڼ������жϲ�ѯ��</p>
   * <p>3���б��е�ÿ������֮���� AND ��ϵ��</p>
   * <p>5�������ж��޶��������б��е�ÿ������֮���� AND ��ϵ��</p>
   * <p>6��������ؼ�¼������Ϊ-1�������ز�ѯ�����������ݡ�</p>
   * <p>7��������ؼ�¼����������0�������ز�ѯ���ķ�ҳ���ݡ�
   * ��ֹ�����ǣ�( selectIndex+1 , selectIndex+selectNumber )</p>
   * <p>8�������ѯ���Ϊ0����ѯ��¼�����Ϊ1����ѯ��¼��������
   * ����������SELECT_ALL_NUMBER��</p>
   *
   * @param dataSourceName DataSource������
   * @param tableName ��������ͼ������
   * @param al ��ѯSQL����
   * @param strStatus ��ѯSQL������޶�����
   * @param strSort ��ѯSQL����������
   * @param selectIndex ���ؼ�¼������
   * @param selectNumber ���ؼ�¼������
   * @param status ��ѯ��¼���ǲ�ѯ������־
   * @return ��ѯ��¼�б�
   * @throws RemoteException
   */
  public ArrayList selectSQLEXSortAll(String tableName,
                                      ArrayList al, String strStatus,
                                      String strSort, int selectIndex,
                                      int selectNumber, int status) throws
      RemoteException {

    return this.selectSQLEXSortAll(tableName, " * ", al,
                                   strStatus, strSort, selectIndex,
                                   selectNumber, status);

  }

  /**
   * ��ѯ��¼��������е����ݡ�
   *
   * <p>������ʹ���������£�</p>
   * <p>1�������ڵ������ͼ��ѯ��</p>
   * <p>2�������ڼ������жϲ�ѯ��</p>
   * <p>3���б��е�ÿ������֮���� AND ��ϵ��</p>
   * <p>5�������ж��޶��������б��е�ÿ������֮���� AND ��ϵ��</p>
   * <p>6��������ؼ�¼������Ϊ-1�������ز�ѯ�����������ݡ�</p>
   * <p>7��������ؼ�¼����������0�������ز�ѯ���ķ�ҳ���ݡ�
   * ��ֹ�����ǣ�( selectIndex+1 , selectIndex+selectNumber )</p>
   * <p>8�������ѯ���Ϊ0����ѯ��¼�����Ϊ1����ѯ��¼��������
   * ����������SELECT_ALL_NUMBER��</p>
   *
   * @param dataSourceName DataSource������
   * @param tableName ��������ͼ������
   * @param strFields ָ����ѯ���ֶ�
   * @param al ��ѯSQL����
   * @param strStatus ��ѯSQL������޶�����
   * @param strSort ��ѯSQL����������
   * @param selectIndex ���ؼ�¼������
   * @param selectNumber ���ؼ�¼������
   * @param status ��ѯ��¼���ǲ�ѯ������־��1��ѯ������-1��ѯ���У�0��ҳ��ѯ
   * @return ��ѯ��¼�б�
   * @throws RemoteException �쳣
   */
  public ArrayList selectSQLEXSortAll(String tableName,
                                      String strFields, ArrayList al,
                                      String strStatus, String strSort,
                                      int selectIndex, int selectNumber,
                                      int status) throws RemoteException {

    return this.selectSQLEXSortAll(tableName, strFields, al,
                                   strStatus, strSort, null, selectIndex,
                                   selectNumber, status);

  }

  /**
   * ��ѯ��¼��������е����ݡ�
   *
   * <p>������ʹ���������£�</p>
   * <p>1�������ڵ������ͼ��ѯ��</p>
   * <p>2�������ڼ������жϲ�ѯ��</p>
   * <p>3���б��е�ÿ������֮���� AND ��ϵ��</p>
   * <p>5�������ж��޶��������б��е�ÿ������֮���� AND ��ϵ��</p>
   * <p>6��������ؼ�¼������Ϊ-1�������ز�ѯ�����������ݡ�</p>
   * <p>7��������ؼ�¼����������0�������ز�ѯ���ķ�ҳ���ݡ�
   * ��ֹ�����ǣ�( selectIndex+1 , selectIndex+selectNumber )</p>
   * <p>8�������ѯ���Ϊ0����ѯ��¼�����Ϊ1����ѯ��¼��������
   * ����������SELECT_ALL_NUMBER��</p>
   *
   * @param dataSourceName DataSource������
   * @param tableName ��������ͼ������
   * @param strFields ָ����ѯ���ֶ�
   * @param al ��ѯSQL����
   * @param strStatus ��ѯSQL������޶�����
   * @param strSort ��ѯSQL����������
   * @param strGroupBy ��ѯSQL�ķ�������
   * @param selectIndex ���ؼ�¼������
   * @param selectNumber ���ؼ�¼������
   * @param status ��ѯ��¼���ǲ�ѯ������־��1��ѯ������-1��ѯ���У�0��ҳ��ѯ
   * @return ��ѯ��¼�б�
   * @throws RemoteException �쳣
   */
  public ArrayList selectSQLEXSortAll(String tableName,
                                      String strFields, ArrayList al,
                                      String strStatus, String strSort, String strGroupBy,
                                      int selectIndex, int selectNumber,
                                      int status) throws RemoteException {

    StringBuffer sqlString = new StringBuffer("");

    if (status == 1) {
      //��ѯ��¼����
      sqlString.append(" select count(1) as SELECT_ALL_NUMBER from ");
      sqlString.append(tableName);
    }
    else if (selectNumber == -1) {
      //��ѯȫ����¼
      sqlString.append(" select ");
      sqlString.append(strFields);
      sqlString.append(" from ");
      sqlString.append(tableName);
    }
    else {
      //��ѯ��ҳָ����¼
      sqlString.append(" select ");
      sqlString.append(strFields);
      sqlString.append(" from ");
      sqlString.append(tableName);
    }

    //�ж��Ƿ�����޶�����
    boolean isHaveAl = true;
    boolean isHaveStatus = true;
    if (al == null || al.size() == 0) {
      isHaveAl = false;
    }
    if (strStatus == null || strStatus.trim().equals("")) {
      isHaveStatus = false;
    }

    //������������������where
    if (isHaveAl || isHaveStatus) {
      sqlString.append(" where ");
    }

    //�����������
    if (isHaveStatus) {
      //sqlString.append(" ( ");
      sqlString.append(strStatus);
      //sqlString.append(" ) ");
    }

    //����������б����������and
    if (isHaveStatus && isHaveAl) {
      sqlString.append(" and ");
    }

    //����б�����
    if (isHaveAl) {
      //sqlString.append(" ( ");

      //ѭ���ж��Ƿ�����޶�����
      //���޶������б��У������б�ֵBaseObjectȡ BaseObject��1��ֵ ���б�ֵ֮��ȡ AND
      //������( ... (=/>/</>=/<=/<>/like) (or/and)  ... ) and ( ... )

      int checkAllSize = al.size();
      for (int i = 0; i < checkAllSize; i++) {

        //ȡ���б�ֵBaseObject
        BaseObject obj = (BaseObject)(al.get(i));
        int objSize = obj.getFieldCount();

        //����趨����
        if (objSize > 0) {

          //�޶��б���֮����� AND ģʽ
          if (i > 0) {
            sqlString.append(" and ");
          }

          if (checkAllSize > 1) {
            sqlString.append(" ( ");
          }

          //ѭ���趨�б�ֵ��ȡ OR
          ArrayList nameAL = obj.getFieldName();
          ArrayList typeAL = obj.getFieldType();
          ArrayList valueAL = obj.getFieldValue();
          int typeIndex = 0;

          //����ֵ�ʹ���ģʽ
          for (int j = 0; j < objSize; j += 2) {

            //�ж�����
            typeIndex = ((BaseDataType)(typeAL.get(j))).getDataType();
            switch (typeIndex) {
              case BaseDataType.INVALID:

                //������Ч����
                break;
              case BaseDataType.INTEGER:

                if (j > 0) {
                  sqlString.append(" or ");
                }

                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                sqlString.append(" ");
                sqlString.append(((Integer)(valueAL.get(j))).intValue());
                break;
              case BaseDataType.NUMBER:

                if (j > 0) {
                  sqlString.append(" or ");
                }

                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                sqlString.append(" ");
                sqlString.append(((BigDecimal)(valueAL.get(j))).toString());
                break;
              case BaseDataType.STRING:

                if (j > 0) {
                  sqlString.append(" or ");
                }

                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                sqlString.append(" '");
                sqlString.append((String)(valueAL.get(j)));
                sqlString.append("' ");
                break;
              case BaseDataType.DATE:

                if (j > 0) {
                  sqlString.append(" and ");
                }

                //��ʽ��TO_DATE('2003-04-22','yyyy-MM-DD')
                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                Date tempDate = (Date)(valueAL.get(j));
                sqlString.append(" TO_DATE('");
                sqlString.append(PromosDate.toShortFormatString(tempDate));
                sqlString.append("','");
                sqlString.append(PromosDate.SHORTFORMAT);
                sqlString.append("')");

                break;

            }

          }

          if (checkAllSize > 1) {
            sqlString.append(" ) ");
          }
        }

      }

      //sqlString.append(" ) ");
    }

    //�������
    if (strGroupBy != null && !strGroupBy.equals("")) {
      sqlString.append(" group by ");
      sqlString.append(strGroupBy);
      sqlString.append(" ");
    }

    //�������
    if (strSort != null && !strSort.equals("")) {
      sqlString.append(" order by ");
      sqlString.append(strSort);
      sqlString.append(" ");
    }

    //��ʾ��ѯ���
    String sql = sqlString.toString();
    //�����Ҫ��ҳ
    if (status == 0) {
      sqlString = new StringBuffer("");
      sqlString.append(
          " select * from ( select ROWNUM as TEMP_ROWNUM, TEMP_TABLE.* from (  ");
      sqlString.append(sql);
      sqlString.append(") TEMP_TABLE  where rownum < ");
      sqlString.append((selectIndex + 1) * selectNumber + 1);
      sqlString.append(") where TEMP_ROWNUM >= ");
      sqlString.append(selectIndex * selectNumber + 1);
      sql = sqlString.toString();
    }

    BasePrint.print(" selectSQLEXSortAll : ", sql, true);

    return this.selectSQL(tableName, sqlString.toString());

  }

  /**
   * ��ѯ��¼
   *
   * @param dataSourceName DataSource������
   * @param tableName ��������ͼ������
   * @param sqlString ��ѯSQL���
   * @return ��ѯ��¼�б�
   * @throws RemoteException
   */
  public ArrayList selectSQL(String tableName,
                             String sqlString) throws RemoteException {

    //��ѯ�������ݣ�����Ĭ��
    ArrayList rAL = new ArrayList();

    //���
    if (dataSourceName == null) {
      return rAL;
    }
    if (tableName == null) {
      return rAL;
    }

    if ((tableName.indexOf(".")<0)&&(tableName.equals("")==false)&&(tableName!=null)&&(tableName.indexOf("select")<0)&&(tableName.indexOf("SELECT")<0))
	{
    	tableName=DB_USER+"."+tableName;
	}
    
    if (sqlString == null) {
      return rAL;
    }

    //�õ�ָ������
    BaseObject obj = null;
    Connection con = null;
    Statement stmt = null;
    ResultSet sqlrs = null;

    //����ֶ�����
    int fieldCount = 0;

    int i = 0;
    int fieldType = 0;
    String fieldAlias = "";

    //��ʾSQL���
    BasePrint.println(" selectSQL : "+sqlString);

    try {

      //�õ�����
      con = this.getConnection();
      stmt = con.createStatement();

      //��ѯָ��������
      sqlrs = stmt.executeQuery(sqlString);

      //�õ������ͼ�Ľṹ
      obj = this.getTableByResultSet(sqlrs);

      //��ӱ����ͼ����
      obj.setTableName(tableName);

      //�õ�����ֶ�����
      fieldCount = obj.getFieldCount();
      while (sqlrs.next()) {

        //ѭ���õ��ֶ�ֵ
        ArrayList valueAL = new ArrayList();

       for (i = 0; i < fieldCount; i++) {

          //�õ��ֶα���
          fieldAlias = (String)(obj.getFieldAlias().get(i));
          
 
          //�õ��ֶ�����
          fieldType = ((BaseDataType)(obj.getFieldType().get(i))).getDataType();

          //�����ֶ����͵õ��ֶ�ֵ
          switch (fieldType) {

            //����ǣ�INTEGER
            case BaseDataType.INTEGER:
              valueAL.add(new Integer(sqlrs.getInt(fieldAlias)));
              break;

              //����ǣ�NUMBER
            case BaseDataType.NUMBER:
              valueAL.add(sqlrs.getBigDecimal(fieldAlias));
              break;

              //����ǣ�STRING
            case BaseDataType.STRING:

              //�����NULL,����""
              String tempFRS = sqlrs.getString(fieldAlias);
             if (tempFRS == null) {
                valueAL.add(new String(""));
              }
              else {
                valueAL.add(new String(tempFRS.trim()));
              }

              break;
              //����ǣ�BLOB
            case BaseDataType.BLOB:
              Blob blob = sqlrs.getBlob(fieldAlias);
              java.io.InputStream tempobj1 = blob.getBinaryStream();
              java.io.BufferedInputStream is = new java.io.BufferedInputStream(tempobj1);
              try {
                byte[] byte1 = new byte[10];
                int nodata = is.read(byte1, 0, 10);
                if (nodata == -1) {
                  valueAL.add(null);
                }
                else {
                  valueAL.add("not null");
                }
              }
              catch (Exception e) {}

              break;

            case BaseDataType.LONG:

              //�����NULL,����""
              tempFRS = sqlrs.getString(fieldAlias);
              //BasePrint.println(tempFRS);
              /*
                    InputStream in = sqlrs.getBinaryStream(fieldAlias);
                    BufferedReader buf = new BufferedReader(new InputStreamReader(in));
                    BasePrint.println("****"+buf.readLine());
                   in.close();
               */
              if (tempFRS == null) {
                valueAL.add(new String(""));
              }
              else {
                //BasePrint.println(fieldAlias);
                valueAL.add(new String(tempFRS.trim()));
              }

              break;

              //����ǣ�STREAM
            case BaseDataType.STREAM:
              java.io.InputStream tempobj = sqlrs.getBinaryStream(fieldAlias);
              java.io.BufferedInputStream io = new java.io.BufferedInputStream(tempobj);
              try {
                byte[] byte1 = new byte[10];
                int nodata = io.read(byte1, 0, 10);
                if (nodata == -1) {
                  valueAL.add(null);
                }
                else {
                  valueAL.add("not null");
                }
              }
              catch (Exception e) {}

              break;
              //����ǣ�CLOB
            case BaseDataType.CHAR_STREAM:
            	Clob clob = sqlrs.getClob(fieldAlias);
            	if (clob!=null){
            		BufferedReader reader = null;
            		StringBuffer buffer = new StringBuffer();
            		try {
            			reader = new BufferedReader(clob.getCharacterStream());
            			String line;
            			while ((line = reader.readLine()) != null) {
            				buffer.append(line);
            			}
            		}
            		catch (Exception e) {
            			e.printStackTrace();
            		}
            		finally {
            			try {
            				if (reader != null) {
            					reader.close();
            				}
            			}
            			catch (Exception e) {
            				e.printStackTrace();
            			}
            		}
            		valueAL.add(buffer.toString());
            	}
            	else
            	{
                    valueAL.add(null);
            	}
              break;

              //����ǣ�DATE
            case BaseDataType.DATE:

              //��ȡʱ��
              //�����NULL������Ĭ��ֵ
              Timestamp tempTimestamp = sqlrs.getTimestamp(fieldAlias);
              Date tempDate = null;
              if (tempTimestamp != null) {
                tempDate = new Date(tempTimestamp.getTime());
              }
              valueAL.add(tempDate);
              //BasePrint.println(valueAL.size()-1);
              //valueAL.add(sqlrs.getDate(fieldAlias));
              break;

              //����ǣ�DATETIME
            case BaseDataType.DATETIME:

              //��ȡʱ��
              //�����NULL������Ĭ��ֵ
              Timestamp tempTime = sqlrs.getTimestamp(fieldAlias);
              Date tempDateTime = null;
              if (tempTime != null) {
            	  tempDateTime = new Date(tempTime.getTime());
              }
              valueAL.add(tempDateTime);
              //BasePrint.println(valueAL.size()-1);
              //valueAL.add(sqlrs.getDate(fieldAlias));
              break;
              
            default:
              valueAL.add(new String("")); ;

          }

          //����
          fieldAlias = new String("");

        }

        //�趨�ֶ�ֵ
        obj.setFieldValue(valueAL);

        //����ֶ�ֵ�б�
        valueAL = new ArrayList();

        //����
        fieldAlias = new String("");

        //δ����������ֱ������ֶ�ֵ
        rAL.add(obj);

        //�����µ�������
        obj = BaseObject.clone(obj);

      }

    }
    catch (Exception e) {
      if (e.getClass().isInstance(new SQLException())) {

        //BasePrint.println("���ݿ����--���룺"+( (SQLException) e).getErrorCode());

        e.printStackTrace();
        throw new BaseException("", ((SQLException)e).getErrorCode());
      }
      else {
        e.printStackTrace();
      }

    }
    finally {

      if (sqlrs != null) {
        try {
          sqlrs.close();
        }
        catch (SQLException ignore) {
        }
      }

      if (stmt != null) {
        try {
          stmt.close();
        }
        catch (SQLException ignore) {
        }
      }

      if (con != null) {
        try {
          con.close();
        }
        catch (SQLException ignore) {
        }
      }

    }

    return rAL;

  }

  /**
   * ���ָ�����еļ�¼
   *
   * @param dataSourceName ����Դ������
   * @param updateTableName ����Ӽ�¼�ı������
   * @param obj ��ӵļ�¼
   * @param sequenceFieldName ���ָ������ֵ���ֶ�����
   * @param sequenceName ָ������������
   * @return �����ӳɹ�������True�����򷵻�False
   * @throws RemoteException
   */
  public boolean insertSQLBySequence(
                                     String updateTableName, BaseObject obj,
                                     String sequenceFieldName,
                                     String sequenceName) throws
      RemoteException {

    //���
    if (dataSourceName == null) {
      return false;
    }
    if (updateTableName == null) {
      return false;
    }
    if (obj == null) {
      return false;
    }
    if (sequenceFieldName == null) {
      return false;
    }
    if (sequenceName == null) {
      return false;
    }

    //��Ӽ�¼
    //��Ӹ�ʽ��insert into test (ID,NAME,PID) values (SEQ.nextval,'test',0)
    StringBuffer strSQL = new StringBuffer();
    strSQL.append(" insert into ");
    strSQL.append(updateTableName);

    int fieldCount = obj.getFieldCount();
    int lastIndex = fieldCount - 1;
    ArrayList fieldNameAL = obj.getFieldName();
    ArrayList fieldTypeAL = obj.getFieldType();
    int fieldType = 0;
    ArrayList fieldValueAL = obj.getFieldValue();

    int i = 0;

    //����Ƿ�ָ��������
    boolean isSequence = false;
    if (sequenceFieldName.compareTo("") == 0) {
      isSequence = false;
    }
    else {
      isSequence = true;
    }
    int sequenceField = -1;
    String fieldName = "";

    //����ֶ�����
    strSQL.append(" ( ");
    for (i = 0; i < fieldCount; i++) {
      fieldType = ((BaseDataType)(fieldTypeAL.get(i))).getDataType();
      //����ֶ���
      fieldName = (String)(fieldNameAL.get(i));
      strSQL.append(fieldName);

      //���ָ��������
      if (isSequence) {

        //��鵱ǰ�ֶ��Ƿ���ָ���������ֶ�
        if (fieldName.compareTo(sequenceFieldName) == 0) {
          sequenceField = i;
          isSequence = false;
        }
      }

      //��ӷָ���
      if (i >= 0 && i < lastIndex) {
        strSQL.append(" , ");
      }
    }
    strSQL.append(" ) ");

    //����ֶ�ֵ
    strSQL.append(" values ( ");
    for (i = 0; i < fieldCount; i++) {

      //�����ָ���������ֶ�
      if (sequenceField == i) {
        //������к�ֵ
        strSQL.append(sequenceName);
        strSQL.append(".nextval");
      }
      else {

        //����ֶ����ͣ�����ֶ�ֵ
        //�����ֶ����͵õ��ֶ�ֵ
        fieldType = ((BaseDataType)(fieldTypeAL.get(i))).getDataType();
        switch (fieldType) {

          //����ǣ�INVALID
          case BaseDataType.INVALID:
            break;

            //����ǣ�INTEGER
          case BaseDataType.INTEGER:
            if (fieldValueAL.get(i) != null) {
              strSQL.append(((Integer)(fieldValueAL.get(i))).intValue());
            }
            else {
              strSQL.append("null");
            }
            break;

            //����ǣ�NUMBER
          case BaseDataType.NUMBER:
            if (fieldValueAL.get(i) != null) {
              strSQL.append(((BigDecimal)(fieldValueAL.get(i))).toString());
            }
            else {
              strSQL.append("null");
            }
            break;

            //����ǣ�STRING
          case BaseDataType.STRING:
            strSQL.append("'");
            String strString = (String)(fieldValueAL.get(i));
            strSQL.append(FormatTools.getOracleString(strString));
            strSQL.append("'");
            break;

            //����ǣ�DATE
          case BaseDataType.DATE:

            //��ʽ��TO_DATE('2003-04-22 21:30:25','yyyy-MM-DD hh24:mi:ss')
            Object object2 = fieldValueAL.get(i);
            if (object2 != null) {
              Date tempDate = (Date)(fieldValueAL.get(i));
              strSQL.append("TO_DATE('");
              strSQL.append(PromosDate.toDefaultString(tempDate));
              strSQL.append("','");
              strSQL.append(PromosDate.toDefaultFormat(tempDate));
              strSQL.append("')");
            }
            else {
              strSQL.append(" null ");
            }

            //BasePrint.println("��ʱ��ɵ�sql�����:"+strSQL);
            break;
          case BaseDataType.CHAR_STREAM:
            strSQL.append("empty_clob()");
            break;

        } //END SWITCH

      }

      //��ӷָ���
      if (i >= 0 && i < lastIndex) {
        strSQL.append(" , ");
      }

    }

    strSQL.append(" ) ");

    return executeSQL(strSQL.toString());

  }

  /**
   * ��Ӽ�¼
   *
   * @param dataSourceName ����Դ������
   * @param updateTableName ����Ӽ�¼�ı������
   * @param obj ��ӵļ�¼
   * @return ����ɹ���Ӽ�¼������True�����򷵻�False
   * @throws RemoteException
   */
  public boolean insertSQL(String updateTableName,
                           BaseObject obj) throws RemoteException {

    return insertSQLBySequence(updateTableName, obj, "", "");

  }

  /**
   * ��Ӽ�¼
   *
   * @param dataSourceName ����Դ������
   * @param updateTableName ����Ӽ�¼�ı������
   * @param obj ��ӵļ�¼
   * @param str ������ӵļ�¼��������õ�����
   * @return ����ɹ���Ӽ�¼������True�����򷵻�False
   * @throws RemoteException
   */
  public boolean insertSQLWithClob(
                                   String updateTableName, BaseObject obj,
                                   String strWhere) throws RemoteException {

    insertSQLBySequence(updateTableName, obj, "", "");

    int fieldCount = obj.getFieldCount();
    ArrayList fieldNameAL = obj.getFieldName();
    ArrayList fieldTypeAL = obj.getFieldType();
    int fieldType = 0;
    ArrayList fieldValueAL = obj.getFieldValue();

    for (int i = 0; i < fieldCount; i++) {
      fieldType = ((BaseDataType)(fieldTypeAL.get(i))).getDataType();
      if (fieldType == BaseDataType.CHAR_STREAM) {
        String fieldName = (String)(fieldNameAL.get(i));
        String sql = "select " + fieldName + " from " + updateTableName +
            " where 1=1";
        if (strWhere != null && strWhere.trim().length() > 0) {
          sql = sql + " and " + strWhere + " for update";
        }
        try {
          updateClob(sql, (String)fieldValueAL.get(i));
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }

    }

    return true;

  }

  /**
   * ɾ����¼
   *
   * @param dataSourceName ����Դ������
   * @param updateTableName ��ɾ����¼�ı������
   * @param sqlString ɾ����¼���޶�����
   * @return ����ɹ�ɾ����¼������True�����򷵻�False
   * @throws RemoteException
   */
  public boolean deleteSQL( String updateTableName,
                           String sqlString) throws RemoteException {

    //���
    if (dataSourceName == null) {
      return false;
    }
    if (updateTableName == null) {
      return false;
    }
    if (sqlString == null) {
      return false;
    }

    //ɾ��ָ���ļ�¼
    //ɾ����ʽ��delete from test where ID=100
    StringBuffer strSQL = new StringBuffer();
    strSQL.append(" delete from ");
    strSQL.append(updateTableName);
    strSQL.append(" where ");
    strSQL.append(sqlString);

    return executeSQL(strSQL.toString());
  }

  /**
   * ���¼�¼
   *
   * @param dataSourceName ����Դ������
   * @param updateTableName �����¼�¼�ı������
   * @param obj ��Ҫ���µļ�¼
   * @param sqlString ���¼�¼���޶�����
   * @return ����ɹ����¼�¼������True�����򷵻�False
   * @throws RemoteException �쳣
   */
  public boolean updateSQL(String updateTableName,
                           BaseObject obj, String sqlString) throws
      RemoteException {
    //���
    if (dataSourceName == null) {
      return false;
    }
    if (updateTableName == null) {
      return false;
    }
    if (obj == null) {
      return false;
    }
    if (sqlString == null) {
      return false;
    }

    //����ָ���ļ�¼
    //���¸�ʽ��update test set ID=200,Name='mofidy' where ID=100 and PID=0
    StringBuffer strSQL = new StringBuffer();
    strSQL.append(" update ");
    strSQL.append(updateTableName);
    strSQL.append(" set ");

    int fieldCount = obj.getFieldCount();
    ArrayList fieldNameAL = obj.getFieldName();
    ArrayList fieldTypeAL = obj.getFieldType();
    int fieldType = 0;
    ArrayList fieldValueAL = obj.getFieldValue();

    int i = 0;
    //��Ӹ��ļ�¼
    for (i = 0; i < fieldCount; i++) {

      //����ֶ���
      fieldType = ((BaseDataType)(fieldTypeAL.get(i))).getDataType();

      if (fieldType != BaseDataType.CHAR_STREAM &&
          fieldType != BaseDataType.STREAM) {
        strSQL.append((String)(fieldNameAL.get(i)));
        strSQL.append(" = ");
        //����ֶ����ͣ�����ֶ�ֵ
        //�����ֶ����͵õ��ֶ�ֵ
        switch (fieldType) {
          //����ǣ�INVALID
          case BaseDataType.INVALID:
            break;
            //����ǣ�INTEGER
          case BaseDataType.INTEGER:
            if (fieldValueAL.get(i) != null) {
              strSQL.append(((Integer)(fieldValueAL.get(i))).intValue());
            }
            else {
              strSQL.append("null");
            }
            break;
            //����ǣ�NUMBER
          case BaseDataType.NUMBER:
            if (fieldValueAL.get(i) != null) {
              strSQL.append(((BigDecimal)(fieldValueAL.get(i))).toString());
            }
            else {
              strSQL.append("null");
            }
            break;
            //����ǣ�STRING
          case BaseDataType.STRING:
            strSQL.append("'");
            String strString = (String)(fieldValueAL.get(i));
            strSQL.append(FormatTools.getOracleString(strString));
            strSQL.append("'");
            break;
            //����ǣ�DATE
          case BaseDataType.DATE:

            //��ʽ��TO_DATE('2003-04-22 21:30:25','yyyy-MM-DD hh24:mi:ss')
            Date tempDate = (Date)(fieldValueAL.get(i));
            strSQL.append("TO_DATE('");
            strSQL.append(PromosDate.toDefaultString(tempDate));
            strSQL.append("','");
            strSQL.append(PromosDate.toDefaultFormat(tempDate));
            strSQL.append("')");

            break;
        }
        //��ӷָ���

        boolean hasNextField = false;
        for (int j = i + 1; j < fieldCount; j++) {
          int nextFieldType = ((BaseDataType)(fieldTypeAL.get(j))).
              getDataType();
          if (nextFieldType != BaseDataType.CHAR_STREAM &&
              nextFieldType != BaseDataType.STREAM) {
            hasNextField = true;
            break;
          }
        }
        if (hasNextField) {
          //BasePrint.println(fieldType + "   " + (String) (fieldNameAL.get(i)));
          strSQL.append(" , ");
        }
      }

    }

    strSQL.append(" where ");
    strSQL.append(sqlString);

    executeSQL(strSQL.toString());

    for (i = 0; i < fieldCount; i++) {
      fieldType = ((BaseDataType)(fieldTypeAL.get(i))).getDataType();
      if (fieldType == BaseDataType.CHAR_STREAM) {
        String fieldName = (String)(fieldNameAL.get(i));
        String sql = "select " + fieldName + " from " + updateTableName +
            " where " + sqlString + " for update";
        try {
          updateClob(sql, (String)fieldValueAL.get(i));
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }

    }

    return true;
  }

  /**
   * ������
   *
   * @param dataSourceName ����Դ������
   * @param updateTableName �������ı������
   * @param sqlString �������ı�Ľṹ
   * @return ����ɹ�����������True�����򷵻�False
   * @throws RemoteException
   */
  public boolean createSQL(String updateTableName,
                           String sqlString) throws RemoteException {

    //���
    if (dataSourceName == null) {
      return false;
    }
    if (updateTableName == null) {
      return false;
    }
    if (sqlString == null) {
      return false;
    }

    //�½���
    //�½���ʽ��create table test ( ID number(20), NAME varchar(50), PID number(20))
    StringBuffer strSQL = new StringBuffer();
    strSQL.append(" create table ");
    strSQL.append(updateTableName);
    strSQL.append(" (");
    strSQL.append(sqlString);
    strSQL.append(") ");

    return executeSQL(strSQL.toString());

  }

  /**
   * ɾ����
   *
   * @param dataSourceName ����Դ������
   * @param updateTableName ��ɾ���ı������
   * @return ����ɹ�ɾ��������True�����򷵻�False
   * @throws RemoteException
   */
  public boolean dropSQL(String updateTableName) throws
      RemoteException {

    //���
    if (dataSourceName == null) {
      return false;
    }
    if (updateTableName == null) {
      return false;
    }

    //ɾ����
    //ɾ����ʽ��drop table test
    StringBuffer strSQL = new StringBuffer();
    strSQL.append(" drop table ");
    strSQL.append(updateTableName);

    return executeSQL(strSQL.toString());

  }

  /**
   * ��ӡ�ɾ�������¼�¼
   *
   * @param dataSourceName DataSource������
   * @param sqlString ��ӡ�ɾ�������µ�SQL���
   * @return ��������ɹ�������True,���򷵻�False
   * @throws RemoteException
   */
  public boolean executeSQL(String sqlString) throws
      RemoteException {

    boolean rs = false;
    Connection con = null;
    Statement stmt = null;
    //��ʾSQL���
    BasePrint.print(" executeSQL : ", sqlString, true);

    try {

      //�õ�����
      con = this.getConnection();
      stmt = con.createStatement();

      //���SQL���ִ�гɹ�������TRUE
      int resule=stmt.executeUpdate(sqlString);

      //System.out.println(" resule2: "+resule);
      
      if (resule>=0) {
        rs = true;
      }
      else {
        rs = false;
      }

    }
    catch (Exception e) {
      if (e.getClass().isInstance(new SQLException())) {

        //BasePrint.println("���ݿ����--���룺"+( (SQLException) e).getErrorCode());

        e.printStackTrace();
        throw new BaseException("", ((SQLException)e).getErrorCode());
      }
      else {
        e.printStackTrace();
      }
    }
    finally {

      if (stmt != null) {
        try {
          stmt.close();
        }
        catch (SQLException ignore) {
        }
      }

      if (con != null) {
        try {
          con.close();
        }
        catch (SQLException ignore) {
        }
      }

    }

    return rs;
  }

  /**
   * ִ��Begin-end���
   *
   * @param dataSourceName DataSource������
   * @param sqlString Begin-end���
   * @return ��������ɹ�������True,���򷵻�False
   * @throws RemoteException
   */
  public boolean executeBeginEnd(String sqlString) throws
      RemoteException {

    boolean rs = false;
    Connection con = null;
    Statement stmt = null;
    //��ʾSQL���
    BasePrint.print(" executeBeginEnd : ", sqlString, true);

    try {

      //�õ�����
      con = this.getConnection();
      stmt = con.createStatement();

      //���SQL���ִ�гɹ�������TRUE
      stmt.execute(sqlString);

      return true;

    }
    catch (Exception e) {
      if (e.getClass().isInstance(new SQLException())) {

        //BasePrint.println("���ݿ����--���룺"+( (SQLException) e).getErrorCode());

        //e.printStackTrace();
        throw new BaseException("", ((SQLException)e).getErrorCode());
      }
      else {
        e.printStackTrace();
      }
    }
    finally {

      if (stmt != null) {
        try {
          stmt.close();
        }
        catch (SQLException ignore) {
        }
      }

      if (con != null) {
        try {
          con.close();
        }
        catch (SQLException ignore) {
        }
      }

    }

    return rs;
  }

  /**
   * ��ѯ��¼����
   *
   * @param dataSourceName DataSource������
   * @param tableName ��������ͼ������
   * @param sqlString ��ѯSQL���
   * @return ��¼����
   * @throws RemoteException
   */
  public int getCount(String tableName, String sqlString) throws
      RemoteException {

    BasePrint.print(" getCount : ", sqlString, true);
    Connection con = null;
    Statement stmt = null;
    ResultSet sqlrs = null;

    try {

      //�õ�����
      con = this.getConnection();
      stmt = con.createStatement();

      //��ѯָ��������
      sqlrs = stmt.executeQuery(sqlString);

      if (sqlrs.next()) {
        return sqlrs.getInt(1);
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {

      if (sqlrs != null) {
        try {
          sqlrs.close();
        }
        catch (Exception ignore) {
        }
      }

      if (stmt != null) {
        try {
          stmt.close();
        }
        catch (Exception ignore) {
        }
      }

      if (con != null) {
        try {
          con.close();
        }
        catch (Exception ignore) {
        }
      }

    }

    return 0;

  }

  /**
   * �Զ���ȡ��һ�����к�
   *
   * @param dataSourceName DataSource������
   * @param seqName ���к�����
   * @return ��һ�����к�
   * @throws RemoteException
   */
  public long getSeqNextValue(String seqName) throws
      RemoteException {

    Connection con = null;
    Statement stmt = null;
    ResultSet sqlrs = null;

    try {

      //�õ�����
      con = this.getConnection();
      stmt = con.createStatement();

      //��ѯָ��������
      sqlrs = stmt.executeQuery("select " + seqName + ".nextVal from dual");

      if (sqlrs.next()) {
        return sqlrs.getLong(1);
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {

      if (sqlrs != null) {
        try {
          sqlrs.close();
        }
        catch (Exception ignore) {
        }
      }

      if (stmt != null) {
        try {
          stmt.close();
        }
        catch (Exception ignore) {
        }
      }

      if (con != null) {
        try {
          con.close();
        }
        catch (Exception ignore) {
        }
      }

    }

    return -1;

  }

  /**
   * ִ��һ��SQL���
   *
   * @param dataSourceName DataSource������
   * @param arrayList SQL����б�
   * @return ����ִ�н��
   * @throws RemoteException
   */
  public boolean executeBatch(ArrayList arrayList) throws
      RemoteException {

    boolean bol=false;

    Connection con = null;
    Statement st = null;

    try {
      //�õ�����
      con = this.getConnection();
      st = con.createStatement();

      st.addBatch((String)arrayList.get(0));
      for (int i = 1; i < arrayList.size(); i++) {
        st.addBatch((String)arrayList.get(i));
      }

      st.executeBatch();
      bol=true;
    }
    catch (Exception e) {
      if (e.getClass().isInstance(new SQLException())) {

        //BasePrint.println("���ݿ����--���룺"+( (SQLException) e).getErrorCode());

        e.printStackTrace();
        throw new BaseException("", ((SQLException)e).getErrorCode());
      }
      else {
        e.printStackTrace();
      }

    }
    finally {
      if (st != null) {
        try {
          st.close();
        }
        catch (SQLException e) {
        }
      }
      if (con != null) {
        try {
          con.close();
        }
        catch (SQLException e) {
        }
      }

    }

    return bol;
  }

  /**
   * ���´��ַ����ֶ�
   *
   * @param dataSourceName DataSource������
   * @param sql
   * @param text
   * @return ����ִ�н��
   * @throws RemoteException
   */
  public boolean updateClob(String sql, String text) throws
      RemoteException {
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;

    boolean defaultCommit = true;
    BasePrint.print(" updateClob : ", sql, true);
    try {
      //�õ�����
      con = this.getConnection();
      defaultCommit = con.getAutoCommit();
      con.setAutoCommit(false);

      st = con.createStatement();
      rs = st.executeQuery(sql);

      if (rs.next()) {
        Object obj = rs.getClob(1);
        BufferedWriter writer = null;
        try {
          if (obj instanceof oracle.sql.CLOB) {
            BasePrint.println("clob is oracle.sql.CLOB");
            oracle.sql.CLOB clob = (oracle.sql.CLOB)obj;
            writer = new BufferedWriter(clob.getCharacterOutputStream());
          }
          writer.write(text, 0, text.length());
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        finally {
          writer.close();
        }
      }
      con.commit();
    }
    catch (Exception e) {
      try {
        con.rollback();
      }
      catch (Exception e1) {
      }
      ;
      e.printStackTrace();
    }
    finally {
      if (rs != null) {
        try {
          rs.close();
        }
        catch (Exception e) {
        }
      }
      if (st != null) {
        try {
          st.close();
        }
        catch (Exception e) {
        }
      }
      if (con != null) {
        try {
          con.setAutoCommit(defaultCommit);
          con.close();
        }
        catch (Exception e) {
        }
      }
    }

    return true;
  }

  /**
   * ִ��SQLȡ���ַ���
   *
   * @param dataSourceName DataSource������
   * @param sql
   * @return ����ִ���ַ���
   * @throws RemoteException
   */
  public String getValue(String sql) throws
      RemoteException {
    ArrayList list = this.selectSQL("", sql);
    

    if (list.size() == 0) {
      return null;
    }
    BaseObject baseObj = (BaseObject)list.get(0);
    if (baseObj != null) {
      ArrayList value = baseObj.getFieldValue();
      return value.get(0).toString();
    }

    return null;

  }

  /**
   * ��ѯ��¼
   *
   * @param Connection ���ݿ�����
   * @param tableName ��������ͼ������
   * @param sqlString ��ѯSQL���
   * @return ��ѯ��¼�б�
   * @throws RemoteException
   */
  public ArrayList selectSQL(Connection con, String tableName,
                             String sqlString) throws RemoteException {

    //��ѯ�������ݣ�����Ĭ��
    ArrayList rAL = new ArrayList();

    //���
    if (con == null) {
      return rAL;
    }
    if (tableName == null) {
      return rAL;
    }
    if (sqlString == null) {
      return rAL;
    }

    //�õ�ָ������
    BaseObject obj = null;
    Statement stmt = null;
    ResultSet sqlrs = null;

    //����ֶ�����
    int fieldCount = 0;

    int i = 0;
    int fieldType = 0;
    String fieldAlias = "";

    //��ʾSQL���
    BasePrint.println(" selectSQL : "+sqlString);

    try {

      //�õ�����
      stmt = con.createStatement();

      //��ѯָ��������
      sqlrs = stmt.executeQuery(sqlString);

      //�õ������ͼ�Ľṹ
      obj = this.getTableByResultSet(sqlrs);

      //��ӱ����ͼ����
      obj.setTableName(tableName);

      //�õ�����ֶ�����
      fieldCount = obj.getFieldCount();
      while (sqlrs.next()) {

        //ѭ���õ��ֶ�ֵ
        ArrayList valueAL = new ArrayList();

        for (i = 0; i < fieldCount; i++) {

          //�õ��ֶα���
          fieldAlias = (String)(obj.getFieldAlias().get(i));

          //�õ��ֶ�����
          fieldType = ((BaseDataType)(obj.getFieldType().get(i))).getDataType();

          //�����ֶ����͵õ��ֶ�ֵ
          switch (fieldType) {

            //����ǣ�INTEGER
            case BaseDataType.INTEGER:
              valueAL.add(new Integer(sqlrs.getInt(fieldAlias)));
              break;

              //����ǣ�NUMBER
            case BaseDataType.NUMBER:
              valueAL.add(sqlrs.getBigDecimal(fieldAlias));
              break;

              //����ǣ�STRING
            case BaseDataType.STRING:

              //�����NULL,����""
              String tempFRS = sqlrs.getString(fieldAlias);
              if (tempFRS == null) {
                valueAL.add(new String(""));
              }
              else {
                valueAL.add(new String(tempFRS.trim()));
              }

              break;

              //����ǣ�CLOB
            case BaseDataType.CHAR_STREAM:
              Clob clob = sqlrs.getClob(fieldAlias);
              BufferedReader reader = null;
              StringBuffer buffer = new StringBuffer();
              try {
                reader = new BufferedReader(clob.getCharacterStream());
                String line;
                while ((line = reader.readLine()) != null) {
                  buffer.append(line);
                }
              }
              catch (Exception e) {
                e.printStackTrace();
              }
              finally {
                try {
                  if (reader != null) {
                    reader.close();
                  }
                }
                catch (Exception e) {
                  e.printStackTrace();
                }
              }
              valueAL.add(buffer.toString());
              break;

              //����ǣ�DATE
            case BaseDataType.DATE:

              //��ȡʱ��
              //�����NULL������Ĭ��ֵ
              Timestamp tempTimestamp = sqlrs.getTimestamp(fieldAlias);
              Date tempDate = null;
              if (tempTimestamp != null) {
                tempDate = new Date(tempTimestamp.getTime());
              }
              valueAL.add(tempDate);
              //BasePrint.println(valueAL.size()-1);
              //valueAL.add(sqlrs.getDate(fieldAlias));
              break;

            default:
              valueAL.add(new String("")); ;

          }

          //����
          fieldAlias = new String("");

        }

        //�趨�ֶ�ֵ
        obj.setFieldValue(valueAL);

        //����ֶ�ֵ�б�
        valueAL = new ArrayList();

        //����
        fieldAlias = new String("");

        //δ����������ֱ������ֶ�ֵ
        rAL.add(obj);

        //�����µ�������
        obj = BaseObject.clone(obj);

      }

    }
    catch (Exception e) {
      if (e.getClass().isInstance(new SQLException())) {

        //BasePrint.println("���ݿ����--���룺"+( (SQLException) e).getErrorCode());

        e.printStackTrace();
        throw new BaseException("", ((SQLException)e).getErrorCode());
      }
      else {
        e.printStackTrace();
      }

    }
    finally {

      if (sqlrs != null) {
        try {
          sqlrs.close();
        }
        catch (SQLException ignore) {
        }
      }

      if (stmt != null) {
        try {
          stmt.close();
        }
        catch (SQLException ignore) {
        }
      }

    }

    return rAL;

  }

  /**
   * ����һ��BaseObject����(Lib)
   * @param obj BaseObject
   * @throws RemoteException
   * @return boolean
   */
  public boolean insertBaseObject(BaseObject obj) throws RemoteException {
    return insertSQL(obj.getTableName(), obj);
  }

  /**
   * �޸�һ��BaseObject����(Lib)
   * @param obj BaseObject
   * @param sqlWhere String
   * @throws RemoteException
   * @return boolean
   */
  public boolean updateBaseObject(BaseObject obj, String sqlWhere) throws RemoteException {
    return updateSQL(obj.getTableName(), obj, sqlWhere);
  }

  /**
   * ɾ��һ��BaseObject����(Lib)
   * @param obj BaseObject
   * @param sqlWhere String
   * @throws RemoteException
   * @return boolean
   */
  public boolean deleteBaseObject(BaseObject obj, String sqlWhere) throws RemoteException{
    return deleteSQL(obj.getTableName(),sqlWhere);
  }
  
   
  /**
   * �õ���¼for��ѯ����
   *
   * @param strWhere
   *            ��ѯ����
   * @param strTab
   *            ��ѯ�ı���
   * @param fldOrderName
   *            �����ֶ���
   * @return ArrayList ָ���б�
   * @throws RemoteException
   */
  public ArrayList selectSQL(String tableName,String strWhere, 
		  String fldOrderName) throws RemoteException {
	  StringBuffer sb = new StringBuffer("");
	  sb.append("select * from ");
	  sb.append(tableName);
	  if (strWhere != null && strWhere.equals("") == false) {
		  sb.append(" where " + strWhere);
	  }
	  if (fldOrderName != null && fldOrderName.equals("") == false) {
		  sb.append(" order by " + fldOrderName);
	  }
	  //���ɲ�ѯ�б�
	  ArrayList rs = this.selectSQL(tableName, sb.toString());
	  return rs;
  } 
  
 
  /**
   * �õ���¼for��ѯ����
   *
   * @param strWhere
   *            ��ѯ����
   * @param strTab
   *            ��ѯ�ı���
   * @param fldOrderName
   *            �����ֶ���
   * @param selectIndex ���ؼ�¼������
   * @param selectNumber ���ؼ�¼������
   * @throws RemoteException
   */
  public ArrayList selectSQL(String tableName,
		  String strWhere,String fldOrderName,int selectIndex,int selectNumber) throws RemoteException {
	  StringBuffer sqlString = new StringBuffer("");
	  
	  
	  if ((selectIndex==0)||(selectNumber==0))
	  {
		  return this.selectSQL(tableName,strWhere,fldOrderName);
	  }
	  
	  
	  //��ѯ��ҳָ����¼
	  sqlString.append(" select * ");
	  sqlString.append(" from ");
	  sqlString.append(" ( select ROWNUM as tempRowNumIndex , tempTableName.*  from ");
	  sqlString.append(tableName);
	  sqlString.append(" tempTableName where ");
	  
	  //������������
	  int searchFirstIndex = selectIndex * selectNumber;
	  
	  //����ĩ������
	  int searchLastIndex = searchFirstIndex + selectNumber;
	  
	  sqlString.append(" ROWNUM <= ");
	  
	  sqlString.append(searchLastIndex);
	  
	  if (strWhere != null && strWhere.equals("") == false) {
		  sqlString.append(" and " + strWhere);
	  }
	  if (fldOrderName != null && fldOrderName.equals("") == false) {
		  sqlString.append(" order by " + fldOrderName);
	  }
	  
	  sqlString.append(" ) where tempRowNumIndex > ");
	  sqlString.append(searchFirstIndex);
	  
	  //���ɲ�ѯ�б�
	  ArrayList rs = this.selectSQL(tableName, sqlString.toString());
	  return rs;
  } 
  
  
}
