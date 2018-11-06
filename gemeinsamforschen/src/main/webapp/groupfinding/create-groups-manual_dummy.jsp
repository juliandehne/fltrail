<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>jQuery UI Sortable - Handle empty lists</title>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <style>
        #sortable1, #sortable2, #sortable3, #sortable4 {
            list-style-type: none;
            margin: 0;
            float: left;
            margin-right: 10px;
            background: #eee;
            padding: 5px;
            width: 143px;
        }

        #sortable1 li, #sortable2 li, #sortable3 li, #sortable4 li {
            margin: 5px;
            padding: 5px;
            font-size: 1.2em;
            width: 120px;
        }
    </style>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        $(function () {
            $("ul.droptrue").sortable({
                connectWith: "ul"
            });

            $("#sortable1, #sortable2, #sortable3, #sortable4").disableSelection();
        });
    </script>
</head>
<body>


<table>

    <tr>
        <td>
            <h3>Gruppe 1</h3>
            <ul id="sortable1" class="droptrue">
                <li class="ui-state-default">Can be dropped..</li>
                <li class="ui-state-default">..on an empty list</li>
                <li class="ui-state-default">Item 3</li>
                <li class="ui-state-default">Item 4</li>
                <li class="ui-state-default">Item 5</li>
            </ul>
        </td>
        <td>
            <h3>Gruppe 2</h3>
            <ul id="sortable2" class="droptrue">
                <li class="ui-state-highlight">Cannot be dropped..</li>
                <li class="ui-state-highlight">..on an empty list</li>
                <li class="ui-state-highlight">Item 3</li>
                <li class="ui-state-highlight">Item 4</li>
                <li class="ui-state-highlight">Item 5</li>
            </ul>
        </td>
    </tr>
    <td>
        <h3>Gruppe 3</h3>
        <ul id="sortable3" class="droptrue">
            <li class="ui-state-highlight">x..</li>
            <li class="ui-state-highlight">.x2t</li>
            <li class="ui-state-highlight">x 3</li>
            <li class="ui-state-highlight">x 4</li>
            <li class="ui-state-highlight">x 5</li>
        </ul>
    </td>
    <td>
        <h3>Gruppe 4</h3>
        <ul id="sortable4" class="droptrue">
            <li class="ui-state-highlight">x 6</li>
        </ul>
    </td>

</table>


</body>
</html>