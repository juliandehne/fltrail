function buildAnnotationList(categories) {
    let data = {categories: []};
    data.fileRole = fileRole;
    categories.forEach(function (category) {
        data.categories.push({name: category, nameLower: category.toLowerCase()})
    });
    let tmpl = $.templates("#annotationTemplate");
    let html = tmpl.render(data);
    $("#annotationTemplateResult").html(html);
}