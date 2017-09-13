
'use strict';

//index.jsp的登录页面，用路由加载，并且指定了登录页面的controller
//访问localhost:8080/p/#/login就是在路由，由这里处理
var wkCommon = angular.module('wkLogin', ['ngRoute']).config(function ($routeProvider, $logProvider) {
	$routeProvider.when('/login', {
		templateUrl: 'app/login/loginPage',
		controller: 'LoginCtrl',
		data: {
			standalonePage: true
		}
	});
});

