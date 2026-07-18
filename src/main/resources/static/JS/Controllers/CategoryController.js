angular.module('Stock').controller('CategoryMgt', function ($scope, $http, $timeout) {
    $scope.categoryName = '';
    $scope.genericFields = [];
    $scope.inputData = {};

    $scope.openForm = function (type) {
        $http.get(APP_CONFIG.apiBase + '/forms/' + type.toLowerCase())
            .then(function (res) {
                $scope.genericFields = res.data;
                $scope.type = type.toLowerCase();

                angular.forEach($scope.genericFields, function (field) {
                    if (field.fieldType === 'selectStores') {
                        $scope.inputData[field.fieldName] = [];
                    }
                });

            }, function () {
                toastr.warning("Failed to load form fields", "Warning!");
            });
    }

    $scope.saveData = function (type) {
        if (type.toLowerCase() === 'category') {
            const categoryName = $scope.inputData['categoryName'];
            if (!categoryName) {
                toastr.warning("Category Name is required", "Warning!");
                return;
            }
            $http.post(APP_CONFIG.apiBase + '/mgt/createCategory', categoryName)
                .then(function () {
                    getMainCategories();
                    $scope.inputData = {};
                    toastr.success("Category saved successfully");
                    $('#addGenericDataModal').modal('hide');
                }, function () {
                    toastr.error("Error saving category", "Error!");
                });
        } else {
            angular.forEach($scope.genericFields, function (field) {
                if (field.fieldType === 'date') {
                    const val = $scope.inputData[field.fieldName];
                    if (val) {
                        $scope.inputData[field.fieldName] = new Date(val).toISOString().split('T')[0];
                    }
                }
            });
            $http.post(APP_CONFIG.apiBase + '/company/' + type, {
                formData: $scope.inputData
            })
                .then(function () {
                    getMainCategories();
                    $scope.inputData = {};
                    toastr.success("Data saved successfully");
                    $('#addGenericDataModal').modal('hide');
                }, function () {
                    toastr.error("Error saving data", "Error!");
                });
        }
    };

    $scope.selectCompany = function (companyId) {
        const id = parseInt(companyId);
        $scope.selectedSubCompanies = $scope.subCompanies.filter(function (subCompany) {
            return subCompany.companyId === id;
        });
    };

    $scope.selectSubCompany = function (subCompanyId) {
        const id = parseInt(subCompanyId);
        $scope.selectedStores = $scope.stores.filter(function (store) {
            return store.subcompanyId === id;
        });
    };

    $scope.addCategory = function () {
        $http.post(APP_CONFIG.apiBase + `/mgt/createCategory`, $scope.categoryName)
            .then(function (response) {
                toastr.success('Category Added!', 'Success');
                getMainCategories();
            }, function (error) {
                toastr.warning("Error Saving Data");
            });
    }

    function getMainCategories() {
        $http.get(APP_CONFIG.apiBase + '/mgt/cat')
            .then(function (res) {
                $scope.categories = res.data.map(function (cat) {
                    cat.displayName = cat.categoryName;
                    return cat;
                });
                $scope.categoryHeaders = getUniqueFields($scope.categories);
            }, function (err) {
                toastr.warning("Error Fetching Data");
            });
    }

    function loadCompanyData() {
        $http.get(APP_CONFIG.apiBase + '/company/company')
            .then(function (res) {
                $scope.companies = res.data;
            }, function () {
                toastr.warning("Error fetching company data", "Warning!");
            });

        $http.get(APP_CONFIG.apiBase + '/company/subcompany')
            .then(function (res) {
                $scope.subCompanies = res.data;
            }, function () {
                toastr.warning("Error fetching subcompany data", "Warning!");
            });

        $http.get(APP_CONFIG.apiBase + '/company/store')
            .then(function (res) {
                $scope.stores = res.data;
            }, function () {
                toastr.warning("Error fetching store data", "Warning!");
            });
    }

    function getUniqueFields(entityList) {
        const fieldSet = new Set();
        entityList.forEach(entity => {
            if (entity.customFields) {
                Object.keys(entity.customFields).forEach(field => {
                    fieldSet.add(field);
                });
            }
        });
        return Array.from(fieldSet);
    }

    getMainCategories();
    loadCompanyData();
});