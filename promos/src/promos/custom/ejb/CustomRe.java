package promos.custom.ejb;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import java.rmi.RemoteException;

/**
 *
 * <p>
 * ����: �ӿ� CoreManage
 * </p>
 * <p>
 * ����: ϵͳ������Bean�Ľӿ�
 * </p>
 * </p>
 * ��Ȩ: Copyright (c) 2003-2004
 * </p>
 * <p>
 * ��˾: ��������ý���޹�˾
 * </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 06/8/2004
 *
 */
@Local

public interface CustomRe {
	public String reCustomPara(String sHtml,HttpServletRequest request) throws RemoteException ;
}