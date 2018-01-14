Ext.define('app.views.LoginPanelConfigurer', {

    statics: {
        configureButtons: function () {
            var loginPanel = Ext.ComponentQuery.query('login-panel')[0];

            var registerButton = loginPanel.down('button[name=register]');
            var loginButton = loginPanel.down('button[name=login]');
            var logoutButton = loginPanel.down('button[name=logout]');

            var sessionCookie = Ext.util.Cookies.get('LOGGED');
            if (Ext.isEmpty(sessionCookie)) {
                registerButton.setVisible(true);
                loginButton.setVisible(true);
                logoutButton.setVisible(false);
            } else {
                registerButton.setVisible(false);
                loginButton.setVisible(false);
                logoutButton.setVisible(true);
            }
        }
    }

});