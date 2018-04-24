Ext.define('app.views.MessageWindow', {
    extend: 'app.views.ModelWindow',
    requires: [
        'app.controllers.MessageWindowController'
    ],

    controller: 'message-window-controller',

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
                xtype: 'resizable-textarea',
                fieldLabel: 'Текст сообщения',
                flex: 1,
                name: 'text'
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