Ext.define('app.views.EntityGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
        'app.stores.EntityStore',
        'app.controllers.EntityGridController'
    ],

    xtype: 'entity-grid',

    enableColumnHide: false,

    plugins: 'gridfilters',

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
                    type: 'string'
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