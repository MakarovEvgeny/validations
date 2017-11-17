Ext.define('app.models.Operation', {
    extend: 'app.models.BaseVersionAwareModel',

    proxy: {
        type: 'rest',
        url: '/operation',
        writer: {
            writeAllFields: true
        }
    },

    fields: [
        {name: 'name'},
        {name: 'description'}
    ]

});