var typTable = $('table#typTable');

$(document).ready(function () {
    typTable = typTable.DataTable({
        processing: true,
        serverSide: true,
        ajax: {
            url: "/data/typs",
            dataSrc: 'data'
        },
        columns: [
            {"data": "id"},
            {"data": "typGroup.name"},
            {"data": "code"},
            {"data": "name"}
        ],
        columnDefs: [
            {
                "targets": [0],
                "visible": false,
                "searchable": false
            },
            {
                targets: [1],
                orderData: [1, 2]
            },
            {
                targets: [2],
                orderData: [2, 3]
            }
        ],
        pageLength: 50,
        order: [[1, "asc"]]
    });

    $('table#typTable').find('tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');
    });

});

function loadToRemoveTypItem() {
    var array = loadSelectedTypItem();
    var removeModal = $('#removeTypModal');

    if (array.length < 1) {
        showDangerAlert("Es wurde kein Element markiert")
    } else {
        hidenDangerAlert();
        removeModal.modal();

        //Clear Remove Item List
        removeModal.find('ul').empty();
        array.forEach(function (item) {
            removeModal.find('ul').append('<li class="list-group-item">' + item.name + '</li>')
        })
    }
}

function removeSelectedTypItems() {
    var array = loadSelectedTypItem();

    array.forEach(function (item) {
        deleteTypItem(item.id)
    })
}

function loadSelectedTypItem() {
    var array = [];
    typTable.rows('.selected').every(function (rowIdx) {
        array.push(typTable.row(rowIdx).data())
    });
    return array;
}

function deleteTypItem(id) {
    $.ajax({
        url: '/admin/typs/delete/' + id,
        type: 'DELETE',
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        }
    });
}

function loadTypEditMode() {
    var array = loadSelectedTypItem();

    if (array.length === 1) {
        hidenDangerAlert();
        var id = "";
        array.forEach(function (item) {
            id = item.id;
        });

        $.ajax({
            url: '/admin/typs/edit/' + id,
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