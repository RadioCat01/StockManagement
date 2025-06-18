angular.module('Stock').controller('FormsCont', function($scope, $http){
    $scope.formTypes = [];
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
            },function (err){
                toastr.warning("Error Fetching company Form","Warning!")
            });
    };

    $scope.addFieldToForm = function () {
        if (!$scope.newField || !$scope.newField.fieldName) return;

        $http.post(APP_CONFIG.apiBase + '/forms', $scope.newField)
            .then(function () {
                toastr.success("Field added to " + $scope.activeFormType);
                $scope.openFormModal($scope.activeFormType);
                clearFields();
            }, function () {
                toastr.error("Failed to add field");
            });
    };

    function getTemplates(){
        $http.get(APP_CONFIG.apiBase +'/forms/templates')
            .then(function (res){
                $scope.formTypes = res.data;
            },function (err){
                toastr.error("Failed to fetch templates");
            })
    }

    function clearFields(){
        $scope.newField={
            templateId:0,
            fieldName:'',
            fieldType:'',
            mandatory:false
        };
    }

    getTemplates();
});