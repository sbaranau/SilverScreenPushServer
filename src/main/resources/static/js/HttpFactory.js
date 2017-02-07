/**
 * Created by sbaranau on 2/3/2017.
 */

app.factory('tokens', function($q, $http, serverSettings) {
    var getTokens = function(callback) {
        $http.get(serverSettings.serverUrl + 'tokens')
            .then(
                function (response) {
                    var users = response.data;
                    //  $scope.user = response.data.user;
                    angular.forEach(users, function (user) {
                        if (user.login == "") {
                            user.isman = 'скрыт'
                        } else if (user.isman == 1) {
                            user.isman = 'мужской'
                        } else {
                            user.isman = 'женский'
                        }
                    });
                    callback( {
                        message: "",
                        data: users
                    })
                },
                function (response) {
                    callback( {
                        message: response.message,
                        data: []
                    })
                }
            );
    };
    var getUser = function() {
        var deferred = $q.defer();
        $http.get(serverSettings.serverUrl + 'username')
            .then(
                function(response){
                    deferred.resolve({
                        message : "",
                        user : response.data
                    });

                },
                function(response){
                    deferred.resolve({
                        message : response.message
                    });
                }
            );
        return deferred.promise;
    };
    var getNotifications = function() {
        var deferred = $q.defer();
        $http.get(serverSettings.serverUrl + 'notifications')
            .then(
                function(response){
                    deferred.resolve({
                        message : "",
                        data : response.data
                    });

                },
                function(response){
                    deferred.resolve({
                        message : response.message,
                        data: []
                    });
                }
            );
        return deferred.promise;
    };

    return {
        getTokens: getTokens,
        getUser: getUser,
        getNotifications: getNotifications
    }
});
