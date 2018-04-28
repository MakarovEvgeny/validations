Ext.define('app.controllers.TagWindowController', {
    extend: 'app.controllers.ModelWindowController',
    alias: 'controller.tag-window-controller',

    requires: [
        'app.models.Tag'
    ],

    /** @override */
    createEmptyModel: function () {
        return Ext.create('app.models.Tag');
    },

    /** @override */
    getChangesUrlPart: function () {
        return 'tag';
    }

});
