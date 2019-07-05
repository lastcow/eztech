/////////////////////////////////////////////////////////////////////////////////////////
// "cui-menu-right" module scripts

;(function($) {
  'use strict'
  $(function() {
    /////////////////////////////////////////////////////////////////////////////////////////
    // set active menu item

    var url = window.location.href
    var page = url.substr(url.lastIndexOf('/') + 1)
    var currentItem = $('.cui-menu-top-list-root').find('a[href="' + page + '"]')
    currentItem.parent().toggleClass('cui-menu-top-item-active')

    /////////////////////////////////////////////////////////////////////////////////////////
    // add backdrop

    $('.cui-menu-top').after('<div class="cui-menu-top-backdrop"><!-- --></div>')

    /////////////////////////////////////////////////////////////////////////////////////////
    // menu logic

    $('.cui-menu-top-trigger-action').on('click', function() {
      $('body').toggleClass('cui-menu-top-toggled')
    })

    var isTabletView = false

    function toggleMenu() {
      if (!isTabletView) {
        $('body').addClass('cui-menu-top-toggled')
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

    $('.cui-menu-top-handler, .cui-menu-top-backdrop').on('click', function() {
      $('body').toggleClass('cui-menu-top-toggled-mobile')
    })

    /////////////////////////////////////////////////////////////////////////////////////////
    // submenu

    $('.cui-menu-top-submenu > a').on('click', function() {
      if ($('body').find('.cui-menu-top').length && $(window).innerWidth() < 768) {
        var parent = $(this).parent(),
          opened = $('.cui-menu-top-submenu-toggled')

        if (
          !parent.hasClass('cui-menu-top-submenu-toggled') &&
          !parent.parent().closest('.cui-menu-top-submenu').length
        )
          opened
            .removeClass('cui-menu-top-submenu-toggled')
            .find('> .cui-menu-top-list')
            .slideUp(200)

        parent.toggleClass('cui-menu-top-submenu-toggled')
        var item = parent.find('> .cui-menu-top-list')
        if (item.is(':visible')) {
          item.slideUp(200)
        } else {
          item.slideDown(200)
        }
      }
    })

    /////////////////////////////////////////////////////////////////////////////////////////
    // colorful menu

    var colorfulClasses =
        'cui-menu-top-colorful-primary cui-menu-top-colorful-secondary cui-menu-top-colorful-primary cui-menu-top-colorful-default cui-menu-top-colorful-info cui-menu-top-colorful-success cui-menu-top-colorful-warning cui-menu-top-colorful-danger cui-menu-top-colorful-yellow',
      colorfulClassesArray = colorfulClasses.split(' ')

    function setColorfulClasses() {
      $('.cui-menu-top-list-root > .cui-menu-top-item').each(function() {
        var randomClass =
          colorfulClassesArray[Math.floor(Math.random() * colorfulClassesArray.length)]
        $(this).addClass(randomClass)
      })
    }

    function removeColorfulClasses() {
      $('.cui-menu-top-list-root > .cui-menu-top-item').removeClass(colorfulClasses)
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
