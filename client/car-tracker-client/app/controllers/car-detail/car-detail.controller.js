(function() {
    'use strict';
    angular.module('plunker')
        .controller('CarDetailController', CarDetailController);

    CarDetailController.$inject = ['carService', '$routeParams'];

    function CarDetailController(carService, $routeParams) {
        var carDetailVm = this;

        carService.getById($routeParams.vin)
            .then(function (car){
                carDetailVm.car = car;
            }, function (error){
                console.log(error);
            });
    }

})();