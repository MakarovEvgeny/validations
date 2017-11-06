Ext.define('app.views.EntityWindow', {
    extend: 'Ext.window.Window',
    requires: [
        'app.views.EntityWindowController'
    ],

    controller: 'entity-window-controller',

    title: 'Создать',
    width: 350,
    height: 400,

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
        }
    ]

});