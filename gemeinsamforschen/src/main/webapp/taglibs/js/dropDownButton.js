let currentButtonName = "";


function dropDownClick(buttonName) {
    if (typeof buttonName === 'undefined') {
        buttonName = "myDropdown";
    }
    currentButtonName = buttonName;
    $(`#${buttonName}`).toggleClass('show');
}

// close dropdown after clicking
window.onclick = function (e) {
    if (!e.target.matches('.dropbtn') && !e.target.matches('.fa-caret-down')) {
        let myDropdown = document.getElementById(currentButtonName);
        if (myDropdown && myDropdown.classList.contains('show')) {
            myDropdown.classList.remove('show');
        }
    }
};