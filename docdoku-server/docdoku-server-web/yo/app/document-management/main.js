/*global _,require,window*/
var workspace = /^#([^\/]+)/.exec(window.location.hash);
if(!workspace){
    location.href = '../faces/admin/workspace/workspacesMenu.xhtml';
    throw new Error('Cannot parse workspace in url');
}
var App = {
    debug:false,
	config:{
		workspaceId: decodeURIComponent(workspace[1]).trim() || null,
		login: '',
		groups: [],
		contextPath: '',
		locale: window.localStorage.getItem('locale') || 'en'
	}
};


App.log=function(message){
    'use strict';
    if(App.debug){
        window.console.log(message);
    }
};

require.config({

    baseUrl: '../js/document-management',

    shim: {
        jqueryUI: { deps: ['jquery'], exports: 'jQuery' },
        effects: { deps: ['jquery'], exports: 'jQuery' },
        popoverUtils: { deps: ['jquery'], exports: 'jQuery' },
        inputValidity: { deps: ['jquery'], exports: 'jQuery' },
        bootstrap: { deps: ['jquery', 'jqueryUI'], exports: 'jQuery' },
        bootbox: { deps: ['jquery'], exports: 'jQuery' },
        datatables: { deps: ['jquery'], exports: 'jQuery' },
	    unmask: { deps: ['jquery'], exports: 'jQuery' },
	    unmaskConfig: { deps: ['unmask','jquery'], exports: 'jQuery' },
        bootstrapSwitch: { deps: ['jquery'], exports: 'jQuery'},
        bootstrapDatepicker: {deps: ['jquery','bootstrap'], exports: 'jQuery'},
        backbone: { deps: ['underscore', 'jquery'], exports: 'Backbone'},
        date_picker_lang: { deps: ['bootstrapDatepicker'], exports: 'jQuery'}
    },

    paths: {
        jquery: '../../bower_components/jquery/jquery',
        backbone: '../../bower_components/backbone/backbone',
        underscore: '../../bower_components/underscore/underscore',
        mustache: '../../bower_components/mustache/mustache',
        text: '../../bower_components/requirejs-text/text',
        i18n: '../../bower_components/requirejs-i18n/i18n',
        buzz: '../../bower_components/buzz/dist/buzz',
        bootstrap: '../../bower_components/bootstrap/docs/assets/js/bootstrap',
        bootbox:'../../bower_components/bootbox/bootbox',
        datatables: '../../bower_components/datatables/media/js/jquery.dataTables',
        jqueryUI: '../../bower_components/jqueryui/ui/jquery-ui',
        unmask:'../../bower_components/jquery-maskedinput/dist/jquery.maskedinput',
        bootstrapSwitch:'../../bower_components/bootstrap-switch/static/js/bootstrap-switch',
        bootstrapDatepicker:'../../bower_components/bootstrap-datepicker/js/bootstrap-datepicker',
        date:'../../bower_components/date.format/date.format',
        unorm:'../../bower_components/unorm/lib/unorm',
        moment:'../../bower_components/moment/min/moment-with-locales',
        momentTimeZone:'../../bower_components/moment-timezone/builds/moment-timezone-with-data',
        unmaskConfig:'../utils/jquery.maskedinput-config',
        localization: '../localization',
        modules: '../modules',
        'common-objects': '../common-objects',
        effects: '../utils/effects',
        popoverUtils: '../utils/popover.utils',
        inputValidity: '../utils/input-validity',
        datatablesOsortExt: '../utils/datatables.oSort.ext',
        utilsprototype: '../utils/utils.prototype',
        userPopover: 'modules/user-popover-module/app',
        async: '../../bower_components/async/lib/async',
        date_picker_lang: '../../bower_components/bootstrap-datepicker/js/locales/bootstrap-datepicker.fr'
    },

    deps: [
        'jquery',
        'underscore',
        'date',
        'bootstrap',
        'bootbox',
        'bootstrapSwitch',
        'jqueryUI',
        'effects',
        'popoverUtils',
        'inputValidity',
        'datatables',
        'datatablesOsortExt',
        'unmaskConfig',
        'utilsprototype',
        'date_picker_lang'
    ],
    config: {
        i18n: {
            locale: (function(){
	            'use strict';
                try{
                    return App.config.locale;
                }catch(ex){
                    return 'en';
                }
            })()
        }
    }
});

require(['common-objects/contextResolver','i18n!localization/nls/common','i18n!localization/nls/document-management'],
function (ContextResolver,  commonStrings, documentManagementStrings) {
    'use strict';

    App.config.i18n = _.extend(commonStrings,documentManagementStrings);

    ContextResolver.resolveUser(function(){
        require(['backbone','app','router','common-objects/views/header','modules/all'],function(Backbone, AppView, Router,HeaderView,Modules,date_picker_lang){
            App.appView = new AppView().render();
            App.headerView = new HeaderView().render();
            App.router = Router.getInstance();
            App.coworkersView = new Modules.CoWorkersAccessModuleView().render();
            Backbone.history.start();
        });
    });
});
