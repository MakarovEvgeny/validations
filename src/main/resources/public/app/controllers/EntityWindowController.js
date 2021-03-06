Ext.define('app.controllers.EntityWindowController', {
    extend: 'app.controllers.ModelWindowController',
    alias: 'controller.entity-window-controller',

    requires: [
        'app.models.Entity'
    ],

    /** @override */
    createEmptyModel: function () {
        return Ext.create('app.models.Entity');
    },

    /** @override */
    getChangesUrlPart: function () {
        return 'entity';
    }

});