/* 
 * 鼠标拖动修改列宽
 */
(function($) {
    $.fn.dragTable = function(options) {
        var $table = $(this);
        var $th = null;
        $table.find("tr:first th").mousedown(function(event) {
            $th = $(this);
            if (event.pageX > $th.offset().left + $th.width() - 5) {
                $th.mouseDown = true;
                $th.oldX = $th.offset().left;
                $th.oldWidth = $th.width();
            }
        });
        $table.find("tr:first th").mousemove(function(event) {
            if (event.pageX > $(this).offset().left + $(this).width() - 5) $(this).css("cursor", "col-resize"); else $(this).css("cursor", "pointer");
            if ($th == undefined) $th = $(this);
            if (null != $th && $th.mouseDown == true) {
                if (event.pageX - $th.oldX > 0) $th.width(event.pageX - $th.oldX);
            }
        });
        $table.find("tr:first th").mouseup(function(event) {
            if ($th == undefined) $th = $(this);
            $th.mouseDown = false;
            $th.css("cursor", "pointer");
        });
    };
})(jQuery);