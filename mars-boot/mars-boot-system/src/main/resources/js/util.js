
function getSwjg(fpdm, ckflag) {
    var flag = " ";
    var citys = [{
        'code': '1100',
        'sfmc': '北京',
        'Ip': 'https://fpcy.beijing.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.beijing.chinatax.gov.cn:443'
    }, {
        'code': '1200',
        'sfmc': '天津',
        'Ip': 'https://fpcy.tjsat.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.tjsat.gov.cn:443'
    }, {
        'code': '1300',
        'sfmc': '河北',
        'Ip': 'https://fpcy.hebei.chinatax.gov.cn/NWebQuery',
        'address': 'https://fpcy.hebei.chinatax.gov.cn'
    }, {
        'code': '1400',
        'sfmc': '山西',
        'Ip': 'https://fpcy.shanxi.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.shanxi.chinatax.gov.cn:443'
    }, {
        'code': '1500',
        'sfmc': '内蒙古',
        'Ip': 'https://fpcy.neimenggu.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.neimenggu.chinatax.gov.cn:443'
    }, {
        'code': '2100',
        'sfmc': '辽宁',
        'Ip': 'https://fpcy.liaoning.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.liaoning.chinatax.gov.cn:443'
    }, {
        'code': '2102',
        'sfmc': '大连',
        'Ip': 'https://sbf.dalian.chinatax.gov.cn:8402/NWebQuery',
        'address': 'https://sbf.dalian.chinatax.gov.cn:8402'
    }, {
        'code': '2200',
        'sfmc': '吉林',
        'Ip': 'https://fpcy.jilin.chinatax.gov.cn:4432/NWebQuery',
        'address': 'https://fpcy.jilin.chinatax.gov.cn:4432'
    }, {
        'code': '2300',
        'sfmc': '黑龙江',
        'Ip': 'https://fpcy.hl-n-tax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.hl-n-tax.gov.cn:443'
    }, {
        'code': '3100',
        'sfmc': '上海',
        'Ip': 'https://fpcy.shanghai.chinatax.gov.cn:1001/NWebQuery',
        'address': 'https://fpcy.shanghai.chinatax.gov.cn:1001'
    }, {
        'code': '3200',
        'sfmc': '江苏',
        'Ip': 'https://fpcy.jiangsu.chinatax.gov.cn:80/NWebQuery',
        'address': 'https://fpcy.jiangsu.chinatax.gov.cn:80'
    }, {
        'code': '3300',
        'sfmc': '浙江',
        'Ip': 'https://fpcy.zhejiang.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.zhejiang.chinatax.gov.cn:443'
    }, {
        'code': '3302',
        'sfmc': '宁波',
        'Ip': 'https://fpcy.ningbo.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.ningbo.chinatax.gov.cn:443'
    }, {
        'code': '3400',
        'sfmc': '安徽',
        'Ip': 'https://fpcy.anhui.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.anhui.chinatax.gov .cn:443'
    }, {
        'code': '3500',
        'sfmc': '福建',
        'Ip': 'https://fpcy.fujian.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.fujian.chinatax.gov.cn:443'
    }, {
        'code': '3502',
        'sfmc': '厦门',
        'Ip': 'https://fpcy.xiamen.chinatax.gov.cn/NWebQuery',
        'address': 'https://fpcy.xiamen.chinatax.gov.cn'
    }, {
        'code': '3600',
        'sfmc': '江西',
        'Ip': 'https://fpcy.jiangxi.chinatax.gov.cn:82/NWebQuery',
        'address': 'https://fpcy.jiangxi.chinatax.gov.cn:82'
    }, {
        'code': '3700',
        'sfmc': '山东',
        'Ip': 'https://fpcy.shandong.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.shandong.chinatax.gov.cn:443'
    }, {
        'code': '3702',
        'sfmc': '青岛',
        'Ip': 'https://fpcy.qingdao.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.qingdao.chinatax.gov.cn:443'
    }, {
        'code': '4100',
        'sfmc': '河南',
        'Ip': 'https://fpcy.henan.chinatax.gov.cn/NWebQuery',
        'address': 'https://fpcy.henan.chinatax.g   ov.cn'
    }, {
        'code': '4200',
        'sfmc': '湖北',
        'Ip': 'https://fpcy.hubei.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.hubei.chinatax.gov.cn:443'
    }, {
        'code': '4300',
        'sfmc': '湖南',
        'Ip': 'https://fpcy.hunan.chinatax.gov.cn:8083/NWebQuery',
        'address': 'https://fpcy.hunan.chinatax.gov.cn:8083'
    }, {
        'code': '4400',
        'sfmc': '广东',
        'Ip': 'https://fpcy.guangdong.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.guangdong.chinatax.gov.cn:443'
    }, {
        'code': '4403',
        'sfmc': '深圳',
        'Ip': 'https://fpcy.shenzhen.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.shenzhen.chinatax.gov.cn:443'
    }, {
        'code': '4500',
        'sfmc': '广西',
        'Ip': 'https://fpcy.guangxi.chinatax.gov.cn:8200/NWebQuery',
        'address': 'https://fpcy.guangxi.chinatax.gov.cn:8200'
    }, {
        'code': '4600',
        'sfmc': '海南',
        'Ip': 'https://fpcy.hainan.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.hainan.chinatax.gov.cn:443'
    }, {
        'code': '5000',
        'sfmc': '重庆',
        'Ip': 'https://fpcy.chongqing.chinatax.gov.cn:80/NWebQuery',
        'address': 'https://fpcy.chongqing.chinatax.gov.cn:80'
    }, {
        'code': '5100',
        'sfmc': '四川',
        'Ip': 'https://fpcy.sichuan.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.sichuan.chinatax.gov.cn:443'
    }, {
        'code': '5200',
        'sfmc': '贵州',
        'Ip': 'https://fpcy.guizhou.chinatax.gov.cn:80/NWebQuery',
        'address': 'https://fpcy.guizhou.chinatax.gov.cn:80'
    }, {
        'code': '5300',
        'sfmc': '云南',
        'Ip': 'https://fpcy.yunnan.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.yunnan.chinatax.gov.cn:443'
    }, {
        'code': '5400',
        'sfmc': '西藏',
        'Ip': 'https://fpcy.xztax.gov.cn:81/NWebQuery',
        'address': 'https://fpcy.xztax.gov.cn:81'
    }, {
        'code': '6100',
        'sfmc': '陕西',
        'Ip': 'https://fpcy.shaanxi.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.shaanxi.chinatax.gov.cn:443'
    }, {
        'code': '6200',
        'sfmc': '甘肃',
        'Ip': 'https://fpcy.gansu.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.gansu.chinatax.gov.cn:443'
    }, {
        'code': '6300',
        'sfmc': '青海',
        'Ip': 'https://fpcy.qinghai.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.qinghai.chinatax.gov.cn:443'
    }, {
        'code': '6400',
        'sfmc': '宁夏',
        'Ip': 'https://fpcy.ningxia.chinatax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.ningxia.chinatax.gov.cn:443'
    }, {
        'code': '6500',
        'sfmc': '新疆',
        'Ip': 'https://fpcy.xj-n-tax.gov.cn:443/NWebQuery',
        'address': 'https://fpcy.xj-n-tax.gov.cn:443'
    }];

    var dqdm = null;
    var swjginfo = new Array();

    if (fpdm.length == 12) {
        dqdm = fpdm.substring(1, 5);
    } else {
        dqdm = fpdm.substring(0, 4);
    }
    if (dqdm != "2102" && dqdm != "3302" && dqdm != "3502" && dqdm != "3702" && dqdm != "4403") {
        dqdm = dqdm.substring(0, 2) + "00";
    }
    for (var i = 0; i < citys.length; i++) {
        if (dqdm == citys[i].code) {
            swjginfo[0] = citys[i].sfmc;
            if (flag == 'debug') {
                swjginfo[1] = "http://localhost:7001/NWebQuery";
            } else {
                swjginfo[1] = citys[i].Ip;
            }
            break;
        }
    }
    return swjginfo;
}

function myGetSwjg(fpdm, ckflag) {
    return JSON.stringify(getSwjg(fpdm, ckflag));
}

var code = ["144031539110", "131001570151", "133011501118", "111001571071"]
function alxd(a) {
    var b;
    var c = "99";

    if (a.length == 12) {
        b = a.substring(7, 8);
        for (var i = 0; i < code.length; i++) {
            if (a == code[i]) {
                c = "10";
                break;
            }
        }
        if (c == "99") {
            if (a.charAt(0) == '0' && a.substring(10, 12) == '11') {
                c = "10";
            }
            if (a.charAt(0) == '0' && (a.substring(10, 12) == '04' || a.substring(10, 12) == '05')) {
                c = "04";
            }
            if (a.charAt(0) == '0' && (a.substring(10, 12) == '06' || a.substring(10, 12) == '07')) {
                c = "11";
            }
            if (a.charAt(0) == '0' && a.substring(10, 12) == '12') {
                c = "14";
            }
        }
        if (c == "99") {
            if (a.substring(10, 12) == '17' && a.charAt(0) == '0') {
                c = "15";
            }
            if (c == "99" && b == 2 && a.charAt(0) != '0') {
                c = "03";
            }
        }

        // 添加电子专用发票的规则判断  20200308
        if (c == "99") {
            if (a.charAt(0) == '0' && a.substring(10, 12) == '13') {
                c = "08";
            }
        }

    } else if (a.length == 10) {
        b = a.substring(7, 8);
        if (b == 1 || b == 5) {
            c = "01";
        } else if (b == 6 || b == 3) {
            c = "04";
        } else if (b == 7 || b == 2) {
            c = "02";
        }
    }
    return c;
}

function aje(a, b) {
    var c = alxd(a);
    //20200308添加电子专用发票判断
    if (c == "01" || c == "02" || c == "03" || c == "15" || c == "08") {
        return ea(b);
    } else {
        return eb(b);
    }
}
function ea(a) {
    var b = /(^-?\d{1,11}$)|(^-?\d{1,11}\.\d{1,2}$)/;
    var c = b.test(a);
    return c;
}
function eb(a) {
    var b = /^-?(\d+$)|(\d+\.\d{1,2})$/;
    var c = b.test(a);
    return c;
}
function ajy(a) {
    var b = /^\d{6}$/;
    var e = b.test(a);
    return e;
}

function ahm(a) {
    var b = /^\d{8}$/;
    var c = /^0{8}$/;
    var d = /^0{11}-?[1-9]*\w\d*$/;
    var e = b.test(a);
    var f = c.test(a);
    if (e == true && f == false) {
        return true;
    } else {
        return false;
    }
}

function afcdm(_0x39ea20) {
    if (_0x39ea20.length == 0xa || _0x39ea20.length == 0xc) {
        var _0x23de2a = /^[0-9]*$/;
        var _0x50cd12 = _0x23de2a.test(_0x39ea20);
        return _0x50cd12;
    }
    return false;
}