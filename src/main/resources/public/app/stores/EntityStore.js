Ext.define('app.stores.EntityStore', {
    extend: 'Ext.data.Store',
    requires: ['app.models.Entity'],

    model: 'app.models.Entity',

    autoLoad: true,

    proxy: {
        type: 'rest',
        url: '/entity',
        reader: {
            type: 'json'
        }
    }

});