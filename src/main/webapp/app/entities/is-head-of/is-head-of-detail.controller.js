(function() {
    'use strict';

    angular
        .module('tmtApp')
        .controller('IsHeadOfDetailController', IsHeadOfDetailController);

    IsHeadOfDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'IsHeadOf', 'User'];

    function IsHeadOfDetailController($scope, $rootScope, $stateParams, previousState, entity, IsHeadOf, User) {
        var vm = this;

        vm.isHeadOf = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tmtApp:isHeadOfUpdate', function(event, result) {
            vm.isHeadOf = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
