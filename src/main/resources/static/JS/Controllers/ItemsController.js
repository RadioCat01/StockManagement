angular.module('Stock').controller('ItemsMgt', function ($scope, $http, $timeout) {
    $scope.categories = [];
    $scope.flatCategories = [];
    $scope.suppliers = [];
    $scope.stores = [];
    $scope.genericFields = [];
    $scope.inputData = {};

    $scope.selectedCategory = null;
    $scope.selectedBrand = null;
    $scope.selectedItemInfo = null;
    $scope.selectedSupplier = null;

    $scope.itemDTO = {
        categoryId: 0,
        brandId: 0,
        supplierId: 0,
        quantity: 0,
        itemCode: '',
        warranty: '',
        sellerWarranty: '',
        itemCost: 0.00,
        dealerPrice: 0.00,
        retailPrice: 0.00,
        paymentStatus: '',
        supplierPayment: '',
        hasSerialNumbers: true,
        supplierInvoiceNumber: '',
        grnDate: null,
        store: null,
        stockType: "Goods",
        serialNumbers: [],
        customFields: []
    };

    $scope.formData = { serialNumber: '' };

    $scope.stockCustom = {
        additional: { enabled: false, value: '' },
        grnDate: { enabled: false }
    };

    $scope.selectCategory = function (category) {
        $scope.selectedCategory = category;
        $scope.selectedBrand = null;
        $scope.itemDTO.categoryId = category.categoryId;
    };

    $scope.selectBrand = function (brand) {
        $scope.selectedBrand = brand;
        $scope.itemDTO.brandId = brand.brandId;
    };

    $scope.selectItemInfo = function (itemInfo) {
        $scope.itemDTO.itemCode = itemInfo.itemCode;
    };

    $scope.selectItemSupplier = function (supplier) {
        $scope.itemDTO.supplierId = supplier.supplierId;
    };

    $scope.addItems = function () {
        var sn = $scope.formData.serialNumber.trim();
        if (sn.length > 0) {
            $scope.itemDTO.serialNumbers.push(sn);
            $scope.formData.serialNumber = '';
        }
    };

    $scope.removeItem = function (item) {
        const index = $scope.itemDTO.serialNumbers.indexOf(item);
        if (index !== -1) {
            $scope.itemDTO.serialNumbers.splice(index, 1);
        }
    };

    $scope.addStock = function () {
        if (!validateSerialCount()) {
            toastr.warning("Invalid Serial Number Count!");
            return;
        }
        if ($scope.stockCustom.additional.enabled && $scope.stockCustom.additional.value != null) {
            $scope.itemDTO.customFields.push({
                fieldName: "Additional Info",
                fieldValue: $scope.stockCustom.additional.value,
                fieldType: "TEXT",
                enabled: true,
                required: false
            });
        }
        $http.post(APP_CONFIG.apiBase + '/mgt/createItem', $scope.itemDTO)
            .then(function () {
                toastr.success('Stock Added!', 'Success');
                getCategories();
                clearFields();
                $('#addStockModal').modal('hide');
            }, function () {
                toastr.error("Error Saving Data", "Error!");
            });
    };

    function validateSerialCount() {
        const serialCount = $scope.itemDTO.serialNumbers ? $scope.itemDTO.serialNumbers.length : 0;
        if ($scope.itemDTO.hasSerialNumbers === true && serialCount !== $scope.itemDTO.quantity) {
            toastr.error('The number of serial numbers does not match the specified quantity.');
            return false;
        }
        return true;
    }

    const itemsTable = $('#itemsTable').DataTable({
        paging: true,
        searching: true,
        ordering: true,
        destroy: true
    });

    function drawItemsTable() {
        const data = $scope.flatCategories;
        if (Array.isArray(data)) {
            itemsTable.clear();
            data.forEach(function (inv) {
                itemsTable.row.add([
                    inv.categoryName || '',
                    inv.brandName || '',
                    inv.itemCode || '',
                    inv.itemDescription || '',
                    inv.quantity || '',
                    inv.date || ''
                ]);
            });
            itemsTable.draw();
        }
    }

    function getFlatCategories() {
        $http.get(APP_CONFIG.apiBase + '/mgt/flatCat')
            .then(function (res) {
                $scope.flatCategories = res.data;
                drawItemsTable();
            }, function () {
                toastr.warning("Error Fetching Data");
            });
    }

    function getCategories() {
        $http.get(APP_CONFIG.apiBase + '/mgt/cat')
            .then(function (res) {
                $scope.categories = res.data;
                getFlatCategories();
            }, function () {
                toastr.warning("Error Fetching Data");
            });
    }

    function getSuppliers() {
        $http.get(APP_CONFIG.apiBase + '/supplier')
            .then(function (res) {
                $scope.suppliers = res.data;
            }, function () {
                toastr.warning("Error Fetching Supplier Data");
            });
    }

    function getStores() {
        $http.get(APP_CONFIG.apiBase + '/mgt/stores')
            .then(function (res) {
                $scope.stores = res.data;
            }, function () {
                toastr.warning("Error Fetching Store Data");
            });
    }

    function clearFields() {
        $scope.itemDTO = {
            categoryId: 0,
            brandId: 0,
            supplierId: 0,
            quantity: 0,
            itemCode: '',
            warranty: '',
            sellerWarranty: '',
            itemCost: 0.00,
            dealerPrice: 0.00,
            retailPrice: 0.00,
            paymentStatus: '',
            supplierPayment: '',
            hasSerialNumbers: true,
            grnDate: null,
            customFields: [],
            serialNumbers: []
        };
        $scope.stockCustom.additional.value = '';
        $scope.selectedCategory = null;
        $scope.selectedBrand = null;
        $scope.selectedItemInfo = null;
        $scope.selectedSupplier = null;
        $scope.formData = { serialNumber: '' };
    }

    getCategories();
    getSuppliers();
    getStores();
});
