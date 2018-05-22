Ext.define('app.views.LoginPanelConfigurer', {
    requires: [
            'app.models.UserCard'
    ],

    statics: {
        isUserLoggedIn: function () {
            var sessionCookie = Ext.util.Cookies.get('LOGGED');
            return !Ext.isEmpty(sessionCookie);
        },

        configureButtons: function () {
            var loginPanel = Ext.ComponentQuery.query('login-panel')[0];

            var registerButton = loginPanel.down('button[name=register]');
            var loginButton = loginPanel.down('button[name=login]');
            var logoutButton = loginPanel.down('button[name=logout]');
            var userNameLabel = loginPanel.down('label[name=userinfo]');

            userNameLabel.setHtml('');

            if (app.views.LoginPanelConfigurer.isUserLoggedIn()) {
                registerButton.setVisible(false);
                loginButton.setVisible(false);
                logoutButton.setVisible(true);
            } else {
                registerButton.setVisible(true);
                loginButton.setVisible(true);
                logoutButton.setVisible(false);
            }
        },
        
        loadUserCard: function () {
            var loginPanel = Ext.ComponentQuery.query('login-panel')[0];
            var userNameLabel = loginPanel.down('label[name=userinfo]');
            userNameLabel.setHtml('');

            app.models.UserCard.load(null, {
                url: 'user/whoami',
                scope: this,
                success: function (record) {
                    userNameLabel.setHtml(record.get('username'));
                }

            });

        }
    }

});