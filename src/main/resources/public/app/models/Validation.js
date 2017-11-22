Ext.define('app.models.Validation', {
    extend: 'app.models.BaseVersionAwareModel',

    requires: [
        'app.models.Entity',
        'app.models.Operation'
    ],

    proxy: {
        type: 'rest',
        url: '/validation',
        writer: {
            nameProperty: 'mapping',
            writeAllFields: true,
            expandData: true
        }
    },

    fields: [
        {name: 'messageId', mapping: 'message.id'},
        {name: 'description'},
        {name: 'entities'},
        {name: 'operations'},
        {name: 'severity'}
    ],

    associations: [
        {
            type: 'hasMany',
            associatedName: 'entities',
            model: 'app.models.Entity'
        },
        {
            type: 'hasMany',
            associatedName: 'operations',
            model: 'app.models.Entity'
        }
    ]

});