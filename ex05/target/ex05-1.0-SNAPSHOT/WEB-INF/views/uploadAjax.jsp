<%--
  Created by IntelliJ IDEA.
  User: yeonjinoh
  Date: 2021/04/20
  Time: 2:38 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>Upload with Ajax</h1>
<div class="uploadDiv">
    <input type="file" name="uploadFile" multiple>
</div>
<button id="uploadBtn">Submit</button>


<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function (){
        $("#uploadBtn").on("click", function (e){
            var formData = new FormData();

            var inputFile = $("input[name='uploadFile']");

            var files = inputFile[0].files;

            console.log(files);

            //add filedate to formdata
            for(var i = 0; i < files.length; i++){
                formData.append("uploadFile", files[i]);
            }
            $.ajax({
                url:'/uploadAjaxAction',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                success: function(result){
                    alert("Uploaded");
                }
            });
        });
    });
</script>
</body>
</html>
