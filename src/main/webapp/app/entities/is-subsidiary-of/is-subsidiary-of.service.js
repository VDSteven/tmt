(function() {
    'use strict';
    angular
        .module('tmtApp')
        .factory('IsSubsidiaryOf', IsSubsidiaryOf);

    IsSubsidiaryOf.$inject = ['$resource'];

    function IsSubsidiaryOf ($resource) {
        var resourceUrl =  'api/is-subsidiary-ofs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'querycurrent': {method: 'GET', url: 'api/is-subsidiary-ofs/current', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
