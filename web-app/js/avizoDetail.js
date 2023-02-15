CustomComponents.addClassProperties({
	avizoDetailCustom:function(compProps){
		//tu ideme zrobit speciale componentu
		isc.addProperties(compProps,{
	         _drawLayout : function() {
	        	 //debugger;
	             var D = this;
	             D._detailCanvas.minHeight = 750;
	             D._detailCanvas.addMember(D.toolBar);
	             var switchPos = NumberUtil.parseInt(App.get.config["crfosp.avizo.tbref"]);
                 D._hasAsocFields = false; // ma asoc field

	             for ( var fname in D.dataSource.fields) {
	                if (D.dataSource.fields[fname].type == "agrmany" || D.dataSource.fields[fname].type == "bref"
	                      || D.dataSource.fields[fname].type == "manyToMany") {

	                   // ak nema pravo videt pokracuj dalsim fieldom
	                   if (D.dataSource.fields[fname].viewRequiresRole && !App.get.user.hasAnyRole(D.dataSource.fields[fname].viewRequiresRole))
	                      continue;
	                   if (D.dataSource.fields[fname]["canCreateTab"] && isA.Function(D.dataSource.fields[fname]["canCreateTab"])) {
	                      if (D.dataSource.fields[fname].canCreateTab(D) == false)
	                         continue;
	                   }
	                   if(!D._hasAsocFields){

	                      if (!D._sectionStack) {
	                         D._sectionStack = isc.SectionStack.create({
	                            height : "100%",
	                            width : "100%",
	                            overflow : "auto",
	                            visibilityMode : "mutex"
                        	 });
	                         D._sectionStack.addSection({
	                            title : D.dataSource.title,
	                            expanded : true,
	                            resizeable : true,
	                            items : [ D.detail ]
	                         });

	                      }

	                      D._detailCanvas.addMember(D._sectionStack);
	                      D.addTab({
	                          title : D.dataSource.title,
	                          pane : D._detailCanvas,
	                          canClose : false
	                        });	                      
	                   }
	                   D._hasAsocFields = true;
	                   if(D.dataSource.fields[fname].fieldPosition>switchPos) D._asocLayout="TABSET";
	                   D.createAsocTab(D.dataSource.fields[fname]);

	                }
	             }

	             D._myTabName = D.tabSet.getSelectedTab().name;
	             D.delayCall("focusInside");
	          },		
	        focusInSection : function(){
	        	var D = this;	        	
	        	var section = D._sectionStack.getSectionHeader(D._sectionStack.getExpandedSections()[0]);
	        	if(section && section.items[0].isA("DynamicForm")){
	        		section.items[0].delayCall("focusInside");
	        	}
	        	if(section && section.items[0].isA("SmartTabSet")){
	        		if(section.items[0].getTabPane(section.items[0].getSelectedTab()))
	        		section.items[0].getTabPane(section.items[0].getSelectedTab()).delayCall("focusInside");
	        	}
	        },
			drawComponent : function() {
			      var D = this;
			      if (isc.Browser.isDesktop) {
			         var cols = Object.keys(D.dataSource.fields).length > 15 ? 4 : 2;
			         var hght = 50 * Object.keys(D.dataSource.fields).length / cols;
			      } else
			         cols = 2;
			      
			      D._createForm(cols);
			      D.detail._ownerComp=D;
			      D._createToolBar();
			      D._setValuesByMode(function() {
			    	 D._asocLayout="SECTION"; 
			         D._drawLayout();
//			         D.switchLayout();
			         D._sectionStack.collapseSection(D._sectionStack.getSections());
			         D._sectionStack.expandSection(0);
			      });
			      // D._drawLayout();	
		   },
		   cancelReloadAsoc: function(asocGrid){
			   var D = this;
			   D._asocGrids.remove(asocGrid);
		   },
		   returnReloadAsoc: function(asocGrid){
			   var D = this;
			   D._asocGrids.add(asocGrid);
		   },
		   canSaveAsoc: function(){
			   if(!this.toolBar._btnSave.isDisabled() && this.valuesManager.valuesHaveChanged()){
				   isc.warn(isc.i18nMessages["avizoOsoba.changed.warn.message"]);
				   return false;
			   }else{
				   return true;
			   }  
		   },
		   _canSwitchLayout:false
		   });
		return DetailFormComponentAny.create(compProps);
	}
});