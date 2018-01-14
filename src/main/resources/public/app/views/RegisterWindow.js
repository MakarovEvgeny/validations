Ext.define('app.views.RegisterWindow', {
    extend: 'Ext.window.Window',
    requires: 'app.controllers.RegisterWindowController',
    modal: true,
    autoShow: true,
    width: 450,
    resizable: false,
    layout: 'vbox',
    title: 'Регистрация',

    xtype: 'register-window',

    controller: 'register-window-controller',

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
            width: '100%',
            emptyText: 'Пароль',
            height: '40',
            name: 'password',
            inputType: 'password'
        },
        {
            xtype: 'textfield',
            width: '100%',
            emptyText: 'Повторный пароль',
            height: '40',
            name: 'password2',
            inputType: 'password'
        },
        {
            xtype: 'button',
            listeners: {
                click: 'onButtonClick'
            },
            width: '100%',
            scale: 'large',
            name: 'register',
            text: 'Зарегистрироваться'
        }
    ]

});