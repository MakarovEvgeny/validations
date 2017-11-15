Ext.define('app.views.ModelGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
        'app.views.filters.filter.MultiString',
        'app.controllers.ModelGridController'
    ],

    enableColumnHide: false,

    plugins: 'gridfilters',

    listeners: {
        selectionChange: 'onSelectionChange'
    },

    bbar: [
        {
            xtype: 'button',
            name: 'create',
            scale: 'large',
            text: 'Создать',
            listeners: {
                click: 'create'
            }
        },
        {
            xtype: 'button',
            name: 'edit',
            scale: 'large',
            disabled: true,
            text: 'Редактировать',
            listeners: {
                click: 'edit'
            }
        }
    ],


    getEditButton: function () {
        return this.down('button[name=edit]');
    }

});