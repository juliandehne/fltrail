$(document).ready(function () {

    getAnnotationCategories(function (categories) {
        buildAnnotationList(categories);
    });

    let title = $('#ownTitle').val();
});