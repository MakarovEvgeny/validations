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
            xtype: 'label',
            name: 'userinfo',
            style: 'padding-right: 20px; padding-top: 3px',
            html: ''

        },
        {
            xtype: 'button',
            name: 'register',
            text: 'Зарегистрироваться',
            listeners: {
                click: 'onRegisterButtonClick'
            }
        },
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