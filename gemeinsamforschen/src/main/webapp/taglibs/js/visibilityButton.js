function changeButtonText(clickedItem, callback) {
    let dropBtn = $('.dropbtn');
    let oldText = dropBtn.html();
    let oldVisibility = currentVisibility;
    currentVisibility = possibleVisibilities[clickedItem];
    let newText = oldText.replace(oldVisibility.buttonText, currentVisibility.buttonText);
    dropBtn.html(newText);
    if (callback) {
        callback();
    }
}


function dropDownClick() {
    $('#myDropdown').toggleClass('show');
}

// close dropdown after clicking
window.onclick = function (e) {
    if (!e.target.matches('.dropbtn') && !e.target.matches('.fa-caret-down')) {
        let myDropdown = document.getElementById("myDropdown");
        if (myDropdown && myDropdown.classList.contains('show')) {
            myDropdown.classList.remove('show');
        }
    }
};