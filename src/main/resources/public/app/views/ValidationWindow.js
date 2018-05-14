Ext.define('app.views.ValidationWindow', {
    extend: 'app.views.ModelWindow',
    requires: [
        'app.controllers.ValidationWindowController',
        'app.custom.CustomCombobox',
        'app.custom.CustomTagField',
        'app.stores.ValidationEntityStore',
        'app.stores.MessageStore',
        'app.stores.SeverityStore'
    ],

    height: 650,
    controller: 'validation-window-controller',

    /** @override */
    getFormItems: function () {
        return [
            {
                xtype: 'textfield',
                fieldLabel: 'Код',
                name: 'id',
                margin: '15 10 5 10'
            },

            {
                xtype: 'combo',
                fieldLabel: 'Ошибка/Предупреждение',
                name: 'severity',
                valueField: 'id',
                displayField: 'name',
                editable: false,
                store: Ext.create(app.stores.SeverityStore),
                local: true
            },

            {
                xtype: 'grid',
                name: 'entityOperationsGrid',
                height: 200,

                sortableColumns: false,
                menuDisabled: true,

                store: Ext.create('app.stores.ValidationEntityStore'),

                columns: {
                    defaults: {
                        menuDisabled: true
                    },
                    items: [
                        {
                            header: 'Сущность',
                            dataIndex: 'entity',
                            xtype: 'widgetcolumn',
                            widget: {
                                xtype: 'custom-combo',
                                displayField: 'name',
                                valueField: 'id',
                                filterProperty: 'name',
                                store: {
                                    type: 'entity-store',
                                    autoLoad: false
                                },

                                listeners: {
                                    select: 'onEntitySelect'
                                }
                            },
                            onWidgetAttach: 'onEntityWidgetAttach',
                            flex: 1
                        },


                        {
                            header: 'Операция',
                            dataIndex: 'operations',
                            // Чтобы записи добавлялись каждая в новой строчке.
                            cellWrap: true,

                            xtype: 'widgetcolumn',
                            widget: {
                                xtype: 'custom-tagfield',
                                name: 'operationIds',
                                displayField: 'name',
                                valueField: 'id',
                                filterProperty: 'name',
                                grow: true,
                                store: {
                                    type: 'operation-store',
                                    autoLoad: false
                                },

                                listeners: {
                                    select: 'onOperationsChange'
                                }

                            },
                            onWidgetAttach: 'onOperationsWidgetAttach',
                            flex: 1
                        },


                        {
                            xtype: 'actioncolumn',
                            sortable: false,
                            items: [{
                                iconCls: 'x-message-box-error tiny',
                                tooltip: 'Удалить сущность',
                                cellFocusable: false,
                                scope: this,
                                //https://stackoverflow.com/questions/28066941/extjs-grid-handling-action-columns-click-event-in-the-controller
                                handler: this.getController().onDeleteGridRow
                            }],
                            flex: 1
                        }
                    ]
                },



                bbar: [
                    {
                        xtype: 'button',
                        name: 'add',
                        text: 'Добавить',
                        listeners: {
                            click: 'onAddGridRow'
                        }
                    },
                    {
                        xtype: 'button',
                        name: 'remove',
                        text: 'Удалить',
                        listeners: {
                            click: 'onDeleteGridRowAfterButtonClick'
                        }
                    }
                ]
            },


            {
                xtype: 'custom-combo',
                store: {
                    type: 'message-store',
                    autoLoad: false
                },
                valueField: 'id',
                displayField: 'id',
                fieldLabel: 'Код сообщения об ошибке',
                name: 'messageId'
            },
            {
                xtype: 'resizable-textarea',
                fieldLabel: 'Описание',
                flex: 1,
                name: 'description'
            },
            {
                xtype: 'resizable-textarea',
                fieldLabel: 'Комментарий',
                flex: 1,
                name: 'commentary'
            },
            {
                xtype: 'numberfield',
                name: 'version',
                hidden: true
            }
        ];
    }

});