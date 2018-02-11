/* Our main application module is defined here using a single controller which will initiate its scope
and define some behavior.
This module further depends on an helper module 'txtHighlight'.
*/
var app = angular.module('login', []);
	app.controller('bfaController', function($rootScope,$scope,$http,$window) {
		$scope.next='html/login.html';
		//$rootScope.page=true;
		$rootScope.$watch(function() {
			return $rootScope.page;
		}, function() {
			$rootScope.page=$rootScope.page;
		}, true);
		});
	app.controller('loginController', function($rootScope,$scope,$http,$window) {	
	$scope.login = function(){
		var usr = $scope.username;
		var pwd = $scope.password;
		var parameter = JSON.stringify({username:usr, password:pwd, type:""});
		$http.post("http://localhost:8080/BooksForAll/login", parameter)
		.then(function(response) {
			//$rootScope.usern=response.data.username;
			$rootScope.poop="boo";
			$window.location.href = 'html/welcome.html';
		}, function(response) {
			var status = response.status;
			if(status=="405")
				$scope.content="There is no such user in our system. you can try again or sign up and \"book\" yourself a seat in the spectacular ride that is our application!";
			else if(status=="406")	  
				$scope.content="The password is incorrect";
		    	  
		});
	};
	$scope.signup = function(){
		//$rootScope.snext='html/signUp.html';
		$rootScope.page=false;
	}
});
app.controller('welcomeController', function($rootScope,$scope,$http,$window) {
	$scope.$watch(function() {
		return $rootScope.poop;
	}, function() {
		$scope.content = $rootScope.poop;
	}, true);
});