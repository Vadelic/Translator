app.controller('myCtrl', function ($scope, $http, $location) {
    $scope.myUrl = $location.absUrl();
    $http.get("availableLangs").then(function (response) {
        $scope.availableLangs = response.data;
    });

    $scope.words = {};

    $scope.selectLang = function(lang) {
        $http.get("words?lang="+lang).then(function (response) {
            $scope.words = response.data;
            return $scope.words;
        });
    };

    $scope.$watch('selectedLang', function (newval, oldval) {
        $http.get("words?lang="+newval.code).then(function (response) {
            $scope.words = response.data;
            return $scope.words;
        });
    });

});

app.directive("w3TestDirective", function () {
    return {
        template: "<h1>Made by a directive!</h1>"
    };
});
