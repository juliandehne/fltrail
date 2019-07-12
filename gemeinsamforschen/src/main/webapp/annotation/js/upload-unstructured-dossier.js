$(document).ready(function () {

    getAnnotationCategories(function (categories) {
        buildAnnotationList(categories);
    });
});