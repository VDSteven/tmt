(function() {
    'use strict';

    angular
        .module('tmtApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('work-day', {
            parent: 'entity',
            url: '/work-day',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WorkDays'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/work-day/work-days.html',
                    controller: 'WorkDayController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('work-day-detail', {
            parent: 'work-day',
            url: '/work-day/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WorkDay'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/work-day/work-day-detail.html',
                    controller: 'WorkDayDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'WorkDay', function($stateParams, WorkDay) {
                    return WorkDay.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'work-day',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('work-day-detail.edit', {
            parent: 'work-day-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-day/work-day-dialog.html',
                    controller: 'WorkDayDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkDay', function(WorkDay) {
                            return WorkDay.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('work-day.new', {
            parent: 'work-day',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-day/work-day-dialog.html',
                    controller: 'WorkDayDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                day: null,
                                isHoliday: null,
                                hours: null,
                                hoursApproved: null,
                                expenses: null,
                                expensesApproved: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('work-day', null, { reload: 'work-day' });
                }, function() {
                    $state.go('work-day');
                });
            }]
        })
        .state('work-day.edit', {
            parent: 'work-day',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-day/work-day-dialog.html',
                    controller: 'WorkDayDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkDay', function(WorkDay) {
                            return WorkDay.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-day', null, { reload: 'work-day' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('work-day.delete', {
            parent: 'work-day',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-day/work-day-delete-dialog.html',
                    controller: 'WorkDayDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WorkDay', function(WorkDay) {
                            return WorkDay.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-day', null, { reload: 'work-day' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
