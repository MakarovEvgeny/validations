Ext.define('app.stores.SeverityStore', {
    extend: 'Ext.data.Store',
    data: [
        {id: 'ERROR', name: 'Ошибка'},
        {id: 'WARNING', name: 'Предупреждение'}
    ]
});