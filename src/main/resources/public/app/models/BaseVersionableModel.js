Ext.define('app.models.BaseVersionableModel', {
    extend: 'Ext.data.Model',

    fields: [
        {name: 'id'},
        {name: 'commentary'},
        {name: 'version'}
    ]

});