Ext.define('app.views.MainView', {
    extend: 'Ext.panel.Panel',

    requires: [
        'app.views.EntityGrid',
        'app.views.OperationGrid',
        'app.views.MessageGrid',
        'app.views.ValidationGrid',
        'app.views.LoginPanel'
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
        }
    ]

});