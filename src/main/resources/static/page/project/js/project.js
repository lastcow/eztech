

$(function(){

    /**
     * Enable menu
     */
    $('#mmProject').addClass("selected");

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
                caption: "Students"
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

                if(isEditing){

                }else{
                    $links.filter(".dx-link-edit").removeClass("dx-icon-edit").addClass("icmn-pencil2")
                    $("<a>").addClass("dx-link text-primary icmn-user-plus").on('dxclick', function(){
                        var data = jQuery.extend(true, {}, e.row.data);
                        delete data.__metadata;
                        delete data.id;
                        delete data.projectName;
                        delete data.deadLine;
                        delete data.projectDesc;
                        delete data.todos;
                        delete data.status;
                        delete data.students;
                        $('#fm_add_user').dxDropDownBox({

                            value: ["1", "2"],
                            valueExpr: "id",
                            placeholder: "Select a value...",
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