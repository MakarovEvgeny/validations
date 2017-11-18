Ext.define('app.views.MessageWindowController', {
    extend: 'app.views.ModelWindowController',
    alias: 'controller.message-window-controller',

    requires: [
        'app.models.Message'
    ],

    /** @override */
    createEmptyModel: function () {
        return Ext.create('app.models.Message');
    }

});