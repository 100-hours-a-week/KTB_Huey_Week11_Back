document.addEventListener("DOMContentLoaded", (event) => {
    if (localStorage.getItem("user_id") === null) {
        console.log("unauthorized");
        window.location = "login.html";
    }
});