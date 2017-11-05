Ext.define('app.views.EntityWindowController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.entity-window-controller',

    requires: [
        'app.models.Entity'
    ],

    createEntity: function () {
        var entity = this.getViewModel().getData().entity;
        entity.phantom = true; //у нас проставлен id, надо установить этот признак чтобы не выполнилась операция update.
        entity.save();
    }

});