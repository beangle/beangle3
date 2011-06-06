jQuery.fn.extend({cssRadio:function(){
	 jQuery(this).each( function(){
        if ( jQuery(this).prev()[0].checked ) {
            jQuery(this).find("img").attr("src",bg.getContextPath()+"/static/themes/default/images/radio2.gif");
          }
        })
    .click( function() { 
         var contents = jQuery(this).parent(); 
        jQuery(".radioClass", contents).each( function() { 
            jQuery(this).prev()[0].checked=false; 
             jQuery(this).find("img").attr("src",bg.getContextPath()+"/static/themes/default/images/radio1.gif"); 
        }); 
        jQuery(this).prev()[0].checked=true; 
          jQuery(this).find("img").attr("src",bg.getContextPath()+"/static/themes/default/images/radio2.gif");
        }); 
}});
