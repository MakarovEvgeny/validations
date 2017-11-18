Ext.define('app.models.Message', {
    extend: 'app.models.BaseVersionAwareModel',

    proxy: {
        type: 'rest',
        url: '/message',
        writer: {
            writeAllFields: true
        }
    },

    fields: [
        {name: 'text'}
    ]

});