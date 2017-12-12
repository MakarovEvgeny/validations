Ext.define('app.ServerResponseExceptionHandler', {
    requires: ['app.views.ErrorWindow', 'app.views.LoginWindow'],
    singleton: true,

    constructor: function () {
        this.callParent();

        Ext.Ajax.on('requestexception', function (conn, response, options) {
            // обработка http статусов...
            switch (response.status) {
                case 400: {
                    Ext.create('app.views.ErrorWindow', {codesAndMessages: Ext.JSON.decode(response.responseText).data});
                    break;
                }
                case 401: {
                    Ext.create('app.views.LoginWindow');
                }
            }
        });
    }

});