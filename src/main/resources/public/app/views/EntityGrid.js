Ext.define('app.views.EntityGrid', {
    extend: 'app.views.ModelGrid',
    requires: [
        'app.stores.EntityStore',
        'app.controllers.EntityGridController'
    ],

    xtype: 'entity-grid',

    controller: 'entity-grid-controller',

    store: Ext.create('app.stores.EntityStore'),

    columns: {
        items: [
            {
                text: 'Код',
                dataIndex: 'id',
                filter: {
                    type: 'list'
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