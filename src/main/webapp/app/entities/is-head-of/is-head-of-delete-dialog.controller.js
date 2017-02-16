(function() {
    'use strict';

    angular
        .module('tmtApp')
        .controller('IsHeadOfDeleteController',IsHeadOfDeleteController);

    IsHeadOfDeleteController.$inject = ['$uibModalInstance', 'entity', 'IsHeadOf'];

    function IsHeadOfDeleteController($uibModalInstance, entity, IsHeadOf) {
        var vm = this;

        vm.isHeadOf = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            IsHeadOf.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
