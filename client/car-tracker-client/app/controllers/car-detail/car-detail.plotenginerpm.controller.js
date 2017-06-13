(function() {
    'use strict';
    console.log('here in the plotter controller');
    angular.module('plunker')
        .controller('CarReadingEngineRpmDetailController', CarReadingEngineRpmDetailController);

    CarReadingEngineRpmDetailController.$inject = ['carService', '$routeParams','$scope'];


    function CarReadingEngineRpmDetailController(carService, $routeParams,$scope) {
        $scope.dataEng=[];
        carService.getByEngineRpmReadingsId($routeParams.vin)
            .then(function (dataEng){
                console.log('mdat before assigning---'+JSON.stringify(dataEng));
                $scope.dataEng= dataEng;
                console.log('controller passed ', $scope.dataEng);
            }, function (error){
                console.log(error);
            });

    }
})();

