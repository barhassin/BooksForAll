/* Our main application module is defined here using a single controller which will initiate its scope
and define some behavior.
This module further depends on an helper module 'txtHighlight'.
*/
angular.module('login')
	.controller('loginController', ['$scope','$http', function($scope,$http) {
			
	$scope.search = function(){
		var usr = $scope.username;
		var pwd = $scope.password;
		var parameter = JSON.stringify({username:usr, password:pwd, type:""});
		$http.post("http://localhost:8080/BooksForAll/login", parameter)
		.then(function(response) {
			$scope.content = "good";
		}, function(response) {
		      $scope.content = "Something went wrong";
		});
	};
}]);

