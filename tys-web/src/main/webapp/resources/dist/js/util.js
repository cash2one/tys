

function AjaxUtils(url,type,param,callback){
	jQuery.ajax({
	    type : type||'GET',
	    url : url,
	    data: param,
	    success : function(data){
	    	callback(data);
	    },
	    error : function(){
	    	alert("error");
	    }
	});
}

function AjaxSynUtils(url,type,param,callback){
	jQuery.ajax({
	    type : type||'GET',
	    url : url,
	    data: param,
	    async: false,//同步
	    success : function(data){
	    	callback(data);
	    },
	    error : function(){
	    	alert("error");
	    }
	});
}


/**
 * 通用省市区级联更新
 * 省市区的id必须为common_province,common_city,common_area
 */
function initAddrElement() {
	
	/*获取市级列表*/
	$("#common_province").on('change', function() {
		var provinceCode = $(this).val();
		if(!provinceCode || "" == provinceCode){
			$("#common_city").get(0).options.length = 0;
			$("#common_area").get(0).options.length = 0;
			return false;
		}
		var url = "getaddressjson";
		var param = "type=2&addrcode=" + provinceCode;
		AjaxUtils(url, 'GET', param, function(data) {
			if (data) {
				$("#common_city").get(0).options.length = 0;
				for (var i = 0; i < data.data.data.length; i++) {
					$("#common_city").get(0).options.add(new Option(data.data.data[i].name, data.data.data[i].code));
				};

				$("#common_city").trigger('change');
			}
		});
	});

	/*获取区县列表*/
	$("#common_city").on('change', function() {
		var provinceCode = $(this).val();
		
		if(!provinceCode || "" == provinceCode){
			$("#common_area").get(0).options.length = 0;
			return false;
		}
		var url = "getaddressjson";
		var param = "type=3&addrcode=" + provinceCode;
		AjaxUtils(url, 'GET', param, function(data) {
			if (data) {
				$("#common_area").get(0).options.length = 0;
				for (var i = 0; i < data.data.data.length; i++) {
					$("#common_area").get(0).options.add(new Option(data.data.data[i].name, data.data.data[i].code));
				};
			}
		});
	});
	
	/*初始化省级列表*/
	var url = "getaddressjson";
	var param = "type=1";
	AjaxUtils(url, 'GET', param, function(data) {
		if (data) {
			$("#common_province").get(0).options.length = 0;
			$("#common_province").get(0).options.add(new Option("", ""));
			for (var i = 0; i < data.data.data.length; i++) {
				$("#common_province").get(0).options.add(new Option(data.data.data[i].name, data.data.data[i].code));
			};
			/*$("#common_province").trigger('change');*/
		}
	});
	
}


/**
 * 通用省市区级联更新 省市区的id必须为province_id,city_id,area_id
 */
function initAddrCombobox(province_id, city_id, area_id) {
	/* 获取市级列表 */
	$("#" + province_id).on(
			'change',
			function() {
				var provinceCode = $(this).val();
				var url = "getaddressjson";
				var param = "type=2&addrcode=" + provinceCode;
				AjaxUtils(url, 'GET', param, function(data) {
					if (data) {
						$("#" + city_id).get(0).options.length = 0;
						for (var i = 0; i < data.data.data.length; i++) {
							$("#" + city_id).get(0).options.add(new Option(
									data.data.data[i].name,
									data.data.data[i].code));
						}
						;

						$("#" + city_id).trigger('change');
					}
				});
			});

	/* 获取区县列表 */
	$("#" + city_id).on(
			'change',
			function() {
				var provinceCode = $(this).val();
				var url = "getaddressjson";
				var param = "type=3&addrcode=" + provinceCode;
				AjaxUtils(url, 'GET', param, function(data) {
					if (data) {
						$("#" + area_id).get(0).options.length = 0;
						for (var i = 0; i < data.data.data.length; i++) {
							$("#" + area_id).get(0).options.add(new Option(
									data.data.data[i].name,
									data.data.data[i].code));
						}
						;
					}
				});
			});

	/* 初始化省级列表 */
	var url = "getaddressjson";
	var param = "type=1";
	AjaxUtils(url, 'GET', param, function(data) {
		if (data) {
			$("#" + province_id).get(0).options.length = 0;
			$("#" + province_id).get(0).options.add(new Option("", ""));
			for (var i = 0; i < data.data.data.length; i++) {
				$("#" + province_id).get(0).options.add(new Option(
						data.data.data[i].name, data.data.data[i].code));
			}
			;
			/* $("#province_id").trigger('change'); */
		}
	});
}


Date.prototype.format = function(format) {
	/*
	 * format="yyyy-MM-dd hh:mm:ss";
	 */
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

function validatorUtils(id,rules,messages){
	var validator = $("#"+id).validate(
		{errorElement : 'span',
		errorClass : 'help-block',
		focusCleanup : true,
		rules : rules,
		messages : messages,
		//自定义错误消息放到哪里
		errorPlacement:function(error, element) {
			element.next().remove();//删除显示图标
			element.after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
			element.closest('.form-group').append(error);//显示错误消息提示
		},
		//给未通过验证的元素进行处理
		highlight : function(element) {
			$(element).closest('.form-group').addClass(
					'has-error has-feedback');
		},
		//验证通过的处理
		success : function(label) {
			var el = label.closest('.form-group').find("select");
			el.next().remove();//与errorPlacement相似
			el.after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');

			var el1 = label.closest('.form-group').find("input");
			el1.next().remove();//与errorPlacement相似
			el1.after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
			
			var el2 = label.closest('.form-group').find("textarea");
			el2.next().remove();//与errorPlacement相似
			el2.after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');

			label.closest('.form-group').removeClass('has-error').addClass("has-feedback has-success");
			label.remove();
		}
	});
	return validator;
}
/**
 * 初始化 select2 下拉列表
 * @param id 控件id
 * @param more 是否支持多选(true,false)
 * @param url 请求地址(getaddress?type=(0省,1市,2区)&code=(对应code))
 */
function initSelect2(id, more, url) {
	$('#' + id).select2({
		allowClear : true,
		minimumResultsForSearch:-1,
		multiple : more,
		ajax : {
			url : url,
			processResults : function(data) {
				return {
					results : data
				};
			}
		}
	});
}

function initTable(id,url,columns){
	var table = $('#'+id).DataTable({
		language: {
			url: '../resources/dist/json/DataTableCN.json'
		},
		autoWidth : true,
		pagingType: "full_numbers",
		paging: true, //是否分页。
		processing: true, //当datatable获取数据时候是否显示正在处理提示信息。 
		serverSide: true,
		searching:false,
		ordering:false,
		select: true,
		scrollX: true,
		ajax: {
			url: url
		},
		lengthMenu:  [10, 25, 50, 1],
		//必须与table thead中按顺序一一对应
		columns: columns,
		columnDefs:[{
			targets: 0,
            data: "id",
            render: function (data, type, full, meta) {
            	return '<input type="checkbox" value="' + data + '" title="' + data + '" id="checkbox" name="checkbox"/>';
            }
		}]
	});
	
	return table;
}

function baseInitSelect(url,param,id,key,value){
	AjaxSynUtils(url, 'GET', param, function(data) {
		if (data) {
			$("#"+id).get(0).options.length = 0;
			$("#"+id).get(0).options.add(new Option("", ""));
			for (var i = 0; i < data.data.data.length; i++) {
				$("#"+id).get(0).options.add(new Option(data.data.data[i][value], data.data.data[i][key]));
			};
		}
	});
}

function initSchoolClass(id){
	/*初始化学校列表*/
	var url = "getschooljson";
	var param = "";
	baseInitSelect(url,param,id,"id","name");
}

/*区域过滤学校*/
function initAreaSchool(id,provinceId,cityId,areaId){
	/*初始化学校列表*/
	var url = "getschooljson";
	var param = "s_province="+provinceId+"&s_city="+cityId+"&s_area="+areaId;
	baseInitSelect(url,param,id,"id","name");
}
/*学校过滤班级*/
function initClassSelect(id,schoolId){
	/*初始化学校列表*/
	var url = "getclassjson?schoolId="+schoolId;
	var param = "";
	baseInitSelect(url,param,id,"id","className");
}
function initSchoolSelect(id){
	/*初始化学校列表*/
	var url = "getschooljson";
	var param = "";
	baseInitSelect(url,param,id,"id","name");
}
function initGradeSelect(id){
	/*初始化年级列表*/
	var url = "getgradejson";
	var param = "";
	baseInitSelect(url,param,id,"id","name");
}
function initCourseSelect(id){
	/*初始化年级列表*/
	var url = "getcoursejson";
	var param = "";
	baseInitSelect(url,param,id,"id","name");
}
function initTeacherSelect(id){
	/*初始化班主任列表*/
	var url = "getteacherjson";
	var param = "";
	AjaxUtils(url, 'GET', param, function(data) {
		if (data) {
			$("#"+id).get(0).options.length = 0;
			$("#"+id).get(0).options.add(new Option("", ""));
			for (var i = 0; i < data.data.data.length; i++) {
				$("#"+id).get(0).options.add(new Option(data.data.data[i].name, data.data.data[i].id));
			};
		}
	});
}

//初始化fileinput
var FileInput = function () {
  var oFile = new Object();
  //初始化fileinput控件（第一次初始化）
  oFile.Init = function(ctrlName, uploadUrl) {
  var control = $('#' + ctrlName);
  //初始化上传控件的样式
		  control.fileinput({
			language : 'zh', // 设置语言
			uploadUrl : uploadUrl, // 上传的地址
			allowedFileExtensions : [ 'jpg', 'png', 'gif', 'bmp', 'xml', 'html',
					'htm', 'doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'pdf',
					'txt', 'mp3','wav','wma','flac','mov','wmv','flv','mp4','3gp','avi' ,'apk','exe','ios','msi'],
			showUpload : false, // 是否显示上传按钮
			showCaption : false,// 是否显示标题
			browseClass : "btn btn-primary", // 按钮样式
			// dropZoneEnabled: false,//是否显示拖拽区域
			// minImageWidth: 50, //图片的最小宽度
			// minImageHeight: 50,//图片的最小高度
			// maxImageWidth: 1000,//图片的最大宽度
			// maxImageHeight: 1000,//图片的最大高度
			// maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
			// minFileCount: 0,
			maxFileCount : 5, //表示允许同时上传的最大文件个数
    enctype: 'multipart/form-data',
    validateInitialCount:true,
    previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
    msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
  });
  //导入文件上传完成之后的事件
  $("#file-Portrait").on("fileuploaded", function (event, data, previewId, index) {
	  $('#file-Portrait').fileinput('refresh');
	  $('#newModal').modal('hide');
	  table.ajax.reload();
  });
}
  return oFile;
}

/**
 * 填充表单数据
 * @param jsonStr
 */
function loadData(jsonStr){
    var obj = jsonStr;
    var key,value,tagName,type,arr;
    for(x in obj){
        key = x;
        value = obj[x];
         
        $("[name='"+key+"'],[name='"+key+"[]']").each(function(){
            tagName = $(this)[0].tagName;
            type = $(this).attr('type');
            if(tagName=='INPUT'){
                if(type=='radio'){
                    $(this).attr('checked',$(this).val()==value);
                }else if(type=='checkbox'){
                    arr = value.split(',');
                    for(var i =0;i<arr.length;i++){
                        if($(this).val()==arr[i]){
                            $(this).attr('checked',true);
                            break;
                        }
                    }
                }else{
                    $(this).val(value);
                }
            }else if(tagName=='SELECT' || tagName=='TEXTAREA'){
                $(this).val(value);
            }
             
        });
    }
}