angular.module('model', [])

    .factory('modelHolder', function () {

        var _child = null;

        return {
            setChild: function(child) {
                _child = child;
            },

            getChild: function() {
                return _child;
            }
        }
    });

