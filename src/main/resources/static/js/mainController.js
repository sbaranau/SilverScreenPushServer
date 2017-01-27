/**
 * Created by sbaranau on 1/27/2017.
 */
app.controller('mainController', function($scope, ngTableParams, $http,$filter,$location) {
    if ($scope.user != undefined && $scope.user.username != undefined) {
        $scope.userName = 'Вы вошли как: ' + $scope.user.username
    } else {
        $scope.userName = ''
    }
    updateUser = function(val) {
        $scope.user = val;
    }
});