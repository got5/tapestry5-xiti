(function($){
	
	$.extend(Tapestry.Initializer, {
		xtClick: function(spec) {
			var elementId = spec.elementId;
			var label = spec.label;
			var level = spec.level;
			var type = spec.type;
			var custom = spec.custom;
			if(elementId != null && elementId != undefined){
				$("#"+elementId).bind('click', function(){
					if(custom != null && custom != undefined){
						xt_med('C', level, label, type, null, null, custom);
					}else{
						xt_med('C', level, label, type);
					}
				});
			}
        },
        
        xtForm: function(spec) {
        	var elementId = spec.elementId;
			var label = spec.label;
			var level = spec.level;
			var type = spec.type;
			var custom = spec.custom;
			
			if(elementId != null && elementId != undefined){
				$("#"+elementId).bind('submit', function(){
					if(custom != null && custom != undefined){
						xt_form(this ,'C',level, label, type ,false, custom);
					}else{
						xt_form(this ,'C',level, label, type ,false);
					}
				});
			}
        }
	});
})(jQuery);

