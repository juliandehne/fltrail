$(document).ready(function () {
    handleLocker("UPLOAD_DOSSIER");
    getAnnotationCategories(function (categories) {
        buildAnnotationList(categories);
    });
});