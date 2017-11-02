$(document).ready(function () {
var colorTable = $('table#colorTable');
var colorTableEditor;

    //Color Editor
    colorTableEditor = new $.fn.dataTable.Editor({
        table: colorTable,
        fields: [{
            label: "Code",
            name: "code"
        }, {
            label: "Bezeichnung",
            name: "name"
        }]
    });

    // Activate the bubble editor on click of a table cell
    colorTable.on('click', 'tbody td:not(:first-child)', function (e) {
        colorTableEditor.bubble(this);
    });

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
            orderable: false
        }, {
            data: 'code'
        }, {
            data: 'name'
        }],
        order: [[1, "asc"]],
        select: {
            style:    'os',
            selector: 'td:first-child'
        },
        buttons: [
            {extend: "create", editor: colorTableEditor},
            {extend: "edit", editor: colorTableEditor},
            {extend: "remove", editor: colorTableEditor}
        ]
    });

});

