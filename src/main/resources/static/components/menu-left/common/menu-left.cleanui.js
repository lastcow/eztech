/////////////////////////////////////////////////////////////////////////////////////////
// "cui-menu-right" module scripts

;(function($) {
  'use strict'
  $(function() {
    /////////////////////////////////////////////////////////////////////////////////////////
    // set active menu item

    var url = window.location.href
    var page = url.substr(url.lastIndexOf('/') + 1)
    var currentItem = $('.cui-menu-left-list-root').find('a[href="' + page + '"]')
    currentItem.parent().toggleClass('cui-menu-left-item-active')
    currentItem
      .closest('.cui-menu-left-submenu')
      .addClass('cui-menu-left-submenu-toggled')
      .find('> .cui-menu-left-list')
      .slideToggle(0)

    /////////////////////////////////////////////////////////////////////////////////////////
    // add backdrop

    $('.cui-menu-left').after('<div class="cui-menu-left-backdrop"><!-- --></div>')

    /////////////////////////////////////////////////////////////////////////////////////////
    // menu logic

    $('.cui-menu-left-trigger-action').on('click', function() {
      $('body').toggleClass('cui-menu-left-toggled')
    })

    var isTabletView = false

    function toggleMenu() {
      if (!isTabletView) {
        $('body').addClass('cui-menu-left-toggled')
      }
    }

    if ($(window).innerWidth() <= 992) {
      toggleMenu()
      isTabletView = true
    }

    $(window).on('resize', function() {
      if ($(window).innerWidth() <= 992) {
        toggleMenu()
        isTabletView = true
      } else {
        isTabletView = false
      }
    })

    $('.cui-menu-left-handler, .cui-menu-left-backdrop').on('click', function() {
      $('body').toggleClass('cui-menu-left-toggled-mobile')
    })

    /////////////////////////////////////////////////////////////////////////////////////////
    // submenu

    $('.cui-menu-left-submenu > a').on('click', function() {
      if ($('body').find('.cui-menu-left').length) {
        var parent = $(this).parent(),
          opened = $('.cui-menu-left-submenu-toggled')

        if (
          !parent.hasClass('cui-menu-left-submenu-toggled') &&
          !parent.parent().closest('.cui-menu-left-submenu').length
        )
          opened
            .removeClass('cui-menu-left-submenu-toggled')
            .find('> .cui-menu-left-list')
            .slideUp(200)

        parent.toggleClass('cui-menu-left-submenu-toggled')
        var item = parent.find('> .cui-menu-left-list')
        if (item.is(':visible')) {
          item.slideUp(200)
        } else {
          item.slideDown(200)
        }
      }
    })

    /////////////////////////////////////////////////////////////////////////////////////////
    // custom scroll init

    if ($('body').find('.cui-menu-left').length) {
      if (!/Mobi/.test(navigator.userAgent) && jQuery().perfectScrollbar) {
        const menuCustomScroll = $('.cui-menu-left-scroll').perfectScrollbar({
          theme: 'cleanui',
        })
      }
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // colorful menu

    var colorfulClasses =
        'cui-menu-left-colorful-primary cui-menu-left-colorful-secondary cui-menu-left-colorful-primary cui-menu-left-colorful-default cui-menu-left-colorful-info cui-menu-left-colorful-success cui-menu-left-colorful-warning cui-menu-left-colorful-danger cui-menu-left-colorful-yellow',
      colorfulClassesArray = colorfulClasses.split(' ')

    function setColorfulClasses() {
      $('.cui-menu-left-list-root > .cui-menu-left-item').each(function() {
        var randomClass =
          colorfulClassesArray[Math.floor(Math.random() * colorfulClassesArray.length)]
        $(this).addClass(randomClass)
      })
    }

    function removeColorfulClasses() {
      $('.cui-menu-left-list-root > .cui-menu-left-item').removeClass(colorfulClasses)
    }

    if ($('body').hasClass('cui-menu-colorful')) {
      setColorfulClasses()
    }

    $('body').on('setColorfulClasses', function() {
      setColorfulClasses()
    })

    $('body').on('removeColorfulClasses', function() {
      removeColorfulClasses()
    })
  })
})(jQuery)
