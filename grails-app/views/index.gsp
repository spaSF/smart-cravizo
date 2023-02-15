<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="mobile-web-app-capable" content="yes">  
<meta name="apple-mobile-web-app-capable" content="yes">
<title><g:message code="default.application.title" /></title>
<link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'app.ico')}" type="image/x-icon" />
<script type="text/javascript">
var serverTime = ${Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis()}
var localTime =  Date.now();
var localTimeOffset = serverTime - localTime;

var isomorphicDir =  "${createLink(uri: '/isomorphic/')}";
var contextRoot = "${createLink(uri: '')}";
window.isc_useDefaultViewport = false;
window.isc_useMinimalUI = false;
</script>
<r:require module="smartsfs" />
<r:require module="scapplication" />
<r:layoutResources />
</head>
<body onload="setTimeout(function() {window.scrollTo(0, 1)}, 100)">
	  <r:external uri="/isomorphic/skins/${grailsApplication.config.smartsfs.skin}/load_skin.js"/>
      <r:layoutResources />
      <h2><g:message code="smartsfs.needjs.info"/></h2>
      <script type="text/javascript">
      App.create();
      App.get.wsTest = WSTest.create();
      App.get.dsRoleGroupProfile = function(){
          var rgDS=DataSource.get("RoleGroup");
          if(rgDS&&rgDS.isA("DataSource")&&!rgDS.customProfile){
          console.log("After register DS RoleGroup");
          rgDS.customProfile={_constructor:"AdvancedCriteria",operator:"and",criteria:[{fieldName:"id",operator:"greaterThan",value:50}]};
          }
      };
      App.get.observe(App,"registerDataSources","observer.dsRoleGroupProfile()");
      App.get.overTitle = function(){
          isc.i18nMessages["default.application.title"]=isc.i18nMessages["avizo.application.title"]+isc.i18nMessages["avizo.application.version"];
          document.title = isc.i18nMessages["default.application.title"];   
          // #27707       
          if (!App.get.user.hasAnyRole("ROLE_AVREAD")) App.get.config["smartsfs.header.contents"]=App.get.config["cravizo.header.contents"];
          };
      App.get.wsTest.observe(App.get,"createAppLayout","observer.loadWS()");
      App.get.observe(App.get,"userLoginSucces","observer.overTitle()");
      App.get.observe(App.get,"loginUser","observer.overTitle()");
      App.get.loadUser();
      </script>
      <form id="_uploadForm" style="display: none;"></form>
<%--  IE potrebuje fyzicky Form aby urobil submit  --%>
</body>
</html>