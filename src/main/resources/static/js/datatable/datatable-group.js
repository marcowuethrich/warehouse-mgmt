var groupTable = $('table#groupTable');

$(document).ready(function () {
    groupTable = groupTable.DataTable({
        processing: true,
        serverSide: true,
        responsive: true,
        scrollX: true,
        ajax: {
            url: "/data/groups",
            dataSrc: 'data'
        },
        columns: [
            {"data": "id"},
            {"data": "category.name"},
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
    // function (data, groupe, row, meta) { //TODO add code the column
    //     return row.category.code + " " + row.category.name;}},

    $('table#groupTable').find('tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');
    });

});

function loadToRemoveGroupItem() {
    var array = loadSelectedGroupItem();
    var removeModal = $('#removeGroupModal');

    if (array.length < 1) {
        showDangerAlert("Es wurde kein Element markiert")
    } else {
        hidenDangerAlert();
        removeModal.modal();

        //Clear Remove Item List
        removeModal.find('ul').empty();
        array.forEach(function (item) {
            removeModal.find('ul').append('<li class="list-group-item">' + item.category.name + ' - ' + item.code + ' - ' + item.name + '</li>')
        })
    }
}

function removeSelectedGroupItems() {
    var array = loadSelectedGroupItem();

    array.forEach(function (item) {
        deleteGroupItem(item.id)
    })
}

function loadSelectedGroupItem() {
    var array = [];
    groupTable.rows('.selected').every(function (rowIdx) {
        array.push(groupTable.row(rowIdx).data())
    });
    return array;
}

function deleteGroupItem(id) {
    $.ajax({
        url: '/admin/groups/delete/' + id,
        type: 'DELETE',
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        }
    });
}

function loadGroupEditMode() {
    var array = loadSelectedGroupItem();

    if (array.length === 1) {
        hidenDangerAlert();
        var id = "";
        array.forEach(function (item) {
            id = item.id;
        });

        $.ajax({
            url: '/admin/groups/edit/' + id,
            groupe: 'GET',
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