angular.module('Stock').controller('AuthCont', function($scope, $http, $timeout){
    $scope.loginReq = {
        username:"",
        password:"",
        loginType: LOGIN_CONFIG.loginType
    }
    $scope.login = function (){
        $http.post(APP_CONFIG.apiBase + `/auth/login`,$scope.loginReq)
            .then(function (response) {
                var roles = response.data.roles || [];
                var landingPage = roles.includes('ROLE_PLATFORM_ADMIN')
                    || roles.includes('ROLE_COMPANY_ADMIN')
                    ? '/admin/users/page'
                    : roles.includes('ROLE_STOCK_CLERK')
                        ? '/inventory'
                        : roles.includes('ROLE_CASHIER')
                            ? '/pos'
                            : null;

                if (!landingPage) {
                    toastr.error('Login succeeded, but this account has no application role. Ask an administrator to assign one.');
                    return;
                }
                window.location.href = APP_CONFIG.apiBase + landingPage;
            }, function (error) {
                var message = error.data && error.data.message;
                toastr.warning(message || (error.status === 403
                    ? "This account cannot use this login page."
                    : "Login failed. Check your username and password."));
            });
    }
});