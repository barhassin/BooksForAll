
/* Our main application module is defined here using a single controller which will initiate its scope
and define some behavior.
This module further depends on an helper module 'txtHighlight'.
*/
var app = angular.module('signUpApp', []);
	

	
	app.controller('signUpController', function($scope,$http) {
	 //input validation//
		$scope.sevenNumbers = /^[0-9]{1,7}$/;
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
		var Telephoned =$scope.Telephone;
		var Nicknamed =$scope.Nickname;
		var Descriptiond=$scope.Description;
		
		var parameter = JSON.stringify({username:usernamed, password:passwordd, type:"user", email:emaild, street:Streetd, streetNumber:StreetNumberd, city:Cityd, zipcode:Zipcoded, telephone:Telephoned, nickname:Nicknamed, description:Descriptiond});
		$http.post("http://localhost:8080/BooksForAll/signUp",parameter)
		.then(function(response) {
			$scope.content=response.data.username;
		}, function(response) {
			//navigate to home page
			var status = response.status;
			if(status=="410")
				$scope.content="There is already a user with that username  in our system. please try again with Other user name ";
		    	  
		});
	};
});

