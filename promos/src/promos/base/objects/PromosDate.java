package promos.base.objects;

//import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import java.sql.Date;

import java.io.Serializable;

/**
 *
 * <p>标题: 类 SeperpDate </p>
 * <p>描述: 时间日期类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 *
 * @author 胡维新
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
   * 构建
   */
  public PromosDate() {
  }

  /**
   * 得到两个日期之间天数，不足一天略去
   *
   * @param strDay1 起始日期
   * @param strDay2 结束日期
   * @return 相隔天数
   * @throws Exception 异常
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

    //一天
    long dayTime = 60 * 60 * 24*1000;

    return (int) (allTime / dayTime);
  }

  /**
   * 得到两个时间之间的时间段
   *
   * @param BeginTime 开始时间
   * @param EndTime 结束时间
   * @param intFormat 返回的日期格式
   * @return 时间段
   * @throws Exception
   */
  public static double getTimeDistence(java.sql.Date BeginTime,java.sql.Date EndTime,
                                       int intFormat)
      throws Exception {
    double dblReturn = 0;

    long lngBeginTime = BeginTime.getTime();
    long lngEndTime = EndTime.getTime();
    long lngDistence = lngEndTime - lngBeginTime;

    long lngMinuteTime = 60 * 1000;//分钟
    long lngHourTime = 60 * lngMinuteTime;//小时
    long lngDayTime = 24 * lngHourTime;//天

    switch (intFormat) {
      //计算天数
      case DD:
        dblReturn = ((0.1 * lngDistence) / (0.1 * lngDayTime));
        break;
      //计算小时
      case HH:
        dblReturn = ((0.1 * lngDistence) / (0.1 * lngHourTime));
        break;
      //计算分钟
      case MI:
        dblReturn = ((0.1 * lngDistence) / (0.1 * lngMinuteTime));
        break;
    }
    return dblReturn;
  }

  /**
   * 得到年月日和星期几。2003-03-02 00:00:00
   *
   * @param strDate 日期格式
   * @return 年月日和星期几
   * @throws Exception 异常
   */
  public static String getYearMonthDayWeek(String strDate) throws Exception {
    //2003年3月2日 (星期一)
    StringBuffer rs = new StringBuffer("");
    //
    //根据字符串创建时间
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
      rs.append("年");

      int indexMonth = tDate.indexOf("-");
      String strMonth = tDate.substring(0, indexMonth);
      tDate = tDate.substring(indexMonth + 1);
      //System.out.print("Month:"+strMonth);
      int Month = Integer.valueOf(strMonth).intValue();
      rs.append(Month);
      rs.append("月");

      String strDay = tDate.substring(0, 2);
      int Day = Integer.valueOf(strDay).intValue();
      //tDate = tDate.substring(indexDay);
      //System.out.print("Day:"+strDay);
      rs.append(Day);
      rs.append("日 (星期");

      //根据年月日创建时间
      Calendar c = Calendar.getInstance();
      c.set(Year, Month - 1, Day);
      int rsindex = c.get(Calendar.DAY_OF_WEEK);
      //System.out.print("日期:"+rsindex);
      switch (rsindex) {
        case 1:
          rs.append("日");
          break;
        case 2:
          rs.append("一");
          break;
        case 3:
          rs.append("二");
          break;
        case 4:
          rs.append("三");
          break;
        case 5:
          rs.append("四");
          break;
        case 6:
          rs.append("五");
          break;
        case 7:
          rs.append("六");
          break;
      }
      rs.append(")");

    }

    //
    return rs.toString();
  }

  /**
   * 长格式日期字符串转换成短格式
   *
   * @return 短格式日期字符串
   */
  public static String longToShort(String longFormat) throws Exception {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    return sdf1.format(sdf.parse(longFormat));
  }

  /**
   * 得到当前日期
   *
   * @return 当前日期
   */
  public static Date getCurrentDate() {

    Calendar currentCalendar = Calendar.getInstance();

    return new Date(currentCalendar.getTime().getTime());
  }

  /**
   * 得到指定日期
   *
   * @param strDate 日期
   * @return 日期
   */
  public static Date getSelectDate(String strDate) {
    try {
      //得到日期
      SimpleDateFormat formatter = new SimpleDateFormat(SIMPLEFORMATSTRING);
      return new Date( (formatter.parse(strDate)).getTime());
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new Date(0);
  }


  /**
   * 得到指定日期
   *
   * @param strDate 日期
   * @return 日期
   */
  public static Date getSelectDate(String strDate, String pattern) {
    try {
      //得到日期
      SimpleDateFormat formatter = new SimpleDateFormat(pattern);
      return new Date( (formatter.parse(strDate)).getTime());
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new Date(0);
  }


  /**
   * 得到当前星期几
   *
   * @return 星期几
   */
  public static String getCurrentMonth() {
    return PromosDate.getCurrentMonthByString(PromosDate.toShortFormatString(
        PromosDate.getCurrentDate()));

  }

  /**
   * 得到当前星期几
   *
   * @param strDate 当前日期 如1999-01-12
   * @return 星期几
   */
  public static String getCurrentMonth(String strDate) {
    return PromosDate.getCurrentMonthByString(strDate);
  }

  /**
   * 得到当前星期几
   *
   * @param strDate 当前日期
   * @return 星期几
   */
  public static String getCurrentMonth(Date strDate) {
    return PromosDate.getCurrentMonthByString(PromosDate.toShortFormatString(
        strDate));
  }

  //得到当前星期几
  private static String getCurrentMonthByString(String strDate) {

    //根据字符串创建时间
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

      //根据年月日创建时间
      Calendar c = Calendar.getInstance();
      c.set(Year, Month - 1, Day);
      int rsindex = c.get(Calendar.DAY_OF_WEEK);
      //System.out.print("日期:"+rsindex);
      switch (rsindex) {
        case 1:
          rs = "日";
          break;
        case 2:
          rs = "一";
          break;
        case 3:
          rs = "二";
          break;
        case 4:
          rs = "三";
          break;
        case 5:
          rs = "四";
          break;
        case 6:
          rs = "五";
          break;
        case 7:
          rs = "六";
          break;
      }
    }

    return rs;
  }

  /**
   * 得到默认的日期显示格式
   *
   * @param d 指定的日期
   * @return 指定日期的默认显示格式
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
   * 得到默认的日期格式
   *
   * @param d 指定的日期
   * @return 日期格式
   */
  public static String toDefaultFormat(Date d) {
    return DEFAULTFORMAT;
  }

  /**
   * 得到默认的短日期显示格式
   *
   * @param d
   * @return 短日期显示格式
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
   * 检查日期字符串，如果错误返回空字符串
   *
   * @param strCheckDate 检查日期字符串
   * @param intFormat 返回的日期格式
   * @return 日期显示格式
   */
  public static String checkErrorDateString(String strCheckDate, int intFormat) {

    String rs = "";

    //检测日期字符串是否正确
    if (strCheckDate == null || strCheckDate.compareTo("") == 0) {
      return rs;
    }

    //检测是否是正常时间
    if (strCheckDate.length() < 10 ||
        PromosDate.isFirstStartDate(strCheckDate.substring(0, 10))) {
      return "";
    }

    //检查格式是否正确
    switch (intFormat) {
      //短日期格式
      case YYYY_MM_DD:

        rs = strCheckDate.substring(0, 10);

        break;
        //长日期格式
      case YYYY_MM_DD_HH_MI:

        rs = strCheckDate.substring(0, 16);

        break;
        //全日期格式
      case YYYY_MM_DD_HH_MI_SS:

        rs = strCheckDate.substring(0, 19);

        break;
        //月份日期格式
      case MM_DD:

        rs = strCheckDate.substring(5, 10);

        break;

        //时分格式
      case HH_MI:

        rs = strCheckDate.substring(11, 16);

        break;
        //时分秒格式
      case HH_MI_SS:

        rs = strCheckDate.substring(11, 19);

        break;

        //月日时分秒格式
      case MM_DD_HH_MI_SS:

        rs = strCheckDate.substring(5);

        break;
        //月日时分格式
      case MM_DD_HH_MI:

        rs = strCheckDate.substring(5, 16);
        //如果分为空，删除分
        String rss = strCheckDate.substring(14, 16);
        if (rss.startsWith("0")) {
          rss = rss.substring(1);
        }

        //System.out.print("here --- :"+rss);
        if (rss.compareTo("0") == 0) {
          //rs = strCheckDate.substring(5, 13);//hxh modify 2005.4.13

          //如果时为空，删除时分
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

        //年格式
      case YYYY:
        rs = strCheckDate.substring(0, 4);
        break;

        //月格式
      case MM:
        rs = strCheckDate.substring(5, 7);
        if (rs.startsWith("0")) {
          rs = rs.substring(1);
        }
        break;

        //日格式
      case DD:
        rs = strCheckDate.substring(8, 10);
        if (rs.startsWith("0")) {
          rs = rs.substring(1);
        }
        break;

        //时格式
      case HH:
        rs = strCheckDate.substring(11, 13);
        if (rs.startsWith("0")) {
          rs = rs.substring(1);
        }
        break;

        //分格式
      case MI:
        rs = strCheckDate.substring(14, 16);
        if (rs.startsWith("0")) {
          rs = rs.substring(1);
        }
        break;

        //秒格式
      case SS:
        rs = strCheckDate.substring(17, 19);
        if (rs.startsWith("0")) {
          rs = rs.substring(1);
        }
        break;

      case YYYY_MM:
        rs = strCheckDate.substring(0, 7);
        break;


        //没有格式
      default:
        break;

    }

    return rs;

  }

  //检测日期是否是1970-01-01或1899-12-30
  private static boolean isFirstStartDate(String strDate) {
    if (strDate.compareTo("1970-01-01") == 0 ||
        strDate.compareTo("1899-12-30") == 0 || strDate.compareTo("1969-12-31")==0){
      return true;
    }
    return false;
  }

  /**
   * 得到更改年份的日期
   *
   * @param strDate 日期
   * @param year 年数
   * @param intFormat 显示格式
   * @return 日期
   */
  public static String getMovedDateString(String strDate, int year,
                                          int intFormat) {

    String rs = "";

    try {

      rs = checkErrorDateString(strDate, intFormat);

      if (rs.compareTo("") != 0) {

        //得到日期
        SimpleDateFormat formatter = new SimpleDateFormat(SIMPLEFORMATSTRING);
        Date d = new Date( (formatter.parse(strDate)).getTime());

        //移动日期
        d = PromosDate.getMoveDateByAddDate(d, year, 0, 0, 0, 0, 0);

        //转换格式
        rs = PromosDate.toDefaultString(d);

        //检测是否是非正常日期
        rs = PromosDate.checkErrorDateString(rs, intFormat);

      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return rs;
  }

  /**
   * 得到更改年份的日期
   *
   * @param strDate 日期
   * @param year 年数
   * @param month 月数
   * @param day 年数
   * @param intFormat 显示格式
   * @return 日期
   */
  public static String getMovedDateString(String strDate, int year, int month,
                                          int day, int intFormat) {

    String rs = "";

    try {

      rs = checkErrorDateString(strDate, intFormat);

      if (rs.compareTo("") != 0) {

        //得到日期
        SimpleDateFormat formatter = new SimpleDateFormat(SIMPLEFORMATSTRING);
        Date d = new Date( (formatter.parse(strDate)).getTime());

        //移动日期
        d = PromosDate.getMoveDateByAddDate(d, year, month, day, 0, 0, 0);

        //转换格式
        rs = PromosDate.toDefaultString(d);

        //检测是否是非正常日期
        rs = PromosDate.checkErrorDateString(rs, intFormat);

      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return rs;
  }

  /**
   * 将指定的字符串转换成日期型的数据。
   *
   * @param str 指定的表示日期的字符串。
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
   * 将指定的字符串转换成日期型的数据。
   *
   * @param str 指定的表示日期的字符串。
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
   * 移动被选定的日期
   *
   * @param moveDate 被移动的日期
   * @param year 年
   * @param month 月
   * @param day 日
   * @param hour 时
   * @param minute 分
   * @param second 秒
   * @return 日期
   */
  public static Date getMoveDateByAddDate(Date moveDate, int year, int month,
                                          int day, int hour, int minute,
                                          int second) {

    //得到指定时间
    Calendar cc = Calendar.getInstance();
    cc.setTime(moveDate);

    //移动年
    if (year != 0) {
      cc.add(Calendar.YEAR, year);
    }
    //移动月
    if (month != 0) {
      cc.add(Calendar.MONTH, month);
    }

    //移动日
    if (day != 0) {
      cc.add(Calendar.DAY_OF_YEAR, day);
    }

    //移动时
    if (hour != 0) {
      cc.add(Calendar.HOUR, hour);
    }

    //移动分
    if (minute != 0) {
      cc.add(Calendar.MINUTE, minute);
    }

    //移动秒
    if (second != 0) {
      cc.add(Calendar.SECOND, second);
    }

    //返回
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
      Calendar calendar = Calendar.getInstance(); //实例只能一个
      calendar.setTime(d);
      strYear = String.valueOf(calendar.get(Calendar.YEAR));
      strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
      strTempMonth = String.valueOf(calendar.get(Calendar.MONTH));
      strDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
      strHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
      strMinute = String.valueOf(calendar.get(Calendar.MINUTE));
      if (isShortDate) {
        strDate = strYear + "年" + strMonth + "月" + strDay + "日";
      }
      else {
        strDate = strYear + "年" + strMonth + "月" + strDay + "日" + strHour + "时" +
            strMinute + "分";
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
        return "星期日";
      case Calendar.MONDAY:
        return "星期一";
      case Calendar.TUESDAY:
        return "星期二";
      case Calendar.WEDNESDAY:
        return "星期三";
      case Calendar.THURSDAY:
        return "星期四";
      case Calendar.FRIDAY:
        return "星期五";
      case Calendar.SATURDAY:
        return "星期六";
      default:
        return "日期错误";
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
          * 日期加上天数得到的新日期(lb)
          * @param strDate Date
          * @param dayNumber int
          * @return Date
          */
         public static Date getDateAddDayNumber(String strDate, int dayNumber) {
           return getDateAddDayNumber(getDateAuto(strDate), dayNumber);
         }

         /**
          * 日期加上天数得到的新日期(lb)
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
          * 日期减去天数得到的新日期(lb)
          * @param strDate Date
          * @param dayNumber int
          * @return Date
          */
         public static Date getDateSubDayNumber(String strDate, int dayNumber) {
           return getDateSubDayNumber(getDateAuto(strDate), dayNumber);
         }

         /**
          * 日期减去天数得到的新日期(lb)
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
          * 根据输入的日期长度自动转换成相应长度的Date型日期(lb)
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
          * 字符串转化成时间(java.sql.date)
          * @param strTime 字符串时间格式(yyyy-MM-dd HH:mm:ss)
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
         /** 结束**/  
         
         
         /**
          * 字符串转化成时间(java.sql.date)
          * @param strTime 字符串时间格式(yyyy-MM-dd HH:mm:ss)
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
         /** 结束**/  
        
         

}
