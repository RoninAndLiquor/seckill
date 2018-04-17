<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>列表</title>
	<jsp:include page="base.jsp"></jsp:include>
  </head>
  <body>
   <div class="container">
		<div class="panel panel-default">
			<div class="panel-heading text-center">
				<h2>列表</h2>
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>名称</th>
							<th>库存</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>创建时间</th>
							<th>详情页</th>
						</tr>
					</thead>
					<tbody id="body">
						
					</tbody>
				</table>
			</div>
		</div>
	</div>
  </body>
  <script type="text/javascript">
  	window.onload = init;
  	function init(){
  		listData();
  	}
  	function listData(){
  		$.ajax({
  			url:"listData.json",
  			type:"POST",
  			dataType:"json",
  			async:false,
  			success:function(data){
  				var str = "";
  				for(var i=0;i<data.length;i++){
  					str += "<tr><td>"+data[i].name+"</td>"
  					+"<td>"+data[i].number+"</td>"
  					+"<td>"+formatDate(data[i].startTime)+"</td>"
  					+"<td>"+formatDate(data[i].endTime)+"</td>"
  					+"<td>"+formatDate(data[i].createTime)+"</td>"
  					+"<td><a class='btn btn-info' href='/seckill/seckill/"+data[i].seckillId+"/detail'>详情</a></td></tr>";
  				}
  				$("#body").html(str);
  			}
  		});
  	}
  	function formatDate(param){
  		var date = new Date(param);
  		return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
  	}
  </script>
</html>