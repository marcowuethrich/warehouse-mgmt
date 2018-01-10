var productLocationEditTable = $('table#productLocationEditTable');

$(document).ready(function () {
    productLocationEditTable = productLocationEditTable.DataTable({
        responsive: true,
        ajax: {
            url: "/data/products",
            dataSrc: "data"
        },
        columns: [
            {"data": "id"},
            {"data": "article.name"},
            {"data": "location.name"},
            {"data": "amount"},
            {
                render: function (data, type, row, meta) {
                    return '<select id=\"locationProductEditLocationSelectInTable' + meta.row + '\" class=\"form-control\"' +
                        '                            onchange=\"changeLocationFieldColor(this)\"' +
                        '                            title=\"Location\"></select>';
                }
            },
            {
                render: function (data, type, row, meta) {
                    return '<input type="number" id="locationProductEditAmountSelectInTable" ' +
                        'onchange="changeAmountFieldColor(this)" class="form-control" placeholder="1" \>';
                }
            }
        ],
        columnDefs: [
            {
                "targets": [0],
                "visible": false,
                "searchable": false
            }, {
                targets: [1],
                orderData: [1, 2]
            }
        ],
        pageLength: 25,
        order: [[1, "asc"]],
        fnInitComplete: function (oSettings, json) {
            filterProductEditTable();
        }
    });
});

function filterProductEditTable() {
    var table = $('table#productLocationEditTable').DataTable();
    var locationName = $('#locationProductEditLocationSelect').find('option:selected').text();
    if (locationName !== "Alle") {
        table.column(2).search(locationName).draw();
    } else {
        table.column(2).search("").draw();
    }
    initDropdown();
}


function loadTableChangeToModal() {

}

function submitProductLocationTableChange() {

}

function changeLocationFieldColor(select) {
    var tr = $(select).closest('tr');
    var or_loc = tr.find('td:eq(1)').text();
    var ed_loc = $(select).find('option:selected').text();
    if (or_loc === ed_loc || ed_loc === " - ") {
        $(select).closest('td').removeAttr("style");
    } else {
        $(select).closest('td').css('background', '#ffc10745');
    }
}

function changeAmountFieldColor(input) {
    var tr = $(input).closest('tr');
    var or_amount = Number(tr.find('td:eq(2)').text());
    var ed_amount = Number($(input).val());

    if (or_amount < ed_amount && ed_amount % 1 === 0) {
        $(input).val(or_amount);
    } else if (ed_amount < 0 || ed_amount % 1 !== 0) {
        $(input).val(0);
    }


    if (ed_amount === 0 || ed_amount < 0 || ed_amount % 1 !== 0) {
        $(input).closest('td').removeAttr("style");
    } else {
        $(input).closest('td').css('background', '#ffc10745');
    }
}

function initDropdown() {
    var table = $('table#productLocationEditTable').DataTable();
    table.rows().every(function (rowIdx, tableLoop, rowLoop) {
        var select = $('#locationProductEditLocationSelectInTable' + rowLoop);
        if (select.has('option').length === 0) {
            select.empty();
            $('#locationProductEditLocationSelect').find('option').clone().appendTo('#locationProductEditLocationSelectInTable' + rowLoop);
            select.find('option:selected').removeAttr('selected');
            select.children().first().remove();
            select.append('<option value="nothing" selected="selected"> - </option>');

        } else {
        }
    });
}