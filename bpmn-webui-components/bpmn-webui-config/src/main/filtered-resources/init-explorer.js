
if(!Signavio){ var Signavio = {} };
if (!Signavio.Core) { Signavio.Core = {} };
Signavio.Core.Version = "${webui-version}";

Signavio.Config.EXPLORER_PATH           = '/${webui-webapp-context}/explorer';
Signavio.Config.EDITOR_PATH                     = '/${webui-webapp-context}/editor';
Signavio.Config.BACKEND_PATH            = '/${webui-webapp-context}/p';
Signavio.Config.LIBS_PATH                       = '/${webui-webapp-context}/libs';

Signavio.Config.DIAGRAMS_IMAGE_PATH = Signavio.Config.EXPLORER_PATH + "/src/img"
Signavio.Config.STENCILSET_EXTENSION_PATH = Signavio.Config.EDITOR_PATH + "/stencilsets/extensions/";
Signavio.Config.PATH                            = Signavio.Config.EXPLORER_PATH + '/src/javascript';
Signavio.Config.PLUGIN_PATH             = Signavio.Config.EXPLORER_PATH + '/src/javascript/plugins';
Signavio.Config.EDITOR_HANDLER_URI      = Signavio.Config.BACKEND_PATH + "/editor";
Signavio.Config.STENCILSET_URI          = Signavio.Config.BACKEND_PATH + "/editor_stencilset";
Signavio.Config.PLUGIN_CONFIG           = Signavio.Config.BACKEND_PATH + "/explorer_plugins";
Signavio.Config.USE_CACHE                       = false;
Signavio.Config.REMOVE_ON_DELETE        = true;
Signavio.Config.HIDE_NOTIFICATION       = true;
