<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:directive.include file="../taglib.jsp" />

<html>
<head>
<title>审核图片</title>
<style>
img {
    max-width: 300px;
    max-height: 300px;
}

.img {
    float:left;
}
</style>
</head>
<body>
    <c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
    <c:forEach items="${list}" var="img">
        <div class="img">
            <a href="${ctx}/approve/type?id=${img.id}&type=1&admin">正常</a>
            <a href="${ctx}/approve/delete?id=${img.id}">删除</a> 上传
            ${img.user.id}, ${img.date}<br>
            <img
                src="<spring:message code="redirectPath"></spring:message>${img.generatedName}">

        </div>
    </c:forEach>
</body>

</html>