(function ($) {
    $.extend({
        //弹窗蒙层
        getBpmnBasePath: function () {
            var jd = window.document.location.href;
            var xd = window.document.location.pathname;
            var localhostPath = jd.substring(0, jd.indexOf(xd));
            var pos1 = xd.substr(1).indexOf("/") + 1;
            var pos2 = xd.substr(pos1 + 1).indexOf("/");
            var projectNameAndFirstLevelPath = xd.substring(0, pos1 + 1 + pos2 + 1);
            return localhostPath + projectNameAndFirstLevelPath;
        }
    })
}(jQuery));

function getArgsValue(sArgName) {
    var sHref = window.document.location.href;
    var args = sHref.split("?");
    var argsValue = "";

    if (args[0] === sHref) {
        return argsValue;
    }
    var str = decodeURI(args[1]);
    args = str.split("&");
    for (var i = 0; i < args.length; i++) {
        str = args[i];
        var arg = str.split("=");
        if (arg.length <= 1) continue;
        if (arg[0] === sArgName) argsValue = arg[1];
    }
    return argsValue.trim();
}

(function (w) {
    function Wf(selector, context) {
        return new Wf.fn.init(selector, context);
    }
    Wf.fn = Wf.prototype = {};
    var init = Wf.fn.init = function(selector, context) {
        Wf.getName = function () {
            return "bpmn";
        };
        Wf.getToken = function () {
            return 'abc';
        };
        Wf.year=getArgsValue('year');
        Wf.tokenID=getArgsValue('tokenID');
        Wf.province=getArgsValue('province');
        Wf.basePath=$.getBpmnBasePath();
        return Wf;
    };

    init.prototype = Wf.fn;
    w.Wf = Wf;
}(window));