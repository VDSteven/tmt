(function() {
    'use strict';

    angular
        .module('tmtApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('is-head-of', {
            parent: 'entity',
            url: '/is-head-of',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'IsHeadOfs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/is-head-of/is-head-ofs.html',
                    controller: 'IsHeadOfController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('is-head-of-detail', {
            parent: 'is-head-of',
            url: '/is-head-of/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'IsHeadOf'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/is-head-of/is-head-of-detail.html',
                    controller: 'IsHeadOfDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'IsHeadOf', function($stateParams, IsHeadOf) {
                    return IsHeadOf.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'is-head-of',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('is-head-of-detail.edit', {
            parent: 'is-head-of-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/is-head-of/is-head-of-dialog.html',
                    controller: 'IsHeadOfDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IsHeadOf', function(IsHeadOf) {
                            return IsHeadOf.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('is-head-of.new', {
            parent: 'is-head-of',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/is-head-of/is-head-of-dialog.html',
                    controller: 'IsHeadOfDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('is-head-of', null, { reload: 'is-head-of' });
                }, function() {
                    $state.go('is-head-of');
                });
            }]
        })
        .state('is-head-of.edit', {
            parent: 'is-head-of',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/is-head-of/is-head-of-dialog.html',
                    controller: 'IsHeadOfDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IsHeadOf', function(IsHeadOf) {
                            return IsHeadOf.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('is-head-of', null, { reload: 'is-head-of' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('is-head-of.delete', {
            parent: 'is-head-of',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/is-head-of/is-head-of-delete-dialog.html',
                    controller: 'IsHeadOfDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['IsHeadOf', function(IsHeadOf) {
                            return IsHeadOf.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('is-head-of', null, { reload: 'is-head-of' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
