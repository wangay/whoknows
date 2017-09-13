'use strict';
//用户登陆后，看到的topic(问题)列表（只显示第一个）

angular.module('wkTopic').controller('TopicCtrl',
		function ($scope, $rootScope, $location, $route, $http, $window, UserService, LocalStorageService) {
			// if (!UserService.isSignedIn()) {
			// 	//没登录，路由到login
			// 	LocalStorageService.put('LastPage', $location.path());
			// 	$location.path("/login");
			// 	return;
			// }

			var currentPage = 1;
			$scope.topicLists = [];
			$scope.hideReadMore = false;
			$scope.noTagWarn = false;

			var initData = function () {
				currentPage = 1;
				$scope.topicLists = [];
				$scope.hideReadMore = false;
			}
			var loadTopic = function () {
				$http.get("/tag/home/" + $scope.currentTag.id + "/" + currentPage).success(function (data) {
					if (data.topicResults != null && data.topicResults.length > 0) {
						_.each(data.topicResults, function (result) {
							$scope.topicLists.push(result);
						})
						currentPage += 1;
					} else {
						$scope.hideReadMore = true;
					}
				}).error(function () {
					$scope.hideReadMore = true;
				});
			}

			$scope.loadMore = function () {
				loadTopic();
			}

			$scope.loadTopicByTag = function (tag) {
				$scope.currentTag = tag;
				initData();
				loadTopic();
			}


			var init = function () {
				$http.get("/user/follow/list/tag").success(function (data) {
					if (data != null && data.length > 0) {
						$scope.tags = data;
						//赋值给topicLists，用于页面当前要展示哪个tag下的内容
						//这里默认显示所关注的所有tag中的第一个
						$scope.loadTopicByTag($scope.tags[0])
					} else {
						$scope.hideReadMore = true;
						$scope.noTagWarn = true;
					}
				}).error(function () {
					$scope.hideReadMore = true;
					$scope.noTagWarn = true;
				});
			}

			init();

		});
