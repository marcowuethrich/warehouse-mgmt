var categoryTable = $('table#categoryTable');

$(document).ready(function () {
    categoryTable = categoryTable.DataTable({
        processing: true,
        serverSide: true,
        responsive: true,
        scrollX: true,
        ajax: {
            url: "/data/categories",
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

    $('table#categoryTable').find('tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');
    });

});

function loadToRemoveCategoryItem() {
    var array = loadSelectedCategoryItem();
    var removeModal = $('#removeCategoryModal');

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

function removeSelectedCategoryItems() {
    var array = loadSelectedCategoryItem();

    array.forEach(function (item) {
        deleteCategoryItem(item.id)
    })
}

function loadSelectedCategoryItem() {
    var array = [];
    categoryTable.rows('.selected').every(function (rowIdx) {
        array.push(categoryTable.row(rowIdx).data())
    });
    return array;
}

function deleteCategoryItem(id) {
    $.ajax({
        url: '/admin/categories/delete/' + id,
        type: 'DELETE',
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        }
    });
}

function loadCategoryEditMode() {
    var array = loadSelectedCategoryItem();

    if (array.length === 1) {
        hidenDangerAlert();
        var id = "";
        array.forEach(function (item) {
            id = item.id;
        });

        $.ajax({
            url: '/admin/categories/edit/' + id,
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