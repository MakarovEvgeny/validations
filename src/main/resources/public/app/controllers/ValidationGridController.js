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
    loadModel: function (id, requestConfig, window) {
        requestConfig.success = function (record) {
            window.down('form').loadRecord(record);
            var entities = window.down('custom-tagfield[name=entityIds]');
            entities.setValue(record.entities().getRange());
            var operations = window.down('custom-tagfield[name=operationIds]');
            operations.setValue(record.operations().getRange());
        };
        app.models.Validation.load(id, requestConfig);
    }

});