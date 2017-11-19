Ext.define('app.views.MessageGrid', {
    extend: 'app.views.ModelGrid',
    requires: [
        'app.stores.MessageStore',
        'app.controllers.MessageGridController'
    ],

    xtype: 'message-grid',

    controller: 'message-grid-controller',

    store: Ext.create('app.stores.MessageStore'),

    columns: {
        items: [
            {
                text: 'Код',
                dataIndex: 'id',
                filter: {
                    type: 'list',
                    operator: '='
                },
                flex: 1
            },
            {
                text: 'Текст сообщения',
                dataIndex: 'text',
                filter: {
                    type: 'multi-string'
                },
                flex: 1
            },
            {
                text: 'Комментарий',
                dataIndex: 'commentary',
                flex: 1
            }
        ]
    }

});