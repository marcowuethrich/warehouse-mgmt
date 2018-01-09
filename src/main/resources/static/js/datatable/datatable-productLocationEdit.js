var productLocationEditTable = $('table#productLocationEditTable');

$(document).ready(function () {
    productLocationEditTable = productLocationEditTable.DataTable({
        responsive: true,
        ajax: {
            url: "/data/products",
            dataSrc: "data"
        },
        columns: [
            {"data": "id"},
            {"data": "article.name"},
            {"data": "location.name"},
            {"data": "amount"},
            {
                render: function (data, type, row, meta) {
                    return '<select id=\"locationProductEditLocationSelectInTable' + meta.row + '\" class=\"form-control\"' +
                        '                            onchange=\"changeFieldColor(' + row + ')\"' +
                        '                            title=\"Location\"></select>';
                }
            },
            {
                render: function (data, type, row, meta) {
                    return '<form action="/admin/productLocationEdits/create">' +
                        '<a>an other test Amount</a>';
                }
            }
        ],
        columnDefs: [
            {
                "targets": [0],
                "visible": false,
                "searchable": false
            }, {
                targets: [1],
                orderData: [1, 2]
            }
        ],
        pageLength: 25,
        order: [[1, "asc"]],
        fnInitComplete: function (oSettings, json) {
            filterProductEditTable();
        }
    });

});

function filterProductEditTable() {
    var table = $('table#productLocationEditTable').DataTable();
    var locationName = $('#locationProductEditLocationSelect').find('option:selected').text();
    if (locationName !== "Alle") {
        table.column(2).search(locationName).draw();
    } else {
        table.column(2).search("").draw();
    }
    initDropdown();
}


function loadTableChangeToModal() {

}

function submitProductLocationTableChange() {

}

function changeFieldColor(row) {

}

function initDropdown() {
    var table = $('table#productLocationEditTable').DataTable();
    table.rows().every(function (rowIdx, tableLoop, rowLoop) {
        var select = $('#locationProductEditLocationSelectInTable' + rowLoop);
        select.empty();
        $('#locationProductEditLocationSelect').find('option').clone().appendTo('#locationProductEditLocationSelectInTable' + rowLoop);
        select.find('option:selected').removeAttr('selected');
        select.children().first().remove();
        select.append('<option value="nothing" selected="selected"> - </option>');
    });
}