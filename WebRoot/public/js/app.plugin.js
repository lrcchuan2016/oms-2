+function ($) { "use strict";

  $(function(){
	// slider
	$('.slider').each(function(){
		$(this).slider();
	});
	
	// slim-scroll
	$('.no-touch .slim-scroll').each(function(){
		var $self = $(this), $data = $self.data(), $slimResize=-1;
		$self.slimScroll($data);
		$(window).resize(function(e) {
			clearTimeout($slimResize);
			$slimResize = setTimeout(function(){$self.slimScroll($data);}, 500);
		});
	    $(document).on('updateNav', function(){
	      $self.slimScroll($data);
	    });
	});	
  });
}(window.jQuery);