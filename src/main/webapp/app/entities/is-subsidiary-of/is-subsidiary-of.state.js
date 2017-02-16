(function() {
    'use strict';

    angular
        .module('tmtApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('is-subsidiary-of', {
            parent: 'entity',
            url: '/is-subsidiary-of',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'IsSubsidiaryOfs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/is-subsidiary-of/is-subsidiary-ofs.html',
                    controller: 'IsSubsidiaryOfController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('is-subsidiary-of-detail', {
            parent: 'is-subsidiary-of',
            url: '/is-subsidiary-of/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'IsSubsidiaryOf'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/is-subsidiary-of/is-subsidiary-of-detail.html',
                    controller: 'IsSubsidiaryOfDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'IsSubsidiaryOf', function($stateParams, IsSubsidiaryOf) {
                    return IsSubsidiaryOf.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'is-subsidiary-of',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('is-subsidiary-of-detail.edit', {
            parent: 'is-subsidiary-of-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/is-subsidiary-of/is-subsidiary-of-dialog.html',
                    controller: 'IsSubsidiaryOfDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IsSubsidiaryOf', function(IsSubsidiaryOf) {
                            return IsSubsidiaryOf.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('is-subsidiary-of.new', {
            parent: 'is-subsidiary-of',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/is-subsidiary-of/is-subsidiary-of-dialog.html',
                    controller: 'IsSubsidiaryOfDialogController',
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
                    $state.go('is-subsidiary-of', null, { reload: 'is-subsidiary-of' });
                }, function() {
                    $state.go('is-subsidiary-of');
                });
            }]
        })
        .state('is-subsidiary-of.edit', {
            parent: 'is-subsidiary-of',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/is-subsidiary-of/is-subsidiary-of-dialog.html',
                    controller: 'IsSubsidiaryOfDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IsSubsidiaryOf', function(IsSubsidiaryOf) {
                            return IsSubsidiaryOf.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('is-subsidiary-of', null, { reload: 'is-subsidiary-of' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('is-subsidiary-of.delete', {
            parent: 'is-subsidiary-of',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/is-subsidiary-of/is-subsidiary-of-delete-dialog.html',
                    controller: 'IsSubsidiaryOfDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['IsSubsidiaryOf', function(IsSubsidiaryOf) {
                            return IsSubsidiaryOf.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('is-subsidiary-of', null, { reload: 'is-subsidiary-of' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
