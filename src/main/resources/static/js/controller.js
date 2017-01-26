/**
 * Created by sbaranau on 11/28/2016.
 */
app.controller('usersController', function($scope, ngTableParams, $http,$filter) {
    $scope.headingTitle = "Пользователи";
    $scope.systems = [{id: "", title: ""}, {id: 'ios', title: 'ios'}, {id: 'Android', title: 'Android'}];
    $scope.gender = [{id: "", title: ""}, {id: 'мужской', title: 'мужской'}, {id: 'женский', title: 'женский'}, {id: 'скрыт', title: 'скрыт'}];
    $http.get(serverUrl + 'tokens')
        .then(
            function(response){
                var users = response.data.data;
                angular.forEach(users, function(user) {
                    if (user.login == "") {
                        user.isman = 'скрыт'
                    } else if (user.isman == 1) {
                        user.isman = 'мужской'
                    } else {
                        user.isman = 'женский'
                    }
                });
                $scope.tableParams = new ngTableParams({
                    page: 1,
                    count: 10
                }, {
                    total: users.length, // length of data
                    getData: function($defer, params) {
                        $scope.data = params.sorting() ? $filter('orderBy')(users, params.orderBy()) : users;
                        $scope.data = params.filter() ? $filter('filter')($scope.data, params.filter()) : $scope.data;
                        params.total($scope.data.length);
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
        angular.forEach($scope.data, function(user) {
            if (user.select) {
                recipients.push(user.token);
            }
        });
        $scope.showSuccessAlert = false;
        $scope.showErrorAlert = false;
        if ($scope.message_title == undefined || $scope.message_title.length == 0) {
            $scope.errorMessage = 'Пожалуйста, заполните тему';
            $scope.showErrorAlert = true;
            return;
        }
        if ($scope.message_text == undefined || $scope.message_text.length == 0) {
            $scope.errorMessage = 'Пожалуйста, заполните текст сообщение';
            $scope.showErrorAlert = true;
            return;
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
            $scope.successTextAlert = "Сообщение послано";
            $scope.showSuccessAlert = true;
        } else {
            $scope.errorMessage = 'Пожалуйста, выберите получателя';
            $scope.showErrorAlert = true;
        }
    };

});

app.controller('rolesController', function($scope) {
    $scope.headingTitle = "Список ролей";
});
