(function() {
    'use strict';

    angular
        .module('tmtApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('statistics', {
            parent: 'app',
            url: '/statistics',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Statistics'
            },
            views: {
                'content@': {
                    templateUrl: 'app/statistics/statistics.html',
                    controller: 'StatisticsController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
