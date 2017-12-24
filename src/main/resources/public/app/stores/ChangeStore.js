Ext.define('app.stores.ChangeStore', {
    extend: 'Ext.data.Store',
    requires: [
        'app.models.Change'
    ],

    model: 'app.models.Change',

    autoLoad: false

});