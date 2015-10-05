<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="../taglib.jsp" />
<div class="container">
  <c:if test="${info!=null}">
    <div class="alert alert-success info">
      <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
      ${info}
    </div>
  </c:if>

  <form id="upload" method="post" action="${pageContext.request.contextPath}/api/upload" enctype="multipart/form-data">
    <div id="drop"
      style="border-image: url('${pageContext.request.contextPath}/static/ext/uploader/border-image.png') 25 repeat !important;">
      <h3>拖动图片到这里或者</h3>
      <a class="btn btn-primary">选择图片</a>&nbsp; <input type="file" name="image" multiple />
    </div>
    &nbsp;
    <%-- <security:authorize access="isAuthenticated()">
      <button id="url" type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#urlFetch">url下载</button>
      <button id="html" type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#htmlSelect">获取链接</button>
    </security:authorize> --%>
    <c:if test="${sessionScope.user ne null}">
      <button id="url" type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#urlFetch">url下载</button>
      <button id="html" type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#htmlSelect">获取链接</button>
    </c:if>
    <ul id="fileList">
    </ul>
  </form>
  <div id="message">
  禁止上传三次元和谐图, 发现即删
  <!-- 
<span onclick="$('#ad1').toggle()">点击显示广告</span>
<br/>
<div id="ad1" style="display:none">
<a target="_blank" href="http://s.click.taobao.com/t?e=m%3D2%26s%3DiwFh6wIMySocQipKwQzePCperVdZeJviEViQ0P1Vf2kguMN8XjClAh3%2Fu8srW1SrLtEBlWvgMl4Ih4uhQWq3s7KAwxwteJmVwgh1ZDCa%2FEtp3Md9FsDQ7TDVuRn8ddiDsEVVC24eqozcHtRpEUy6RHVyxRO0gvF4QxJtmCgOmCLXl8Q7TEjBF%2BX11FSyvDCnQiv%2BJKjlPObGDmntuH4VtA%3D%3D">阿里云服务器</a><br/>
<a target="_blank" href="http://s.click.taobao.com/t?e=m%3D2%26s%3DBcOJGAMR6TIcQipKwQzePCperVdZeJviQih%2F7PxfOKS5VBFTL4hn2ZucfspN1emONMWYSf9G9hFJFNRdCHLms7kQN36CBUrATN02naDp%2FDydMBl3rtAdqYUXUqNpxWgfRIhXpwzXCM5Pf2BYFhAHdIysz9fDfhlinaYpFBIfC%2F2X26zqzz%2BB878WLl0Zspj2ZpWFiLpECNZkN0GiobiE1A2nm3%2FuorU57%2FH5sYsEelZtttBhO%2BYITVdRZF2jHoJEnQh24%2FJtJ%2BoTJefgHOmmlNtflAvjM8abze0VhgltO%2BCKFuWNrXFz2OyEPpK3NC5IO0XSNx0aiQf%2BVJHOp9nB5FT2UT8IaK0gX%2Bn4cTaP6N4oQ7jEcsqIRL1e5v7SBuLb6vRasSRRt1yVVe8AFAG7Df%2B4MLnMmUkApXCQL1NAJwlqoZ1G9c5k4CbrTpYdEPLbwzXyg2ChG7Tihki54yJReFLQZWyCs%2FzNOeU%2FnFL59PL586cEnrXYwx3He9F8Mg9ZIYULNg46oBA%3D">舰娘周边</a>
<br/>
<a target="_blank" href="http://s.click.taobao.com/t?e=m%3D2%26s%3DR94942WKtcwcQipKwQzePCperVdZeJviLKpWJ%2Bin0XJRAdhuF14FMak4WT3U%2BZCMRitN3%2FurF3ygoD%2B6rvgpap6jU6cJqOrX530Kzx%2Fjj%2FJqa%2Bjle0uCA13HdI9gRVOxnynZ4bvuOIT7d6VSwIt3C90OMSgyreEIcnjQKEJbkmpQDwcz2G5csFaEkEjWuZ1mxzVx0V54fkzFNGwSJBXnc1YOKPQtLPuUAthOEYOua8tPnvuA191OCBuKzV78Y%2FFY1dL4vabr5mQhhQs2DjqgEA%3D%3D">动漫抱枕</a>
<br/>
</div>
 -->
  </div>
</div>

<div class="modal fade" id="htmlSelect">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">
          <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title">批量获取</h4>
      </div>
      <div class="modal-body">
        <div class="btn-group" data-toggle="buttons">
          <label class="btn btn-primary" id="url-btn"> <input type="radio" name="options">URL
          </label> <label class="btn btn-primary" id="html-btn"> <input type="radio" name="options">HTML
          </label> <label class="btn btn-primary" id="img-btn"> <input type="radio" name="options">[img]
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


<div class="modal fade" id="urlFetch">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">
          <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title">请输入图片url(一行一个), 如果没反应，请耐心等待20秒</h4>
      </div>
      <div class="modal-body">

        <textarea class="form-control col-md-6" rows="8" id="upload-area"></textarea>
        <button type="button" id="fetch-confirm" class="btn btn-primary">确认</button>
        <textarea class="form-control col-md-6" rows="8" id="fetch-result-area"></textarea>
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
            if (h.length >= 0) {
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

        $('#html').on('click', function() {
            $("#result-area").html("");
            var result = getPaths();
            var html = getCode("", "\n", result);
            $("#result-area").html(html);
            $('.modal .btn-group label').eq(0).click();
        });

        var lock = true;
        $("#fetch-confirm").click(function() {
            var urls = new Array();
            var urlStr = document.getElementById("upload-area").value.replace(/^\s*[\r\n]/gm, "");
            var urlArray = urlStr.split("\n");
            for (i = 0; i < urlArray.length; i++) {
                if (urlArray[i].length < 3) {
                    break;
                }
                $.post("${pageContext.request.contextPath}/api/fetch", {
                    path : urlArray[i]
                }, function(result) {
                    if (result && result.error != "") {
                        alert(result.origin + "获取失败:" + result.error);
                    } else {
                        if (result && result.origin != "") {
                            while (!lock) {
                            }
                            var newValue = $("#upload-area").val();
                            newValue = newValue.replace(result.origin, "");
                            $("#upload-area").val(newValue);
                            lock = true;
                            $("#fetch-result-area").append($("#redirectPath").val() + result.redirect + "\n");
                        }
                    }
                }, "json");
            }

        });

    });
</script>

<script src="${pageContext.request.contextPath}/static/ext/uploader/jquery.knob.min.js"></script>
<script src="${pageContext.request.contextPath}/static/ext/uploader/jquery.ui.widget.min.js"></script>
<script src="${pageContext.request.contextPath}/static/ext/uploader/jquery.iframe-transport.min.js"></script>
<script src="${pageContext.request.contextPath}/static/ext/uploader/jquery.fileupload.min.js"></script>
<script src="${pageContext.request.contextPath}/static/ext/uploader/script.js"></script>
<c:if test="${empty param.weibo}">
  <input type="hidden" id="redirectPath" value="<spring:message code="redirectPath"></spring:message>">
  <input type="hidden" id="httpsRedirectPath" value="<spring:message code="httpsRedirectPath"></spring:message>">
</c:if>