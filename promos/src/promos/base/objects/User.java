package promos.base.objects;

import java.io.Serializable;
/**
 *
 * <p>����: �� User </p>
 * <p>����: ��¼���� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 04/23/2003
 *
 */

public final class User
    implements Serializable {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userID;                //ID
	  private String userCode;         	//��¼��
	  private String userName;         	//����
	  private String userAddress;      	//��ַ
	  private String userPhone;        	//�绰
	  private String userMobil;        	//�ֻ�
	  private String userEmail;        	//����
	  private String userDeptId;    	//���ű��
	  private String userDeptName;		//��������
	  private int userStatus;			//״̬
	  private String userCardId;		//����
	  private String userIdenCard;		//���֤��
	  private String website;			//��ǰ��¼վ��
	  private String roleidlist;        //��ɫ�б���ʽ��,XXXX,XXXX,XXXX,
	  private String designrole;        //��ƽ�ɫid
	  private String ownerole;        //������ƽ�ɫid
	  private String sessionid;        //��ϵͳsessionͬ��ʱ��id�����id�Բ����޷���¼


  /**
   * ��¼�����Session����
   */
  public final static String SESSION_USER = "SessionUser";

  /**
   * ����
   */
  public final static int RIGHT_STATUS = 1;

  /**
   * û���û�
   */
  public final static int NO_USER_STATUS = 2;

  /**
   * �������
   */
  public final static int NO_PASSWORD_STATUS = 3;

  /**
   * û�����֤
   */
  public final static int NO_USERIDENCARD_STATUS = 4;

  /**
   * û�в���
   */
  public final static int NO_DEPT_STATUS = 5;

  /**
   * û��Ȩ��
   */
  public final static int NO_PERM_STATUS = 6;

  /**
   * û�п���
   */
  public final static int NO_CARDID_STATUS = 7;

  /**
   * ����
   */
  public User() {
	  
    this.userStatus = 0;
    this.userID = "0";
    this.userCardId = "";
    this.userDeptId = "";
    this.userDeptName = "";
    this.userName = "";
    this.userCode = "";
    this.userIdenCard = "";
    this.userAddress = "";
    this.userPhone = "";
    this.userMobil = "";
    this.userEmail = "";
    this.website = "";
    this.roleidlist = "";
    this.sessionid="";
  }

  
  
  
  /**
   * ���ݵ�¼��״̬�õ���ǰĬ����Ϣ��
   *
   * @param intType ��¼�ߵ�ǰ״̬���
   * @return ��ǰĬ����Ϣ
   */
  public static String getDefaultInfo(int intType) {

    //Ĭ����Ϣ
    String rs = new String("");

    //���
    switch (intType) {

      //�ɹ���¼
      case User.RIGHT_STATUS:
        rs = "�ɹ���¼��";
        break;

        //�û�δ�ҵ�
      case User.NO_USER_STATUS:
        rs = "δ�ҵ��û���";
        break;

        //�������
      case User.NO_PASSWORD_STATUS:
        rs = "�������";
        break;

        //ȱ�ٲ�����Ϣ
      case User.NO_DEPT_STATUS:
        rs = "ȱ�ٲ�����Ϣ��";
        break;

        //ȱ�����֤��Ϣ
      case User.NO_USERIDENCARD_STATUS:
        rs = "ȱ�����֤��Ϣ��";
        break;

        //ȱ��Ȩ����Ϣ
      case User.NO_PERM_STATUS:
        rs = "ȱ��Ȩ����Ϣ��";
        break;
    }
    //����
    return rs;
  }




public String getRoleidlist() {
	return roleidlist;
}




public void setRoleidlist(String roleidlist) {
	this.roleidlist = roleidlist;
}


public String getDesignRole() {
	return designrole;
}


public void setDesignRole(String designrole) {
	this.designrole = designrole;
}

public String getOwnerRole() {
	return ownerole;
}


public void setOwnerRole(String ownerole) {
	this.ownerole = ownerole;
}


public String getUserAddress() {
	return userAddress;
}




public void setUserAddress(String userAddress) {
	this.userAddress = userAddress;
}




public String getUserCardId() {
	return userCardId;
}




public void setUserCardId(String userCardId) {
	this.userCardId = userCardId;
}




public String getUserCode() {
	return userCode;
}




public void setUserCode(String userCode) {
	this.userCode = userCode;
}




public String getUserDeptId() {
	return userDeptId;
}




public void setUserDeptId(String userDeptId) {
	this.userDeptId = userDeptId;
}




public String getUserDeptName() {
	return userDeptName;
}




public void setUserDeptName(String userDeptName) {
	this.userDeptName = userDeptName;
}




public String getUserEmail() {
	return userEmail;
}




public void setUserEmail(String userEmail) {
	this.userEmail = userEmail;
}




public String getUserID() {
	return userID;
}




public void setUserID(String userID) {
	this.userID = userID;
}




public String getUserIdenCard() {
	return userIdenCard;
}




public void setUserIdenCard(String userIdenCard) {
	this.userIdenCard = userIdenCard;
}




public String getUserMobil() {
	return userMobil;
}




public void setUserMobil(String userMobil) {
	this.userMobil = userMobil;
}




public String getUserName() {
	return userName;
}




public void setUserName(String userName) {
	this.userName = userName;
}




public String getUserPhone() {
	return userPhone;
}




public void setUserPhone(String userPhone) {
	this.userPhone = userPhone;
}




public int getUserStatus() {
	return userStatus;
}




public void setUserStatus(int userStatus) {
	this.userStatus = userStatus;
}




public String getWebsite() {
	return website;
}




public void setWebsite(String website) {
	this.website = website;
}



public String getSessionid() {
	return sessionid;
}


public void setSessionid(String sessionid) {
	this.sessionid = sessionid;
}



}
