function getUnfilledPack(packs, availableLangs, wordCode) {
    var unFilledLangs = [];
    var wordsPacks = [];

    wordsPacks.push(wordCode);
    packs.forEach(function (pack) {
        wordsPacks.push(pack.language.code);
    });

    availableLangs.forEach(function (avLang) {
        if (!wordsPacks.includes(avLang.code)) {
            unFilledLangs.push(avLang);
        }
    });
    return unFilledLangs;
}

app.controller('navigateWords', function ($scope, $http) {
    $http.get("availableLangs").then(function (response) {
        $scope.availableLangs = response.data;
    });

    $scope.selectedWord = null;
    $scope.unFilledPack = [];
    $scope.selectWord = function (word) {
        $http.get("wordItem?word=" + word + "&lang=" + $scope.selectedLang.code).then(function (response) {
            $scope.selectedWord = response.data;
            $scope.unFilledPack = getUnfilledPack($scope.selectedWord.translatePacks, $scope.availableLangs, $scope.selectedWord.language.code);
        });
    };

    $scope.words = [];
    $scope.selectedLang = null;
    $scope.selectLang = function (lang) {
        $scope.selectedLang = lang;
        $http.get("words?lang=" + lang.code).then(function (response) {
            $scope.words = response.data;
            $scope.selectedWord = null;
        });
    };

    $scope.editWord = function (word) {
        alert(word);
    };

    $scope.addPack = function (word, packLang) {
        var paramWordId = "wordId=" + word.id;
        var paramLang = "lang=" + packLang.code;

        $http.get("addPack?" + paramLang + "&" + paramWordId)
            .then(function (response) {
                $scope.selectedWord.translatePacks.push(response.data);
                $scope.unFilledPack = getUnfilledPack($scope.selectedWord.translatePacks, $scope.availableLangs, $scope.selectedWord.language.code);
            })
            .catch(function (reason) {
                alert(reason.data.message);
            })
        ;
    };

    $scope.addItem = function (wordtext, lang) {
        debugger;
        var paramWordId = "word=" + wordtext;
        var paramLang = "lang=" + lang;

        $http.get("addWord?" + paramLang + "&" + paramWordId)
            .then(function (response) {
                var word = response.data.word;
                $scope.words.push(word);
                $scope.selectWord(word);
            })
            .catch(function (reason) {
                alert(reason.data.message);
            })
        ;
    };

});

