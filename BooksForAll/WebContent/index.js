/* Our main application module is defined here using a single controller which will initiate its scope
and define some behavior.
This module further depends on an helper module 'txtHighlight'.
*/
var app = angular.module('login', []);
	app.controller('loginController', function($scope,$http) {
			
	$scope.search = function(){
		var usr = $scope.username;
		var pwd = $scope.password;
		var parameter = JSON.stringify({Username:usr, Password:pwd, Type:""});
		$http.post("http://localhost:8080/BooksForAll/login", parameter)
		.then(function(response) {
			$scope.content = "good";
		}, function(response) {
		      $scope.content = "Something went wrong";
		});
	};
});

