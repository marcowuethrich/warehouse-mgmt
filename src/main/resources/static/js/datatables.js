var colorTable = $('table#colorTable');

$(document).ready(function () {
    colorTable = colorTable.DataTable({
        processing: true,
        serverSide: true,
        ajax: {
            url: "/data/colors",
            dataSrc: 'data'
        },
        columns: [
            { "data": "id" },
            { "data": "code" },
            { "data": "name" }
        ],
        columnDefs : [
            {
                "targets": [ 0 ],
                "visible": false,
                "searchable": false
            }
        ],
        "order": [[ 2, "asc" ]]
    });

    $('table#colorTable').find('tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
    });

});

function loadToRemoveColorItem() {
    var array = loadSelectedColorItem();
    var removeModal = $('#removeColorModal');
    var emtpyText = $('p#emptyText');
    var removeText = $('p#removeText');

    //Clear Remove Item List
    removeModal.find('ul').empty();

    if(array.length < 1) {
        emtpyText.removeClass('hidden');
        removeText.addClass('hidden');
        removeModal.find('button').last().addClass('hidden');
    }else {
        removeText.removeClass('hidden');
        emtpyText.addClass('hidden');
        removeModal.find('button').last().removeClass('hidden');

        array.forEach(function (item) {
            removeModal.find('ul').append('<li class="list-group-item">' + item.name + '</li>')
        })
    }
}

function removeSelectedColorItems() {
    var array = loadSelectedColorItem();

    array.forEach(function (item){
        deleteColorItem(item.id)
    })
}

function loadSelectedColorItem() {
    var array = [];
    colorTable.rows('.selected').every(function(rowIdx) {
        array.push(colorTable.row(rowIdx).data())
    });
    return array;
}

function deleteColorItem(id) {
    $.ajax({
        url: '/admin/colors/delete/'+ id,
        type: 'DELETE',
        success: function(response) {
            document.open();
            document.write(response);
            document.close();
        }
    });
}

function loadEditMode() {
    var array = loadSelectedColorItem();
    var alert = $('#alertD');
    var msg = $('#alertDMessage');

    if (array.length === 1){
        var id = "";
        array.forEach(function (item){id = item.id;});

        $.ajax({
            url: '/admin/colors/edit/'+ id,
            type: 'GET',
            success: function(response) {
                 document.open();
                 document.write(response);
                 document.close();
            }
        });
    }else if(array.length === 0){
        msg.text("Es wurde kein Element markiert");
        alert.removeClass('hidden');
    }else {
        msg.text("Sie können nur ein Element auf einmal bearbeiten");
        alert.removeClass('hidden');
    }
}