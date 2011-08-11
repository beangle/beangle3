(function( window, undefined ) {
	if(beangle.history) return;
	jQuery.struts2_jquery.require("/struts/js/plugins/jquery.form"+jQuery.struts2_jquery.minSuffix+".js",function(){
		window.beangle.history = {
			init : function(){
				if ( document.location.protocol === 'file:' ) {
					alert('The HTML5 History API (and thus History.js) do not work on files, please upload it to a server.');
				}
				var History = window.History;
				jQuery(document).ready(function(){
					History.Adapter.bind(window,'statechange',function(e){	
						var currState = History.getState();
						if(jQuery.type((currState.data||{}).target)!="undefined" &&  jQuery.type((currState.data||{}).html)!="undefined"){
								jQuery(currState.data.target).empty();
								jQuery(currState.data.target).html(currState.data.html);
						}
					});
				});	
			},
			back : function(queue){
				return History.back(queue);
			},
			forward : function(queue){
				return History.forward(queue);
			},
			historyGo : function(url,target){
				var off = url.indexOf( " " );
				if ( off >= 0 ) {
					var selector = url.slice( off, url.length );
					url = url.slice( 0, off );
				}
				jQuery.ajax({
					url: url,
					type: "GET",
					dataType: "html",
					complete: function( jqXHR, status, responseText ) {
						responseText = jqXHR.responseText;
						if ( jqXHR.isResolved() ) {
							jqXHR.done(function( r ) {
								responseText = r;
							});
							var html = selector ?jQuery("<div>").append(responseText.replace(rscript, "")).find(selector) :responseText;
							History.pushState({html:html,target:"#"+target},"",url);
						}
					}
				});
			},
			historySubmit : function(form,action,target){
				if(jQuery.type(form)=="string" && form.indexOf("#")!=0){
					form = "#" + form;	
				}
				if(jQuery.type(target)=="string" && target.indexOf("#")!=0){
					target = "#" + target;	
				}
				jQuery(form).ajaxForm(function(result,message,response) { 
				    if(message==="success" && response.status==200 && response.readyState==4){
				    	History.pushState({html:result,target:target},"",action);
				    }
					return false; 
				});	
			}
		}
	},bg.getContextPath());		
})(window);