//import * as FilePond from 'filepond';
//import FilePond from 'filepond';
//import FilepondPluginImagePreview from 'filepond-plugin-image-preview';


FilePond.registerPlugin(
    FilePondPluginImagePreview,
);

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


FilePond.setOptions(
    {
        server: 'test/'  //Here insert server
    }
);