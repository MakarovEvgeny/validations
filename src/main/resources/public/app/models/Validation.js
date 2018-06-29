Ext.define('app.models.Validation', {
    extend: 'app.models.BaseVersionableModel',

    requires: [
        'app.models.ValidationEntity',
        'app.models.Tag'
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
        {name: 'tags'},
        {name: 'severity'}
    ],

    associations: [
        {
            type: 'hasMany',
            associatedName: 'validationEntities',
            model: 'app.models.ValidationEntity'
        },
        {
            type: 'hasMany',
            associatedName: 'tags',
            model: 'app.models.Tag'
        }
    ]

});