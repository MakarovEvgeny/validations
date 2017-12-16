Ext.define('app.controllers.LoginWindowController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login-window-controller',

    requires: ['app.views.LoginPanelConfigurer'],

    onButtonClick: function () {
        var window = this.getView();
        var username = window.down('textfield[name="username"]').getValue();
        var password = window.down('textfield[name="password"]').getValue();

        Ext.Ajax.request({
            url: 'login',
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            rawData: 'username=' + username + '&password=' + password,
            success: function() {
                app.views.LoginPanelConfigurer.configureButtons();
                window.close(); // Аутентификация прошла успешно.
            },
            failure: function () {
                Ext.Msg.show({
                    title:'Ошибка аутентификации',
                    message: 'Логин или пароль неверны',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.ERROR
                });
            }
        });
    }

});