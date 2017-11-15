Ext.define('app.models.BaseVersionAwareModel', {
    extend: 'Ext.data.Model',

    fields: [
        {name: 'id'},
        {name: 'commentary'},
        {name: 'version'}
    ]

});