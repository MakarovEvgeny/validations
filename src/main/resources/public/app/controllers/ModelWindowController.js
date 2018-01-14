Ext.define('app.controllers.ModelWindowController', {
    extend: 'Ext.app.ViewController',

    /** @protected */
    createEmptyModel: function () {
        throw 'should be overridden';
    },

    /**
     * @private
     * Получим данные с формы.
     */
    getModel: function () {
        var model = this.createEmptyModel();
        this.getView().down('form').updateRecord(model);
        return model;
    },

    /** @protected */
    create: function () {
        var model = this.getModel();

        model.phantom = true; //у нас проставлен id, надо установить этот признак чтобы не выполнилась операция update.
        this.save(model);
    },

    /** @protected */
    edit: function () {
        var model = this.getModel();

        model.phantom = false; // установим признак чтобы выполнилась операция update.
        this.save(model);
    },

    /** @protected */
    delete: function () {
        var model = this.getModel();

        model.dropped = true;
        model.phantom = false; // Установим признаки для удаления записи.
        this.save(model);
    },

    /** @private */
    save: function(model) {
        var w = this.getView();
        var gridStore = w.gridStore;
        w.setLoading(true);
        model.save({
            callback: function (record, operation, success) {
                w.setLoading(false);
                if (success) {
                    w.close();
                    gridStore.reload();
                }
            }
        });
    },

    /** @protected */
    onExpand: function () {
        // Если режим создания или данные уже загружены, то к серверу не обращаемся.
        if (this.changesLoaded !== true && this.getView().operation !== 'create') {
            this.loadChanges();
        }
    },

    /** @private */
    loadChanges: function () {
        var model = this.getModel();
        var store = Ext.create('app.stores.ChangeStore', {
            proxy: {
                type: 'ajax',
                url: '/' + this.getChangesUrlPart() + '/' + model.get('id') + '/change',
                reader: {
                    type: 'json'
                }
            }
        });

        var changesGrid = this.getView().down('grid[name=changesGrid]');
        store.load({
            scope: this,
            callback: function () {
                changesGrid.reconfigure(store);
                this.changesLoaded = true;
            }
        });
    },

    /**
     * @protected
     * Получим часть ссылки для получения изменений данных по модели.
     */
    getChangesUrlPart: function () {
        throw 'should be overridden';
    },

    /** Загрузим данные версии на форму. */
    onRowDoubleClick: function (table, record) {
        var window = this.getView();
        var model = this.getModel();
        var versionId = record.get('id');

        window.setLoading(true);

        Ext.Ajax.request({
            url: this.getChangesUrlPart() + '/' + model.get('id') + '/change/' + versionId,
            scope: this,
            success: function(response) {
                var dataObj = Ext.JSON.decode(response.responseText);
                var model = this.createEmptyModel();
                model.set(dataObj);
                this.setModelToForm(window.down('form'), model);
            },
            callback: function () {
                window.setLoading(false);
            }
        });

    },

    /**
     * @protected
     * Заполним форму данными версии.
     */
    setModelToForm: function (form, model) {
        // Версию модели обновлять не будем, чтобы была возможность сохранить старую версию в качестве актуальной.
        var version = form.down('numberfield[name=version]').getValue();
        model.set('version', version);
        form.loadRecord(model);
    }

});