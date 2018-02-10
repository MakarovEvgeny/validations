Ext.define('app.models.ValidationEntity', {
    extend: 'Ext.data.Model',

    requires: [
        'app.models.Entity',
        'app.models.Operation'
    ],

    fields: [
        {name: 'entity'},
        {name: 'operations'}
    ],

    associations: [
        {
            type: 'hasOne',
            associatedName: 'entity',
            model: 'app.models.Entity'
        },
        {
            type: 'hasMany',
            associatedName: 'operations',
            model: 'app.models.Operation'
        }
    ]
});