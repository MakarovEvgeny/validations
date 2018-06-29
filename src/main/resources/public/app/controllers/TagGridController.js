Ext.define('app.controllers.TagGridController', {
    extend: 'app.controllers.ModelGridController',
    alias: 'controller.tag-grid-controller',

    requires: [
        'app.views.TagWindow',
        'app.views.MergeTagWindow'
    ],

    /** @override */
    createWindow: function (config) {
        return Ext.create('app.views.TagWindow', config);
    },

    /** @override */
    loadModel: function (id, requestConfig) {
        app.models.Tag.load(id, requestConfig);
    },

    merge: function() {
        var row = this.getView().getSelectionModel().getSelection()[0];
        var mergeWindow = Ext.create('app.views.MergeTagWindow', {
            tag: row,
            gridStore: this.getView().store
        });
        mergeWindow.show();
    },

    onShow: function() {
        this.callParent(arguments);
        this.getView().getMergeButton().setHidden(false);
    },

    onSelectionChange: function (model, selected) {
        this.callParent(arguments);
        this.getView().getMergeButton().setDisabled(selected.length === 0);
    }

});
