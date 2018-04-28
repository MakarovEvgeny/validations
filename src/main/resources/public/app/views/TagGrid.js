Ext.define('app.views.TagGrid', {
    extend: 'app.views.ModelGrid',
    requires: [
        'app.stores.TagStore',
        'app.controllers.TagGridController'
    ],

    xtype: 'tag-grid',

    controller: 'tag-grid-controller',

    /** @override */
    createStore: function (storeConfig) {
        return Ext.create('app.stores.TagStore', storeConfig);
    },

    columns: {
        items: [
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
