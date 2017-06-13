(function() {
    'use strict';
    console.log('Alerts Details Controller 1');
    angular.module('plunker')
        .controller('AlertsDetailController', AlertsDetailController);

    AlertsDetailController.$inject = ['alertService', '$routeParams'];

    function AlertsDetailController(alertService, $routeParams) {
        var alertDetailVm = this;
        console.log('Alerts Controller');
        alertService.getAlertById($routeParams.vin)
            .then(function (alerts){
                alertDetailVm.alerts = alerts;
            }, function (error){
                console.log(error);
            });
    }

})();