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
                    return '<select id="locationProductEditLocationSelectInTable' + meta.row + '" class="form-control"' +
                        '                            onchange="changeLocationFieldColor(this)" title="Location"></select>';
                }
            },
            {
                render: function (data, type, row, meta) {
                    return '<input type="number" id="locationProductEditAmountSelectInTable' + meta.row + '"' +
                        ' onchange="changeAmountFieldColor(this)" class="form-control" placeholder="1" \>';
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
    var array = loadChanges();
    if (array.length > 0) {
        $('#removeProductLocationEditModal').modal();
        var table = $('#productLocationChangeTable').find('tbody');

        table.empty();
        for (var i = 0, len = array.length; i < len; i++) {
            var newRowContent = "<tr>" +
                "<td>" + array[i].locationName + "</td>" +
                "<td>-></td>" +
                "<td>" + array[i].editAmount + "x " + array[i].articleName + "</td>" +
                "<td>-></td>" +
                "<td>" + array[i].editLocationName + "</td>" +
                "</tr>";
            table.append(newRowContent);
        }
    } else {
        showDangerAlert("Keine Produkte zum verschieben!")
    }

}

function submitProductLocationTableChange() {
    var array = loadChanges();
    var success = true;
    if (!sendChangeToBackend(array)) {
        success = false;
    }
    $.ajax({
        url: '/admin/productToLocationEdit/reload/' + success,
        type: 'GET',
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        }
    });
}

function sendChangeToBackend(array) {
    var success = true;
    for (var i = 0; i < array.length; i++) {
        var getUrl = '/admin/productToLocationEdit/change/'
            + array[i].productId
            + '/' + array[i].editLocationName
            + '/' + array[i].editAmount;
        $.ajax({
            url: getUrl,
            type: 'GET',
            fail: function (data, textStatus, xhr) {
                success = false;
            }
        });
    }
    return success;
}

function loadChanges() {
    var arrayIndex = 0;
    var array = [];
    var table = $('table#productLocationEditTable').DataTable();
    table.rows({search: 'applied'}).every(function (rowIdx, tableLoop, rowLoop) {
        var row = table.row(this.node()).data();
        var articleId = row.article.id;
        var productId = row.id;
        var articleName = row.article.name;
        var locationName = row.location.name;

        var editLocationNameSelect = $('#locationProductEditLocationSelectInTable' + rowIdx);
        var changeAmountSelect = $('#locationProductEditAmountSelectInTable' + rowIdx);
        var editLocTd = $(editLocationNameSelect).closest('td');
        var amountTd = $(changeAmountSelect).closest('td');
        try {
            var bo_editLoc = $(editLocTd).attr('style').indexOf('background') != null;
            var bo_editAmount = $(amountTd).attr('style').indexOf('background') != null;
            if (bo_editLoc && bo_editAmount) {

                array[arrayIndex] = {
                    productId: productId,
                    articleId: articleId,
                    articleName: articleName,
                    locationName: locationName,
                    editLocationName: $(editLocationNameSelect).find('option:selected').text(),
                    editAmount: $(changeAmountSelect).val()
                };
                arrayIndex++;
            }
        } catch (error) {
            console.log("Error: one row has no background on \"Location dropdown\" and \"To change amount Field\"")
        }
    });
    return array;
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