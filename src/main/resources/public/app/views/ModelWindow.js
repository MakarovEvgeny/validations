Ext.define('app.views.ModelWindow', {
    extend: 'Ext.window.Window',
    requires: [
        'app.controllers.EntityWindowController',
        'app.custom.ResizableTextArea'
    ],

    layout: 'border',
    width: 850,
    height: 350,

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
            region: 'center',
            border: false,
            scrollable: true,
            layout: {
                type: 'vbox',
                align: 'left'
            },

            defaults: {
                margin: '5 10 5 10',
                labelWidth: 150,
                width: 700
            }
        },
        {
            xtype: 'panel',
            name: 'changesPanel',
            region: 'east',
            layout: 'fit',
            width: '70%',
            split: true,
            collapsible: true,
            collapsed: true,

            autoScroll: true,

            title: 'Изменения',
            listeners: {
                expand: 'onExpand'
            },
            items: [
                {
                    xtype: 'grid',
                    name: 'changesGrid',

                    listeners: {
                        rowdblclick: 'onRowDoubleClick'
                    },

                    columns: {
                        items: [
                            {
                                dataIndex: 'date',
                                flex: 1,
                                text: 'Дата',
                                renderer: function (date) {
                                    return Ext.Date.format(date, 'Y.m.d H:i:s');
                                }
                            },
                            {
                                dataIndex: 'username',
                                text: 'Пользователь',
                                flex: 1
                            },
                            {
                                dataIndex: 'commentary',
                                text: 'Комментарий',
                                flex: 3
                            }
                        ]
                    }
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