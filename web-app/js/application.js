isc.DateItem.addProperties({
   startDate : isc.DateUtil.createLogicalDate(1900,0,1)
});

isc.RelativeDateItem.addProperties({
   startDate : isc.DateUtil.createLogicalDate(1900,0,1)
});

isc.ListGrid.addProperties({
   exportAlternateRowBGColor : null
});
isc.LoginDialog.addProperties({
   headerIconProperties : {
      src : contextRoot + "/images/app.ico"
   },
   items : [ "autoChild:headerImage", "autoChild:loginForm" ],
   headerImageDefaults : {
      _constructor : isc.Img,
//      width : "100%",
      imageType : "normal",
      imageWidth : 300,
      imageHeight : 120,
      src : contextRoot + "/images/login.png"
   },
   loginFailureItemProperties:{
      wrap:true
   },
   usernameItemProperties : {
      wrapTitle : false,
      clipTitle : false
   },
   passwordItemProperties : {
      wrapTitle : false,
      clipTitle : false
   }
});

isc.ClassFactory.defineClass("UListGridItem", "CanvasItem");

isc.UListGridItem.addProperties({
   shouldSaveValue : false,
   fetchGridData : function() {
      //debugger;	      
      var crit = {};
      crit[this.foreignKey] = this.form.formTabset._instanceID;
      crit = this.gridDataSource.convertDataSourceCriteria(crit, "exactCase");
      console.log("GridDataSource:" + this.gridDataSource.ID);
      console.log(crit);
      this.grid.fetchData(crit);
   },
   createCanvas : function(form, item) {
      this.gridDataSource = DataSource.get(item.optionDataSource);
      var G = this;
      var gridProps = {
         autoDraw : false,

         // fill the space the form allocates to the item
         width : this.width,
         height : this.height,
         leaveScrollbarGaps : false,

         // dataSource and fields to use, provided to a listGridItem as
         // listGridItem.gridDataSource and optional gridFields
         dataSource : this.gridDataSource,
         // fields:this.gridFields,
         autoFetchData : false,
         getSelectedRecord : function() {
      	   return G.record;
         },
         recordDoubleClick : function(viewer, record, recordNum) {             
             var req = {};
             var dtlComp;
             var gridDS = this.dataSource;
             var pkey = this.dataSource.getPrimaryKeyFieldName();
             var recId = record[pkey];
             req[pkey] = recId;
             G.record = record;
             debugger;
             this.dataSource.title = item.title
             App._showDetailByPK(this.dataSource, recId, form.formTabset, "normal", G.brefField, G.brefValue, G._ownerComp, G);
             viewer.deselectRecord (record);
         }
      };
      if (item.formItemProps) {
         var props = {};
         try {
            for ( var propkey in item.formItemProps) {
            props[propkey] = App._evalPropertyValue(item.formItemProps[propkey]);
            }
         } catch (e) {
            console.log("nezdarilo sa");
         }
         isc.addProperties(gridProps, props);
      }     
      this.grid = isc.ListGrid.create(gridProps);
 
      this.delayCall("fetchGridData");
      return this.grid;
   }

// // implement showValue to update the ListGrid selection
// showValue : function (displayValue, dataValue) {
// if (this.canvas == null) return;
//        
// var record = this.canvas.data.find(this.name, dataValue);
// if (record) this.canvas.selection.selectSingle(record)
// else this.canvas.selection.deselectAll();
// }
});


isc.ClassFactory.defineClass("DetailItem", "CanvasItem");
isc.DetailItem.addProperties({
	getData : function(pk) {
		var D=this.dtlComp;
		this.dtlComp.initialValues = {id:pk};
		D._setValuesByMode(function() {
	         D._drawLayout();
	      });
	},
	createCanvas : function(form, item) {
		debugger;
//		App.getDataSource(item.optionDataSource, function(getDS) {
//			if (getDS) {
				this.dtlComp = DetailFormComponentAny.create({
					dataSource : isc.DataSource.get(item.optionDataSource),
					mode : "EDIT_BY_PK",
					parentDataSource : form.dataSource,
					drawComponent : function() {
					      var D = this;
					      if (isc.Browser.isDesktop) {
					         var cols = Object.keys(D.dataSource.fields).length > 15 ? 4 : 2;
					         var hght = 50 * Object.keys(D.dataSource.fields).length / cols;
					      } else
					         cols = 2;
					      D._createForm(cols);
					      D._createToolBar();
					   }
				});
//			}
//		});
		this.dtlComp.toolBar.hide()
		return this.dtlComp;
	}
});	
ClassFactory.defineClass("htmlItem", "CanvasItem").addProperties(
		{
			height : "*",
			maxHeight: 400,
			createCanvas : function() {
				return isc.HTMLFlow.create({
							autoDraw : false,
							ID : "htmlItem",
							padding : 2,
							height : "*",
							width : "*",
							overflow : "auto",
							canDragResize : true,
							showEdges : false,
							backgroundColor : '#FFFFDF',
							contents : "<div><strong><font face='Courier New' size='2'>Výraz spustenia: s m h D M W R</font></strong></div><div><font color='#333399' face='courier new, courier, monospace' size='2'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | | | | | | `- Rok [nepovinný]</strong></font></div><div><font color='#333399' face='courier new, courier, monospace' size='2'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | | | | | `- Deň v týždni, 1-7 alebo SUN-SAT, ?</strong></font></div><div><font color='#333399' face='courier new, courier, monospace' size='2'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | | | | `- Mesiac, 1-12 alebo JAN-DEC</strong></font></div><div><font color='#333399' face='courier new, courier, monospace' size='2'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | | | `- Deň v Mesiaci, 1-31, ?</strong></font></div><div><font color='#333399' face='courier new, courier, monospace' size='2'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | | `- Hodiny, 0-23</strong></font></div><div><font color='#333399' face='courier new, courier, monospace' size='2'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| `- Minúty, 0-59</strong></font></div><div><font color='#333399' face='courier new, courier, monospace' size='2'><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; `- Sekundy, 0-59</strong></font></div><div><font face='courier new, courier, monospace' size='2'></font></div><div><font face='courier new, courier, monospace' size='2'><b>?</b>&nbsp;opakovať&nbsp;</font></div><div><font face='courier new, courier, monospace' size='2'><b>*</b>&nbsp;nezaujímavé</font></div><div><font face='courier new, courier, monospace' size='2'><br></font></div><div><font face='courier new, courier, monospace' size='2'><div><b>Príklad</b>:</div><div><b>1</b>&nbsp;- výrazom sa vytvorí aktivačná udalosť, ktorá sa spustí každých 5 minút</div><div><b>0 0/5 * * * ?</b></div><div><br></div><div><div><b>2</b>&nbsp;- výrazom sa vytvorí aktivačná udalosť, ktorá sa spustí každých 5 minút, 10 sekúnd po minúte (napr. 10:00:10, 10:05:10, atď.)</div><div><b>10 0/5 * * * ?</b></div></div><div><b><br></b></div><div><div><b>3&nbsp;</b>- výrazom sa vytvorí aktivačná udalosť, ktorá sa spustí o 10:30, 11:30, 12:30 a 13:30, každú stredu a piatok.</div><div style='font-weight: bold;'>0 30 10-13 ? * WED,FRI</div></div></font></div><div style='font-weight: bold;'>&nbsp;</div><div><font face='courier new, courier, monospace' size='2'><b>4&nbsp;</b>- výrazom sa vytvorí aktivačná udalosť, ktorá sa spustí každú pol hodinu medzi 8 až 10 hodinou v 5 a 20 dni každého mesiaca. Všimnite si, že sa nespustí o 10:00, ale len o 8:00, 8:30, 9:00 a 9:30</font></div><div style='font-weight: bold;'><font face='courier new, courier, monospace' size='2'>0 0/30 8-9 5,20 * ?</font></div>"
						})
			}
		});

ClassFactory.defineClass("htmlFormItem", "CanvasItem").addProperties(
		{
			height : "*",
//			maxHeight: 400,
			createCanvas : function() {
				return isc.HTMLFlow.create({
							autoDraw : false,
//							ID : "htmlItem",
							padding : 2,
							height : "*",
							width : "*",
							overflow : "inherit",
							canDragResize : true,
							showEdges : true,
							backgroundColor:'#FFFFFF'
						})
			},
			showValue:function(displayValue, dataValue, form, item){
				if (this.canvas == null) return;
				this.canvas.setContents(displayValue);
			}
		});

/**
 * globalne validatori: kazda property je validator typ s definiciou v objekte s
 * props: action:function(result, item, validator, record, component){} - co
 * urobit po validacii condition: function(item, validator, value, record){} -
 * kontrolna fcia vratit boolean valid?true:false defaultErrorMessage :""
 * type:String nazov typu
 */
isc.Validator.addValidatorDefinitions({
	avizoAtrErr:{
		condition : function(item, validator, value, record) {
			var atrName = item.name+"ErrMsg";
			if(record&&record[atrName]){
				validator.errorMessage = record[atrName];
				return false;
			}
			return true;
		},
		type:"avizoAtrErr"
	},
	avizoPobyt:{
		type:"avizoPobyt",
		condition : function(item, validator, value, record) {
			var atrName = item.name+"ErrMsg";
			if(record&&record[atrName]){
				if(record.znakocA||
						record.zobecA||
						record.zokresA||
						record.zorientcisA||
						record.zsupiscA||
						record.zulicaA||
						record.bytcisA||
						record.dtprihlA||
						record.dtukonA||
						record.fg_mimoA||
						record.orientcA||
						record.pobstatA||
						record.regadridA||
						record.regdomA||
						record.regobcastA||
						record.regobecA||
						record.regokresA||
						record.regulicaA||
						record.regvchodA||
						record.supcisA||
						record.typpobA||
						record.zcastbudA||
						record.zcastobA||
						record.zmiestoA){
					validator.errorMessage = record[atrName];
					return false;
				}
			}
			return true;
		}
	},
	avizoVztah:{
		type:"avizoVztah",
		condition : function(item, validator, value, record) {
			var atrName = item.name+"ErrMsg";
			if(record&&record[atrName]){
				if(record.dtvznikA||
						record.dtzanikA||
						record.dvdneplA||
						record.ifoA||
						record.miestovydA||
						record.miestovydtxtA||
						record.ptyproleA||
						record.sobmatrA||
						record.sobmatrtxtA||
						record.typvztA||
						record.vtyproleA){
					validator.errorMessage = record[atrName];
					return false;
				}
			}
			return true;
		}
	}

});

isc.ClassFactory.defineClass('btnSendAvizo', 'BtnIcon').addProperties({
	title : 'Duplikovaš záznam',
	prompt : 'Duplikovaš záznam',
	icon : '[SKIN]/actions/dynamic.png',
	minWidth : 24,
	dataComp : null,
	initWidget : function() {
		this.Super("initWidget", arguments);
		this.prompt = isc.i18nMessages["avizo.send.label"];
	},
	click : function() {
		debugger;
		var D = this.parentElement.parentElement.dataComp;
	}
});
	
App.addMethods({
	detailByLink : function(dSName, DetailId) {
		var req = {};
		var tabSet = SmartTabSet.getById("appTabSet");
		App.getDataSource(dSName, function(detailDS) {
			detailDS.fetchRecord(DetailId, function(result) {			
				if (!result.data || result.data == null)
					return;
				var record = result.data[0];
				var pkey = detailDS.getPrimaryKeyFieldName();
				req[pkey] = DetailId;
				//req = req._constructor == "AdvancedCriteria" ? req : detailDS.convertDataSourceCriteria(req, "exactCase");
				//req = detailDS.convertDataSourceCriteria(req, "exactCase");
				if (detailDS) {
						dtlComp = App._customCompDetail(detailDS, detailDS, {
			                dataSource : detailDS,
			                initialValues : req,
			                parentDataSource : detailDS,
			                mode : "EDIT_BY_PK",
			                type : "",
			                tabSet : tabSet
			            }) || DetailFormComponentAny.create({
							dataSource : detailDS,
							initialValues : req,
							parentDataSource : detailDS,
							mode : "EDIT_BY_PK",
							type : "",
							tabSet : tabSet
						});				
					var tabID = "_tabDetail_" + dtlComp.dataSource.ID + "_" + DetailId;
					var tabTitle = dtlComp.dataSource.title + ' ['
							+ DetailId + ']';
					dtlComp._myTabName = tabID;
					if (!tabSet.getTab(tabID)) {
						var myTabNum = tabSet.getSelectedTabNumber();
						tabSet.addTab({
							name : tabID,
							title : tabTitle,
							canClose : true,
							pane : dtlComp
						}, (myTabNum + 1));
					}
					tabSet.selectTab(tabID);					
				}
			});
		});
	} ,
	// #27707
    createAppLayout : function() {
        console.log(App.get.user);
        //user uspesne prihlaseny - ak isiel cez login dialog a zavolanie urobi destroy logindialogu
        App.get.userLoginSucces();
        App.userDataSource = App.get.config["smartsfs.datasources.user"] || App.userDataSource;
        App.navDS = App.get.config["smartsfs.datasources.menu"] || App.navDS;
        App.getDataSource("SmartSource");//globalne operacie su na SmartSource DS
        if (App.get.config["smartsfs.userSetting.supported"].toString().toBoolean())
            App.getDataSource("SmartUserSettings");//globalne potrebny DS
        var ms = [];
        this.splitPane = isc.SplitPane.create({
            autoDraw : false,
            navigationTitle : isc.i18nMessages["default.application.title"],
            showLeftButton : false,
            showRightButton : true,
            paneChanged : function(pane) {
                if (isc.Browser.isHandset) {
                    if (pane == "detail") {
                        this.navigationBar.rightButton.hide();
                    } else {
                        this.navigationBar.rightButton.show();
                    }
                }
            }
        });

        if (App.get.user.hasAnyRole("ROLE_AVREAD")) {
        	this.splitPane.leftLayout.hide();
        	this.splitPane.leftLayout.setProperty("showResizeBar",false);
        }
        
        var loginMenu = App.get.createLoginMenu();

        this.splitPane.setDetailToolButtons([ isc.Label.create({
            contents : isc.i18nMessages["springSecurity.logged.label"],
            wrap : false,
            padding : 0
        }), isc.MenuButton.create({
            title : " <b>" + App.get.user.titlename + "</b>",
            menu : loginMenu,
            autoFit : true
        }) ]);

        // hlp button create
        if (typeof App.getHelp == "function" && App.helpFileUrl) {
            var A = this;
            var btnHelp = isc.BtnIcon.create({
                icon : '[SKIN]/custom/help.png',
                title : 'Help',
                prompt : isc.i18nMessages["smartsfs.buttons.help.label"],
                minWidth : 24,
                margin : 15,
                click : function() {
                    if (this.fillScreenWindow == null) {
                        this.fillScreenWindow = isc.HelpWindow.create({
                            title : isc.i18nMessages["smartsfs.buttons.help.label"]
                        })
                        this.fillScreenWindow.addMember(App.getHelp())
                    }
                    this.fillScreenWindow.show();
                    this.fillScreenWindow.bringToFront();
                    this.fillScreenWindow.focus();
                }
            });
            this.splitPane.detailToolButtons.push(btnHelp)
        }

        var detailPane = isc.SmartTabSet.create({
            ID : "appTabSet",
            tabBarPosition : "top",
            autoDraw : false,
            canCloseTabs : true,
            destroyPanes : true
        });
        App.get.createNavPane(function(navigationPane) {
            App.get.splitPane.setNavigationPane(navigationPane);
            if (isc.Browser.isHandset) {
                App.get.splitPane.navigationBar.rightButton.addProperties({
                    click : function() {
                        App.get.splitPane.showDetailPane();
                    },
                    icon : '[SKIN]/NavigationBar/back_arrow~2_rtl.png'
                });
            }

            App.get.splitPane.setDetailPane(detailPane);
            if (isc.Browser.isHandset)
                isc.Page.updateViewport(1, null, null, true);
            if (!isc.Browser.isHandset)
                ms.push(Header.create());
            ms.push(App.get.splitPane);
            if (!isc.Browser.isHandset)
                ms.push(Foother.create());
            
            var homeTitle = isc.i18nMessages["default.home.label"]
            if (App.get.user.hasAnyRole("ROLE_AVREAD")) homeTitle = isc.i18nMessages["menu.osobaview.title"]
            appTabSet.addTab({
                title : homeTitle,
                canClose : false,
                icon:"[SKIN]/custom/house.png",
                pane : App.get.createHomePane()
            });

            App.get.splitPane.showNavigationPane();
            App.get.setMembers(ms);
            App.get.draw();

        });
    }
});