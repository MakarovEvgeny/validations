Ext.define('app.stores.MessageStore', {
    extend: 'Ext.data.Store',
    requires: [
        'app.models.Message',
        'app.stores.AppRestProxy'
    ],

    alias: 'store.message-store',

    model: 'app.models.Message',

    autoLoad: true,

    remoteFilter: true,

    proxy: {
        type: 'app-rest',
        url: 'message',
        api: {
            read: 'message/query'
        }
    }

});