(function() {
    'use strict';
    angular.module('plunker')
        .service('alertService', alertService);

    alertService.$inject = ['$q', '$http'];

    function alertService($q, $http) {
        var self = this;

        self.getAllAlerts = getAllAlerts;
        self.getAlertById = getAlertById;

        function getAllAlerts() {
            console.log('Alerts Service');
            return $http.get('http://localhost:8080/alertsCount')
                .then(successFn, errorFn);
        }

        function getAlertById(vin) {
            return $http.get('http://localhost:8080/alertsForVin/' + vin)
                .then(successFn, errorFn);
        }

        function successFn(response) {
            return response.data;
        }

        function errorFn(error) {
            return $q.reject(error);
        }
    }
})();