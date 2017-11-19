Ext.define('app.views.MainView', {
    extend: 'Ext.tab.Panel',
    requires: [
        'app.views.EntityGrid',
        'app.views.OperationGrid',
        'app.views.MessageGrid'
    ],

    activeTab: 3,
    items: [
        {
            title: 'Сущности',
            xtype: 'entity-grid'
        },
        {
            title: 'Операции',
            xtype: 'operation-grid'
        },
        {
            title: 'Сообщения',
            xtype: 'message-grid'
        },
        {
            title: 'Валидации'
        }
    ]
});