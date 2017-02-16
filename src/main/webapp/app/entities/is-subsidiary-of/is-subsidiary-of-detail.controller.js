(function() {
    'use strict';

    angular
        .module('tmtApp')
        .controller('IsSubsidiaryOfDetailController', IsSubsidiaryOfDetailController);

    IsSubsidiaryOfDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'IsSubsidiaryOf', 'User'];

    function IsSubsidiaryOfDetailController($scope, $rootScope, $stateParams, previousState, entity, IsSubsidiaryOf, User) {
        var vm = this;

        vm.isSubsidiaryOf = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tmtApp:isSubsidiaryOfUpdate', function(event, result) {
            vm.isSubsidiaryOf = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
