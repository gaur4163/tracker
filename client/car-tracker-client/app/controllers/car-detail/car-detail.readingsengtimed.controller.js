(function() {
    'use strict';
    console.log('here in the plotter controller');
    angular.module('plunker')
        .controller('CarTimedController', CarTimedController);

    CarTimedController.$inject = ['carService', '$routeParams','$scope'];


    function CarTimedController(carService, $routeParams,$scope) {
        $scope.dataEngt=[];
        carService.getByEngineRpmReadingsIdtimed($routeParams.vin,$routeParams.timeslot)
            .then(function (dataEngt){
                console.log('mdat before assigning---'+JSON.stringify(dataEngt));
                $scope.dataEngt= dataEngt;
                console.log('controller passed ', $scope.dataEngt);
            }, function (error){
                console.log(error);
            });

    }
})();