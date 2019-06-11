/**
 * This function will fire when the DOM is ready
 */
let groupId = 0;
let contributionCategory;
let hierarchyLevel;
let fullSubmissionId = "";
let personal;
let currentVisibility;
let possibleVisibilities = [];

$(document).ready(function () {
    contributionCategory = $('#contributionCategory').html().trim();
    setupPageContent();
    if (!personal) {
        getMyGroupId(getFullSubmissionOfGroup);
    }
    hierarchyLevel = $('#hierarchyLevel').html().trim();


    $('#btnSave').click(function () {

        getMyGroupId(function (groupId) {
            if (quill.getText().length > 1) {
                let content = quill.getContents();
                let html = quill.root.innerHTML;

                // build request
                let fullSubmissionPostRequest = {
                    groupId: groupId,
                    text: JSON.stringify(content),
                    html: html,
                    projectName: $('#projectName').text().trim(),
                    personal: personal,
                    contributionCategory: contributionCategory.toUpperCase(),
                    visibility: currentVisibility.name
                };

                // save request in database
                createFullSubmission(fullSubmissionPostRequest, function () {

                    // back to main page
                    location.href = hierarchyLevel + "project/tasks-student.jsp?projectName=" + $('#projectName').text().trim();
                });
            } else {
                alert("Ein Text wird benötigt");
            }
        });
    });

    $('#backToTasks').click(function () {
        location.href = "../project/tasks-student.jsp?projectName=" + $('#projectName').text().trim();
    });
    $('#btnBack').click(function () {
        // if there is text inside the textarea
        // show user alert message that the text will be lost
        if (window.confirm("Möchten Sie zur vorherigen Seite zurückkehren? \nIhr bisheriger Text wird nicht gespeichert.")) {
            // clear textarea
            quill.setText("");

            // jump to previous page
            //window.history.back();
            location.href = "../../project/projects-student.jsp";
        }

        // nothing to check
        else {
            // jump to previous page
            //window.history.back();
            location.href = "../../project/projects-student.jsp";
        }
    });

});


function setupPageContent() {
    let personalString = $("#personal").html().trim();
    personal = personalString.toUpperCase() === 'TRUE';
    getVisibilities(function (response) {
        Object.entries(response).forEach(([name, buttonText]) => {
            possibleVisibilities[name] = {name: name, buttonText: buttonText};
        });
        if (personal === true) {
            currentVisibility = possibleVisibilities['PERSONAL'];
        } else {
            currentVisibility = possibleVisibilities['GROUP'];
        }
        populateTextFields();
    });
}


function populateTextFields() {
    let data = {};
    data.header = contributionCategory === "Portfolio" ? "Portfolio-Eintrag" : contributionCategory;
    data.contributionCategory = contributionCategory;
    data.possibleVisibilities = Object.values(possibleVisibilities);
    data.currentVisibility = currentVisibility;
    let tmpl = $.templates("#visibilityTemplate");
    let html = tmpl.render(data);
    $("#templateResult").html(html);

}

function visibilityDropDownClicked(clickedItem) {

    let dropBtn = $('.dropbtn');
    let oldText = dropBtn.html();
    let oldVisibility = currentVisibility;
    currentVisibility = possibleVisibilities[clickedItem];
    let newText = oldText.replace(oldVisibility.buttonText, currentVisibility.buttonText);
    dropBtn.html(newText);

}


function dropDownClick() {
    $('#myDropdown').toggleClass('show');
}

// close dropdown after clicking
window.onclick = function (e) {
    if (contributionCategory === 'Portfolio') {
        if (!e.target.matches('.dropbtn') && !e.target.matches('.fa-caret-down')) {
            var myDropdown = document.getElementById("myDropdown");
            if (myDropdown.classList.contains('show')) {
                myDropdown.classList.remove('show');
            }
        }
    }
};
