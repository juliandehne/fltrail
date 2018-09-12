
function calculateHierachy(level) {
    if (level == 0) {
        return "";
    } else {
        return calculateHierachy(level-1)+"../";
    }
}