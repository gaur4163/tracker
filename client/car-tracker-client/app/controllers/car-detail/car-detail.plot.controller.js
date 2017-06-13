(function() {
    'use strict';
    angular.module('plunker')
        .controller('CarPlotSignal', CarPlotSignal);

    CarPlotSignal.$inject = ['carService', '$routeParams'];

    function CarPlotSignal(carService, $routeParams) {
        var carPlotSignalVm = this;
        carPlotSignalVm.vin =[];
        carPlotSignalVm.vin = $routeParams.vin;
        console.log('insde plo ',carPlotSignalVm.vin )
    }

})();