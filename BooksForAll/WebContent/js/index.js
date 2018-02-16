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
			$scope.username="";
			$scope.password="";
			$scope.changeLogin=false;
			if(response.data.type=="user")
				$scope.changeNavbar=true;
			else
				$scope.changeNavbarAdmin=true;
		}, function(response) {
			var status = response.status;
			if(status=="405"){
				$("#myModal").modal('show');
				$scope.content="There is no such user in our system. you can try again or sign up and \"book\" yourself a seat in the spectacular ride that is our application!";
		}
			else if(status=="406"){ 
				$("#myModal").modal('show');
				$scope.content="The password is incorrect, please try again";
		}
		    	  
		});
	};
	$scope.signup = function(){
		$scope.changeLogin=false;
		$scope.changeSignup=true;
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
	$scope.logout = function(){
		$scope.changeWelcome=false;
		$scope.changeMyBooks=false;
		$scope.changeBrowseBooks=false;
		$rootScope.user="";
		$scope.$parent.$parent.$parent.changeNavbar=false;
		$scope.$parent.$parent.$parent.changeLogin=true;
	}
});
app.controller('browseBooksController', function($rootScope,$scope,$http,$window) {
	$scope.toBook = function(param){
		$rootScope.chosenBook=param;
		$scope.changeBrowse=false;
		$scope.changeBook=true;
	};
	$http.post("http://localhost:8080/BooksForAll/browseBooksServlet")
	.then(function(response) {
		$scope.bookslist = response.data;
	}, function(){});
});
app.controller('myBooksController', function($rootScope,$scope,$http,$window) {
	 
});
app.controller('signUpController', function($rootScope,$scope,$http,$window) {
	 //input validation//
		$scope.sevenNumbers = /^[0-9]{7}$/;
		$scope.lettersOnly =/^[a-zA-Z ]{1,100}$/;
		$scope.onlyNumbers = /^[1-9][0-9]*$/;
		
		$scope.signUpApp = function(){
		var usernamed = $scope.userName;
		var passwordd = $scope.Pass;
		var emaild = $scope.email;
		var Streetd =$scope.Street;
		var StreetNumberd =$scope.StreetNumber;
		var Cityd =$scope.City;
		var Zipcoded =$scope.Zipcode;
		var Telephoned =$scope.selected + "-" + $scope.Telephone;
		var Nicknamed =$scope.Nickname;
		var Descriptiond=$scope.Description;
		var parameter = JSON.stringify({username:usernamed, password:passwordd, type:"user", email:emaild, street:Streetd, streetnumber:StreetNumberd, city:Cityd, zipcode:Zipcoded, telephone:Telephoned, nickname:Nicknamed, description:Descriptiond});
		$http.post("http://localhost:8080/BooksForAll/signUp",parameter)
		.then(function(response) {
			$rootScope.user=response.data.username;
			$scope.userName="";
			$scope.Pass="";
			$scope.email="";
			$scope.Street="";
			$scope.StreetNumber="";
			$scope.City="";
			$scope.Zipcode="";
			$scope.selected="";
			$scope.Telephone="";
			$scope.Nickname="";
			$scope.Description="";
			$scope.$parent.changeSignup = false;
			$scope.$parent.changeNavbar = true;
		}, function(response) {
			var status = response.status;
			if(status=="410")
				$scope.content="There is already a user with that username in our system. please try again with a different user name";	    	  
		});
	};
});
app.controller('navbarAdminController', function($rootScope,$scope,$http,$window) {
	$scope.content=$rootScope.user;
	$scope.welcome = function(){
		$scope.changeWelcomeAd=true;
		$scope.changeViewUsersAd=false;
		$scope.changeBrowseBooksAd=false;
		$scope.changeViewPurchasesAd=false;
		$scope.changeViewReviewsAd=false;
		$('li.active').removeClass('active');
		$('a[ng-click="welcome()"]').closest('li').addClass('active');
	}
	$scope.viewUsers = function(){
		$scope.changeWelcomeAd=false;
		$scope.changeViewUsersAd=true;
		$scope.changeBrowseBooksAd=false;
		$scope.changeViewPurchasesAd=false;
		$scope.changeViewReviewsAd=false;
		$('li.active').removeClass('active');
		$('a[ng-click="viewUsers()"]').closest('li').addClass('active');
	}
	$scope.browseBooks = function(){
		$scope.changeWelcomeAd=false;
		$scope.changeViewUsersAd=false;
		$scope.changeBrowseBooksAd=true;
		$scope.changeViewPurchasesAd=false;
		$scope.changeViewReviewsAd=false;
		$('li.active').removeClass('active');
		$('a[ng-click="browseBooks()"]').closest('li').addClass('active');
	}
	$scope.viewPurchases = function(){
		$scope.changeWelcomeAd=false;
		$scope.changeViewUsersAd=false;
		$scope.changeBrowseBooksAd=false;
		$scope.changeViewPurchasesAd=true;
		$scope.changeViewReviewsAd=false;
		$('li.active').removeClass('active');
		$('a[ng-click="viewPurchases()"]').closest('li').addClass('active');
	}
	$scope.viewReviews = function(){
		$scope.changeWelcomeAd=false;
		$scope.changeViewUsersAd=false;
		$scope.changeBrowseBooksAd=false;
		$scope.changeViewPurchasesAd=false;
		$scope.changeViewReviewsAd=true;
		$('li.active').removeClass('active');
		$('a[ng-click="viewReviews()"]').closest('li').addClass('active');
	}
	$scope.logout = function(){
		$scope.changeWelcomeAd=false;
		$scope.changeViewUsersAd=false;
		$scope.changeMyBooksAd=false;
		$scope.changeBrowseBooksAd=false;
		$scope.changeViewPurchasesAd=false;
		$scope.changeViewReviewsAd=false;
		$rootScope.user="";
		$scope.$parent.$parent.$parent.changeNavbar=false;
		$scope.$parent.$parent.$parent.changeLogin=true;
	}
});
app.directive('tooltip', function(){
    return {
        link: function(scope, element, attrs){
            $(element).hover(function(){
                // on mouseenter
                $(element).tooltip('show');
            }, function(){
                // on mouseleave
                $(element).tooltip('hide');
            });
        }
    };
});
app.controller('book', function($rootScope,$scope,$http,$window){
	$scope.likeimg="images/like.png";
	$scope.send=false;
	$scope.bookname = $rootScope.chosenBook;
	$scope.username = $rootScope.user;
	var usr=$scope.username;
	var bookname = $scope.bookname;
	var parameter = JSON.stringify({username:usr,bookname:bookname});
	$scope.PurchasedOrNot=function(){
		return false;
	}
	var bookparameter = JSON.stringify({name:bookname, image:"", description:"",price:""});
	$http.post("http://localhost:8080/BooksForAll/browseBooksLikesServlet",bookparameter)
	.then(function(response) {
		$scope.userInfolist = response.data;
		var nickNames="";
		for(x in $scope.userInfolist){
			if(x==$scope.userInfolist.length-1)
			nickNames += $scope.userInfolist[x].nickname;
			else{
			nickNames += $scope.userInfolist[x].nickname + ", ";
			}
		}
		$scope.likes=nickNames;
		$scope.numberOfLIKes=$scope.userInfolist.length;
	}, function(){});
//-----------------Browse book details-----------------------
	$http.post("http://localhost:8080/BooksForAll/BrowseBookByNameServlet",bookparameter)
	.then(function(response) {
		$scope.price = response.data.price;
		$scope.description = response.data.description;
		$scope.image= "books/" + response.data.name + "_files/" + response.data.image + "";
	}, function(){});
	//-----------------Browse reviews -----------------------
	$http.post("http://localhost:8080/BooksForAll/BrowseReviewsByBookServlet",bookparameter)
	.then(function(response) {
		$scope.reviewsList = response.data;
	}, function(){});
	
	
	$scope.buy = function(){
		$scope.changeBookPage=false
		$scope.changeBuyBook=true
	}
	//-----------------Add like-----------------------
	$scope.like = function(){
			$http.post("http://localhost:8080/BooksForAll/addLikeServlet",parameter)
			.then(function(response) {
				$scope.userInfolist = response.data;
				var nickNames="";
				for(x in $scope.userInfolist){
					if(x==$scope.userInfolist.length-1)
					nickNames += $scope.userInfolist[x].nickname;
					else{
					nickNames += $scope.userInfolist[x].nickname + ", ";
					}
				}
				$scope.likes=nickNames;
				$scope.numberOfLIKes=$scope.userInfolist.length;
			},function(){});
	}
//-------------------Add review----------------------
	$scope.ifButtonClicked = function(){
		$scope.send=!$scope.send;
	}
	$scope.addReview = function(){
		var userReview=$scope.$userEnterReview;
		var reviewJson = JSON.stringify({bookname:bookname, nickname:"", review:userReview, approved:"no"});
		$http.post("http://localhost:8080/BooksForAll/AddReviewsServlet",reviewJson)
		.then(function(response) {
			$scope.responseWait = "Thanks for your response, your response is awaiting for approval";},
			function(){});
	}
//-----------------Read book-------------------------
	$scope.readBook = function(){
	
	}
});
