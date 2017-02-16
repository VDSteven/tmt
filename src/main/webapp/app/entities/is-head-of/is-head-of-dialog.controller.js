(function() {
    'use strict';

    angular
        .module('tmtApp')
        .controller('IsHeadOfDialogController', IsHeadOfDialogController);

    IsHeadOfDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'IsHeadOf', 'User'];

    function IsHeadOfDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, IsHeadOf, User) {
        var vm = this;

        vm.isHeadOf = entity;
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
            if (vm.isHeadOf.id !== null) {
                IsHeadOf.update(vm.isHeadOf, onSaveSuccess, onSaveError);
            } else {
                IsHeadOf.save(vm.isHeadOf, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tmtApp:isHeadOfUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
