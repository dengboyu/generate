$(function(){

	$('.btn').click(function(){
		var send_data={};
		send_data.username=$('.username').val();
		send_data.password=$('.password').val();

		$.ajax({
			url:"/findAreaCityInfo",
			type:"get",
			success:function(list){
				console.log(list);
			},
			error:function(list){
				var status = list.status;//错误状态码
				switch (status){
					// case 500: location.href="/static/error/page500.html";break;
					// case 404: location.href="/static/error/page404.html";break;
					// default:location.href="/static/error/page404.html";
				}
			}
		});
	});

	


});