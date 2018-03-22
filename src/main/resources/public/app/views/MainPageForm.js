Ext.define('app.views.MainPageForm', {
    extend: 'Ext.form.Panel',
    requires: [
            'app.controllers.MainPageController'
    ],
    controller: 'main-page-controller',

    xtype: 'main-page-form',
    items: [
        {
            xtype: 'label',
            html:
            '<div style="padding: 30px">' +
                '<h1>Управление валидациями.</h1><p>TODO Информация <b>о продукте</b> </p>.' +
                '<p>TODO Рекомендации к заведению валидаций.</p>' +
                '<p>TODO Возможно, красивые графики.</p>' +
                '<p>TODO Возможно, очень красивые графики.</p>' +
            '</div>'

        }
    ],

    dockedItems: [{
        xtype: 'toolbar',
        dock: 'bottom',
        layout: {
            type: 'vbox',
            align: 'stretch'
        },
        margin: 0,
        padding: 0,
        items: [
            {
                xtype: 'panel',
                margin: 0,
                padding: 0,
                items: [
                    {
                        xtype: 'button',
                        name: 'export-excel',
                        scale: 'large',
                        text: 'Экспортитовать в EXCEL',
                        listeners: {
                            click: 'onExportExcelClick'
                        }
                    }
                ]
            }
        ]
    }],


    getExportExcelButton: function () {
        return this.down('button[name=export-excel]');
    }

});