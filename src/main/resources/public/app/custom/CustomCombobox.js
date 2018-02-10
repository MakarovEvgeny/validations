Ext.define('app.custom.CustomCombobox', {
    extend: 'Ext.form.field.ComboBox',
    xtype: 'custom-combo',

    filterProperty: 'id',
    filterOperator: app.models.SearchOperator.ILIKE,

    forceSelection: true, // Произвольный текст не должен оставаться в выпадающем списке в качестве выбранного значения.
    triggerAction: 'query',

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