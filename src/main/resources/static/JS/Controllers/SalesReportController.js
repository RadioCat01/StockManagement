angular.module('Stock').controller('SalesReport', function ($scope, $http, $timeout) {

    const inventoryTable = $('#inventoryTable').DataTable({
        paging: true,
        searching: true,
        ordering: true,
        destroy: true,

        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'excel',
                text: '<i class="la la-file-excel-o"></i>',
                titleAttr: 'Export to Excel'
            },
            {
                extend: 'print',
                text: '<i class="la la-print"></i>',
                titleAttr: 'Print'
            }
        ]
    });

    $('#inventoryTable tfoot th').each(function () {
        var title = $(this).text();
        $(this).html('<input type="text" placeholder="Search ' + title + '" />');
    });

    $('.buttons-print, .buttons-excel').addClass('btn-light mr-1');

    inventoryTable.columns().every(function () {
        var column = this;

        $('input', column.footer()).on('keyup change', function () {
            if (column.search() !== this.value) {
                column
                    .search(this.value)
                    .draw();
            }
        });
    });




    $http.get( APP_CONFIG.apiBase + `/sales/salesReport`)
        .then(function (res){
            inventoryTable.clear();
            const data =res.data;
            if(Array.isArray(data)){
                data.forEach(function (r){
                    inventoryTable.row.add([
                        `<strong>${r.itemCode}</strong><br><small>${r.brand}</small><br><small>${r.description}</small>`,
                        r.serialNumbers,
                        r.invoiceNumber,
                        r.poReference,
                        `<strong>${r.customerName}</strong><br><small>${r.customerPhone}<br> ${r.customerAddress}</small>`,
                        new Date(r.soldDate).toLocaleString(),
                        `${r.storeName}<br>${r.storeAddress}`
                    ])
                });
                inventoryTable.draw();
            }
        },function (err){
            toastr.warning('Something Went Wrong!', 'Error');
        });

});