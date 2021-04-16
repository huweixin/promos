function item(id,pid,title,pl,fc) {
  this.id = id;
  this.pid = pid;
  this.title = title;
  this.pageLink = pl;
  this.fcolor = fc;
}
var i=0;
var treeItem=new Array();
var selectItem=new Array();
function addItem(id,pid,title,pl,fc) {
  treeItem[i++] = new item(id,pid,title,pl,fc);
}
function init() { //��ʼ��
  var s = "<table border=0 cellspacing=0 cellpadding=0 height=0>";
  for (j = 0; j < treeItem.length; j++ ) {

    if (getNoteUpID(treeItem[j].pid) == -10000) {
      s += "<tr><td id='n" + treeItem[j].id + "'>";
      s += "<a onclick=expand('" + treeItem[j].id + "') id='s" + treeItem[j].id;
      s += "' href='javascript:void(0)'><img align='middle' name= 'i"+treeItem[j].id+"' src='" + getMark(treeItem[j].id);
      if(treeItem[j].fcolor == ""){
        s += "' border=0></a> " + treeItem[j].pageLink + treeItem[j].title + " </a></td></tr>";
      }
      else{
	    s += "' border=0></a> " + treeItem[j].pageLink + treeItem[j].fcolor + treeItem[j].title + "</font></a></td></tr>";
      }
    }
  }
  s += "</table>";
  treedir.innerHTML = s;
}

//ָ������ʾ�ض��ڵ��ʼ��
function initByParentNote(parent){

  var s = "<table border=0 cellspacing=0 cellpadding=0 height=0>";
  for (j = 0; j < treeItem.length; j++ ) {

    //if (treeItem[j].pid == parent || getNoteUpID(treeItem[j].pid) == -10000) {
    if (treeItem[j].pid == parent ) {
      s += "<tr><td id='n" + treeItem[j].id + "'>";
      s += "<a onclick=expand('" + treeItem[j].id + "') id='s" + treeItem[j].id;
      s += "' href='javascript:void(0)'><img align='middle' name= 'i"+treeItem[j].id+"' src='" + getMark(treeItem[j].id);
      if(treeItem[j].fcolor == ""){
        s += "' border=0></a> " + treeItem[j].pageLink + treeItem[j].title + " </a></td></tr>";
      }
      else{
	    s += "' border=0></a> " + treeItem[j].pageLink + treeItem[j].fcolor + treeItem[j].title + "</font></a></td></tr>";
      }
    }
  }
  s += "</table>";
  treedir.innerHTML = s;


}

//ָ������ʾ�ض��ڵ��ʼ��
function initByParentNoteDevices(parent){

  var s = "<table border=0 cellspacing=0 cellpadding=0 height=0>";
  for (j = 0; j < treeItem.length; j++ ) {

    if (treeItem[j].pid == parent) {
      s += "<tr><td id='n" + treeItem[j].id + "'>";
      s += "<a onclick=expand('" + treeItem[j].id + "') id='s" + treeItem[j].id;
      s += "' href='javascript:void(0)'><img align='middle' name= 'i"+treeItem[j].id+"' src='" + getMark(treeItem[j].id);
      if(treeItem[j].fcolor == ""){
        s += "' border=0></a> " + treeItem[j].pageLink + treeItem[j].title + " </a></td></tr>";
      }
      else{
	    s += "' border=0></a> " + treeItem[j].pageLink + treeItem[j].fcolor + treeItem[j].title + "</font></a></td></tr>";
      }
    }
  }
  s += "</table>";
  treedir.innerHTML = s;


}

function getMark(id) {//"+","-","*"
  var n = countNode(id);
  if (n>0) return "/promos/images/TreeClose.gif"
    else return "/promos/images/TreeOpen.gif";
}

function changeMark(id) {//ת��"+","-"
  var txt = eval("s"+id).childNodes[0].src.replace(/(.+)[\\/]/,"");
  var s = txt == "TreeClose.gif" ? "/promos/images/TreeOpen.gif" : "/promos/images/TreeClose.gif";
  eval("s"+id).childNodes[0].src = s;
  eval("s"+id).blur();
}

function changeMarkByNote(id) {//ת��ָ�����"+","-"
  if(isLastNote(id)){
  }
  else{
    var txt = document.images["i"+id].src.replace(/(.+)[\\/]/,"");
    var s = txt == "TreeClose.gif" ? "/promos/images/TreeOpen.gif" : "/promos/images/TreeClose.gif";
    document.images["i"+id].src = s;
  }
}

function countNode(id) {//children��Ŀ
  var n = 0;
  for(k = 0; k < treeItem.length; k++){
    if (treeItem[k].pid == id && treeItem[k].id != treeItem[k].pid) n++;
  }
  return n;
}

function expand(id) {//չ����£
  var n = countNode(id);
  if (n == 0) {
    eval("s"+id).blur();
    //alert('it\'s the end!');
    return;
  }
  if (document.getElementById("t"+id)) {
	removeElement(eval("t"+id));
    //eval("t"+id).removeNode(true);
    changeMark(id);
    return;
  }
  changeMark(id);
  var s = "<table border=0 id='t" + id + "' cellspacing=0 cellpadding=0 style=\"position:relative;left:15\">";
  for (j = 0; j < treeItem.length; j++) {
    if (treeItem[j].pid == id && treeItem[j].id != id) {
      s += "<tr><td id='n" + treeItem[j].id + "'>";
      s += "<a onclick=expand('" + treeItem[j].id + "') id='s" + treeItem[j].id + "' href='javascript:void(0)'>";
      s += "<img align='middle' name= 'i"+treeItem[j].id+"' src='" + getMark(treeItem[j].id);
      if(treeItem[j].fcolor == ""){
	s += "' border=0></a> " + treeItem[j].pageLink + treeItem[j].title + " </a></td></tr>";
      }
      else{
      	s += "' border=0></a> " + treeItem[j].pageLink + treeItem[j].fcolor + treeItem[j].title + "</font></a></td></tr>";
      }
    }
   }
   s += "</table>";
   eval("n"+id).innerHTML += s;
}

function removeElement(_element){
    var _parentElement = _element.parentNode;
    if(_parentElement){
           _parentElement.removeChild(_element);
    }
}

//ָ����ʾ�׽ڵ��ʼ��
function initByFirstNote() { //��ʼ����һ���ڵ�

  //��ʼ��
  init();

  //�򿪵�һ���ڵ�
  if(treeItem.length > 0){
    openSelectNote(treeItem[0].id);
    changeMarkByNote(treeItem[0].id);
  }

}

//Ѳ��ϵͳˢ�½ڵ�
function chekcupUpdateByParentNote(note){
//��ʼ��
initByParentNote(0);

//�õ�ָ���ڵ�������֮��Ľڵ�����
getAllPathNoteChekcup(note);

//�Ը���㿪ʼ��ʾ�ڵ�����
var itemLength = selectItem.length;
for(var i=itemLength-1;i>=0;i--){
  //alert("��"+selectItem[i]);

  if(selectItem[i] > 0){
  //�򿪽ڵ�
  openSelectNote(selectItem[i]);
  //ˢ�½ڵ�
  changeMarkByNote(selectItem[i]);
  }
  //alert("ok!");

}

}



//ˢ�µ�ָ���ڵ�
function updateByParentNote(note){

//��ʼ��
initByParentNote(0);

//�õ�ָ���ڵ�������֮��Ľڵ�����
getAllPathNote(note);

//�Ը���㿪ʼ��ʾ�ڵ�����
var itemLength = selectItem.length;
for(var i=itemLength-1;i>=0;i--){
  //alert(selectItem[i]);

  //�򿪽ڵ�
  openSelectNote(selectItem[i]);
  //ˢ�½ڵ�
  changeMarkByNote(selectItem[i]);

}


//�򿪸���㣬��ת���ڵ�ͼ��
//openSelectNote(p_note);
//changeMarkByNote(p_note);

}

//ˢ�µ�ָ���ڵ�
function updateByParentNote(pnote,note){

//��ʼ��
initByParentNote(pnote);

if(note > 0){

//�õ�ָ���ڵ�������֮��Ľڵ�����
getAllPathNote(pnote,note);


//�Ը���㿪ʼ��ʾ�ڵ�����
var itemLength = selectItem.length;
for(var i=itemLength-1;i>=0;i--){
  //�򿪽ڵ�
  //alert(selectItem[i]);
  openSelectNote(selectItem[i]);
  //ˢ�½ڵ�
  changeMarkByNote(selectItem[i]);


}
}


//�򿪸���㣬��ת���ڵ�ͼ��
//openSelectNote(p_note);
//changeMarkByNote(p_note);

}

//�õ�ָ���ڵ�������֮��Ľڵ�����
function getAllPathNoteChekcup(note){
  selectItem=new Array();
  var j = 0;
  var tl= treeItem.length;
  var tNote = note;
  var btNote = note;

  //ѭ���õ��ڵ�
  while(true){
    //�õ���ǰ�ڵ�ĸ��ڵ�
    btNote = tNote;
    selectItem[j++] = tNote;
    //alert(tNote);
    tNote = getNoteUpID(tNote);
    //ֱ�������
    if(tNote == "0" || tNote == "-10000"){
      break;
    }
  }
}


//�õ�ָ���ڵ�������֮��Ľڵ�����
function getAllPathNote(note){
  selectItem=new Array();
  var j = 0;
  var tl= treeItem.length;
  var tNote = note;
  var btNote = note;

  //ѭ���õ��ڵ�
  while(true){
    //�õ���ǰ�ڵ�ĸ��ڵ�
    btNote = tNote;
    selectItem[j++] = tNote;
    tNote = getNoteUpID(tNote);
    //ֱ�������
    if(tNote == "0" || tNote == "-10000"){
      break;
    }
  }
}

//�õ�ָ���ڵ�������֮��Ľڵ�����
function getAllPathNote(pnote,note){
  selectItem=new Array();
  var j = 0;
  var tl= treeItem.length;
  var tNote = note;
  var btNote = note;

  //ѭ���õ��ڵ�
  while(true){
    //�õ���ǰ�ڵ�ĸ��ڵ�
    btNote = tNote;

    selectItem[j++] = tNote;

    tNote = getNoteUpID(tNote);
    //ֱ�������
    if(tNote == "0" || tNote == "-10000"||tNote == pnote){
      break;
    }

  }
}


function initByNote(note) { //ָ����ʼ���ʼ��

  //��ʼ��
  init();

  //����ָ���ڵ�ĸ��ڵ㣬ֱ����һ���ڵ�
  var tpid = getNoteUpID(note);
  var old_id = note;
  while(tpid != -10000){
      old_id = tpid;
      tpid = getNoteUpID(tpid);
  }

  //�ҵ���һ���ڵ�󣬴򿪸ýڵ�
  //openSelectNote(old_id);
  changeMarkByNote(old_id);

  //�������ҽڵ����ϵĽڵ�

  /*


  //����ǵ�һ���ڵ㣬ֱ����ʾ��ǰ��һ���ڵ��µ��ӽڵ�
  var tnote = getNoteUpID(note);

  if(tnote == "-1"){
    //�򿪽ڵ��ǵ�һ���ڵ�ʱ��ֱ����ʾ
  }
  else if(tnote == "0"){
    openSelectNote(note);
    changeMarkByNote(note);
  }
  else{



    //������ǵ�һ���ڵ㣬���ұ��ڵ�ĵ�һ���ڵ�
    tnote = getNoteFLBH(note);

    //�Ա��ڵ�ĵ�һ���ڵ㿪ʼ����ʾ
    openSelectNote(tnote);
    changeMarkByNote(tnote);

    //���ڵ���ʾ·���ϵĽڵ�
    var tempnote = openCurrentLevel(tnote,note);

    //ѭ�����ӽڵ㣬ֱ����ʾ���ڵ�
    while(tempnote != -1 && tempnote != note){
    tempnote = openCurrentLevel(tempnote,note);
    if(tempnote  == note){
      break;
     }
   }
   //�����ʾ���ڵ�
   openSelectNote(note);
   changeMarkByNote(note);
  }*/

}

//��ѡ���ڵ�������ӽڵ㣬ת��ͼƬ����������ѡ��ͼƬ��ʾ·���ϵ��ӽڵ�
function openCurrentLevel(note,snote){

    //��ѡ���ڵ��·���ϵı���νڵ�
	var rnote = -1;

	  var tl = treeItem.length;
	  var j = 0;
	  for(j=0;j<tl;j++){

		//�����ѡ���ڵ���ӽڵ�
		if(treeItem[j].pid == note){
			//������ڵ���ѡ���ڵ����ʾ·���ϣ����ر��ڵ�
			if(isSubNoteByNote(treeItem[j].id,snote)){

				if(treeItem[j].id == snote){
					openSelectNote(treeItem[j].id);
				}
				else{
					openSelectNote(treeItem[j].id);
				}

				changeMarkByNote(treeItem[j].id);
				rnote = treeItem[j].id;
			}
		}

	  }

	return rnote;
}

//��ѡ���Ľڵ㣬��ת���ڵ��ͼƬ
function openSelectNote(note){
   if(!isLastNote(note)){
   var noteSize = treeItem.length;
   var s = "<table border=0 id='t" + note + "' cellspacing=0 cellpadding=0 style=\"position:relative;left:15\">";
   for (j = 0; j < noteSize; j++) {
	 if (treeItem[j].pid == note && treeItem[j].id != note) {
		s += "<tr><td id='n" + treeItem[j].id + "'>";
		s += "<a onclick=expand('" + treeItem[j].id + "') id='s" + treeItem[j].id + "' href='javascript:void(0)'>";
		s += "<img align='middle' name= 'i"+treeItem[j].id+"' src='" + getMark(treeItem[j].id);

		if(treeItem[j].fcolor == ""){
				s += "' border=0></a> " + treeItem[j].pageLink + treeItem[j].title + " </a></td></tr>";
		}
		else{
				s += "' border=0></a> " + treeItem[j].pageLink + treeItem[j].fcolor + treeItem[j].title + "</font></a></td></tr>";
		}



	 }
   }
   s += "</table>";
   eval("n"+note).innerHTML += s;
   }
}

//���Խڵ��Ƿ������ս��,�����ս�㣬����True���������ս�㣬����False
function isLastNote(note){
	var noteSize = treeItem.length;
	var j = 0;
	for(j=0;j<noteSize;j++){
		if(treeItem[j].pid == note){
			return false;
		}
	}
	return true;
}

//�жϵ�ǰ�ӽڵ��Ƿ��ڱ�ѡ���Ľڵ���ʾ·����
 function isSubNoteByNote(subnote,note){

	var tnote = note;

	//��ѯ
	while(true){

	  tnote = getNoteUpID(tnote);
	  if(tnote == subnote){
		  return 1;
	  }
	  if(tnote == 0 || tnote == -10000){
		  return 0;
	  }

	}

	return 0;

 }


//�õ�ָ���ڵ�ĵ�һ���ڵ��id
function getNoteFLBH(note){
    var tl= treeItem.length;
	var tNote = note;
	var btNote = note;

	//ѭ���õ��ڵ�
	while(true){
		//�õ���ǰ�ڵ�ĸ��ڵ�
		btNote = tNote;
		tNote = getNoteUpID(tNote);
		if(tNote == "0" || tNote == "-10000"){
			break;
		}
	}

	//���õ���һ���ڵ�ı��btNote
	return btNote;
}

//�õ��ڵ����һ���ڵ�
function getNoteUpID(note){
  var tl= treeItem.length;
  var j = 0;
  for(j=0;j<tl;j++){
	  //����ҵ��ڵ�
	  if(treeItem[j].id == note){
		  return treeItem[j].pid;
	  }
  }
  return "-10000";
}




