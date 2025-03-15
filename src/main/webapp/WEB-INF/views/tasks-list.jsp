<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="hu">
<head>
    <meta charset="UTF-8">
    <title>Feladatlista</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; padding: 10px; }
        h2 { color: #333; }
        form { margin-bottom: 20px; }
        input[type="text"] { padding: 5px; width: 250px; }
        button { padding: 5px 10px; background-color: #4CAF50; color: white; border: none; cursor: pointer; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 8px; border-bottom: 1px solid #ddd; }
        th { background-color: #f4f4f4; }
        .task-description { color: gray;}
    </style>
</head>
<body>

<h2>Feladatlista</h2>

<!-- Új feladat hozzáadása -->
<form action="/tasks/add" method="post">
    <input class="task-title" type="text" name="title" placeholder="Új feladat" required>
    <input class="task-description" type="text" name="description" placeholder="Leírás" required>
    <button type="submit">Hozzáadás</button>
</form>

<!-- Feladatok listázása -->
<table>
    <thead>
        <tr>
            <th>Feladat</th>
            <th>Leírás</th>
            <th>Elvégezve</th>
            <th>Törlés</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="task" items="${tasks}">
            <tr>
                <td class="task-title">${task.title}</td>
                <td class="task-description">${task.description}</td>
                <td>
                    <form action="/tasks/update/${task.id}" method="post" style="display:inline;">
                        <input type="hidden" name="_method" value="PUT">
                        <input type="checkbox" name="completed" value="true" onchange="this.form.submit()" ${task.completed ? 'checked' : ''}>
                    </form>
                </td>
                <td>
                    <form action="/tasks/delete/${task.id}" method="post" style="display:inline;">
                        <input type="hidden" name="_method" value="DELETE">
                        <button type="submit" style="background-color: red;">Törlés</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>
