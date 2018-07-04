wordDesc.directive("translateDescription", function ($http) {
    return {
        restrict: 'E',
        scope: {
            translateD: '=pack'
        },
        templateUrl: '../directive/translateDescription.html',
        link: function (scope, elem, attr, ctrl) {
            // scope.translateD = attr.pack;
            scope.temp = null;
            scope.edit = function (original) {
                scope.temp = angular.copy(original);
            };
            scope.savePack = function (pack) {

                $http({
                    url: "saveTranslatePack",
                    method: "POST",
                    data: pack
                }).then(function (sucsses) {
                    scope.translateD = sucsses.data;
                }, function (error) {
                    console.log(error.data.message);
                });
            }
        }

    };
});

