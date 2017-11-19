Ext.define('app.controllers.OperationWindowController', {
    extend: 'app.controllers.ModelWindowController',
    alias: 'controller.operation-window-controller',

    requires: [
        'app.models.Operation'
    ],

    /** @override */
    createEmptyModel: function () {
        return Ext.create('app.models.Operation');
    }

});