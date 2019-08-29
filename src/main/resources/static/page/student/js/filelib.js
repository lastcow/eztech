
var dropifyBox;
var dropifyData;

$(function(){

    $('select').select2();
    /**
     * Enable menu
     */
    $('#menu_left_student_files').addClass("cui-menu-left-item-active");

    $('#student-file-lib').dxFileManager({
        name: "fileLib",
        fileProvider: new DevExpress.FileProviders.WebApi({
            endpointUrl: "/api/FileManagerApi"
        }),
        itemView:{
            // mode: "thumbnails"
        },
        height: 600,
        permissions: {
            // create: true,
            // rename: true,
            // upload: true
        }
    });

    dropifyBox = $('.dropify').dropify({
        messages: {
            'default': 'Drag and drop a file here or click, file will be store to your specified folder only !',
            'replace': 'Drag and drop or click to replace',
            'remove': 'Remove file',
            'error': 'Ooops, something wrong happened'
        }
    });

    dropifyData = dropifyBox.data('dropify');

    // File upload form
    $('#form_file_uploader').on('submit', function(e){
        e.preventDefault();

        // Ajax call
        $.ajax({
            type: 'POST',
            url: '/api/file/upload',
            data: new FormData(this),
            contentType: false,
            cache: false,
            processData: false,
            beforeSend: function(){

            },
            success: function(msg){
                dropifyData.resetPreview();
                dropifyData.clearElement();

                // Show message
                $.notify(
                    {
                        icon: 'icmn-grin',
                        title: '<strong>File successfully uploaded!</strong>',
                        message: 'Uploaded file has been stored to your specified folder.'
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
    })
});
