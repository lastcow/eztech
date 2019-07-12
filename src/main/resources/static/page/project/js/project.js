

$(function(){

    /**
     * Enable menu
     */
    $('#menu_project_left').addClass("cui-menu-left-item-active");

    // if($('#tbl_projects')){
    //     $('#tbl_projects').DataTable();
    // }

    /**
     * Projects data source
     *
     */

    var dsProjects = new DevExpress.data.CustomStore({

       load:function (loadOptions){
           var deferred = $.Deferred(),
               args = {};

           // if(loadOptions.sort){
           //     args.orderby = loadOptions.sort[0].selector;
           //     if(loadOptions.sort[0].desc)
           //         args.orderby += " desc";
           // }
           //
           // args.skip = loadOptions.skip;
           // args.take = loadOptions.take;

           $.ajax({
               url: "/api/data/projects",
               // dataType: "json",
               type: "POST",
               data: args,
               success: function(result){
                   deferred.resolve(result, { totalCount: result.length });
               },
               error: function(){
                   deferred.reject ("Data Loading Error");
               },
               timeout: 5000
           });

           return deferred.promise();
       },
        insert: function(values){
           return $.ajax({
               url: "/api/data/project/new",
               method: "POST",
               data: values
           })
        },
        update: function (key, values) {
            return $.ajax({
                url: "/api/data/project/update/"+key.id,
                method: "PUT",
                data: values
            })
        }
    });

    $("#dgProjects").dxDataGrid({
        dataSource: dsProjects,
        showBorders: false,
        rowAlternationEnabled: true,
        editing: {
            allowUpdating: true,
            allowAdding: true,
            allowDelete: true,
            useIcons: true,
            mode: "popup",
            popup: {
                title: "Project",
                showTitle: true,
                width: 700,
                height: 320
            },
            form: {
                items: [{
                    itemType: "group",
                    colCount: 2,
                    colSpan: 2,
                    items: ["projectName", {
                        dataField: "deadLine",
                        editorType: "dxDateBox",
                        editorOptions: {
                            dateSerializationFormat: "yyyy-MM-dd"
                        }
                    }, {
                        dataField: "projectDesc",
                        editorType: "dxTextArea",
                        colSpan: 2,
                        editorOptions: {
                            height: 100
                        }
                    }]
                }]
            }
        },
        pager: {
            showPageSizeSelector: true,
            allowedPageSizes: [5, 15, 20]
        },
        columns: [
            {
                dataField: "projectName",
                caption: "Project Name",
                validationRules: [{ type: "required" }]
            },{
                dataField: "deadLine",
                dataType: "date",
                caption: "Project Deadline",
                validationRules: [{ type: "required" }]
            },{
                dataField: "students",
                caption: "Members"
            },{
                dataField: "todos",
                caption: "To-Dos"
            },
            {
                dataField: "projectDesc"
            },
            {
                dataField: "status",
                caption: "Project Status"
            }
        ],onToolbarPreparing: function(e) {
            var dataGrid = e.component;

            e.toolbarOptions.items.unshift({
                location: "after",
                widget: "dxButton",
                options: {
                    icon: "refresh",
                    onClick: function(){
                        dataGrid.refresh();
                    }
                }
            });
        },
        onCellPrepared: function(e){
            if(e.rowType === "data" && e.column.command === "edit"){
                var isEditing = e.row.isEditing,
                    $links = e.cellElement.find(".dx-link");
                $links.text("");
                var projectId = e.row.data.id;

                if(isEditing){

                }else{
                    $links.filter(".dx-link-edit").removeClass("dx-icon-edit").addClass("icmn-pencil2")
                    $("<a>").addClass("dx-link text-primary icmn-user-plus").on('dxclick', function(){
                        var data = jQuery.extend(true, {}, e.row.data);
                        $('#fm_add_user').dxDropDownBox({

                            value: _.pluck(data.users, "id"),
                            valueExpr: "id",
                            placeholder: "Select members ...",
                            displayExpr: "email",
                            showClearButton: true,
                            dataSource: getDataSourceUsers(),
                            contentTemplate: function (e) {
                                var value = e.component.option("value"),
                                    $dataGrid = $("<div>").dxDataGrid({
                                        dataSource: e.component.option("dataSource"),
                                        columns: ["name", "email"],
                                        hoverStateEnabled: true,
                                        paging: {enabled: true, pageSize: 10},
                                        filterRow: {visible: true},
                                        scrolling: {mode: "infinite"},
                                        height: 345,
                                        selection: {mode: "multiple"},
                                        selectedRowKeys: value,
                                        onSelectionChanged: function (selectedItems) {
                                            var keys = selectedItems.selectedRowKeys;
                                            e.component.option("value", keys);
                                        }
                                    });

                                dataGrid = $dataGrid.dxDataGrid("instance");

                                e.component.on("valueChanged", function (args) {
                                    var value = args.value;
                                    dataGrid.selectRows(value, false);
                                });

                                return $dataGrid;
                            }
                        });

                        $('#popup_add_users').dxPopup({
                            title: 'Users for this project',
                            visible: true,
                            height: "200",
                            width: "800",
                            toolbarItems: [
                                {
                                    toolbar: "bottom",
                                    widget: "dxButton",
                                    location: "after",
                                    options: {
                                        text: "Save",
                                        onClick: function(e){

                                            // Write data to database.
                                            $.ajax({
                                                type: "POST",
                                                contentType: "application/json",
                                                url: "/professor/project/" + projectId + "/user/add",
                                                data: JSON.stringify($('#fm_add_user').dxDropDownBox('instance').option('value')),
                                                dataType: "json",
                                                cache: false,
                                                timeout: 600000,
                                                success: function(data){
                                                    $("#dgProjects").dxDataGrid("instance").refresh();
                                                }
                                            });
                                            $('#popup_add_users').dxPopup('hide');
                                        }
                                    }
                                },{
                                    toolbar: "bottom",
                                    widget: "dxButton",
                                    location: "after",
                                    options: {
                                        text: "Cancel",
                                        onClick: function(e){
                                            // Close popup window
                                            $('#popup_add_users').dxPopup('hide');
                                        }
                                    }
                                }
                            ]
                        });
                    }).appendTo(e.cellElement);

                    $("<a>").addClass("dx-link text-warning icmn-target").on('dxclick', function() {
                        $('#text_add_milestone').dxTextBox({
                            placeholder: "Enter milestone...",
                            showClearButton: true
                        }).dxValidator({
                            type: "required",
                            message: "Description is required"
                        });

                        $('#date_add_milestone').dxDateBox({
                            type: "date"
                        });

                        $('#popup_add_milestone').dxPopup({
                           title: 'Add milestone',
                            visible: true,
                            height: "300",
                            width: "600",
                            toolbarItems: [
                                {
                                    toolbar: "bottom",
                                    widget: "dxButton",
                                    location: "after",
                                    options: {
                                        text: "Save",
                                        onClick: function(e){
                                            // Get Data
                                            var milestone = {};
                                            milestone["projectId"] = projectId;
                                            milestone["desc"] = $('#text_add_milestone').dxTextBox("instance").option("value");
                                            milestone["deadline"] = $('#date_add_milestone').dxDateBox("instance").option("value");
                                            // Write data to database.
                                            $.ajax({
                                                type: "POST",
                                                contentType: "application/json",
                                                url: "/professor/project/milestone/add",
                                                data: JSON.stringify(milestone),
                                                dataType: "json",
                                                cache: false,
                                                timeout: 600000,
                                                success: function(data){
                                                    $("#dgProjects").dxDataGrid("instance").refresh();
                                                }
                                            });
                                            $('#popup_add_milestone').dxPopup('hide');
                                        }
                                    }
                                },{
                                    toolbar: "bottom",
                                    widget: "dxButton",
                                    location: "after",
                                    options: {
                                        text: "Cancel",
                                        onClick: function(e){
                                            // Close popup window
                                            $('#popup_add_milestone').dxPopup('hide');
                                        }
                                    }
                                }
                            ]
                        });
                    }).appendTo(e.cellElement);
                }
            }
        }
    }).dxDataGrid("instance");

    var getDataSourceUsers = function(){
        return new DevExpress.data.CustomStore({
            loadMode: "raw",
            key: "id",
            load: function() {
                return $.getJSON("/api/data/users/all");
            }
        });
    };


    // var fmNewUser = $('#fm_add_user').dxForm({
    //
    //     items: [
    //         "email"
    //     ]
    // }).dxForm("instance");

    $('#fm_add_user').dxForm({
    });
});