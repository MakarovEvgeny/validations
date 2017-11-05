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
        var window = new app.views.EntityWindow();
        window.show();
    }


});