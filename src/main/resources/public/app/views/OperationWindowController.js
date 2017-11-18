Ext.define('app.views.OperationWindowController', {
    extend: 'app.views.ModelWindowController',
    alias: 'controller.operation-window-controller',

    requires: [
        'app.models.Operation'
    ],

    /** @override */
    createEmptyModel: function () {
        return Ext.create('app.models.Operation');
    }

});