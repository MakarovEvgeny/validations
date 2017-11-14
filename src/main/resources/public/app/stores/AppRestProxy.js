Ext.define('app.stores.AppRestProxy', {
    extend: 'Ext.data.proxy.Rest',

    alias : 'proxy.app-rest',

    actionMethods: {
        read: 'POST'
    },
    paramsAsJson: true,
    /**
     * @override
     * @param value
     * @return {Array}
     * @see <a href="https://www.sencha.com/forum/showthread.php?297692-sortParam-is-sent-as-String-when-using-paramsAsJson-true">read this</a>
     */
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
    reader: {
        type: 'json'
    }

});