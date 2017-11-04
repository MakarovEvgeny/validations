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
        labelWidth: 3,
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
        me.task = new Ext.util.DelayedTask(me.processFiltersState, me);
        this.toDelete = [];

    },

    /**
     * @protected
     * Функция для обработки состояния фильтров.
     * @param {boolean} skipMark - не использовать алгоритм определения активности фильтра для столюца.
     */
    processFiltersState: function (skipMark) {
        // Есть ли критерии поиска?
        var filterDataPresence = false;

        this.getTextFields().each(function (textfield) {
            if (!Ext.isEmpty(textfield.getValue())) {
                filterDataPresence = true;
                return false; // Прервем итерацию
            }
        });

        this.applyTextFieldDataToGridStoreFilters();

        // Когда мы явно включаем галочку в меню, фильтры могут не присутствовать и галочка слетит.
        // Флаг для того чтобы галочка не слетала.
        if (skipMark !== true) {
            this.markFilterAndColumn(filterDataPresence);
        }
        this.active = filterDataPresence;
    },

    /**
     * @private
     * Установать/снать галочку (активности фильтра) в меню фильтра колонки. Изменить подчеркивание наименования
     * колонки в соответствии с активностью фильтрации по колонке
     */
    markFilterAndColumn: function(active) {
        var me = this,
            menuItem = me.owner.activeFilterMenuItem;

        // Make sure we update the 'Filters' menu item.
        if (menuItem && menuItem.activeFilter === me) {
            menuItem.suspendEvents(); // проигнорируем все события.
            menuItem.setChecked(active);
            menuItem.resumeEvents();
        }

        me.setColumnActive(active);
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

        config = this.getTextFieldConfig();

        var inputItem = me.menu.add(config);

        this.setListenersForTextField(inputItem, me);
    },


    /**
     * @protected
     * Получим параметры для создания поля ввода критерия поиска.
     */
    getTextFieldConfig: function () {
        var config = Ext.apply({}, this.getItemDefaults());
        // if (config.iconCls && !('labelClsExtra' in config)) {
        //     config.labelClsExtra = Ext.baseCSSPrefix + 'grid-filters-icon ' + config.iconCls;
        // }
        // delete config.iconCls;

        config.emptyText = config.emptyText || this.emptyText;

        return config;
    },

    /**
     * Повесим обработчики событий на компонент.
     * @param textfield поле для ввода критерия поиска
     * @param scope контекст
     */
    setListenersForTextField: function (textfield, scope) {
        textfield.on({
            scope: scope,
            change: scope.onValueChange,
            el: {
                click: function(e) {
                    e.stopPropagation();
                }
            }
        });
    },


    /**
     * @protected
     * Обновим фильтры у стора.
     */
    applyTextFieldDataToGridStoreFilters: function () {
        var filterCollection = this.getGridStore().getFilters();
        filterCollection.beginUpdate();

        // Сбросим текущие фильтры.
        this.clearFilters();

        var values = [];
        this.getTextFields().each(function (textfield) {
                var value = textfield.getValue();
                if (!Ext.isEmpty(value)) {
                    values.push(value);
                }
            }
            , this);

        this.getStoreFilters().add(this.createFilter({
            operator: this.operator,
            value: values
        }));
        filterCollection.endUpdate();
    },


    /**
     * @private
     * Handler method called when there is a significant event on an input item.
     */
    onValueChange: function (field, e) {
        var me = this,
            updateBuffer = me.updateBuffer;

        Ext.each(this.toDelete, function (f) {
            f.destroy();
        });
        this.toDelete = [];

        //<debug>
        if (!field.isFormField) {
            Ext.raise('`field` should be a form field instance.');
        }
        //</debug>

        if (field.isValid()) {

            if (Ext.isEmpty(field.getValue()) && this.getEmptyTextFieldsCount() > 1) {

                // me.menu.remove(field);
                // Уничтожение элемента меню почему-то приводит к: Uncaught TypeError: Cannot read property 'removeCls' of null
                // За вменяемое время разобраться в причинах не удалось, проблему решает отложенное уничтожение textfield-а.

                var removed = me.menu.remove(field, false);
                this.toDelete.push(removed); //Компоненты которые уничтожаться при следующем изменении значений фильтров.



            } else if (!Ext.isEmpty(field.getValue()) && this.getEmptyTextFieldsCount() === 0) {
                var textfield = me.menu.add(this.getTextFieldConfig());
                this.setListenersForTextField(textfield, me);
            }

            if (updateBuffer) {
                me.task.delay(updateBuffer);
            } else {
                me.processFiltersState(false);
            }
        }
    },


    /**
     * @private
     * Количество незаполненных компонентов, требуется для логики добавления/удаление новых.
     */
    getEmptyTextFieldsCount: function () {
        var count = 0;
        this.getTextFields().each(function (textfield) {
            if (Ext.isEmpty(textfield.getValue())) {
                count++;
            }

        });

        return count;
    },

    /**
     * @protected
     * Получим все textfield-ы в которые вводятся поисковые значения.
     * @return {Ext.util.ItemCollection}
     */
    getTextFields: function() {
        return this.owner.activeFilterMenuItem.menu.items;
    },

    /**
     * @override
     * Вызывается когда в меню столбца включается/выключается галочка активности фильтра.
     * @param active
     * @see Ext.grid.filters.filter.Base
     */
    setActive: function (active) {
        if (active) {
            this.processFiltersState(true);
            this.markFilterAndColumn(true);
        } else {
            this.clearFilters();
            this.markFilterAndColumn(false);
            this.active = false; // Обязаны высталвять этот признак, т.к. он предустанавливается в меню при его повторном открытии.
        }

    },

    /**
     * @private
     * Очистим фильтры которые относятся к текущему столбцу.
     */
    clearFilters: function () {
        var filtersToRemove = [];
        this.getStoreFilters().each(function (filter) {
            if (filter.getProperty() === this.dataIndex) {
                filtersToRemove.push(filter)
            }
        }, this);
        //Очистим фильры, метод бросит события.
        this.getStoreFilters().remove(filtersToRemove);
    },

    /** @override */
    activateMenu: function () {
    //     this.inputItem.setValue(this.filter.getValue());
    }

});