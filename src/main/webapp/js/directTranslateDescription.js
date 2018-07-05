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
                    scope.edit(scope.translateD);
                }, function (error) {
                    console.log(error.data.message);
                });
            };

            scope.removeSentence = function (index) {
                scope.temp.sentences.splice(index, 1);
            };

            scope.addSentence = function () {
                var newSent = {};
                newSent.sentenceFrom = scope.tempSentenceFrom;
                newSent.sentenceTo = scope.tempSentenceTo;
                newSent.resource = "web";
                scope.temp.sentences    .push(newSent);

                scope.tempSentenceFrom='';
                scope.tempSentenceTo='';

            };
        }

    };
});

