Ext.define('app.views.ErrorWindow', {
    extend: 'Ext.window.Window',
    modal: true,
    autoShow: true,
    alias: 'error-window',

    title: 'Ошибки при выполнении операции',

    layout: 'border',

    iconCls: 'x-message-box-error',

    height: 300,
    width: 450,

    constructor: function () {
        this.callParent(arguments);

        if (!Ext.isEmpty(this.codesAndMessages) && Ext.isArray(this.codesAndMessages)) {
            var store = Ext.create('Ext.data.Store', {data: this.codesAndMessages});
            this.down('grid').reconfigure(store);
        }

        this.down('button').on('click', function () {
            this.close();
        }, this);
    },

    items: [
        {
            xtype: 'grid',
            region: 'center',
            disableSelection: true,
            store: null,
            columns: {
                items: [
                    {
                        flex: 1,
                        dataIndex: 'message'
                    }
                ]
            }
        },
        {
            xtype: 'button',
            region: 'south',
            text: 'Ок'
        }
    ]


});