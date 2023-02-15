isc.ClassFactory.defineClass("XMLBeauty").addProperties(
      {
         step : '   ',// 3 spaces
         shift : [],
         createShiftArr : function(step) {
            var space = '    ';
            if (isNaN(parseInt(step))) { // argument is string
               space = step;
            } else { // argument is integer
               switch (step) {
                  case 1:
                     space = ' ';
                     break;
                  case 2:
                     space = '  ';
                     break;
                  case 3:
                     space = '   ';
                     break;
                  case 4:
                     space = '    ';
                     break;
                  case 5:
                     space = '     ';
                     break;
                  case 6:
                     space = '      ';
                     break;
                  case 7:
                     space = '       ';
                     break;
                  case 8:
                     space = '        ';
                     break;
                  case 9:
                     space = '         ';
                     break;
                  case 10:
                     space = '          ';
                     break;
                  case 11:
                     space = '           ';
                     break;
                  case 12:
                     space = '            ';
                     break;
               }
            }

            var shift = [ '\n' ]; // array of shifts
            for (ix = 0; ix < 100; ix++) {
               shift.push(shift[ix] + space);
            }
            return shift;
         },
         init : function() {
            this.step = '   '; // 3 spaces
            this.shift = this.createShiftArr(this.step);
         },
         parseXml : function(text, step) {

            var ar = text.replace(/>\s{0,}</g, "><").replace(/</g, "~::~<").replace(/\s*xmlns\:/g, "~::~xmlns:").replace(/\s*xmlns\=/g, "~::~xmlns=")
                  .split('~::~'), len = ar.length, inComment = false, deep = 0, str = '', ix = 0, shift = step ? this.createShiftArr(step) : this.shift;

            for (ix = 0; ix < len; ix++) {
               // start comment or <![CDATA[...]]> or <!DOCTYPE //
               if (ar[ix].search(/<!/) > -1) {
                  str += shift[deep] + ar[ix];
                  inComment = true;
                  // end comment or <![CDATA[...]]> //
                  if (ar[ix].search(/-->/) > -1 || ar[ix].search(/\]>/) > -1 || ar[ix].search(/!DOCTYPE/) > -1) {
                     inComment = false;
                  }
               } else
               // end comment or <![CDATA[...]]> //
               if (ar[ix].search(/-->/) > -1 || ar[ix].search(/\]>/) > -1) {
                  str += ar[ix];
                  inComment = false;
               } else
               // <elm></elm> //
               if (/^<\w/.exec(ar[ix - 1]) && /^<\/\w/.exec(ar[ix])
                     && /^<[\w:\-\.\,]+/.exec(ar[ix - 1]) == /^<\/[\w:\-\.\,]+/.exec(ar[ix])[0].replace('/', '')) {
                  str += ar[ix];
                  if (!inComment)
                     deep--;
               } else
               // <elm> //
               if (ar[ix].search(/<\w/) > -1 && ar[ix].search(/<\//) == -1 && ar[ix].search(/\/>/) == -1) {
                  str = !inComment ? str += shift[deep++] + ar[ix] : str += ar[ix];
               } else
               // <elm>...</elm> //
               if (ar[ix].search(/<\w/) > -1 && ar[ix].search(/<\//) > -1) {
                  str = !inComment ? str += shift[deep] + ar[ix] : str += ar[ix];
               } else
               // </elm> //
               if (ar[ix].search(/<\//) > -1) {
                  str = !inComment ? str += shift[--deep] + ar[ix] : str += ar[ix];
               } else
               // <elm/> //
               if (ar[ix].search(/\/>/) > -1) {
                  str = !inComment ? str += shift[deep] + ar[ix] : str += ar[ix];
               } else
               // <? xml ... ?> //
               if (ar[ix].search(/<\?/) > -1) {
                  str += shift[deep] + ar[ix];
               } else
               // xmlns //
               if (ar[ix].search(/xmlns\:/) > -1 || ar[ix].search(/xmlns\=/) > -1) {
                  str += shift[deep] + ar[ix];
               }

               else {
                  str += ar[ix];
               }
            }

            return (str[0] == '\n') ? str.slice(1) : str;
         }

      });

/**
 * WSTest class pre testovanie WS crfo
 */
isc.ClassFactory.defineClass("WSTest").addClassProperties({
   wsProps : [ {
      name : "ws1",
      wsoper : "ws1OverenieRegistracie",
      wsresp : "ws1OverenieRegistracieResponse",
      title : "requestLog.ws1",
      icon : "[SKIN]/custom/world.png"
   }, {
      name : "ws1recall",
      wsoper : "ws1RecallRequest",
      wsresp : "ws1RecallRequestResponse",
      title : "requestLog.ws1recall",
      icon : "[SKIN]/custom/world.png"
   }, {
      name : "ws2ifo",
      wsoper : "ws2AktualizaciaFOByIFO",
      wsresp : "ws2AktualizaciaFOByIFOResponse",
      title : "requestLog.ws2ifo",
      icon : "[SKIN]/custom/world.png"
   }, {
      name : "ws2rc",
      wsoper : "ws2AktualizaciaFOByRC",
      wsresp : "ws2AktualizaciaFOByRCResponse",
      title : "requestLog.ws2",
      icon : "[SKIN]/custom/world.png"
   }, {
      name : "ws2recall",
      wsoper : "ws2RecallRequest",
      wsresp : "ws2RecallRequestResponse",
      title : "requestLog.ws2recall",
      icon : "[SKIN]/custom/world.png"
   }, {
      name : "ws3",
      wsoper : "ws3ZmenySkupUdajov",
      wsresp : "ws3ZmenySkupUdajovResponse",
      title : "requestLog.ws3",
      icon : "[SKIN]/custom/world.png"
   }, {
      name : "ws4",
      wsoper : "ws4ZaujemFO",
      wsresp : "ws4ZaujemFOResponse",
      title : "requestLog.ws4",
      icon : "[SKIN]/custom/world.png"
   }, {
      name : "ws4recall",
      wsoper : "ws4RecallRequest",
      wsresp : "ws4RecallRequestResponse",
      title : "requestLog.ws4recall",
      icon : "[SKIN]/custom/world.png"
   },{
      name : "ws6",
      wsoper : "ws6NovoNarodeneDeti",
      wsresp : "ws6NovoNarodeneDetiResponse",
      title : "requestLog.ws6",
      icon : "[SKIN]/custom/world.png"
	   } ],
   service : null,
   wsName : null,
   params : null
}).addProperties(
      {
         loadWS : function(cbk) {
            var W = this;
            if (W.service == null) {
               isc.XMLTools.loadWSDL(App.appRootUrl + "/services/crfoWSA?wsdl", function(servis) {
                  W.service = servis;
                  if (isA.Function(cbk))
                     cbk();
               }, null, true);
            }
         },
         sendWS : function(wsName, params) {
            var W = this;
            if(W.dialog&&W.dialog.isA("Window")) W.dialog.destroy();
            if (!W.service) {
               // neni loadnuty service
               if (wsName) {
                  W.wsName = wsName;
                  W.params = params;
               }
               W.loadWS(W.sendWS);
            } else {
               if (!wsName) {
                  wsName = W.wsName;
                  params = W.params;
               }

               var wsprop = WSTest.wsProps.find({
                  name : wsName
               });
               W.parentDataComp = params["dataComp"];
               W.inputDS = W.service.getInputDS(wsprop.wsoper);
               console.log(W.inputDS);
               W.inputForm = isc.DynamicForm.create({
                  validateOnExit : true,
                  width : "100%",
                  border : "1px solid blue",
                  padding : 20,
                  dataSource : W.inputDS,
                  canEdit : true,
                  wrapItemTitles : false,
                  autoFocus : true
               });
               var fields = W.inputForm.getFields();
               var fldProps = [];
               for (var f = 0; f < fields.length; f++) {
                  var field = fields[f];
                  prop = {
                        name:field.name,
                        type:field.type,
                        title :isc.i18nMessages["requestLog." + wsprop.name + "." + field.name + ".label"],
                        valuesMap:field.valuesMap
                  };
                  fldProps.add(prop);
                  field.destroy();
               }
               fldProps.add({
                  name : "spacer",
                  editorType : "RowSpacerItem"
               });

               fldProps.add({
                  name : "executeBtn",
                  type : "button",
                  canFocus : true,
                  colSpan : 2,
                  canEdit : null,
                  title : isc.i18nMessages[wsprop.title],
                  autoFit : true,
                  showButtonTitle : true,
                  icon : wsprop.icon,
                  click : function() {
                     if (this.form.validate()) {
                        var resp = W.service.callOperation(wsprop.wsoper, W.inputForm.getValues(), wsprop.wsresp, function(data, xmlDoc, rpcResponse,
                              wsRequest) {
                           var fmt = XMLBeauty.create().parseXml(isc.XMLTools.serializeToString(xmlDoc));
                           W.inputForm.setValue("result", fmt);
                           W.inputForm.items.find({
                              name : "result"
                           }).show();
                           W.inputForm.markForRedraw();
                           W.parentDataComp.reload();
                        }, {
                           xmlResult : true
                        });
                     }
                  }
               });
               fldProps.add({
                  name : "rspace",
                  editorType : "RowSpacerItem"
               });
               fldProps.add({
                  name : "result",
                  colSpan : 3,
                  endRow : true,
                  startRow : true,
                  length : 50000,
                  title : isc.i18nMessages["requestLog.wstest.result.label"],
                  titleOrientation : "top",
                  type : "text",
                  width : 900,
                  minHeight : 250,
                  maxHeight : 500,
                  wrap : "OFF",
                  visible : false
               });
               //removeFields je tajna operacia
               W.inputForm.removeFields(fields);
               W.inputForm.setFields(fldProps);
               W.dialog = isc.Window.create({
                  width : 300,
                  isModal : true,
                  headerIconProperties : {
                     src : wsprop.icon
                  },
                  showModalMask : true,
                  autoCenter : true,
                  autoSize : true,
                  canDragResize : true,
                  showFooter : true,
                  showMinimizeButton : false,
                  canDragReposition : true,
                  canDragResize : true,
                  title : isc.i18nMessages[wsprop.title],
                  items : [ W.inputForm ],
                  autoDraw : true
               });
            }

         }

      });