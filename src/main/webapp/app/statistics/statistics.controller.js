(function() {
    'use strict';

    angular
        .module('tmtApp')
        .controller('StatisticsController', StatisticsController);

    StatisticsController.$inject = ['$scope', '$state', 'Statistics', 'User', 'Project'];

    var monthNames = [
        'January',
        'February',
        'March',
        'April',
        'Mai',
        'June',
        'July',
        'August',
        'September',
        'October',
        'November',
        'December'
    ];

    function StatisticsController ($scope, $state, Statistics, User, Project) {
        var vm = this;

        vm.Users = User.query();
        vm.Projects = Project.query();
        vm.Statistics = [];
        vm.Request = {};
        vm.isLoading = false;

        vm.loadStats = loadStats;
        vm.monthNumberToName = monthNumberToName;

        function loadStats() {
            vm.isLoading = true;

            var requestParameters = {};
            requestParameters.year = vm.Request.year;

            if (vm.Request.user != null) {
                requestParameters.userId = vm.Request.user.id;
            }

            if (vm.Request.project != null) {
                requestParameters.projectId = vm.Request.project.id;
            }

            Statistics.query(requestParameters, onLoadSuccess, onLoadError);
        }

        function onLoadSuccess(result) {
            vm.isLoading = false;
            vm.Statistics = result;
            console.log(vm.Statistics);
        }

        function onLoadError() {
            vm.isLoading = false;
        }

        function monthNumberToName(number) {
            return monthNames[number-1];
        }
    }
})();
