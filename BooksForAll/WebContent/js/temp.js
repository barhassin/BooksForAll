var app = angular.module('books', []);
// change this to the real app name insted books
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
	$scope.numberOfLIKes=0;
	$scope.likeimg="../images/like.png";
	//$scope.bookname = $rootScope.bookname;
	//$scope.username = $rootScope.username;
	//var usr=$scope.username;
	//var bookname = $rootScope.bookname; need to add this paramter to post 
	//var parameter = JSON.stringify({username:usr,bookname:bookname});
	var usr="user";
	var bookname = "Peter Poodle, by William Henry Bradley, A Project Gutenberg eBook"
	var parameter = JSON.stringify({username:usr,bookname:bookname});
	$scope.PurchasedOrNot=function($rootScope,$scope,$http,$window){
		return true;
	}
	var boookoko = JSON.stringify({name:bookname, image:"", description:"",price:""});
	$http.post("http://localhost:8080/BooksForAll/browseBooksLikesServlet",boookoko)
	.then(function(response) {
		$scope.contnent="bdsafsaf";
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

	$http.post("http://localhost:8080/BooksForAll/BrowseBookByNameServlet",boookoko)
	.then(function(response) {
		$scope.price = response.data.price;
		$scope.description = response.data.description;
		$scope.image= "../books/" + response.data.name + "_files/" + response.data.image + "";
	}, function(){});

	$scope.buy = function(){
		
	}
	//var like=false;
	$scope.like = function(){
		//if(!like){
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
		//}
		//else{
		//}
	}
});
