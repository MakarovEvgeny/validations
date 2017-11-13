Ext.define('app.ServerResponseExceptionHandler', {
    requires: ['app.views.ErrorWindow'],
    singleton: true,

    constructor: function () {
        this.callParent();

        Ext.Ajax.on('requestexception', function (conn, response, options) {
            // обработка http статусов...
            switch (response.status) {
                case 400: {
                    Ext.create('app.views.ErrorWindow', {codesAndMessages: Ext.JSON.decode(response.responseText).data});
                }
            }
        });
    }

});