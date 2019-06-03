<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>Введите текст</h1>
<form action="\analyze" method="post">
    <textarea rows="15" cols="50" name="text" id="text" >
        ${requestScope.text}
    </textarea><br>
    <input type="submit" value="Отправить"><br>
    <textarea rows="10" cols="50">
        ${requestScope.verdict.getVerdict().toString()}
    </textarea>
</form>

</body>
</html>