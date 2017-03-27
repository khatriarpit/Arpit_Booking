$(function () {

    var heightDocs = $(window).height() - 70;
    var window_h = $(window).height() - 70;
    var content_h = $('#page-wrapper').height();
    if (window_h <= content_h) {
        $('.page-footer').css('position', 'relative');
        $('#page-wrapper').css('min-height', heightDocs);
    } else {
        // $('.page-footer').css('position', 'absolute');
        $('#sidebar').css('height', heightDocs);
        $('#page-wrapper').css('height', 1200);
    }

    /*********************/
    /*** Menu SideBar ***/
    $('#side-menu').metisMenu();

    $(window).bind("load resize", function () {
        if ($(this).width() < 768) {
            $('body').removeClass('left-side-collapsed');
            $('.navbar-header').removeClass('logo-collapsed');
            $('div.sidebar-collapse').addClass('collapse');
            $('#sidebar').css('height', 'auto');
        } else {
            $('div.sidebar-collapse').removeClass('collapse');
            $('#sidebar').css('height', 'auto');
        }
    });
    var page_wrapper_h = $('#page-wrapper').height();
    var menu_h = $('#side-menu').height();
    var sidebar_h;
    if (page_wrapper_h < menu_h) {
        sidebar_h = page_wrapper_h;
        //BEGIN JQUERY SLIMSCROLL
        $('.menu-scroll').slimScroll({
            "height": sidebar_h,
            "wheelStep": 5
        });
        //END JQUERY SLIMSCROLL
    }
    /*** Menu SideBar ***/
    /*******************/

    //BEGIN TOOTLIP
    $("[data-toggle='tooltip'], [data-hover='tooltip']").tooltip();
    //END TOOLTIP

    //BEGIN POPOVER
    $("[data-toggle='popover'], [data-hover='popover']").popover();
    //END POPOVER

    /*************************/
    /*** Template Setting ***/
    $('#template-setting > a.btn-template-setting').click(function () {
        if ($('#template-setting').css('right') < '0') {
            $('#template-setting').css('right', '0');
        } else {
            $('#template-setting').css('right', '-250px');
        }
    });

    // Begin Change Color Theme
    var setColorTheme = function (color) {
        $.cookie('#color-style', color);
        $('#color-style').attr('href', 'css/themes/' + color + '.css');
    }
    $('ul.color-theme > li').click(function () {
        var color = $(this).attr('data-style');
        setColorTheme(color);
    });
    if ($.cookie('#color-style')) {
        setColorTheme($.cookie('#color-style'));
    }
    // End Change Color Theme

    // Begin Change Style
    $('#change-style').change(function () {
        if ($(this).val() == '0') {
            $('#theme-style').attr('href', 'css/style-mango.css');
        } else {
            $('#theme-style').attr('href', 'css/style-none-border-bottom.css');
        }
    });
    // End Change Style

    /*** Template Setting ***/
    /***********************/

    /****************************/
    /******* Full Screen *******/
    $('.btn-fullscreen').click(function () {

        if (!document.fullscreenElement &&    // alternative standard method
            !document.mozFullScreenElement && !document.webkitFullscreenElement && !document.msFullscreenElement) {  // current working methods
            if (document.documentElement.requestFullscreen) {
                document.documentElement.requestFullscreen();
            } else if (document.documentElement.msRequestFullscreen) {
                document.documentElement.msRequestFullscreen();
            } else if (document.documentElement.mozRequestFullScreen) {
                document.documentElement.mozRequestFullScreen();
            } else if (document.documentElement.webkitRequestFullscreen) {
                document.documentElement.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
            }
        } else {
            if (document.exitFullscreen) {
                document.exitFullscreen();
            } else if (document.msExitFullscreen) {
                document.msExitFullscreen();
            } else if (document.mozCancelFullScreen) {
                document.mozCancelFullScreen();
            } else if (document.webkitExitFullscreen) {
                document.webkitExitFullscreen();
            }
        }
    });
    /******* Full Screen *******/
    /**************************/

    /*************************/
    /******** Portlet *******/
    $(".portlet").each(function (index, element) {
        var me = $(this);
        $(">.portlet-header>.tools>i", me).click(function (e) {
            if ($(this).hasClass('fa-chevron-up')) {
                $(">.portlet-body", me).slideUp('fast');
                $(this).removeClass('fa-chevron-up').addClass('fa-chevron-down');
            }
            else if ($(this).hasClass('fa-chevron-down')) {
                $(">.portlet-body", me).slideDown('fast');
                $(this).removeClass('fa-chevron-down').addClass('fa-chevron-up');
            }
            else if ($(this).hasClass('fa-cog')) {
                //Show modal
            }
            else if ($(this).hasClass('fa-refresh')) {
                //$(">.portlet-body", me).hide();
                $(">.portlet-body", me).addClass('wait');

                setTimeout(function () {
                    //$(">.portlet-body>div", me).show();
                    $(">.portlet-body", me).removeClass('wait');
                }, 1000);
            }
            else if ($(this).hasClass('fa-times')) {
                me.remove();
            }
        });
    });
    /******** Portlet *******/
    /***********************/

    /******************************************/
    /********** Jquery Digital Clock *********/
    window.onload = function () {
        date()
    }, setInterval(function () {
        date()
    }, 1000);
    function date() {
        var dt = new Date();
        var day = dt.getDay();
        var mm, dd, h, m, s;
        mm = (mm = dt.getMonth() + 1) < 10 ? '0' + mm : mm
        dd = (dd = dt.getDate()) < 10 ? '0' + dd : dd
        h = (h = dt.getHours()) < 10 ? '0' + h : h
        m = (m = dt.getMinutes()) < 10 ? '0' + m : m
        s = (s = dt.getSeconds()) < 10 ? '0' + s : s
        var days = new Array("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        $('#get-date').html(days[day] + ', ' + dd + '.' + mm + '.' + dt.getFullYear());
        $('#getHours').html(h);
        $('#getMinutes').html(m);
        $('#getSeconds').html(s);
    }

    /********** Jquery Digital Clock *********/
    /****************************************/

    /***********************************/
    /************ Back To Top *********/
    $(window).scroll(function () {
        if ($(this).scrollTop() < 200) {
            $('#totop').fadeOut();
        } else {
            $('#totop').fadeIn();
        }
    });
    $('#totop').on('click', function () {
        $('html, body').animate({scrollTop: 0}, 'fast');
        return false;
    });
    /************ Back To Top *********/
    /*********************************/


});


//     jQuery('#new_input').click(function(){
//         var html = '<div class="col-md-10 flot_for_hour_left flot_for_hour_right"><input class="clr-blc bottom-input-pad form-control" placeholder="Enter Car Number"  name="car type" type="text"></div><div class="col-md-2 close_icon_for_driver"> <i class="fa fa-close col-md-2"></i></div>';
//         jQuery('#new-box').html(html);
// });
    $(document).ready(function() {
    var max_fields      = 10; //maximum input boxes allowed
    var wrapper         = $("#new-box"); //Fields wrapper
    var add_button      = $("#new_input"); //Add button ID

    var x = 1; //initlal text box count
    $(add_button).click(function(e){ //on add input button click
        e.preventDefault();

        if(x < max_fields){ //max input box allowed
            x++; //text box increment
             var html = '<div><div class="col-md-10 flot_for_hour_left flot_for_hour_right"><input class="clr-blc bottom-input-pad form-control" placeholder="Enter Car Number"  name="car type" type="text"></div><div class="col-md-2 close_icon_for_driver remove_field"> <i class="fa fa-close col-md-2"></i></div> </div>';
            $(wrapper).append(html); //add input box
        }
    });

    $(wrapper).on("click",".remove_field", function(e){ //user click on remove text
        e.preventDefault(); $(this).parent('div').remove(); x--;
    })
});

jQuery(".click-action").click(function(){
    jQuery(".click-action").addClass('bg-applied');
});

jQuery(document).ready(function(){ jQuery('.car-details-wrp ul li').each(function(){ var parent = jQuery(this); if(jQuery(($(this).find('div'))).length == 5){ jQuery($(this).find('.data-not-available ')).css({ 'width' : '24.7143%' }); jQuery($(this).find('.data-booked')).css({ 'width' : '20%' }); jQuery($(this).find('.data-available ')).css({ 'width' : '17.1429%' }); } if(jQuery(($(this).find('div'))).length == 3){ jQuery($(this).find('.data-not-available ')).css({ 'width' : '61%' }); jQuery($(this).find('.data-booked')).css({ 'width' : '20%' }); jQuery($(this).find('.data-available ')).css({ 'width' : '17.1429%' }); } if(jQuery(($(this).find('div'))).length == 1){ jQuery($(this).find('div')).css({ 'width' : '98%' }); } }); });
