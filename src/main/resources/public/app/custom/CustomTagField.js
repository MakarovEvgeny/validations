Ext.define('app.custom.CustomTagField', {
    extend: 'Ext.form.field.Tag',
    xtype: 'custom-tagfield',

    filterProperty: 'id',
    filterOperator: 'like',

    minChars: 1,

    /** @override */
    getParams: function(queryString) {
        var params = {
            filter: []
        };

        if (!Ext.isEmpty(queryString)) {
            params.filter.push({
                property: this.filterProperty,
                value: queryString,
                operator: this.filterOperator
            });
        }

        return params;
    }

});