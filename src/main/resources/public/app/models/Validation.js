Ext.define('app.models.Validation', {
    extend: 'app.models.BaseVersionableModel',

    requires: [
        'app.models.ValidationEntity'
    ],

    proxy: {
        type: 'rest',
        url: 'validation',
        writer: {
            nameProperty: 'mapping',
            writeAllFields: true,
            expandData: true
        }
    },

    fields: [
        {name: 'messageId', mapping: 'message.id'},
        {name: 'description'},
        {name: 'validationEntities'},
        {name: 'severity'}
    ],

    associations: [
        {
            type: 'hasMany',
            associatedName: 'validationEntities',
            model: 'app.models.ValidationEntity'
        }
    ]

});