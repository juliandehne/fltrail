$(document).ready(function(){
    $('#nextPhase').click(function(){
        var progressbar =$('#progressbar');
        switch (progressbar.attr('class')){
            case 'progress-bar pg-groups':
                progressbar.removeClass('pg-groups');
                progressbar.addClass('pg-dossier');
                break;
            case 'progress-bar pg-dossier':
                progressbar.removeClass('pg-dossier');
                progressbar.addClass('pg-feedback');
                break;
            case 'progress-bar pg-feedback':
                progressbar.removeClass('pg-feedback');
                progressbar.addClass('pg-reflection');
                break;
            case 'progress-bar pg-reflection':
                progressbar.removeClass('pg-reflection');
                progressbar.addClass('pg-presentation');
                break;
            case 'progress-bar pg-presentation':
                progressbar.removeClass('pg-presentation');
                progressbar.addClass('pg-assessment');
                break;
            case 'progress-bar pg-assessment':
                progressbar.removeClass('pg-assessment');
                progressbar.addClass('pg-done');
                break;
        }
    });
});
