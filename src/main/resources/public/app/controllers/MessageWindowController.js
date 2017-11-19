Ext.define('app.controllers.MessageWindowController', {
    extend: 'app.controllers.ModelWindowController',
    alias: 'controller.message-window-controller',

    requires: [
        'app.models.Message'
    ],

    /** @override */
    createEmptyModel: function () {
        return Ext.create('app.models.Message');
    }

});