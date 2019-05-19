$(function(){
	$("#my_nav li").on("click",function(){
		$(this).addClass("active").siblings().removeClass("active");
		$("#xitong").toggleClass("rotate_");
	})
	/*左侧导航栏-跳转保存样式*/
	/*$("#my_nav a").each(function(){
		$this = $(this);
		//alert($this[0].href + "==========" + window.location);
		if($this[0].href==String(window.location)){
			//alert($(this).parents())
			//console.log($(this).parent().parent().prev().html()+"1111111111111111111111111111111111111")
		    $this.parent().addClass("active").siblings().removeClass("active");
			$this.parent().parent().parent().siblings().removeClass("active");//去除默认样式
			if(String(window.location).indexOf("/back/user/list")>0){
				//$this.parent().parent().prev().addClass("active");//只有系统管理改变样式
				$this.parent().parent().parent().addClass("active");//系统管理下的所有都包括
				$this.parent().parent().parent().find("i").first().addClass("rotate_");//系统管理下的所有都包括
				$this.parent().addClass("active_two").removeClass("active");//二级菜单样式
				$this.parent().parent().addClass("in");
			}
		}
	});*/
	
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
	/* ---------------------------userlist页面参数------------------- */
	var myTables = tables_init('#table_id_example',language,list_columns,list_columnDefs,list_ajax);
	/*del_f('#table_id_example','/back/user/delete','._del',myTables);*/
	/*使用layer弹框，子ifram页面，父页面提交*/
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
			content : '/back/user/toSaveUser',
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
						url : '/back/user/saveUser',
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
	/* ---------------------------work页面参数------------------- */
	var work_tables = tables_init('#my_work',language,work_columns,hideone_columnDefs,work_ajax);
	/*添加初始化、校验*/
	validatorInit('#add_form',null,'#add_submit','/back/work/add','#add',work_tables);
	/*删除*/
	$('#my_work').on('click','._del', function () {//._del是数组中删除按钮的类
		var data = work_tables.row( $(this).parents('tr')).data().id;
		del('/back/work/delete',data,work_tables)
	});
	/* ---------------------------opction页面参数------------------- */
	var opction_tables = tables_init('#opction_table',language,opction_columns,hideone_columnDefs,opction_ajax);
	/*添加初始化、校验*/
	validatorInit('#opction_form',opctionFields,'#btnOpction','/back/workopction/add','#opctionModel',opction_tables);
	/*删除*/
	$('#opction_table').on('click','._del', function () {//._del是数组中删除按钮的类
		var data = opction_tables.row( $(this).parents('tr')).data().id;
		del('/back/workopction/delete',data,opction_tables)
	});
	/* ---------------------------opction页面参数------------------- */
});
/*初始化 . -. -..--------------------------------------------------------------------*/

/* ================= 校验 ... - .- .-. - ================= */

/*公共校验初始化、添加时校验*/
function validatorInit(formID,opctionFields,addBtnID,url,modelID,vartables){
	$(formID).bootstrapValidator({
		message : '通用的验证失败消息',//好像从来没出现过
		feedbacklcons : feedbacklcons,
		fields : opctionFields
	});
	
	$(addBtnID).on('click',function() {//非submit按钮点击后进行验证，如果是submit则无需此句直接验证
		/*手动验证表单，当是普通按钮时。*/
		$(formID).data('bootstrapValidator').validate(); 
		if($(formID).data('bootstrapValidator').isValid()){ 
			addInit(url,formID,modelID,vartables);
		}
	});
	$(modelID).on('hide.bs.modal', function () {//模态框关闭触发
		$(formID)[0].reset();//重置表单，此处用jquery获取Dom节点时一定要加[0]
		$(formID).data('bootstrapValidator').resetForm();//清除当前验证的关键之处
	});
};
/*校验公共选项*/
var feedbacklcons ={
		valid : 'glyphicon glyphicon-ok',
		invalid : 'glyphicon glyphicon-remove',
		validating : 'glyphicon glyphicon-refresh'
};
/*work表字段校验*/

/*opction表字段校验*/
var opctionFields = {
		opctionCode : {
			message : '验证失败',
			validators : {
				required: true,
				notEmpty : {
					message : '不能为空'
				}
			}
		},
		opction : {
			validators : {
				notEmpty : {
					message : '不能为空'
				}
			}
		}
};

/* ================= 校验 . -. -.. =================*/

/*boot弹出框添加封装*/
function addInit(url,formName,modelID,vartables){
		$.ajax({
			type: "post",
			url: url,
			dataType: "json",// 预期服务器返回的数据类型
			data: $(formName).serialize(),
			success: function (result) {
				// console.log("----------"+result.msg);//打印服务端返回的数据(调试用)
				if(result.success=="200"){
					layer.alert("添加成功！");
					// 这俩需要一起用hide
					$(modelID).modal('hide');
					/*$(modelID).on('hide.bs.modal','.modal', function () {
						$(this).removeData("bs.modal");
					});*/
					$(formName)[0].reset();
					// 下边至清空input，不清空下拉框
					/* document.getElementById("add_form").reset(); */
					vartables.ajax.reload();
				}
				if(result.fail=="400"){
					layer.alert("添加失败！");
				}
				if(result.repeat =="222"){
					layer.alert("已存在，重新添加！");
				}
			},
			error : function() {
				alert("异常！");
			}
		});
};


/*tables初始化封装 */
function tables_init(tablesid,language,columns,columnDefs,ajax){
	return $(tablesid).DataTable({
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
/*tables公用语言设置 */
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
/*删除ajax封装*/
function del(urls,data,tabName){
	 $.ajax({
		 url: urls,
		 type: 'get',
		 dataType: 'json',
		 data: {"id": data},
		 success: function(data) {
			 console.log(data)
			 if(data==200){
				 //如果后台删除成功，则刷新表格，并提示用户删除成功
				 layer.msg("删除成功！",{icon:1,time:2000});
				 //保留分页信息
				 //myTables.ajax.reload(null, false);
				 //不保留分页信息
				 tabName.ajax.reload();
			 }
		 }
	 })
 }
3/*----------------------------userlist页面参数-------------------------*/
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
		url: "/back/user/listInfo",
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
	data : 'regTime'
	// title: "注册时间",
	//render:function(data, type, row, meta){
		// console.log(data)
		// console.log(new
		// Date(parseInt(Date.parse(data))).toLocaleString())
		//return new Date(parseInt(Date.parse(data))).toLocaleString().replace(/[\u4e00-\u9fa5]/g, " ").replace(new RegExp('/','g'),"-");
	//} 
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
var list_columnDefs = [{
	// 指定第最后一列
	targets: 7,
	render: function(data, type, row, meta) {
		return '<a class="_eidt btn btn-info" type="button" href="#" data-toggle="tooltip" title="添加用户" ><span class="glyphicon glyphicon-list-alt"></span> 详情</a> <a class="_eidt btn btn-warning" type="button" href="#" > <span class="glyphicon glyphicon-edit"></span> 编辑</a> <a class="_del btn btn-danger" type="button" href="#" ><span class="glyphicon glyphicon-trash"></span> 删除</a>';
	}
},{
	// 隐藏第一列
	targets: 0,
	visible: false,
	searchable: false
}];
	
/*------------------work页面参数---------------------- */
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
	 render:function(data, type, row, meta){
		 return "<div class='text-left' style='width:265px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;'>"+data+"</div>"
	 }
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
	defaultContent:""
	// title: "时间",
//	render:function(data, type, row, meta){
//		return new Date(parseInt(Date.parse(data))).toLocaleString().replace(/[\u4e00-\u9fa5]/g, " ").replace(new RegExp('/','g'),"-");
//	}
},{
	data : 'updateTime',
	defaultContent:""
	// title: "时间",
//	render:function(data, type, row, meta){
//		return new Date(parseInt(Date.parse(data))).toLocaleString().replace(/[\u4e00-\u9fa5]/g, " ").replace(new RegExp('/','g'),"-");
//	}
},{
	data : null,
	// title: "操作",
	render:function(data, type, row, meta){
		var html ='<a class="_eidt btn btn-info" type="button" href="#" > <span class="glyphicon glyphicon-edit"></span></a>'
		html +='<a class="_del btn btn-danger" type="button" href="#" ><span class="glyphicon glyphicon-trash"></span></a>'
		return html;
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
		url: "/back/work/queryAll",
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
/*------------------opction页面参数---------------------- */
var opction_columns = [{
	data : 'id'
},{
	data : 'opctionCode'
},{
	data : 'opction'
},{
	data : null,
	title: "操作",
	render:function(data, type, row, meta){
		return '<a class="_eidt btn btn-info" type="button" href="#" > <span class="glyphicon glyphicon-edit"></span></a> <a class="_del btn btn-danger" type="button" href="#" ><span class="glyphicon glyphicon-trash"></span></a>';
	}
}];
var opction_ajax = function (data, callback, settings) {
	// 封装请求参数
	var param = {};
	param.size = data.length;// 页面显示记录条数，在页面显示每页显示多少项的时候
	// console.log(data.length)
	// param.start = data.start;//开始的记录序号
	param.page = (data.start / data.length);// 当前页码
	// param.search = data.search.value;//搜索条件
//	if (data.order.length > 0) {
//		 param.order = data.columns[data.order[3].column].data;
//		param.order = data.columns[7].data;
//		param.dir = data.order[0].dir;
//		console.log(param.order+"----------"+param.dir);
//	} 
	
	// ajax请求数据
	$.ajax({
		type: "GET",
		url: "/back/workopction/queryAll",
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
var hideone_columnDefs = [{
	// 隐藏第一列
	targets: 0,
	visible: false,
	searchable: false
}];
/*笔记
(function($) {
	})(jQuery);//闭包格式
var isIndex = window.location.href;// 当前url
*/
