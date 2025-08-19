angular.module('Stock').controller('ItemsManagement', function($scope, $http, $timeout){
    $scope.categories=[];
    $scope.flatCategories=[];
    $scope.brands=[];
    $scope.suppliers=[];
    $scope.stores=[];

    $scope.categoryName='';

    $scope.brandDTO={
        categoryId:0,
        brandName:'',
        productDescription:'',
        itemCode:'',
        customFields:[],
    }
    $scope.itemDTO={
        categoryId:0,
        brandId:0,
        supplierId:0,
        quantity:0,
        itemCode:'',
        warranty:'',
        sellerWarranty:'',
        itemCost:0.00,
        dealerPrice:0.00,
        retailPrice: 0.00,
        paymentStatus: '',
        supplierPayment: '',
        hasSerialNumbers:true,
        supplierInvoiceNumber:'',
        grnDate:null,
        store:null,
        stockType:"Goods",
        serialNumbers:[],
        customFields:[]
    }
    $scope.formData = {
        serialNumber: ''
    };
    $scope.selectedCategory = null;
    $scope.selectedBrand = null;
    $scope.selectedItemInfo=null;
    $scope.selectedSupplier=null;

    $scope.itemCustom={
        stockNumber:{
            enabled: false,
            value:''
        },
        additional:{
            enabled:false,
            value:''
        }
    }

    $scope.stockCustom={
        additional:{
            enabled: false,
            value:''
        },
        grnDate:{
            enabled: false,
        }
    }

    $scope.toggleTaxId = function() {
        if (!$scope.supplier.customFields.taxId.enabled) {
            $scope.supplier.customFields.taxId.value = '';
        }
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
    $scope.selectItemInfo= function (itemInfo){
        $scope.itemDTO.itemCode = itemInfo.itemCode;
    }
    $scope.selectItemSupplier= function (supplier){
        $scope.itemDTO.supplierId= supplier.supplierId;
    }

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


    $scope.addCategory=function (){
        $http.post(APP_CONFIG.apiBase + `/mgt/createCategory`,$scope.categoryName)
            .then(function (response) {
                getCategories();
                clearFields();
                toastr.success('Category Added!', 'Success');
            }, function (error) {
                toster.warning("Error Fetching Data");
            });
    }

    $scope.addBrand=function (){
        var dto = $scope.brandDTO;
        dto.brandName = (dto.brandName || '').trim();
        dto.itemCode = (dto.itemCode || '').trim();

        if (!dto.brandName) return toastr.error('Brand name is required.', 'Validation Error');
        if (!dto.itemCode) return toastr.error('Item code is required.', 'Validation Error');
        if (dto.categoryId <= 0) return toastr.error('Please select a valid category.', 'Validation Error');

        if (dto.productDescription.trim().length > 500) {
            return toastr.error('Product description cannot exceed 500 characters.', 'Validation Error');
        }

        if ($scope.itemCustom.stockNumber.enabled && $scope.itemCustom.stockNumber.value !=null){
            $scope.brandDTO.customFields.push({
                fieldName: "Stock Number",
                fieldValue: $scope.itemCustom.stockNumber.value,
                fieldType: "TEXT",
                enabled: true,
                required: false
            })
        }
        if ($scope.itemCustom.additional.enabled && $scope.itemCustom.additional.value !=null){
            $scope.brandDTO.customFields.push({
                fieldName: "Additional Details",
                fieldValue: $scope.itemCustom.additional.value,
                fieldType: "TEXT",
                enabled: true,
                required: false
            })
        }
        $http.post(APP_CONFIG.apiBase + `/mgt/createBrand`,$scope.brandDTO)
            .then(function (response) {
                getCategories();
                clearFields();
                toastr.success('Item Added!', 'Success');
            }, function (error) {
                toster.warning("Error Fetching Data");
            });
    }

    $scope.addStock= function (){
        if (!validateSerialCount()) {
            toster.warning("Invalid Serial Number Count!");
            return;
        }

        if($scope.stockCustom.additional.enabled && $scope.stockCustom.additional.value != null){
            $scope.itemDTO.customFields.push({
                fieldName: "Additional Info",
                fieldValue: $scope.stockCustom.additional.value,
                fieldType: "TEXT",
                enabled: true,
                required: false
            })
        }

        $http.post(APP_CONFIG.apiBase + `/mgt/createItem`,$scope.itemDTO)
            .then(function (response){
                toastr.success('Stock Added!', 'Success');
                getCategories();
                clearFields();
            }, function (error){
                toster.warning("Error Saving Data");
            });
    }

    function validateSerialCount() {
        const serialCount = $scope.itemDTO.serialNumbers ? $scope.itemDTO.serialNumbers.length : 0;

        if ($scope.itemDTO.hasSerialNumbers === true && serialCount !== $scope.itemDTO.quantity) {
            toastr.error('The number of serial numbers does not match the specified quantity.');
            return false;
        }

        return true;
    }

    function getFlatCategories() {
        $http.get(APP_CONFIG.apiBase + '/mgt/flatCat')
            .then(function (res) {
                $scope.flatCategories = res.data;
                drawItemsTable();
            }, function (err) {
                toster.warning("Error Fetching Data");
            });
    }
    function getCategories(){
        $http.get(APP_CONFIG.apiBase + '/mgt/cat')
            .then(function (res) {
                $scope.categories = res.data;
                getFlatCategories();
            }, function (err) {
                toster.warning("Error Fetching Data");
            });
    }

    function clearFields(){
        $scope.categoryName='';

        $scope.brandDTO={
            categoryId:0,
            brandName:'',
            productDescription:'',
            itemCode:'',
            customFields:[],
        }
        $scope.itemDTO={
            categoryId:0,
            brandId:0,
            supplierId:0,
            quantity:0,
            itemCode:'',
            warranty:'',
            sellerWarranty:'',
            itemCost:0.00,
            dealerPrice:0.00,
            retailPrice: 0.00,
            paymentStatus: '',
            supplierPayment: '',
            hasSerialNumbers:true,
            grnDate:null,

            customFields:[],
            serialNumbers:[]
        }

        $scope.itemCustom.stockNumber.value='';
        $scope.itemCustom.additional.value='';

        $scope.stockCustom.additional.value='';

        $scope.selectedCategory = null;
        $scope.selectedBrand = null;
        $scope.selectedItemInfo=null;
        $scope.selectedSupplier=null;
    }

    const itemsTable = $('#itemsTable').DataTable({
        paging: true,
        searching: true,
        ordering: true,
        destroy: true,
    });

    function drawItemsTable(){
        const data = $scope.flatCategories;
        if(Array.isArray(data)){
            itemsTable.clear();
            data.forEach(function (inv){
                itemsTable.row.add([
                    inv.categoryName || '',
                    inv.brandName || '',
                    inv.itemCode || '',
                    inv.itemDescription || '',
                    inv.quantity || '',
                    inv.date || ''
                ])
            });
            itemsTable.draw();
        }
    }

    function getSuppliers(){
        $http.get(APP_CONFIG.apiBase + '/supplier')
            .then(function (res) {
                $scope.suppliers = res.data;
                drawSupplierTable();
            }, function (err) {
                toster.warning("Error Fetching Supplier Data");
            });
    }
    function getStores(){
        $http.get(APP_CONFIG.apiBase + '/mgt/stores')
            .then(function (res){
                $scope.stores = res.data;
                console.log($scope.stores);
            },function (err){
                toster.warning("Error Fetching Store Data");
            })
    }

    getCategories();
    getSuppliers();
    getStores();




    $scope.supplier = {
        name: '',
        contactNumber: '',
        contactName: '',
        address: '',
        period: '',
        paymentTerms: '',
        customFields:[]
    };

    $scope.supplierCustom={
        taxId: {
            enabled: false,
            value: ''
        },
        bankDetails:{
            enabled: false,
            value:''
        }
    }
    $scope.saveSupplier = function() {
        if ($scope.supplierCustom.taxId.enabled && $scope.supplierCustom.taxId.value !=null){
            $scope.supplier.customFields.push({
                fieldName: "Vat Number",
                fieldValue: $scope.supplierCustom.taxId.value,
                fieldType: "TEXT",
                enabled: true,
                required: false
            })
        }
        if ($scope.supplierCustom.bankDetails.enabled && $scope.supplierCustom.bankDetails.value !=null){
            $scope.supplier.customFields.push({
                fieldName: "Bank Details",
                fieldValue: $scope.supplierCustom.bankDetails.value,
                fieldType: "TEXT",
                enabled: true,
                required: false
            })
        }

        $http.post(APP_CONFIG.apiBase +'/supplier', $scope.supplier)
            .then(function (response) {
                toastr.success('Supplier Added!', 'Success');
                resetForm();
                getSuppliers();
                drawSupplierTable();
            })
            .catch(function (error) {
                toastr.error('Something Went Wrong!', 'Error');
            });
    };
    function resetForm() {
        $scope.supplier = {
            name: '',
            contactNumber: '',
            contactName: '',
            address: '',
            period: '',
            paymentTerms: '',
            customFields:[]
        };
        $scope.taxId={
            enabled: false,
            value: ''
        }
    }

    const supplierTable = $('#supplierTable').DataTable({
        paging: true,
        searching: true,
        ordering: true,
        destroy: true,
    });
    function drawSupplierTable(){
        const data = $scope.suppliers;
        if (!Array.isArray(data)) return;

        const hasVatNumber = data.some(inv => inv.vatNumber !== null && inv.vatNumber !== undefined && inv.vatNumber.trim() !== '');
        const hasBankDetails = data.some(inv => inv.bankDetails !== null && inv.bankDetails !== undefined && inv.bankDetails.trim() !== '');

        if (Array.isArray(data)) {
            supplierTable.clear();
            data.forEach(function(inv) {
                supplierTable.row.add([
                    inv.name || '',
                    inv.address || '',
                    inv.contactName || '',
                    inv.contactNumber || '',
                    inv.paymentTerms || '',
                    inv.period || '',
                    inv.vatNumber || 'N/A',
                    inv.bankDetails || 'N/A'
                ]);
            });
            supplierTable.draw();
            supplierTable.column(6).visible(hasVatNumber);
            supplierTable.column(7).visible(hasBankDetails);
        }
    }
});