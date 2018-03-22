Ext.define('app.controllers.MainPageController', {
    extend : 'Ext.app.ViewController',
    alias: 'controller.main-page-controller',

    requires: [
        'app.ServerResponseExceptionHandler'
    ],

    onExportExcelClick: function () {
        var frame = Ext.create('Ext.Component', {
            renderTo: Ext.getBody(),
            cls: 'x-hidden',
            autoEl: {
                tag: 'iframe'
            }
        });

        frame.getEl().dom.onload = function (event) {
            var body = event.target.contentDocument.body.innerHTML;
            if (body.indexOf('status=401') !== -1) {
                app.ServerResponseExceptionHandler.handleResponse({status: 401});
            }
        };

        frame.getEl().dom.src  = 'export/excel';
    }

});