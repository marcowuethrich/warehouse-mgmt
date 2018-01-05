var articleTable = $('table#articleTable');

$(document).ready(function () {
    articleTable = articleTable.DataTable({
        processing: true,
        serverSide: true,
        ajax: {
            url: "/data/articles",
            dataSrc: 'data'
        },
        columns: [
            {"data": "id"},
            {"data": "name"},
            {"data": "category.name"},
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
            }
        ],
        pageLength: 25,
        order: [[1, "asc"]]
    });

    $('table#articleTable').find('tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');
    });

});

function loadToRemoveArticleItem() {
    var array = loadSelectedArticleItem();
    var removeModal = $('#removeArticleModal');

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

function removeSelectedArticleItems() {
    var array = loadSelectedArticleItem();

    array.forEach(function (item) {
        deleteArticleItem(item.id)
    })
}

function loadSelectedArticleItem() {
    var array = [];
    articleTable.rows('.selected').every(function (rowIdx) {
        array.push(articleTable.row(rowIdx).data())
    });
    return array;
}

function deleteArticleItem(id) {
    $.ajax({
        url: '/admin/articles/delete/' + id,
        type: 'DELETE',
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        }
    });
}

function loadArticleEditMode() {
    var array = loadSelectedArticleItem();

    if (array.length === 1) {
        hidenDangerAlert();
        var id = "";
        array.forEach(function (item) {
            id = item.id;
        });

        $.ajax({
            url: '/admin/articles/edit/' + id,
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