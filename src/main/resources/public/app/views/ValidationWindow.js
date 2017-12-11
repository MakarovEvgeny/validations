Ext.define('app.views.ValidationWindow', {
    extend: 'app.views.ModelWindow',
    requires: [
        'app.controllers.ValidationWindowController',
        'app.custom.CustomTagField',
        'app.stores.SeverityStore'
    ],

    width: 450,

    controller: 'validation-window-controller',

    /** @override */
    getFormItems: function () {
        return [
            {
                xtype: 'textfield',
                fieldLabel: 'Код',
                name: 'id'
            },

            {
                xtype: 'combo',
                fieldLabel: 'Ошибка/Предупреждение',
                name: 'severity',
                valueField: 'id',
                displayField: 'name',
                store: Ext.create(app.stores.SeverityStore),
                local: true
            },

            {
                xtype: 'custom-tagfield',
                name: 'entityIds',
                fieldLabel: 'Сущности',
                displayField: 'id',
                valueField: 'id',
                maxWidth: 350,
                width: 350,
                grow: true,
                store: Ext.create('app.stores.EntityStore')
            },

            {
                xtype: 'custom-tagfield',
                name: 'operationIds',
                fieldLabel: 'Операции',
                displayField: 'id',
                valueField: 'id',
                maxWidth: 350,
                width: 350,
                grow: true,
                store: Ext.create('app.stores.OperationStore')
            },


            {
                xtype: 'textfield',
                fieldLabel: 'Код сообщения об ошибке',
                name: 'messageId'
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