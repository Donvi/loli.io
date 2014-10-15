<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:directive.include file="taglib.jsp" />
<head>
<title>萝莉图床</title>
<link href="${pageContext.request.contextPath}/static/ext/uploader/style.css" rel="stylesheet" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<META content="图床,免费图床,屏幕截图,萝莉图床" name="keywords" />
<meta name="description" content="一个好用的免费图床" />

<jsp:include page="static.jsp"></jsp:include>
</head>
<body>
  <jsp:include page="image/anoUpload.jsp"></jsp:include>
  <jsp:include page="bottom.jsp"></jsp:include>
</body>
</html>