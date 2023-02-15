isc.ClassFactory.defineClass("HomeComp", "VLayout").addProperties({
	height : "100%",
	width : "100%",
	padding : 2,
	thisTab : null,
    initWidget : function() {
        this.Super("initWidget", arguments);
		// #27707
		var C = this;
		if (App.get.user.hasAnyRole("ROLE_AVREAD")) {
			var dsName = "OsobaView";
			this.thisTab.name = dsName
			App.getDataSource(dsName, function(rqDS) {
				if (rqDS) {
					C.mainGrid = isc.GridComponentAny
							.create({
								dataSource : rqDS,
								tabSet : appTabSet,
								type : "normal",
								_myTabName : C.thisTab.name,
								getDetail : function() {
									var G = this;
									var req = {};
									var dtlComp;
									var gridDS = this.dataSource;
									if (this.grid.getSelectedRecord()) {
										var pkey = this.dataSource
												.getPrimaryKeyFieldName();
										var recId = this.grid
												.getSelectedRecord()[pkey];
										req[pkey] = recId;
									} else {
										return;
									}
									App._showDetailByPK(this.dataSource, recId,
											appTabSet, G.type, G.brefField,
											G.brefValue, G._ownerComp, G);
								}
							});
					C.mainGrid.toolBar._btnCancel.hide();
					C.addMember(C.mainGrid);
				}
			})
			appTabSet.setTabPane(this.thisTab, C);
		} else {
			this._sectionStack = isc.SectionStack.create({
				height : "100%",
				width : "100%",
				overflow : "auto",
				visibilityMode : "multiple"
			});
			this.addMember(this._sectionStack);
			var oper = isc.HTMLPane
					.create({
						height : "100%",
						contents : "<table style='font-size:16; border-bottom:1px solid black;width:100%;height:20px;'>"
								+ "<tr><td><b>Expression Filter</b></td></tr>"
								+ "</table><br>"
								+ "<table style='font-size:14;height:100%;;max-height:200px;'>"
								+ "<tr><td style='border-bottom:1px solid black;'><b>Prefix</b></td><td style='border-bottom:1px solid black;'><b>Operator</b></td></tr>"
								+ "<tr><td>&lt;</td><td>"
								+ isc.i18nMessages["operators_lessThanTitle"]
								+ "</td></tr>"
								+ "<tr><td>&gt;</td><td>"
								+ isc.i18nMessages["operators_greaterThanTitle"]
								+ "</td></tr>"
								+ "<tr><td>&lt;=</td><td>"
								+ isc.i18nMessages["operators_lessOrEqualTitle"]
								+ "</td></tr>"
								+ "<tr><td>&gt;=</td><td>"
								+ isc.i18nMessages["operators_greaterOrEqualTitle"]
								+ "</td></tr>"
								+ "<tr><td>someValue...someValue</td><td>"
								+ isc.i18nMessages["operators_iBetweenInclusiveTitle"]
								+ "</td></tr>"
								+ "<tr><td>!</td><td>"
								+ isc.i18nMessages["operators_notEqualTitle"]
								+ "</td></tr>"
								+ "<tr><td>^</td><td>"
								+ isc.i18nMessages["operators_startsWithTitle"]
								+ "</td></tr>"
								+ "<tr><td>|</td><td>"
								+ isc.i18nMessages["operators_endsWithTitle"]
								+ "</td></tr>"
								+ "<tr><td>!^</td><td>"
								+ isc.i18nMessages["operators_notStartsWithTitle"]
								+ "</td></tr>"
								+ "<tr><td>!@</td><td>"
								+ isc.i18nMessages["operators_notEndsWithTitle"]
								+ "</td></tr>"
								+ "<tr><td>~</td><td>"
								+ isc.i18nMessages["operators_containsTitle"]
								+ "</td></tr>"
								+ "<tr><td>!~</td><td>"
								+ isc.i18nMessages["operators_notContainsTitle"]
								+ "</td></tr>"
								+ "<tr><td>=(value1|value2)</td><td>"
								+ isc.i18nMessages["operators_inSetTitle"]
								+ "</td></tr>"
								+ "<tr><td>!=(value1|value2)</td><td>"
								+ isc.i18nMessages["operators_notInSetTitle"]
								+ "</td></tr>"
								+ "<tr><td>#</td><td>"
								+ isc.i18nMessages["operators_isNullTitle"]
								+ "</td></tr>"
								+ "<tr><td>!#</td><td>"
								+ isc.i18nMessages["operators_notNullTitle"]
								+ "</td></tr>"
								+ "<tr><td>==</td><td>"
								+ isc.i18nMessages["operators_equalsTitle"]
								+ "</td></tr>"
								+ "<tr><td>=.</td><td>"
								+ isc.i18nMessages["operators_equalsFieldTitle"]
								+ "</td></tr>" + "</table>"
					});
			var shortKeys = isc.HTMLPane
					.create({
						height : "100%",
						contents : "<table style='font-size:16; border-bottom:1px solid black;width:100%;height:20px;'>"
								+ "<tr><td><b>"
								+ isc.i18nMessages["smartsfs.help.shortkeys"]
								+ "</b></td></tr>"
								+ "</table><br>"
								+ "<table style='font-size:14;height:100%;max-height:180px;'>"
								+ "<tr><td style='border-bottom:1px solid black;'><b>"
								+ isc.i18nMessages["smartsfs.help.shortkey"]
								+ "</b></td><td style='border-bottom:1px solid black;'><b>"
								+ isc.i18nMessages["smartsfs.help.shortkeyAction"]
								+ "</b></td></tr>"
								+ "<tr><td>F2 / Ctrl+A</td><td>"
								+ isc.i18nMessages["smartsfs.help.shortkeyF2"]
								+ "</td></tr>"
								+ "<tr><td>F3 / Ctrl+S</td><td>"
								+ isc.i18nMessages["smartsfs.help.shortkeyF3"]
								+ "</td></tr>"
								+ "<tr><td>F4 / Ctrl+F</td><td>"
								+ isc.i18nMessages["smartsfs.help.shortkeyF4"]
								+ "</td></tr>"
								+ "<tr><td>F5 / Ctrl+R</td><td>"
								+ isc.i18nMessages["smartsfs.help.shortkeyF5"]
								+ "</td></tr>"
								+ "<tr><td>F6 / Ctrl+E</td><td>"
								+ isc.i18nMessages["smartsfs.help.shortkeyF6"]
								+ "</td></tr>"
								+ "<tr><td>F7 / Ctrl+I</td><td>"
								+ isc.i18nMessages["smartsfs.help.shortkeyF7"]
								+ "</td></tr>"
								+ "<tr><td>F8 / Ctrl+E</td><td>"
								+ isc.i18nMessages["smartsfs.help.shortkeyF8"]
								+ "</td></tr>"
								+ "<tr><td>F9 / Ctrl+P</td><td>"
								+ isc.i18nMessages["smartsfs.help.shortkeyF9"]
								+ "</td></tr>"
								+ "<tr><td>F10</td><td>"
								+ isc.i18nMessages["smartsfs.help.shortkeyF10"]
								+ "</td></tr>"
								+ "<tr><td>Esc / Ctrl+Q</td><td>"
								+ isc.i18nMessages["smartsfs.help.shortkeyEsc"]
								+ "</td></tr>" + "</table>"
					});
			var help = isc.HLayout.create({
				height : "100%",
				width : "100%"
			});
			help.addMember(oper);
			help.addMember(shortKeys);
			var servcPane = isc.HTMLPane.create({
				height : 200,
				contentsURL : App.appRootUrl + "/services"
			});
			C._sectionStack.addSection({
				name : 'section_help',
				title : isc.i18nMessages["smartcrfo.help.title"],
				expanded : false,
				resizeable : true,
				items : [ help ]
			});
			C._sectionStack.addSection({
				name : 'section_main',
				title : isc.i18nMessages["smartcrfo.services.title"],
				expanded : true,
				resizeable : true,
				items : [ servcPane ]
			});
			App.getDataSource("HotNews", function(newsDS) {
				if (newsDS) {

					var hiliteArray = [ {
						fieldName : "messageText",
						cssText : "color:#0000CC;",
						criteria : {
							fieldName : "priority",
							operator : "equals",
							value : "MEDIUM"
						}
					}, {
						fieldName : "messageText",
						cssText : "color:#FF0000;",
						criteria : {
							fieldName : "priority",
							operator : "equals",
							value : "HIGH"
						}
					} ];

					var now = new Date();
					var hotNewsProfile = {
						_constructor : "AdvancedCriteria",
						operator : "and",
						criteria : [ {
							fieldName : "startDate",
							operator : "lessOrEqual",
							value : now
						}, {
							operator : "or",
							criteria : [ {
								fieldName : "endDate",
								operator : "greaterOrEqual",
								value : now
							}, {
								fieldName : "endDate",
								operator : "isNull"
							} ]
						} ]
					};

					C.homeNews = isc.ListGrid.create({
						ID : "hotNews",
						height : "100%",
						width : "100%",
						dataSource : newsDS,
						initialCriteria : hotNewsProfile,
						refresh : function() {
							this.invalidateCache();
							this.delayCall("refresh", [], 600000);
						},
						type : "normal",
						showHeader : false,
						autoFetchData : true,
						fields : [ {
							name : "messageText",
							title : "messageText"
						}, {
							name : "startDate",
							title : "startDate"
						} ],
						wrapCells : true,
						fixedRecordHeights : false,
						initialSort : [ {
							property : 'startDate',
							direction : 'descending'
						}, {
							property : 'priority',
							direction : 'ascending'
						} ],
						hilites : true ? hiliteArray : null
					});

					C._sectionStack.addSection({
						name : 'section_news',
						title : isc.i18nMessages["smartekom.homenews.title"],
						expanded : true,
						resizeable : true,
						items : [ C.homeNews ]
					});

					C.homeNews.delayCall("refresh", [], 600000);
				}
			});
			appTabSet.setTabPane(this.thisTab, C);
		}
    }
})