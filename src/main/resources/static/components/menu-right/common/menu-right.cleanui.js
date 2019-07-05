/////////////////////////////////////////////////////////////////////////////////////////
// "cui-menu-right" module scripts
;(function($) {
  'use strict'
  $(function() {
    /////////////////////////////////////////////////////////////////////////////////////////
    // toggle right menu

    $('.cui-menu-right-action-toggle').on('click', function() {
      $('body').toggleClass('cui-menu-right-visible')
    })

    /////////////////////////////////////////////////////////////////////////////////////////
    // options scripts

    $('.cui-menu-right-example-option').each(function() {
      var inputs = $(this).find('input'),
        buttons = $(this).find('.btn')

      // detect current options and set active buttons
      var found = false
      inputs.each(function() {
        if ($('body').hasClass($(this).val())) {
          $(this)
            .parent()
            .trigger('click')
          found = true
        }
      })
      if (!found) {
        $(this)
          .find('input[value=""]')
          .parent()
          .trigger('click')
        $('.cui-menu-right .jspPane').css({ top: 0 })
      }

      // change options on click
      $(this)
        .find('.btn')
        .on('click', function() {
          var removeClasses = '',
            addClass = $(this)
              .find('input')
              .val()

          buttons.removeClass('active')
          $(this).addClass('active')

          inputs.each(function() {
            removeClasses += $(this).val() + ' '
          })

          $('body')
            .removeClass(removeClasses)
            .addClass(addClass)

          if (
            $(this)
              .find('input')
              .attr('name') == 'options-colorful' &&
            $(this)
              .find('input')
              .val() == 'cui-menu-left-colorful'
          ) {
            $('body').trigger('removeColorfulClasses')
            $('body').trigger('setColorfulClasses')
          }

          if (
            $(this)
              .find('input')
              .attr('name') == 'options-colorful' &&
            $(this)
              .find('input')
              .val() == ''
          ) {
            $('body').trigger('removeColorfulClasses')
          }
        })
    })
  })
})(jQuery)
