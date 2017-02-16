(function() {
    'use strict';
    angular
        .module('tmtApp')
        .factory('WorkDay', WorkDay);

    WorkDay.$inject = ['$resource', 'DateUtils'];

    function WorkDay ($resource, DateUtils) {
        var resourceUrl =  'api/work-days/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'querysubsidiary': { method: 'GET', url: 'api/work-days/subsidiary', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.day = DateUtils.convertLocalDateFromServer(data.day);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.day = DateUtils.convertLocalDateToServer(copy.day);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.day = DateUtils.convertLocalDateToServer(copy.day);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
