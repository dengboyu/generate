/**
 * @author:by@Deng
 * @timer:2016-12-07
 * @email:by@6886432@163.com
 * @version:1.0
 * @title:常用的校验工具类
 */

//********************************身份证校验和获取信息工具类****************************
var IDCardUtils={
	/**
	 * 校验身份证号是否正确
	 * @return {[type]}          [description]
	 */
    checkIdCard: function(idCardNo){
        var check = /^\d{15}|(\d{17}(\d|x|X))$/.test(idCardNo);
        if(!check) return false;

        //判断长度为15位或18位
        if(idCardNo.length==15){
            return this.check15IdCardNo(idCardNo);
        }else if(idCardNo.length==18){
            return this.check18IdCardNo(idCardNo);
        }else{
            return false;
        }
    },
    /**
     * 根据身份证号获取一些信息
     * @return 返回对象{gender,birthday,address}
     */
    getIdCardInfo: function(idCardNo){
    	//返回性别、出生日期、籍贯
        var idCardInfo = {gender:"",birthday:"",address:""};
        if(idCardNo.length==15){
            var aday = '19' + idCardNo.substring(6,12);
            idCardInfo.birthday=this.formateDateCN(aday);
            var _address = idCardNo.substring(0,2);
            idCardInfo.address=this.provinceAndCitys[_address];
            if(parseInt(idCardNo.charAt(14))%2==0){
                idCardInfo.gender=this.genders.female;
            }else{
                idCardInfo.gender=this.genders.male;
            }
        }else if(idCardNo.length==18){
            var aday = idCardNo.substring(6,14);
            idCardInfo.birthday = this.formateDateCN(aday);
            var _address = idCardNo.substring(0,2);
            idCardInfo.address=this.provinceAndCitys[_address];
            if(parseInt(idCardNo.charAt(16))%2==0){
                idCardInfo.gender = this.genders.female;
            }else{
                idCardInfo.gender = this.genders.male;
            }
        }
        return idCardInfo;
    },
    /**
     * 护照校验
     * @return {[type]} [description]
     */
    checkPassport:function(number){
	    var Expression=/(P\d{7})|(G\d{8})/;
	    var objExp=new RegExp(Expression);

	    if(objExp.test(number)){
	        return true;
	    }
	    return false;
    },

	provinceAndCitys: {11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"},
    powers: ["7","9","10","5","8","4","2","1","6","3","7","9","10","5","8","4","2"],
    parityBit: ["1","0","X","9","8","7","6","5","4","3","2"],
    genders: {male:"男",female:"女"},

    checkAddressCode: function(addressCode){
        var check = /^[1-9]\d{5}$/.test(addressCode);
        if(!check) return false;
        if(this.provinceAndCitys[parseInt(addressCode.substring(0,2))]){
            return true;
        }else{
            return false;
        }
    },
    checkBirthDayCode: function(birDayCode){
        var check = /^[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$/.test(birDayCode);
        if(!check) return false;
        var yyyy = parseInt(birDayCode.substring(0,4),10);
        var mm = parseInt(birDayCode.substring(4,6),10);
        var dd = parseInt(birDayCode.substring(6),10);
        var xdata = new Date(yyyy,mm-1,dd);
        if(xdata > new Date()){
            return false;//生日不能大于当前日期
        }else if ( ( xdata.getFullYear() == yyyy ) && ( xdata.getMonth () == mm - 1 ) && ( xdata.getDate() == dd ) ){
            return true;
        }else{
            return false;
        }
    },
    getParityBit: function(idCardNo){
        var id17 = idCardNo.substring(0,17);
        var power = 0;
        for(var i=0;i<17;i++){
            power += parseInt(id17.charAt(i),10) * parseInt(this.powers[i]);
        }
        var mod = power % 11;
        return this.parityBit[mod];
    },
    checkParityBit: function(idCardNo){
        var parityBit = idCardNo.charAt(17).toUpperCase();
        if(this.getParityBit(idCardNo) == parityBit){
            return true;
        }else{
            return false;
        }
    },
    //校验15位的身份证号码
    check15IdCardNo: function(idCardNo){
        //15位身份证号码的基本校验
        var check = /^[1-9]\d{7}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}$/.test(idCardNo);
        if(!check) return false;
        //校验地址码
        var addressCode = idCardNo.substring(0,6);
        check = this.checkAddressCode(addressCode);
        if(!check) return false;
        var birDayCode = '19' + idCardNo.substring(6,12);
        //校验日期码
        return this.checkBirthDayCode(birDayCode);
    },
    //校验18位的身份证号码
    check18IdCardNo: function(idCardNo){
        //18位身份证号码的基本格式校验
        var check = /^[1-9]\d{5}[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}(\d|x|X)$/.test(idCardNo);
        if(!check) return false;

        //校验地址码
        var addressCode = idCardNo.substring(0,6);
        check = this.checkAddressCode(addressCode);
        if(!check) return false;

        //校验日期码
        var birDayCode = idCardNo.substring(6,14);
        check = this.checkBirthDayCode(birDayCode);
        if(!check) return false;

        //验证校检码
        return this.checkParityBit(idCardNo);
    },
    formateDateCN: function(day){
        var yyyy =day.substring(0,4);
        var mm = day.substring(4,6);
        var dd = day.substring(6);
        return yyyy + '-' + mm +'-' + dd;
    },
    getId15:function(idCardNo){
        if(idCardNo.length==15){
            return idCardNo;
        }else if(idCardNo.length==18){
            return idCardNo.substring(0,6) + idCardNo.substring(8,17);
        }else{
            return null;
        }
    },
    getId18: function(idCardNo){
        if(idCardNo.length==15){
            var id17 = idCardNo.substring(0,6) + '19' + idCardNo.substring(6);
            var parityBit = this.getParityBit(id17);
            return id17 + parityBit;
        }else if(idCardNo.length==18){
            return idCardNo;
        }else{
            return null;
        }
    }
}


/** 
 * 校验时间格式 
 * 
 * @param {} 
 *            timevale 
 * @return {} 
 */  
function checkTime(timevale) {  
    var regex = /^(([0-1][0-9])|([2][0-4]))(\:)[0-5][0-9](\:)[0-5][0-9]$/g;  
    var b = regex.test(timevale);  
    return b;  
}  
  
/** 
 * 校验Ip地址格式 
 * 
 * @param {} 
 *            ipvale 
 */  
function checkIp(ipvale) {  
    var regex = /^([1-9]|[1-9]\d|1\d{2}|2[0-1]\d|22[0-3])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}$/;  
    var b = regex.test(ipvale);  
    return b;  
}  
  
/** 
 * 是否是由字母或数字组成的字符串 
 * 
 * @param {} 
 *            letVale 
 */  
function checkLetOrNum(letVale) {  
    var regex = /^([a-zA-Z_]{1})([\w]*)$/g;  
    var b = regex.test(letVale);  
    return b;  
} 

/** 
 * 验证数字 
 * 
 * @param {} 
 *            source 
 * @return {} 
 */  
function checkNumber(source) {  
    var regex = /^(\-|\+)?\d+(\.\d+)?$/;  
    return regex.test(source);  
}  





















