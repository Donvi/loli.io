<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:directive.include file="../taglib.jsp" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录-萝莉图床</title>


<jsp:include page="../static.jsp"></jsp:include>
<!-- <script src="${pageContext.request.contextPath}/static/js/https.js"></script> -->

<script src="${pageContext.request.contextPath}/static/js/login.js"></script>
<script src="${pageContext.request.contextPath}/static/js/md5.js"></script>

<style type="text/css">
</style>
</head>
<body>
  <jsp:include page="../top.jsp"></jsp:include>

  <div id="main">
    <div class="container">
      <c:if test="${info!=null}">
        <div class="alert alert-success info">
          <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
          ${info}
        </div>
      </c:if>
      <div class="login-form">
        <h2>登录</h2>
        <form class="form-horizontal" action="${pageContext.request.contextPath}/user/login" role="form" method="POST"
          onsubmit="return md5password();">
          <div class="form-group">
            <label for="user-email" class="col-sm-4 control-label">邮箱</label>
            <div class="col-sm-4">
              <input type="email" name="email" class="form-control" id="user-email" value="${user.email}"
                placeholder="E-mail">
            </div>
            <span class="label label-danger" id="email-error">${message["email"]}</span>
          </div>
          <div class="form-group">
            <label for="user-password" class="col-sm-4 control-label">密码</label>
            <div class="col-sm-4">
              <input type="password" class="form-control" id="user-password" placeholder="Password">
            </div>
            <span class="label label-danger" id="password_error"></span>
          </div>
          <!-- <div class="form-group">
            <label class="col-sm-4 control-label"></label>
            <div class="col-sm-4">
              <label><input type="checkbox" name="_spring_security_remember_me">记住我</label>
            </div>
            <span class="label label-danger" id="password_error"></span>
          </div> -->
          <div class="form-group">
            <div class="col-sm-offset-4 col-sm-6">
              <button type="submit" class="btn btn-primary">登录</button>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-4 control-label">社交账号</label>
            <div class="col-sm-offset-4 col-sm-6">
              <a href="${pageContext.request.contextPath}/social/weibo/redirect"><img
                src="${pageContext.request.contextPath}/static/img/weibo-btn.png"></a> <a
                href="${pageContext.request.contextPath}/social/github/redirect"><img
                src="${pageContext.request.contextPath}/static/img/github-btn.png"></a>
            </div>
          </div>
          <input type="hidden" id="password_md5" name="password"> <input type="hidden"
            name="${_csrf.parameterName}" value="${_csrf.token}" />
          <%-- <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
            <font color="red"> Your login attempt was not successful due to <br />
            <br /> <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.
            </font>
          </c:if>
 --%>
        </form>
      </div>
    </div>
  </div>
  <jsp:include page="../bottom.jsp"></jsp:include>
</body>
</html>