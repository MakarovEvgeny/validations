Ext.define('app.views.ValidationGrid', {
    extend: 'app.views.ModelGrid',
    requires: [
        'app.stores.ValidationStore',
        'app.controllers.ValidationGridController'
    ],

    xtype: 'validation-grid',

    controller: 'validation-grid-controller',

    /** @override */
    createStore: function (storeConfig) {
        return Ext.create('app.stores.ValidationStore', storeConfig);
    },

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
                text: 'Код сообщения об ошибке',
                dataIndex: 'messageId',
                filter: {
                    type: 'multi-string'
                },
                flex: 1
            },
            {
                text: 'Описание',
                dataIndex: 'description',
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