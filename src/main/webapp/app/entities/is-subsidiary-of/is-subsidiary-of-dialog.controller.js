(function() {
    'use strict';

    angular
        .module('tmtApp')
        .controller('IsSubsidiaryOfDialogController', IsSubsidiaryOfDialogController);

    IsSubsidiaryOfDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'IsSubsidiaryOf', 'User'];

    function IsSubsidiaryOfDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, IsSubsidiaryOf, User) {
        var vm = this;

        vm.isSubsidiaryOf = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.isSubsidiaryOf.id !== null) {
                IsSubsidiaryOf.update(vm.isSubsidiaryOf, onSaveSuccess, onSaveError);
            } else {
                IsSubsidiaryOf.save(vm.isSubsidiaryOf, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tmtApp:isSubsidiaryOfUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
