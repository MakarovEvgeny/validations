Ext.define('app.stores.ValidationStore', {
    extend: 'Ext.data.Store',
    requires: [
        'app.models.ValidationDto',
        'app.stores.AppRestProxy'
    ],

    model: 'app.models.ValidationDto',

    autoLoad: true,

    remoteFilter: true,

    proxy: {
        type: 'app-rest',
        url: 'validation',
        api: {
            read: 'validation/query'
        }
    }

});