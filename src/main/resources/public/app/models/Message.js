Ext.define('app.models.Message', {
    extend: 'app.models.BaseVersionableModel',

    proxy: {
        type: 'rest',
        url: 'message',
        writer: {
            writeAllFields: true
        }
    },

    fields: [
        {name: 'text'}
    ]

});