[#ftl]
[@b.head/]
[#if Parameters['nav']??]
[#include "../nav.ftl"/]
[#else]
[@b.toolbar title="用户权限面板"]bar.addClose("${b.text("action.close")}");[/@]
[/#if]
<style>
#user-profile{
    margin: 10px 5px;
    width: 1000px;
}
#lspace{
	float:left;
	width: 220px;
	padding: 2px;
}
#mspace{
	float:left;
	width: 420px;
	padding: 2px;
}
#rspace{
	float:right;
	width: 340px;
	padding: 2px;
}
#portrait{
	height:80px;
}
#portrait ul, ol {
	list-style-type: none;
}

#portrait img {
	float: left;
    border: 1px solid #CCCCCC;
    padding: 2px;
    height: 60px;
    width: 60px;
}

#portrait #olnks em {
    color: #000000;
    font-style: normal;
    font-weight: bold;
}
#portrait #olnks ul {
    font-size: 10pt;
    line-height: 20px;
    margin-top:2px;
}
#portrait #olnks ul li {
    float: left;
    padding-left: 5px;
    width: 70px;
}
#portrait #olnks ul li a:hover {
    color: #AA0000;
}
div .ui-widget {margin:5px;}
div .ui-widget-header{margin:2px;}
div .portlet-content{margin:2px;}
.ui-icon {float: right;}
</style>
<div id="user-profile">
<div id="lspace">
	<div id="portrait">
		<div id="portrait-img" ><img  title="${user.fullname}" alt="${user.fullname}" src="${b.url('/avatar/my')}"/></div>
		<div id="olnks">
			<em>${user.fullname}</em>
			<ul>
				<li>[@b.a href="!edit" target="user-info"]修改密码[/@]</li>
				[@ems.guard res="/avatar/my-upload"]<li>[@b.a href="/avatar/my-upload"]更换头像[/@]</li>[/@]
				[#--[@ems.guard res="/preference"]<li>[@b.a href="/perference"]设置偏好[/@]</li>[/@]--]
				<li>[@b.a href="/logout" target="_self"]退出系统[/@]</li>
			</ul>
		</div>
	</div>
	[#include "../user/panels/online_portlet.ftl"/]
	[#include "../user/panels/group_portlet.ftl"/]
</div>
<div id="mspace">
[@b.div id="user-info"][#include "infolet.ftl"/][/@]
[#include "../user/panels/menu_portlet.ftl"/]
</div>
<div id="rspace">

[#include "../user/panels/restriction_portlet.ftl"/]
[#include "../user/panels/session_portlet.ftl"/]
</div>
</div>
<script type="text/javascript">
   function _wi_tm(moudleId){
	   var id= document.getElementById(moudleId);
	   if(id.className=="module collapsed"){
		 id.className="module expanded";
	   }else{
		 id.className="module collapsed";
	   }
   }
   $(function() {
		$(".ui-icon").click(function() {
			$(this).toggleClass("ui-icon-minusthick");
			$(this).parents(".column div").find(".portlet-content").toggle();
		});
	});
  </script>
[@b.foot/]
