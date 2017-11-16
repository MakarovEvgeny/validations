Ext.define('app.views.ModelWindow', {
    extend: 'Ext.window.Window',
    requires: [
        'app.views.EntityWindowController'
    ],

    width: 350,
    height: 400,

    constructor: function () {
        this.callParent(arguments);
        this.configureFields();
    },

    initComponent: function () {
        this.callParent(arguments);
        var formItems = this.getFormItems();
        var form = this.down('form');
        Ext.Array.each(formItems, function (item) {
            form.add(item);
        });
    },

    configureFields: function () {
        if (this.operation === 'create') {
            this.title = 'Создать';
            this.down('button[name=edit]').hide();
            this.down('button[name=delete]').hide();
        } else {
            this.title = 'Редактировать';
            this.down('textfield[name=id]').setReadOnly(true);
            this.down('button[name=create]').hide(true);
        }
    },

    /** @protected */
    getFormItems: function () {
        throw 'should be overridden';
    },

    items: [

        {
            xtype: 'form',
            border: false,
            layout: {
                type: 'vbox',
                align: 'left'
            },

            defaults: {
                margin: '5 10 5 10',
                labelWidth: 150
            }
        }

    ],

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
            text: 'Редактировать',
            listeners: {
                click: 'edit'
            }
        },
        {
            xtype: 'button',
            name: 'delete',
            scale: 'large',
            text: 'Удалить',
            listeners: {
                click: 'delete'
            }
        }
    ]

});