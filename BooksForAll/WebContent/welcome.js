var app = angular.module('login', []);
	app.controller('welcomeController', function($rootScope,$scope,$http,$window) {
		$scope.$watch(function() {
			return $rootScope.poop;
		}, function() {
			$scope.content = $rootScope.poop;
		}, true);
	});