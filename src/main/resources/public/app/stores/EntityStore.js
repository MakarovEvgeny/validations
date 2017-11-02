Ext.define('app.stores.EntityStore', {
    extend: 'Ext.data.Store',
    requires: ['app.models.Entity'],

    model: 'app.models.Entity',

    autoLoad: true,

    remoteFilter: true,

    proxy: {
        type: 'rest',
        url: '/entity',
        actionMethods: {
            read: 'POST'
        },
        paramsAsJson: true,
        // override
        // https://www.sencha.com/forum/showthread.php?297692-sortParam-is-sent-as-String-when-using-paramsAsJson-true
        applyEncoding: function (value) {
            var result = [];
            Ext.each(value, function (item) {

                if (Ext.isArray(item.value)) {
                    Ext.each(item.value, function (itemValue) {
                        result.push(
                            {
                                property: item.property,
                                value: itemValue,
                                operator: item.operator
                            }
                        )
                    }, this);

                } else {
                    result.push(item);
                }

            }, this);

            return result;
        },
        api: {
            read: 'entity/query'
        },
        reader: {
            type: 'json'
        }
    }

});