isc.ClassFactory.defineClass("OsobaHistoryWindow", "Window").addProperties({
   isModal : true,
   width : 500,
   height : 200,
   childrenSnapToGrid : true,
   childrenSnapResizeToGrid : true,
   headerIconDefaults : {
      src : "[SKIN]/headerIcons/clock.png",
      height : 14,
      width : 16
   },
   showModalMask : true,
   autoCenter : true,
   autoSize : true,
   canDragResize : true,
   showFooter : true,
   showMinimizeButton : false,
   canDragReposition : true,
   canDragResize : true,
   historyItem : null,
   historyValues : null,
   dataSource : null,
   keyDown : function() {
      // console.log("detail KEY DOWN:" + isc.Event.getKey());
      var key = isc.EventHandler.getKey();
      var retbul;
      if (((key == "Q") && (isc.EventHandler.ctrlKeyDown())) || (key == "Escape")) {
         this.cancel();
         retbul = false;
      }
      return retbul;
   },
   cancel : function() {
      this.close();
      this.markForDestroy();
   },
   initWidget : function() {
      this.Super("initWidget", arguments);
      var W = this;
      App.getDataSource("OsobaHistory", function(historyDS) {
         if (historyDS) {
            W.setTitle(W.historyItem.title + ' ' + isc.i18nMessages["osobaHistory.historyWindow.label"]);
            var valProps = {
               editorType : W.historyItem.editorType,
               optionDataSource : W.historyItem.optionDataSource,
               valueField : W.historyItem.valueField,
               displayField : W.historyItem.displayField,
               valueMap : W.historyItem.valueMap
            };
            var gridFields = [];
            gridFields.push(isc.addProperties({}, historyDS.fields.dateChange));
            gridFields.push(isc.addProperties(historyDS.fields.oldVal, valProps));
            gridFields.push(isc.addProperties(historyDS.fields.newVal, valProps));
            var hisEntity = "";
            var hisField = W.historyItem.name;
            switch (W.dataSource.ID) {
               case "PobytDetail":
               case "Pobyt":
                  hisEntity = "pobyt";
                  break;
               case "Osoba":
                  hisEntity = "osoba";
                  break;
               case "Priezvisko":
                  hisEntity = "priezvisko";
                  hisField = "priezvisko";
                  break;
               case "RodnePriezvisko":
                  hisEntity = "rodnePriezvisko";
                  hisField = "priezvisko";
                  break;
               case "MenoOsoby":
                  hisEntity = "menoOsoby";
                  hisField = "meno";
                  break;
               case "StatnaPrislusnost":
                  hisEntity = "statnaPrislusnost";
                  hisField = "stprisNazov";
                  break;
               default:
                  break;
            }
            var initCrit = {
               _constructor : "AdvancedCriteria",
               operator : "and",
               criteria : [ {
                  fieldName : "entity",
                  operator : "equals",
                  value : hisEntity
               }, {
                  fieldName : "field",
                  operator : "equals",
                  value : hisField
               }, {
                  fieldName : "entityId",
                  operator : "equals",
                  value : W.historyValues.id
               } ]
            };
            W.grid = isc.ListGrid.create({
               height : 200,
               width : "100%",
               snapToGrid : true,
               canEdit : false,
               autoFitWidthApproach : "both",
               rowEndEditAction : 'same', // none stop same
               listEndEditAction : 'done', // none stop same
               autoFitFieldWidths : true,
               alternateRecordStyles : true,
               autoFetchData : true,
               showFilterEditor : false,
               initialSort : [ {
                  property : "dateChange",
                  direction : "descending"
               } ],
               initialCriteria : initCrit,
               dataSource : historyDS,
               fields : gridFields
            });
            W.addItem(W.grid);
            W.draw();
            W.grid.focusInRow(0);
         }
      });
   }
});

isc.ClassFactory.defineClass('TextItemHistory', 'TextItem').addProperties({
   iconClick : function(form, item, icon) {
      OsobaHistoryWindow.create({
         historyValues : form.getValues(),
         historyItem : item,
         dataSource : form.dataSource
      });
   },
   icons : [ {
      name : "history",
      src : '[SKIN]headerIcons/clock.png',
      disableOnReadOnly : false
   } ],
   init : function() {
      this.Super("init", arguments);
      this.icons[0].prompt = isc.i18nMessages["smartcrfo.buttons.history.prompt"];
   }

});

isc.ClassFactory.defineClass('SmartComboHistory', 'SmartCombo').addProperties({
   iconClick : function(form, item, icon) {
      if (icon.name == "detail") {
         var thisDS = item.optionDataSource;
         if (!thisDS && item.foreignKey)
            thisDS = item.foreignKey.charAt(0).toUpperCase() + item.foreignKey.slice(1).split(".")[0];
         App.getDataSource(thisDS, function(getDS) {
            if (getDS) {
               if (item.getValue() && icon.name == "detail") {
                  App._showDetailByPK(getDS, item.getValue(), App._findParentTabSet(item.grid || item.form));
               }
            }
         });
      } else {
         console.log(item);
         console.log(form);
         OsobaHistoryWindow.create({
            historyValues : form.getValues(),
            historyItem : item,
            dataSource : form.dataSource
         });
      }
   },
   icons : [ {
      name : "detail",
      src : '[SKIN]DynamicForm/default_formItem_icon.png',
      disableOnReadOnly : false
   }, {
      name : "history",
      src : '[SKIN]headerIcons/clock.png',
      disableOnReadOnly : false
   } ],
   init : function() {
      this.Super("init", arguments);
      this.icons[0].prompt = isc.i18nMessages["smartsfs.buttons.showdetail.prompt"];
      this.icons[1].prompt = isc.i18nMessages["smartcrfo.buttons.history.prompt"];
   }

});