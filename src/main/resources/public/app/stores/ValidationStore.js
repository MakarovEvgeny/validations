Ext.define('app.stores.ValidationStore', {
    extend: 'Ext.data.Store',
    requires: [
        'app.models.Validation',
        'app.stores.AppRestProxy'
    ],

    model: 'app.models.Validation',

    autoLoad: true,

    remoteFilter: true,

    proxy: {
        type: 'app-rest',
        url: '/validation',
        api: {
            read: 'validation/query'
        }
    }

});