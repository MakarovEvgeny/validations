Ext.define('app.views.MainView', {
    extend: 'Ext.tab.Panel',
    requires: [
        'app.views.EntityGrid',
        'app.views.OperationGrid',
        'app.views.MessageGrid',
        'app.views.ValidationGrid'
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
            title: 'Валидации',
            xtype: 'validation-grid'
        }
    ]
});