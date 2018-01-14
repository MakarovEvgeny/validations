Ext.define('app.views.OperationWindow', {
    extend: 'app.views.ModelWindow',
    requires: [
        'app.controllers.OperationWindowController'
    ],

    controller: 'operation-window-controller',

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
        ];
    }

});