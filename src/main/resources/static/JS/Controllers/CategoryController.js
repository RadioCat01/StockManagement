angular.module('Stock').controller('CategoryMgt', function($scope, $http, $timeout){
    $scope.categoryName='';

    $scope.addCategory=function (){
        $http.post(APP_CONFIG.apiBase + `/mgt/createCategory`,$scope.categoryName)
            .then(function (response) {
                toastr.success('Category Added!', 'Success');
            }, function (error) {
                toster.warning("Error Fetching Data");
            });
    }

    function getMainCategories(){
        $http.get(APP_CONFIG.apiBase + '/mgt/cat')
            .then(function (res) {
                $scope.categories = res.data;
                $scope.categoryHeaders = getUniqueFields($scope.categories);
            }, function (err) {
                toster.warning("Error Fetching Data");
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
});