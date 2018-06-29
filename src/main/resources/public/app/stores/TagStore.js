Ext.define('app.stores.TagStore', {
    extend: 'Ext.data.Store',
    requires: [
        'app.models.Tag',
        'app.stores.AppRestProxy'
    ],

    alias: 'store.tag-store',

    model: 'app.models.Tag',

    autoLoad: true,

    remoteFilter: true,

    proxy: {
        type: 'app-rest',
        url: '/tag',
        api: {
            read: 'tag/query'
        }
    }

});
