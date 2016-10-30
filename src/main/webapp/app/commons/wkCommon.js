'use strict';

angular.module('wkCommon').controller('wkCommon.appCtrl',
	function ($scope, $rootScope, $location, $route, $window, LocalStorageService, UserService) {
		$scope.loginIn = false;
		UserService.initialize().then(function () {
			$scope.loginIn = UserService.isSignedIn();
			if($scope.loginIn){
				initUser();
			}
		});
		
		
		$rootScope.$on('event:login:success', function () {
			$scope.loginIn = true;
			initUser();
			if (LocalStorageService.get('LastPage')) {
				var lastPage = LocalStorageService.get('LastPage');
				if (lastPage) {
					LocalStorageService.remove('LastPage');
					$location.path(lastPage);
				}
			} else {
				$location.path('/');
			}
			
		});
		
		
		function initUser(){
			$scope.user = UserService.getCurrent();
			if($scope.user.picture == undefined || $scope.user.picture == null){
				$scope.user.picture = "../../images/people-no-img-default.png";
			}
		}
		$scope.isActive = function (viewLocation) {
		     var active = (viewLocation === $location.path());
		     return active;
		};
		
		$scope.createQuestion = function(){
			$location.path('/creteTopic');
		}
		console.log("wkCommon- wkCommon.appCtrl load.")
	});
