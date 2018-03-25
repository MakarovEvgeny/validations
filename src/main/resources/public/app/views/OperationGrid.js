Ext.define('app.views.OperationGrid', {
    extend: 'app.views.ModelGrid',
    requires: [
        'app.stores.OperationStore',
        'app.controllers.OperationGridController'
    ],

    xtype: 'operation-grid',

    controller: 'operation-grid-controller',

    /** @override */
    createStore: function (storeConfig) {
        return Ext.create('app.stores.OperationStore', storeConfig);
    },

    columns: {
        items: [
            {
                text: 'Код',
                dataIndex: 'id',
                filter: {
                    type: 'multi-string',
                    operator: app.models.SearchOperator.ILIKE
                },
                flex: 1
            },
            {
                text: 'Наименование',
                dataIndex: 'name',
                filter: {
                    type: 'multi-string',
                    operator: app.models.SearchOperator.ILIKE
                },
                flex: 1
            },
            {
                text: 'Описание',
                dataIndex: 'description',
                filter: {
                    type: 'multi-string',
                    operator: app.models.SearchOperator.ILIKE
                },
                flex: 1
            },
            {
                text: 'Комментарий',
                dataIndex: 'commentary',
                filter: {
                    type: 'multi-string',
                    operator: app.models.SearchOperator.ILIKE
                },
                flex: 1
            }
        ]
    }

});