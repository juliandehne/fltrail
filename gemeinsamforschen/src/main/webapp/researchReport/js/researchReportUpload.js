//import * as FilePond from 'filepond';
//import FilePond from 'filepond';
//import FilepondPluginImagePreview from 'filepond-plugin-image-preview';


FilePond.registerPlugin(
    FilePondPluginImagePreview,
);

$(document).ready(function() {
    $('#student').val(student);
    $('#project').val(project);


})

var student = getQueryVariable("token");
var project = getQueryVariable("projectId");
//document.body.appendChild(pond.element);
const inputElement = document.querySelector('input[type="file"]');
const pond = FilePond.create(
    inputElement,
    {
        allowImagePreview: true,
        name: 'filepond',
        labelIdle: 'Hier<span class="filepond--label-action"> Browse </span>',
    }
);

var storage = "../uploads/" + student + "/" + project;

FilePond.setOptions(
    {
        server: storage  //Here insert server
    }
);


