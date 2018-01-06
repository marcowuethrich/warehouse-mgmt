var supplierTable = $('table#supplierTable');

$(document).ready(function () {
    supplierTable = supplierTable.DataTable({
        processing: true,
        serverSide: true,
        responsive: true,
        scrollX: true,
        ajax: {
            url: "/data/suppliers",
            dataSrc: 'data'
        },
        columns: [
            {"data": "id"},
            {"data": "code"},
            {"data": "name"}
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

    $('table#supplierTable').find('tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');
    });

});

function loadToRemoveSupplierItem() {
    var array = loadSelectedSupplierItem();
    var removeModal = $('#removeSupplierModal');

    if (array.length < 1) {
        showDangerAlert("Es wurde kein Element markiert")
    } else {
        hidenDangerAlert();
        removeModal.modal();

        //Clear Remove Item List
        removeModal.find('ul').empty();
        array.forEach(function (item) {
            removeModal.find('ul').append('<li class="list-group-item">' + item.code + ' - ' + item.name + '</li>')
        })
    }
}

function removeSelectedSupplierItems() {
    var array = loadSelectedSupplierItem();

    array.forEach(function (item) {
        deleteSupplierItem(item.id)
    })
}

function loadSelectedSupplierItem() {
    var array = [];
    supplierTable.rows('.selected').every(function (rowIdx) {
        array.push(supplierTable.row(rowIdx).data())
    });
    return array;
}

function deleteSupplierItem(id) {
    $.ajax({
        url: '/admin/suppliers/delete/' + id,
        type: 'DELETE',
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        }
    });
}

function loadSupplierEditMode() {
    var array = loadSelectedSupplierItem();

    if (array.length === 1) {
        hidenDangerAlert();
        var id = "";
        array.forEach(function (item) {
            id = item.id;
        });

        $.ajax({
            url: '/admin/suppliers/edit/' + id,
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