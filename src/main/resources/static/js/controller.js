/**
 * Created by sbaranau on 11/28/2016.
 */
app.controller('usersController', function($scope, ngTableParams, $http,$filter) {
    $scope.headingTitle = "Users and last Enter date";
  //  $scope.data = [{name: "Moroni", age: 50},{name: "Moroni2", age: 50} /*,*/];
    $http.get(serverUrl + 'tokens')
        .then(
            function(response){
                $scope.users = response.data.data;
                $scope.tableParams = new ngTableParams({
                    page: 1,
                    count: 10
                }, {
                    total: $scope.users.length, // length of data
                    getData: function($defer, params) {
                        $scope.data = params.sorting() ? $filter('orderBy')($scope.users, params.orderBy()) : $scope.users;
                        $scope.data = params.filter() ? $filter('filter')($scope.data, params.filter()) : $scope.data;
                        $scope.data = $scope.data.slice((params.page() - 1) * params.count(), params.page() * params.count());
                        $defer.resolve($scope.data);
                    }
                });
            },
            function(response){
                // failure callback
            }
        );
    $scope.selectAll = false;
    $scope.checkAll = function() {
        $scope.selectAll = !$scope.selectAll;
        angular.forEach($scope.data, function(user) {
            user.select = $scope.selectAll;
        });
    };
    $scope.submitMessage = function() {
        var recipients = [];
        angular.forEach($scope.users, function(user) {
            $scope.showSuccessAlert = false;
            $scope.showErrorAlert = false;
            if ($scope.message_title == undefined || $scope.message_title.length == 0) {
                $scope.errorMessage = 'Please enter title';
                $scope.showErrorAlert = true;
                return;
            }
            if ($scope.message_text == undefined || $scope.message_text.length == 0) {
                $scope.errorMessage = 'Please enter message';
                $scope.showErrorAlert = true;
                return;
            }
            if (user.select) {
                recipients.push(user.token);
            }
            if (recipients.length > 0) {
                var postData = {
                    'tokens' : recipients,
                    'title' : $scope.message_title,
                    'message' : $scope.message_text

                };
                var parameter = JSON.stringify(postData);
                $http.post(serverUrl + 'send', parameter).
                success(function(data, status, headers, config) {
                    // this callback will be called asynchronously
                    // when the response is available
                    console.log(data);
                }).
                error(function(data, status, headers, config) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                });
                $scope.successTextAlert = "Message sent";
                $scope.showSuccessAlert = true;
            } else {
                $scope.errorMessage = 'Please select recipient';
                $scope.showErrorAlert = true;
            }

        });
    };

});

app.controller('rolesController', function($scope) {
    $scope.headingTitle = "Roles List";
});
