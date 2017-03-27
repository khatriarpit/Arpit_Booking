<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 21-03-2017
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:forEach  var="timeline" items="${list}">
    <c:choose>
        <c:when test="${timeline.key eq 'ticket'}" >
            Ticket Created: ${timeline.value.createdDate}
            <br/>
        </c:when>
        <c:when test="${timeline.key eq 'comment'}" >
            commented At: ${timeline.value.createdDate}
            comment: ${timeline.value.comment}
            comment: ${timeline.value.user.firstName}
            <br/>
        </c:when>
        <c:when test="${timeline.key eq 'status'}" >
             ${timeline.value.createdDate}
            <c:choose>
                <c:when test="${timeline.value.toStatus eq 1}" >
                    OPEN
                    <br/>
                </c:when>
                <c:when test="${timeline.value.toStatus eq 3}" >
                    RESOLVED
                    <br/>
                </c:when>
                <c:otherwise>
                    CLOSED
                    <br/>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>

</c:forEach>

<section class="timeline-section">

    <div class="container">
        <div class="page-header">
            <h1 id="timeline">Timeline</h1>
        </div>
        <ul class="timeline">
            <li>
                <div class="timeline-badge"></div>
                <div class="timeline-panel2">
                    <div class="timeline-heading">
                        <h4 class="timeline-title">Title</h4>
                        <p><small class="text-muted"> 11 hours ago via Twitter</small></p>
                    </div>
                    <div class="timeline-body">
                        <p>Mussum ipsum cacilds, vidis litro abertis.</p>
                    </div>
                </div>
                <div class="timeline-panel">
                    <div class="timeline-heading">
                        <h4 class="timeline-title">Discriptioin</h4>

                    </div>
                    <div class="timeline-body">
                        <p>Mussum ipsum cacilds, vidis litro abertis. Consetis adipiscings elitis. Pra lá , depois divoltis porris, paradis. Paisis, filhis, espiritis santis. Mé faiz elementum girarzis, nisi eros vermeio, in elementis mé pra quem é amistosis quis leo. Manduma pindureta quium dia nois paga. Sapien in monti palavris qui num significa nadis i pareci latim. Interessantiss quisso pudia ce receita de bolis, mais bolis eu num gostis.</p>
                    </div>
                </div>


            </li>
            <li>
                <div class="timeline-status"></div>
                <div class="timeline-panel">
                    <div class="timeline-heading">
                        <h4 class="timeline-title">Status</h4>

                    </div>
                    <div class="timeline-body">
                        <p>Mussum ipsum cacilds, vidis litro abertis. Consetis adipiscings elitis. Pra lá , depois divoltis porris, paradis. Paisis, filhis, espiritis santis.</p>
                    </div>
                </div>
            </li>
            <li class="timeline-inverted">
                <div class="timeline-badge warning"></div>
                <div class="timeline-panel23">
                    <div class="timeline-heading">
                        <h4 class="timeline-title">Title</h4>
                        <p><small class="text-muted"> 11 hours ago via fb</small></p>
                    </div>
                    <div class="timeline-body">
                        <p>Mussum ipsum cacilds, vidis litro abertis. Consetis adipiscings elitis. Pra lá , depois divoltis porris, paradis. Paisis, filhis, espiritis santis.</p>
                    </div>
                </div>
                <div class="timeline-panel">
                    <div class="timeline-heading">
                        <h4 class="timeline-title">Discriptioin</h4>
                    </div>
                    <div class="timeline-body">
                        <p>Mussum ipsum cacilds, vidis litro abertis. Consetis adipiscings elitis. Pra lá , depois divoltis porris, paradis. Paisis, filhis, espiritis santis. Mé faiz elementum girarzis, nisi eros vermeio, in elementis mé pra quem é amistosis quis leo. Manduma pindureta quium dia nois paga. Sapien in monti palavris qui num significa nadis i pareci latim. Interessantiss quisso pudia ce receita de bolis, mais bolis eu num gostis.</p>

                    </div>
                </div>

            </li>
            <li class="timeline-inverted">
                <div class="timeline-badge1 warning"></div>
                <div class="timeline-panel">
                    <div class="timeline-heading">
                        <h4 class="timeline-title">Status</h4>

                    </div>
                    <div class="timeline-body">
                        <p>Mussum ipsum cacilds, vidis litro abertis. Consetis adipiscings elitis. Pra lá , depois divoltis porris, paradis. Paisis, filhis, espiritis santis.</p>
                    </div>
                </div>
            </li>

            <li>
                <div class="timeline-badge info"></div>
                <div class="timeline-panel2">
                    <div class="timeline-heading">
                        <h4 class="timeline-title">Title</h4>
                        <p><small class="text-muted"> 11 hours ago via Twitter</small></p>
                    </div>
                    <div class="timeline-body">
                        <p>Mussum ipsum cacilds, vidis litro abertis.</p>
                    </div>
                </div>
                <div class="timeline-panel">
                    <div class="timeline-heading">
                        <h4 class="timeline-title">Discription</h4>
                    </div>
                    <div class="timeline-body">
                        <p>Mussum ipsum cacilds, vidis litro abertis. Consetis adipiscings elitis. Pra lá , depois divoltis porris, paradis. Paisis, filhis, espiritis santis. Mé faiz elementum girarzis, nisi eros vermeio, in elementis mé pra quem é amistosis quis leo. Manduma pindureta quium dia nois paga. Sapien in monti palavris qui num significa nadis i pareci latim. Interessantiss quisso pudia ce receita de bolis, mais bolis eu num gostis.</p>
                        <hr>

                    </div>
                </div>

            </li>
            <li>
                <div class="timeline-badge1 info"></div>
                <div class="timeline-panel">
                    <div class="timeline-heading">
                        <h4 class="timeline-title">Status</h4>

                    </div>
                    <div class="timeline-body">
                        <p>Mussum ipsum cacilds, vidis litro abertis. Consetis adipiscings elitis. Pra lá , depois divoltis porris, paradis. Paisis, filhis, espiritis santis.</p>
                    </div>
                </div>
            </li>


        </ul>
    </div>
    <!-- /.content -->
    </div>
</section>