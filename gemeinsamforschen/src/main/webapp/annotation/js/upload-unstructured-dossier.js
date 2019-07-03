$(document).ready(function () {

    getAnnotationCategories(function (categories) {
        buildAnnotationList(categories);
    });

    let titel = $('#ownTitle').val();
});