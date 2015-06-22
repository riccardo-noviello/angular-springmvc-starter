//Define an angular module for our app
var myApp = angular.module('myApp', [ 'ngRoute', 'myAppControllers',
		'restangular' ]);

// Define Routing for app
myApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'views/welcome.html'
	}).when('/person/list', {
		templateUrl : 'views/persons/list.html',
		controller : 'PersonListController'
	}).when('/person/new', {
		templateUrl : 'views/persons/create.html',
		controller : 'PersonCreateController'
	}).when('/person/:personId', {
		templateUrl : 'views/persons/detail.html',
		controller : 'PersonDetailController'
	}).otherwise({
		redirectTo : '/'
	});
} ]);

myApp.config([ "RestangularProvider", function(RestangularProvider) {
	RestangularProvider.setBaseUrl('api/v1');
} ]);

myApp.config([ "RestangularProvider", function(RestangularProvider) {
	RestangularProvider.setRestangularFields({
		id : "_id"
	});
} ]);
