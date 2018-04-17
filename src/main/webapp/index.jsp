<html>
<body>
<h2>Hello World!</h2>
</body>
<script>
	$(function(){
		var roomtype=$("#sd").text();
		alert(roomtype);
		var data = 1;
		alert(eval(data));
		$.ajax({
			url:"/Redhotel/roomtype/findroomcount.action",
			data: {roomtype: roomtype},
			type:"post",
			dataType:"json",
			success: function (data) { 
			    var str=eval(data);
			    alert(str);
			    $("sycount").val(str);
			}
		});
	});
</script>
</html>
