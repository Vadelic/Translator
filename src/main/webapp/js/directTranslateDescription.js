
wordDesc.directive("translateDescription", function () {
    return {
        restrict: 'E',
        scope: {
            translateD: '=pack'
        },
        templateUrl: '../directive/translateDescription.html',
        link: function () {
        }

    };
});

