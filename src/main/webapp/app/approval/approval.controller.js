(function() {
    'use strict';

    angular
        .module('tmtApp')
        .controller('ApprovalController', ApprovalController);

    ApprovalController.$inject = ['$scope', '$state', 'WorkDay'];

    function ApprovalController ($scope, $state, WorkDay) {
        var vm = this;

        vm.workDays = [];

        loadAll();

        function loadAll() {
            WorkDay.queryhead(function(result) {
                vm.workDays = result;
                vm.searchQuery = null;
            });
        }
    }
})();
