var productInLocationTable = $('table#productInLocationTable');

$(document).ready(function () {
    productInLocationTable = productInLocationTable.DataTable({
        responsive: true,
        ajax: {
            url: "/data/productInLocations",
            dataSrc: ""
        },
        columns: [
            {"data": "id"},
            {"data": "location.name"},
            {"data": "amountProduct"},
            {"data": "amountDifferentArticle"},
            {"data": "fullRentPrice"},
            {"data": "fullNewPrice"},
            {
                render: function (data, type, row, meta) {
                    return '<form action="/admin/productInLocations/create">' +
                        '<button type="button" class="btn btn-danger btn-circle" ' +
                        'onclick="loadProductInLocationEditMode(\'' + row.id + '\')\"><i ' +
                        'class="fa fa-pencil"></i></button></form>';
                }
            }
        ],
        columnDefs: [
            {
                "targets": [0],
                "visible": false,
                "searchable": false
            }
        ],
        pageLength: 25,
        order: [[1, "asc"]]
    });

});

function loadProductInLocationEditMode(uuid) {
    $.ajax({
        url: '/admin/productToLocationEdit/' + uuid,
        type: 'GET',
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        }
    });
}
