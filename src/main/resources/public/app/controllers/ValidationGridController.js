Ext.define('app.controllers.ValidationGridController', {
    extend: 'app.controllers.ModelGridController',
    alias: 'controller.validation-grid-controller',

    requires: [
        'app.views.ValidationWindow'
    ],

    /** @override */
    createWindow: function (config) {
        return Ext.create('app.views.ValidationWindow', config);
    },

    /** @override */
    loadModel: function (id, requestConfig) {
        app.models.Validation.load(id, requestConfig);
    }

});