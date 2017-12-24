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
                xtype: 'textfield',
                fieldLabel: 'Текст сообщения',
                name: 'text'
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
        ];
    }

});