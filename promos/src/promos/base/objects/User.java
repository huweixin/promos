package promos.base.objects;

import java.io.Serializable;
/**
 *
 * <p>标题: 类 User </p>
 * <p>描述: 登录者类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
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
	  private String userCode;         	//登录名
	  private String userName;         	//姓名
	  private String userAddress;      	//地址
	  private String userPhone;        	//电话
	  private String userMobil;        	//手机
	  private String userEmail;        	//电邮
	  private String userDeptId;    	//部门编号
	  private String userDeptName;		//部门名称
	  private int userStatus;			//状态
	  private String userCardId;		//卡号
	  private String userIdenCard;		//身份证号
	  private String website;			//当前登录站点
	  private String roleidlist;        //角色列表，格式：,XXXX,XXXX,XXXX,
	  private String designrole;        //设计角色id
	  private String ownerole;        //自身设计角色id
	  private String sessionid;        //两系统session同步时的id，如果id对不上无法登录


  /**
   * 登录者类的Session名称
   */
  public final static String SESSION_USER = "SessionUser";

  /**
   * 正常
   */
  public final static int RIGHT_STATUS = 1;

  /**
   * 没有用户
   */
  public final static int NO_USER_STATUS = 2;

  /**
   * 密码错误
   */
  public final static int NO_PASSWORD_STATUS = 3;

  /**
   * 没有身份证
   */
  public final static int NO_USERIDENCARD_STATUS = 4;

  /**
   * 没有部门
   */
  public final static int NO_DEPT_STATUS = 5;

  /**
   * 没有权限
   */
  public final static int NO_PERM_STATUS = 6;

  /**
   * 没有卡号
   */
  public final static int NO_CARDID_STATUS = 7;

  /**
   * 构建
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
   * 根据登录者状态得到当前默认信息。
   *
   * @param intType 登录者当前状态编号
   * @return 当前默认信息
   */
  public static String getDefaultInfo(int intType) {

    //默认信息
    String rs = new String("");

    //检测
    switch (intType) {

      //成功登录
      case User.RIGHT_STATUS:
        rs = "成功登录！";
        break;

        //用户未找到
      case User.NO_USER_STATUS:
        rs = "未找到用户！";
        break;

        //密码错误
      case User.NO_PASSWORD_STATUS:
        rs = "密码错误！";
        break;

        //缺少部门信息
      case User.NO_DEPT_STATUS:
        rs = "缺少部门信息！";
        break;

        //缺少身份证信息
      case User.NO_USERIDENCARD_STATUS:
        rs = "缺少身份证信息！";
        break;

        //缺少权限信息
      case User.NO_PERM_STATUS:
        rs = "缺少权限信息！";
        break;
    }
    //返回
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
