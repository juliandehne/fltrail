$(document).ready(function () {
    if (fileRole.toUpperCase() === 'DOSSIER') {
        handleLocker("UPLOAD_DOSSIER");
        getAnnotationCategories(function (categories) {
            buildAnnotationList(categories);
        });
    }
    //not recommended to change placeholder during runtime ... but it works =)
    editor.__quill.root.dataset.placeholder = "Fügen Sie hier Ihr Dossier ein. Speichern Sie danach mit dem Button unten rechts.";
});