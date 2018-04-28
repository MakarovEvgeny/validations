Ext.define('app.controllers.ValidationWindowController', {
    extend: 'app.controllers.ModelWindowController',
    alias: 'controller.validation-window-controller',

    requires: [
        'app.models.Validation'
    ],

    /** @override */
    getModel: function () {
        var model = this.callParent();

        var tagsView = this.getView().down('custom-tagfield[name=tags]');
        var tags = Ext.Array.map(tagsView.getValueRecords(), function (tag) {
            return tag.getData();
        });
        model.set('tags', tags);

        var validationEntitiesStore = model.validationEntities();
        validationEntitiesStore.add(this.getView().down('grid').getStore().getRange());
        Ext.apply(model.data, model.getAssociatedData());

        return model;
    },

    /** @override */
    createEmptyModel: function () {
        return Ext.create('app.models.Validation');
    },

    /** @override */
    getChangesUrlPart: function () {
        return 'validation';
    },

    /** @override */
    setModelToForm: function (form, model) {
        this.callParent(arguments);

        var validationEntitiesStore = this.getView().down('grid').getStore();
        validationEntitiesStore.removeAll();

        var ve = model.get('validationEntities');
        Ext.Array.each(ve, function (item) {
            var validationEntity = Ext.create('app.models.ValidationEntity');
            validationEntity.setEntity(Ext.create('app.models.Entity', item.entity));
            validationEntity.operations().add(item.operations);
            validationEntitiesStore.add(validationEntity);
        });
    },

    /** Удаление строки с сущностью и операциями по нажатию на кнопку. */
    onDeleteGridRowAfterButtonClick: function () {
        var grid = this.getView().down('grid');
        var row = grid.getSelectionModel().getSelection()[0];
        grid.getStore().remove(row);
    },

    /** Удаление строки с сущностью и операциями по нажатию на крестик той же строки. */
    onDeleteGridRow: function (view, rowIndex) {
        view.getStore().removeAt(rowIndex);
    },

    /** Добавим новую строку в таблицу сущность-операции, но только в том случае если в таблице отсутствуют незаполненные строки. */
    onAddGridRow: function () {
        var store = this.getView().down('grid').getStore();
        var row = Ext.create('app.models.ValidationEntity');

        if (!this.hasNotFilledRow(store)) {
            store.add(row);
        }

    },

    /** Есть ли незаполненная строка в гриде? */
    hasNotFilledRow: function (store) {
        var hasNotFilledRow = false;

        store.each(function (each) {
            if (Ext.isEmpty(each.getEntity()) || each.operations().count() === 0) {
                hasNotFilledRow = true;
                return false;
            }
        });

        return hasNotFilledRow;
    },

    initTagView: function(record) {
        var tagsField = this.getView().down('custom-tagfield[name=tags]');
        tagsField.suspendEvents(false);
        tagsField.getStore().add(record.tags().getRange()); // Чтобы не пропадали значения установленные программно, т.к. forceSelection = true.
        tagsField.setValue(record.tags().getRange());
        tagsField.resumeEvents();
    },

    /** При выборе сущности установим значение в соответствующей записи ValidationEntityStore. */
    onEntitySelect: function (combo, selectedRecord) {
        combo.getWidgetRecord().setEntity(selectedRecord);
    },

    /** При выборе операций установим значения в соответствующей записи ValidationEntityStore. */
    onOperationsChange: function (tag, newValue) {
        var operations = tag.getWidgetRecord().operations();
        operations.removeAll();
        operations.add(newValue);
    },

    /** Обработчик создания выпадающего списка сущностей, заполнение начальными данными если они есть в ValidationEntityStore. */
    onEntityWidgetAttach: function (column, combo, record) {
        combo.suspendEvents(false);
        combo.getStore().removeAll();
        combo.getStore().add(record.getEntity()); // Чтобы не пропадали значения установленные программно, т.к. forceSelection = true.
        combo.setValue(record.getEntity());
        combo.resumeEvents();
    },

    /** Обработчик создания выпадающего списка операций с множественным выбором, заполнение начальными данными если они есть в ValidationEntityStore. */
    onOperationsWidgetAttach: function (column, tag, record) {
        tag.suspendEvents(false);
        tag.getStore().add(record.operations().getRange()); // Чтобы не пропадали значения установленные программно, т.к. forceSelection = true.
        tag.setValue(record.operations().getRange());
        tag.resumeEvents();
    }

});