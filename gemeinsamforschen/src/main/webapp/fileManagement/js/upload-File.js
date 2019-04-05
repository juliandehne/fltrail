$(document).ready(function () {
    $('#upload').on('click', function () {
        let data =  document.querySelector('#fileInput').files[0];
        let fileName = data.name;
        let formData = new FormData();
        let xhr = new XMLHttpRequest();

        formData.append(fileName,data,fileName);

        //open connection to servlet
        xhr.open('POST', '../rest/fileStorage/presentation/fileName/'+fileName, true);

        //handle status when request finished
        xhr.onload = function () {
            if (xhr.status === 200) {
                // File(s) uploaded.
                uploadButton.innerHTML = 'Upload';
            } else {
                alert('An error occurred!');
            }
        };
        //send data
        xhr.send(formData);
/*
        $.ajax({
            url: '../rest/fileStorage/presentation/fileName/'+fileName,
            headers: {
                "Cache-Control": "no-cache"
            },
            data: data,
            processData: false,
            contentType: false,
            mediaType: false,
            type: 'POST',
            success: function(response){

            }
        });
        */
    });
});