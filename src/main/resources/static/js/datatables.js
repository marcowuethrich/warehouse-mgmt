var colorTable = $('table#colorTable');

$(document).ready(function () {
    //Init
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

    //Select
    $('#colorTable').find('tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
    });


});

//Get Selected Items
function loadSelectedItem() {
    var array = [];
    colorTable.rows('.selected').every(function(rowIdx) {
        array.push(colorTable.row(rowIdx).data())
    });

    //Clear Remove Item List
    $('#addColorModal').find('ul').empty();

    if(array.length < 1) {
        $('p#emptyText').removeClass('hidden');
        $('p#removeText').addClass('hidden');
        $('#addColorModal button').last().addClass('hidden');
    }else {
        $('p#removeText').removeClass('hidden');
        $('p#emptyText').addClass('hidden');
        $('#addColorModal button').last().removeClass('hidden');

        array.forEach(function (item) {
            $('#addColorModal').find('ul').append('<li class="list-group-item"><a>' + item.name + '</a></li>')
        })
    }
}
