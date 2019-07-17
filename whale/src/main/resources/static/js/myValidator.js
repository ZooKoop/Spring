$(function(){
	/**
	 * 功能未完善区
	 */
	$('#my_nav a').attr("disabled",true).css("pointer-events","none");
	
	/* ---------------------------插件初始化--------------------------- */
	/*多选初始化*/
	$('body').one('shown.bs.modal', function (e) { 
	    $(this).find('div.modal-content select').selectpicker(); 
	});
	/*全选、反选选初始化*/
	select_all('#select_all','.checkbox_select');
	/*上传初始化 - 修改方法里的初始化也需要同步*/
	fileUpload("#sqlurl","/whale/back/work/sqlUpload",['sql','txt']);//初始化提交
	fileUpload("#sqlurl_edit","/whale/back/work/sqlUpload",['sql','txt']);//初始化提交
	//多选插件初始化
	work_select_ajax('#version,#patch,#serach_patch',null);
	//监听回车按键
	$(document).keydown(function(event){ 
		if(event.keyCode==13){ 
			$("#serach_btn").click(); 
		} 
	}); 
//	时间插件初始化
	laydate.render({
		elem: '.layerDate' //指定元素
		,theme: 'grid'
	});

	/* ---------------------------查--------------------------- */
	/* work */ 
	 $('#my_work tfoot th').each( function () {
        var title = $('#my_work thead th').eq( $(this).index() ).text();
        if(title!="" && title!="编辑"){
        	$(this).html( '<input type="text" placeholder="输入'+title+'" />' );
        }
	 });
	var work_tables = tables_init('#my_work',language,work_columns,work_columnDefs,"/whale/back/work/queryAll");
	work_tables.draw( false ); //页面操作保留在当前页
	// Apply the filter
	work_tables.columns().every( function () {
	    var column = this;
//	    console.log(column.data.ticket)
	    $( 'input', this.footer() ).on( 'keyup change', function () {
	        column
	            .search( this.value )
	            .draw();
	    } );
	});
	//初始化完毕后续
	//在被选元素的开头插入内容prepend
	$('.dataTables_length').prepend($work_table_GN);
	$('.T-Serach').append($work_table_serach);
	$("#work_down").click(function(){
	    $(".buttons-excel").trigger('click');
	})
	$('#serach_btn').on('click', function () {
		var ticket = $('#serach_ticket').val();
			titel = $('#serach_titel').val();
		    patch = $('#serach_patch').val();
		    sql = $('#serach_sql').val();
		    isClose = $('#serach_isClose').val();
		work_tables
		 	.column(1)
		    .search(ticket)
		    .column(2)
		    .search(titel)
		    .column(3)
		    .search(patch)
		    .column(7)
		    .search(sql)
		    .column(8)
		    .search(isClose)
		    .draw();
	});

	/* work end */
	
	var opction_tables = tables_init('#opction_table',language,opction_columns,hideone_columnDefs,opction_ajax);
	opction_tables.draw( false ); //页面操作保留在当前页
	
	/* ---------------------------增--------------------------- */
	/*添加初始化、校验*/
	
	validatorAddInit('#add_form',workFields,'#add',work_tables,"#sqlurl");
	validatorAddInit('#opction_form',opctionFields,'#opctionModel',opction_tables);
	
	/* ---------------------------改--------------------------- */
	
	validatorAddInit('#edit_workForm',workFields,null,work_tables,"#sqlurl_edit");
	
	/* --------------------------- 删 -> 删除封装 --------------------------- */
	/*Work删除\批量*/
	$('#work_del_m').on('click', function(e) {
		e.preventDefault();
//		var idList = [];
		var idList = "";
		$(".checkbox_select").each(function(){
			if($(this).is(':checked')){
//				idList.push($(this).val());// 选中的值
				idList+=$(this).val()+",";// 选中的值
			}//获得值
		});
		if(idList!=null && idList.length>0){
			layer.confirm('确定删除 吗？', {
				title:false,
				btnAlign: 'c',
				area: ['20rem', '12rem'],
				btn: ['删除', '关闭'] //按钮
			}, function(index){
				console.log(idList)
				del_all('/whale/back/work/delete_All',idList,work_tables);
			});
		}else{
			layer.alert("请选择至少一条数据！")
		}
		//当没有定义skin时使用的是全局样式

		
	});
	$('#my_work tbody').on('click', 'a._del', function(e) {
		e.preventDefault();
		var data = work_tables.row( $(this).parents('tr')).data();
		 //当没有定义skin时使用的是全局样式
	    layer.confirm('确定删除  ['+data.ticketNumber+'] Ticket吗？', {
	                            title:false,
	                            btnAlign: 'c',
	                            area: ['20rem', '12rem'],
	                            btn: ['删除', '关闭'] //按钮
	                        }, function(index){
	                			del('/whale/back/work/delete',data.id,work_tables)
//	                            layer.close(index);
	                        });

	});
	/*opction删除*/
	$('#opction_table tbody').on('click', 'a._del', function(e) {
		e.preventDefault();
		var data = opction_tables.row($(this).parents('tr')).data();//需改tables
		//当没有定义skin时使用的是全局样式
		layer.confirm('确定删除这个参数吗？', {
						title:false,
						btnAlign: 'c',
						area: ['20rem', '12rem'],
						btn: ['删除', '关闭'] //按钮
					}, function(index){
						del('/back/workopction/delete',data.id,opction_tables);//需改tables
			//	                            layer.close(index);
					});
		
	});


});
/*初始化 . -. -..--------------------------------------------------------------------*/

/*tables初始化封装 */
function tables_init(tablesid,language,columns,columnDefs,ajaxUrl){
	return $(tablesid).DataTable({
		dom: '<"T-Serach">lrt<"hide"B><"pull-left"i><"pull-right"p>',
		buttons: [
			{
				extend: 'excel',//使用 excel扩展
				text: '导出本页',// 显示文字
				exportOptions: {
					//自定义导出选项
					//如：可自定义导出那些列，那些行
					//TODO...
				}
			}
		],
		PagingType : "full_numbers",
		language : language,
		destroy : true, // 销毁表格对象
		deferRender:true,// 延迟渲染
		Paging : true,// paging属性必须为true才能实现默认初始值得功能
		ordering:true,//是否允许Datatables开启排序
		order: [[ 8, "desc" ],[ 5, "asc" ]],//表格在初始化的时候的排序
		searching : true, // 是否禁用原生搜索(false为禁用,true为使用)
		scrollX: true,
//		 scrollCollapse: true,
		Processing : true, // DataTables载入数据时，是否显示‘进度’提示
//		 bLengthChange: false,   //去掉每页显示多少条数据方法
		lengthMenu : [ 10, 20, 50, 70, 100 ],
		columns : columns,
		columnDefs: columnDefs,
		sAjaxSource:ajaxUrl,
		fnCreatedRow: function( row, data, dataIndex ) {
			console.log(data.deadline)
			if ( data.isClose == "0" ) {
				$(row).css({"color":"#777","background":"rgba(119,119,119,.1)"}).find(".label").attr("class","label radius label-default");
				trHover("rgba(119,119,119,.3)","rgba(119,119,119,.1)");
			}
			if(data.deadline!=null){
				var i = daysBetween(data.deadline);
				// console.log(i)
				if(i<=2 && i>=0){
					$(row).css({"color":"#d9534f","background":"rgba(217,83,79,.1)"}).find(".label").attr("class","label radius label-danger");
					trHover("rgba(217,83,79,.3)","rgba(217,83,79,.1)");
				}else if(i>=3&&i<=6){
					$(row).css({"color":"#f0ad4e","background":"rgba(240,173,78,.1)"}).find(".label").attr("class","label radius label-warning");
					trHover("rgba(240,173,78,.3)","rgba(240,173,78,.1)");
				}else if(i>=7){
					$(row).css({"color":"#5cb85c","background":"rgba(92,184,92,.1)"}).find(".label").attr("class","label radius label-success");
					trHover("rgba(92,184,92,.3)","rgba(92,184,92,.1)");
				}
			}else if(data.isClose=="1"){
				$(row).css({"color":"#5cb85c","background":"rgba(91,192,222,.1)"}).find(".label").attr("class","label radius label-info");
				trHover("rgba(91,192,222,.3)","rgba(91,192,222,.1)");
			}
			function trHover(color1,color2) {
				$(row).hover(function () {
					$(this).css({"background":color1})
				},function () {
					$(this).css({"background":color2})
				})
			}
		},
		fnInitComplete: function (oSettings, json) {
			console.log($(this))
			$("[data-toggle='tooltip']").tooltip();
			//页面上点击此属性，将当前页的列表数据全部选中
			$('#select_all').on('click', function () {
				if (this.checked) {
					$('.checkbox_select').each(function () {
						this.checked = true;
					});
				} else {
					$('.checkbox_select').each(function () {
						this.checked = false;
					});
				}
			});
		}
//		 bAutoWidth : false// 自动宽度
//		 stateSave:true,
//		 serverSide : true, // 是否启动服务器端数据导入
//		 ajax: ajax
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
	search : "查询:",
	zeroRecords : "没有匹配结果",
	paginate : {
		"first" : "首页",
		"previous" : "上页",
		"next" : "下页",
		"last" : "末页"
	}
};
/*------------------work页面参数---------------------- */
var work_columns = [
	{data : 'id'},
	{data : 'ticketNumber',sWidth:"30px"},
	{data : 'ticketTitel'},
	{data : 'patch'},
	{data : 'version'},
	{data : 'deadline',sWidth:"118px"},
	{data : 'isExample'},
	{data : 'workConcentList'},
	{data : 'isClose'}
	];
var work_columnDefs = [
	{
		targets: 0,
	//	visible: false,// 隐藏第一列
		data:"id",
		orderable:false,//不执行排序
		searchable: false,
		render:function(data,type,full){
			return '<input type="checkbox" class="checkbox_select" value="'+data+'">'
		}
	},
	{
		targets: 1,
		//	visible: false,// 隐藏第一列
		data:"ticketNumber",
		orderable:false,//不执行排序
//		searchable: false,
		render:function(data,type,full){
			return '<a class="_edit" href="/whale/back/work/toUpdate?id='+full.id+'" title="编辑" class="_edit" >'+data+'</a>'
		}
	},
	{
		targets: 2,
		//	visible: false,// 隐藏第一列
		data:"ticketTitel",
		orderable:false,//不执行排序
		render:function(data,type,full){
			return "<div data-toggle='tooltip' data-placement='right' title='"+data+"' class='text-left' style='width:265px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;'>"+data+"</div>"
		}
	},
	{
		targets: 3,
		data:"patch",
		render:function(data,type,full){
			if(data == null || data == ""){
				return "<span class='label label-default radius'>无</span>"
			}
			return "<span class='label label-info radius'>"+data+"</span>"
		}
	},
	{
		targets: 4,
		data:"version",
		render:function(data,type,full){
			if(data == null || data == ""){
				return "<span class='label label-default radius'>无</span>"
			}
			return "<span class='label label-info radius'>"+data+"</span>"
		}
	},
	{
		targets: 5,
		data:"deadline",
		// type:"html",//设置列属性 https://datatables.net/reference/option/columns.type
		createdCell: function (td, cellData, rowData, row, col) {
			// console.log(rowData.isClose)
			if(cellData!=null && cellData!=""){
				var i = daysBetween(cellData);
				// console.log(i)
				if(i<=2 && i>=0){
					$(td).parent().css({"color":"#d9534f"});
				}else if(i>=3&&i<=6){
					$(td).parent().css({"color":"#f0ad4e"});
				}else if(i>=7){
					$(td).parent().css({"color":"#5cb85c"});
				}
			}
		},
		render:function(data,type,full){
			if(data!=null && data!=""){
				var i = daysBetween(data);
//				console.log(typeof(data))
				if(i<=2 && i>=0){
					return "<span data-toggle='tooltip' data-placement='right' title='剩"+i+"天' class='label label-danger radius'>"+data+"</span>"
				}else if(i>=3&&i<=7){
					return "<span data-toggle='tooltip' data-placement='right' title='剩"+i+"天' class='label label-warning radius'>"+data+"</span>"
				}else if(i>=8){
					return "<span data-toggle='tooltip' data-placement='right' title='剩"+i+"天' class='label label-success radius'>"+data+"</span>"
				}
			}else{
				return "<span class='label label-default radius'>无</span>"
			}
			return "<span class='label label-default radius'>"+data+"</span>"
		}
	},
	{
		targets: 6,
		//	visible: false,// 隐藏第一列
		data:"isExample",
		render:function(data,type,full){
			if(data>0){
				return "<span class='label label-success radius'>有</span>";
			}else{
				return "<span class='label label-default radius'>无</span>";
			}		
		}
	},
	{
		targets:7,
		//	visible: false,// 隐藏第一列
		data:"workConcentList",
		render:function(data,type,full){
			var html= '';
			if(data !=null && data.length != 0){
				return  html += '<span class="label label-success radius">有</span>';
			}else{
				return html += '<span class="label label-default radius">无</span>';
			}		
		}
	},
	{
		targets:8,
		//	visible: false,// 隐藏第一列
		data:"isClose",
		render:function(data,type,full){
			var html= '';
			if(data == 0){
				return html += '<span class="label label-default radius">Y-关</span>';
			}else if(data == 1){
				return html += '<span class="label label-success radius">Z-开</span>';
			}else if(data == 2){
				return html += '<span class="label label-warning radius">X-自提</span>';
			}				
		}
	},
	{
		targets: 9,
		//	visible: false,// 隐藏第一列
		data:"id",
		sWidth:"80px",
		orderable:false,//不执行排序<button type="button" class="btn btn-default">
		searchable: false,
		render:function(data,type,full){//data为null拿到的是整行数据
//			console.log(data)
			var html ='<div class="btn-group"><a class="_edit btn btn-default" href="/whale/back/work/toUpdate?id='+data+'" title="编辑" class="_edit" > <span class="glyphicon glyphicon-edit"></span></a>'
				html +=' <a class=" _del btn btn-default" href="javascript:void(0);"><span class="glyphicon glyphicon-trash"></span></a></div>'
			return html;
		}
	},
];
/*修改方法*/
//var work_update = function(datables){
//	$('#work_edit_model').on('shown.bs.modal', function () {
//		fileUpload("#sqlurl_edit","/back/work/sqlUpload",['sql','txt']);//初始化提交
//		$('.work_edit_btn').off().on('click',function () {
//			$.ajax({
//				type: "post",
//				url: "/back/work/Update",
//				dataType: "json",// 预期服务器返回的数据类型
//				data: $("#edit_workForm").serialize(),
//				success: function (result) {
//					if(result=200){
//						$('#work_edit_model').modal('hide');
//						layer.msg('修改成功', {icon: 1});
//						$("#sqlurl_edit").fileinput("clear");
//						datables.ajax.reload(null,false);//刷新添加完的数据
//					}else{
//						layer.alert('修改失败', {icon: 2});
//					}
//				}
//			})
//		});
//	})
//}
/* --------------------------- work2个多选框回显  --------------------------- */
var work_select_ajax = function(selectId,work){
	$.ajax({
		type: "get",
		url: "/whale/back/workopction/getAll",
		dataType: "json",// 预期服务器返回的数据类型
		success: function (data) {
			var $ID = $(selectId);
//			var oldnumber = new Array();
			$ID.find("option").remove();//先删除在添加
			data.forEach(function(e){
				$ID.append('<option value="'+e.opctionCode + '" >' + e.opctionCode + '</option>');
//		    	oldnumber.push(e.opctionCode);
		    });
			if(work!=null){
				if(work.version!="" && work.version!=null){
					$("#version_edit").selectpicker('val', work.version.split(','));//默认选中第二个参数需是数组
				}
				if(work.patch!="" && work.patch!=null){
					$("#patch_edit").selectpicker('val', work.patch.split(','));//默认选中第二个参数需是数组
				}
				$("#isClose_edit").selectpicker('val', work.isClose);
				console.log(JSON.stringify(work.workConcentList) +"---------------");
//	        $ID.selectpicker('render'); 
				if(work.workConcentList != null && work.workConcentList.length>0){
					$('.downList').find("a").remove();//先删除在添加
					work.workConcentList.forEach(function(e){
						if(e!=""&&e!=null){
							var file_sqlUrls = e.sqlUrls;
							var file_name = getCaption(file_sqlUrls);
							$('.downList').append('<a class="btn btn-sm btn-info" style="margin:0 5px 0 0 " href="'+file_sqlUrls+ '" >'+file_name+'  <span class="glyphicon glyphicon-download"></span></a>');
						}
					});
				}
			}
			$ID.selectpicker('refresh');
		}
	})
}

var $work_table_serach ="<form class='form-inline' role='form' style='margin-left: 5px;'>"+
							"<div class='form-group'>"+
							    "<div><label for='serach_ticket'>TICKET</label></div>"+
							    "<input id='serach_ticket' class='form-control' type='number' oninput='if(value.length>5)value=value.slice(0,5)'>"+
							"</div>"+
							"<div class='form-group'>"+
								"<div><label for='serach_titel'>标题</label></div>"+
								"<input id='serach_titel' class='form-control' type='text'>"+
							"</div>"+
							"<div class='form-group'>"+
								"<div><label for='serach_patch'>发包版本</label></div>"+
								"<select id='serach_patch' class='selectpicker' title='发包版本'> </select>"+
							"</div>"+
							"<div class='form-group'>"+
								"<div><label for='serach_sql'>脚本</label></div>"+
								"<select id='serach_sql' class='selectpicker' title='脚本'>"+
								  "<option></option>"+
								  "<option>有</option>"+
								  "<option>无</option>"+
								"</select>"+
							"</div>"+
							"<div class='form-group'>"+
								"<div><label for='serach_isClose'>状态</label></div>"+
								"<select id='serach_isClose' class='selectpicker' title='状态'>"+
									"<option></option>"+
									"<option>自提</option>"+
									"<option>开</option>"+
									"<option>关</option>"+
								"</select>"+
							"</div>"+
							"<div class='form-group'>"+
								"<div><label class='invisible'>查询</label></div>"+
								"<button id='serach_btn' type='button' class='btn btn-info'><span class='glyphicon glyphicon-search'></span></button>"+
							"</div>"+
						"</form>";





var $work_table_GN ="<div class='btn-group' style='margin: 5px;'>"+
					"<button class='btn btn-default' data-toggle='modal' data-target='#add'>"+
						"<span class='glyphicon glyphicon-plus'></span>"+
					"</button>"+
					"<button class='btn btn-default' id='work_del_m' title='批量删除' disabled='disabled'>"+
						"<span class='glyphicon glyphicon-trash'></span>"+
					"</button>"+
					"<button class='btn btn-default' id='work_edit_m' title='批量修改' disabled='disabled'>"+
						"<span class='glyphicon glyphicon-edit'></span>"+
					"</button>"+
					"<button class='btn btn-default' id='work_down' title='下载全部数据'>"+
						"<span class='glyphicon glyphicon-download-alt'></span>"+
					"</button>"+
					"</div>";

/*------------------opction_ajax---------------------- */
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
//				console.log(returnData);
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
	data : null,
	title: "操作",
	render:function(data, type, row, meta){
		var html ='<a href="javascript:void(0);" title="编辑" class="_edit btn btn-info" > <span class="glyphicon glyphicon-edit"></span></a>'
			html +='<a href="javascript:void(0);" class="_del btn btn-danger"><span class="glyphicon glyphicon-trash"></span></a>'
			return html;
	}
}];
/* ================= 校验 ... - .- .-. - ================= */

/*公共校验初始化、添加时校验*/
function validatorAddInit(formID,fields,modelID,vartables,inputId){

	$(formID).bootstrapValidator({
		message : '验证失败',//好像从来没出现过
		feedbacklcons : feedbacklcons,
		fields : fields
	}).on('success.form.bv', function(e) {
        // Prevent form submission
        e.preventDefault();
        // Get the form instance
        var $form = $(e.target);
        // Get the BootstrapValidator instance
        var bv = $form.data('bootstrapValidator');
        // Use Ajax to submit form data
//        $.ajax({
//			type: "post",
//			url: $form.attr('action'),
//			dataType: "json",// 预期服务器返回的数据类型
//			data:$form.serialize(),
//			success: function (result) {
//				// console.log("----------"+result.msg);//打印服务端返回的数据(调试用)
//				if(result.success=="200"){
//					layer.msg("添加成功！");
//					vartables.ajax.reload(null,false);//刷新添加完的数据
//					$(modelID).modal('hide');//模态框关闭背景隐藏
//				}
//				if(result.fail=="400"){
//					layer.alert("添加失败！");
//				}
//				if(result.repeat =="222"){
//					layer.alert("已存在，重新添加！");
//				}
//			},
//			error : function() {
//				alert("异常！");
//			}
//		});
        //提交上传
        $.post($form.attr('action'), $form.serialize(), function(result) {
        	if(result.success=="200"){
				
				var i = $(".file-caption-name").eq(0).text();
				if(i!==null && i!==""){
					$(inputId).fileinput('upload');
				}
				if(window.location.pathname.indexOf("back/work/toUpdate") >= 0){
					layer.msg("操作成功！",{time:300},function(){
						window.location.href = "/whale/back/work/toWork";
					});
				}else{
					$(modelID).modal('hide');//模态框关闭背景隐藏
					layer.msg("操作成功！",{time:300},function(){
						vartables.ajax.reload(null,false);//当前页刷新添加完的数据
//						window.location.reload();
					});
				}
			}
			if(result.fail=="400"){
				layer.alert("操作失败！");
			}
			if(result.repeat =="222"){
				layer.alert("已存在，重新添加！");
			}
        }, 'json');
        
    });
	$(modelID).on('hide.bs.modal', function () {//模态框关闭触发
		$(formID)[0].reset();//重置表单，此处用jquery获取Dom节点时一定要加[0]
		$('.selectpicker').selectpicker('val',['noneSelectedText']);//清空
		$('.selectpicker').selectpicker('refresh');//刷新
		$(formID).data('bootstrapValidator').resetForm(true);//清除当前验证的关键之处加，true清空值不太好使文字域的清除不了
		$('#isClose').selectpicker('val','0');//清空
	});
};
/*修改校验*/
/*function updateValid(formID,opctionFields,addBtnID,url,modelID,vartables){
	$(formID).bootstrapValidator({
		message : '通用的验证失败消息',//好像从来没出现过
		feedbacklcons : feedbacklcons,
		fields : opctionFields
	});
	$(addBtnID).on('click',function() {//非submit按钮点击后进行验证，如果是submit则无需此句直接验证
		手动验证表单，当是普通按钮时。
		$(formID).data('bootstrapValidator').validate(); 
		if($(formID).data('bootstrapValidator').isValid()){ 
			addInit(url,formID,modelID,vartables);
		}
	});
}*/
/*校验公共选项*/
var feedbacklcons ={
		valid : 'glyphicon glyphicon-ok',
		invalid : 'glyphicon glyphicon-remove',
		validating : 'glyphicon glyphicon-refresh'
};
/* --------------------------- opction表字段校验 ---------------------------*/
var opctionFields = {
		opctionCode : {
			message : '验证失败',
			validators : {
				required: true,
				stringLength: {
//					min: 6,
					max: 5,
					message: '最长5个字符'
				},
				notEmpty : {
					message : '不能为空'
				}
			}
		},
		opction : {
			validators : {
				regexp: {
				    regexp: /^[a-zA-Z0-9_]+$/,
				    message: '正则验证，这里验证只能输入大小写字母数字和下划线'
				}
			}
		}
};
/* --------------------------- work表字段校验 ---------------------------*/
var workFields = {
		ticketNumber : {
			message : '格式错误',
			validators : {
				notEmpty : {
					message : '不能为空'
				},
				regexp: {
				    regexp: /^[a-zA-Z0-9_]+$/,
				    message: '正则验证，这里验证只能输入大小写字母数字和下划线'
				},
				stringLength : {
                    min : 5,
                    max : 5,
                    message : '长度5位字符'
                }
			}
		},
		isClose:{
			validators : {
				notEmpty : {
					message : '必须选择'
				}
//				callback: {
//					message: '必须选择',
//                    callback: function(value, validator) {
//                             if (value == -1) {
//                                return false;
//                             } else {
//                               return true;
//                             }
//
//                    }
//                 }
			}
		}
};
/* ================= 校验 . -. -.. =================*/
/* --------------------------- 初始化 --------------------------- */

/* --------------------------- 删除封装 --------------------------- */
/*删除ajax封装*/
function del(urls,id,tabName){
	 $.ajax({
		 url: urls,
		 type: 'get',
		 dataType: 'json',
		 data: {"id": id},
		 success: function(data) {
			 console.log(data)
			 if(data==200){
				 //如果后台删除成功，则刷新表格，并提示用户删除成功
//				 layer.msg("删除成功！",{icon:1,time:2000});
				 layer.msg("删除成功！");
				 //保留分页信息
				 tabName.ajax.reload(null,false);//需初始化完毕，加table.draw( false ); 
				 //不保留分页信息
//				 tabName.ajax.reload();
			 }
		 }
	 })
 }
function del_all(urls,id,tabName){
	$.ajax({
		url: urls,
		type: 'post',
		dataType: 'json',
		data: {"ids": id},
//		data: {"ids": JSON.stringify(id)},
		success: function(data) {
			console.log(data)
			if(data==200){
				//如果后台删除成功，则刷新表格，并提示用户删除成功
//				 layer.msg("删除成功！",{icon:1,time:2000});
				layer.msg("删除成功！");
				//保留分页信息
				tabName.ajax.reload(null,false);//需初始化完毕，加table.draw( false ); 
				//不保留分页信息
//				 tabName.ajax.reload();
			}
		}
	})
}

/* --------------------------- 全选、反选封装 --------------------------- */
var select_all = function(headId,bodyClass){
	$(headId).on('click', function () {
		if(this.checked){
			$(bodyClass).each(function(){
				this.checked = true;	
			})
		}else{
			$(bodyClass).each(function(){
				this.checked = false;	
			})
		}
	})
}
/* --------------------------- 上传 --------------------------- */
var fileUpload =function(inputId,url,type,fromID){
	$(inputId).fileinput({
		uploadUrl : url, // you must set a valid URL here else you will get an error
		allowedFileExtensions : type,
		uploadAsync:true,//是否为异步上传,默认true
		overwriteInitial : true,//是否显示预览
		dropZoneEnabled:true,//是否显示拖拽
		showUpload: false, //是否显示上传按钮
//		showCaption: true,//是否显示标题
		//showClose:true,//是否显示关闭按钮
		//autoReplace:false,//是否自动替换当前图片，设置为true时，再次选择文件，
		showPreview : true, //展前预览
		language : 'zh',
		maxFileSize : 1024 * 100,//1024KB
		browseClass : "btn btn-primary",//按钮样式
		maxFileCount : 5,//允许同时上传的最大文件数
		previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
		msgFilesTooMany : "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
		enctype : 'multipart/form-data',
		//allowedFileTypes: ['image', 'video', 'flash'],
		overwriteInitial: false,//不覆盖已存在的图片 
		// 这个配置就是解决办法了,初始化时限制图片大小
//        previewSettings: {
//            image: {width: "100px", height: "100px"},
//        },
		slugCallback : function(filename) {
			return filename.replace('(', '_').replace(']', '_');
		},
		uploadExtraData: function(){//报length错误时，看报错是否为这个方法。返回值一个空的json
//			var obj = {}; 
//			$(fromID).find('input').each(function() { 
//			var id = $(this).attr('id'), val = $(this).val(); 
//			obj[id] = val; 
//			}); 
			  var data={ticketNumber:$("#ticketNumber,#ticketNumber_edit").val()};
			return data; 
        }
	}).on("filebatchselected", function(event, files) {//自动提交
//		$(this).fileinput("upload");
	}).on("fileuploaded", function(event, data) {
		//filebatchuploadsuccess同步上传成功结果处理fileuploaded  filebatchuploaderror
		if (data.response.status) {
//			layer.msg(data.response.msg + " - 上传成功！", {
//				time : 3000
//			});
		} else {
			if(data.response.namesame!="" && data.response.namesame!=null){
				layer.alert(data.response.namesame + "文件名称重复，上传失败！", {
					icon : 2
//					time : 2000
				},function(index){
					layer.close(index);
					$(inputId).fileinput("clear");
				});
				
			}
			if(data.response.error!="" && data.response.error!=null){
				layer.alert(data.response.error + "上传失败！", {
					icon : 2
//					time : 2000
				},function(index){
					layer.close(index);
					$(inputId).fileinput("clear");
				});
			}
			
		}
		
	});
} 
/* --------------------------- 默认隐藏第一段 --------------------------- */
var hideone_columnDefs = [{
	// 隐藏第一列
	targets: 0,
	visible: false,
	searchable: false
}];
/*------------------work页面参数 服务器模式用---------------------- */
var work_ajax = function (data, callback, settings) {
	// ajax请求数据
	$.ajax({
		type: "GET",
		url: "/back/work/queryAll",
		cache: false, // 禁用缓存
		data: null, // 传入组装的参数
		dataType: "json",
		success: function (result) {
			 
			// setTimeout仅为测试延迟效果
			setTimeout(function () {
				// 封装返回数据
//				var returnData = {};
//				returnData.draw = data.draw;// 这里直接自行返回了draw计数器,应该由后台返回
//				returnData.recordsTotal = result.totalElements;// 返回数据全部记录
//				returnData.recordsFiltered = result.totalElements;// 后台不实现过滤功能，每次查询均视作全部结果
//				returnData.data = result.content;// 返回的数据列表
//				console.log(returnData);
				// $("tr").css("display","inline-block");
				// 调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
				// 此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
//				callback(result);
				console.log(JSON.stringify(result)+"返回的数据");
			}, 10);
		}
	});
};
/*------------------截取文件名---------------------- */
function getCaption(obj){
	var index=obj.lastIndexOf("/");
	obj=obj.substring(index+1,obj.length);
	return obj;
}
function daysBetween(deadline){
	var local = new Date();
	var localMonth = local.getMonth() + 1;
	var localDay = local.getDate();
	var localYear = local.getFullYear();  
	var localTime = localYear+'-'+localMonth+'-'+localDay;
//	console.log(localTime)
	var sArr = deadline.split("-");
	var eArr = localTime.split("-");
	var sRDate = new Date(sArr[0], sArr[1], sArr[2]);
	var eRDate = new Date(eArr[0], eArr[1], eArr[2]);
	var result = (sRDate-eRDate)/(24*60*60*1000);
return result;
}
//var work_find_ajax = function (data, callback, settings) {
//// 封装请求参数
//var param = {};
//param.ticketNumber = $("#find_ticketNumber").val();
//param.isClose = $("#find_isClose").val();
//param.size = data.length;// 页面显示记录条数，在页面显示每页显示多少项的时候
//// console.log(data.length)
//// param.start = data.start;//开始的记录序号
//param.page = (data.start / data.length);// 当前页码
////param.search = data.search.value;//搜索条件
//if (data.order.length > 0) {
//	// param.order = data.columns[data.order[3].column].data;
//	param.order = data.columns[6].data;
//	param.dir = data.order[0].dir;
//	console.log(param.order+"----------"+param.dir);
//} 
////console.log(JSON.stringify(data)+"----------");
////alert(JSON.stringify(param))
//// ajax请求数据
//$.ajax({
//	type: "GET",
//	url: "/back/work/queryAll",
//	cache: false, // 禁用缓存
//	data: param, // 传入组装的参数
//	dataType: "json",
//	success: function (result) {
////		 console.log(JSON.stringify(result));
//		// setTimeout仅为测试延迟效果
//		setTimeout(function () {
//			// 封装返回数据
//			var returnData = {};
//			returnData.draw = data.draw;// 这里直接自行返回了draw计数器,应该由后台返回
//			returnData.recordsTotal = result.totalElements;// 返回数据全部记录
//			returnData.recordsFiltered = result.totalElements;// 后台不实现过滤功能，每次查询均视作全部结果
//			returnData.data = result.content;// 返回的数据列表
//			console.log(returnData);
//			callback(returnData);
//		}, 10);
//	}
//});
//};