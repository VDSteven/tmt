(function() {
    'use strict';

    angular
        .module('tmtApp')
        .controller('WorkDayDeleteController',WorkDayDeleteController);

    WorkDayDeleteController.$inject = ['$uibModalInstance', 'entity', 'WorkDay'];

    function WorkDayDeleteController($uibModalInstance, entity, WorkDay) {
        var vm = this;

        vm.workDay = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WorkDay.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
