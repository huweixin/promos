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
function init() { //初始化
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

//指定仅显示特定节点初始化
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

//指定仅显示特定节点初始化
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

function changeMark(id) {//转换"+","-"
  var txt = eval("s"+id).childNodes[0].src.replace(/(.+)[\\/]/,"");
  var s = txt == "TreeClose.gif" ? "/promos/images/TreeOpen.gif" : "/promos/images/TreeClose.gif";
  eval("s"+id).childNodes[0].src = s;
  eval("s"+id).blur();
}

function changeMarkByNote(id) {//转换指定编号"+","-"
  if(isLastNote(id)){
  }
  else{
    var txt = document.images["i"+id].src.replace(/(.+)[\\/]/,"");
    var s = txt == "TreeClose.gif" ? "/promos/images/TreeOpen.gif" : "/promos/images/TreeClose.gif";
    document.images["i"+id].src = s;
  }
}

function countNode(id) {//children数目
  var n = 0;
  for(k = 0; k < treeItem.length; k++){
    if (treeItem[k].pid == id && treeItem[k].id != treeItem[k].pid) n++;
  }
  return n;
}

function expand(id) {//展开收拢
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

//指定显示首节点初始化
function initByFirstNote() { //初始化第一个节点

  //初始化
  init();

  //打开第一个节点
  if(treeItem.length > 0){
    openSelectNote(treeItem[0].id);
    changeMarkByNote(treeItem[0].id);
  }

}

//巡检系统刷新节点
function chekcupUpdateByParentNote(note){
//初始化
initByParentNote(0);

//得到指定节点与根结点之间的节点序列
getAllPathNoteChekcup(note);

//自根结点开始显示节点序列
var itemLength = selectItem.length;
for(var i=itemLength-1;i>=0;i--){
  //alert("打开"+selectItem[i]);

  if(selectItem[i] > 0){
  //打开节点
  openSelectNote(selectItem[i]);
  //刷新节点
  changeMarkByNote(selectItem[i]);
  }
  //alert("ok!");

}

}



//刷新到指定节点
function updateByParentNote(note){

//初始化
initByParentNote(0);

//得到指定节点与根结点之间的节点序列
getAllPathNote(note);

//自根结点开始显示节点序列
var itemLength = selectItem.length;
for(var i=itemLength-1;i>=0;i--){
  //alert(selectItem[i]);

  //打开节点
  openSelectNote(selectItem[i]);
  //刷新节点
  changeMarkByNote(selectItem[i]);

}


//打开根结点，并转换节点图标
//openSelectNote(p_note);
//changeMarkByNote(p_note);

}

//刷新到指定节点
function updateByParentNote(pnote,note){

//初始化
initByParentNote(pnote);

if(note > 0){

//得到指定节点与根结点之间的节点序列
getAllPathNote(pnote,note);


//自根结点开始显示节点序列
var itemLength = selectItem.length;
for(var i=itemLength-1;i>=0;i--){
  //打开节点
  //alert(selectItem[i]);
  openSelectNote(selectItem[i]);
  //刷新节点
  changeMarkByNote(selectItem[i]);


}
}


//打开根结点，并转换节点图标
//openSelectNote(p_note);
//changeMarkByNote(p_note);

}

//得到指定节点与根结点之间的节点序列
function getAllPathNoteChekcup(note){
  selectItem=new Array();
  var j = 0;
  var tl= treeItem.length;
  var tNote = note;
  var btNote = note;

  //循环得到节点
  while(true){
    //得到当前节点的父节点
    btNote = tNote;
    selectItem[j++] = tNote;
    //alert(tNote);
    tNote = getNoteUpID(tNote);
    //直到根结点
    if(tNote == "0" || tNote == "-10000"){
      break;
    }
  }
}


//得到指定节点与根结点之间的节点序列
function getAllPathNote(note){
  selectItem=new Array();
  var j = 0;
  var tl= treeItem.length;
  var tNote = note;
  var btNote = note;

  //循环得到节点
  while(true){
    //得到当前节点的父节点
    btNote = tNote;
    selectItem[j++] = tNote;
    tNote = getNoteUpID(tNote);
    //直到根结点
    if(tNote == "0" || tNote == "-10000"){
      break;
    }
  }
}

//得到指定节点与根结点之间的节点序列
function getAllPathNote(pnote,note){
  selectItem=new Array();
  var j = 0;
  var tl= treeItem.length;
  var tNote = note;
  var btNote = note;

  //循环得到节点
  while(true){
    //得到当前节点的父节点
    btNote = tNote;

    selectItem[j++] = tNote;

    tNote = getNoteUpID(tNote);
    //直到根结点
    if(tNote == "0" || tNote == "-10000"||tNote == pnote){
      break;
    }

  }
}


function initByNote(note) { //指定起始点初始化

  //初始化
  init();

  //查找指定节点的父节点，直到第一级节点
  var tpid = getNoteUpID(note);
  var old_id = note;
  while(tpid != -10000){
      old_id = tpid;
      tpid = getNoteUpID(tpid);
  }

  //找到第一级节点后，打开该节点
  //openSelectNote(old_id);
  changeMarkByNote(old_id);

  //继续查找节点树上的节点

  /*


  //如果是第一级节点，直接显示当前第一级节点下的子节点
  var tnote = getNoteUpID(note);

  if(tnote == "-1"){
    //打开节点是第一级节点时，直接显示
  }
  else if(tnote == "0"){
    openSelectNote(note);
    changeMarkByNote(note);
  }
  else{



    //如果不是第一级节点，查找本节点的第一级节点
    tnote = getNoteFLBH(note);

    //自本节点的第一级节点开始打开显示
    openSelectNote(tnote);
    changeMarkByNote(tnote);

    //本节点显示路径上的节点
    var tempnote = openCurrentLevel(tnote,note);

    //循环打开子节点，直到显示本节点
    while(tempnote != -1 && tempnote != note){
    tempnote = openCurrentLevel(tempnote,note);
    if(tempnote  == note){
      break;
     }
   }
   //最后显示本节点
   openSelectNote(note);
   changeMarkByNote(note);
  }*/

}

//打开选定节点的所有子节点，转换图片，并返回在选定图片显示路径上的子节点
function openCurrentLevel(note,snote){

    //在选定节点的路径上的本层次节点
	var rnote = -1;

	  var tl = treeItem.length;
	  var j = 0;
	  for(j=0;j<tl;j++){

		//如果是选定节点的子节点
		if(treeItem[j].pid == note){
			//如果本节点在选定节点的显示路径上，返回本节点
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

//打开选定的节点，并转换节点的图片
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

//测试节点是否是最终结点,是最终结点，返回True，不是最终结点，返回False
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

//判断当前子节点是否在被选定的节点显示路径上
 function isSubNoteByNote(subnote,note){

	var tnote = note;

	//查询
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


//得到指定节点的第一级节点的id
function getNoteFLBH(note){
    var tl= treeItem.length;
	var tNote = note;
	var btNote = note;

	//循环得到节点
	while(true){
		//得到当前节点的父节点
		btNote = tNote;
		tNote = getNoteUpID(tNote);
		if(tNote == "0" || tNote == "-10000"){
			break;
		}
	}

	//最后得到第一级节点的编号btNote
	return btNote;
}

//得到节点的上一级节点
function getNoteUpID(note){
  var tl= treeItem.length;
  var j = 0;
  for(j=0;j<tl;j++){
	  //如果找到节点
	  if(treeItem[j].id == note){
		  return treeItem[j].pid;
	  }
  }
  return "-10000";
}




