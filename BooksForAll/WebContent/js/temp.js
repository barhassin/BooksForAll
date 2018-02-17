var app = angular.module('temp', []);
app.controller('payment', function($rootScope,$scope,$http,$window) {
	//$scope.price = $rootscope.bookPrice;
	$scope.threeNumbers = /^[0-9]{3}$/;
	$scope.twoNumbers = /^[0-9][0-9]$/;
	$scope.lettersOnly =/^[a-zA-Z ]{1,100}$/;
	$scope.amexValidation = /^[3][4][0-9]{13}$/;
	$scope.amexValidation = /^[4][0-9]{15}$/;

	$scope.submitPaymet=function(){
		
	}
});