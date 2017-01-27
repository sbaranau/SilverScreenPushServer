var app = angular.module('app', ['ngRoute','ngResource','ngTable','ui.bootstrap']);
app.config(function($routeProvider){
    $routeProvider
        .when('/users',{
            templateUrl: 'views/users.html',
            controller: 'usersController'
        })
        .when('/roles',{
            templateUrl: 'views/notifications.html',
            controller: 'notificationsController'
        })
        /*.when('/login',{
            templateUrl: 'views/_login.html',
            controller: 'loginController'
        })
        .when('/loginerror',{
            templateUrl: 'views/notifications.html'
        })*/
        .otherwise(
            { redirectTo: '/'}
        );
});
