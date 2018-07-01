app.controller('myCtrl', function ($scope, $http, $location) {

    $scope.myUrl = $location.absUrl();
    $http.get("availableLangs").then(function (response) {
        $scope.availableLangs = response.data;
    });

    $scope.words = {};

    $scope.selectWord = function (word) {
        console.log(word);
        $scope.selectedWord = word;
    };

    $scope.$watch('selectedLang', function (newval, oldval) {
        $http.get("words?lang=" + newval.code).then(function (response) {
            $scope.words = response.data;
            return $scope.words;
        });
    });

});

