var colorTableEditor;
$(document).ready(function () {
    var colorTable = $('table#colorTable');

    //Color Editor
    colorTableEditor = new $.fn.dataTable.Editor({
        ajax: {
            create: {
                type: 'PUT',
                url: '/data/color/create'
            },
            edit: {
                type: 'PUT',
                url: '/data/color/edit/id=_id_&code=_data[code]_'
            },
            remove: {
                type: 'DELETE',
                url: '/data/color/delete/_id_'
            }
        },
        table: colorTable,
        //idSrc: 'id',
        fields: [{
            label: "Code:",
            name: "code"
        }, {
            label: "Bezeichnung:",
            name: "name"
        }]
    });

    // Activate the bubble editor on click of a table cell
    colorTable.on('click', 'tbody td:not(:first-child)', function (e) {
        colorTableEditor.bubble(this);
    });
    //Disable row inline editing
    colorTable.off('click', 'tbody td:not(:first-child)');

    //Color DataTable
    colorTable.DataTable({
        dom: "Bfrtip",
        ajax: {
            url: "/data/colors",
            dataSrc: 'data'
        },
        serverSide: true,
        columns: [{
            data: null,
            defaultContent: '',
            className: 'select-checkbox',
            orderable: false,
            searchable: false
        }, {
            data: 'code'
        }, {
            data: 'name'
        }],
        select: {
            style: 'os',
            selector: 'td:first-child'
        },
        order: [[2, "asc"]],
        buttons: [
            {extend: "create", editor: colorTableEditor},
            {extend: "edit", editor: colorTableEditor},
            {extend: "remove", editor: colorTableEditor}
        ]
    });
});
