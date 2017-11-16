Ext.define('app.views.EntityWindowController', {
    extend: 'app.views.ModelWindowController',
    alias: 'controller.entity-window-controller',

    requires: [
        'app.models.Entity'
    ],

    /** @override */
    createEmptyModel: function () {
        return Ext.create('app.models.Entity');
    }

});