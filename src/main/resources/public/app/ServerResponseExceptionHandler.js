Ext.define('app.ServerResponseExceptionHandler', {
    requires: [
        'app.views.ErrorWindow',
        'app.views.LoginWindow',
        'app.views.LoginPanelConfigurer'
    ],
    singleton: true,

    constructor: function () {
        this.callParent();

        var me = this;
        Ext.Ajax.on('requestexception', function (conn, response, options) {
            // обработка http статусов...
            me.handleResponse(response);
        });
    },

    handleResponse: function (response) {
        switch (response.status) {
            case 400: {
                Ext.create('app.views.ErrorWindow', {codesAndMessages: Ext.JSON.decode(response.responseText).data});
                break;
            }
            case 401: {
                app.views.LoginPanelConfigurer.configureButtons();
                if (Ext.ComponentQuery.query('login-window').length === 0) {
                    Ext.create('app.views.LoginWindow');
                }
            }
        }
    }

});