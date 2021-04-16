var badChar = "><,[]{}?/+=|\\'\":;~!@#$%^&`";

function Value_CheckOut(sformat)
{
  var subs
  var npos
  var sname
  var sStyle
  var nstar
  var nstop
  var cannull
  var maxlength
  if (sformat=="") return true;
  nstar=sformat.indexOf('[');
  nstop=sformat.indexOf(']');
  while ((nstar>=0) && (nstop>0))
  {
  if ((nstar>=0) && (nstop>0))
  {
    subs=sformat.substring(1, nstop)
    sformat=sformat.substring(nstop+1, sformat.length)
    npos=subs.indexOf(',')
    if (npos>=0)
    {
      sname=subs.substring(0, npos)
      subs=subs.substring(npos+1, subs.length)
    }
    else
    {
       alert("输入参数错误！")
       return false
    }

    npos=subs.indexOf(',')
    if (npos>=0)
    {
      sStyle=subs.substring(0, npos)
      subs=subs.substring(npos+1, subs.length)
    }
    else
    {
       alert("输入参数错误！")
       return false
    }

    npos=subs.indexOf(',')
    if (npos>=0)
    {
      cannull=subs.substring(0, npos)
      subs=subs.substring(npos+1, subs.length)
    }
    else
    {
      if (subs!="")
      {
        cannull=subs
        subs=""
        }
      else
      {
        alert("输入参数错误！")
        return false
       }
    }

    if (subs!="")
       maxlength=subs
    else
       maxlength="nothing"
    if (document.all[sname])
    {
    gh=document.all[sname].value
    if (((cannull=="0")||(cannull=="N")) && (isEmpty(gh)))
    {
      alert("该域不能为空！")
      document.all[sname].focus()
      return false
    }

    if (!(isEmpty(gh)))
    {
    if (maxlength!="nothing")
    {
      if ((gh.length>maxlength)&&(sStyle!="date")&&(sStyle!="DATE"))
      {
      alert("该域最大长度为"+maxlength+"！")
      document.all[sname].focus()
      return false
      }
    }

    if (((sStyle=="int")||(sStyle=="SMALLINT")||(sStyle=="INTEGER")||(sStyle=="integer")||(sStyle=="INT")) && (!isCharsInBag(gh,"0123456789")))
    {
      alert("该域只能输入整数！")
      document.all[sname].focus()
      return false
    }
    if (((sStyle=="REAL")||(sStyle=="FLOAT")||(sStyle=="NUMBER")||(sStyle=="LONG")) && (!isCharsInBag(gh,"0123456789.")))
    {
      alert("该域只能输入数字！")
      document.all[sname].focus()
      return false
    }

    if (((sStyle=="date")||(sStyle=="DATE")) && (!chkdate(gh)))
    {
      document.all[sname].focus()
      return false
    }
    if (((sStyle=="time")||(sStyle=="TIME")) && (!chkdatetime(gh)))
    {
      document.all[sname].focus()
      return false
    }
    

    if (((sStyle=="space")||(sStyle=="SPACE")) && isWhitespace(gh))
    {
      alert("该域不能输入空格！")
      document.all[sname].focus()
      return false
    }

    if (((sStyle=="email")||(sStyle=="EMAIL"))  && (!isEmail(gh)))
    {
      document.all[sname].focus()
      return false
    }

    if (((sStyle=="zipcode")||(sStyle=="ZIPCODE")) && (!isZipCode(gh)))
    {
      document.all[sname].focus()
      return false
    }
    if (((sStyle=="cardnum")||(sStyle=="CARDNUM")) && (!isCardNum(gh)))
    {
      document.all[sname].focus()
      return false
    }
    if (((sStyle=="forchar")||(sStyle=="FORCHAR")) && (!ischar(gh)))
    {
      document.all[sname].focus()
      return false
    }



    }
    }
  }

  nstar=sformat.indexOf('[');
  nstop=sformat.indexOf(']');
  }
  return true;
}

var newwin = null
//-------------------------------------------------弹出窗口（地址，宽，高）
function newwindow(to,w,h)
{
if (!newwin || newwin.closed)
{
newwin=window.open(to,"indexww","width="+w+",height="+h+",scrollbars")
}
else
{
newwin.close()
newwin=window.open(to,"indexww","width="+w+",height="+h+",scrollbars")
}
newwin.focus()
}

//--------------------------------------------------检验字符
function ischar(s)
{
var errorChar;

if ( s == "" )
{
return true;
}

if ( isWhitespace(s) )
{
alert("输入的字符中不能包含空格符，请重新输入！");
return false;
}
errorChar = isCharsInBagEx( s, badChar)
if (errorChar != "" )
{
alert("您输入的字符" + s+"是无效的,\n\n请不要在字符中输入" + errorChar + "!\n\n请重新输入合法的字符！" );
return false;
}


return true;
}

//公用函数

//---------------------------------------------------字符是否在S中
function isCharsInBag (s, bag)
{
var i;
// Search through string's characters one by one.
// If character is in bag, append to returnString.

for (i = 0; i < s.length; i++)
{
// Check that current character isn't whitespace.
var c = s.charAt(i);
if (bag.indexOf(c) == -1) return false;
}
return true;
}

function isEmpty(s)
{
return ((s == null)||(s.length == 0));
}

//----------------------------------------------------空格判断
function isWhitespace (s)
{
var whitespace = " \t\n\r";
var i;
for (i = 0; i < s.length; i++)
{
var c = s.charAt(i);
if (whitespace.indexOf(c) >= 0)
{
return true;
}
}

return false;
}

//----------------------------------------------------闰年判断
function isleapyear(thisyear)
{
return(((thisyear%4==0) && (thisyear%100!=0)) || (thisyear%400==0))

}

//----------------------------------------------------除S以外的字符
function isCharsInBagEx (s, bag)
{
var i,c;
// Search through string's characters one by one.
// If character is in bag, append to returnString.
for (i = 0; i < s.length; i++)
{
c = s.charAt(i);
if (bag.indexOf(c) > -1)
return c;
}
return "";
}

//----------------------------------------------------是否选择单选按钮
function radioselected(radioBth)
{
for(i=0;i<radioBth.length;i++)
{
if(radioBth[i].checked)
return true
}
return false
}

//----------------------------------------------------年份判断
function isYear(s)
{
var Today=new Date()
thisYear=Today.getYear()
var len = s.length;
if (isEmpty(s)){
alert("必须输入年份")
return false;
}
if ( isCharsInBag( s, badChar)){
alert("年份输入非法")
return false;
}
if ((len!=4)){
alert("年份是四位！");
return false;
}
if(!isCharsInBag (s, "0123456789")){
alert("请检查一下您输入的年份是否为数字！");
return false;
}
if (s<1800){
alert("年份要大于1800");
return false;
}
// if ((thisYear-s)<0){
// alert("年份要不能大于"+thisYear);
// return false;
// }
return true;
}

//---------------------------------------------------Email判断
function isEmail(s)
{
if (isEmpty(s))
{
alert("输入的E-mail地址不能为空，请输入！");
return false;
}
//is s contain whitespace
if (isWhitespace(s))
{
alert("输入的E-mail地址中不能包含空格符，请重新输入！");
return false;
}
var i = 1;
var len = s.length;

if (len > 40)
{
alert("E-mail地址长度不能超过40位!");
return false;
}

pos1 = s.indexOf("@");
pos2 = s.indexOf(".");
pos3 = s.lastIndexOf("@");
pos4 = s.lastIndexOf(".");
if ((pos1 <= 0)||(pos1 == len)||(pos2 <= 0)||(pos2 == len)){
alert("请输入有效的E-mail地址！");
return false;
}
else{
if( (pos1 == pos2 - 1) || (pos1 == pos2 + 1)
|| ( pos1 != pos3 ) //find two @
|| ( pos4 < pos3 ) ) //. should behind the '@'
{
alert("请输入有效的E-mail地址！");
return false;
}
}

if ( !isCharsInBag( s, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-_@"))
{
alert("email地址中只能包含字符ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-_@\n" + "请重新输入" );
return false;
}
return true;
}

//--------------------------------------------------数字判断
function isNum(s)
{
if (isEmpty(s)){
alert("必须输入数字")
return false;
}
if(!isCharsInBag (s, "0123456789")){
alert("请检查一下您输入的是否为数字！");
return false;
}
// if (s<1){
// alert("输入的数字要大于0！");
// return false;
// }
// if (s>2000){
// alert("输入的数字要小于2000！");
// return false;
// }
return true;
}

//--------------------------------------------------身份证判断
function isCardNum(s){
if (isEmpty(s)){
alert("必须输入数字")
return false;
}
if(!isCharsInBag (s, "0123456789")){
alert("请检查一下您输入的是否为数字?");
return false;
}
if (s.length==15 || s.length==18){
return true;
}else{
alert("输入的数字长度为15位或者18位！");
return false;
}
}

//--------------------------------------------------邮政编码判断
function isZipCode(s){
if (!isEmpty(s)){
if(!isCharsInBag (s, "0123456789")){
alert("请检查一下您输入的是否为数字！");
return false;
}
if (s.length==6){
return true;
}else{
alert("输入的邮政编码长度为6！");
return false;
}
} else {
return true;
}
}

//--------------------------------------------------日期判断
function chkdate(datestr)
{
    chkdate
    var lthgh
    lthgh= datestr.length ;
    var tmpy="";
    var tmpm="";
    var tmpd="";
    var status;
    status=0;

    for (i=0;i<lthgh;i++)
    {
      if (datestr.charAt(i)== '-')
      {
        status++;
      }
      if (status>2)
      {
        alert("该域只能输入日期,格式为:【YYYY-MM-DD】！")
        return false;
      }
      if ((status==0) && (datestr.charAt(i)!='-'))
      {
         tmpy=tmpy+datestr.charAt(i)
      }
      if ((status==1) && (datestr.charAt(i)!='-'))
      {
        tmpm=tmpm+datestr.charAt(i)
      }
      if ((status==2) && (datestr.charAt(i)!='-'))
      {
        tmpd=tmpd+datestr.charAt(i)
      }
    }

    year=new String (tmpy);
    month=new String (tmpm);
    day=new String (tmpd)
    if ((tmpy.length!=4) || (tmpm.length>2) || (tmpd.length>2))
    {
       alert("该域只能输入日期,格式为:【YYYY-MM-DD】！")
       return false;
    }
    if (!((1<=month) && (12>=month) && (31>=day) && (1<=day)) )
    {
      alert ("错误的月份或天数！");
      return false;
    }
    if (!((year % 4)==0) && (month==2) && (day==29))
    {
       alert("这一年不是闰年！");
       return false;
    }
    if ((month<=7) && ((month % 2)==0) && (day>=31))
    {
      alert ("这个月只有30天！");
      return false;
    }
    if ((month>=8) && ((month % 2)==1) && (day>=31))
    {
       alert ("这个月只有30天！");
       return false;
    }
    if ((month==2) && (day==30))
    {
      alert("2月永远没有这一天！");
      return false;
    }
    return true;
}

function chkdatetime(fullDate)
{
   var theDate;
   var theTime;
   var date_arr;
   if (fullDate.indexOf(":")==-1&&fullDate.indexOf(" ")==-1)
   {
                 return chkdate(fullDate);
   }
   else
   {
       var strtemp_arr;
       var time_arr;
        //分割全日期成为DATE和TIME
       if (fullDate.indexOf(" ")!=-1)
       {
            strtemp_arr = fullDate.split(" ");
            theDate =strtemp_arr[0];
            theTime =strtemp_arr[1];
       }
        //判断日期格式
        if (!chkdate(theDate)) return false;

       //分割TIME
       if (theTime.length==0)
       {
                              return true;
       }

       time_arr = theTime.split(":");
       //--------------------------------------------------
       if (time_arr.length==1)
       {
       if (!isNumeric(time_arr[0]))
       {
                                      return false;
       }
       }
      //--------------------------------------------------
      //------------minute begin-------------------精确到分
      if (time_arr.length==2)
      {
              //小时是否为数字
              if (!isNumeric(time_arr[0]))
              {
                      alert("小时只能是数字!");
                      return false;
              }else{
                      //小时是否大于0
                      if (parseInt(time_arr[0])<0)
                      {
                             alert("小时不能小于0!");
                              return false;
                      }
                      //小时是否大于12
                      if (parseInt(time_arr[0])>=24)
                      {
                              alert("小时不能大于24!");
                              return false;
                      }
              }
              //分钟是否为数字
              if (!isNumeric(time_arr[1]))
              {
                      alert("分钟只能是数字!");
                      return false;
              }else{
                      //是否大于0
                      if (parseInt(time_arr[1])<0)
                      {
                              alert("分钟不能小于0!");
                              return false;
                      }
                      //是否大于59
                      if (parseInt(time_arr[1])>59)
                      {
                              alert("分钟不能大于59!");
                              return false;
                      }
              }
      }
      //------------minute end----------------------------

//------------second begin-------------------精确到秒
      if (time_arr.length==3)
      {
              //小时是否为数字
              if (!isNumeric(time_arr[0]))
              {
                      alert("小时只能是数字!");
                      return false;
              }else{
                      //小时是否大于0
                      if (parseInt(time_arr[0])<0)
                      {
                             alert("小时不能小于0!");
                              return false;
                      }
                      //小时是否大于12
                      if (parseInt(time_arr[0])>=24)
                      {
                              alert("小时不能大于24!");
                              return false;
                      }
              } 
              //分钟是否为数字
              if (!isNumeric(time_arr[1]))
              {
                      alert("分钟只能是数字!");
                      return false;
              }else{
                      //是否大于0
                      if (parseInt(time_arr[1])<0)
                      {
                              alert("分钟不能小于0!");
                              return false;
                      }
                      //是否大于59
                      if (parseInt(time_arr[1])>59)
                      {
                              alert("分钟不能大于59!");
                              return false;
                      }
              }
              //秒数是否为数字

              if (!isNumeric(time_arr[2]))
              {
                      alert("秒数只能是数字!");
                      return false;
              }else{
                      //是否大于0
                      if (parseInt(time_arr[2])<0)
                      {
                              alert("秒数不能小于0!");
                              return false;
                      }
                      //是否大于59
                      if (parseInt(time_arr[2])>59)
                      {
                              alert("秒数不能大于59!");
                              return false;
                      }
              }
      }
      //-----------second end-------------------
  }
       return true;
}

//判断是否为数字
function isNumeric(nValue) {
              return nValue.search(/^\-?[\d]+(\.\d+)?$/) == 0
}

function getCurrentTime(obj,type){
  var ob=obj;
  if((ob.value=="")&&(ob.readOnly==false)){
    var curTime=new Date();
    var curYear=curTime.getYear();
    var curMonth=curTime.getMonth()+1;
	var curDate=curTime.getDate();

	var curHour=curTime.getHours();
	var curMinute=curTime.getMinutes();

    if(curMonth<10){
      curMonth='0'+curMonth;
    }
    if(curDate<10){
      curDate='0'+curDate;
    }
    if(curHour<10){
      curHour='0'+curHour;
    }
    if(curMinute<10){
      curMinute='0'+curMinute;
    }
    if (type==1){
      ob.value=curYear+"-"+curMonth+"-"+curDate;
    }
    else
    {
      ob.value=curYear+"-"+curMonth+"-"+curDate+" "+curHour+":"+curMinute;
    }

  }

 }

function GetNumberOfsting1(snum) //v4.01
{
  var sTmp="";
  while (snum!="")
  {
     if(isCharsInBag (snum.substring(0,1), "0123456789")) sTmp=sTmp+snum.substring(0,1);
     else
     return sTmp;
     snum=snum.substring(1,snum.length);
  }
     return sTmp;
}

function myBrowser(){
    var userAgent = navigator.userAgent; 
    var isOpera = userAgent.indexOf("Opera") > -1;
    if (isOpera) {
        return "Opera"
    }; 
    if (userAgent.indexOf("Firefox") > -1) {
        return "FF";
    } 
    if (userAgent.indexOf("Chrome") > -1){
  return "Chrome";
 }
    if (userAgent.indexOf("Safari") > -1) {
        return "Safari";
    }
    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
        return "IE";
    }; 
}


function jh_getIframByid(siframid){
	var mb = myBrowser();
	if ("IE" == mb) {
	    return document.getElementById(siframid).window;
	}
	else
	if ("FF" == mb) {
	    return document.getElementById(siframid).window;
	}
	else
	if ("Chrome" == mb) {
	    return document.getElementById(siframid).contentWindow;
	}
	else
	if ("Opera" == mb) {
	    return document.getElementById(siframid).window;
	}
	else
	if ("Safari" == mb) {
	    return document.getElementById(siframid).window;
	}
	else
	{
	    return document.getElementById(siframid).window;
	}
}