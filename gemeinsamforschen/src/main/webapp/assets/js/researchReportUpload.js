//import * as FilePond from 'filepond';
import FilePond from 'filepond';
import FilepondPluginImagePreview from 'filepond-plugin-image-preview';

FilePond.registerPlugin(
    FilepondPluginImagePreview
);

const inputElement = document.querySelector('input[type="file"]');
const filepondTest = FilePond.create(
    inputElement,
    {
        allowImagePreview: true,
        labelIdle: 'Bitte Datei wählen <span class="filepond--label-action"> Browse </span>>'
    }
);

FilePond._setOptions(
    {
        server: 'test/'  //Here insert server
    }
);