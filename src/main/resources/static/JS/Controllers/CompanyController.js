angular.module('Stock').controller('CompanyCont', function($scope, $http){

    $scope.genericFields = [];
    $scope.inputData = {};

    $scope.saveData = function (type) {
        angular.forEach($scope.genericFields, function (field) {
            if (field.fieldType === 'date') {
                const val = $scope.inputData[field.fieldName];
                if (val) {
                    $scope.inputData[field.fieldName] = new Date(val).toISOString().split('T')[0];
                }
            }
        });
        $http.post(APP_CONFIG.apiBase + '/company/'+ type, {
            formData: $scope.inputData
        })
            .then(function () {
                getCompanyData();
                $scope.inputData = {};
                toastr.success("Company data saved successfully");
                $('#addCompanyDataModal').modal('hide');
            }, function () {
                toastr.error("Error saving data", "Error!");
            });
    };

    $scope.openForm = function (type){
        $http.get(APP_CONFIG.apiBase + '/forms/'+ type.toLowerCase())
                .then(function (res) {
                    $scope.genericFields = res.data;
                    $scope.type=type.toLowerCase();

                    angular.forEach($scope.genericFields, function (field) {
                        if (field.fieldType === 'selectStores') {
                            $scope.inputData[field.fieldName] = [];
                        }
                    });

                }, function () {
                    toastr.warning("Failed to load form fields", "Warning!");
                });
    }

    $scope.selectCompany = function (companyId) {
        const id = parseInt(companyId);
        $scope.selectedSubCompanies = $scope.subCompanies.filter(function (subCompany) {
            return subCompany.companyId === id;
        });
    };
    $scope.selectSubCompany = function (subCompanyId){
        const id = parseInt(subCompanyId);
        $scope.selectedStores = $scope.stores.filter(function (store) {
            return store.subcompanyId === id;
        });
    }
    $scope.selectStore = function (storeId){
        const id = parseInt(storeId);
        $scope.selectedStoreFronts = $scope.storeFronts.filter(function (storeFront) {
            return storeFront.storeId && storeFront.storeId.includes(id);
        });
    }
    $scope.selectStoreFront= function (storeFrontId){
        const id = parseInt(storeFrontId);
        $scope.selectedCounters = $scope.counters.filter(function (counter) {
            return counter.storefrontId === id;
        });
    }

    function getCompanyData(){
        $http.get(APP_CONFIG.apiBase +'/company/company')
            .then(function (res){
                $scope.companies=res.data;
                $scope.companyHeaders = getUniqueFields($scope.companies);

            },function (err){
                toastr.warning("Error fetching company Data","Warning!")
            })

        $http.get(APP_CONFIG.apiBase +'/company/cashdrawer')
            .then(function (res){
                $scope.drawers=res.data;
                $scope.drawerHeaders = getUniqueFields($scope.drawers);

            },function (err){
                toastr.warning("Error fetching drawer Data","Warning!")
            })
        $http.get(APP_CONFIG.apiBase +'/company/posterminal')
            .then(function (res){
                $scope.posTerminals=res.data;
                $scope.posTerminalHeaders = getUniqueFields($scope.posTerminals);

            },function (err){
                toastr.warning("Error fetching POS Data","Warning!")
            })
        $http.get(APP_CONFIG.apiBase +'/company/scanner')
            .then(function (res){
                $scope.scanner=res.data;
                $scope.scannerHeaders = getUniqueFields($scope.scanner);

            },function (err){
                toastr.warning("Error fetching scanner Data","Warning!")
            })
        $http.get(APP_CONFIG.apiBase +'/company/counter')
            .then(function (res){
                $scope.counters=res.data;
                $scope.countersHeaders = getUniqueFields($scope.counters);

            },function (err){
                toastr.warning("Error fetching counter Data","Warning!")
            })
        $http.get(APP_CONFIG.apiBase +'/company/storeFront')
            .then(function (res){
                $scope.storeFronts=res.data;
                $scope.storeFrontHeaders = getUniqueFields($scope.storeFronts);

            },function (err){
                toastr.warning("Error fetching storeFront Data","Warning!")
            })
        $http.get(APP_CONFIG.apiBase +'/company/store')
            .then(function (res){
                $scope.stores=res.data;
                $scope.storeHeaders = getUniqueFields($scope.stores);

            },function (err){
                toastr.warning("Error fetching store Data","Warning!")
            })
        $http.get(APP_CONFIG.apiBase +'/company/subcompany')
            .then(function (res){
                $scope.subCompanies=res.data;
                $scope.subCompanyHeaders = getUniqueFields($scope.subCompanies);

            },function (err){
                toastr.warning("Error fetching subCompany Data","Warning!")
            })
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


    getCompanyData();
});