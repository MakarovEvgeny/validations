Ext.define('app.views.MainView', {
    extend: 'Ext.tab.Panel',
    activeTab: 3,
    items: [
        {
            title: 'Сущности'
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