

$(function(){

    /**
     * Enable menu
     */
    $('#mmProject').addClass("selected");

    if($('#tbl_projects')){
        $('#tbl_projects').DataTable();
    }

    // /**
    //  * Projects data source
    //  *
    //  */
    //
    // var dsProjects = new DevExpress.data.CustomStore({
    //
    //    load:function (loadOptions){
    //        var deferred = $.Deferred(),
    //            args = {};
    //
    //        // if(loadOptions.sort){
    //        //     args.orderby = loadOptions.sort[0].selector;
    //        //     if(loadOptions.sort[0].desc)
    //        //         args.orderby += " desc";
    //        // }
    //        //
    //        // args.skip = loadOptions.skip;
    //        // args.take = loadOptions.take;
    //
    //        $.ajax({
    //            url: "/api/data/projects",
    //            // dataType: "json",
    //            type: "POST",
    //            data: args,
    //            success: function(result){
    //                deferred.resolve(result, { totalCount: result.length });
    //            },
    //            error: function(){
    //                deferred.reject ("Data Loading Error");
    //            },
    //            timeout: 5000
    //        });
    //
    //        return deferred.promise();
    //    },
    //     insert: function(values){
    //        return $.ajax({
    //            url: "/api/data/project/new",
    //            method: "POST",
    //            data: values
    //        })
    //     },
    //     update: function (key, values) {
    //         return $.ajax({
    //             url: "/api/data/project/update/"+key.id,
    //             method: "PUT",
    //             data: values
    //         })
    //     }
    // });
    //
    // $("#dgProjects").dxDataGrid({
    //     dataSource: dsProjects,
    //     showBorders: false,
    //     rowAlternationEnabled: true,
    //     editing: {
    //         allowUpdating: true,
    //         allowAdding: true,
    //         mode: "popup",
    //         popup: {
    //             title: "Project",
    //             showTitle: true,
    //             width: 700,
    //             height: 320
    //         },
    //         form: {
    //             items: [{
    //                 itemType: "group",
    //                 colCount: 2,
    //                 colSpan: 2,
    //                 items: ["projectName", {
    //                     dataField: "deadLine",
    //                     editorType: "dxDateBox",
    //                     editorOptions: {
    //                         dateSerializationFormat: "yyyy-MM-dd"
    //                     }
    //                 }, {
    //                     dataField: "projectDesc",
    //                     editorType: "dxTextArea",
    //                     colSpan: 2,
    //                     editorOptions: {
    //                         height: 100
    //                     }
    //                 }]
    //             }]
    //         }
    //     },
    //     pager: {
    //         showPageSizeSelector: true,
    //         allowedPageSizes: [5, 15, 20]
    //     },
    //     columns: [
    //         {
    //             dataField: "projectName",
    //             caption: "Project Name",
    //             validationRules: [{ type: "required" }]
    //         },{
    //             dataField: "deadLine",
    //             dataType: "date",
    //             caption: "Project Deadline",
    //             validationRules: [{ type: "required" }]
    //         },{
    //             dataField: "students",
    //             caption: "Students"
    //         },{
    //             dataField: "todos",
    //             caption: "To-Dos"
    //         },
    //         {
    //             dataField: "projectDesc"
    //         },
    //         {
    //             dataField: "status",
    //             caption: "Project Status"
    //         },{
    //             type: "buttons",
    //             buttons: [{
    //                 text: "My Command",
    //                 hint: "Add user",
    //                 icon: "/img/icons/user_add.png",
    //                 onClick: function(e){
    //                     console.log("User clicked");
    //                 }
    //             }]
    //         }
    //     ],onToolbarPreparing: function(e) {
    //         var dataGrid = e.component;
    //
    //         e.toolbarOptions.items.unshift({
    //             location: "after",
    //             widget: "dxButton",
    //             options: {
    //                 icon: "refresh",
    //                 onClick: function(){
    //                     dataGrid.refresh();
    //                 }
    //             }
    //         });
    //     }
    // }).dxDataGrid("instance");
});