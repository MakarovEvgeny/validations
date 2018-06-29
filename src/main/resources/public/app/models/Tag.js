Ext.define('app.models.Tag', {
    extend: 'app.models.BaseVersionableModel',

    proxy: {
        type: 'rest',
        url: '/tag',
        writer: {
            writeAllFields: true
        }
    },

    fields: [
        {name: 'name'},
        {name: 'description'}
    ]

});
