
wordDesc.directive("wordDescription", function () {
    return {
        restrict: 'E',
        scope: {
            wordD: '=word'
        },
        templateUrl: '../directive/wordDescription.html',
        link: function () {
            // console.log(scope);
        }

    };
});
//
// wordDesc.controller('wordsView', function ($scope, $http) {
//     console.log('$scope');
//     console.log($scope);
//     console.log('scope');
//     console.log(scope);
//
// });

