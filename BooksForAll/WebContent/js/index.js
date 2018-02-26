/* Our main application module */
var app = angular.module('login', []);

	/* The startup controller */
	app.controller('bfaController', function($rootScope,$scope,$http,$window) {
	});
	
	/* The login controller */
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
				if(response.data.type=="user") 	// Checks the user type
					$scope.changeNavbar=true;
				else
					$scope.changeNavbarAdmin=true;
			}, function(response) { 
				var status = response.status;
				if(status=="405"){ 			// Wrong username
					$("#myModal").modal('show');
					$scope.content="There is no such user in our system. you can try again or sign up and \"book\" yourself a seat in the spectacular ride that is our website!";
				}
				else if(status=="406"){ 	// Wrong password
					$("#myModal").modal('show');
					$scope.content="The password is incorrect, please try again";
				}    	  
			});
		};
		$scope.signup = function(){ 	// Switches to the signup page
			$scope.changeLogin=false;
			$scope.changeSignup=true;
		}
	});
	
	/* The welcome controller */
	app.controller('welcomeController', function($rootScope,$scope,$http,$window) {
		var usr = $rootScope.user;
		$scope.content=usr;
		var parameter = JSON.stringify({username:usr, password:"", type:""});
		$http.post("http://localhost:8080/BooksForAll/myBooksServlet", parameter) // Query of the books owned by the user
		.then(function(response) {
			$scope.myBooksList = response.data; // The user's book list
		}, function(){});
	});

	/* The welcome admin controller */
	app.controller('welcomeAdminController', function($rootScope,$scope,$http,$window) {
		var usr = $rootScope.user;
		$scope.content=usr;
	});
	
	/* The navbar controller */
	app.controller('navbarController', function($rootScope,$scope,$http,$window) {
		$scope.content=$rootScope.user;
		$scope.welcome = function(){		// The Welcome tab
			$scope.changeWelcome=true;
			$scope.changeMyBooks=false;
			$scope.changeBrowseBooks=false;
			$('li.active').removeClass('active');	// Removes the active class from the active tab and assigns it to this tab
			$('a[ng-click="welcome()"]').closest('li').addClass('active');
		}
		$scope.myBooks = function(){		// The MyBooks tab
			$scope.changeWelcome=false;
			$scope.changeMyBooks=true;
			$scope.changeBrowseBooks=false;
			$('li.active').removeClass('active');	// Removes the active class from the active tab and assigns it to this tab
			$('a[ng-click="myBooks()"]').closest('li').addClass('active');
		}
		$scope.browseBooks = function(){	// The Browse Books tab
			$scope.changeWelcome=false;
			$scope.changeMyBooks=false;
			$scope.changeBrowseBooks=true;
			$('li.active').removeClass('active');	// Removes the active class from the active tab and assigns it to this tab
			$('a[ng-click="browseBooks()"]').closest('li').addClass('active');
		}
		$scope.logout = function(){			// The Logout tab
			$scope.changeWelcome=false;
			$scope.changeMyBooks=false;
			$scope.changeBrowseBooks=false;
			$rootScope.user="";
			$scope.$parent.$parent.$parent.changeNavbar=false;
			$scope.$parent.$parent.$parent.changeLogin=true;	// Displays the login page
		}
	});
	
	/* The browse books controller */
	app.controller('browseBooksController', function($rootScope,$scope,$http,$window) {
		$scope.toBook = function(param){	// Displays the chosen book page
			$rootScope.chosenBook=param;
			$scope.changeBrowse=false;
			$scope.changeBook=true;
		};
		$http.post("http://localhost:8080/BooksForAll/browseBooksServlet")
		.then(function(response) {
			$scope.bookslist = response.data;
		}, function(){});
	});
	
	/* The my books controller */
	app.controller('myBooksController', function($rootScope,$scope,$http,$window) {
		$scope.toMyBook = function(param){	// Displays the chosen book page
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
	
	/* The sign up controller */
	app.controller('signUpController', function($rootScope,$scope,$http,$window) {
		$scope.sevenNumbers = /^[0-9]{7}$/;			// Input validation
		$scope.lettersOnly =/^[a-zA-Z ]{1,100}$/;
		$scope.onlyNumbers = /^[1-9][0-9]*$/;
		$scope.emailValid =/^.+@.+\\..+$/;
		$scope.signUpApp = function(){				// Signup
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
				else if(status=="433"){
					$("#myModalSignUp").modal('show');
					$scope.content="Sign up failed. please try again";	
				}
			});
		};
		$scope.toLogin= function(){		// Back to login page
			$scope.$parent.$parent.$parent.changeSignup=false;
			$scope.$parent.$parent.$parent.changeLogin=true;
		}
	});
	
	/* The navbar admin controller */
	app.controller('navbarAdminController', function($rootScope,$scope,$http,$window) {
		$scope.content=$rootScope.user;
		$scope.welcome = function(){				// The Welcome tab
			$scope.changeWelcomeAd=true;
			$scope.changeViewUsersAd=false;
			$scope.changeBrowseBooksAd=false;
			$scope.changeViewPurchasesAd=false;
			$scope.changeViewReviewsAd=false;
			$('li.active').removeClass('active');	// Removes the active class from the active tab and assigns it to this tab
			$('a[ng-click="welcome()"]').closest('li').addClass('active');
		}
		$scope.viewUsers = function(){				// The View Users tab
			$scope.changeWelcomeAd=false;
			$scope.changeViewUsersAd=true;
			$scope.changeBrowseBooksAd=false;
			$scope.changeViewPurchasesAd=false;
			$scope.changeViewReviewsAd=false;
			$('li.active').removeClass('active');	// Removes the active class from the active tab and assigns it to this tab
			$('a[ng-click="viewUsers()"]').closest('li').addClass('active');
		}
		$scope.browseBooks = function(){			// The Browse Books tab
			$scope.changeWelcomeAd=false;
			$scope.changeViewUsersAd=false;
			$scope.changeBrowseBooksAd=true;
			$scope.changeViewPurchasesAd=false;
			$scope.changeViewReviewsAd=false;
			$('li.active').removeClass('active');	// Removes the active class from the active tab and assigns it to this tab
			$('a[ng-click="browseBooks()"]').closest('li').addClass('active');
		}
		$scope.viewPurchases = function(){			// The View Purchases tab
			$scope.changeWelcomeAd=false;
			$scope.changeViewUsersAd=false;
			$scope.changeBrowseBooksAd=false;
			$scope.changeViewPurchasesAd=true;
			$scope.changeViewReviewsAd=false;
			$('li.active').removeClass('active');	// Removes the active class from the active tab and assigns it to this tab
			$('a[ng-click="viewPurchases()"]').closest('li').addClass('active');
		}
		$scope.viewReviews = function(){			// The View Reviews tab
			$scope.changeWelcomeAd=false;
			$scope.changeViewUsersAd=false;
			$scope.changeBrowseBooksAd=false;
			$scope.changeViewPurchasesAd=false;
			$scope.changeViewReviewsAd=true;
			$('li.active').removeClass('active');	// Removes the active class from the active tab and assigns it to this tab
			$('a[ng-click="viewReviews()"]').closest('li').addClass('active');
		}
		$scope.logout = function(){					// The Logout tab
			$scope.changeWelcomeAd=false;
			$scope.changeViewUsersAd=false;
			$scope.changeMyBooksAd=false;
			$scope.changeBrowseBooksAd=false;
			$scope.changeViewPurchasesAd=false;
			$scope.changeViewReviewsAd=false;
			$rootScope.user="";
			$scope.$parent.$parent.$parent.changeNavbarAdmin=false;
			$scope.$parent.$parent.$parent.changeLogin=true;		// Displays the login page
		}
	});
	
	/* Used for the tooltip */
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

	/* The book controller */
	app.controller('book', function($rootScope,$scope,$http,$window){
		$scope.likeimg="images/like.png";
		$scope.dislikeimg="images/dislike.jpg";
		var bookName = $rootScope.chosenBook;
		var usr = $rootScope.user;
		var likeParameter = JSON.stringify({username:usr,bookname:bookName});
		var userParameter = JSON.stringify({username:usr,password:"",type:""});
		$http.post("http://localhost:8080/BooksForAll/UserPageServlet",userParameter) // The response holds the user's info
		.then(function(response) {
			$scope.userNickname=response.data.nickname;
		},function(response){});
	
		$http.post("http://localhost:8080/BooksForAll/IsLikedServlet",likeParameter) // Checks if the user liked the book or not
		.then(function(response) {
			$scope.changeLike=false;
			$scope.changeDislike=true;
		},function(response){
			var status = response.status;
			if(status="499"){
				$scope.changeLike=true;
				$scope.changeDislike=false;
			}
		});
	
		var bookParameter = JSON.stringify({name:bookName,image:"",description:"",price:""});
		$http.post("http://localhost:8080/BooksForAll/BookDetailsServlet",bookParameter) // The response holds the book's details
		.then(function(response) {
			$scope.bookDetails=response.data;
			$scope.price=response.data.price;
		},function(response){});

		$http.post("http://localhost:8080/BooksForAll/browseBooksLikesServlet",bookParameter) // The response holds a list of all the info of users that liked the book
		.then(function(response) {
			$scope.nicknames=""
			$scope.userInfoList = response.data;
			for(x in $scope.userInfoList){
				$scope.nicknames= $scope.nicknames + $scope.userInfoList[x].nickname +"; "; // A string of the user's nicknames
			}
			$scope.numberOfLIKes=$scope.userInfoList.length;
		}, function(response){});
	
		var purchaseParameter = JSON.stringify({username:usr,bookname:bookName,price:""});
		$http.post("http://localhost:8080/BooksForAll/FindPurchasesByNameAndBookServlet",purchaseParameter) // Checks if the user bought the book or not
		.then(function(response) {
			$scope.changeNotBought=true;	
			$scope.changeBought=false;
		},function(response){
			$scope.changeNotBought=false;
			$scope.changeBought=true;
		});
	
		$scope.buy = function(){	// Buy the book
			$rootScope.bookPrice = $scope.price;
			$scope.changeBookPage=false
			$scope.changeBuyBook=true	// Switches to the payment page
		}
	
	
		$scope.like = function(){	// Like the book
			$http.post("http://localhost:8080/BooksForAll/addLikeServlet",likeParameter)	
			.then(function(response) {
				var num = $scope.numberOfLIKes;
				$scope.numberOfLIKes=parseInt(num)+1;
				$http.post("http://localhost:8080/BooksForAll/browseBooksLikesServlet",bookParameter)	// The response holds an updated list of all the info of users that liked the book
				.then(function(response) {
					$scope.nicknames="";
					$scope.userInfoList = response.data;
					for(x in $scope.userInfoList){
						$scope.nicknames= $scope.nicknames + $scope.userInfoList[x].nickname +"; ";
					}
					$('#like').tooltip('hide').attr('title', $scope.nicknames).tooltip('fixTitle');	// Used to change the content of the tooltip after the like
					$('#dislike').tooltip('hide').attr('title', $scope.nicknames).tooltip('fixTitle');
					$scope.changeLike=false;
					$scope.changeDislike=true;
				},function(response){});
			},function(response){});
		}
		$scope.dislike = function(){	// Unlike the book
			$http.post("http://localhost:8080/BooksForAll/removeLikeServlet",likeParameter)
			.then(function(response) {
				var num = $scope.numberOfLIKes;
				$scope.numberOfLIKes=parseInt(num)-1;
				$http.post("http://localhost:8080/BooksForAll/browseBooksLikesServlet",bookParameter)	// The response holds an updated list of all the info of users that liked the book
				.then(function(response) {
					$scope.nicknames="";
					$scope.userInfoList = response.data;
					for(x in $scope.userInfoList){
						$scope.nicknames= $scope.nicknames + $scope.userInfoList[x].nickname +"; ";
					}	
					$('#like').tooltip('hide').attr('title', $scope.nicknames)	// Used to change the content of the tooltip after the unlike
					.tooltip('fixTitle');
					$('#dislike').tooltip('hide').attr('title', $scope.nicknames)
					.tooltip('fixTitle');
					$scope.changeLike=true;
					$scope.changeDislike=false;
				},function(response){});
			},function(response){});
		}

		$http.post("http://localhost:8080/BooksForAll/BrowseReviewsByBookServlet",bookParameter) // The response holds a list of all the book's reviews
		.then(function(response) {
			$scope.reviewsList = response.data;
		}, function(){});
	
		$scope.showRev=function(){	// Used for the collapsable list of reviews
			$('#rev').collapse('toggle');
		}
	
		$scope.addReview = function(){	// Submit a review
			var userReview=$scope.reviewText;
			var nick= $scope.userNickname;
			var reviewJson = JSON.stringify({bookname:bookName, nickname:nick, review:userReview, approved:"no"});
			$http.post("http://localhost:8080/BooksForAll/AddReviewsServlet",reviewJson)
			.then(function(response) {
				$scope.content = "Thank you for your review, your review is now awaiting approval";
				$('#myModal').modal({show:true})
				$scope.reviewText="";
			},function(){});
		}
		$scope.readBook = function(){	// Read the book
			$scope.changeBookPage=false
			$scope.changeReadBook=true;	// Switches to the read book page
		}
	});
	
	/* The view users controller */
	app.controller('viewUsersController', function($rootScope,$scope,$http,$window) {
		$http.post("http://localhost:8080/BooksForAll/ViewUsersServlet")	// The response holds a list of all the users
		.then(function(response) {
			$scope.userlist=response.data;	
		},function(){});
		$(document).ready(function(){	// Used for the filter
			$("#myInput").on("keyup", function() {
				var value = $(this).val().toLowerCase();
				$("#myList li").filter(function() {
					$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
				});
			});
		});
		$scope.viewUser = function(param){ // Switches to the selected user's page
			$rootScope.userPageView=param;
			$scope.changeViewUsers = false;
			$scope.changeUserPage = true;
		}
		$scope.sure=function(param){	// Delete a user
			$('#myModalUser').modal({show:true});	// A modal that prevents the user from deleting a user by accident
			$scope.removeUser = function(){
				var parameter = JSON.stringify({username:param, password:"", type:"user"});
				$http.post("http://localhost:8080/BooksForAll/RemoveUserServlet",parameter)
				.then(function(response) {
					$("#" + param).remove();	// Removes the deleted user from the list
				},function(){});
			}
		}
	});
	
	/* The user page controller */
	app.controller('userPageController', function($rootScope,$scope,$http,$window) {
		var usr = $rootScope.userPageView;
		var parameter = JSON.stringify({username:usr, password:"", type:"user"});
		$http.post("http://localhost:8080/BooksForAll/UserPageServlet",parameter)
		.then(function(response) {
			$scope.userDetails=response.data;	
		},function(){});
	});
	
	/* The view purchases controller */
	app.controller('viewPurchasesController', function($rootScope,$scope,$http,$window) {
		$(document).ready(function(){	// Used for the filter
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
	
	/* The view reviews controller */
	app.controller('viewReviewsController', function($rootScope,$scope,$http,$window) {
		$http.post("http://localhost:8080/BooksForAll/ViewReviewsServlet")
		.then(function(response) {
			$scope.reviewList=response.data;	
		},function(){});
		$scope.approveReview = function(bname,nname,rev){ // Approve review
			var parameter = JSON.stringify({bookname:bname, nickname:nname, review:rev, approved:"no"});
			$http.post("http://localhost:8080/BooksForAll/ApproveReviewServlet", parameter)
			.then(function(response) {	
				$("#" + rev).remove();	// Removes the approved review from the list
			},function(){});
		}
	});

	/* The payment controller */
	app.controller('payment', function($rootScope,$scope,$http,$window) {
		$scope.price = $rootScope.bookPrice;
		$scope.threeNumbers = /^[0-9]{3}$/;			// Input validation
		$scope.twoNumbers = /^[0-9][0-9]$/;
		$scope.lettersOnly =/^[a-zA-Z ]{1,100}$/;
		$scope.amexValidation = /^[3][4][0-9]{13}$/;
		$scope.visaValidation = /^[4][0-9]{15}$/;
		var date = new Date();
		var month = date.getMonth()+1;
		var year =date.getYear()+1900;
		$scope.currentYear=year;
		$scope.maxYear=year+6;
		if(month<10){	
			$scope.currentMonth="0" + month;
		}
		else{
			$scope.currentMonth=month;
		}
		$scope.creditcard="amex";
		$scope.submitPayment=function(){	// Submit payment
			var price = $scope.price;
			var bookname = $rootScope.chosenBook;
			var usr=$rootScope.user;
			var purchaseJson = JSON.stringify({username:usr, bookname:bookname, price:price });
			$http.post("http://localhost:8080/BooksForAll/AddPurchasesServlet",purchaseJson)
			.then(function(response) {
				$("#myModaltwo").modal('show');
				$scope.content="Thank you for your purchase, have a good day";
				$scope.moveBrowse=function(){	// Return to browse books
					$("#myModaltwo").modal('hide');
					$scope.$parent.$parent.$parent.$parent.$parent.$parent.changeBrowse=true;
					$scope.$parent.$parent.$parent.$parent.$parent.$parent.changeBook=false;
				}
			},function(){});				
		}
	});

	/* The browse books admin controller */
	app.controller('browseBooksAdminController', function($rootScope,$scope,$http,$window) {
		$scope.toBook = function(param){	// Displays the chosen book page
			$rootScope.chosenBook=param;
			$scope.changeBrowseAd=false;
			$scope.changeBookAd=true;
		};
		$http.post("http://localhost:8080/BooksForAll/browseBooksServlet")
		.then(function(response) {
			$scope.bookslist = response.data;
		}, function(){});
	});

	/* The book admin controller */
	app.controller('bookAdmin', function($rootScope,$scope,$http,$window) {
		$scope.likeimg="images/like.png";
		var bookName = $rootScope.chosenBook;
		var bookParameter = JSON.stringify({name:bookName,image:"",description:"",price:""});
		$http.post("http://localhost:8080/BooksForAll/BookDetailsServlet",bookParameter)	// The response holds the book's details
		.then(function(response) {
			$scope.bookDetails=response.data;
			$scope.price=response.data.price;
		},function(response){});

		$http.post("http://localhost:8080/BooksForAll/browseBooksLikesServlet",bookParameter)	// The response holds a list of all the info of users that liked the book
		.then(function(response) {
			$scope.nicknames=""
				$scope.userInfoList = response.data;
			for(x in $scope.userInfoList){
				$scope.nicknames= $scope.nicknames + $scope.userInfoList[x].nickname +"; ";	// A string of the user's nicknames
			}
			$scope.numberOfLIKes=$scope.userInfoList.length;
		}, function(response){});
		$http.post("http://localhost:8080/BooksForAll/BrowseReviewsByBookServlet",bookParameter)	// The response holds a list of all the book's reviews
		.then(function(response) {
			$scope.reviewsList = response.data;
		}, function(){});
	
		$scope.showRev=function(){
			$('#revAd').collapse('toggle');		// Used for the collapsable list of reviews
		}
		$(document).ready(function(){		// Used for the likes tooltip
			$(".dropmenu").mouseover(function(){
				$(".dropdown-content").show();
			});
			$(".dropmenu").mouseout(function(){
				$(".dropdown-content").hide();
			});
		});
	 
		$scope.moveUser=function(usr){	// Switches to the user's page
			$rootScope.userPageView=usr;
			$scope.changeBookPage=false;
			$scope.changeUserPage=true;
		}
	});
	
	/* The read book controller */
	app.controller('readBookController', function($rootScope,$scope,$http,$window) {
		var bookName = $rootScope.chosenBook;
		var usr = $rootScope.user;
		$scope.content=bookName;
		var parameter = JSON.stringify({username:usr, bookname:bookName, location:"" });
		$scope.startScroll = function() {  // Moves to the last scrolling location
			$http.post("http://localhost:8080/BooksForAll/LoadLocationServlet",parameter)
			.then(function(response) {
				$window.scrollTo(0, response.data.location);
			}, function(){});
		}
		$window.onbeforeunload = function(event) {		// Saves the location in the book when the window is closed
			saveScrollLocation($window.pageYOffset)
		}
		$scope.$on("$destroy", function() {		// Saves the location in the book when the controller is closed
			saveScrollLocation($window.pageYOffset)
		})
		saveScrollLocation= function(param){	// Save the scrolling location in the book
			var saveParameter = JSON.stringify({username:usr, bookname:bookName, location:param });
			$http.post("http://localhost:8080/BooksForAll/SaveLocationServlet",saveParameter)
			.then(function(response) {
			}, function(){});
		}
		$scope.returnToBook= function() // Back to the book page
		{
			$scope.$parent.$parent.$parent.changeReadBook=false;
			$scope.$parent.$parent.$parent.changeBookPage=true;
		}
	});
