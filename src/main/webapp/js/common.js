var secure = null;	// 权限参数
var dialog = null;



var Dialog = {
        msg: function (text) {
            new BootstrapDialog({
                title: '标题',
                message: text,
                type: BootstrapDialog.TYPE_SUCCESS
            }).open();
        },
        loading: function () {
            new BootstrapDialog.show({
                title: "加载中",
                closable: false,
                message: "正在加载, 请稍等..."
            }).open();
        },
        confirm: function (message, callback) {
            new BootstrapDialog({
                title: '提示信息',
                message: message,
                closable: true,
                data: {'callback': callback},
                buttons: [{
                    label: '取消',
                    action: function (dialog) {
                        // 容易理解的写法 if(typeof dialog.getData('callback') === 'function') dialog.getData('callback')(false); // or callback(false);
                        typeof dialog.getData('callback') === 'function' && dialog.getData('callback')(false);
                        dialog.close();
                    }
                }, {
                    label: '确定',
                    cssClass: 'btn-primary',
                    action: function (dialog) {
                        typeof dialog.getData('callback') === 'function' && dialog.getData('callback')(true);
                        dialog.close();
                    }
                }]
            }).open();
        },
        hideModel: function (eml) {
            eml.modal('hide');
        },
        showModel: function (eml) {
            eml.modal({backdrop: 'static', keyboard: false}).modal('show');
        },
        isSubmitted: function () {
            return new BootstrapDialog.show({
                title: "正在提交",
                closable: false,
                message: "请稍等, 正在提交请求!"
            });
        }
    };

(function ($) {
    $.isSuccess = function (data) {
        if (typeof data != 'object') {
            data = $.parseJSON(data);
        }
        if (data.head == true) {
            return true;
        }
        if ('UNLOGIN' == data.body) {
            window.location.href = './login.html';
            return false;
        }
        Dialog.msg(data.body);
        return false;
    };

    $('.acctInfo').on('click', function (data) {
        Dialog.showModel($('div.update-password-box'));
    });

   // $('body').append(modifyPassHtml);

    $.isUnLogin = function (data) {
        if (typeof data != 'object') {
            data = $.parseJSON(data);
        }
        if ('UNLOGIN' == data.body) {
            window.location.href = './login.html';
        }
    };

    $.findMenu = function (moduleCode, initFun) {
        var ul = $('ul.navbar-list').empty();
        $.post("module/findMenu", function (data) {
            if (!$.isSuccess(data)) return;
            if (typeof data != 'object') {
                data = $.parseJSON(data);
            }

            $.each(data.body, function (index, value) {
                if (value.moduleLevel != 0) return;
                $("<li class='dropdown'></li>")
                //                    .append(value.moduleName)
                    .append($("<a href='#' class='dropdown-toggle' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'></a>").append(value.moduleName)
                        .append("<span class='caret'></span>"))
                    .append($.subMenu(data.body, value.moduleCode))
                    .appendTo(ul);
                console.log(value.moduleName);
            });
            findModuleParameter(moduleCode, initFun);
        });
    };
    $.subMenu = function (data, code) {
        if (!data || !code) return;
        var ul = '';
        ul += "<ul class='dropdown-menu'>";
        $.each(data, function (i, v) {
            if (v.moduleLevel != 1 || v.moduleSuperCode != code) {
                return;
            }
            ul += "<li><a href='" + v.modulePage + "'>" + v.moduleName + "</a></li>";

            console.log(v.moduleName);
        });
        ul += "</ul>";
        console.log(ul);
        return ul;
    };

    $.verify = true;
    $.verifyForm = function (className, isNull) {
        var val = $(className).removeClass('border-red').val();
        if (val == null || val == '' || val.length <= 0) {
            $.verify = false;
            $(className).addClass('border-red');
        }
        return val;
    };
    $.page = 0;
    $.setPage = function (data, fun) {
        $("#pagination").pagy({
            currentPage: data.nowPage,
            totalPages: data.totalPage,
            innerWindow: 2,
            first: '首页',
            prev: '上一页',
            next: '下一页',
            last: '最后一页',
            gap: '..',
            truncate: true,
            page: function (clickPage) {
                if ($.page == clickPage) return false;
                $.page = clickPage;
                fun(clickPage);
                return true;
            }
        });
    };

    $.findChecked = function (val) {
        return val ? " checked=true " : "";
    };

    $.findCheckedByValue = function (val) {
        return val == 1 ? " checked=true " : "";
    };

    // 获取项目根路径
    $.getRootPath = function () {
        var curWwwPath = window.document.location.href; // 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname; // 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pos = curWwwPath.indexOf(pathName);
        var localhostPaht = curWwwPath.substring(0, pos); // 获取主机地址，如： http://localhost:8083
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1); // 获取带"/"的项目名，如：/uimcardprj
        return (localhostPaht + projectName);
    };

})(jQuery);

function findModuleParameter(moduleCode, initFun) {
    if (!moduleCode) return;
    $.getJSON('module/findModuleParameter', {moduleCode: moduleCode}, function (data) {
        if (!$.isSuccess(data)) return;
        $('a.acctInfo').append(data.body.account);

        secure = data.body;
        if (!secure.find) {
            $('div.container').remove();	// 删除页面主要元素
            Dialog.msg("非法操作, 你没有当前页面的权限!", BootstrapDialog.TYPE_DANGER);
            return;
        }
        $('a.login').addClass('hide');
        $('a.acctInfo').removeClass('hide');
        $('a.exit').removeClass('hide');
        $('div.main').removeClass('hide');
        if ("0" != moduleCode) {
            var obj = $('ol.breadcrumb').empty();
            obj.append($("<li></li>").append(data.body.superModuleName));
            obj.append($("<li ckass='active'></li>").append(data.body.moduleName));
            $('.navbar-nav').find('.dropdown[name=' + data.body.code + ']').addClass('active');
            $('title').text(data.body.moduleName + ' - ' + data.body.superModuleName);
        }
        initFun();
    });
};

function exit() {
    Dialog.confirm("请确认是是否需要注销登录!", function (result) {
        if (!result) return;
        /* BootstrapDialog.show({
         title: "加载中",
         closable: false,
         message: "正在加载, 请稍等..."
         });*/
        $.getJSON('account/exit', function (data) {
            if (!$.isSuccess(data)) return;
            window.location.href = './index.html';
            // toLogin();
        });
    });
};

function toLogin() {
    $.post('toLogin',function(){

    })
}

function modifyPass() {
    $.isSubmit = true;
    $.verify = true;
    var password = $.verifyForm('input.password');
    var passwordConfirm = $.verifyForm('input.password-confirm');
    if (!$.isSubmit) return;
    if(!$.verify) return;
    if (password != passwordConfirm) {
        Dialog.msg("再次密码输入不一致!", BootstrapDialog.TYPE_PRIMARY);
        return;
    }
    $.getJSON('account/modifyPass', {password: password}, function (data) {
        if (!$.isSuccess(data)) return;
        Dialog.msg(data.body, BootstrapDialog.TYPE_SUCCESS);
    });
};








