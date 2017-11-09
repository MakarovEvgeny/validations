Ext.application({
    extend: 'Ext.app.Application',
    requires: ['app.views.MainView', 'app.ServerResponseExceptionHandler'],

    name: 'app',
    paths: {
        'app': './resources/app'
    },
    mainView: 'app.views.MainView'

});