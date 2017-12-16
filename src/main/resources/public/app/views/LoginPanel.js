Ext.define('app.views.LoginPanel', {
    extend: 'Ext.panel.Panel',
    requires: [
        'app.controllers.LoginPanelController'
    ],

    layout: {
        type: 'hbox',
        pack: 'end'
    },

    xtype: 'login-panel',

    controller: 'login-panel-controller',

    constructor: function () {
        this.callParent(arguments);
        app.views.LoginPanelConfigurer.configureButtons();
    },

    items: [
        {
            xtype: 'button',
            name: 'login',
            text: 'Войти',
            listeners: {
                click: 'onLoginButtonClick'
            }
        },
        {
            xtype: 'button',
            name: 'logout',
            text: 'Выйти',
            listeners: {
                click: 'onLogoutButtonClick'
            }
        }
    ]

});