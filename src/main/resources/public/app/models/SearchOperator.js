Ext.define('app.models.SearchOperator', {
    // Константы для поисковых операций
    statics: {
        EQUALS: '=',
        LIKE: 'like',
        ILIKE: 'likeIgnoreCase',
        /** Полнотекстовый поиск. */
        FTS: 'fts'
    }
});