<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    var APP_CONFIG = {
        apiBase: '${pageContext.request.contextPath}'
    };
    console.log("API Base Path:", APP_CONFIG.apiBase);
</script>