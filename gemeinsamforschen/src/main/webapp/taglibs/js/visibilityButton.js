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