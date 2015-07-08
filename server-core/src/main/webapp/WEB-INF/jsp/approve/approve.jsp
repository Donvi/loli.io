<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:directive.include file="../taglib.jsp" />

<html>
<head>
<title>审核图片</title>
</head>
<body>
    <c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
    <c:if test="${empty img}">
已经没有图片了!
</c:if>
    <c:if test="${not empty img}">
        <img
            src="<spring:message code="redirectPath"></spring:message>${img.generatedName}">

        <p>
            <a href="${ctx}/approve/type?id=${img.id}&type=1">普通图片</a> <a
                href="${ctx}/approve/type?id=${img.id}&type=2">二次元河蟹图</a>
            <a href="${ctx}/approve/type?id=${img.id}&type=3">成人图片</a> <a
                href="${ctx}/approve/type?id=${img.id}&type=4">其他违法</a>
        </p>
    </c:if>

</body>
</html>