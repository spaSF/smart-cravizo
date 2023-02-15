isc.ClassFactory.defineClass("OsobaHistoryWindow","Window").addProperties({isModal:!0,width:500,height:200,childrenSnapToGrid:!0,childrenSnapResizeToGrid:!0,headerIconDefaults:{src:"[SKIN]/headerIcons/clock.png",height:14,width:16},showModalMask:!0,autoCenter:!0,autoSize:!0,canDragResize:!0,showFooter:!0,showMinimizeButton:!1,canDragReposition:!0,canDragResize:!0,historyItem:null,historyValues:null,dataSource:null,keyDown:function(){var a=isc.EventHandler.getKey(),b;if("Q"==a&&isc.EventHandler.ctrlKeyDown()||
"Escape"==a)this.cancel(),b=!1;return b},cancel:function(){this.close();this.markForDestroy()},initWidget:function(){this.Super("initWidget",arguments);var a=this;App.getDataSource("OsobaHistory",function(b){if(b){a.setTitle(a.historyItem.title+" "+isc.i18nMessages["osobaHistory.historyWindow.label"]);var c={editorType:a.historyItem.editorType,optionDataSource:a.historyItem.optionDataSource,valueField:a.historyItem.valueField,displayField:a.historyItem.displayField,valueMap:a.historyItem.valueMap},
e=[];e.push(isc.addProperties({},b.fields.dateChange));e.push(isc.addProperties(b.fields.oldVal,c));e.push(isc.addProperties(b.fields.newVal,c));var c="",d=a.historyItem.name;switch(a.dataSource.ID){case "PobytDetail":case "Pobyt":c="pobyt";break;case "Osoba":c="osoba";break;case "Priezvisko":d=c="priezvisko";break;case "RodnePriezvisko":c="rodnePriezvisko";d="priezvisko";break;case "MenoOsoby":c="menoOsoby";d="meno";break;case "StatnaPrislusnost":c="statnaPrislusnost",d="stprisNazov"}a.grid=isc.ListGrid.create({height:200,
width:"100%",snapToGrid:!0,canEdit:!1,autoFitWidthApproach:"both",rowEndEditAction:"same",listEndEditAction:"done",autoFitFieldWidths:!0,alternateRecordStyles:!0,autoFetchData:!0,showFilterEditor:!1,initialSort:[{property:"dateChange",direction:"descending"}],initialCriteria:{_constructor:"AdvancedCriteria",operator:"and",criteria:[{fieldName:"entity",operator:"equals",value:c},{fieldName:"field",operator:"equals",value:d},{fieldName:"entityId",operator:"equals",value:a.historyValues.id}]},dataSource:b,
fields:e});a.addItem(a.grid);a.draw();a.grid.focusInRow(0)}})}});isc.ClassFactory.defineClass("TextItemHistory","TextItem").addProperties({iconClick:function(a,b,c){OsobaHistoryWindow.create({historyValues:a.getValues(),historyItem:b,dataSource:a.dataSource})},icons:[{name:"history",src:"[SKIN]headerIcons/clock.png",disableOnReadOnly:!1}],init:function(){this.Super("init",arguments);this.icons[0].prompt=isc.i18nMessages["smartcrfo.buttons.history.prompt"]}});
isc.ClassFactory.defineClass("SmartComboHistory","SmartCombo").addProperties({iconClick:function(a,b,c){"detail"==c.name?(a=b.optionDataSource,!a&&b.foreignKey&&(a=b.foreignKey.charAt(0).toUpperCase()+b.foreignKey.slice(1).split(".")[0]),App.getDataSource(a,function(a){a&&b.getValue()&&"detail"==c.name&&App._showDetailByPK(a,b.getValue(),App._findParentTabSet(b.grid||b.form))})):(console.log(b),console.log(a),OsobaHistoryWindow.create({historyValues:a.getValues(),historyItem:b,dataSource:a.dataSource}))},
icons:[{name:"detail",src:"[SKIN]DynamicForm/default_formItem_icon.png",disableOnReadOnly:!1},{name:"history",src:"[SKIN]headerIcons/clock.png",disableOnReadOnly:!1}],init:function(){this.Super("init",arguments);this.icons[0].prompt=isc.i18nMessages["smartsfs.buttons.showdetail.prompt"];this.icons[1].prompt=isc.i18nMessages["smartcrfo.buttons.history.prompt"]}});