package promos.base.ejb;

import java.rmi.RemoteException;
import java.util.ArrayList;

import java.sql.Connection;
//import java.sql.ResultSet;

import javax.ejb.Local;
import javax.sql.DataSource;

import promos.base.objects.BaseObject;
import promos.base.objects.FieldError;

//import javax.naming.Name;

/**
 *
 * <p>����: �ӿ� DBBase </p>
 * <p>����: ����Bean�Ľӿ� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @author ��ά��
 * @version 1.0 04/22/2003
 *
 */
@Local

public interface DBBase {

  /**
   * �õ�ָ�������ͼ�Ľṹ�������������ͺ��ַ�����
   *
   *
   * @param strDataSource ����Դ��JNDI����
   * @param strTableName �����ͼ������
   * @return �����ͼ�Ļ���
   * @throws RemoteException
   */
  public BaseObject getSimpleFieldByName(String strTableName) throws     RemoteException;

  public FieldError checkField( BaseObject baseObj) throws RemoteException;

  /**
   * �õ�ָ�������ͼ�Ľṹ
   *
   *
   * @param strDataSource ����Դ��JNDI����
   * @param strTableName �����ͼ������
   * @return �����ͼ�Ļ���
   * @throws RemoteException
   */
  public BaseObject getTableByName( String strTableName) throws
      RemoteException;

  /**
   * �õ�����Դ
   *
   * @param dataSourceName ����ԴJNDI����
   * @return ����Դ
   * @throws RemoteException
   */
  public DataSource getDataSource() throws RemoteException;

  /**
   * �õ�����
   *
   * @param dataSourceName ����ԴJNDI����
   * @return ����
   * @throws RemoteException
   */
  public Connection getConnection() throws RemoteException;

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
      RemoteException;

  /**
   * ��ѯ��¼
   *
   * @param dataSourceName DataSource������
   * @param tableName ��������ͼ������
   * @param sqlString ��ѯSQL���
   * @return ��ѯ��¼�б�
   * @throws RemoteException
   */
  public ArrayList selectSQL( String tableName,
                             String sqlString) throws RemoteException;

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
  public ArrayList selectSQL( String tableName,
                             ArrayList al, String strStatus, int selectIndex,
                             int selectNumber, int status) throws
      RemoteException;

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
  public ArrayList selectSQLEX( String tableName,
                               ArrayList al, String strStatus, String strSort,
                               int selectIndex, int selectNumber, int status) throws
      RemoteException;

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
  public ArrayList selectSQLEXSortAll( String tableName,
                                      ArrayList al, String strStatus,
                                      String strSort, int selectIndex,
                                      int selectNumber, int status) throws
      RemoteException;

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
   * @param strFields ָ����ѯ���ֶ�
   * @param al ��ѯSQL����
   * @param strStatus ��ѯSQL������޶�����
   * @param selectIndex ���ؼ�¼������
   * @param selectNumber ���ؼ�¼������
   * @param status ��ѯ��¼���ǲ�ѯ������־
   * @return ��ѯ��¼�б�
   * @throws RemoteException
   */
  public ArrayList selectSQL( String tableName,
                             String strFields, ArrayList al, String strStatus,
                             int selectIndex, int selectNumber, int status) throws
      RemoteException;

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
  public ArrayList selectSQLEX( String tableName,
                               String strFields, ArrayList al, String strStatus,
                               String strSort, int selectIndex,
                               int selectNumber, int status) throws
      RemoteException;

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
  public ArrayList selectSQLEXSortAll( String tableName,
                                      String strFields, ArrayList al,
                                      String strStatus, String strSort,
                                      int selectIndex, int selectNumber,
                                      int status) throws RemoteException;



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
 public ArrayList selectSQLEXSortAll( String tableName,
                                     String strFields, ArrayList al,
                                     String strStatus, String strSort,String strGroupBy,
                                     int selectIndex, int selectNumber,
                                     int status) throws RemoteException ;













  /**
   * ��Ӽ�¼
   *
   * @param dataSourceName ����Դ������
   * @param updateTableName ����Ӽ�¼�ı������
   * @param obj ��ӵļ�¼
   * @return ����ɹ���Ӽ�¼������True�����򷵻�False
   * @throws RemoteException
   */
  public boolean insertSQL( String updateTableName,
                           BaseObject obj) throws RemoteException;

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
                                   String strWhere) throws RemoteException;

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
                           String sqlString) throws RemoteException;

  /**
   * ���¼�¼
   *
   * @param dataSourceName ����Դ������
   * @param updateTableName �����¼�¼�ı������
   * @param obj ��Ҫ���µļ�¼
   * @param sqlString ���¼�¼���޶�����
   * @return ����ɹ����¼�¼������True�����򷵻�False
   * @throws RemoteException
   */
  public boolean updateSQL( String updateTableName,
                           BaseObject obj, String sqlString) throws
      RemoteException;

  /**
   * ������
   *
   * @param dataSourceName ����Դ������
   * @param updateTableName �������ı������
   * @param sqlString �������ı�Ľṹ
   * @return ����ɹ�����������True�����򷵻�False
   * @throws RemoteException
   */
  public boolean createSQL( String updateTableName,
                           String sqlString) throws RemoteException;

  /**
   * ɾ����
   *
   * @param dataSourceName ����Դ������
   * @param updateTableName ��ɾ���ı������
   * @return ����ɹ�ɾ��������True�����򷵻�False
   * @throws RemoteException
   */
  public boolean dropSQL( String updateTableName) throws
      RemoteException;

  /**
   * ��ӡ�ɾ�������¼�¼
   *
   * @param dataSourceName DataSource������
   * @param sqlString ��ӡ�ɾ�������µ�SQL���
   * @return ��������ɹ�������True,���򷵻�False
   * @throws RemoteException
   */
  public boolean executeSQL( String sqlString) throws
      RemoteException;

  /**
   * ִ��Begin-end���
   *
   * @param dataSourceName DataSource������
   * @param sqlString Begin-end���
   * @return ��������ɹ�������True,���򷵻�False
   * @throws RemoteException
   */
  public boolean executeBeginEnd( String sqlString) throws
      RemoteException;

  /**
   * ��ѯ��¼����
   *
   * @param dataSourceName DataSource������
   * @param tableName ��������ͼ������
   * @param sqlString ��ѯSQL���
   * @return ��¼����
   * @throws RemoteException
   */
  public int getCount( String tableName, String sqlString) throws
      RemoteException;

  /**
   * �Զ���ȡ��һ�����к�
   *
   * @param dataSourceName DataSource������
   * @param seqName ���к�����
   * @return ��һ�����к�
   * @throws RemoteException
   */
  public long getSeqNextValue( String seqName) throws
      RemoteException;

  /**
   * ִ��һ��SQL���
   *
   * @param dataSourceName DataSource������
   * @param arrayList SQL����б�
   * @return ����ִ�н��
   * @throws RemoteException
   */
  public boolean executeBatch( ArrayList arrayList) throws
      RemoteException;

  /**
   * ���´��ַ����ֶ�
   *
   * @param dataSourceName DataSource������
   * @param sql
   * @param text
   * @return ����ִ�н��
   * @throws RemoteException
   */
  public boolean updateClob( String sql, String text) throws
      RemoteException;

  /**
   * ִ��SQLȡ���ַ���
   *
   * @param dataSourceName DataSource������
   * @param sql
   * @return ����ִ���ַ���
   * @throws RemoteException
   */
  public String getValue( String sql) throws
      RemoteException;


  /**
   * ��ѯ��¼
   *
   * @param Connection ���ݿ�����
   * @param tableName ��������ͼ������
   * @param sqlString ��ѯSQL���
   * @return ��ѯ��¼�б�
   * @throws RemoteException
   */
  public ArrayList selectSQL(Connection con, String tableName, String sqlString) throws RemoteException;

  /**
   * ����һ��BaseObject����(Lib)
   * @param obj BaseObject
   * @throws RemoteException
   * @return boolean
   */
  public boolean insertBaseObject(BaseObject obj) throws RemoteException ;

  /**
   * �޸�һ��BaseObject����(Lib)
   * @param obj BaseObject
   * @param sqlWhere String
   * @throws RemoteException
   * @return boolean
   */
  public boolean updateBaseObject(BaseObject obj,String sqlWhere) throws RemoteException;

  /**
   * ɾ��һ��BaseObject����(Lib)
   * @param obj BaseObject
   * @param sqlWhere String
   * @throws RemoteException
   * @return boolean
   */
  public boolean deleteBaseObject(BaseObject obj, String sqlWhere) throws RemoteException;
  
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
		  String fldOrderName) throws RemoteException;
  
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
  public ArrayList selectSQL( String tableName,
		  String strWhere,String fldOrderName,int selectIndex,int selectNumber) throws RemoteException;  
}
