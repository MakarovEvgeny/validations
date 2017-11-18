Ext.define('app.stores.OperationStore', {
    extend: 'Ext.data.Store',
    requires: [
        'app.models.Operation',
        'app.stores.AppRestProxy'
    ],

    model: 'app.models.Operation',

    autoLoad: true,

    remoteFilter: true,

    proxy: {
        type: 'app-rest',
        url: '/operation',
        api: {
            read: 'operation/query'
        }
    }

});