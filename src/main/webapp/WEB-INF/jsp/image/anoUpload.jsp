<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:directive.include file="../taglib.jsp" />
<!DOCTYPE html>
<style>
#upload ul li span {
	background:
		url('<spring:message code="staticPath"></spring:message>/icons.png')
		no-repeat !important;
}

#drop {
	border-image:
		url('<spring:message code="staticPath"></spring:message>/border-image.png')
		25 repeat !important;
}

#result-area {
	margin-top: 10px;
}

.modal-body {
	height: 240px;
}
</style>


<jsp:include page="../top.jsp"></jsp:include>
<div class="container">
	<c:if test="${info!=null}">
		<div class="alert alert-success info">
			<button type="button" class="close" data-dismiss="alert"
				aria-hidden="true">&times;</button>
			${info}
		</div>
	</c:if>
	<form id="upload" method="post"
		action="${pageContext.request.contextPath}/api/upload"
		enctype="multipart/form-data">
		<div id="drop">
			<h3>拖动图片到这里或者</h3>
			<a class="btn">选择图片</a>&nbsp; <input type="file" name="image"
				multiple />
		</div>
		<button id="clear" type="button" class="btn btn-sm btn-primary">清空上传列表</button>
		&nbsp;
		<button id="html" type="button" class="btn btn-sm btn-primary"
			data-toggle="modal" data-target="#htmlSelect">获取代码</button>
		<ul id="fileList">
		</ul>
	</form>
	<div id="message">
		<h4>更新日志</h4>
		<ul>
			<li>2014-07-21: 非注册用户每天只允许上传10张图片, 请登陆后再上传, 如有疑问请发送邮件至页面底部的邮箱 </li>
			<li>2014-07-12: 增加获取图片html代码的按钮</li>
			<li>2014-07-11: 完善登陆状态的保存</li>
			<li>2014-07-02: 增加文件名搜索功能</li>
			<li>2014-06-14: 图片使用域名"1.loli.io", 其余使用"2.loli.io"</li>
			<li>2014-06-13: 登陆后可以查看并删除已上传的图片</li>
			<!-- <li>2014-06-13: 保留原文件的扩展名(不再是统一的png)</li> -->
			<!-- <li>2014-06-13: 增加非图片类文件的上传(文件外链), 见页面底部</li> -->
		</ul>
	</div>
</div>

<div class="modal fade" id="htmlSelect">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">获取代码</h4>
			</div>
			<div class="modal-body">
				<div class="btn-group" data-toggle="buttons">
					<label class="btn btn-primary" id="url-btn"> <input
						type="radio" name="options">URL
					</label> <label class="btn btn-primary" id="html-btn"> <input
						type="radio" name="options">HTML
					</label> <label class="btn btn-primary" id="img-btn"> <input
						type="radio" name="options">[img]
					</label>
				</div>
				<textarea class="form-control col-md-6" rows="8" id="result-area">
				</textarea>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script>
    function getPaths() {
        var result = new Array();
        $(".path").each(function(i, e) {
            var h = $(this).text();
            if (h.indexOf("loli.io") >= 0) {
                result.push(h);
            }
        });

        return result;
    }

    function getCode(prefix, suffix, array) {
        var html = "";
        for (var i = 0; i < array.length; i++) {
            var line = prefix;
            line += array[i];
            line += suffix;
            html += line;
        }
        return html;
    }
    $(document).ready(function() {
        $("#url-btn").click(function() {
            $("#result-area").html("");
            var result = getPaths();
            var html = getCode("", "\n", result);
            $("#result-area").html(html);
        });
        $("#html-btn").click(function() {
            $("#result-area").html("");
            var result = getPaths();
            var html = getCode("<img src='", "'>\n", result);
            $("#result-area").html(html);
        });
        $("#img-btn").click(function() {
            $("#result-area").html("");
            var result = getPaths();
            var html = getCode("[img]", "[/img]\n", result);
            $("#result-area").html(html);
        });
		
		$('#html').on('click', function(){
			$("#result-area").html("");
            var result = getPaths();
            var html = getCode("", "\n", result);
            $("#result-area").html(html);
			$('.modal .btn-group label').eq(0).addClass('active');
		});
		
    });
</script>

<script
	src="<spring:message code="staticPath"></spring:message>/jquery.knob.js"></script>
<script
	src="<spring:message code="staticPath"></spring:message>/jquery.ui.widget.js"></script>
<script
	src="<spring:message code="staticPath"></spring:message>/jquery.iframe-transport.js"></script>
<script
	src="<spring:message code="staticPath"></spring:message>/jquery.fileupload.js"></script>
<script
	src="${pageContext.request.contextPath}/static/ext/uploader/script.js"></script>
<input type="hidden" id="redirectPath"
	value="<spring:message code="redirectPath"></spring:message>">
