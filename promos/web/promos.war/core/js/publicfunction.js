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
       alert("�����������")
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
       alert("�����������")
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
        alert("�����������")
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
      alert("������Ϊ�գ�")
      document.all[sname].focus()
      return false
    }

    if (!(isEmpty(gh)))
    {
    if (maxlength!="nothing")
    {
      if ((gh.length>maxlength)&&(sStyle!="date")&&(sStyle!="DATE"))
      {
      alert("������󳤶�Ϊ"+maxlength+"��")
      document.all[sname].focus()
      return false
      }
    }

    if (((sStyle=="int")||(sStyle=="SMALLINT")||(sStyle=="INTEGER")||(sStyle=="integer")||(sStyle=="INT")) && (!isCharsInBag(gh,"0123456789")))
    {
      alert("����ֻ������������")
      document.all[sname].focus()
      return false
    }
    if (((sStyle=="REAL")||(sStyle=="FLOAT")||(sStyle=="NUMBER")||(sStyle=="LONG")) && (!isCharsInBag(gh,"0123456789.")))
    {
      alert("����ֻ���������֣�")
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
      alert("����������ո�")
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
//-------------------------------------------------�������ڣ���ַ�����ߣ�
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

//--------------------------------------------------�����ַ�
function ischar(s)
{
var errorChar;

if ( s == "" )
{
return true;
}

if ( isWhitespace(s) )
{
alert("������ַ��в��ܰ����ո�������������룡");
return false;
}
errorChar = isCharsInBagEx( s, badChar)
if (errorChar != "" )
{
alert("��������ַ�" + s+"����Ч��,\n\n�벻Ҫ���ַ�������" + errorChar + "!\n\n����������Ϸ����ַ���" );
return false;
}


return true;
}

//���ú���

//---------------------------------------------------�ַ��Ƿ���S��
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

//----------------------------------------------------�ո��ж�
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

//----------------------------------------------------�����ж�
function isleapyear(thisyear)
{
return(((thisyear%4==0) && (thisyear%100!=0)) || (thisyear%400==0))

}

//----------------------------------------------------��S������ַ�
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

//----------------------------------------------------�Ƿ�ѡ��ѡ��ť
function radioselected(radioBth)
{
for(i=0;i<radioBth.length;i++)
{
if(radioBth[i].checked)
return true
}
return false
}

//----------------------------------------------------����ж�
function isYear(s)
{
var Today=new Date()
thisYear=Today.getYear()
var len = s.length;
if (isEmpty(s)){
alert("�����������")
return false;
}
if ( isCharsInBag( s, badChar)){
alert("�������Ƿ�")
return false;
}
if ((len!=4)){
alert("�������λ��");
return false;
}
if(!isCharsInBag (s, "0123456789")){
alert("����һ�������������Ƿ�Ϊ���֣�");
return false;
}
if (s<1800){
alert("���Ҫ����1800");
return false;
}
// if ((thisYear-s)<0){
// alert("���Ҫ���ܴ���"+thisYear);
// return false;
// }
return true;
}

//---------------------------------------------------Email�ж�
function isEmail(s)
{
if (isEmpty(s))
{
alert("�����E-mail��ַ����Ϊ�գ������룡");
return false;
}
//is s contain whitespace
if (isWhitespace(s))
{
alert("�����E-mail��ַ�в��ܰ����ո�������������룡");
return false;
}
var i = 1;
var len = s.length;

if (len > 40)
{
alert("E-mail��ַ���Ȳ��ܳ���40λ!");
return false;
}

pos1 = s.indexOf("@");
pos2 = s.indexOf(".");
pos3 = s.lastIndexOf("@");
pos4 = s.lastIndexOf(".");
if ((pos1 <= 0)||(pos1 == len)||(pos2 <= 0)||(pos2 == len)){
alert("��������Ч��E-mail��ַ��");
return false;
}
else{
if( (pos1 == pos2 - 1) || (pos1 == pos2 + 1)
|| ( pos1 != pos3 ) //find two @
|| ( pos4 < pos3 ) ) //. should behind the '@'
{
alert("��������Ч��E-mail��ַ��");
return false;
}
}

if ( !isCharsInBag( s, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-_@"))
{
alert("email��ַ��ֻ�ܰ����ַ�ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-_@\n" + "����������" );
return false;
}
return true;
}

//--------------------------------------------------�����ж�
function isNum(s)
{
if (isEmpty(s)){
alert("������������")
return false;
}
if(!isCharsInBag (s, "0123456789")){
alert("����һ����������Ƿ�Ϊ���֣�");
return false;
}
// if (s<1){
// alert("���������Ҫ����0��");
// return false;
// }
// if (s>2000){
// alert("���������ҪС��2000��");
// return false;
// }
return true;
}

//--------------------------------------------------���֤�ж�
function isCardNum(s){
if (isEmpty(s)){
alert("������������")
return false;
}
if(!isCharsInBag (s, "0123456789")){
alert("����һ����������Ƿ�Ϊ����?");
return false;
}
if (s.length==15 || s.length==18){
return true;
}else{
alert("��������ֳ���Ϊ15λ����18λ��");
return false;
}
}

//--------------------------------------------------���������ж�
function isZipCode(s){
if (!isEmpty(s)){
if(!isCharsInBag (s, "0123456789")){
alert("����һ����������Ƿ�Ϊ���֣�");
return false;
}
if (s.length==6){
return true;
}else{
alert("������������볤��Ϊ6��");
return false;
}
} else {
return true;
}
}

//--------------------------------------------------�����ж�
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
        alert("����ֻ����������,��ʽΪ:��YYYY-MM-DD����")
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
       alert("����ֻ����������,��ʽΪ:��YYYY-MM-DD����")
       return false;
    }
    if (!((1<=month) && (12>=month) && (31>=day) && (1<=day)) )
    {
      alert ("������·ݻ�������");
      return false;
    }
    if (!((year % 4)==0) && (month==2) && (day==29))
    {
       alert("��һ�겻�����꣡");
       return false;
    }
    if ((month<=7) && ((month % 2)==0) && (day>=31))
    {
      alert ("�����ֻ��30�죡");
      return false;
    }
    if ((month>=8) && ((month % 2)==1) && (day>=31))
    {
       alert ("�����ֻ��30�죡");
       return false;
    }
    if ((month==2) && (day==30))
    {
      alert("2����Զû����һ�죡");
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
        //�ָ�ȫ���ڳ�ΪDATE��TIME
       if (fullDate.indexOf(" ")!=-1)
       {
            strtemp_arr = fullDate.split(" ");
            theDate =strtemp_arr[0];
            theTime =strtemp_arr[1];
       }
        //�ж����ڸ�ʽ
        if (!chkdate(theDate)) return false;

       //�ָ�TIME
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
      //------------minute begin-------------------��ȷ����
      if (time_arr.length==2)
      {
              //Сʱ�Ƿ�Ϊ����
              if (!isNumeric(time_arr[0]))
              {
                      alert("Сʱֻ��������!");
                      return false;
              }else{
                      //Сʱ�Ƿ����0
                      if (parseInt(time_arr[0])<0)
                      {
                             alert("Сʱ����С��0!");
                              return false;
                      }
                      //Сʱ�Ƿ����12
                      if (parseInt(time_arr[0])>=24)
                      {
                              alert("Сʱ���ܴ���24!");
                              return false;
                      }
              }
              //�����Ƿ�Ϊ����
              if (!isNumeric(time_arr[1]))
              {
                      alert("����ֻ��������!");
                      return false;
              }else{
                      //�Ƿ����0
                      if (parseInt(time_arr[1])<0)
                      {
                              alert("���Ӳ���С��0!");
                              return false;
                      }
                      //�Ƿ����59
                      if (parseInt(time_arr[1])>59)
                      {
                              alert("���Ӳ��ܴ���59!");
                              return false;
                      }
              }
      }
      //------------minute end----------------------------

//------------second begin-------------------��ȷ����
      if (time_arr.length==3)
      {
              //Сʱ�Ƿ�Ϊ����
              if (!isNumeric(time_arr[0]))
              {
                      alert("Сʱֻ��������!");
                      return false;
              }else{
                      //Сʱ�Ƿ����0
                      if (parseInt(time_arr[0])<0)
                      {
                             alert("Сʱ����С��0!");
                              return false;
                      }
                      //Сʱ�Ƿ����12
                      if (parseInt(time_arr[0])>=24)
                      {
                              alert("Сʱ���ܴ���24!");
                              return false;
                      }
              } 
              //�����Ƿ�Ϊ����
              if (!isNumeric(time_arr[1]))
              {
                      alert("����ֻ��������!");
                      return false;
              }else{
                      //�Ƿ����0
                      if (parseInt(time_arr[1])<0)
                      {
                              alert("���Ӳ���С��0!");
                              return false;
                      }
                      //�Ƿ����59
                      if (parseInt(time_arr[1])>59)
                      {
                              alert("���Ӳ��ܴ���59!");
                              return false;
                      }
              }
              //�����Ƿ�Ϊ����

              if (!isNumeric(time_arr[2]))
              {
                      alert("����ֻ��������!");
                      return false;
              }else{
                      //�Ƿ����0
                      if (parseInt(time_arr[2])<0)
                      {
                              alert("��������С��0!");
                              return false;
                      }
                      //�Ƿ����59
                      if (parseInt(time_arr[2])>59)
                      {
                              alert("�������ܴ���59!");
                              return false;
                      }
              }
      }
      //-----------second end-------------------
  }
       return true;
}

//�ж��Ƿ�Ϊ����
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