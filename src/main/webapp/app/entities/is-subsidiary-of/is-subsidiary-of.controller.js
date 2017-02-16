(function() {
    'use strict';

    angular
        .module('tmtApp')
        .controller('IsSubsidiaryOfController', IsSubsidiaryOfController);

    IsSubsidiaryOfController.$inject = ['$scope', '$state', 'IsSubsidiaryOf'];

    function IsSubsidiaryOfController ($scope, $state, IsSubsidiaryOf) {
        var vm = this;

        vm.isSubsidiaryOfs = [];

        loadAll();

        function loadAll() {
            IsSubsidiaryOf.query(function(result) {
                vm.isSubsidiaryOfs = result;
                vm.searchQuery = null;
            });
        }
    }
})();
