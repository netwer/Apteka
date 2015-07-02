var module = angular.module('myApp.directives', [])
    .directive('statusText', function () {
        return {
            restrict: 'E',
            compile:function(templateElement, templateAttrs) {
                templateElement.html("<div class=\"text-center\" style='margin-top 20px; margin-bottom: 20px;'> " +
                    "<h2><small><br><i class=\"fa " + templateAttrs.faIconClass + " \"></i><br>"+ templateAttrs.text +"</small></h2>" +
                    "</div>");
            },
            link: function (scope, element, attrs) {

            }
        }
    });