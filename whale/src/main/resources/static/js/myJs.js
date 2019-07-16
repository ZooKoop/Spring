$(function(){
	$("#my_nav li").on("click",function(){
		$(this).addClass("active").siblings().removeClass("active");
		$("#xitong").toggleClass("rotate_");
	})
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

	/* ---------------------------userlist页面参数------------------- */
//	var myTables = tables_init('#table_id_example',language,list_columns,list_columnDefs,list_ajax);
//	/*del_f('#table_id_example','/back/user/delete','._del',myTables);*/
//	/*使用layer弹框，子ifram页面，父页面提交*/
//	$("#saveUser").on("click",function () {
//		layer.open({
//			type : 2,
//			title :['注册用户','font-size:18px;font-weight:bold;'],
//			area : [ '50%', '90%' ],
//			shade : 0.3,
//			maxmin : false,// 最大化和最小化按钮
//			resize : false,// 拉伸
//			scrollbar : false,// 滚动条
//			moveOut : false,// 拖动到屏幕外面
//			fixed : false, // 不固定
//			content : '/back/user/toSaveUser',
//			end : function() {
//				location.reload();
//			},
//			btn : [ '提交', '取消' ], // 按钮上传按钮怎么提交的是 模态框的？
//			yes : function(index, layero) {
//				var iframes = $(layero).find("iframe")[0].contentWindow;// 找页面html
//				var formdata = new FormData(iframes.document
//						.getElementById("file"));// 序列化表单
//				console.log(formdata);
//				// iframeWin.contentWindow.uploadFiles();//doSubmit
//				// 是你在弹出层的那个jsp里写的表单提交方法
//				if ("formdata" != typeof (formdata) && formdata != null
//						&& formdata != "") {
//					$.ajax({
//						url : '/back/user/saveUser',
//						type : 'POST',
//						data : formdata,
//						contentType : false, // 不设置内容类型
//						processData : false, // 不处理数据
//						success : function(data) {
//							console.log(data);
//							if(data==200){
//								layer.msg('操作成功!',{time:1000},function(){
//									layer.close(index);
//								});
//							}else{
//								layer.msg(data, {icon: 2, time:2000,anim:6});
//							}
//						},
//						error : function() {
//							layer.alert('图片上传失败!', {icon: 2});
//						}
//					});
//				} else {
//					layer.alert("选择的文件无效！请重新选择",{icon:1});
//				}
//			}
//		});
//	});
});
/*初始化 . -. -..--------------------------------------------------------------------*/



/*----------------------------userlist页面参数-------------------------*/
//var list_ajax = function (data, callback, settings) {
//	// 封装请求参数
//	var param = {};
//	param.size = data.length;// 页面显示记录条数，在页面显示每页显示多少项的时候
//	// param.start = data.start;//开始的记录序号
//	param.page = (data.start / data.length);// 当前页码
//	// param.search = data.search.value;//搜索条件
//	// if (data.order.length > 0) {
//	// param.order = data.columns[data.order[0].column].data;
//	// param.dir = data.order[0].dir;
//	// }
//	// console.log(param);
//	// ajax请求数据
//	$.ajax({
//		type: "GET",
//		url: "/back/user/listInfo",
//		cache: false, // 禁用缓存
//		data: param, // 传入组装的参数
//		dataType: "json",
//		success: function (result) {
//			// console.log(result);
//			// setTimeout仅为测试延迟效果
//			setTimeout(function () {
//				// 封装返回数据
//				var returnData = {};
//				returnData.draw = result.draw;// 这里直接自行返回了draw计数器,应该由后台返回
//				returnData.recordsTotal = result.totalElements;// 返回数据全部记录
//				returnData.recordsFiltered = result.totalElements;// 后台不实现过滤功能，每次查询均视作全部结果
//				returnData.data = result.content;// 返回的数据列表
//				console.log(returnData);
//				// 调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
//				// 此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
//				callback(returnData);
//			}, 10);
//		}
//	});
//};
//var list_columns = [{
//	data : 'id'
//},{
//	data:null,
//	render:function(data, type, row, meta){
//		return "<input type='checkbox' name='onDutyToId'  value='" + data + "'>";
//	}
//},{
//	data : 'userName'
//}, {
//	data : 'userPassword'
//}, {
//	data : 'email'
//}, {
//	data : 'regTime'
//	// title: "注册时间",
//	//render:function(data, type, row, meta){
//		// console.log(data)
//		// console.log(new
//		// Date(parseInt(Date.parse(data))).toLocaleString())
//		//return new Date(parseInt(Date.parse(data))).toLocaleString().replace(/[\u4e00-\u9fa5]/g, " ").replace(new RegExp('/','g'),"-");
//	//} 
//}, {
//	data : 'srcImg',
//	// title: "头像",
//	render:function(data, type, row, meta){
//		if(data==null){
//			return "无";
//		}
//		return "<img class='img-rounded center-block img-thumbnail img-responsive' style='width:80px' src='" + data + "' />";
//	}
//}];
//var list_columnDefs = [{
//	// 指定第最后一列
//	targets: 7,
//	render: function(data, type, row, meta) {
//		return '<a class="_eidt btn btn-info" type="button" href="#" data-toggle="tooltip" title="添加用户" ><span class="glyphicon glyphicon-list-alt"></span> 详情</a> <a class="_eidt btn btn-warning" type="button" href="#" > <span class="glyphicon glyphicon-edit"></span> 编辑</a> <a class="_del btn btn-danger" type="button" href="#" ><span class="glyphicon glyphicon-trash"></span> 删除</a>';
//	}
//},{
//	// 隐藏第一列
//	targets: 0,
//	visible: false,
//	searchable: false
//}];

/*笔记
(function($) {
	})(jQuery);//闭包格式
var isIndex = window.location.href;// 当前url
*/
