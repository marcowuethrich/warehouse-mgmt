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
                    return '<form action="/admin/productLocationEdits/create">' +
                        '<a>Test Loc.</a>';
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
}


function loadTableChangeToModal() {

}

function submitProductLocationTableChange() {

}
