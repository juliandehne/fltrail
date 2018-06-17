$(document).ready(function() {

    console.log("start ready function");

    $.contextMenu({
        selector: '.context-menu-one',
        callback: function(key, options) {

            console.log("start annotation");

            var text = generateId(6);
            var user = generateId(1).toUpperCase();
            var color = getRandomColor();

            $.fn.addAnnotation(user, text, color);

            window.close;
        },
        items: {
            "annotation": {name: "Annotation", icon: "edit"}
        }
    });

    $('.context-menu-one').on('click', function(e){
        console.log('clicked', this);
    })

    $.fn.addAnnotation = function(title, text, color) {
        var list = $('#annotations')
        var selection = getSelectedText()

        if (selection.length > 0) {

            var replacement = $('<span></span>').css('background-color', color).html(selection);

            var replacementHtml = $('<div>').append(replacement.clone()).remove().html();

            $('#hiho').html( $('#hiho').html().replace(selection, replacementHtml) );

            if ($('#annotations li').filter( ".listelement" ).length > 0) {
                list.prepend(
                    $('<li>').attr('class', 'spacing')
                )
            }

            list.prepend(
                $('<li>').attr('class', 'listelement').append(
                    $('<div>').attr('class', 'card').append(
                        $('<div>').attr('class', 'cardAvatar').css('background-color', color).append(
                            $('<b>').append(title)
                        )
                    ).append(
                        $('<div>').attr('class', 'cardContent').append(
                            $('<span>').append(selection.substring(0, 5) + "...")
                        )
                    )
                )
            );
        }
    }

    console.log("end ready function");
});

function getSelectedText() {
    if(window.getSelection){
        return window.getSelection().toString();
    }
    else if(document.getSelection){
        return document.getSelection();
    }
    else if(document.selection){
        return document.selection.createRange().text;
    }
}

function generateId(length) {
    var id = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    for (var i = 0; i < length; i++) {
        id += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    return id;
}

function getRandomColor() {
    return 'rgb(' +
        (Math.floor(Math.random()*56)+170) + ', ' +
        (Math.floor(Math.random()*56)+170) + ', ' +
        (Math.floor(Math.random()*56)+170) +
        ')';
}