(function() {
    'use strict';
    angular
        .module('tmtApp')
        .factory('Statistics', Statistics);

    Statistics.$inject = ['$resource'];

    function Statistics ($resource) {
        var resourceUrl =  'api/statistics';

        return $resource(resourceUrl, {}, {
            'query': {
                method: 'GET',
                transformRequest: function (data) {
                    return angular.toJson(data);
                },
                isArray: true
            }
        });
    }
})();
