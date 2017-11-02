$(document).ready(function () {
//Color DataTable
    $('table#colorTable').DataTable({
        ajax: {
            url: "/data/colors",
            dataSrc: 'data'
        },
        serverSide: true,
        columns: [{
            data: 'code'
        }, {
            data: 'name'
        }],
        order: [[1, "asc"]]
    });
});

