Ext.define('app.views.EntityWindow', {
    extend: 'Ext.window.Window',
    requires: [
        'app.views.EntityWindowController'
    ],

    controller: 'entity-window-controller',

    title: 'Создать',
    width: 350,
    height: 400,

    layout: {
        type: 'vbox',
        align: 'left'
    },

    defaults: {
        margin: '5 10 5 10',
        labelWidth: 150
    },


    constructor: function (entityId) {
        this.callParent();

        if (Ext.isEmpty(entityId)) {
            this.setViewModel({
                data: {
                    entity: Ext.create('app.models.Entity')
                }
            });
        }
    },

    items: [

        {
            xtype: 'textfield',
            fieldLabel: 'Код',
            bind: {
                value: '{entity.data.id}'
            },
            name: 'id'
        },
        {
            xtype: 'textfield',
            fieldLabel: 'Наименование',
            bind: {
                value: '{entity.data.name}'
            },
            name: 'name'
        },
        {
            xtype: 'textfield',
            fieldLabel: 'Описание',
            bind: {
                value: '{entity.data.description}'
            },
            name: 'description'
        },
        {
            xtype: 'textfield',
            fieldLabel: 'Комментарий',
            bind: {
                value: '{entity.data.commentary}'
            },
            name: 'commentary'
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
        }
    ]

});