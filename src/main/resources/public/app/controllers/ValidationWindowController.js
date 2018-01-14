Ext.define('app.controllers.ValidationWindowController', {
    extend: 'app.controllers.ModelWindowController',
    alias: 'controller.validation-window-controller',

    requires: [
        'app.models.Validation'
    ],

    /** @override */
    getModel: function () {
        var model = this.callParent();
        var entitiesStore = model.entities();
        var operationsStore = model.operations();

        var entityIds = this.getView().down('custom-tagfield[name=entityIds]').value;
        var operationIds = this.getView().down('custom-tagfield[name=operationIds]').value;

        Ext.Array.each(entityIds, function (entityId) {
                entitiesStore.add(Ext.create('app.models.Entity', {id: entityId}))
        });
        Ext.Array.each(operationIds, function (operationId) {
                operationsStore.add(Ext.create('app.models.Operation', {id: operationId}))
        });

        Ext.apply(model.data, model.getAssociatedData());

        return model;
    },

    /** @override */
    createEmptyModel: function () {
        return Ext.create('app.models.Validation');
    },

    /** @override */
    getChangesUrlPart: function () {
        return 'validation';
    },

    /** @override */
    setModelToForm: function (form, model) {
        this.callParent(arguments);

        var entities = form.down('custom-tagfield[name=entityIds]');
        var entityIds = [];
        Ext.Array.each(model.get('entities'), function (entity) {
            entityIds.push(entity.id);
        });
        entities.setValue(entityIds);

        var operations = form.down('custom-tagfield[name=operationIds]');
        var operationIds = [];
        Ext.Array.each(model.get('operations'), function (operation) {
            operationIds.push(operation.id);
        });
        operations.setValue(operationIds);
    }

});