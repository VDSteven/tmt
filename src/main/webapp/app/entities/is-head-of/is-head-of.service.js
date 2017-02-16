(function() {
    'use strict';
    angular
        .module('tmtApp')
        .factory('IsHeadOf', IsHeadOf);

    IsHeadOf.$inject = ['$resource'];

    function IsHeadOf ($resource) {
        var resourceUrl =  'api/is-head-ofs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
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
