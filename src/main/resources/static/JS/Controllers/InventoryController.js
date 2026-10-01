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
    $scope.categories=[];
    $scope.brands=[];
    $scope.itemInfos=[];

    $scope.selectedCategory = null;
    $scope.selectedBrand = null;
    $scope.selectedItemInfo=null;
    $scope.itemQuantity=0;
    $scope.fromSelectedStore=null;

    $scope.selectCategory = function (category) {
        $scope.selectedCategory = category;
        $scope.selectedBrand = null;
    };
    $scope.selectBrand = function (brand) {
        $scope.selectedBrand = brand;
        $scope.bulkTransferDTO.brand=brand.brandName;
    };
    $scope.selectItemInfo= function (itemInfo){
       $scope.itemQuantity = itemInfo.items.filter(item =>
            item.store === $scope.fromSelectedStore
        ).length;
       $scope.bulkTransferDTO.itemCode=$scope.selectedItemInfo.itemCode;
    }
    $scope.selectBulkTransferTo= function (){
        $scope.itemQuantity = $scope.selectedItemInfo.items.filter(item =>
            item.store.storeId === $scope.fromSelectedStore
        ).length;
    }
    $scope.bulkTransferDTO={
        storeId:0,
        brand:'',
        itemCode:'',
        itemQuantity:0,
        reason:null
    };

    const inventoryTable = $('#inventoryTable').DataTable({
        processing: true,
        serverSide: true,
        searchDelay: 350,
        paging: true,
        searching: true,
        ordering: true,
        ajax: {
            url: APP_CONFIG.apiBase + '/inventoryCont/inv',
            data: function (request) {
                if ($scope.selectedStore) {
                    request.storeId = $scope.selectedStore;
                }
            },
            dataSrc: function (response) {
                $scope.inventoryDTOs = response.data;
                $scope.$evalAsync();
                return response.data;
            }
        },
        columns: [
            {
                data: null,
                render: function (data, type, item) {
                    return `${item.category || ''}<br>${item.brand || ''}`;
                }
            },
            { data: 'itemCode', defaultContent: '' },
            { data: 'description', defaultContent: '' },
            { data: 'productSerial', defaultContent: '' },
            { data: 'grnDate', defaultContent: '' },
            { data: 'lastUpdate', defaultContent: '' },
            { data: 'stockType', defaultContent: '' }
        ]
    });

    $('#inventoryTable tbody').on('click', 'tr', function () {
        const $row = $(this);
        const selectedItem = inventoryTable.row(this).data();
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
        $scope.selectedItems = [];
        inventoryTable.ajax.reload();
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
        scrollY: '600px',
        scrollCollapse: true,
        scroller: false,
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
                    inventoryTable.ajax.reload(null, false);
                    clearFields();
                    getCategories();
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

    $scope.transferBulk = function (){
        if($scope.itemQuantity >= $scope.bulkTransferDTO.itemQuantity && $scope.bulkTransferDTO.itemQuantity>0
            && $scope.bulkTransferDTO.reason !=null){
            $http.post(APP_CONFIG.apiBase + `/inventoryCont/transferBulk`, $scope.bulkTransferDTO)
                .then(function (res){
                    inventoryTable.ajax.reload(null, false);
                    clearBulkTransferFields();
                    getTransfers();
                    getCategories();
                    toastr.success('Stock Moved!', 'Success');
                },function (err){
                    toastr.warning('Stock Moving Failed!', 'Error');
                })
        }
        else {
            toastr.warning('Enter Valid Inputs!', 'Error');
        }
    }

    function clearBulkTransferFields(){
        $scope.bulkTransferDTO={
            storeId:0,
            category:'',
            brand:'',
            itemInfo:'',
            itemQuantity:0,
        };
        $scope.selectedCategory = null;
        $scope.selectedBrand = null;
        $scope.selectedItemInfo=null;
        $scope.itemQuantity=0;
        $scope.fromSelectedStore=null;
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

    function getCategories(){
        $http.get(APP_CONFIG.apiBase + '/mgt/cat')
            .then(function (res) {
                $scope.categories = res.data;
            }, function (err) {
                toster.warning("Error Fetching Data");
            });
    }

    getCategories();
    getStores();
    getTransfers();
});