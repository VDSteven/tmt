(function() {
    'use strict';

    angular
        .module('tmtApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('approval', {
                parent: 'app',
                url: '/approval',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Approve entries'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/approval/approval.html',
                        controller: 'ApprovalController',
                        controllerAs: 'vm'
                    }
                }
            })
            .state('approval.edit', {
                parent: 'approval',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/approval/approval-dialog.html',
                        controller: 'ApprovalDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['WorkDay', function(WorkDay) {
                                return WorkDay.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('approval', null, { reload: 'approval' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            });
    }
})();
