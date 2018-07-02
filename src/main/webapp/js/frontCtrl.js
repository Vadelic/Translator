function getUnfilledPack(packs, availableLangs) {
    var unFilledLangs = [];


    packs.forEach(function (pack) {

    });



    availableLangs.forEach(function (avLang) {
        var exist = false;

        packs.forEach(function (pack) {

            if (avLang.code === pack.language.code) {
               exist =  true;
            }
        });


        if (!exist) {
            unFilledLangs.push(item.language);
        }
    });
    return unFilledLangs;
}

app.controller('navigateWords', function ($scope, $http, $location) {
    $scope.selectedLang = '';
    $scope.selectedWord = {};
    $scope.words = {};
    $scope.unFilledPack = [];

    $http.get("availableLangs").then(function (response) {
        $scope.availableLangs = response.data;
    });


    $scope.selectWord = function (word) {
        $http.get("wordItem?word=" + word + "&lang=" + $scope.selectedLang.code).then(function (response) {
            $scope.selectedWord = response.data;
            $scope.unFilledPack = getUnfilledPack($scope.selectedWord.translatePacks, $scope.availableLangs);

        });
    };

    $scope.$watch('selectedLang', function (newval, oldval) {
        if (newval === undefined)
            return;
        $http.get("words?lang=" + newval.code).then(function (response) {
            $scope.words = response.data;
            $scope.selectedWord = '';
        });
    });

    $scope.unFilledPack = function (pack) {
        return ['ru', 'en'];
    };

});

