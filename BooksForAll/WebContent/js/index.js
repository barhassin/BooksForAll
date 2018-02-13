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
app.controller('navbarController', function($scope) {
	$scope.func = function(){
		$scope.content="boo";
	}
});
app.controller('signUpController', function($scope,$http) {
	 //input validation//
		$scope.sevenNumbers = /^[0-9]{7}$/;
		$scope.lettersOnly =/^[a-zA-Z ]{1,100}$/;
		$scope.onlyNumbers = /^[1-9][0-9]*$/;
		
		$scope.signUpApp = function(){
		var usernamed = $scope.userName;
		var passwordd = $scope.password;
		var emaild = $scope.email;
		var Streetd =$scope.Street;
		var StreetNumberd =$scope.StreetNumber;
		var Cityd =$scope.City;
		var Zipcoded =$scope.Zipcode;
		var Telephoned =$scope.selected + "-" + $scope.Telephone;
		var Nicknamed =$scope.Nickname;
		var Descriptiond=$scope.Description;
		console.log(Telephoned);
		var parameter = JSON.stringify({username:usernamed, password:passwordd, type:"user", email:emaild, street:Streetd, streetNumber:StreetNumberd, city:Cityd, zipcode:Zipcoded, telephone:Telephoned, nickname:Nicknamed, description:Descriptiond});
		$http.post("http://localhost:8080/BooksForAll/signUp",parameter)
		.then(function(response) {
			$scope.content=response.data.username;
		}, function(response) {
			var status = response.status;
			if(status=="411")
				$scope.content=Telephoned;
			//navigate to home page
			if(status=="410")
				$scope.content="There is already a user with that username  in our system. please try again with Other user name ";
		    	  
		});
	};
});

