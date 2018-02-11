
/* Our main application module is defined here using a single controller which will initiate its scope
and define some behavior.
This module further depends on an helper module 'txtHighlight'.
*/
var app = angular.module('signUpApp', []);
	app.controller('signUpController', function($scope,$http) {
			
	$scope.login = function(){
		var usernamed = $scope.userName;
		var passwordd = $scope.password;
		var emaild = $scope.email;
		var Streetd =$scope.Street;
		var StreetNumberd =$scope.StreetNumber;
		var Cityd =$scope.City;
		var Zipcoded =$scope.Zipcode;
		var Telephoned =$scope.Telephone;
		var Nicknamed =$scope.Nickname;
		var Descriptiond=$scope.Description;
		
		
		var parameter = JSON.stringify({username:usernamed, password:passwordd, email:emaild, Street:Streetd, StreetNumberd:StreetNumber, City:Cityd, Zipcode:Zipcoded, Telephone:Telephoned, Nickname:Nicknamed, Description,Descriptiond});
		$http.post("http://localhost:8080/BooksForAll/login", parameter)
		.then(function(response) {
			$scope.content=response.data.username;
		}, function(response) {
			var status = response.status;
			if(status=="405")
				$scope.content="There is no such user in our system. you can try again or sign up and \"book\" yourself a seat in the spectacular ride that is our application!";
			else if(status=="406")	  
				$scope.content="The password is incorrect";
		    	  
		});
	};
});

