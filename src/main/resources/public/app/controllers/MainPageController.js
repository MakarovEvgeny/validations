Ext.define('app.controllers.MainPageController', {
    extend : 'Ext.app.ViewController',
    alias: 'controller.main-page-controller',

    onExportExcelClick: function () {
        Ext.create('Ext.Component', {
            renderTo: Ext.getBody(),
            cls: 'x-hidden',
            autoEl: {
                tag: 'iframe',
                src: 'export/excel'
            }
        });
    }
});