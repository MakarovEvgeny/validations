Ext.define('app.models.Change', {
    extend: 'Ext.data.Model',

    fields: [
        {name: 'date', type: 'date'},
        {name: 'username'},
        {name: 'commentary'}
    ]

});