/**
 * Created by sbaranau on 1/26/2017.
 */
app.controller('loginController', function($rootScope, $scope, ngTableParams, $http,$filter,$location, serverSettings) {

    $scope.loginAction = function() {
        $http({
            method: 'POST',
            url: serverSettings.serverUrl + 'login',
            headers: {
            'Content-Type': undefined
            },
            data: {
                username: $scope.username,
                password: $scope.password
            }}
        ).then(function (response) {
            if (response.data.indexOf('Bad credentials') >= 0) {
                alert("Bad credentials")
            } else {
                $location.path('/users')
            }
        }, function (response) {
            alert(response.data)
        });
    }
});