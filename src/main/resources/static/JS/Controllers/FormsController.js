angular.module('Stock').controller('FormsCont', function($scope, $http){
    $scope.companyFields=[];
    $scope.newField={
        templateId:0,
        fieldName:'',
        fieldType:'',
        fieldQuestion:'',
        mandatory:false
    };

    $scope.addCompanyField = function (){
        $http.post(APP_CONFIG.apiBase +'/forms/company', $scope.newField)
            .then(function (res){
                $scope.companyFields = res.data;
                toastr.success("Field Added.","Success!")
                clearFields();
                getCompany();
            }, function (err){
                tostr.warning("Error adding field","Warning!")
            })
    }

    function getCompany(){
        $http.get(APP_CONFIG.apiBase +'/forms/company')
            .then(function (res){
                $scope.companyFields = res.data;
                if ($scope.companyFields.length > 0) {
                    $scope.newField.templateId = $scope.companyFields[0].templateId;
                }
            },function (err){
                toastr.warning("Error Fetching company Form","Warning!")
            });
    }
    function clearFields(){
        $scope.newField={
            templateId:0,
            fieldName:'',
            fieldType:'',
            mandatory:false
        };
    }
    getCompany();
});