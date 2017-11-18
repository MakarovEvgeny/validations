Ext.define('app.views.OperationGrid', {
    extend: 'app.views.ModelGrid',
    requires: [
        'app.stores.OperationStore',
        'app.controllers.OperationGridController'
    ],

    xtype: 'operation-grid',

    controller: 'operation-grid-controller',

    store: Ext.create('app.stores.OperationStore'),

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
                text: 'Наименование',
                dataIndex: 'name',
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