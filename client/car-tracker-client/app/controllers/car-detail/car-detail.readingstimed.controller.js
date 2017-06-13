(function() {
    'use strict';
    console.log('here in the plotter controller');
    angular.module('plunker')
        .controller('CarReadingDetailtimedController', CarReadingDetailtimedController);

    CarReadingDetailtimedController.$inject = ['carService', '$routeParams','$scope'];


    function CarReadingDetailtimedController(carService, $routeParams,$scope) {

        $scope.datacontt=[];
        carService.getByReadingsIdtimed($routeParams.vin,$routeParams.timeslot)
            .then(function (mdat){
                console.log('mdat before assigning---'+JSON.stringify(mdat));


                $scope.datacontt= mdat;
                console.log('controller passed ', $scope.datacontt);
            }, function (error){
                console.log(error);
            });

    }
})();

