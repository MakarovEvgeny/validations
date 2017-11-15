Ext.define('app.controllers.EntityGridController', {
    extend: 'app.controllers.ModelGridController',
    alias: 'controller.entity-grid-controller',

    requires: [
        'app.views.EntityWindow'
    ],

    /** @override */
    createWindow: function (config) {
        return Ext.create('app.views.EntityWindow', config);
    },

    /** @override */
    loadModel: function (id, requestConfig) {
        app.models.Entity.load(id, requestConfig);
    }

});