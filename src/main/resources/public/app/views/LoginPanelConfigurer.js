Ext.define('app.views.LoginPanelConfigurer', {

    statics: {
        configureButtons: function () {
            var loginPanel = Ext.ComponentQuery.query('login-panel')[0];

            var loginButton = loginPanel.down('button[name=login]');
            var logoutButton = loginPanel.down('button[name=logout]');

            var sessionCookie = Ext.util.Cookies.get('LOGGED');
            if (Ext.isEmpty(sessionCookie)) {
                loginButton.setVisible(true);
                logoutButton.setVisible(false);
            } else {
                loginButton.setVisible(false);
                logoutButton.setVisible(true);
            }
        }
    }

});