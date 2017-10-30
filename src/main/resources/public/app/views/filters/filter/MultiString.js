/**
 * The string grid filter allows you to create a filter selection that limits results
 * to values matching a particular string.  The filter can be set programmatically or via
 * user input with a configurable {@link Ext.form.field.Text text field} in the filter section
 * of the column header.
 *
 * Example String Filter Usage:
 *
 *     @example
 *     var shows = Ext.create('Ext.data.Store', {
 *         fields: ['id','show'],
 *         data: [
 *             {id: 0, show: 'Battlestar Galactica'},
 *             {id: 1, show: 'Doctor Who'},
 *             {id: 2, show: 'Farscape'},
 *             {id: 3, show: 'Firefly'},
 *             {id: 4, show: 'Star Trek'},
 *             {id: 5, show: 'Star Wars: Christmas Special'}
 *         ]
 *     });
 *
 *     Ext.create('Ext.grid.Panel', {
 *         renderTo: Ext.getBody(),
 *         title: 'Sci-Fi Television',
 *         height: 250,
 *         width: 250,
 *         store: shows,
 *         plugins: 'gridfilters',
 *         columns: [{
 *             dataIndex: 'id',
 *             text: 'ID',
 *             width: 50
 *         },{
 *             dataIndex: 'show',
 *             text: 'Show',
 *             flex: 1,
 *             filter: {
 *                 // required configs
 *                 type: 'string',
 *                 // optional configs
 *                 value: 'star',  // setting a value makes the filter active.
 *                 itemDefaults: {
 *                     // any Ext.form.field.Text configs accepted
 *                 }
 *             }
 *         }]
 *     });
 */
Ext.define('app.views.filters.filter.MultiString', {
    extend: 'Ext.grid.filters.filter.SingleFilter',
    alias: 'grid.filter.multi-string',

    type: 'multi-string',

    operator: 'like',

    //<locale>
    /**
     * @cfg {String} emptyText
     * The empty text to show for each field.
     */
    emptyText: 'Enter Filter Text...',
    //</locale>

    itemDefaults: {
        xtype: 'textfield',
        enableKeyEvents: true,
        hideEmptyLabel: false,
        iconCls: Ext.baseCSSPrefix + 'grid-filters-find',
        labelSeparator: '',
        labelWidth: 29,
        margin: 0,
        selectOnFocus: true
    },

    menuDefaults: {
        // A menu with only form fields needs some body padding. Normally this padding
        // is managed by the items, but we have no normal menu items.
        bodyPadding: 3,
        showSeparator: false
    },


    /** ПОКА БЕЗ ПОДДЕРЖКИ НАЧАЛЬНЫХ ЗНАЧЕНИЙ!!!! */
    constructor: function (config) {
        var me = this;

        // Ввызов конструктора у Ext.grid.filters.filter.Base
        this.superclass.superclass.constructor.apply(this, arguments);



        this.filters = me.getStoreFilters();

        if (!Ext.isEmpty(this.filters) && this.filters.length > 0) {
            // This filter was restored from stateful filters on the store so enforce it as active.
            me.active = true;
        }

        if (me.active) {
            me.setColumnActive(true); // Подчеркивание названия колонки.
        }
    },

    /**
     * @protected
     * Получим фильтры у стора грида.
     * @return {Ext.util.FilterCollection}
     */
    getStoreFilters: function () {
        return this.getGridStore().getFilters()
    },

    /**
     * @override
     * @private
     * Template method that is to initialize the filter and install required menu items.
     * @see Ext.grid.filters.filter.Base
     */
    createMenu: function () {
        var me = this,
            config;

        me.callParent();

        config = Ext.apply({}, me.getItemDefaults());
        if (config.iconCls && !('labelClsExtra' in config)) {
            config.labelClsExtra = Ext.baseCSSPrefix + 'grid-filters-icon ' + config.iconCls;
        }
        delete config.iconCls;

        config.emptyText = config.emptyText || me.emptyText;
        var inputItem = me.menu.add(config);

        inputItem.on({
            scope: me,
            keyup: me.onValueChange,
            el: {
                click: function(e) {
                    e.stopPropagation();
                }
            }
        });
    },

    /**
     * @protected
     * Template method that is to set the value of the filter.
     * @param {Object} value The value to set the filter.
     */
    setValue: function (value) {
        var me = this;

        if (value && me.active) {
            me.value = value;
            me.updateStoreFilter();
        } else {
            me.setActive(!!value);
        }
    },

    activateMenu: function () {
        // this.inputItem.setValue(this.filter.getValue());
    },


    /**
     * @override
     * @see Ext.grid.filters.filter.SingleFilter
     */
    activate: function (showingMenu) {
        if (showingMenu) {
            this.activateMenu();
        } else {
            this.applyTextFieldDataToGridStoreFilters();
        }
    },

    /**
     * @override
     * @see Ext.grid.filters.filter.SingleFilter
     */
    deactivate: function () {
        // Сбросим текущие фильтры.
        this.getStoreFilters().clear();
    },

    /**
     * @protected
     * Обновим фильтры у стора. Вызывать в контексте отключенных событий!!!
     */
    applyTextFieldDataToGridStoreFilters: function () {
        // Сбросим текущие фильтры.
        this.getStoreFilters().clear();

        this.getTextFields().each(function (textfield) {
                var newFilter = this.createFilter({
                    operator: this.operator,
                    value: textfield.getValue()
                });
                this.getStoreFilters().add(newFilter);
            }
            , this);
    },

    /**
     * @protected
     * Получим все textfield-ы в которые вводятся поисковые значения.
     * @return {Ext.util.ItemCollection}
     */
    getTextFields: function() {
        return this.owner.activeFilterMenuItem.menu.items;
    }

});