

$(function(){


    // File upload form
    $('#form_question').on('submit', function(e){
        e.preventDefault();

        // Ajax call
        $.ajax({
            type: 'POST',
            url: '/api/question/new',
            data: new FormData(this),
            contentType: false,
            cache: false,
            processData: false,
            beforeSend: function(){

            },
            success: function(msg){

                // Show message
                $.notify(
                    {
                        icon: 'icmn-grin',
                        title: '<strong>Question successfully submitted!</strong>',
                        message: 'Question has been submitted and send to professor.'
                    },
                    {
                        type: 'success',
                        placement: {
                            align: 'center'
                        }

                    }
                );
            }
        })
    });
});