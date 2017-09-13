
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
		//index.jsp默认进/topic的路由,加载 app/topic/topicPage，
		// 然后发现没登录，登录后再跳回/topic
		redirectTo: '/topic'
	});
});

