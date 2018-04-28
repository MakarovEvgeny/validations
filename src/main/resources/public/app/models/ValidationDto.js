Ext.define('app.models.ValidationDto', {
    extend: 'Ext.data.Model',

    fields: [
        {name: 'id'},
        {name: 'commentary'},
        {name: 'version'},
        {name: 'messageId'},
        {name: 'messageText'},
        {name: 'description'},
        {name: 'severityId'},
        {name: 'severityName'},
        {name: 'entityNames'},
        {name: 'operationNames'},
        {name: 'tagNames'},
        {name: 'severityId'}
    ]

});