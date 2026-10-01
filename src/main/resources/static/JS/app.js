var app = angular.module('Stock', []);

app.config(function ($httpProvider) {
    var csrfTokenPromise;
    $httpProvider.interceptors.push(function ($injector) {
        return {
            request: function (config) {
                if (/^(GET|HEAD|OPTIONS|TRACE)$/i.test(config.method)) {
                    return config;
                }
                if (!csrfTokenPromise) {
                    csrfTokenPromise = $injector.get('$http')
                        .get(APP_CONFIG.apiBase + '/auth/csrf')
                        .then(function (response) {
                            return response.data.token;
                        });
                }
                return csrfTokenPromise.then(function (token) {
                    config.headers['X-XSRF-TOKEN'] = token;
                    return config;
                });
            }
        };
    });
});
