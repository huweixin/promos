package promos.base.objects;

//import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import java.sql.Date;

import java.io.Serializable;

/**
 *
 * <p>����: �� SeperpDate </p>
 * <p>����: ʱ�������� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 *
 * @author ��ά��
 * @version 1.0 05/14/2003
 *
 */

public class PromosDate
    implements Serializable {

  public static final String DEFAULTFORMAT = "yyyy-MM-DD hh24:mi:ss";
  public static final String SHORTFORMAT = "yyyy-MM-dd";

  public static final String SIMPLEFORMATSTRING = "yyyy-MM-dd HH:mm:ss";

  public static final int YYYY_MM_DD = 1; //2003-09-5
  public static final int YYYY_MM_DD_HH_MI = 2; //2003-09-5 10:25
  public static final int YYYY_MM_DD_HH_MI_SS = 3; //2003-09-5 10:25:28
  public static final int MM_DD = 4; //09-5
  public static final int HH_MI = 5; //10:25
  public static final int HH_MI_SS = 6; //10:25:28
  public static final int MM_DD_HH_MI_SS = 7; //09-5 10:25:28
  public static final int MM_DD_HH_MI = 8; //09-5 10:25
  public static final int YYYY = 9; //2003
  public static final int MM = 10; //9
  public static final int DD = 11; //5
  public static final int HH = 12; //20
  public static final int MI = 13; //25
  public static final int SS = 14; //28
  public static final int YYYY_MM =15;

  /**
   * ����
   */
  public PromosDate() {
  }

  /**
   * �õ���������֮������������һ����ȥ
   *
   * @param strDay1 ��ʼ����
   * @param strDay2 ��������
   * @return �������
   * @throws Exception �쳣
   */
  public static int getDayNumber(String strDay1, String strDay2) throws
      Exception {
    Date d1 = PromosDate.getSelectDate(strDay1);
    long Time1 = d1.getTime();
    Date d2 = PromosDate.getSelectDate(strDay2);
    long Time2 = d2.getTime();
    long allTime = Time2 - Time1;
    //if (allTime < 0) {
    //  allTime = 0 - allTime;
    //}

    //һ��
    long dayTime = 60 * 60 * 24*1000;

    return (int) (allTime / dayTime);
  }

  /**
   * �õ�����ʱ��֮���ʱ���
   *
   * @param BeginTime ��ʼʱ��
   * @param EndTime ����ʱ��
   * @param intFormat ���ص����ڸ�ʽ
   * @return ʱ���
   * @throws Exception
   */
  public static double getTimeDistence(java.sql.Date BeginTime,java.sql.Date EndTime,
                                       int intFormat)
      throws Exception {
    double dblReturn = 0;

    long lngBeginTime = BeginTime.getTime();
    long lngEndTime = EndTime.getTime();
    long lngDistence = lngEndTime - lngBeginTime;

    long lngMinuteTime = 60 * 1000;//����
    long lngHourTime = 60 * lngMinuteTime;//Сʱ
    long lngDayTime = 24 * lngHourTime;//��

    switch (intFormat) {
      //��������
      case DD:
        dblReturn = ((0.1 * lngDistence) / (0.1 * lngDayTime));
        break;
      //����Сʱ
      case HH:
        dblReturn = ((0.1 * lngDistence) / (0.1 * lngHourTime));
        break;
      //�������
      case MI:
        dblReturn = ((0.1 * lngDistence) / (0.1 * lngMinuteTime));
        break;
    }
    return dblReturn;
  }

  /**
   * �õ������պ����ڼ���2003-03-02 00:00:00
   *
   * @param strDate ���ڸ�ʽ
   * @return �����պ����ڼ�
   * @throws Exception �쳣
   */
  public static String getYearMonthDayWeek(String strDate) throws Exception {
    //2003��3��2�� (����һ)
    StringBuffer rs = new StringBuffer("");
    //
    //�����ַ�������ʱ��
    if (strDate == null || strDate.compareTo("") == 0) {
      return "";
    }

    //
    String tDate = strDate;
    if (strDate.length() >= 10) {
      int indexYear = tDate.indexOf("-");
      String strYear = tDate.substring(0, indexYear);
      tDate = tDate.substring(indexYear + 1);
      int Year = Integer.valueOf(strYear).intValue();
      //System.out.print("Year:"+strYear);
      rs.append(Year);
      rs.append("��");

      int indexMonth = tDate.indexOf("-");
      String strMonth = tDate.substring(0, indexMonth);
      tDate = tDate.substring(indexMonth + 1);
      //System.out.print("Month:"+strMonth);
      int Month = Integer.valueOf(strMonth).intValue();
      rs.append(Month);
      rs.append("��");

      String strDay = tDate.substring(0, 2);
      int Day = Integer.valueOf(strDay).intValue();
      //tDate = tDate.substring(indexDay);
      //System.out.print("Day:"+strDay);
      rs.append(Day);
      rs.append("�� (����");

      //���������մ���ʱ��
      Calendar c = Calendar.getInstance();
      c.set(Year, Month - 1, Day);
      int rsindex = c.get(Calendar.DAY_OF_WEEK);
      //System.out.print("����:"+rsindex);
      switch (rsindex) {
        case 1:
          rs.append("��");
          break;
        case 2:
          rs.append("һ");
          break;
        case 3:
          rs.append("��");
          break;
        case 4:
          rs.append("��");
          break;
        case 5:
          rs.append("��");
          break;
        case 6:
          rs.append("��");
          break;
        case 7:
          rs.append("��");
          break;
      }
      rs.append(")");

    }

    //
    return rs.toString();
  }

  /**
   * ����ʽ�����ַ���ת���ɶ̸�ʽ
   *
   * @return �̸�ʽ�����ַ���
   */
  public static String longToShort(String longFormat) throws Exception {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    return sdf1.format(sdf.parse(longFormat));
  }

  /**
   * �õ���ǰ����
   *
   * @return ��ǰ����
   */
  public static Date getCurrentDate() {

    Calendar currentCalendar = Calendar.getInstance();

    return new Date(currentCalendar.getTime().getTime());
  }

  /**
   * �õ�ָ������
   *
   * @param strDate ����
   * @return ����
   */
  public static Date getSelectDate(String strDate) {
    try {
      //�õ�����
      SimpleDateFormat formatter = new SimpleDateFormat(SIMPLEFORMATSTRING);
      return new Date( (formatter.parse(strDate)).getTime());
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new Date(0);
  }


  /**
   * �õ�ָ������
   *
   * @param strDate ����
   * @return ����
   */
  public static Date getSelectDate(String strDate, String pattern) {
    try {
      //�õ�����
      SimpleDateFormat formatter = new SimpleDateFormat(pattern);
      return new Date( (formatter.parse(strDate)).getTime());
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new Date(0);
  }


  /**
   * �õ���ǰ���ڼ�
   *
   * @return ���ڼ�
   */
  public static String getCurrentMonth() {
    return PromosDate.getCurrentMonthByString(PromosDate.toShortFormatString(
        PromosDate.getCurrentDate()));

  }

  /**
   * �õ���ǰ���ڼ�
   *
   * @param strDate ��ǰ���� ��1999-01-12
   * @return ���ڼ�
   */
  public static String getCurrentMonth(String strDate) {
    return PromosDate.getCurrentMonthByString(strDate);
  }

  /**
   * �õ���ǰ���ڼ�
   *
   * @param strDate ��ǰ����
   * @return ���ڼ�
   */
  public static String getCurrentMonth(Date strDate) {
    return PromosDate.getCurrentMonthByString(PromosDate.toShortFormatString(
        strDate));
  }

  //�õ���ǰ���ڼ�
  private static String getCurrentMonthByString(String strDate) {

    //�����ַ�������ʱ��
    if (strDate == null || strDate.compareTo("") == 0) {
      return "";
    }

    //
    String tDate = strDate;
    String rs = "";
    if (strDate.length() >= 10) {
      int indexYear = tDate.indexOf("-");
      String strYear = tDate.substring(0, indexYear);
      tDate = tDate.substring(indexYear + 1);
      int Year = Integer.valueOf(strYear).intValue();
      //System.out.print("Year:"+strYear);

      int indexMonth = tDate.indexOf("-");
      String strMonth = tDate.substring(0, indexMonth);
      tDate = tDate.substring(indexMonth + 1);
      //System.out.print("Month:"+strMonth);
      int Month = Integer.valueOf(strMonth).intValue();

      String strDay = tDate.substring(0, 2);
      int Day = Integer.valueOf(strDay).intValue();
      //tDate = tDate.substring(indexDay);
      //System.out.print("Day:"+strDay);

      //���������մ���ʱ��
      Calendar c = Calendar.getInstance();
      c.set(Year, Month - 1, Day);
      int rsindex = c.get(Calendar.DAY_OF_WEEK);
      //System.out.print("����:"+rsindex);
      switch (rsindex) {
        case 1:
          rs = "��";
          break;
        case 2:
          rs = "һ";
          break;
        case 3:
          rs = "��";
          break;
        case 4:
          rs = "��";
          break;
        case 5:
          rs = "��";
          break;
        case 6:
          rs = "��";
          break;
        case 7:
          rs = "��";
          break;
      }
    }

    return rs;
  }

  /**
   * �õ�Ĭ�ϵ�������ʾ��ʽ
   *
   * @param d ָ��������
   * @return ָ�����ڵ�Ĭ����ʾ��ʽ
   */
  public static String toDefaultString(Date d) {
    String rs = "";

    try {
      SimpleDateFormat formatter = new SimpleDateFormat(SIMPLEFORMATSTRING);
      rs = formatter.format(d);
    }
    catch (Exception e) {
	  BasePrint.println("******** the date is not in right partten! ***********");
      //e.printStackTrace();
    }

    return rs;

  }

  /**
   * �õ�Ĭ�ϵ����ڸ�ʽ
   *
   * @param d ָ��������
   * @return ���ڸ�ʽ
   */
  public static String toDefaultFormat(Date d) {
    return DEFAULTFORMAT;
  }

  /**
   * �õ�Ĭ�ϵĶ�������ʾ��ʽ
   *
   * @param d
   * @return ��������ʾ��ʽ
   */
  public static String toShortFormatString(Date d) {

    String rs = "";

    try {
      SimpleDateFormat formatter = new SimpleDateFormat(SHORTFORMAT);
      rs = formatter.format(d);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return rs;

  }

  /**
   * ��������ַ�����������󷵻ؿ��ַ���
   *
   * @param strCheckDate ��������ַ���
   * @param intFormat ���ص����ڸ�ʽ
   * @return ������ʾ��ʽ
   */
  public static String checkErrorDateString(String strCheckDate, int intFormat) {

    String rs = "";

    //��������ַ����Ƿ���ȷ
    if (strCheckDate == null || strCheckDate.compareTo("") == 0) {
      return rs;
    }

    //����Ƿ�������ʱ��
    if (strCheckDate.length() < 10 ||
        PromosDate.isFirstStartDate(strCheckDate.substring(0, 10))) {
      return "";
    }

    //����ʽ�Ƿ���ȷ
    switch (intFormat) {
      //�����ڸ�ʽ
      case YYYY_MM_DD:

        rs = strCheckDate.substring(0, 10);

        break;
        //�����ڸ�ʽ
      case YYYY_MM_DD_HH_MI:

        rs = strCheckDate.substring(0, 16);

        break;
        //ȫ���ڸ�ʽ
      case YYYY_MM_DD_HH_MI_SS:

        rs = strCheckDate.substring(0, 19);

        break;
        //�·����ڸ�ʽ
      case MM_DD:

        rs = strCheckDate.substring(5, 10);

        break;

        //ʱ�ָ�ʽ
      case HH_MI:

        rs = strCheckDate.substring(11, 16);

        break;
        //ʱ�����ʽ
      case HH_MI_SS:

        rs = strCheckDate.substring(11, 19);

        break;

        //����ʱ�����ʽ
      case MM_DD_HH_MI_SS:

        rs = strCheckDate.substring(5);

        break;
        //����ʱ�ָ�ʽ
      case MM_DD_HH_MI:

        rs = strCheckDate.substring(5, 16);
        //�����Ϊ�գ�ɾ����
        String rss = strCheckDate.substring(14, 16);
        if (rss.startsWith("0")) {
          rss = rss.substring(1);
        }

        //System.out.print("here --- :"+rss);
        if (rss.compareTo("0") == 0) {
          //rs = strCheckDate.substring(5, 13);//hxh modify 2005.4.13

          //���ʱΪ�գ�ɾ��ʱ��
          rss = strCheckDate.substring(11, 13);
          if (rss.startsWith("0")) {
            rss = rss.substring(1);
          }
          //System.out.print("here222");
          if (rss.compareTo("0") == 0) {
           // rs = strCheckDate.substring(5, 10);//hxh modify 2005.4.13
            // System.out.print("here333");
          }

        }

        break;

        //���ʽ
      case YYYY:
        rs = strCheckDate.substring(0, 4);
        break;

        //�¸�ʽ
      case MM:
        rs = strCheckDate.substring(5, 7);
        if (rs.startsWith("0")) {
          rs = rs.substring(1);
        }
        break;

        //�ո�ʽ
      case DD:
        rs = strCheckDate.substring(8, 10);
        if (rs.startsWith("0")) {
          rs = rs.substring(1);
        }
        break;

        //ʱ��ʽ
      case HH:
        rs = strCheckDate.substring(11, 13);
        if (rs.startsWith("0")) {
          rs = rs.substring(1);
        }
        break;

        //�ָ�ʽ
      case MI:
        rs = strCheckDate.substring(14, 16);
        if (rs.startsWith("0")) {
          rs = rs.substring(1);
        }
        break;

        //���ʽ
      case SS:
        rs = strCheckDate.substring(17, 19);
        if (rs.startsWith("0")) {
          rs = rs.substring(1);
        }
        break;

      case YYYY_MM:
        rs = strCheckDate.substring(0, 7);
        break;


        //û�и�ʽ
      default:
        break;

    }

    return rs;

  }

  //��������Ƿ���1970-01-01��1899-12-30
  private static boolean isFirstStartDate(String strDate) {
    if (strDate.compareTo("1970-01-01") == 0 ||
        strDate.compareTo("1899-12-30") == 0 || strDate.compareTo("1969-12-31")==0){
      return true;
    }
    return false;
  }

  /**
   * �õ�������ݵ�����
   *
   * @param strDate ����
   * @param year ����
   * @param intFormat ��ʾ��ʽ
   * @return ����
   */
  public static String getMovedDateString(String strDate, int year,
                                          int intFormat) {

    String rs = "";

    try {

      rs = checkErrorDateString(strDate, intFormat);

      if (rs.compareTo("") != 0) {

        //�õ�����
        SimpleDateFormat formatter = new SimpleDateFormat(SIMPLEFORMATSTRING);
        Date d = new Date( (formatter.parse(strDate)).getTime());

        //�ƶ�����
        d = PromosDate.getMoveDateByAddDate(d, year, 0, 0, 0, 0, 0);

        //ת����ʽ
        rs = PromosDate.toDefaultString(d);

        //����Ƿ��Ƿ���������
        rs = PromosDate.checkErrorDateString(rs, intFormat);

      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return rs;
  }

  /**
   * �õ�������ݵ�����
   *
   * @param strDate ����
   * @param year ����
   * @param month ����
   * @param day ����
   * @param intFormat ��ʾ��ʽ
   * @return ����
   */
  public static String getMovedDateString(String strDate, int year, int month,
                                          int day, int intFormat) {

    String rs = "";

    try {

      rs = checkErrorDateString(strDate, intFormat);

      if (rs.compareTo("") != 0) {

        //�õ�����
        SimpleDateFormat formatter = new SimpleDateFormat(SIMPLEFORMATSTRING);
        Date d = new Date( (formatter.parse(strDate)).getTime());

        //�ƶ�����
        d = PromosDate.getMoveDateByAddDate(d, year, month, day, 0, 0, 0);

        //ת����ʽ
        rs = PromosDate.toDefaultString(d);

        //����Ƿ��Ƿ���������
        rs = PromosDate.checkErrorDateString(rs, intFormat);

      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return rs;
  }

  /**
   * ��ָ�����ַ���ת���������͵����ݡ�
   *
   * @param str ָ���ı�ʾ���ڵ��ַ�����
   * @return
   */
  public static java.sql.Date getDateByShortString(String str) {
    if (str == null || str.trim().equals("")) {
      return null;
    }
    try {
      Calendar cc = Calendar.getInstance();
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      java.util.Date d = format.parse(str);
      //BasePrint.println(d+"yyyyyyyyyy");

      java.sql.Date d2 = new java.sql.Date(d.getTime());
      return d2;
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * ��ָ�����ַ���ת���������͵����ݡ�
   *
   * @param str ָ���ı�ʾ���ڵ��ַ�����
   * @return
   */
  public static java.sql.Date getSqlDateByString(String str, String pattern) {
    if (str == null || str.trim().equals("")) {
      return null;
    }
    try {
      Calendar cc = Calendar.getInstance();
      SimpleDateFormat format = new SimpleDateFormat(pattern);
      java.util.Date d = format.parse(str);
      //BasePrint.println(d+"yyyyyyyyyy");

      java.sql.Date d2 = new java.sql.Date(d.getTime());
      return d2;
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


  /**
   * �ƶ���ѡ��������
   *
   * @param moveDate ���ƶ�������
   * @param year ��
   * @param month ��
   * @param day ��
   * @param hour ʱ
   * @param minute ��
   * @param second ��
   * @return ����
   */
  public static Date getMoveDateByAddDate(Date moveDate, int year, int month,
                                          int day, int hour, int minute,
                                          int second) {

    //�õ�ָ��ʱ��
    Calendar cc = Calendar.getInstance();
    cc.setTime(moveDate);

    //�ƶ���
    if (year != 0) {
      cc.add(Calendar.YEAR, year);
    }
    //�ƶ���
    if (month != 0) {
      cc.add(Calendar.MONTH, month);
    }

    //�ƶ���
    if (day != 0) {
      cc.add(Calendar.DAY_OF_YEAR, day);
    }

    //�ƶ�ʱ
    if (hour != 0) {
      cc.add(Calendar.HOUR, hour);
    }

    //�ƶ���
    if (minute != 0) {
      cc.add(Calendar.MINUTE, minute);
    }

    //�ƶ���
    if (second != 0) {
      cc.add(Calendar.SECOND, second);
    }

    //����
    Date rs = new Date(cc.getTime().getTime());
    return rs;
  }


public static String strDateToCNString(String strDate) {
    String strYear = "";
    String strMonth = "";
    String strTempMonth = "";
    String strDay = "";
    String strHour = "";
    String strMinute = "";
    boolean isShortDate = false;
    DateFormat df = null;
    if (strDate.equals("")) {
      return strDate;
    }
    try {
      if (strDate.lastIndexOf(":") == -1) {
        df = new SimpleDateFormat("yyyy-MM-dd");
        isShortDate = true;
      }
      else {
        df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
      }
      java.util.Date d = (java.util.Date) df.parse(strDate);
      Calendar calendar = Calendar.getInstance(); //ʵ��ֻ��һ��
      calendar.setTime(d);
      strYear = String.valueOf(calendar.get(Calendar.YEAR));
      strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
      strTempMonth = String.valueOf(calendar.get(Calendar.MONTH));
      strDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
      strHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
      strMinute = String.valueOf(calendar.get(Calendar.MINUTE));
      if (isShortDate) {
        strDate = strYear + "��" + strMonth + "��" + strDay + "��";
      }
      else {
        strDate = strYear + "��" + strMonth + "��" + strDay + "��" + strHour + "ʱ" +
            strMinute + "��";
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return strDate;
  }


  public static String getDayOfWeek(String date, String format) throws Exception {
    DateFormat df = new SimpleDateFormat(format);
    Calendar cal = Calendar.getInstance();
    cal.setTime(df.parse(date));
    int day = cal.get(Calendar.DAY_OF_WEEK);
    switch (day) {
      case Calendar.SUNDAY:
        return "������";
      case Calendar.MONDAY:
        return "����һ";
      case Calendar.TUESDAY:
        return "���ڶ�";
      case Calendar.WEDNESDAY:
        return "������";
      case Calendar.THURSDAY:
        return "������";
      case Calendar.FRIDAY:
        return "������";
      case Calendar.SATURDAY:
        return "������";
      default:
        return "���ڴ���";
    }
  }

	public static int getDayInRange(Date lowerLimitDate,Date upperLimitDate){

	   long upperTime,lowerTime;
	   upperTime=upperLimitDate.getTime();
	   lowerTime=lowerLimitDate.getTime();
	   Long result=new Long((upperTime-lowerTime)/(1000*60*60*24));
	   return result.intValue();
	 }

	 public static String strToday(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new java.util.Date());
	 }


         /**
          * ���ڼ��������õ���������(lb)
          * @param strDate Date
          * @param dayNumber int
          * @return Date
          */
         public static Date getDateAddDayNumber(String strDate, int dayNumber) {
           return getDateAddDayNumber(getDateAuto(strDate), dayNumber);
         }

         /**
          * ���ڼ��������õ���������(lb)
          * @param startDate String
          * @param dayNumber int
          * @return Date
          */
         public static java.sql.Date getDateAddDayNumber(Date dDate, int dayNumber) {
           try {
             //Date d1 = SeperpDate.getSelectDate(strDate);
             Date d1 = dDate;
             long oldTime = d1.getTime();
             long dayTime = 60 * 60 * 24 * 1000;
             dayTime *= dayNumber;
             long newDayTime = oldTime + dayTime;

             java.sql.Date d2 = new java.sql.Date(newDayTime);
             return d2;
           }
           catch (Exception e) {
             e.printStackTrace();
             return null;
           }
         }

         /**
          * ���ڼ�ȥ�����õ���������(lb)
          * @param strDate Date
          * @param dayNumber int
          * @return Date
          */
         public static Date getDateSubDayNumber(String strDate, int dayNumber) {
           return getDateSubDayNumber(getDateAuto(strDate), dayNumber);
         }

         /**
          * ���ڼ�ȥ�����õ���������(lb)
          * @param startDate String
          * @param dayNumber int
          * @return Date
          */
         public static java.sql.Date getDateSubDayNumber(Date dDate, int dayNumber) {
           try {
             //Date d1 = SeperpDate.getSelectDate(strDate);
             Date d1 = dDate;
             long oldTime = d1.getTime();
             long dayTime = 60 * 60 * 24 * 1000;
             dayTime *= dayNumber;
             long newDayTime = oldTime - dayTime;

             java.sql.Date d2 = new java.sql.Date(newDayTime);
             return d2;
           }
           catch (Exception e) {
             e.printStackTrace();
             return null;
           }
         }

         /**
          * ������������ڳ����Զ�ת������Ӧ���ȵ�Date������(lb)
          * @param dDate String
          * @return Date
          */
         public static Date getDateAuto(String dDate) {
           String dateFormat = SHORTFORMAT;

           if (dDate.length() > 10 && dDate.length() <= 16) {
             dateFormat = SIMPLEFORMATSTRING;
           }
           if (dDate.length() > 16) {
             dateFormat = DEFAULTFORMAT;
           }

           Date d1 = PromosDate.getSelectDate(dDate, dateFormat);

           return d1;
         }
         
         /**
          * �ַ���ת����ʱ��(java.sql.date)
          * @param strTime �ַ���ʱ���ʽ(yyyy-MM-dd HH:mm:ss)
          * @return
          */
         public static Date getStringDate(String strTime){
           try {
             DateFormat df = null;

             if (strTime.indexOf(":")<0) {
               df = new SimpleDateFormat("yyyy-MM-dd");
             }
             else {
               if (strTime.length()>16)
               {
                 df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               }
               else
               {
                 df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
               }
             }
             java.util.Date d=null;
             if ((strTime.trim().equals("")==true)||(strTime.trim().equals("null")==true))
             {
               d = (java.util.Date) df.parse(null);
             }
             else
             {
               d = (java.util.Date) df.parse(strTime);
             }
             Date sqlDate = new Date(d.getTime());
             return sqlDate;
           }
           catch (Exception e) {
             return null;
           }
         }
         /** ����**/  
         
         
         /**
          * �ַ���ת����ʱ��(java.sql.date)
          * @param strTime �ַ���ʱ���ʽ(yyyy-MM-dd HH:mm:ss)
          * @return
          */
         public static String getDateFormat(String strTime){
        	 
        	 if (strTime.indexOf(":")<0) {
        		 return "yyyy-MM-dd";
        	 }
        	 else {
        		 if (strTime.length()>16)
        		 {
        			 return "yyyy-MM-dd HH:mi:ss";
        		 }
        		 else
        		 {
        			 return "yyyy-MM-dd HH:mi";
        		 }
        	 }
         }
         /** ����**/  
        
         

}
