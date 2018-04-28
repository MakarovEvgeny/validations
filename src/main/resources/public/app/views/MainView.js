Ext.define('app.views.MainView', {
    extend: 'Ext.panel.Panel',

    requires: [
        'app.views.MainPageForm',
        'app.views.EntityGrid',
        'app.views.OperationGrid',
        'app.views.MessageGrid',
        'app.views.ValidationGrid',
        'app.views.LoginPanel',
        'app.views.TagGrid'
    ],

    layout: 'border',

    items: [
        {
            xtype: 'login-panel',
            width: '100%',
            region: 'north'
        },
        {
            xtype: 'tabpanel',
            region: 'center',
            activeTab: 0,

            items: [
                {
                    title: 'Главная страница',
                    xtype: 'main-page-form'
                },
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
                    title: 'Проверки',
                    xtype: 'validation-grid'
                },
                {
                    title: 'Теги',
                    xtype: 'tag-grid'
                }
            ]
        }
    ]

});