(function() {
    'use strict';
    angular.module('plunker')
        .service('carService', carService);

    carService.$inject = ['$q', '$http'];

    function carService($q, $http) {
        var self = this;

        self.get = getCars;
        self.getById = getCarById;
        self.getByReadingsId = getByReadingsId;
        self.getByEngineRpmReadingsId = getByEngineRpmReadingsId;
        self.getByReadingsIdtimed = getByReadingsIdtimed;
        self.getByEngineRpmReadingsIdtimed=getByEngineRpmReadingsIdtimed;

        function getCars() {
            return $http.get('http://localhost:8080/vehicles')
                .then(successFn, errorFn);
        }

        function getCarById(vin) {
            console.log('inside CarSerice '+vin);
            return $http.get('http://localhost:8080/vehicle/' + vin)
                .then(successFn, errorFn);
        }
        function getByReadingsId(vin) {
            console.log('inside Car reading Serice '+vin);
            return $http.get('http://localhost:8080/readingsPlotFuelVol/' + vin)
                .then(successFn, errorFn);
        }
        function getByEngineRpmReadingsId(vin) {
            console.log('inside Car reading Serice '+vin);
            return $http.get('http://localhost:8080/readingsPlotEngRpm/' + vin)
                .then(successFn, errorFn);
        }
        function getByReadingsIdtimed(vin,timeslot) {
            console.log('inside Car reading Serice '+vin);
            return $http.get('http://localhost:8080/timeslotPlotFuelVol/' + vin+'/'+timeslot)
                .then(successFn, errorFn);
        }
        function getByEngineRpmReadingsIdtimed(vin,timeslot) {
            console.log('inside Car reading Serice '+vin);
            return $http.get('http://localhost:8080/timeslotPlotEngRpm/' + vin+'/'+timeslot)
                .then(successFn, errorFn);
        }
        function successFn(response) {
            console.log('response inside successFn---'+response);
            return response.data;
        }

        function errorFn(error) {
            return $q.reject(error);
        }
    }
})();