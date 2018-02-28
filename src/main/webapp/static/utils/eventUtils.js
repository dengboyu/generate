/**
 * @author:by@Deng
 * @timer:2016-12-07
 * @email:by@6886432@163.com
 * @version:1.0
 * @title:跨浏览器的事件对象
 */
var EventUtils={
	/**
	 * 为某个元素增加一个事件处理
	 */
	addHandler:function(element,type,handler){
		if(element.addEventListener){
			element.addEventListener(type,handler,false);
		}else if(element.attachEvent){
			element.attachEvent("on"+type,handler)
		}else{
			element["on"+type]=handler;
		}
	},
	/**
	 * 为某个元素移除一个事件处理
	 * @return {[type]}         [description]
	 */
	removeHandler:function(element,type,handler){
		if(element.removeEventListener){
			element.removeEventListener(type,handler,false);
		}else if(element.detachEvent){
			element.detachEvent("on"+type,handler)
		}else{
			element["on"+type]=null;
		}
	},
	/**
	 * 获取事件对象
	 * @return {[type]} [description]
	 */
	getEvent:function(event){
		return event?event:window.event;
	},
	/**
	 * 获取某个目标元素
	 * @return {[type]} [description]
	 */
	getTarget:function(event){
		return event.target || event.srcElement;
	},
	/**
	 * 阻止默认事件
	 */
	preventDefault:function(event){
		if(event.preventDefault){
			event.preventDefault();
		}else{
			event.returnValue=false;
		}
	},
	/**
	 * 阻止冒泡事件
	 */
	stopPropagation:function(event){
		if(event.stopPropagation){
			event.stopPropagation();
		}else{
			event.cancelBubble=true;
		}
	}
}