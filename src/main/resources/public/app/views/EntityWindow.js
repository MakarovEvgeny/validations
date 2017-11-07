Ext.define('app.views.EntityWindow', {
    extend: 'Ext.window.Window',
    requires: [
        'app.views.EntityWindowController'
    ],

    controller: 'entity-window-controller',

    width: 350,
    height: 400,

    constructor: function (operation) {
        this.callParent(arguments);

        if (operation === 'create') {
            this.title = 'Создать';
            this.down('button[name=edit]').hide();
            this.down('button[name=delete]').hide();
        } else {
            this.title = 'Редактировать';
            this.down('textfield[name=id]').setReadOnly(true);
            this.down('button[name=create]').hide(true);
        }

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
            },

            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Код',
                    name: 'id'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Наименование',
                    name: 'name'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Описание',
                    name: 'description'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Комментарий',
                    name: 'commentary'
                },
                {
                    xtype: 'numberfield',
                    name: 'version',
                    hidden: true
                }
            ]
        }

    ],

    bbar: [
        {
            xtype: 'button',
            name: 'create',
            scale: 'large',
            text: 'Создать',
            listeners: {
                click: 'createEntity'
            }
        },
        {
            xtype: 'button',
            name: 'edit',
            scale: 'large',
            text: 'Редактировать',
            listeners: {
                click: 'editEntity'
            }
        },
        {
            xtype: 'button',
            name: 'delete',
            scale: 'large',
            text: 'Удалить',
            listeners: {
                click: 'deleteEntity'
            }
        }
    ]

});