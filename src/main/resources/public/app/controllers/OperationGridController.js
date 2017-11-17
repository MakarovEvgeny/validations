Ext.define('app.controllers.OperationGridController', {
    extend: 'app.controllers.ModelGridController',
    alias: 'controller.operation-grid-controller',

    requires: [
        'app.views.OperationWindow'
    ],

    /** @override */
    createWindow: function (config) {
        return Ext.create('app.views.OperationWindow', config);
    },

    /** @override */
    loadModel: function (id, requestConfig) {
        app.models.Operation.load(id, requestConfig);
    }

});