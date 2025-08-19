angular.module('Stock').controller('FormsCont', function($scope, $http){
    $scope.entityFields = [];
    $scope.newField={
        templateId:0,
        fieldName:'',
        fieldType:'',
        fieldQuestion:'',
        mandatory:false
    };

    $scope.openFormModal = function(formType) {
        $scope.activeFormType = formType;
        $scope.newField = {};
        $scope.entityFields = [];

        $http.get(APP_CONFIG.apiBase +'/forms/' + formType.toLowerCase())
            .then(function (res){
                $scope.entityFields = res.data;
                if ($scope.entityFields.length > 0) {
                    $scope.newField.templateId = $scope.entityFields[0].templateId;
                }
                console.log("called");
            },function (err){
                toastr.warning("Error Fetching Form Data","Warning!")
            });
    };

    $scope.addFieldToForm = function () {
        console.log("called");
        if (!$scope.newField || !$scope.newField.fieldName) return;

        $http.post(APP_CONFIG.apiBase + '/forms/'+$scope.activeFormType.toLowerCase(), $scope.newField)
            .then(function () {
                toastr.success("Field added to " + $scope.activeFormType);
                $scope.openFormModal($scope.activeFormType);
                clearFields();
            }, function () {
                toastr.error("Failed to add field");
            });
    };

    function clearFields(){
        $scope.newField={
            templateId:0,
            fieldName:'',
            fieldType:'',
            mandatory:false
        };
    }

});