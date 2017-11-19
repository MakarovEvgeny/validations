/** Туллбар для постраничной навигации, не требует информации о полном количестве страниц. */
Ext.define('app.custom.NoTotalPagesPagingToolbar', {
    extend: 'Ext.toolbar.Paging',

    xtype: 'custom-pagingtoolbar',

    afterPageText: '',
    beforePageText: '',

    /**
     * @override
     * Gets the standard paging items in the toolbar
     */
    getPagingItems: function() {
        var me = this,
            inputListeners = {
                scope: me,
                blur: me.onPagingBlur
            };

        inputListeners[Ext.supports.SpecialKeyDownRepeat ? 'keydown' : 'keypress'] = me.onPagingKeyDown;

        return [
        //     {
        //     itemId: 'first',
        //     tooltip: me.firstText,
        //     overflowText: me.firstText,
        //     iconCls: Ext.baseCSSPrefix + 'tbar-page-first',
        //     disabled: true,
        //     handler: me.moveFirst,
        //     scope: me
        // },
            {
            itemId: 'prev',
            tooltip: me.prevText,
            overflowText: me.prevText,
            iconCls: Ext.baseCSSPrefix + 'tbar-page-prev',
            disabled: true,
            handler: me.movePrevious,
            scope: me
        },
            // '-',
            me.beforePageText,
            {
                xtype: 'numberfield',
                itemId: 'inputItem',
                name: 'inputItem',
                cls: Ext.baseCSSPrefix + 'tbar-page-number',
                allowDecimals: false,
                minValue: 1,
                hideTrigger: true,
                enableKeyEvents: true,
                keyNavEnabled: false,
                selectOnFocus: true,
                submitValue: false,
                // mark it as not a field so the form will not catch it when getting fields
                isFormField: false,
                width: me.inputItemWidth,
                margin: '-1 2 3 2',
                listeners: inputListeners
            },
            {
                xtype: 'tbtext',
                itemId: 'afterTextItem',
                html: Ext.String.format(me.afterPageText, 1)
            },
            // '-',
            {
                itemId: 'next',
                tooltip: me.nextText,
                overflowText: me.nextText,
                iconCls: Ext.baseCSSPrefix + 'tbar-page-next',
                disabled: true,
                handler: me.moveNext,
                scope: me
            },
            // {
            //     itemId: 'last',
            //     tooltip: me.lastText,
            //     overflowText: me.lastText,
            //     iconCls: Ext.baseCSSPrefix + 'tbar-page-last',
            //     disabled: true,
            //     handler: me.moveLast,
            //     scope: me
            // },
            // '-',
            {
                itemId: 'refresh',
                tooltip: me.refreshText,
                overflowText: me.refreshText,
                iconCls: Ext.baseCSSPrefix + 'tbar-loading',
                disabled: me.store.isLoading(),
                handler: me.doRefresh,
                scope: me
            }];
    },


    /**
     * @override
     */
    getPageData: function() {
        var store = this.store,
            totalCount = (store.currentPage + 1) * store.pageSize,
            // totalCount = totalCount = store.getTotalCount(),
            pageCount = Math.ceil(totalCount / store.pageSize),
            toRecord = Math.min(store.currentPage * store.pageSize, totalCount);
        // var store = this.store,
        //     totalCount = store.getTotalCount(),
        //     pageCount = Math.ceil(totalCount / store.pageSize),
        //     toRecord = Math.min(store.currentPage * store.pageSize, totalCount);

        return {
            total : totalCount,
            currentPage : store.currentPage,
            pageCount: Ext.Number.isFinite(pageCount) ? pageCount : 1,
            fromRecord: ((store.currentPage - 1) * store.pageSize) + 1,
            toRecord: toRecord || totalCount
        };
    },


    /**
     * @override
     */
    onLoad : function(){
        var me = this,
            pageData,
            currPage,
            pageCount,
            afterText,
            count,
            isEmpty,
            item;

        count = me.store.getCount();
        isEmpty = count === 0;

        if (!isEmpty) {
            pageData = me.getPageData();
            currPage = pageData.currentPage;
            pageCount = pageData.pageCount;

            // Check for invalid current page.
            if (currPage > pageCount) {
                // If the surrent page is beyond the loaded end,
                // jump back to the loaded end if there is a valid page count.
                if (pageCount > 0) {
                    me.store.loadPage(pageCount);
                }
                // If no pages, reset the page field.
                else {
                    me.getInputItem().reset();
                }
                return;
            }

            afterText = Ext.String.format(me.afterPageText, isNaN(pageCount) ? 1 : pageCount);
        } else {
            currPage = me.store.currentPage;
            // currPage = 0;
            pageCount = me.store.currentPage;
            // pageCount = 0;
            afterText = Ext.String.format(me.afterPageText, currPage);
            // afterText = Ext.String.format(me.afterPageText, 0);
        }

        // Ext.suspendLayouts();
        item = me.child('#afterTextItem');
        if (item) {
            item.update(afterText);
        }
        item = me.getInputItem();
        if (item) {
            // item.setDisabled(isEmpty).setValue(currPage);
            item.setDisabled(false).setValue(currPage);
        }
        me.setChildDisabled('#first', false);
        // me.setChildDisabled('#first', currPage === 1 || isEmpty);
        me.setChildDisabled('#prev', false);
        // me.setChildDisabled('#prev', currPage === 1 || isEmpty);
        me.setChildDisabled('#next', false);
        // me.setChildDisabled('#next', currPage === pageCount  || isEmpty);
        me.setChildDisabled('#last', false);
        // me.setChildDisabled('#last', currPage === pageCount  || isEmpty);
        me.setChildDisabled('#refresh', false);
        me.updateInfo();
        // Ext.resumeLayouts(true);

        if (!me.calledInternal) {
            me.fireEvent('change', me, pageData || me.emptyPageData);
        }
    }

});