<%@ page import="com.projects.model.Business" %>
<%@ page import="java.util.List" %>
<%@ page import="com.projects.controller.MainController" %>
<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

    <style>

        body {
            background: url(/resources/pict/backgr.jpg);
        }

        .colorGr {
            color: darkslategrey;
        }

        hr {
            border: none;
            background-color: darkslategrey;
            height: 4px;
        }

        i {
            font-size: 14pt;
            color: darkslategrey;
        }

        .leftimg {
            float: left;
            margin: 7px 30px 7px 7px;
        }

    </style>

    <script type="text/javascript">

        function funkClick($i) {
            document.getElementById($i).click();
        }
    </script>
</head>


<body>
<table>
    <img src="/resources/pict/zn.png" alt="not found" height="162" width="162" class="leftimg">
    <table>

        <h2 class="colorGr"> Add new business</h2>

        <c:url var="addAction" value="/business/add"></c:url>

        <form:form action="${addAction}" commandName="business" method="post">
            <table>
                <tr>
                    <td>
                        <form:label path="name">
                            <i>Business Name:</i>
                        </form:label>
                    </td>
                    <td>
                        <form:input path="name"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="endDate">
                            <i>Date of End:</i>
                        </form:label>
                    </td>
                    <td>
                        <form:input path="endDate" value="${dateNow}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="note">
                            <i>Note:</i>
                        </form:label>
                    </td>
                    <td>
                        <form:textarea path="note" value=""/>
                    </td>
                </tr>
                <tr>
                    <td>

                    <c:if test="${empty business.name}">
                        <form:input type="hidden" path="do" value="false"/>
                    </c:if>
                    <c:if test="${!empty business.name}">
                        <form:input type="hidden" path="do"/>
                    </c:if>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:input type="hidden" path="id"/>
                    </td>
                </tr>
               <table>


                    <td width="200">
                        <b> <font color="red">${message}</font></b>
                    </td>
                    <td>
                        <c:if test="${!empty business.name}">
                            <input type="submit" value=" Edit "/>
                        </c:if>
                        <c:if test="${empty business.name}">
                            <input type="submit" value=" Add "/>
                        </c:if>
                    </td>


                   </table>
            </table>
        </form:form>


        <table>
            <hr>
            <h1 class="colorGr"> My List To Do</h1>
            <th>
            <th width="50"><b class="colorGr">Filters:</b></th>
            <th width="50"><b>
                ${modelName}</b>

            </th>
        </table>

        <table>
            <td>
            <th width="50">
                <c:url var="allAction" value="/business"></c:url>
                <form:form action="${allAction}" commandName="business" method="get">
                    <input type="submit" name="Submit" value="All"/>
                </form:form></th>
            <th width="50">
                <c:url var="doneAction" value="/business/done"></c:url>
                <form:form action="${doneAction}" commandName="business" method="get">
                    <input type="submit" name="Submit" value="Done"/>
                </form:form></th>
            <th width="50">
                <c:url var="notDoneAction" value="/business/notdone"></c:url>
                <form:form action="${notDoneAction}" commandName="business" method="get">
                    <input type="submit" name="Submit" value="NotDone"/>
                </form:form></th>
            </td>
        </table>
        <hr>


        <table>
            <c:if test="${!empty listBusiness}">
                <form:form>
                    <tr>
                        <th width="30"><i> IsDo </i></th>
                        <th width="120"><i> Business Name</i></th>
                        <th width="120"><i>End date</i></th>
                        <th width="150"><i>Note</i></th>
                        <th width="60"><i>Edit</i></th>
                        <th width="60"><i>Delete</i></th>
                    </tr>

                    <c:forEach items="${listBusiness}" var="business">
                        <tr>
                            <th width="30"><INPUT TYPE="CHECKBOX" NAME="${business.id}"
                                                  <c:if test="${business.do == true}">checked</c:if>
                                                  onchange="funkClick(this.name)"/></th>
                            <c:if test="${business.terminate ==true}">
                                <th width="120" ><font color="red">${business.name}</font></th>
                            </c:if>
                            <c:if test="${business.terminate !=true}">
                                <th width="120">${business.name}</th>
                            </c:if>

                            <th width="120">${business.endDate}</th>
                            <th width="150">${business.note}</th>

                            <th><c:if test="${modelName == 'All'}">

                            <th width="60"><a href="<c:url value='/edit/${business.id}' />">
                                <img src="/resources/pict/edit.png" alt="not found" height="30" width="30"></a></th>
                            <th width="60"><a href="<c:url value='/remove/${business.id}' />">
                                <img src="/resources/pict/del.png" alt="not found" height="30" width="30"></a></th>

                            <a href="<c:url value='/updateAll/${business.id}'/>" id='${business.id}'></a>
                            </c:if>

                            <c:if test="${modelName == 'Done'}">
                                <th width="60"><a href="<c:url value='/editDone/${business.id}' />">
                                    <img src="/resources/pict/edit.png" alt="not found" height="30" width="30"></a></th>
                                <th width="60"><a href="<c:url value='/removeDone/${business.id}' />">
                                    <img src="/resources/pict/del.png" alt="not found" height="30" width="30"></a></th>

                                <a href="<c:url value='/updateDone/${business.id}'/>" id='${business.id}'></a>
                            </c:if>
                            <c:if test="${modelName == 'NotDone'}">

                                <th width="60"><a href="<c:url value='/editNotDone/${business.id}' />">
                                    <img src="/resources/pict/edit.png" alt="not found" height="30" width="30"></a></th>
                                <th width="60"><a href="<c:url value='/removeNotDone/${business.id}' />">
                                    <img src="/resources/pict/del.png" alt="not found" height="30" width="30"></a></th>

                                <a href="<c:url value='/updateNotDone/${business.id}'/>" id='${business.id}'></a>
                            </c:if>

                            </th>
                        </tr>
                    </c:forEach>

                </form:form>
            </c:if>
            <c:if test="${empty listBusiness}">
                <b> There are no business in the list.</b>
            </c:if>


        </table>

    </table>
</table>
</body>
</html>