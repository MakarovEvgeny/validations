Ext.define('app.views.MainView', {
    extend: 'Ext.tab.Panel',
    requires: [
        'app.views.EntityGrid'
    ],

    activeTab: 3,
    items: [
        {
            title: 'Сущности',
            xtype: 'entity-grid'
        },
        {
            title: 'Операции'
        },
        {
            title: 'Сообщения'
        },
        {
            title: 'Валидации'
        }
    ]
});