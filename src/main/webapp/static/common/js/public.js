
//获取url参数,返回"?"后边字符串
function getUrlParam(){
    var url = location.search;
    if (url.indexOf("?") != -1) {
        url= url.substr(1);
        return url;
    }
    return;
}

//返回参数的key/value对象
function getUrlValuesByKey() {
    var url = decodeURI(location.search);
    var param = {};
    if (url.indexOf("?") != -1) {
        var str = url.substr(1).split("&");
        for (var i = 0; i < str.length; i++) {
            param[str[i].split("=")[0]] = str[i].split("=")[1];
        }
    }
    return param;
}

//设置cookie
function setCookie(key,value){
    document.cookie=key+'='+value;
}

//获取cookie
function getCookie(key) {
    var arr = document.cookie.split('; ');
    for(var i = 0; i < arr.length; i++) {
        var arrName = arr[i].split('=');
        if(arrName[0] == key) {
            return arrName[1];
        }
    }
    return;
}

//删除cookie
function deleteCookie(key){
    var oDate=new Date();
    oDate.setDate(oDate.getDate()-1);
    document.cookie=key+"=1; expires="+ oDate;
}

// 一位数转为两位数
function oneToTwo(num){
    if(num<10){
        num='0'+num;
    }
    return num;
}

// 一位数转为三位数
function numToThree(num){
    if(num<10){
        num='00'+num;
    }else if(num <100){
        num = "0"+num;
    }
    return num;
}

//获取当前日期
function getCurrentDate(){
    var date=new Date();
    var month=date.getMonth()+1;
    var day=date.getDate();
    month=oneToTwo(month);
    day=oneToTwo(day);

    var time=date.getFullYear()+'-'+month+'-'+day;
    return time;
}

//向后台访问，传值key=value形式
//fn1为访问成功回调函数
//fn2为访问失败回调函数
//beforeFunc为请求前需要做的事情
//completeFunc请求完成后做的事情
function ajaxCommen(url,type,data,fn1,beforeFunc,completeFunc){
    $.ajax({
        url:url,
        type:type,
        data:data,
        beforeSend:function(){
            if(beforeFunc){
                beforeFunc();
            }
        },
        success:function(list){
            if(fn1){
                fn1(list);
            }
        },
        complete:function(){
            if(completeFunc){
                completeFunc();
            }
        },
        error:function(list){
            console.log("请求失败");
        }
    });
}

//向后台访问，传值json形式
//fn1为访问成功回调函数
//fn2为访问失败回调函数
//beforeFunc为请求前需要做的事情
//completeFunc请求完成后做的事情
function ajaxJSON(url,type,fn1,beforeFunc,completeFunc){
    $.ajax({
        url:url,
        type:type,
        contentType:'application/json;charset=UTF-8',
        data:data,
        beforeSend:function(){
            if(beforeFunc){
                beforeFunc();
            }
        },
        success:function(list){
            if(fn1){
                fn1(list);
            }
        },
        complete:function(){
            if(completeFunc){
                completeFunc();
            }
        },
        error:function(list){
            console.log("请求失败");
        }
    });
}

// 计算天数时间差
function getDateDiff(startDate,endDate)  {
    var startTime = new Date(Date.parse(startDate.replace(/-/g,   "/"))).getTime();
    var endTime = new Date(Date.parse(endDate.replace(/-/g,   "/"))).getTime();
    var dates = Math.abs((startTime - endTime))/(1000*60*60*24);
    return  dates;
}

//模态框弹出
function modalFade(url,fn){
    var dialog=$(window.document).find('#myModal');
    dialog.load(url,function(){
        dialog.modal('show');
        if(fn){
            fn();
        }
    });

}

//load方法加载js
//url为路径
function loadJs(url){
    var loadJs = document.getElementById('loadJs');
    var script = document.createElement('script');
    script.onreadystatechange = function(){
        if( script.readyState == 'loaded' || script.readyState == 'complete'){
            console.log('loaded');
            script.onreadystatechange = null;
        }
    };
    if(loadJs) document.body.removeChild(loadJs);
    script.type='text/javascript';
    script.src=url;
    script.id='loadJs';
    document.body.appendChild(script);
}
//loadCSS
function loadCss(url){
    var cssTag = document.getElementById('loadCss');
    var head = document.getElementsByTagName('head').item(0);
    if(cssTag) head.removeChild(cssTag);
    css = document.createElement('link');
    css.href = url;
    css.rel = 'stylesheet';
    css.type = 'text/css';
    css.id = 'loadCss';
    head.appendChild(css);
}

// 点击回车定义键盘事件 event为键盘事件 obj为对象 fun为点击后的执行方法
function keyLogin(event,obj,fun){
    if(event.keyCode==13 && obj.val()){  //回车键的键值为13
        return fun();
    }
}

// 增加字母数字验证，不为则以‘’代替
function checkNum1(obj){
    obj.value = obj.value.replace(/[^a-zA-Z0-9]/g,"");
}

//增加数字验证 不为数字则以‘’代替
function checkNum2(obj){
    obj.value = obj.value.replace(/[^0-9]/g,"");
}


/*
 *  方法:Array.remove(dx)
 *  功能:根据元素位置值删除数组元素.
 *  参数:元素值
 *  返回:在原数组上修改数组
 *  作者：jinjin
 */
Array.prototype.remove = function (dx) {
    if (isNaN(dx) || dx > this.length) {
        return false;
    }
    for (var i = 0, n = 0; i < this.length; i++) {
        if (this[i] != this[dx]) {
            this[n++] = this[i];
        }
    }
    this.length -= 1;
};
//check两个小数点
function clearNoNum(obj){
    //先把非数字的都替换掉，除了数字和.
    obj.value = obj.value.replace(/[^\d.]/g,"");
    //保证只有出现一个.而没有多个.
    obj.value = obj.value.replace(/\.{2,}/g,".");
    //必须保证第一个为数字而不是.
    obj.value = obj.value.replace(/^\./g,"");
    //保证.只出现一次，而不能出现两次以上
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    //只能输入两个小数
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
}

//只传日期 区分“-”，“/”
function transferDate(date){
    var new_date=date.replace(/\-/g,"/");
    return new Date(new_date).valueOf();
}


//把首字母和pattrn线的第一个字母变成大写
function changeUpperByStr(str,pattrn){
    var arr = str.split(pattrn);
    var joinStr = '';
    for(var i=0;i<arr.length;i++){
        var arrString = arr[i];
        joinStr +=arrString.substr(0,1).toUpperCase()+arrString.substr(1,arrString.length-1);
    }
    return joinStr;
}


















