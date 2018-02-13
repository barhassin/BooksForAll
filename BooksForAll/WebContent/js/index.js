/* Our main application module is defined here using a single controller which will initiate its scope
and define some behavior.
This module further depends on an helper module 'txtHighlight'.
*/
var app = angular.module('login', []);
	app.controller('bfaController', function($rootScope,$scope,$http,$window) {
		});
	app.controller('loginController', function($rootScope,$scope,$http,$window) {	
	$scope.login = function(){
		var usr = $scope.username;
		var pwd = $scope.password;
		var parameter = JSON.stringify({username:usr, password:pwd, type:""});
		$http.post("http://localhost:8080/BooksForAll/login", parameter)
		.then(function(response) {
			$rootScope.user=usr;
			$scope.changeLogin=false;
			$scope.$parent.changeNavbar=true;
		}, function(response) {
			var status = response.status;
			if(status=="405"){
				$("#myModal").modal('show');
				$scope.content="There is no such user in our system. you can try again or sign up and \"book\" yourself a seat in the spectacular ride that is our application!";
		}
			else if(status=="406"){ 
				$("#myModal").modal('show');
				$scope.content="The password is incorrect";
		}
		    	  
		});
	};
	$scope.signup = function(){
		$scope.changeLogin=false;
		$scope.$parent.changeSignup=true;
	}
});
app.controller('welcomeController', function($rootScope,$scope,$http,$window) {
	$scope.content=$rootScope.user;
});
app.controller('navbarController', function($rootScope,$scope,$http,$window) {
	$scope.content=$rootScope.user;
	$scope.welcome = function(){
		$scope.changeWelcome=true;
		$scope.changeMyBooks=false;
		$scope.changeBrowseBooks=false;
		$('li.active').removeClass('active');
		$('a[ng-click="welcome()"]').closest('li').addClass('active');
	}
	$scope.myBooks = function(){
		$scope.changeWelcome=false;
		$scope.changeMyBooks=true;
		$scope.changeBrowseBooks=false;
		$('li.active').removeClass('active');
		$('a[ng-click="myBooks()"]').closest('li').addClass('active');
	}
	$scope.browseBooks = function(){
		$scope.changeWelcome=false;
		$scope.changeMyBooks=false;
		$scope.changeBrowseBooks=true;
		$('li.active').removeClass('active');
		$('a[ng-click="browseBooks()"]').closest('li').addClass('active');
	}
});
app.controller('browseBooksController', function($rootScope,$scope,$http,$window) {
	$scope.content=$rootScope.user;
});
app.controller('myBooksController', function($rootScope,$scope,$http,$window) {
	$scope.content=$rootScope.user;
});
