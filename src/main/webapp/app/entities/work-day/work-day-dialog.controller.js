(function() {
    'use strict';

    angular
        .module('tmtApp')
        .controller('WorkDayDialogController', WorkDayDialogController);

    WorkDayDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WorkDay', 'User', 'Project'];

    function WorkDayDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WorkDay, User, Project) {
        var vm = this;

        vm.workDay = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.querysubsidiary();
        vm.projects = Project.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.workDay.id !== null) {
                WorkDay.update(vm.workDay, onSaveSuccess, onSaveError);
            } else {
                WorkDay.save(vm.workDay, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tmtApp:workDayUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.day = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
