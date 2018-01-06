var lengthTable = $('table#lengthTable');

$(document).ready(function () {
    lengthTable = lengthTable.DataTable({
        processing: true,
        serverSide: true,
        responsive: true,
        scrollX: true,
        ajax: {
            url: "/data/lengths",
            dataSrc: 'data'
        },
        columns: [
            {"data": "id"},
            {"data": "code"},
            {"data": "size"}
        ],
        columnDefs: [
            {
                "targets": [0],
                "visible": false,
                "searchable": false
            }
        ],
        pageLength: 25,
        "order": [[2, "asc"]]
    });

    $('table#lengthTable').find('tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');
    });

});

function loadToRemoveLengthItem() {
    var array = loadSelectedLengthItem();
    var removeModal = $('#removeLengthModal');

    if (array.length < 1) {
        showDangerAlert("Es wurde kein Element markiert")
    } else {
        hidenDangerAlert();
        removeModal.modal();

        //Clear Remove Item List
        removeModal.find('ul').empty();
        array.forEach(function (item) {
            removeModal.find('ul').append('<li class="list-group-item">' + item.code + ' - ' + item.size + '</li>')
        })
    }
}

function removeSelectedLengthItems() {
    var array = loadSelectedLengthItem();

    array.forEach(function (item) {
        deleteLengthItem(item.id)
    })
}

function loadSelectedLengthItem() {
    var array = [];
    lengthTable.rows('.selected').every(function (rowIdx) {
        array.push(lengthTable.row(rowIdx).data())
    });
    return array;
}

function deleteLengthItem(id) {
    $.ajax({
        url: '/admin/lengths/delete/' + id,
        type: 'DELETE',
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        }
    });
}

function loadLengthEditMode() {
    var array = loadSelectedLengthItem();

    if (array.length === 1) {
        hidenDangerAlert();
        var id = "";
        array.forEach(function (item) {
            id = item.id;
        });

        $.ajax({
            url: '/admin/lengths/edit/' + id,
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