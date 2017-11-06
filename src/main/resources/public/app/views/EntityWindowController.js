Ext.define('app.views.EntityWindowController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.entity-window-controller',

    requires: [
        'app.models.Entity'
    ],

    createEntity: function () {
        var entity = Ext.create('app.models.Entity');
        this.getView().down('form').updateRecord(entity);

        entity.phantom = true; //у нас проставлен id, надо установить этот признак чтобы не выполнилась операция update.
        entity.save();
    }

});