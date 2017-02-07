/**
 * Created by sbaranau on 1/27/2017.
 */
app.controller('mainController', function($rootScope, $scope, $http, serverSettings) {
    if ($scope.user != undefined && $scope.user.username != undefined) {
        $scope.userName = 'Вы вошли как: ' + $scope.user.username
    } else {
        $scope.userName = ''
    }
    updateUser = function(val) {
        $scope.user = val;
    };

    var authenticate = function(credentials, callback) {

        var headers = credentials ? {
            authorization : "Basic "
            + btoa(credentials.username + ":"
                + credentials.password)
        } : {};

        $http.get(serverSettings.serverUrl + 'user', {
            headers : headers
        }).then(function(response) {
            if (response.data.name) {
                $rootScope.authenticated = true;
            } else {
                $rootScope.authenticated = false;
            }
            callback && callback($rootScope.authenticated);
        }, function() {
            $rootScope.authenticated = false;
            callback && callback(false);
        });

    };

    authenticate();

    self.credentials = {};
    self.login = function() {
        authenticate(self.credentials, function(authenticated) {
            if (authenticated) {
                console.log("Login succeeded")
                $location.path("/");
                self.error = false;
                $rootScope.authenticated = true;
            } else {
                console.log("Login failed")
                $location.path("/login");
                self.error = true;
                $rootScope.authenticated = false;
            }
        })
    };

    self.logout = function() {
        $http.post(serverSettings + 'logout', {}).finally(function() {
            $rootScope.authenticated = false;
            $location.path("/");
        });
    }
});