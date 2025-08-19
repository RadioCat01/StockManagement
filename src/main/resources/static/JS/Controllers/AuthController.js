angular.module('Stock').controller('AuthCont', function($scope, $http, $timeout){
    $scope.loginReq = {
        username:"",
        password:""
    }
    $scope.login = function (){
        $http.post(APP_CONFIG.apiBase + `/auth/login`,$scope.loginReq)
            .then(function (response) {
                window.location.href = APP_CONFIG.apiBase + "/inventory";
            }, function (error) {
                toster.warning("Error Login");
            });
    }
});