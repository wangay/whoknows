
'use strict';

var wkCommon = angular.module('wkCommon', ['ngRoute']).config(function ($routeProvider, $logProvider) {
	$routeProvider.when('/selfPage', {
		templateUrl: 'app/users/selfHomePage',
		controller: 'SelfHomeCtrl',
		data: {
			standalonePage: true
		}
	}).when('/topic', {
		templateUrl: 'app/topic/topicPage',
		controller: 'TopicCtrl',
		data: {
			standalonePage: true
		}
	}).when('/tagDetail/:tagId', {
        templateUrl: 'app/tags/tagDetailPage',
        controller: 'TagDetailCtrl'
    }).when('/setting', {
		templateUrl: 'app/setting/settingPage',
		controller: 'SettingCtrl',
		data: {
			standalonePage: true
		}
	}).when('/error', {
		templateUrl: 'error',
		data: {
			standalonePage: true
		}
	}).otherwise({
		//默认寻找最新tag，显示最新产生的topic
		redirectTo:'tagDetail/-1'
	});
});

