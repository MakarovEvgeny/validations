Ext.define('app.stores.ValidationEntityStore', {
    extend: 'Ext.data.Store',
    requires: [
        'app.models.ValidationEntity'
    ],

    alias: 'store.entity-operations-store',

    model: 'app.models.ValidationEntity',

    autoLoad: false

});