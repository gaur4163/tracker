(function() {
    'use strict';
    console.log('Alerts Controller 1');
    angular.module('plunker')
        .controller('AlertsAllhighController', AlertsAllhighController);

    AlertsAllhighController.$inject = ['alertService'];

    function AlertsAllhighController(alertService) {
        var alertAllVm = this;
        console.log('Alerts Controller');
        alertService.getAllAlerts()
            .then(function (alerts){
                alertAllVm.alerts = alerts;
            }, function (error){
                console.log(error);
            });
    }

})();