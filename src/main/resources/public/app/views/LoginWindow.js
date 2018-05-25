Ext.define('app.views.LoginWindow', {
    extend: 'Ext.window.Window',
    requires: 'app.controllers.LoginWindowController',
    modal: true,
    autoShow: true,
    width: 450,
    resizable: false,
    layout: 'vbox',
    title: 'Войти',

    xtype: 'login-window',

    controller: 'login-window-controller',

    items: [
        {
            xtype: 'textfield',
            width: '100%',
            emptyText: 'Имя пользователя',
            height: '40',
            name: 'username'
        },
        {
            xtype: 'textfield',
            listeners: {
                specialkey: 'onSpecialKeyPress'
            },
            width: '100%',
            emptyText: 'Пароль',
            height: '40',
            name: 'password',
            inputType: 'password'
        },
        {
            xtype: 'button',
            listeners: {
                click: 'onButtonClick'
            },
            width: '100%',
            scale: 'large',
            name: 'signin',
            text: 'Войти'
        }
    ]

});