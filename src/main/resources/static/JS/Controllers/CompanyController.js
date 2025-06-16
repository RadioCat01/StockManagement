angular.module('Stock').controller('CompanyCont', function($scope, $http){
    $scope.companies=[];
    $scope.companyFields = [];
    $scope.companyData = {};

    $scope.loadCompanyFields = function () {
        $http.get(APP_CONFIG.apiBase + '/forms/company')
            .then(function (res) {
                $scope.companyFields = res.data;
            }, function () {
                toastr.warning("Failed to load form fields", "Warning!");
            });
    };

    $scope.saveCompanyData = function () {
        $http.post(APP_CONFIG.apiBase + '/company/com', {
            formData: $scope.companyData
        })
            .then(function () {
                getCompanies();
                toastr.success("Company data saved successfully");
                $('#addCompanyDataModal').modal('hide');
            }, function () {
                toastr.error("Error saving company data", "Error!");
            });
    };

    function getCompanies(){
        $http.get(APP_CONFIG.apiBase +'/company/com')
            .then(function (res){
                $scope.companies=res.data;
                console.log($scope.companies);
            },function (err){
                toastr.warning("Error fetching company Data","Warning!")
            })
    }

    function loadCompanyFields2(){
        $http.get(APP_CONFIG.apiBase + '/forms/company')
            .then(function (res) {
                $scope.companyFields = res.data;
            }, function () {
                toastr.warning("Failed to load form fields", "Warning!");
            });
    }
    loadCompanyFields2();
    getCompanies();
});