<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:directive.include file="../taglib.jsp" />
<head>
<title>最近30天统计-萝莉图床</title>
<link href="${pageContext.request.contextPath}/static/ext/uploader/style.css" rel="stylesheet" />
<jsp:include page="../meta.jsp"></jsp:include>
<jsp:include page="../static.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/highcharts.js"></script>
<script>
    $(document).ready(function() {
        $.post("${pageContext.request.contextPath}/stat/image", function(result) {
            var cat = new Array();
            var data = new Array();
            for (i = 0; i < result.length; i++) {
                cat.push(result[i].days.substring(4));
                data.push(result[i].total);
            }
            $('#container').highcharts({
                title : {
                    text : '图片数量',
                },
                xAxis : {
                    categories : cat
                },
                yAxis : {
                    title : {
                        text : '图片数量统计'
                    },
                    min : 0,
                },
                legend : {
                    layout : 'vertical',
                    align : 'right',
                    verticalAlign : 'middle',
                    borderWidth : 0
                },
                series : [ {
                    name : '上传图片数量',
                    data : data
                } ]
            });

        });

    });
</script>
</head>
<body>
  <jsp:include page="../top.jsp"></jsp:include>
  <div class="container">
    <div class="data">
      <h3>最近30天图片上传量</h3>
      <div id="container"></div>
    </div>

  </div>
  <jsp:include page="../bottom.jsp"></jsp:include>
</body>
</html>