Simple = function() {
    return {
        init : function() {
            document.all["east-div"].style.visibility="visible";
            var mainLayout = new Ext.BorderLayout(document.body, {
	                    north: {
	                        split:false,
	                        initialSize: 33,
	                        titlebar: false
	                    },
	                    west: {
	                        split:true,
	                        initialSize: 210,
	                        minSize: 95,
	                        maxSize: 320,
	                        titlebar: true,
	                        collapsible: true,
                            animate: true
	                    },
	                    east: {
	                        split:true,
	                        initialSize: 175,
	                        minSize: 175,
	                        maxSize: 175,
	                        titlebar: true,
	                        collapsible: true,
                            animate: true
	                    },
	                    south: {
	                        split:true,
	                        initialSize: 1,
	                        minSize: 1,
	                        maxSize: 1,
	                        titlebar: true,
	                        collapsible: false,
                            animate: false
	                    },
	                    center: {
	                        titlebar: false,
	                        autoScroll:false,
                            closeOnTab: true
                        }
            });
            mainLayout.beginUpdate();
            mainLayout.add('north', new Ext.ContentPanel('north-div', {title:'',fitToFrame: true, closable: false }));
            mainLayout.add('south', new Ext.ContentPanel('south-div', {title:'',fitToFrame: true, closable: false }));
            mainLayout.add('east', new Ext.ContentPanel('east-div', {title:'<div align="center">构件面板</div>',fitToFrame: true, closable: false }));
            mainLayout.add('west', new Ext.ContentPanel('west-div', {title:'<div align="center">导航栏</div>',fitToFrame: false, closable: false }));
            mainLayout.add('center', new Ext.ContentPanel('center-div-web', {title:'表单页面', closable: false }));
            mainLayout.add('center', new Ext.ContentPanel('center-div-jsp', {title:'角本', closable: false }));
            mainLayout.add('center', new Ext.ContentPanel('center-div-pro', {title:'属性', closable: false }));
            mainLayout.add('center', new Ext.ContentPanel('center-div-fun', {title:'业务组件', closable: false }));
            mainLayout.add('center', new Ext.ContentPanel('center-div-con', {title:'常量', closable: false }));
	         
	          mainLayout.getRegion('center').showPanel('center-div-web');
	          //mainLayout.getRegion('west').collapse();
	          mainLayout.getRegion('east').collapse();
            mainLayout.endUpdate();
            
         var tb = new Ext.Toolbar('x-taskbar');
         
         tb.add(
                {text:'&nbsp;&nbsp;新建&nbsp;&nbsp;',handler:jh_new}, 
                '-', {text: '&nbsp;&nbsp;复制&nbsp;&nbsp;',id:"btncopy",handler:jh_formcopy},
                '-', {text: '&nbsp;&nbsp;粘贴&nbsp;&nbsp;',id:"btncopy",handler:jh_formpaste},
                '-', {text: '&nbsp;&nbsp;删除&nbsp;&nbsp;',handler:jh_deleteByID},
                '-', {text: '&nbsp;&nbsp;运行&nbsp;&nbsp;',handler:jh_testrun},
                '-', {text: '&nbsp;&nbsp;清空缓存&nbsp;&nbsp;',handler:jh_clearcash},
                '-', {text: '&nbsp;&nbsp;全屏&nbsp;&nbsp;',handler:jh_editform}
               );             
        }
    };
}();
Ext.EventManager.onDocumentReady(Simple.init, Simple, true);
