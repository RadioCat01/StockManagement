angular.module('Stock').controller('BrandMgt', function ($scope, $http, $timeout) {
    $scope.brands = [];
    $scope.brandHeaders = [];
    $scope.categories = [];
    $scope.genericFields = [];
    $scope.inputData = {};

    $scope.selectedCategory = null;

    $scope.selectCategory = function (category) {
        $scope.selectedCategory = category;
        $scope.selectedBrand = null;
    };

    $scope.brandDTO = {
        categoryId: 0,
        brandName: '',
        productDescription: '',
        itemCode: '',
        customFields: []
    };

    $scope.itemCustom = {
        stockNumber: { enabled: false, value: '' },
        additional: { enabled: false, value: '' }
    };

    $scope.toggleCustomField = function (field) {
        if (!$scope.itemCustom[field].enabled) {
            $scope.itemCustom[field].value = '';
        }
    };

    $scope.openForm = function (type) {
        $http.get(APP_CONFIG.apiBase + '/forms/' + type.toLowerCase())
            .then(function (res) {
                $scope.genericFields = res.data;
                $scope.type = type.toLowerCase();
            }, function () {
                toastr.warning("Failed to load form fields", "Warning!");
            });
    };

    $scope.saveData = function (type) {
        if (type.toLowerCase() === 'brand') {
            $scope.addBrand();
        }
    };

    $scope.addBrand = function () {
        var dto = $scope.brandDTO;
        dto.brandName = (dto.brandName || '').trim();
        dto.itemCode = (dto.itemCode || '').trim();

        if (!dto.brandName) return toastr.error('Brand name is required.', 'Validation Error');
        if (!dto.itemCode) return toastr.error('Item code is required.', 'Validation Error');
        if (dto.categoryId <= 0) return toastr.error('Please select a valid category.', 'Validation Error');

        if (dto.productDescription && dto.productDescription.trim().length > 500) {
            return toastr.error('Product description cannot exceed 500 characters.', 'Validation Error');
        }

        if ($scope.itemCustom.stockNumber.enabled && $scope.itemCustom.stockNumber.value != null) {
            dto.customFields.push({
                fieldName: "Stock Number",
                fieldValue: $scope.itemCustom.stockNumber.value,
                fieldType: "TEXT",
                enabled: true,
                required: false
            });
        }
        if ($scope.itemCustom.additional.enabled && $scope.itemCustom.additional.value != null) {
            dto.customFields.push({
                fieldName: "Additional Details",
                fieldValue: $scope.itemCustom.additional.value,
                fieldType: "TEXT",
                enabled: true,
                required: false
            });
        }

        $http.post(APP_CONFIG.apiBase + '/mgt/createBrand', dto)
            .then(function () {
                getCategories();
                clearFields();
                toastr.success('Brand Added!', 'Success');
                $('#addGenericDataModal').modal('hide');
            }, function () {
                toastr.error("Error Saving Data", "Error!");
            });
    };

    $scope.selectCategoryForBrand = function (categoryId) {
        const id = parseInt(categoryId);
        $scope.brandDTO.categoryId = id;
        $scope.selectedCategory = $scope.categories.find(c => c.categoryId === id) || null;
    };

    function getCategories() {
        $http.get(APP_CONFIG.apiBase + '/mgt/cat')
            .then(function (res) {
                $scope.categories = res.data.map(function (cat) {
                    cat.displayName = cat.categoryName;
                    return cat;
                });
                getBrands();
            }, function () {
                toastr.warning("Error Fetching Category Data");
            });
    }

    function getBrands() {
        const brandList = [];
        ($scope.categories || []).forEach(function (cat) {
            (cat.brands || []).forEach(function (brand) {
                brand.categoryName = cat.categoryName;
                brand.displayName = brand.brandName;
                brand.customFields = brand.customFields || {};
                brandList.push(brand);
            });
        });
        $scope.brands = brandList;
        $scope.brandHeaders = getUniqueFields($scope.brands);
    }

    function clearFields() {
        $scope.brandDTO = {
            categoryId: 0,
            brandName: '',
            productDescription: '',
            itemCode: '',
            customFields: []
        };
        $scope.itemCustom.stockNumber.value = '';
        $scope.itemCustom.additional.value = '';
        $scope.selectedCategory = null;
    }

    function getUniqueFields(entityList) {
        const fieldSet = new Set();
        entityList.forEach(entity => {
            if (entity.customFields) {
                Object.keys(entity.customFields).forEach(field => fieldSet.add(field));
            }
        });
        return Array.from(fieldSet);
    }

    getCategories();
});
