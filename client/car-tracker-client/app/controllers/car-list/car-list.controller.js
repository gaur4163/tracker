(function() {
    'use strict';
    angular.module('plunker')
        .controller('CarListController', CarListController);

    CarListController.$inject = ['carService'];

    function CarListController(carService) {
        var carListVm = this;

        carService.get()
            .then(function(cars){

                carListVm.cars = cars;
            }, function (error){
                console.log(error);
            });
    }

})();