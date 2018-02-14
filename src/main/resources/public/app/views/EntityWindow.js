Ext.define('app.views.EntityWindow', {
    extend: 'app.views.ModelWindow',
    requires: [
        'app.controllers.EntityWindowController'
    ],

    controller: 'entity-window-controller',

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
                xtype: 'textareafield',
                fieldLabel: 'Описание',
                name: 'description'
            },
            {
                xtype: 'textareafield',
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