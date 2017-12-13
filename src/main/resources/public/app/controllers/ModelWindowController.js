Ext.define('app.controllers.ModelWindowController', {
    extend: 'Ext.app.ViewController',

    /** @protected */
    createEmptyModel: function () {
        throw 'should be overridden';
    },

    /**
     * @private
     * Получим данные с формы.
     */
    getModel: function () {
        var model = this.createEmptyModel();
        this.getView().down('form').updateRecord(model);
        return model;
    },

    /** @protected */
    create: function () {
        var model = this.getModel();

        model.phantom = true; //у нас проставлен id, надо установить этот признак чтобы не выполнилась операция update.
        this.save(model);
    },

    /** @protected */
    edit: function () {
        var model = this.getModel();

        model.phantom = false; // установим признак чтобы выполнилась операция update.
        this.save(model);
    },

    /** @protected */
    delete: function () {
        var model = this.getModel();

        model.dropped = true;
        model.phantom = false; // Установим признаки для удаления записи.
        this.save(model);
    },

    /** @private */
    save: function(model) {
        var w = this.getView();
        var gridStore = w.gridStore;
        w.setLoading(true);
        model.save({
            callback: function (record, operation, success) {
                w.setLoading(false);
                if (success) {
                    w.close();
                    gridStore.reload();
                }
            }
        });
    }

});