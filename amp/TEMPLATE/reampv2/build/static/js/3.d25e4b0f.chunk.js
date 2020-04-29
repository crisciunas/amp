(this["webpackJsonpcreate-react-app"]=this["webpackJsonpcreate-react-app"]||[]).push([[3],{77:function(e){e.exports=JSON.parse('{"amp.offline:download":"Download AMP Offline","amp.offline:all-versions":"All installer versions","amp.offline:best-version-message":"We have automatically detected which version of the application meets your operating system requirements. Other versions are available below.","amp.offline:bits":"bits","amp.offline:page-title":"Aid Management Platform - Download Offline Client","amp.offline:download-title":"Download the Offline Client","amp.offline:text":"The AMP Offline application allows you to edit and add activity information to the AMP without having an active internet connection. In order to use the application, you must download and install the compatible version of AMP Offline application from the list of the latest AMP Offline installers. When you run the application for the first time, you must have an active internet connection in order to sync your user data, activity data, and other critical data to the application. After that, you may work offline and sync your data periodically."}')},90:function(e,t,n){e.exports=n.p+"static/media/monitor.17b541c5.png"},91:function(e,t,n){},94:function(e,t,n){"use strict";n.r(t);var a=n(2),r=n(3),i=n(5),o=n(4),l=n(0),s=n.n(l),c=n(12),u=n(72),f=n(14),d=n(73);function p(e,t){return new Promise((function(t,n){return fetch("/rest/translations/label-translations",(a=e,{method:"POST",headers:{"Content-Type":"application/json",Accept:"application/json"},body:JSON.stringify(a)})).then((function(e){return e.json()})).then((function(e){if(e.error)throw new Error(e.error);return t(e)}));var a}))}var m=n(77);var h=function(e){return function(t){return t(function(e){return{type:"FETCH_TRANSLATIONS_PENDING",payload:e}}(e)),p(m).then((function(e){return t(function(e){return{type:"FETCH_TRANSLATIONS_SUCCESS",payload:e}}(e))})).catch((function(e){return t(function(e){return{type:"FETCH_TRANSLATIONS_ERROR",error:e}}(e))}))}},E=n(34),v=n(88),b=n.n(v);var O={pending:!1,releases:[],error:null};var y=function(e){return e.releases},S=function(e){return e.pending},R=function(e){return e.error};var _=function(){return function(e){e({type:"FETCH_RELEASES_PENDING"}),fetch("/rest/amp/amp-offline-release").then((function(e){return e.json()})).then((function(t){if(t.error)throw t.error;return e({type:"FETCH_RELEASES_SUCCESS",payload:t}),t})).catch((function(t){e(function(e){return{type:"FETCH_RELEASES_ERROR",error:e}}(t))}))}},N=function(e){Object(i.a)(n,e);var t=Object(o.a)(n);function n(e){var r;return Object(a.a)(this,n),(r=t.call(this,e)).shouldComponentRender=r.shouldComponentRender.bind(Object(E.a)(r)),r.translations=e.translations,r}return Object(r.a)(n,[{key:"componentDidMount",value:function(){(0,this.props.fetchReleases)()}},{key:"shouldComponentRender",value:function(){return!this.props.pending}},{key:"_buildLinksTable",value:function(){var e=this,t=[];return this.props.releases?(this.props.releases.sort((function(e,t){return e.os>t.os})).map((function(n){return t.push(s.a.createElement("a",{href:"".concat("/rest/amp/amp-offline-release","/").concat(n.id)},e._getInstallerName(n.os,n.arch)))})),s.a.createElement("ul",null,t.map((function(e,t){return s.a.createElement("li",{key:t},e)})))):null}},{key:"_getInstallerName",value:function(e,t){var n="";switch(e){case"windows":n="Windows Vista/7/8/10 - ".concat(t," ").concat(this.translations["amp.offline:bits"]);break;case"debian":n="Ubuntu Linux (.deb) - ".concat(t," ").concat(this.translations["amp.offline:bits"]);break;case"redhat":n="RedHat Linux (.rpm) - ".concat(t," ").concat(this.translations["amp.offline:bits"]);break;case"osx":n="Mac OS - ".concat(t," ").concat(this.translations["amp.offline:bits"])}return n}},{key:"_detectBestInstaller",value:function(){var e=this,t=b.a.os,n=t.architecture,a=t.family.toLowerCase(),r=null;if(a.indexOf("windows")>-1)r=["windows"];else if(a.indexOf("macintosh")>-1||a.indexOf("os x")>-1)r=["osx"];else{if(!(a.indexOf("linux")>-1))return[];r=["debian","redhat"]}var i=this.props.releases.filter((function(e){return r.filter((function(t){return t===e.os})).length>0&&e.arch===n.toString()})).map((function(t){var n=e._getInstallerName(t.os,t.arch);return s.a.createElement("div",{key:t.id,className:"link"},s.a.createElement("a",{href:"".concat("/rest/amp/amp-offline-release","/").concat(t.id)},e.translations["amp.offline:download"]," ",t.version," - ",n))})),o=this.translations["amp.offline:best-version-message"];return i.length>0&&s.a.createElement("div",{className:"alert alert-info",role:"alert"},s.a.createElement("span",{className:"info-text"},o),i)}},{key:"render",value:function(){return this.shouldComponentRender()?s.a.createElement("div",null,s.a.createElement("div",null,this._detectBestInstaller()),s.a.createElement("h4",null,this.translations["amp.offline:all-versions"]),s.a.createElement("div",null,this._buildLinksTable())):s.a.createElement("div",null,"loading")}}]),n}(l.Component),T=Object(f.b)((function(e){return{error:R(e.startupReducer),releases:y(e.startupReducer),pending:S(e.startupReducer),translations:e.translationsReducer.translations}}),(function(e){return Object(c.b)({fetchReleases:_},e)}))(N),w=n(90),C=n.n(w),g=(n(91),function(e){Object(i.a)(n,e);var t=Object(o.a)(n);function n(){return Object(a.a)(this,n),t.apply(this,arguments)}return Object(r.a)(n,[{key:"render",value:function(){var e=this.props.translationsReducer.translations;return s.a.createElement("div",null,s.a.createElement("div",{className:"col-md-5"},s.a.createElement("img",{src:C.a})),s.a.createElement("div",{className:"col-md-7"},s.a.createElement("div",{className:"main_text"},s.a.createElement("h2",null,e["amp.offline:download-title"]),s.a.createElement("span",null,e["amp.offline:text"]),"*/}"),s.a.createElement("div",null,s.a.createElement(T,null))))}}]),n}(l.Component)),j=Object(f.b)((function(e){return Object(d.a)({},e)}),(function(e){return Object(c.b)({},e)}))(g),A={pending:!1,translations:{},error:null};var k=Object(c.c)({startupReducer:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:O,t=arguments.length>1?arguments[1]:void 0;switch(t.type){case"FETCH_RELEASES_PENDING":return Object(d.a)({},e,{pending:!0});case"FETCH_RELEASES_SUCCESS":return Object(d.a)({},e,{pending:!1,releases:t.payload});case"FETCH_RELEASES_ERROR":return Object(d.a)({},e,{pending:!1,error:t.error});default:return e}},translationsReducer:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:A,t=arguments.length>1?arguments[1]:void 0;switch(t.type){case"FETCH_TRANSLATIONS_PENDING":return Object(d.a)({},e,{pending:!0,translations:t.payload.defaultTrnPack});case"FETCH_TRANSLATIONS_SUCCESS":return Object(d.a)({},e,{pending:!1,translations:t.payload});case"FETCH_TRANSLATIONS_ERROR":return Object(d.a)({},e,{pending:!1,error:t.error});default:return e}}}),L=window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__||c.d,I=function(e){Object(i.a)(n,e);var t=Object(o.a)(n);function n(e){var r;return Object(a.a)(this,n),(r=t.call(this,e)).store=Object(c.e)(k,L(Object(c.a)(u.a))),r}return Object(r.a)(n,[{key:"componentDidMount",value:function(){this.store.dispatch(h({defaultTrnPack:m}))}},{key:"render",value:function(){return s.a.createElement(f.a,{store:this.store},s.a.createElement(j,null))}}]),n}(l.Component);t.default=I}}]);
//# sourceMappingURL=3.d25e4b0f.chunk.js.map