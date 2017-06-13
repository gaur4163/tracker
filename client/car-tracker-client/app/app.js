(function() {
    'use strict';
    angular.module('plunker', ['ngRoute', "ng-fusioncharts"]);


    angular.module('plunker')
        .config(moduleConfig);

    moduleConfig.$inject = ['$routeProvider', '$locationProvider'];

    function moduleConfig($routeProvider, $locationProvider) {
        $routeProvider
            .when('/cars', {
                templateUrl: 'views/car-list.tmpl.html',
                controller: 'CarListController',
                controllerAs: 'carListVm'
            })
            .when('/cars/:vin', {
                templateUrl: 'views/car-detail.tmpl.html',
                controller: 'CarDetailController',
                controllerAs: 'carDetailVm'
            })
            .when('/alerts', {
                templateUrl: 'views/alert-list.tmpl.html',
                controller: 'AlertsAllhighController',
                controllerAs: 'alertAllVm'
            })
            .when('/allAlertsVin/:vin', {
                templateUrl: 'views/alert-detail.tmpl.html',
                controller: 'AlertsDetailController',
                controllerAs: 'alertDetailVm'
            })
            .when('/plotFuelVol/:vin', {
                templateUrl: 'views/new.tmpl.html',
                controller: 'CarReadingDetailController',
                controllerAs: 'carReadingDetailVm'
            })
            .when('/plotSignal/:vin', {
                templateUrl: 'views/plot-range.tmpl.html',
                controller: 'CarPlotSignal',
                controllerAs: 'carPlotSignalVm'
            })
            .when('/plotEngRpm/:vin', {
                templateUrl: 'views/showReading.tmpl.html',
                controller: 'CarReadingEngineRpmDetailController',
                controllerAs: 'carReadingEngDetailVm'
            })
            .when('/plotFuelVoltimed/:vin/:timeslot', {
                templateUrl: 'views/timedfuel.tmpl.html',
                controller: 'CarReadingDetailtimedController',
                controllerAs: 'carReadingDetailtimedVm'
            })
            .when('/plotEngRpmtimed/:vin/:timeslot', {
                templateUrl: 'views/timedReading.tmpl.html',
                controller: 'CarTimedController',
                controllerAs: 'carReadingEngDetailtimedVm'
            })
            .otherwise({
                redirectTo: '/cars'
            });
    }
})();
