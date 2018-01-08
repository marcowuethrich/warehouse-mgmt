var productNewTable = $('table#articleNewTable');

$(document).ready(function () {
    productNewTable = productNewTable.DataTable({
        processing: true,
        serverSide: true,
        responsive: true,
        scrollX: true,
        select: true,
        ajax: {
            url: "/data/articles",
            dataSrc: 'data'
        },
        columns: [
            {"data": "id"},
            {"data": "name"},
            {"data": "group.name"},
            {"data": "typ.name"},
            {"data": "length.size"},
            {"data": "color.name"},
            {"data": "newPrice"},
            {"data": "rentPrice"}
        ],
        columnDefs: [
            {
                "targets": [0],
                "visible": false,
                "searchable": false
            },
            {
                targets: [6, 7],
                searchable: false
            }
        ],
        pageLength: 10,
        order: [[1, "asc"]],
        fnInitComplete: function (oSettings, json) {
            selectProduct();
        }
    });

    productNewTable.on('select', function (e, dt, type, indexes) {
        if (type === 'row') {
            var uuid = productNewTable.rows(indexes).data().pluck('id');
            var name = productNewTable.rows(indexes).data().pluck('name');

            $('#newProductArticleSelect').append($('<option>', {
                value: uuid[0],
                text: name[0],
                selected: "selected"
            }));
            $('#selectedArticleTitle').val("\"" + name[0] + "\"");
        }
    });

});

function selectProduct() {
    productNewTable.rows().every(function (rowIdx, tableLoop, rowLoop) {
        var uuid = $('#newProductArticleSelect').attr('value');

        var table = $('table#articleNewTable').DataTable();
        var pos = table.column(0, {order: 'current'}).data().indexOf(uuid);
        if (pos >= 0) {
            var page = Math.floor(pos / this.page.info().length);
            this.page(page).draw(false);
        }


        if (this.data().id === uuid) {
            this.select();
        }
    });
}
