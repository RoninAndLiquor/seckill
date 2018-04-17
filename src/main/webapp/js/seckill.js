var seckill = {
	//封装秒杀相关的AJAX的URL
	URL:{
		now : function(){
			return '/seckill/seckill/time/now';
		},
		exposer:function(seckillId){
			return "/seckill/seckill/"+seckillId+"/exposer";
		},
		execution:function(seckillId,md5){
			return "/seckill/seckill/"+seckillId+"/"+md5+"/execute";
		}
	},
	handlerSeckill:function(seckillId,seckillBox){
		seckillBox.hide().html("<button class='btn btn-primary btn-lg' id='killBtn'>开始秒杀</button>");
		$.post(seckill.URL.exposer(seckillId),{},function(result){
			//在回调函数中，执行交互流程
			if(result && result['success']){
				var exposer = result['data'];
				if(exposer['exposed']){
					var md5 = exposer['md5'];
					var killUrl = seckill.URL.execution(seckillId, md5);
					console.log("killUrl="+killUrl);
					//绑定一次点击事件
					$("#killBtn").one("click",function(){
						//执行秒杀请求
						//1.禁用按钮
						$(this).addClass('disabled');
						//2.发送秒杀请求
						$.post(killUrl,{},function(result){
							var s = result;
							if(result && result['success']){
								var killResult = result['data'];
								var state = killResult['state'];
								var stateInfo = killResult['stateInfo'];
								seckillBox.html("<span class='label label-success'>"+stateInfo+"</span>");
							}
						});
					});
					seckillBox.show();
				}else{
					//未开启秒杀
					var now = exposer['now'];
					var start = exposer['startTime'];
					var end = exposer['endTime'];
					//重新进入时间判断逻辑
					//seckill.countDown(seckillId, now, start, end);
				}
			}else{
				console.log("result="+result);
			}
		});
	},
	validatePhone:function(phone){
		var sg = /^1\d{10}$/;
		if(sg.test(phone)){
			return true;
		}
		return false;
	},
	countDown:function(seckillId,nowTime,startTime,endTime){
		var startTime = new Date(startTime);
		var startTimeMill = startTime.getTime();
		var seckillBox = $("#seckill-box");
		//时间的判断
		if(nowTime>endTime){
			seckillBox.html('秒杀结束');
		}else if(nowTime<startTimeMill){
			/*var killStartDate = new Date();
			alert(killStartDate);
			killStartDate.setTime(startTimeMill+1000);
			alert(killStartDate.toString());*/
			seckillBox.countdown(startTime,function(event){
				var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
				seckillBox.html(format);
				//时间完成后回调函数
			}).on('finish.countdown',function(){
				//获取秒杀地址，控制实现逻辑，执行秒杀
				seckill.handlerSeckill(seckillId,seckillBox);
			});
		}else{
			seckill.handlerSeckill(seckillId,seckillBox);
		}
	},
	//详情页秒杀逻辑
	detail:{
		//详情页初始化
		init:function(params){
			
			//手机验证，计时交互
			//规划我们的交互流程
			var killPhone = $.cookie('killPhone');
			//验证手机号
			if(!seckill.validatePhone(killPhone)){
				//绑定Phone
				//控制输出
				var killPhoneModal = $("#killPhoneModal");
				killPhoneModal.modal({
					//显示弹出层
					show:true,
					backdrop:'static',//禁止位置关闭
					keyboard:false //关闭键盘事件
				});
				$("#killPhoneBtn").click(function(){
					var inputPhone = $("#killPhoneKey").val();
					if(seckill.validatePhone(inputPhone)){
						//电话填入cookie
						$.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
						//页面重载
						window.location.reload();
					}else{
						$("#killPhoneMessage").hide().html("<label class='label label-danger'>手机号错误</label>").show(300);
					}
				});	
			}
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			var seckillId= params['seckillId'];
			$.get(seckill.URL.now(),{},function(result){
				if(result && result['success']){
					var nowTime = result['data'];
					seckill.countDown(seckillId,nowTime,startTime,endTime);
				}else{
					console.log('result:'+result);
				}
			});
		}
	}	
}
