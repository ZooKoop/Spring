$(function(){
	/* ---------------------------插件初始化--------------------------- */
	/*多选初始化*/
	$('body').one('shown.bs.modal', function (e) { 
	    $(this).find('div.modal-content select').selectpicker(); 
	})
	$("#work_down").click(function(){
	    $(".buttons-excel").trigger('click');
	})
	/*全选、反选选初始化*/
	select_all('#select_all','.checkbox_select');
	/* ---------------------------查--------------------------- */
	/* work */ 
	var work_tables = tables_init('#my_work',language,work_columns,work_columnDefs,"/back/work/queryAll");
	work_tables.draw( false ); //页面操作保留在当前页
	/* work end */
	var opction_tables = tables_init('#opction_table',language,opction_columns,hideone_columnDefs,opction_ajax);
	opction_tables.draw( false ); //页面操作保留在当前页
	
	/* ---------------------------增--------------------------- */
	/*添加初始化、校验*/
	work_select_ajax('#version,#patch');
	fileUpload("#sqlurl","/back/work/sqlUpload",['jpg','png']);//初始化不提交
	validatorAddInit('#add_form',workFields,'#add',work_tables,"#sqlurl");
	
//	})
	validatorAddInit('#opction_form',opctionFields,'#opctionModel',opction_tables);
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
				del_all('/back/work/delete_All',idList,work_tables);
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
	                			del('/back/work/delete',data.id,work_tables)
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
	/* ---------------------------改--------------------------- */
	/*编辑*/
	$('#my_work tbody').on('click', 'a._edit', function(e) {
		e.preventDefault();
		var data = work_tables.row( $(this).parents('tr')).data();
		$('#work_edit_model').modal({
			 	backdrop: 'static',     // 点击空白不关闭
			    keyboard: false,        // 按键盘esc也不会关闭
	            remote: '/back/work/toUpdate'
	    });
		//查完值，直接在ajax里赋值
		work_select_ajax('#version_edit,#patch_edit',data.id);
		work_update(work_tables);
	});
});
/*初始化 . -. -..--------------------------------------------------------------------*/
/*tables初始化封装 */
function tables_init(tablesid,language,columns,columnDefs,ajaxUrl){
	return $(tablesid).DataTable({
		 dom: 'Bfrtip',
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
		 order: [[ 5, "desc" ],[ 8, "desc" ]],//表格在初始化的时候的排序
		 searching : true, // 是否禁用原生搜索(false为禁用,true为使用)
		 scrollX: true,
		 Processing : true, // DataTables载入数据时，是否显示‘进度’提示
//		 bLengthChange: false,   //去掉每页显示多少条数据方法
		 lengthMenu : [ 5,10, 20, 50, 70, 100 ],
		 columns : columns,
		 columnDefs: columnDefs,
		 sAjaxSource:ajaxUrl
//		 stateSave:true,
//		 bAutoWidth : true,// 自动宽度
//		 serverSide : true, // 是否启动服务器端数据导入
//		 ajax: ajax
	 });
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
/*------------------work页面参数---------------------- */
var work_columns = [
	{data : 'id'},
	{data : 'ticketNumber'},
	{data : 'ticketTitel'},
	{data : 'patch'},
	{data : 'version'},
	{data : 'dateTime',sWidth:"118px"},
	{data : 'isExample'},
	{data : 'sqlUrls'},
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
		targets: 2,
		//	visible: false,// 隐藏第一列
		data:"ticketTitel",
		orderable:false,//不执行排序
//		searchable: false,
		render:function(data,type,full){
			return "<div class='text-left' style='width:265px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;'>"+data+"</div>"
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
		data:"sqlUrls",
		render:function(data,type,full){
			var html= '';
			if(data !=null && data.length != 0){
				return html += '<span class="label label-success radius">有</span>';
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
			if(data == 1){
				return html += '<span class="label label-success radius">开</span>';
			}else{
				return html += '<span class="label label-default radius">关</span>';
			}		
		}
	},
	{
		targets: 9,
		//	visible: false,// 隐藏第一列
		data:null,
		sWidth:"90px",
		orderable:false,//不执行排序<button type="button" class="btn btn-default">
		searchable: false,
		render:function(data,type,full){//data为null拿到的是整行数据
			var html ='<div class="btn-group"><a class="_edit btn btn-default" href="javascript:void(0);" title="编辑" class="_edit" > <span class="glyphicon glyphicon-edit"></span></a>'
				html +=' <a class=" _del btn btn-default" href="javascript:void(0);"><span class="glyphicon glyphicon-trash"></span></a></div>'
			return html;
		}
	},
];
/*修改方法*/
var work_update = function(datables){
	$('#work_edit_model').on('shown.bs.modal', function () {
		$('.work_edit_btn').off().on('click',function () {
			$.ajax({
				type: "post",
				url: "/back/work/Update",
				dataType: "json",// 预期服务器返回的数据类型
				data: $("#edit_workForm").serialize(),
				success: function (result) {
					if(result=200){
						$('#work_edit_model').modal('hide');
						layer.msg('修改成功', {icon: 1});
						datables.ajax.reload(null,false);//刷新添加完的数据
					}else{
						layer.alert('修改失败', {icon: 2});
					}
				}
			})
		});
	})
}
/* --------------------------- work2个多选框回显  --------------------------- */
var work_select_ajax = function(selectId,infoID){
	$.ajax({
		type: "get",
//		cache:false,
		url: "/back/workopction/getAll",
		dataType: "json",// 预期服务器返回的数据类型
		success: function (data) {
			if(infoID!=null && infoID!= ""){
				work_updateInfo(infoID);//回显
			}
			var $ID = $(selectId);
			var oldnumber = new Array();
			$ID.find("option").remove();//先删除在添加
			data.forEach(function(e){
//				$('<option value="'+e.opctionCode + '" >' + e.opctionCode + '</option>').appendTo($ID);
				$ID.append('<option value="'+e.opctionCode + '" >' + e.opctionCode + '</option>');
		    	oldnumber.push(e.opctionCode);
		    });
			console.log(JSON.stringify(data) +"---------------");
//			$ID.selectpicker('val', oldnumber);//默认选中第二个参数需是数组
			$ID.selectpicker('refresh');
//	       $ID.selectpicker('render'); 
			
		}
	})
}
/*获取当前信息*/
var work_updateInfo = function(dataId){
	$.ajax({
		type: "GET",
//		cache:false,
		url: "/back/work/toUpdateInfo",
		dataType: "json",// 预期服务器返回的数据类型
		data: {"id": dataId},
		success: function (result) {
//			console.log("----------"+JSON.stringify(result));//打印服务端返回的数据(调试用)，object转键值对字符串
//			layer.alert("1");
            //清空多选（多选为selectpicker插件）
			$("#work_id").val(result.id);
			$("#work_security_user_id").val(result.securityUser.id);
			$("#ticketNumber_edit").val(result.ticketNumber);
			$("#ticketTitel_edit").val(result.ticketTitel);
			$("#description_edit").val(result.description);
			$("#work_edit_dateTime").val(result.dateTime);
			$('#isExample_edit').selectpicker('val', result.isExample);//设置选中 
			if(result.patch != null && result.patch != ""){
				$('#patch_edit').selectpicker('val', result.patch.split(','));//设置选中 
			}
			if(result.version != null && result.version != ""){
				$('#version_edit').selectpicker('val',result.version.split(','));//split分割转为数组
			}
			$('#isSql_edit').selectpicker('val',result.isSql);
			$('#isClose_edit').selectpicker('val',result.isClose);
			$('#patch_edit,#isExample_edit,#version_edit,#isClose_edit,#isSql_edit').selectpicker('refresh');
			$('#work_edit_model').modal('show');
		},
		error : function() {
			alert("请求失败！");
		}
	});
}
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
        $(inputId).fileinput('upload').fileinput('reset');
        $.post($form.attr('action'), $form.serialize(), function(result) {
        	if(result.success=="200"){
				layer.msg("添加成功！");
				vartables.ajax.reload(null,false);//刷新添加完的数据
				$(modelID).modal('hide');//模态框关闭背景隐藏
			}
			if(result.fail=="400"){
				layer.alert("添加失败！");
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
var fileUpload =function(inputId,url,type){
	$(inputId).fileinput({
		uploadUrl : url, // you must set a valid URL here else you will get an error
		allowedFileExtensions : type,
		uploadAsync:false,//是否为异步上传,默认true
		overwriteInitial : false,//是否显示预览
		dropZoneEnabled:false,//是否显示拖拽
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
		slugCallback : function(filename) {
			return filename.replace('(', '_').replace(']', '_');
		},
		uploadExtraData: function(){//报length错误时，看报错是否为这个方法。返回值一个空的json
            return {};
        }
	}).on("filebatchselected", function(event, files) {//自动提交
//		$(this).fileinput("upload");
	}).on("filebatchuploadsuccess", function(event, data) {
		//filebatchuploadsuccess同步上传成功结果处理  filebatchuploaderror
		if (data.response.status) {
//			layer.msg(data.response.msg + " - 上传成功！", {
//				time : 3000
//			});
		} else {
			if(data.response.namesame!="" && data.response.namesame!=null){
				layer.alert(data.response.namesame + "文件名称重复，上传失败！", {
					icon : 2
//					time : 2000
				});

				$(inputId).outerHTML;
				return false;
			}
			if(data.response.error!="" && data.response.error!=null){
				layer.alert(data.response.error + "上传失败！", {
					icon : 2
//					time : 2000
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
//var work_columns = [{
//data : 'id'
//},{
//data : 'ticketNumber',
//// title: "Ticket号",
//name : 'ticketNumber',
//render:function(data, type, row, meta){
//	return "#"+ data +"";
//} 
//},{
//data : 'description',
//defaultContent:"",
//// title: "Ticket描述"
//render:function(data, type, row, meta){
//	return "<div class='text-left' style='width:265px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;'>"+data+"</div>"
//}
//},{
//data : 'patch',
//defaultContent:"",
//title: "E-patch",
//},{
//data : 'isExample',
//defaultContent:"",
//// title: "Example",
//render:function(data, type, row, meta){
//	if(data>0){
//		return "<p style='margin: 0;'>有</p>";
//	}else{
//		return "<p style='margin: 0;color:red'>无</p>";
//	}			
//}
//},{
//data : 'version',
//defaultContent:"",
//// title: "版本",
//render:function(data, type, row, meta){
//	return "<div>"+data+"</div>";
//}
//},{
//data : 'dateTime',
//defaultContent:""
//	// title: "时间",
////render:function(data, type, row, meta){
////	return new Date(parseInt(Date.parse(data))).toLocaleString().replace(/[\u4e00-\u9fa5]/g, " ").replace(new RegExp('/','g'),"-");
////}
//},{
//data : 'isClose',
//defaultContent:"",
//// title: "时间",
//render:function(data, type, row, meta){
//	var html= '';
//	if(data == 1){
//		html += '<div style="color:green">关闭</div>'
//	}else{
//		html += '<div style="color:red">未关闭</div>'
//	}
//	return html;
//}
//},{
//data : null,
//// title: "操作",
//render:function(data, type, row, meta){
//	var html ='<a href="javascript:void(0);" title="编辑" class="_edit btn btn-info" > <span class="glyphicon glyphicon-edit"></span></a>'
////	html +='<a class="btn btn-danger" onclick="del(&quot;'+ row.id +'&quot;)" type="button" href="#" ><span class="glyphicon glyphicon-trash"></span></a>'
//		html +='<a href="javascript:void(0);" class="_del btn btn-danger"><span class="glyphicon glyphicon-trash"></span></a>'
//			return html;
//}
//}];
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