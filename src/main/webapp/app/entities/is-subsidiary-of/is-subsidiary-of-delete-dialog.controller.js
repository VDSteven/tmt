(function() {
    'use strict';

    angular
        .module('tmtApp')
        .controller('IsSubsidiaryOfDeleteController',IsSubsidiaryOfDeleteController);

    IsSubsidiaryOfDeleteController.$inject = ['$uibModalInstance', 'entity', 'IsSubsidiaryOf'];

    function IsSubsidiaryOfDeleteController($uibModalInstance, entity, IsSubsidiaryOf) {
        var vm = this;

        vm.isSubsidiaryOf = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            IsSubsidiaryOf.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
