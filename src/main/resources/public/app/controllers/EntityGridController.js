Ext.define('app.controllers.EntityGridController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.entity-grid-controller',

    requires: [
        'app.views.EntityWindow'
    ],

    onEntityGridSelectionChange: function (model, selected) {
        if (selected.length > 0) {
            this.getView().getEditButton().setDisabled(false);
        }
    },

    createEntity: function () {
        var window = new app.views.EntityWindow('create');
        window.show();
    },

    editEntity: function () {
        var row = this.getView().getSelectionModel().getSelection()[0];

        var window = Ext.create('app.views.EntityWindow', {
            operation: 'edit'
        });
        window.show();
        window.setLoading(true);

        app.models.Entity.load(row.get('id'), {
            scope: this,
            success: function (record) {
                window.down('form').loadRecord(record);
            },
            callback: function () {
                window.setLoading(false);
            }
        });

    }


});