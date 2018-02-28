$(function() {
	var basePath = FileUtils.getBasePath(); //项目基地址

	//数据库访问
	ajaxCommen(basePath + '/autoCode/getDatabaseName', 'get', '', function(list) {
		if (list.length > 0) {
			var _html = '';
			for (var i = 0; i < list.length; i++) {
				_html += '<option value="' + list[i] + '">' + list[i] + '</option>';
			}
			$('.code-dataBase select').append(_html);
		}
	});

	//点击数据库，表名进行联动
	$('.code-dataBase select').change(function() {
		var text = $(this).val();

		$('.code-table select').find('option:eq(0)').siblings().remove();
		if (text) {
			ajaxCommen(basePath + '/autoCode/getTablesName', 'get', 'dataBaseName=' + text, function(list) {
				if (list.length > 0) {
					var _html = '';
					for (var i = 0; i < list.length; i++) {
						_html += '<option value="' + list[i] + '">' + list[i] + '</option>';
					}
					$('.code-table select').append(_html);
				}
			});
		}

		//对类名进行清空
		$('.className').val('');
		$('.code-table-row tbody').html('');
	});

	//点击表名，返回该表所对应的信息
	$('.code-table select').change(function() {
		var text = $(this).val(); //表名称
		var dataBaseName = $('.code-dataBase select').val();
		//把首字母和_线的第一个字母变成大写
		if (text) {
			var className = changeUpperByStr(text, '_');
			$('.className').val(className);

			//请求返回表中的字段
			var send_data = 'dataBaseName=' + dataBaseName + '&tableName=' + text;
			ajaxCommen(basePath + '/autoCode/getTableColumnsByTableName', 'get', send_data, function(list) {
				if (list.length > 0) {
					var _html = '';
					for (var i = 0; i < list.length; i++) {
						var inputChecked = '';
						if (list[i].primaryKey) {
							inputChecked = '<input type="checkbox" checked disabled>'
						} else {
							inputChecked = '<input type="checkbox" disabled>'
						}

						_html += '<tr>\
										<td>' + (i + 1) + '</td>\
										<td>' + list[i].columnName + '</td>\
										<td>' + list[i].entityName + '</td>\
										<td>\
											<input type="text" class="form-control" value="' + list[i].annotation + '">\
										</td>\
										<td>' + list[i].mybatisType + '</td>\
										<td>\
											<select class="code-javaType">\
												<option value="">请选择</option>\
												<option value="Integer">Integer</option>\
                    							<option value="Long">Long</option>\
												<option value="String">String</option>\
												<option value="Float">Float</option>\
												<option value="Double">Double</option>\
												<option value="BigDecimal">BigDecimal</option>\
												<option value="Boolean">Boolean</option>\
												<option value="byte[]">byte[]</option>\
												<option value="java.sql.Date">java.sql.Date</option>\
												<option value="java.util.Date">java.util.Date</option>\
												<option value="Timestamp">java.sql.Timestamp</option>\
												<option value="Time">java.sql.Time</option>\
											</select>\
										</td>\
										<td>' + inputChecked +
							'</td>\
									</tr>';
					}
					$('.code-table-row tbody').append(_html);
					for (var i = 0; i < list.length; i++) {
						$('.code-table-row tbody select').eq(i).val(list[i].javaType);
					}
				}
			});

		} else {
			//对类名进行清空
			$('.className').val('');
		}

		$('.code-table-row tbody').html('');
	});


	//生成代码
	$('.code-generate').click(function() {

		var send_data = {};
		send_data.dataBaseName = $('.code-dataBase select').val(); //数据库名称
		send_data.tableName = $('.code-table select').val(); //表名
		send_data.author = $('.code-author').val().trim(); //作者
		send_data.moduleName = $('.code-moduleName').val().trim(); //模块名
		send_data.className = $('.className').val().trim(); //类名
		send_data.codePath = $('.code-path').val().trim(); //生成代码路径

		//数据校验
		for (i in send_data) {
			if (!send_data[i]) {
				$('.modal-body').text("请将表头数据补充完整");
				$('.btn-cancel').text("好的,我知道了");
				return $('.modal').modal('show');
			}
		}

		//封装表字段信息
		send_data.tableCodeEntityList = [];

		var trs = $('.code-table-row tbody tr');
		for (var i = 0; i < trs.length; i++) {

			var tdsObj = {};
			tdsObj.columnName = $(trs[i]).find('td').eq(1).text(); //字段名
			tdsObj.entityName = $(trs[i]).find('td').eq(2).text(); //实体类属性
			tdsObj.annotation = $(trs[i]).find('td').eq(3).find('input').val().trim(); //备注
			tdsObj.mybatisType = $(trs[i]).find('td').eq(4).text(); //mybatis类型
			tdsObj.javaType = $(trs[i]).find('td').eq(5).find('select').val(); //java类型
			if (!tdsObj.javaType) {
				$('.modal-body').text("请将表格java实体类型数据补充完整");
				$('.btn-cancel').text("好的,我知道了");
				return $('.modal').modal('show');
			}
			var isCheck = $(trs[i]).find('td').eq(6).find('input').attr('checked');
			if (isCheck == "checked") {
				tdsObj.primaryKey = "1";
			} else {
				tdsObj.primaryKey = "0";
			}

			send_data.tableCodeEntityList.push(tdsObj);
		}

		$.ajax({
			"url": basePath + "/autoCode/addFileByTableName",
			"type": "post",
			"contentType": "application/json;charset=utf-8",
			"data": JSON.stringify(send_data),
			"beforeSend": function() {
				$('.modal-body').text("正在生成code中,请稍候...");
				$('.btn-cancel').hide();
				return $('.modal').modal('show');
			},
			"success": function(list) {
				$('.modal-body').text("生成文件成功");
				$('.btn-cancel').show().text("确定");
				return $('.modal').modal('show');
			},
			"error": function(list) {
				if (list.status == 400 && list.responseJSON) {
					$('.modal-body').text(list.responseJSON.exception);
				}
				$('.btn-cancel').show().text("取消");
				return $('.modal').modal('show');
			}
		});

	});



});