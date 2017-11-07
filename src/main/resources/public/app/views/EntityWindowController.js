Ext.define('app.views.EntityWindowController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.entity-window-controller',

    requires: [
        'app.models.Entity'
    ],

    /** Получим данные с формы. */
    getEntity: function () {
        var entity = Ext.create('app.models.Entity');
        this.getView().down('form').updateRecord(entity);
        return entity;
    },

    createEntity: function () {
        var entity = this.getEntity();

        entity.phantom = true; //у нас проставлен id, надо установить этот признак чтобы не выполнилась операция update.
        entity.save();
    },

    editEntity: function () {
        var entity = this.getEntity();

        entity.phantom = false; // установим признак чтобы выполнилась операция update.
        entity.save();
    },

    deleteEntity: function () {
        var entity = this.getEntity();

        entity.dropped = true;
        entity.phantom = false; // Установим признаки для удаления записи.
        entity.save();
    }

});