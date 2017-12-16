Ext.define('app.controllers.LoginPanelController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'app.views.LoginWindow',
        'app.views.LoginPanelConfigurer'
    ],

    alias: 'controller.login-panel-controller',

    onLoginButtonClick: function () {
        Ext.create('app.views.LoginWindow');
    },

    onLogoutButtonClick: function () {
        Ext.Ajax.request({
            url: 'logout',
            callback: function (record, operation, success) {
                app.views.LoginPanelConfigurer.configureButtons();
                if (success) {
                    Ext.Msg.show({
                        title:'Выход',
                        message: 'Вы вышли из учетной записи',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.INFO
                    });
                }
            }
        });
    }

});