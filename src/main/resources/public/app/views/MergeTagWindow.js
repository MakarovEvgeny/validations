Ext.define('app.views.MergeTagWindow', {
    extend: 'Ext.window.Window',
    requires: [
        'app.custom.CustomCombobox',
        'app.custom.CustomTagField',
        'app.controllers.MergeTagController'
    ],

    layout: 'border',
    width: 850,
    height: 350,
    title: 'Объединить теги',
    controller: 'merge-tag-window-controller',

    constructor: function (config) {
        this.callParent(arguments);
        this.getController().configure(config);
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
            },

            items: [
                {
                    xtype: 'custom-combo',
                    store: {
                        type: 'tag-store',
                        autoLoad: false
                    },
                    valueField: 'id',
                    displayField: 'name',
                    fieldLabel: 'Правильный тег',
                    name: 'tagId'
                },
                {
                    xtype: 'custom-tagfield',
                    fieldLabel: 'Поглощаемые теги',
                    name: 'mergeTagIds',
                    displayField: 'name',
                    valueField: 'id',
                    store: {
                        type: 'tag-store',
                        autoLoad: false
                    }
                }
            ]
        }
    ],

    bbar: [
        {
            xtype: 'button',
            name: 'merge',
            scale: 'large',
            text: 'Объединить',
            listeners: {
                click: 'merge'
            }
        }
    ],

    getTagCombo: function() {
        return this.down('custom-combo[name=tagId]');
    },

    getMergeTagsField: function() {
        return this.down('custom-tagfield[name=mergeTagIds]');
    }

});
