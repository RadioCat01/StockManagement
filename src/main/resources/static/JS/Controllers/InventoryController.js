angular.module('Stock').controller('InventoryCont', function ($scope, $http, $timeout) {
    $scope.stores=[];
    $scope.selectedStore=null;
    $scope.inventoryDTOs=[];
    $scope.selectedItems = [];
    $scope.transfers=[];
    $scope.toTransfer={
        items:[],
        transferTo:null,
        reason:null
    }
    $scope.itemHistory=[];
    $scope.searchSerialNumber=null;

    const inventoryTable = $('#inventoryTable').DataTable({
        paging: true,
        searching: true,
        ordering: true,
        destroy: true,
    });

    $('#inventoryTable tfoot th').each(function () {
        var title = $(this).text();
        $(this).html('<input type="text" placeholder="Search ' + title + '" />');
    });
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
    $('#inventoryTable tbody').on('click', 'tr', function () {
        const $row = $(this);
        const rowIndex = inventoryTable.row(this).index();

        if (rowIndex === undefined || rowIndex === null) {
            return;
        }
        const selectedItem = $scope.inventoryDTOs[rowIndex];
        if (!selectedItem) {
            return;
        }

        if ($row.hasClass('selected')) {
            $row.removeClass('selected');

            $scope.selectedItems = $scope.selectedItems.filter(item => item !== selectedItem);
        } else {
            $row.addClass('selected');

            if (!$scope.selectedItems.includes(selectedItem)) {
                $scope.selectedItems.push(selectedItem);
            }
        }
        $scope.$apply();
    });


    $scope.onStoreSelect = function() {
        if (!$scope.selectedStore) {
            inventoryTable.clear().draw();
            $scope.inventoryDTOs = [];
            return;
        }
        fetchAndRenderInventory($scope.selectedStore);
    };


    const transferTable = $('#transfersTable').DataTable({
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
        ],
        initComplete: function () {
            this.api().columns().every(function () {
                var column = this;
                var title = $(column.footer()).text();

                $(column.footer()).html('<input type="text" placeholder="' + title + '"/>');

                $('input', column.footer()).on('keyup change clear', function () {
                    if (column.search() !== this.value) {
                        column.search(this.value).draw();
                    }
                });
            });
        }
    });

    const historyTable = $('#historyTable').DataTable({
        paging: true,
        searching: true,
        ordering: true,
        destroy: true,
        dom: 'Bfrtip', columnDefs: [
            { width: '15%', targets: 0 },
            { width: '20%', targets: 1 },
            { width: '20%', targets: 2 },
            { width: '25%', targets: 3 },
            { width: '20%', targets: 4 }
        ],
        autoWidth: false,

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
        ],
        initComplete: function () {
            this.api().columns().every(function () {
                var column = this;
                var title = $(column.footer()).text();

                $(column.footer()).html('<input type="text" placeholder="' + title + '"/>');

                $('input', column.footer()).on('keyup change clear', function () {
                    if (column.search() !== this.value) {
                        column.search(this.value).draw();
                    }
                });
            });
        }
    });
    $('.buttons-print, .buttons-excel').addClass('btn btn-primary mr-1');


    $scope.transfer=function (){
        if($scope.selectedItems.length > 0 && $scope.toTransfer.transferTo != null && $scope.toTransfer.reason != null){
            $scope.selectedItems.forEach(function (item){
                $scope.toTransfer.items.push(item);
            })
            $http.post(APP_CONFIG.apiBase + `/inventoryCont/transfer`, $scope.toTransfer)
                .then(function (res){
                    fetchAndRenderInventory($scope.selectedStore);
                    clearFields();
                    getTransfers();
                    toastr.success('Stock Moved!', 'Success');
                },function (err){
                    toastr.warning('Stock Moving Failed!', 'Error');
                })
        }else {
            toastr.warning('Fill all the fields!', 'Error');
        }
    }

    $scope.clearSelected=function (){
        $scope.selectedItems = [];
    }

    $scope.getItemHistory=function (serial){
        $http.get(APP_CONFIG.apiBase + `/inventoryCont/itemHistory`,{
            params:{serial:serial}
        })
            .then(function (res){
                $scope.itemHistory=res.data;
                toastr.success('Item History Fetched!', 'Success');
                drawHistoryTable();
            },function (err){
                toastr.warning('Error Fetching ItemHistory!', 'Error');
            })
    }

    function fetchAndRenderInventory(filterStoreId) {
        $http.get(APP_CONFIG.apiBase + `/inventoryCont/inv`)
            .then(function (res) {
                const allInventory = res.data;
                if (!Array.isArray(allInventory)) {
                    return;
                }

                $scope.inventoryDTOs = filterStoreId
                    ? allInventory.filter(inv => inv.storeId === filterStoreId)
                    : allInventory;

                inventoryTable.clear();
                $scope.inventoryDTOs.forEach(function (inv) {
                    inventoryTable.row.add([
                        `${inv.category || ''}<br>${inv.brand || ''}`,
                        inv.itemCode || '',
                        inv.description || '',
                        inv.productSerial || '',
                        inv.grnDate || '',
                        inv.lastUpdate || '',
                        inv.stockType || '',
                    ]);
                });
                inventoryTable.draw();
            }, function (err) {
                toastr.warning('Something Went Wrong!', 'Error');
            });
    }
    function getTransfers(){
        $http.get(APP_CONFIG.apiBase + '/inventoryCont/trf')
            .then(function (res){
                $scope.transfers = res.data;
                drawTransferTable();
            },function (err){
                toster.warning("Error Fetching Store Data");
            })
    }
    function getStores(){
        $http.get(APP_CONFIG.apiBase + '/mgt/stores')
            .then(function (res){
                $scope.stores = res.data;
            },function (err){
                toster.warning("Error Fetching Store Data");
            })
    }
    function clearFields(){
        $scope.toTransfer={
            items:[],
            transferTo:null,
            reason:null
        }
        $scope.selectedItems = [];
    }
    function drawTransferTable(){
        transferTable.clear();
        if(Array.isArray($scope.transfers)){
            $scope.transfers.forEach(function (dt){
                transferTable.row.add([
                    dt.transferNumber ||'',
                    dt.transferDate || '',
                    dt.serials || '',
                    dt.transferFrom || '',
                    dt.transferTo || '',
                    dt.reason || ''
                ]);
            });
        }
        transferTable.draw();
    }

    function drawHistoryTable(){
        historyTable.clear();
        if(Array.isArray($scope.itemHistory)){
            $scope.itemHistory.forEach(function (ht){
                historyTable.row.add([
                    ht.brand || '',
                    ht.itemCode || '',
                    ht.serialNo || '',
                    ht.currentState || '',
                    ht.lastUpdate || ''
                ]);
            });
        }
        historyTable.draw();
    }

    getStores();
    fetchAndRenderInventory();
    getTransfers();
});