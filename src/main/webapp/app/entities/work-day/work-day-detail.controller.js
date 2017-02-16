(function() {
    'use strict';

    angular
        .module('tmtApp')
        .controller('WorkDayDetailController', WorkDayDetailController);

    WorkDayDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WorkDay', 'User', 'Project'];

    function WorkDayDetailController($scope, $rootScope, $stateParams, previousState, entity, WorkDay, User, Project) {
        var vm = this;

        vm.workDay = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tmtApp:workDayUpdate', function(event, result) {
            vm.workDay = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
