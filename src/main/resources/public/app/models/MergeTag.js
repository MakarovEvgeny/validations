Ext.define('app.models.MergeTag', {
    extend: 'Ext.data.Model',

    proxy: {
        type: 'rest',
        url: '/mergeTag',
        writer: {
            writeAllFields: true
        }
    },

    fields: [
        {name: 'tagId'},
        {name: 'mergeTagIds'}
    ]

});
