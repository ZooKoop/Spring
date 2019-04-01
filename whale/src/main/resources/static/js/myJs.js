$(function(){
	$("#saveUser").on("click",function () {
		layer.open({
			type : 2,
			title :['注册用户','font-size:18px;font-weight:bold;'],
			area : [ '50%', '90%' ],
			shade : 0.3,
			maxmin : false,// 最大化和最小化按钮
			resize : false,// 拉伸
			scrollbar : false,// 滚动条
			moveOut : false,// 拖动到屏幕外面
			fixed : false, // 不固定
			content : '/user/toSaveUser',
			end : function() {
				location.reload();
			},
			btn : [ '提交', '取消' ], // 按钮上传按钮怎么提交的是 模态框的？
			yes : function(index, layero) {
				var iframes = $(layero).find("iframe")[0].contentWindow;// 找页面html
				var formdata = new FormData(iframes.document
						.getElementById("file"));// 序列化表单
				console.log(formdata);
				// iframeWin.contentWindow.uploadFiles();//doSubmit
				// 是你在弹出层的那个jsp里写的表单提交方法
				if ("formdata" != typeof (formdata) && formdata != null
						&& formdata != "") {
					$.ajax({
						url : '/user/saveUser',
						type : 'POST',
						data : formdata,
						contentType : false, // 不设置内容类型
						processData : false, // 不处理数据
						success : function(data) {
							console.log(data);
							if(data==200){
								layer.msg('操作成功!',{time:1000},function(){
									layer.close(index);
								});
							}else{
								layer.msg(data, {icon: 2, time:2000,anim:6});
							}
						},
						error : function() {
							layer.alert('图片上传失败!', {icon: 2});
						}
					});
				} else {
					layer.alert("选择的文件无效！请重新选择",{icon:1});
				}
			}
		});
	});
	
	/* 登陆弹框 */
	$("#login").on("click",function () {
		layer.open({
			type : 2,
			title :['Whale Login','font-size:18px;font-weight:bold;'],
			area : [ '50%', '50%' ],
			shade : 0.3,
			maxmin : false,// 最大化和最小化按钮
			resize : false,// 拉伸
			scrollbar : false,// 滚动条
			moveOut : false,// 拖动到屏幕外面
			fixed : true, // 不固定
			content : '/login',
			end : function() {
				location.reload();
			},
			btnAlign: 'c',
			btn : [ 'Login','Sign Up'], // 按钮上传按钮怎么提交的是 模态框的？
			yes : function(index, layero) {
// layer.getChildFrame('body', index).find('#loginFrom').submit();//表单提交
				var formdata = layer.getChildFrame('#loginFrom', index);
// layer.alert(formdata);
				$.ajax({
					url : '/lg',
					type : 'POST',
					dataType:'JSON',
					data : formdata.serialize(),
					success : function(data) {
						console.log(data);
						  if (typeof data.msg != "undefined") { 
							  layer.msg(data.msg,{icon: 2,time:2000});
						  } else {
							  layer.msg('欢迎 '+data.principal.username+' 回来！',{icon: 1,time:1000},function(){
									layer.close(index);
								});
						  }
					},
					error : function() {
						layer.msg('请求超时！', {icon: 2,time:1000});
					}
				});
			}
		});
	});
	/* 提示初始化 */
    $("[data-toggle='tooltip']").tooltip();
	/* tables封装 */
	var tables_init = function(tablesid,language,columns,columnDefs,ajax){
		$(tablesid).DataTable({
			pagingType : "full_numbers",
			language : language,
			destroy : true, // 销毁表格对象
			deferRender:true,// 延迟渲染
			paging : true,// paging属性必须为true才能实现默认初始值得功能
			bSort: true,
			bAutoWidth : false,// 自动宽度
			ordering : true, // 排序
			// bPaginate : true, //翻页功能
			bProcessing : true, // DataTables载入数据时，是否显示‘进度’提示
			serverSide : true, // 是否启动服务器端数据导入
			searching : true, // 是否禁用原生搜索(false为禁用,true为使用)
			// renderer: "Bootstrap", //渲染样式：Bootstrap和jquery-ui
			// "stripeClasses": ["odd", "even"], //为奇偶行加上样式，兼容不支持CSS伪类的场合
			lengthMenu : [ 5, 20, 50, 70, 100 ],
			columns : columns,
			columnDefs: columnDefs,
			ajax: ajax
		});
	};
	/* 公用语言设置 */
	var language = {
		decimal : "",
		emptyTable : "No data available in table",
		info : "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
		infoEmpty : "当前显示第 0 至 0 项，共 0 项",
		infoFiltered : "(由 _MAX_ 项结果过滤)",
		infoPostFix : "",
		thousands : ",",
		lengthMenu : "每页 _MENU_ 项",
		loadingRecords : "载入中...",
		processing : "处理中...",
		search : "搜索:",
		zeroRecords : "没有匹配结果",
		paginate : {
			"first" : "首页",
			"previous" : "上页",
			"next" : "下页",
			"last" : "末页"
		}
	};
	// 公共删除方法封装
//	var del = function (tables_id,urls,trdelclass,tables_name){
//		$(tables_id).on( 'click',trdelclass, function () {// ._del是数组中删除按钮的类
//			var data = tables_name.rows( $(this).parents('tr')).data();
//			$.ajax({
//				url: urls,
//				type: 'get',
//				dataType: 'json',
//				data: {id: data.id},
//				success: function(data) {
//					console.log(data)
//					if(data==200){
//						// 如果后台删除成功，则刷新表格，并提示用户删除成功
//						layer.msg("删除成功！",{icon:1,time:2000});
//						// 保留分页信息
//						// myTables.ajax.reload(null, false);
//						// 不保留分页信息
//						myTables.ajax.reload();
//					}else{
//						layer.msg("删除失败！",{icon:2,time:2000});
//					}
//				}
//			})
//		});
//	};
	
	var del = function (tables_id,urls,trdelclass,tables_name){
		//alert(tables_id)
		$(tables_id).on('click',trdelclass, function () {// ._del是数组中删除按钮的类
			console.log($(this).row())
			var data = tables_name.row( $(this).parents('tr')).data();
			$.ajax({
				url: urls,
				type: 'get',
				dataType: 'json',
				data: {id: data.id},
				success: function(data) {
					console.log(data)
					if(data==200){
						// 如果后台删除成功，则刷新表格，并提示用户删除成功
						layer.msg("删除成功！",{icon:1,time:2000});
						// 保留分页信息
						// myTables.ajax.reload(null, false);
						// 不保留分页信息
						myTables.ajax.reload();
					}else{
						layer.msg("删除失败！",{icon:2,time:2000});
					}
				}
			})
		});
	};
	var isIndex = window.location.href;// 当前url
	/* list页面参数 */
	if(isIndex.indexOf("/user/list") >= 0){
		var list_ajax = function (data, callback, settings) {
			// 封装请求参数
			var param = {};
			param.size = data.length;// 页面显示记录条数，在页面显示每页显示多少项的时候
			// param.start = data.start;//开始的记录序号
			param.page = (data.start / data.length);// 当前页码
			// param.search = data.search.value;//搜索条件
			// if (data.order.length > 0) {
			// param.order = data.columns[data.order[0].column].data;
			// param.dir = data.order[0].dir;
			// }
			// console.log(param);
			// ajax请求数据
			$.ajax({
				type: "GET",
				url: "/user/listInfo",
				cache: false, // 禁用缓存
				data: param, // 传入组装的参数
				dataType: "json",
				success: function (result) {
					// console.log(result);
					// setTimeout仅为测试延迟效果
					setTimeout(function () {
						// 封装返回数据
						var returnData = {};
						returnData.draw = result.draw;// 这里直接自行返回了draw计数器,应该由后台返回
						returnData.recordsTotal = result.totalElements;// 返回数据全部记录
						returnData.recordsFiltered = result.totalElements;// 后台不实现过滤功能，每次查询均视作全部结果
						returnData.data = result.content;// 返回的数据列表
						console.log(returnData);
						// 调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
						// 此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
						callback(returnData);
					}, 10);
				}
			});
		};
		var list_columns = [{
			data : 'id'
		},{
			data:null,
			render:function(data, type, row, meta){
				return "<input type='checkbox' name='onDutyToId'  value='" + data + "'>";
			}
		},{
			data : 'userName'
		}, {
			data : 'userPassword'
		}, {
			data : 'email'
		}, {
			data : 'regTime',
			// title: "注册时间",
			render:function(data, type, row, meta){
				// console.log(data)
				// console.log(new
				// Date(parseInt(Date.parse(data))).toLocaleString())
				return new Date(parseInt(Date.parse(data))).toLocaleString().replace(/[\u4e00-\u9fa5]/g, " ").replace(new RegExp('/','g'),"-");
			} 
		}, {
			data : 'srcImg',
			// title: "头像",
			render:function(data, type, row, meta){
				if(data==null){
					return "无";
				}
				return "<img class='img-rounded center-block img-thumbnail img-responsive' style='width:80px' src='" + data + "' />";
			}
		}];
		var list_columnDefs = [
			{
				// 指定第最后一列
				targets: 7,
				render: function(data, type, row, meta) {
					return '<a class="_eidt btn btn-info" type="button" href="#" data-toggle="tooltip" title="添加用户" ><span class="glyphicon glyphicon-list-alt"></span> 详情</a> <a class="_eidt btn btn-warning" type="button" href="#" > <span class="glyphicon glyphicon-edit"></span> 编辑</a> <a class="_del btn btn-danger" type="button" href="#" data-rowindex="'+meta.row+'"><span class="glyphicon glyphicon-trash"></span> 删除</a>';
//				return '<a class="_eidt btn btn-info" type="button" href="#" data-toggle="tooltip" title="添加用户" ><span class="glyphicon glyphicon-list-alt"></span> 详情</a> <a class="_eidt btn btn-warning" type="button" href="#" > <span class="glyphicon glyphicon-edit"></span> 编辑</a> <a class="_del btn btn-danger" type="button" href="#" ><span class="glyphicon glyphicon-trash"></span> 删除</a>';
				}
			},{
				// 隐藏第一列
				targets: 0,
				visible: false,
				searchable: false
			}];
		var list_tables = tables_init('#table_id_example',language,list_columns,list_columnDefs,list_ajax);
		//console.log()
		del("#table_id_example","/user/delete","._del",list_tables);// list页面删除
	}
	
	/* work页面参数 */
	if(isIndex.indexOf("/work") >= 0){
		
		var work_columns = [{
			data : 'id'
		},{
			data : 'ticketNumber',
			// title: "Ticket号",
			name : 'ticketNumber',
			render:function(data, type, row, meta){
				return "#"+ data +"";
			} 
		},{
			data : 'description',
			defaultContent:"",
			// title: "Ticket描述"
			/*
			 * render:function(data, type, row, meta){ return "<div
			 * class='text-center'>"+data+"</div>"; }
			 */
		},{
			data : 'isCreate',
			defaultContent:"",
			title: "Trunk",
			render:function(data, type, row, meta){
				// console.log(data)
				if(data>0){
					return "<p style='margin: 0;'>已发</p>";
				}else{
					return "<p style='margin: 0;color:red'>未发</p>";
				}		
			}
		},{
			data : 'isCreate316',
			defaultContent:"",
			// title: "316",
			render:function(data, type, row, meta){
				// console.log(data)
				if(data>0){
					return "<p style='margin: 0;'>已发</p>";
				}else{
					return "<p style='margin: 0;color:red'>未发</p>";
				}		
			}
		},{
			data : 'isCreate317',
			defaultContent:"",
			// title: "317",
			render:function(data, type, row, meta){
				// console.log(data)
				if(data>0){
					return "<p style='margin: 0;'>已发</p>";
				}else{
					return "<p style='margin: 0;color:red'>未发</p>";
				}		
			}
		},{
			data : 'isExample',
			defaultContent:"",
			// title: "Example",
			render:function(data, type, row, meta){
				if(data>0){
					return "<p style='margin: 0;'>有</p>";
				}else{
					return "<p style='margin: 0;color:red'>无</p>";
				}			
			}
		},{
			data : 'version',
			defaultContent:"",
			// title: "版本",
			render:function(data, type, row, meta){
				return "<div>"+data+"</div>";
			}
		},{
			data : 'dateTime',
			defaultContent:"",
			// title: "时间",
			render:function(data, type, row, meta){
				return new Date(parseInt(Date.parse(data))).toLocaleString().replace(/[\u4e00-\u9fa5]/g, " ").replace(new RegExp('/','g'),"-");
			}
		},{
			data : null,
			// title: "操作",
			render:function(data, type, row, meta){
				return '<a class="_eidt btn btn-info" type="button" href="#" > <span class="glyphicon glyphicon-edit"></span></a> <a class="_del btn btn-danger" type="button" href="#" ><span class="glyphicon glyphicon-trash"></span></a>';
			}
		}];
		var work_ajax = function (data, callback, settings) {
			// 封装请求参数
			var param = {};
			param.size = data.length;// 页面显示记录条数，在页面显示每页显示多少项的时候
			// console.log(data.length)
			// param.start = data.start;//开始的记录序号
			param.page = (data.start / data.length);// 当前页码
			// param.search = data.search.value;//搜索条件
			if (data.order.length > 0) {
				// param.order = data.columns[data.order[3].column].data;
				param.order = data.columns[7].data;
				param.dir = data.order[0].dir;
				console.log(param.order+"----------"+param.dir);
			} 
			
			// ajax请求数据
			$.ajax({
				type: "GET",
				url: "/work/queryAll",
				cache: false, // 禁用缓存
				data: param, // 传入组装的参数
				dataType: "json",
				success: function (result) {
					// console.log(result);
					// setTimeout仅为测试延迟效果
					setTimeout(function () {
						// 封装返回数据
						var returnData = {};
						returnData.draw = result.draw;// 这里直接自行返回了draw计数器,应该由后台返回
						returnData.recordsTotal = result.totalElements;// 返回数据全部记录
						returnData.recordsFiltered = result.totalElements;// 后台不实现过滤功能，每次查询均视作全部结果
						returnData.data = result.content;// 返回的数据列表
						console.log(returnData);
						// $("tr").css("display","inline-block");
						// 调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
						// 此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
						callback(returnData);
					}, 10);
				}
			});
		};
		var work_columnDefs = [{
			// 隐藏第一列
			targets: 0,
			visible: false,
			searchable: false
		}];
		// tables_init.api().ajax.reload();
		//var work_tables = tables_init('#my_work',language,work_columns,work_columnDefs,work_ajax);
		//del('#my_work','/work/delete','._del',work_tables);// work页面删除
		
		$("#add_submit").on("click",function(){
			$.ajax({
				type: "post",
				url: "/work/add",
				dataType: "json",// 预期服务器返回的数据类型
				data: $('#add_form').serialize(),
				success: function (result) {
					// console.log("----------"+result.msg);//打印服务端返回的数据(调试用)
					if(result.success=="200"){
						layer.alert("添加成功！");
						// 这俩需要一起用hide
						$("#add").modal('hide');
						$('#add').on('hide.bs.modal', '.modal', function () {
							$(this).removeData('bs.modal');
						});
						// 下边至清空input，不清空下拉框
						/* document.getElementById("add_form").reset(); */
						workTables.ajax.reload();
					}
					if(result.fail=="400"){
						layer.alert("添加失败！");
					}
					if(result.repeat =="222"){
						layer.alert("Ticket已存在！");
					}
				},
				error : function() {
					alert("异常！");
				}
			});
		});
	}
	
});