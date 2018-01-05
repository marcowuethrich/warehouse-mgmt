var typgroupTable = $('table#typgroupTable');

$(document).ready(function () {
    typgroupTable = typgroupTable.DataTable({
        processing: true,
        serverSide: true,
        ajax: {
            url: "/data/typgroups",
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

    $('table#typgroupTable').find('tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');
    });

});

function loadToRemoveTypGroupItem() {
    var array = loadSelectedTypGroupItem();
    var removeModal = $('#removeTypGroupModal');

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

function removeSelectedTypGroupItems() {
    var array = loadSelectedTypGroupItem();

    array.forEach(function (item) {
        deleteTypGroupItem(item.id)
    })
}

function loadSelectedTypGroupItem() {
    var array = [];
    typgroupTable.rows('.selected').every(function (rowIdx) {
        array.push(typgroupTable.row(rowIdx).data())
    });
    return array;
}

function deleteTypGroupItem(id) {
    $.ajax({
        url: '/admin/typgroups/delete/' + id,
        type: 'DELETE',
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        }
    });
}

function loadTypGroupEditMode() {
    var array = loadSelectedTypGroupItem();

    if (array.length === 1) {
        hidenDangerAlert();
        var id = "";
        array.forEach(function (item) {
            id = item.id;
        });

        $.ajax({
            url: '/admin/typgroups/edit/' + id,
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