Ext.define('app.models.Entity', {
    extend: 'Ext.data.Model',

    proxy: {
        type: 'rest',
        url: '/entity',
        writer: {
            writeAllFields: true
        }
    },

    fields: [
        {name: 'id'},
        {name: 'name'},
        {name: 'description'},
        {name: 'commentary'},
        {name: 'version'}
    ]

});