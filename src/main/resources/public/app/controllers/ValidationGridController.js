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
            window.getController().initTagView(record);
            var validationEntitiesStore = window.down('grid').getStore();
            validationEntitiesStore.add(record.validationEntities().getRange());
        };
        app.models.Validation.load(id, requestConfig);
    }

});