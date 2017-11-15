Ext.define('app.models.Entity', {
    extend: 'app.models.BaseVersionAwareModel',

    proxy: {
        type: 'rest',
        url: '/entity',
        writer: {
            writeAllFields: true
        }
    },

    fields: [
        {name: 'name'},
        {name: 'description'}
    ]

});