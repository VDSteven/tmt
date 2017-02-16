(function() {
    'use strict';

    angular
        .module('tmtApp')
        .controller('IsHeadOfController', IsHeadOfController);

    IsHeadOfController.$inject = ['$scope', '$state', 'IsHeadOf'];

    function IsHeadOfController ($scope, $state, IsHeadOf) {
        var vm = this;

        vm.isHeadOfs = [];

        loadAll();

        function loadAll() {
            IsHeadOf.query(function(result) {
                vm.isHeadOfs = result;
                vm.searchQuery = null;
            });
        }
    }
})();
