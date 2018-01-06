var productTable = $('table#productTable');

$(document).ready(function () {
    productTable = productTable.DataTable({
        processing: true,
        serverSide: true,
        ajax: {
            url: "/data/products",
            dataSrc: 'data'
        },
        columns: [
            {"data": "id"},
            {"data": "article.name"},
            {"data": "article.category.name"},
            {"data": "article.group.name"},
            {"data": "article.typ.name"},
            {"data": "article.length.size"},
            {"data": "article.color.name"},
            {"data": "supplier.name"},
            {"data": "amount"},
            {"data": "article.rentPrice"},
            {"data": "article.newPrice"},
            {"data": "location.name"}
        ],
        columnDefs: [
            {
                "targets": [0],
                "visible": false,
                "searchable": false
            },
            {
                targets: [7, 8],
                searchable: false
            }
        ],
        pageLength: 25,
        order: [[1, "asc"]]
    });

    $('table#productTable').find('tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');
    });

});

function loadToRemoveProductItem() {
    var array = loadSelectedProductItem();
    var removeModal = $('#removeProductModal');

    if (array.length < 1) {
        showDangerAlert("Es wurde kein Element markiert")
    } else {
        hidenDangerAlert();
        removeModal.modal();

        //Clear Remove Item List
        removeModal.find('ul').empty();
        array.forEach(function (item) {
            removeModal.find('ul').append('<li class="list-group-item">' +
                '' + item.category.name + ' - ' + item.code + ' - ' + item.name + '</li>')
        })
    }
}

function removeSelectedProductItems() {
    var array = loadSelectedProductItem();

    array.forEach(function (item) {
        deleteProductItem(item.id)
    })
}

function loadSelectedProductItem() {
    var array = [];
    productTable.rows('.selected').every(function (rowIdx) {
        array.push(productTable.row(rowIdx).data())
    });
    return array;
}

function deleteProductItem(id) {
    $.ajax({
        url: '/admin/products/delete/' + id,
        type: 'DELETE',
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        }
    });
}

function loadProductEditMode() {
    var array = loadSelectedProductItem();

    if (array.length === 1) {
        hidenDangerAlert();
        var id = "";
        array.forEach(function (item) {
            id = item.id;
        });

        $.ajax({
            url: '/admin/products/edit/' + id,
            type: 'GET',
            success: function (response) {
                document.open();
                document.write(response);
                document.close();
            }
        });
    } else if (array.length === 0) {
        showDangerAlert("Es wurde kein Element markiert")
    } else {
        showDangerAlert("Es kann nur ein Element auf einmal bearbeiten werden");
    }
}