Ext.define('app.controllers.RegisterWindowController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.register-window-controller',

    onButtonClick: function () {
        var window = this.getView();
        var username = window.down('textfield[name="username"]').getValue();
        var password = window.down('textfield[name="password"]').getValue();
        var password2 = window.down('textfield[name="password2"]').getValue();

        Ext.Ajax.request({
            url: 'user/register',
            method: 'POST',
            jsonData: {
                username: username,
                password: password,
                password2: password2
            },
            success: function() {
                app.views.LoginPanelConfigurer.configureButtons();
                window.close(); // Аутентификация прошла успешно.
                Ext.toast('Пользователь зарегистрирован', 'Успешная регистрация')
            }
        });
    }

});