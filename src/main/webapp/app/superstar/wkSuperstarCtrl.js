'use strict';

angular.module('wkSuperstar').controller('wkSuperstar.SuperstarCtrl',
	function ($scope, $rootScope, $location, $route, $window) {
		$scope.loginIn = true; 
		$scope.isActive = function (viewLocation) {
		     var active = (viewLocation === $location.path());
		     return active;
		};
		console.log("wkCommon- wkSuperStar.SuperStarCtrl  load.")
	});
