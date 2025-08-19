angular.module('Stock').controller('GRNController', function ($scope, $http,$timeout) {
    $scope.suppliers = [];
    $scope.selectedSupplier = null;
    $scope.selectedStore=null;
    $scope.supplierGRNs=[];
    $scope.stores=[];

    $http.get(APP_CONFIG.apiBase + '/supplier')
        .then(function (response) {
            $scope.suppliers = response.data;
        }, function (error) {
            toastr.warning('Something Went Wrong!', 'Error');
        });


    const grnTable = $('#grnTable').DataTable({
        paging: true,
        searching: true,
        ordering: true,
        destroy: true,
        scrollX: true,
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
    $('.buttons-print, .buttons-excel').addClass('btn btn-primary mr-1');



    $scope.getSupplierGRN=function (){
        if(!$scope.selectedSupplier){
            toastr.warning('Please select a supplier first', 'Error');

        }
        $http.get(APP_CONFIG.apiBase + '/supplierGRNS/getSGRNs', {
            params: {
                supplierId: $scope.selectedSupplier
            }
        }).then(function success(response) {
            $scope.supplierGRNs = response.data;
            grnTable.clear();
            toastr.success('GRN data loaded successfully!', 'Success');
            const data = response.data;
            if (Array.isArray(data)) {
                data.forEach(function (grn) {
                    grnTable.row.add([
                        `${grn.categoryName || ''}<br>${grn.brandName || ''}`,
                        grn.productDescription || '',
                        grn.grnDate || '',
                        grn.supplierInvoice || '',
                        grn.warranty || '',
                        grn.quantity || '',
                        grn.serialNumbers || '',
                        grn.cost || '',
                        grn.dealerPrice || '',
                        grn.retailPrice || '',
                        `${grn.supplierPayment || ''}<br>${grn.paymentStatus || ''}`
                    ]);
                });
            }
            grnTable.draw();
        }, function error(err) {
            toster.warning("Error Fetching Data");
        });
    }

    $scope.onStoreSelect = function() {
        if (!$scope.selectedStore) {
            grnTable.clear().draw();
            $scope.inventoryDTOs = [];
            return;
        }

        $http.get(APP_CONFIG.apiBase + '/supplierGRNS/getSGRNs', {
            params: {
                supplierId: $scope.selectedSupplier
            }
        }).then(function (res) {
                const allInventory = res.data;
                if (Array.isArray(allInventory)) {
                    $scope.supplierGRNs = allInventory.filter(inv => inv.storeId === $scope.selectedStore);
                    grnTable.clear();
                    $scope.supplierGRNs.forEach(function (grn) {
                        grnTable.row.add([
                            `${grn.categoryName || ''}<br>${grn.brandName || ''}`,
                            grn.productDescription || '',
                            grn.grnDate || '',
                            grn.supplierInvoice || '',
                            grn.warranty || '',
                            grn.quantity || '',
                            grn.serialNumbers || '',
                            grn.cost || '',
                            grn.dealerPrice || '',
                            grn.retailPrice || '',
                            `${grn.supplierPayment || ''}<br>${grn.paymentStatus || ''}`
                        ]);
                    });
                    grnTable.draw();
                }
            }, function (err) {
                toastr.warning('Something Went Wrong!', 'Error');
            });
    };

    function getStores(){
        $http.get(APP_CONFIG.apiBase + '/mgt/stores')
            .then(function (res){
                $scope.stores = res.data;
            },function (err){
                toster.warning("Error Fetching Store Data");
            })
    }

    getStores();
})


