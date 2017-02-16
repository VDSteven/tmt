(function() {
    'use strict';

    angular
        .module('tmtApp')
        .controller('WorkDayController', WorkDayController);

    WorkDayController.$inject = ['$scope', '$state', 'WorkDay'];

    function WorkDayController ($scope, $state, WorkDay) {
        var vm = this;

        vm.workDays = [];

        loadAll();

        function loadAll() {
            WorkDay.querysubsidiary(function(result) {
                vm.workDays = result;
                vm.searchQuery = null;
            });
        }
    }
})();
