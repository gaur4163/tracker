(function() {
    'use strict';
    console.log('here in the plotter controller');
    angular.module('plunker')
        .controller('CarReadingDetailController', CarReadingDetailController);

    CarReadingDetailController.$inject = ['carService', '$routeParams','$scope'];


    function CarReadingDetailController(carService, $routeParams,$scope) {

        $scope.datacont=[];
        carService.getByReadingsId($routeParams.vin)
            .then(function (mdat){
                console.log('mdat before assigning---'+JSON.stringify(mdat));


                $scope.datacont= mdat;
                console.log('controller passed ', $scope.datacont);
            }, function (error){
                console.log(error);
            });

    }
})();

