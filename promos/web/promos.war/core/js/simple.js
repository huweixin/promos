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
            mainLayout.add('east', new Ext.ContentPanel('east-div', {title:'<div align="center">�������</div>',fitToFrame: true, closable: false }));
            mainLayout.add('west', new Ext.ContentPanel('west-div', {title:'<div align="center">������</div>',fitToFrame: false, closable: false }));
            mainLayout.add('center', new Ext.ContentPanel('center-div-web', {title:'��ҳ��', closable: false }));
            mainLayout.add('center', new Ext.ContentPanel('center-div-jsp', {title:'�Ǳ�', closable: false }));
            mainLayout.add('center', new Ext.ContentPanel('center-div-pro', {title:'����', closable: false }));
            mainLayout.add('center', new Ext.ContentPanel('center-div-fun', {title:'ҵ�����', closable: false }));
            mainLayout.add('center', new Ext.ContentPanel('center-div-con', {title:'����', closable: false }));
	         
	          mainLayout.getRegion('center').showPanel('center-div-web');
	          //mainLayout.getRegion('west').collapse();
	          mainLayout.getRegion('east').collapse();
            mainLayout.endUpdate();
            
         var tb = new Ext.Toolbar('x-taskbar');
         
         tb.add(
                {text:'&nbsp;&nbsp;�½�&nbsp;&nbsp;',handler:jh_new}, 
                '-', {text: '&nbsp;&nbsp;����&nbsp;&nbsp;',id:"btncopy",handler:jh_formcopy},
                '-', {text: '&nbsp;&nbsp;ճ��&nbsp;&nbsp;',id:"btncopy",handler:jh_formpaste},
                '-', {text: '&nbsp;&nbsp;ɾ��&nbsp;&nbsp;',handler:jh_deleteByID},
                '-', {text: '&nbsp;&nbsp;����&nbsp;&nbsp;',handler:jh_testrun},
                '-', {text: '&nbsp;&nbsp;��ջ���&nbsp;&nbsp;',handler:jh_clearcash},
                '-', {text: '&nbsp;&nbsp;ȫ��&nbsp;&nbsp;',handler:jh_editform}
               );             
        }
    };
}();
Ext.EventManager.onDocumentReady(Simple.init, Simple, true);
