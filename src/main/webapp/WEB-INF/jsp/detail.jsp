<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>详情页</title>
<jsp:include page="base.jsp"></jsp:include>
<style>
	th{
		text-align:center;
	}
	td{
		text-align:center;
	}
</style>
</head>
<body>
	<div class="container">
		<div class="panel panel-default text-center">
			<div class="panel-heading"><h2>${seckill.name }</h2></div>
			<div class="panel-body">
				<h2 class="text-danger">
					<span class="glyphicon glyphicon-time"></span>
					<span class="glyphicon" id="seckill-box"></span>
				</h2>
				<%-- <table class="table table-hover">
					<thead>
						<tr>
							<th>名称</th>
							<th>库存</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>创建时间</th>
						</tr>
					</thead>
					<tbody id="body">
						<tr>
							<td>${seckill.name }</td>
							<td>${seckill.number }</td>
							<td>
								<fmt:formatDate value="${seckill.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<fmt:formatDate value="${seckill.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<fmt:formatDate value="${seckill.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
						</tr>
					</tbody>
				</table> --%>
			</div>
		</div>
		<p style="text-align:center"><a class="btn btn-warning" href="#" onclick="javascript:history.back(-1)">返回上一页</a></p>
	</div>
	<div id="killPhoneModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title text-center">
						<span class="glyphicon glyphicon-phone"></span>
					</h3>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2" >
							<input type="text" name="killPhone" id="killPhoneKey" placeholder="填写手机号" class="form-control"/>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<span id="killPhoneMessage" class="glyphicon"></span>
					<button type="button" id="killPhoneBtn" class="btn btn-success">
						<span class="glyphicon glyphicon-phone"></span>
						Submit
					</button>
				</div>
			</div>
		</div>
	</div>
	
	<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
	<script src="/seckill/js/seckill.js"></script>
	<script type="text/javascript">
		$(function(){
			seckill.detail.init({
				seckillId:"${seckill.seckillId}",
				startTime:"${seckill.startTime}",
				endTime:"${seckill.endTime.time}"
			});
		});
	</script>
</body>
</html>