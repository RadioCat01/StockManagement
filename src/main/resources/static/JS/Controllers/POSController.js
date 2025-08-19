angular.module('Stock').controller('POSController', function($scope, $http, $timeout) {
    $scope.productDTOs=[];

    $scope.inputType = "qty";
    $scope.tax=4;
    $scope.subtotal=0.00;
    $scope.calculatedTax=0;
    $scope.total=0.00;
    $scope.barcodeReady=false;
    $scope.isSearchActive = false;
    $scope.searchQuery = '';
    $scope.filteredProducts=[];

    $scope.soldDTO={
        retail:true,
        products:[],
        customerName:'',
        customerPhone:'',
        customerAddress:'',
        paymentTerms:'Cash',
        poReference:'',
        serviceCharges:[],
        customFields:[],
    };
    $scope.serviceChargeType=['Repair','Cleaning','Replacements']
    $scope.paymentOptions = ['Cash', 'Card', 'Bank Transfer', 'Online Payment','Pending'];

    $scope.customers = [];
    $scope.customerSearchQuery = '';
    $scope.filteredCustomersList = [];
    $scope.selectedCustomer =null;

    $scope.posCustom={
        poNumber:{
            enabled: false,
            value: ''
        },
        additionalDetails:{
            enable: false,
            value:''
        }
    }

    function getProductDTOs() {
        $http.get(APP_CONFIG.apiBase + `/sales/getSaleItems`)
            .then(function (res){
                $scope.productDTOs= res.data;
                console.log($scope.productDTOs);
            },function (err){
                console.log(err)
            })
    }
    function getCustomers(){
        $http.get(APP_CONFIG.apiBase + `/sales/getCustomers`)
            .then(function (res){
                $scope.customers= res.data;
            },function (err){
                console.log(err)
            })
    }

    $scope.selectProduct = function (product) {
        let existingProduct = $scope.soldDTO.products.find(p => p.itemCode === product.itemCode);

        if (!existingProduct) {
            $scope.soldDTO.products.push({
                itemCode:product.itemCode,
                selectedQuantity: 1,
                supplierGRNID:product.supplierGRNID,
                description:product.description,
                retailPrice:product.retailPrice,
                dealerPrice:product.dealerPrice,
                availableQuantity:product.quantity,
                storeId:product.storeId
            });
            calculateSubTotal();
        }
    };
    $scope.addServiceCharge = function () {
        if ($scope.serviceCharge.serviceChargeType && $scope.serviceCharge.chargeAmount > 0) {
            $scope.soldDTO.serviceCharges.push({
                description: $scope.serviceCharge.serviceChargeType,
                chargeAmount: $scope.serviceCharge.chargeAmount
            });
            $scope.serviceCharge = {
                description: '',
                chargeAmount: 0.00
            };
            $timeout(function () {
                $('#serviceModal').modal('hide');
                $('.modal-backdrop').remove();
                $scope.categoryName='';
            }, 100);
            calculateSubTotal();
        }
    };

    $scope.handleNumberInput = function (number) {
        if ($scope.inputType === 'qty') {
            if ($scope.soldDTO.products.length > 0) {
                let lastProduct = $scope.soldDTO.products[$scope.soldDTO.products.length - 1];
                lastProduct.selectedQuantity *= number;
                calculateSubTotal();

                if (lastProduct.selectedQuantity > lastProduct.availableQuantity) {
                    toastr.warning('Quantity exceeds available stock!', 'Warning');
                    lastProduct.selectedQuantity= lastProduct.availableQuantity;
                }
            } else {
                toastr.warning('No products selected to modify quantity!', 'Warning');
            }
        }
    };

    $scope.search = function() {
        const query = $scope.searchQuery ? $scope.searchQuery.trim().toLowerCase() : '';

        if (!query) {
            $scope.isSearchActive = false;
            $scope.filteredProducts = [];
            return;
        }

        $scope.filteredProducts = $scope.productDTOs.filter(product => {
            const serialMatch = product.itemCode.toLowerCase().includes(query);
            const descriptionMatch = product.description.toLowerCase().includes(query) ||
                product.itemCode.toLowerCase().includes(query);

            return serialMatch || descriptionMatch;
        });

        $scope.isSearchActive = $scope.filteredProducts.length > 0;
    }

    $scope.resetSearch = function () {
        $scope.searchQuery = '';
        $scope.isSearchActive = false;
        $scope.filteredProducts = [];
    };

    $scope.setBarcodeReady= function (){
        $scope.barcodeReady=!$scope.barcodeReady;
    }

    $scope.setInputType = function (type) {
        if(type===$scope.inputType){
            $scope.inputType=null;
        }else {
            $scope.inputType = type;
        }
    };



    $scope.updateFilteredCustomers = function(directQuery) {
        const query = (directQuery || '').toLowerCase().trim();

        if (!query) {
            $scope.filteredCustomersList = [];
            return;
        }

        $scope.filteredCustomersList = $scope.customers.filter(function(cust) {
            const nameMatch = cust.name && cust.name.toLowerCase().includes(query);
            const phoneMatch = cust.phone && cust.phone.includes(query);
            const result = nameMatch || phoneMatch;

            return result;
        });

        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };

    $scope.selectCustomer = function(customer) {
        $scope.soldDTO.customerName = customer.name;
        $scope.soldDTO.customerPhone = customer.phone;
        $scope.soldDTO.customerAddress = customer.address;
    };

    $scope.clearCustomer = function() {
        $scope.soldDTO.customerName = null;
        $scope.soldDTO.customerPhone = null;
        $scope.soldDTO.customerAddress = null;
        $scope.customerSearchQuery = '';
    };

    $scope.createSale=function (){
        if(!validateInputs()) return;

        if ($scope.posCustom.poNumber.enabled && $scope.posCustom.poNumber.value !=null){
            $scope.soldDTO.customFields.push({
                fieldName: "PO Number",
                fieldValue: $scope.posCustom.poNumber.value,
                fieldType: "TEXT",
                enabled: true,
                required: false
            })
        }
        if ($scope.posCustom.additionalDetails.enabled && $scope.posCustom.additionalDetails.value !=null){
            $scope.soldDTO.customFields.push({
                fieldName: "Additional Details",
                fieldValue: $scope.posCustom.additionalDetails.value,
                fieldType: "TEXT",
                enabled: true,
                required: false
            })
        }

        $http.post(APP_CONFIG.apiBase + `/sales/createSale`, $scope.soldDTO)
            .then(function (res){
                getProductDTOs();
                toastr.success('Operation Completed', 'Success');
                clearAll();
                const Id = res.data.invoiceId;

                setTimeout(function (){
                    window.location.href = APP_CONFIG.apiBase + "/invoiceTemp?invoiceId=" + Id;
                })
            },function (err){
                toster.warning("Error Fetching Data");
            })
    }

    function clearAll(){
        $scope.soldDTO={
            retail:true,
            products:[],
            customerName:'',
            customerPhone:'',
            customerAddress:'',
        };
        $scope.productDTOs=[];
        $scope.selectedCustomer=null;
        $scope.customerSearchQuery = '';
        $scope.subtotal=0.00;
        $scope.calculatedTax=0;
        $scope.total=0.00;
    }

    $scope.printInvoice = function () {
        window.print();
    };


    $scope.getAllItems = function() {
        var allItems = [];
        if ($scope.soldDTO.products && $scope.soldDTO.products.length > 0) {
            angular.forEach($scope.soldDTO.products, function(product) {
                var enhancedProduct = angular.copy(product);
                enhancedProduct.itemType = 'product';
                allItems.push(enhancedProduct);
            });
        }
        if ($scope.soldDTO.serviceCharges && $scope.soldDTO.serviceCharges.length > 0) {
            angular.forEach($scope.soldDTO.serviceCharges, function(service) {
                var enhancedService = angular.copy(service);
                enhancedService.itemType = 'service';
                allItems.push(enhancedService);
            });
        }
        return allItems;
    };

    $scope.removeItem = function(item, displayIndex) {
        if (item.itemType === 'product') {
            var productIndex = -1;

            for (var i = 0; i < $scope.soldDTO.products.length; i++) {
                if ($scope.soldDTO.products[i].id === item.id) {
                    productIndex = i;
                    break;
                }
            }
            if (productIndex !== -1) {
                $scope.soldDTO.products.splice(productIndex, 1);
                calculateSubTotal();
            }
        }
        else if (item.itemType === 'service') {
            var serviceIndex = -1;

            for (var j = 0; j < $scope.soldDTO.serviceCharges.length; j++) {
                if ($scope.soldDTO.serviceCharges[j].serviceChargeType === item.serviceChargeType &&
                    $scope.soldDTO.serviceCharges[j].chargeAmount === item.chargeAmount) {
                    serviceIndex = j;
                    break;
                }
            }
            if (serviceIndex !== -1) {
                $scope.soldDTO.serviceCharges.splice(serviceIndex, 1);
                calculateSubTotal();
            }
        }
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };

    function calculateSubTotal(){
        $scope.subtotal = 0;

        $scope.soldDTO.products.forEach(p =>{
            if($scope.soldDTO.retail) {
                $scope.subtotal += p.selectedQuantity * p.retailPrice;
            }else {
                $scope.subtotal += p.selectedQuantity * p.dealerPrice;
            }
        });
        $scope.soldDTO.serviceCharges.forEach(s => {
            $scope.subtotal += s.chargeAmount;
        })

        $scope.subtotal = parseFloat($scope.subtotal.toFixed(2));

        $scope.calculatedTax = parseFloat($scope.subtotal * ($scope.tax / 100)).toFixed(2);

        $scope.total = (parseFloat($scope.subtotal) + parseFloat($scope.calculatedTax)).toFixed(2);
    }

    function validateInputs() {
        var dto = $scope.soldDTO;

        dto.customerName = (dto.customerName || '').trim();
        dto.customerPhone = (dto.customerPhone || '').trim();

        const hasProduct = dto.products && dto.products.length > 0;
        const hasService = dto.serviceCharges && dto.serviceCharges.length > 0;

        if (!hasProduct && !hasService) {
            toastr.error('Please add at least one product or service.', 'Validation Error');
            return false;
        }
        if (!dto.customerName) {
            toastr.error('Customer name is required.', 'Validation Error');
            return false;
        }

        if (!dto.customerPhone) {
            toastr.error('Customer phone number is required.', 'Validation Error');
            return false;
        }
        return true;
    }

    getProductDTOs();
    getCustomers();
});

