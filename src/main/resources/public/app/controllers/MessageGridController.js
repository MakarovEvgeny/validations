Ext.define('app.controllers.MessageGridController', {
    extend: 'app.controllers.ModelGridController',
    alias: 'controller.message-grid-controller',

    requires: [
        'app.views.MessageWindow'
    ],

    /** @override */
    createWindow: function (config) {
        return Ext.create('app.views.MessageWindow', config);
    },

    /** @override */
    loadModel: function (id, requestConfig) {
        app.models.Message.load(id, requestConfig);
    }

});