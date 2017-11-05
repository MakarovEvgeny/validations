Ext.define('app.models.Entity', {
    extend: 'Ext.data.Model',

    proxy: {
        type: 'rest',
        url: '/entity'
    },

    fields: [
        {name: 'id'},
        {name: 'name'},
        {name: 'description'},
        {name: 'commentary'}
    ]

});