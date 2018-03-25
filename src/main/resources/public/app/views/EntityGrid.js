Ext.define('app.views.EntityGrid', {
    extend: 'app.views.ModelGrid',
    requires: [
        'app.stores.EntityStore',
        'app.controllers.EntityGridController'
    ],

    xtype: 'entity-grid',

    controller: 'entity-grid-controller',

    /** @override */
    createStore: function (storeConfig) {
        return Ext.create('app.stores.EntityStore', storeConfig);
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