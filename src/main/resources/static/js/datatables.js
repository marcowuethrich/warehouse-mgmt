$(document).ready(function () {
    var colorTable = $('table#colorTable');


    colorTable.DataTable({
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

    $('#colorTable').find('tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
    });

});
