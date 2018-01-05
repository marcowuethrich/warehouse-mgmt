var locationTable = $('table#locationTable');

$(document).ready(function () {
    locationTable = locationTable.DataTable({
        processing: true,
        serverSide: true,
        ajax: {
            url: "/data/locations",
            dataSrc: 'data'
        },
        columns: [
            {"data": "id"},
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

    $('table#locationTable').find('tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');
    });

});

function loadToRemoveLocationItem() {
    var array = loadSelectedLocationItem();
    var removeModal = $('#removeLocationModal');

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

function removeSelectedLocationItems() {
    var array = loadSelectedLocationItem();

    array.forEach(function (item) {
        deleteLocationItem(item.id)
    })
}

function loadSelectedLocationItem() {
    var array = [];
    locationTable.rows('.selected').every(function (rowIdx) {
        array.push(locationTable.row(rowIdx).data())
    });
    return array;
}

function deleteLocationItem(id) {
    $.ajax({
        url: '/admin/locations/delete/' + id,
        type: 'DELETE',
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        }
    });
}

function loadLocationEditMode() {
    var array = loadSelectedLocationItem();

    if (array.length === 1) {
        hidenDangerAlert();
        var id = "";
        array.forEach(function (item) {
            id = item.id;
        });

        $.ajax({
            url: '/admin/locations/edit/' + id,
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