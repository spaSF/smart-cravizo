isc.DateItem.addProperties({startDate:isc.DateUtil.createLogicalDate(1900,0,1)});isc.RelativeDateItem.addProperties({startDate:isc.DateUtil.createLogicalDate(1900,0,1)});isc.ListGrid.addProperties({exportAlternateRowBGColor:null});
isc.LoginDialog.addProperties({headerIconProperties:{src:contextRoot+"/images/app.ico"},items:["autoChild:headerImage","autoChild:loginForm"],headerImageDefaults:{_constructor:isc.Img,imageType:"normal",imageWidth:300,imageHeight:120,src:contextRoot+"/images/login.png"},loginFailureItemProperties:{wrap:!0},usernameItemProperties:{wrapTitle:!1,clipTitle:!1},passwordItemProperties:{wrapTitle:!1,clipTitle:!1}});isc.ClassFactory.defineClass("UListGridItem","CanvasItem");
isc.UListGridItem.addProperties({shouldSaveValue:!1,fetchGridData:function(){var b={};b[this.foreignKey]=this.form.formTabset._instanceID;b=this.gridDataSource.convertDataSourceCriteria(b,"exactCase");console.log("GridDataSource:"+this.gridDataSource.ID);console.log(b);this.grid.fetchData(b)},createCanvas:function(b,c){this.gridDataSource=DataSource.get(c.optionDataSource);var e=this,a={autoDraw:!1,width:this.width,height:this.height,leaveScrollbarGaps:!1,dataSource:this.gridDataSource,autoFetchData:!1,
getSelectedRecord:function(){return e.record},recordDoubleClick:function(a,f,d){d=this.dataSource.getPrimaryKeyFieldName();d=f[d];e.record=f;debugger;this.dataSource.title=c.title;App._showDetailByPK(this.dataSource,d,b.formTabset,"normal",e.brefField,e.brefValue,e._ownerComp,e);a.deselectRecord(f)}};if(c.formItemProps){var f={};try{for(var d in c.formItemProps)f[d]=App._evalPropertyValue(c.formItemProps[d])}catch(g){console.log("nezdarilo sa")}isc.addProperties(a,f)}this.grid=isc.ListGrid.create(a);
this.delayCall("fetchGridData");return this.grid}});isc.ClassFactory.defineClass("DetailItem","CanvasItem");
isc.DetailItem.addProperties({getData:function(b){var c=this.dtlComp;this.dtlComp.initialValues={id:b};c._setValuesByMode(function(){c._drawLayout()})},createCanvas:function(b,c){debugger;this.dtlComp=DetailFormComponentAny.create({dataSource:isc.DataSource.get(c.optionDataSource),mode:"EDIT_BY_PK",parentDataSource:b.dataSource,drawComponent:function(){if(isc.Browser.isDesktop){var b=15<Object.keys(this.dataSource.fields).length?4:2;Object.keys(this.dataSource.fields)}else b=2;this._createForm(b);
this._createToolBar()}});this.dtlComp.toolBar.hide();return this.dtlComp}});ClassFactory.defineClass("htmlItem","CanvasItem").addProperties({height:"*",maxHeight:400,createCanvas:function(){return isc.HTMLFlow.create({autoDraw:!1,ID:"htmlItem",padding:2,height:"*",width:"*",overflow:"auto",canDragResize:!0,showEdges:!1,backgroundColor:"#FFFFDF",contents:"<div><strong><font face='Courier New' size='2'>V\u00fdraz spustenia: s m h D M W R</font></strong></div><div><font color='#333399' face='courier new, courier, monospace' size='2'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | | | | | | `- Rok [nepovinn\u00fd]</strong></font></div><div><font color='#333399' face='courier new, courier, monospace' size='2'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | | | | | `- De\u0148 v t\u00fd\u017edni, 1-7 alebo SUN-SAT, ?</strong></font></div><div><font color='#333399' face='courier new, courier, monospace' size='2'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | | | | `- Mesiac, 1-12 alebo JAN-DEC</strong></font></div><div><font color='#333399' face='courier new, courier, monospace' size='2'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | | | `- De\u0148 v Mesiaci, 1-31, ?</strong></font></div><div><font color='#333399' face='courier new, courier, monospace' size='2'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | | `- Hodiny, 0-23</strong></font></div><div><font color='#333399' face='courier new, courier, monospace' size='2'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| `- Min\u00faty, 0-59</strong></font></div><div><font color='#333399' face='courier new, courier, monospace' size='2'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; `- Sekundy, 0-59</strong></font></div><div><font face='courier new, courier, monospace' size='2'></font></div><div><font face='courier new, courier, monospace' size='2'><b>?</b>&nbsp;opakova\u0165&nbsp;</font></div><div><font face='courier new, courier, monospace' size='2'><b>*</b>&nbsp;nezauj\u00edmav\u00e9</font></div><div><font face='courier new, courier, monospace' size='2'><br></font></div><div><font face='courier new, courier, monospace' size='2'><div><b>Pr\u00edklad</b>:</div><div><b>1</b>&nbsp;- v\u00fdrazom sa vytvor\u00ed aktiva\u010dn\u00e1 udalos\u0165, ktor\u00e1 sa spust\u00ed ka\u017ed\u00fdch 5 min\u00fat</div><div><b>0 0/5 * * * ?</b></div><div><br></div><div><div><b>2</b>&nbsp;- v\u00fdrazom sa vytvor\u00ed aktiva\u010dn\u00e1 udalos\u0165, ktor\u00e1 sa spust\u00ed ka\u017ed\u00fdch 5 min\u00fat, 10 sek\u00fand po min\u00fate (napr. 10:00:10, 10:05:10, at\u010f.)</div><div><b>10 0/5 * * * ?</b></div></div><div><b><br></b></div><div><div><b>3&nbsp;</b>- v\u00fdrazom sa vytvor\u00ed aktiva\u010dn\u00e1 udalos\u0165, ktor\u00e1 sa spust\u00ed o 10:30, 11:30, 12:30 a 13:30, ka\u017ed\u00fa stredu a piatok.</div><div style='font-weight: bold;'>0 30 10-13 ? * WED,FRI</div></div></font></div><div style='font-weight: bold;'>&nbsp;</div><div><font face='courier new, courier, monospace' size='2'><b>4&nbsp;</b>- v\u00fdrazom sa vytvor\u00ed aktiva\u010dn\u00e1 udalos\u0165, ktor\u00e1 sa spust\u00ed ka\u017ed\u00fa pol hodinu medzi 8 a\u017e 10 hodinou v 5 a 20 dni ka\u017ed\u00e9ho mesiaca. V\u0161imnite si, \u017ee sa nespust\u00ed o 10:00, ale len o 8:00, 8:30, 9:00 a 9:30</font></div><div style='font-weight: bold;'><font face='courier new, courier, monospace' size='2'>0 0/30 8-9 5,20 * ?</font></div>"})}});
ClassFactory.defineClass("htmlFormItem","CanvasItem").addProperties({height:"*",createCanvas:function(){return isc.HTMLFlow.create({autoDraw:!1,padding:2,height:"*",width:"*",overflow:"inherit",canDragResize:!0,showEdges:!0,backgroundColor:"#FFFFFF"})},showValue:function(b,c,e,a){null!=this.canvas&&this.canvas.setContents(b)}});
isc.Validator.addValidatorDefinitions({avizoAtrErr:{condition:function(b,c,e,a){b=b.name+"ErrMsg";return a&&a[b]?(c.errorMessage=a[b],!1):!0},type:"avizoAtrErr"},avizoPobyt:{type:"avizoPobyt",condition:function(b,c,e,a){b=b.name+"ErrMsg";return a&&a[b]&&(a.znakocA||a.zobecA||a.zokresA||a.zorientcisA||a.zsupiscA||a.zulicaA||a.bytcisA||a.dtprihlA||a.dtukonA||a.fg_mimoA||a.orientcA||a.pobstatA||a.regadridA||a.regdomA||a.regobcastA||a.regobecA||a.regokresA||a.regulicaA||a.regvchodA||a.supcisA||a.typpobA||
a.zcastbudA||a.zcastobA||a.zmiestoA)?(c.errorMessage=a[b],!1):!0}},avizoVztah:{type:"avizoVztah",condition:function(b,c,e,a){b=b.name+"ErrMsg";return a&&a[b]&&(a.dtvznikA||a.dtzanikA||a.dvdneplA||a.ifoA||a.miestovydA||a.miestovydtxtA||a.ptyproleA||a.sobmatrA||a.sobmatrtxtA||a.typvztA||a.vtyproleA)?(c.errorMessage=a[b],!1):!0}}});
isc.ClassFactory.defineClass("btnSendAvizo","BtnIcon").addProperties({title:"Duplikova\u0161 z\u00e1znam",prompt:"Duplikova\u0161 z\u00e1znam",icon:"[SKIN]/actions/dynamic.png",minWidth:24,dataComp:null,initWidget:function(){this.Super("initWidget",arguments);this.prompt=isc.i18nMessages["avizo.send.label"]},click:function(){debugger}});
App.addMethods({detailByLink:function(b,c){var e={},a=SmartTabSet.getById("appTabSet");App.getDataSource(b,function(b){b.fetchRecord(c,function(d){if(d.data&&null!=d.data&&(d=b.getPrimaryKeyFieldName(),e[d]=c,b)){dtlComp=App._customCompDetail(b,b,{dataSource:b,initialValues:e,parentDataSource:b,mode:"EDIT_BY_PK",type:"",tabSet:a})||DetailFormComponentAny.create({dataSource:b,initialValues:e,parentDataSource:b,mode:"EDIT_BY_PK",type:"",tabSet:a});d="_tabDetail_"+dtlComp.dataSource.ID+"_"+c;var g=dtlComp.dataSource.title+
" ["+c+"]";dtlComp._myTabName=d;if(!a.getTab(d)){var h=a.getSelectedTabNumber();a.addTab({name:d,title:g,canClose:!0,pane:dtlComp},h+1)}a.selectTab(d)}})})},createAppLayout:function(){console.log(App.get.user);App.get.userLoginSucces();App.userDataSource=App.get.config["smartsfs.datasources.user"]||App.userDataSource;App.navDS=App.get.config["smartsfs.datasources.menu"]||App.navDS;App.getDataSource("SmartSource");App.get.config["smartsfs.userSetting.supported"].toString().toBoolean()&&App.getDataSource("SmartUserSettings");
var b=[];this.splitPane=isc.SplitPane.create({autoDraw:!1,navigationTitle:isc.i18nMessages["default.application.title"],showLeftButton:!1,showRightButton:!0,paneChanged:function(a){isc.Browser.isHandset&&("detail"==a?this.navigationBar.rightButton.hide():this.navigationBar.rightButton.show())}});App.get.user.hasAnyRole("ROLE_AVREAD")&&(this.splitPane.leftLayout.hide(),this.splitPane.leftLayout.setProperty("showResizeBar",!1));var c=App.get.createLoginMenu();this.splitPane.setDetailToolButtons([isc.Label.create({contents:isc.i18nMessages["springSecurity.logged.label"],
wrap:!1,padding:0}),isc.MenuButton.create({title:" <b>"+App.get.user.titlename+"</b>",menu:c,autoFit:!0})]);"function"==typeof App.getHelp&&App.helpFileUrl&&(c=isc.BtnIcon.create({icon:"[SKIN]/custom/help.png",title:"Help",prompt:isc.i18nMessages["smartsfs.buttons.help.label"],minWidth:24,margin:15,click:function(){null==this.fillScreenWindow&&(this.fillScreenWindow=isc.HelpWindow.create({title:isc.i18nMessages["smartsfs.buttons.help.label"]}),this.fillScreenWindow.addMember(App.getHelp()));this.fillScreenWindow.show();
this.fillScreenWindow.bringToFront();this.fillScreenWindow.focus()}}),this.splitPane.detailToolButtons.push(c));var e=isc.SmartTabSet.create({ID:"appTabSet",tabBarPosition:"top",autoDraw:!1,canCloseTabs:!0,destroyPanes:!0});App.get.createNavPane(function(a){App.get.splitPane.setNavigationPane(a);isc.Browser.isHandset&&App.get.splitPane.navigationBar.rightButton.addProperties({click:function(){App.get.splitPane.showDetailPane()},icon:"[SKIN]/NavigationBar/back_arrow~2_rtl.png"});App.get.splitPane.setDetailPane(e);
isc.Browser.isHandset&&isc.Page.updateViewport(1,null,null,!0);isc.Browser.isHandset||b.push(Header.create());b.push(App.get.splitPane);isc.Browser.isHandset||b.push(Foother.create());a=isc.i18nMessages["default.home.label"];App.get.user.hasAnyRole("ROLE_AVREAD")&&(a=isc.i18nMessages["menu.osobaview.title"]);appTabSet.addTab({title:a,canClose:!1,icon:"[SKIN]/custom/house.png",pane:App.get.createHomePane()});App.get.splitPane.showNavigationPane();App.get.setMembers(b);App.get.draw()})}});