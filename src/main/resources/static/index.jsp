<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>Введите текст</h1>
<form action="\analyze">
    <textarea rows="4" cols="50" name="text" id="text" >
      ${requestScope.text}
    </textarea>
    <input type="submit" value="Отправить">
    <textarea>
        ${requestScope.verdict.toString()}
    </textarea>
</form>

</body>
</html>