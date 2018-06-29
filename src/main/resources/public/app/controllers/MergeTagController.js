Ext.define('app.controllers.MergeTagController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.merge-tag-window-controller',

    requires: [
        'app.models.MergeTag'
    ],

    configure: function(config) {
        var mainTag = config.tag;
        this.getView().getTagCombo().setValue(mainTag);
    },

    merge: function() {
        var model = Ext.create('app.models.MergeTag');
        this.getView().down('form').updateRecord(model);
        //у нас проставлен id, надо установить этот признак чтобы не выполнилась операция update.
        model.phantom = true;

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
