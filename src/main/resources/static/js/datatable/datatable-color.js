var colorTable = $('table#colorTable');

$(document).ready(function () {
    colorTable = colorTable.DataTable({
        processing: true,
        serverSide: true,
        responsive: true,
        scrollX: true,
        ajax: {
            url: "/data/colors",
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
        order: [[2, "asc"]]
    });

    $('table#colorTable').find('tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');
    });

});

function loadToRemoveColorItem() {
    var array = loadSelectedColorItem();
    var removeModal = $('#removeColorModal');

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

function removeSelectedColorItems() {
    var array = loadSelectedColorItem();

    array.forEach(function (item) {
        deleteColorItem(item.id)
    })
}

function loadSelectedColorItem() {
    var array = [];
    colorTable.rows('.selected').every(function (rowIdx) {
        array.push(colorTable.row(rowIdx).data())
    });
    return array;
}

function deleteColorItem(id) {
    $.ajax({
        url: '/admin/colors/delete/' + id,
        type: 'DELETE',
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        }
    });
}

function loadColorEditMode() {
    var array = loadSelectedColorItem();

    if (array.length === 1) {
        hidenDangerAlert();
        var id = "";
        array.forEach(function (item) {
            id = item.id;
        });

        $.ajax({
            url: '/admin/colors/edit/' + id,
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