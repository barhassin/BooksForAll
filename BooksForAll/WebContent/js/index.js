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
	$scope.toMyBook = function(param){
		$rootScope.chosenBook=param;
		$scope.changeMyBooks=false;
		$scope.changeBook=true;
	};
	var usr = $rootScope.user;
	var parameter = JSON.stringify({username:usr, password:"", type:""});
	$http.post("http://localhost:8080/BooksForAll/myBooksServlet", parameter)
	.then(function(response) {
		$scope.mybookslist = response.data;
	}, function(){});
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
		if($scope.Description)
			var Descriptiond=$scope.Description;
		else
			var Descriptiond="No description";
		if($scope.photo)
			var Photod=$scope.photo;
		else
			var Photod="male.jpg";
		var parameter = JSON.stringify({username:usernamed, password:passwordd, type:"user", email:emaild, street:Streetd, streetnumber:StreetNumberd, city:Cityd, zipcode:Zipcoded, telephone:Telephoned, nickname:Nicknamed, description:Descriptiond, photo:Photod});
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
			$scope.photo="";
			$scope.$parent.$parent.$parent.changeSignup = false;
			$scope.$parent.$parent.$parent.changeNavbar = true;
		}, function(response) {
			var status = response.status;
			if(status=="410"){
				$("#myModalSignUp").modal('show');
				$scope.content="There is already a user with that user name in our system. please try again with a different user name";
			}
			else if(status=="411"){
				$("#myModalSignUp").modal('show');
				$scope.content="There is already a user with that nickname in our system. please try again with a different nickname";
			}
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
		$scope.$parent.$parent.$parent.changeNavbarAdmin=false;
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
	$scope.dislikeimg="images/dislike.jpg";
	var bookName = $rootScope.chosenBook;
	var usr = $rootScope.user;
	var likeParameter = JSON.stringify({username:usr,bookname:bookName});
	var userParameter = JSON.stringify({username:usr,password:"",type:""});
	$http.post("http://localhost:8080/BooksForAll/UserPageServlet",userParameter)
	.then(function(response) {
		$scope.userNickname=response.data.nickname;
	},function(response){});
	
	$http.post("http://localhost:8080/BooksForAll/IsLikedServlet",likeParameter)
	.then(function(response) {
		$scope.changeLike=false;
		$scope.changeDislike=true;
	},function(response){
		var status = response.status;
		if(status="499")
			$scope.changeLike=true;
			$scope.changeDislike=false;
	});
	
	var bookParameter = JSON.stringify({name:bookName,image:"",description:"",price:""});
	$http.post("http://localhost:8080/BooksForAll/BookDetailsServlet",bookParameter)
	.then(function(response) {
		$scope.bookDetails=response.data;
		$scope.price=response.data.price;
	},function(response){});

	$http.post("http://localhost:8080/BooksForAll/browseBooksLikesServlet",bookParameter)
	.then(function(response) {
		$scope.nicknames=""
		$scope.userInfoList = response.data;
		for(x in $scope.userInfoList){
			$scope.nicknames= $scope.nicknames + $scope.userInfoList[x].nickname +"; ";
		}
		$scope.numberOfLIKes=$scope.userInfoList.length;
	}, function(response){});
	
	var purchaseParameter = JSON.stringify({username:usr,bookname:bookName,price:""});
	$http.post("http://localhost:8080/BooksForAll/FindPurchasesByNameAndBookServlet",purchaseParameter)
	.then(function(response) {
		$scope.changeNotBought=true;	
		$scope.changeBought=false;
	},function(response){
		$scope.changeNotBought=false;
		$scope.changeBought=true;
	});
	
	$scope.buy = function(){
		$rootScope.bookPrice = $scope.price;
		$scope.changeBookPage=false
		$scope.changeBuyBook=true
	}
	
	
	$scope.like = function(){
			$http.post("http://localhost:8080/BooksForAll/addLikeServlet",likeParameter)
			.then(function(response) {
				var num = $scope.numberOfLIKes;
				$scope.numberOfLIKes=parseInt(num)+1;
				$http.post("http://localhost:8080/BooksForAll/browseBooksLikesServlet",bookParameter)
				.then(function(response) {
					$scope.nicknames="";
					$scope.userInfoList = response.data;
					for(x in $scope.userInfoList){
						$scope.nicknames= $scope.nicknames + $scope.userInfoList[x].nickname +"; ";
					}
				$('#like').tooltip('hide').attr('title', $scope.nicknames).tooltip('fixTitle');
				$('#dislike').tooltip('hide').attr('title', $scope.nicknames).tooltip('fixTitle');
				$scope.changeLike=false;
				$scope.changeDislike=true;
				},function(response){});
			},function(response){});
	}
	$scope.dislike = function(){
		$http.post("http://localhost:8080/BooksForAll/removeLikeServlet",likeParameter)
		.then(function(response) {
			var num = $scope.numberOfLIKes;
			$scope.numberOfLIKes=parseInt(num)-1;
			$http.post("http://localhost:8080/BooksForAll/browseBooksLikesServlet",bookParameter)
			.then(function(response) {
				$scope.nicknames="";
				$scope.userInfoList = response.data;
				for(x in $scope.userInfoList){
					$scope.nicknames= $scope.nicknames + $scope.userInfoList[x].nickname +"; ";
				}	
			$('#like').tooltip('hide').attr('title', $scope.nicknames)
	          .tooltip('fixTitle');
			$('#dislike').tooltip('hide').attr('title', $scope.nicknames)
	          .tooltip('fixTitle');
			$scope.changeLike=true;
			$scope.changeDislike=false;
			},function(response){});
		},function(response){});
	}

	$http.post("http://localhost:8080/BooksForAll/BrowseReviewsByBookServlet",bookParameter)
	.then(function(response) {
		$scope.reviewsList = response.data;
	}, function(){});
	
	$scope.showRev=function(){
		$('#rev').collapse('toggle');
	}
	
	$scope.addReview = function(){
		var userReview=$scope.reviewText;
		var nick= $scope.userNickname;
		var reviewJson = JSON.stringify({bookname:bookName, nickname:nick, review:userReview, approved:"no"});
		$http.post("http://localhost:8080/BooksForAll/AddReviewsServlet",reviewJson)
		.then(function(response) {
			$scope.content = "Thank you for your review, your review is now awaiting approval";
			$('#myModal').modal({show:true})
			},function(){});
	}
	$scope.readBook = function(){
		$scope.changeBookPage=false
		$scope.changeReadBook=true;
	}
});
app.controller('viewUsersController', function($rootScope,$scope,$http,$window) {
	$http.post("http://localhost:8080/BooksForAll/ViewUsersServlet")
	.then(function(response) {
		$scope.userlist=response.data;	
	},function(){});
	$(document).ready(function(){
		  $("#myInput").on("keyup", function() {
		    var value = $(this).val().toLowerCase();
		    $("#myList li").filter(function() {
		      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		    });
		  });
		});
	$scope.viewUser = function(param){
		$rootScope.userPageView=param;
		$scope.changeViewUsers = false;
		$scope.changeUserPage = true;
	}
	
	$scope.sure=function(param){
		$('#myModalUser').modal({show:true});
		$scope.removeUser = function(){
			var parameter = JSON.stringify({username:param, password:"", type:"user"});
			$http.post("http://localhost:8080/BooksForAll/RemoveUserServlet",parameter)
			.then(function(response) {
				$rootScope.userPageView="";
			},function(){});
		}
	}
});
app.controller('userPageController', function($rootScope,$scope,$http,$window) {
	var usr = $rootScope.userPageView;
	var parameter = JSON.stringify({username:usr, password:"", type:"user"});
	$http.post("http://localhost:8080/BooksForAll/UserPageServlet",parameter)
	.then(function(response) {
		$scope.userDetails=response.data;	
	},function(){});
});
app.controller('viewPurchasesController', function($rootScope,$scope,$http,$window) {
	$(document).ready(function(){
		  $("#myInput").on("keyup", function() {
		    var value = $(this).val().toLowerCase();
		    $("#myTable tr").filter(function() {
		      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		    });
		  });
		});
	$http.post("http://localhost:8080/BooksForAll/ViewPurchasesServlet")
	.then(function(response) {
		$scope.purchaseList=response.data;	
	},function(){});
});
app.controller('viewReviewsController', function($rootScope,$scope,$http,$window) {
	$http.post("http://localhost:8080/BooksForAll/ViewReviewsServlet")
	.then(function(response) {
		$scope.reviewList=response.data;	
	},function(){});
	$scope.approveReview = function(bname,nname,rev){
		var parameter = JSON.stringify({bookname:bname, nickname:nname, review:rev, approved:"no"});
		$http.post("http://localhost:8080/BooksForAll/ApproveReviewServlet", parameter)
		.then(function(response) {	
		},function(){});
	}
});
app.controller('payment', function($rootScope,$scope,$http,$window) {
	$scope.price = $rootScope.bookPrice;
	$scope.threeNumbers = /^[0-9]{3}$/;
	$scope.twoNumbers = /^[0-9][0-9]$/;
	$scope.lettersOnly =/^[a-zA-Z ]{1,100}$/;
	$scope.amexValidation = /^[3][4][0-9]{13}$/;
	$scope.visaValidation = /^[4][0-9]{15}$/;
	var date = new Date();
    var month = date.getMonth()+1;
	var year =date.getYear()-100;
	$scope.currentYear=year;
	$scope.maxYear=year+6;
	$scope.currentMonth=month;
	$scope.submitPayment=function(){
		var price = $scope.price;
		var bookname = $rootScope.chosenBook;
		var usr=$rootScope.user;
		var purchaseJson = JSON.stringify({username:usr, bookname:bookname, price:price });
		$http.post("http://localhost:8080/BooksForAll/AddPurchasesServlet",purchaseJson)
		.then(function(response) {
			$("#myModaltwo").modal('show');
			$scope.content="Thank you for your purchase, have a good day";
			$scope.moveBrowse=function(){
				$scope.$parent.$parent.$parent.$parent.$parent.$parent.changeBrowse=true;
				$scope.$parent.$parent.$parent.$parent.$parent.$parent.changeBook=false;
				}
		},function(){});				
	}
});

app.controller('browseBooksAdminController', function($rootScope,$scope,$http,$window) {
	$scope.toBook = function(param){
		$rootScope.chosenBook=param;
		$scope.changeBrowseAd=false;
		$scope.changeBookAd=true;
	};
	$http.post("http://localhost:8080/BooksForAll/browseBooksServlet")
	.then(function(response) {
		$scope.bookslist = response.data;
	}, function(){});
});

app.controller('bookAdmin', function($rootScope,$scope,$http,$window) {
	$scope.likeimg="images/like.png";
	var bookName = $rootScope.chosenBook;
	var bookParameter = JSON.stringify({name:bookName,image:"",description:"",price:""});
	$http.post("http://localhost:8080/BooksForAll/BookDetailsServlet",bookParameter)
	.then(function(response) {
		$scope.bookDetails=response.data;
		$scope.price=response.data.price;
	},function(response){});

	$http.post("http://localhost:8080/BooksForAll/browseBooksLikesServlet",bookParameter)
	.then(function(response) {
		$scope.nicknames=""
		$scope.userInfoList = response.data;
		for(x in $scope.userInfoList){
			$scope.nicknames= $scope.nicknames + $scope.userInfoList[x].nickname +"; ";
		}
		$scope.numberOfLIKes=$scope.userInfoList.length;
	}, function(response){});
	$http.post("http://localhost:8080/BooksForAll/BrowseReviewsByBookServlet",bookParameter)
	.then(function(response) {
		$scope.reviewsList = response.data;
	}, function(){});
	
	$scope.showRev=function(){
		$('#revAd').collapse('toggle');
	}
	 $(document).ready(function(){
         $(".dropmenu").mouseover(function(){
             $(".dropdown-content").show();
         });
         $(".dropmenu").mouseout(function(){
             $(".dropdown-content").hide();
         });
     });
	 
	 $scope.moveUser=function(usr){
		 $rootScope.userPageView=usr;
		 $scope.changeBookPage=false;
		 $scope.changeUserPage=true;
		}
	
});