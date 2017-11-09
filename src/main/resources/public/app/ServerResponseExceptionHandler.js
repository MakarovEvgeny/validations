Ext.define('app.ServerResponseExceptionHandler', {
    singleton: true,

    constructor: function () {
        this.callParent();

        Ext.Ajax.on('requestexception', function (conn, response, options) {
            // обработка http статусов...
        });
    }

});