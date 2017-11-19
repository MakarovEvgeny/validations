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
            },
            {
                xtype: 'numberfield',
                name: 'version',
                hidden: true
            }
        ];
    }

});