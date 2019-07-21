$(document).ready(function () {
    if (fileRole.toUpperCase() === 'DOSSIER') {
        handleLocker("UPLOAD_DOSSIER");
        getAnnotationCategories(function (categories) {
            buildAnnotationList(categories);
        });
    }
});