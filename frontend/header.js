
const userProfile = document.getElementById("user-profile");
if (userProfile !== null) {
    userProfile.src = localStorage.getItem("user_profileImageUrl");
}

const menuButton = document.getElementById("menu-button");
const menu = document.getElementById("dropdown-menu");

if (menuButton !== null) {
    menuButton.addEventListener("click", () => {
        menu.classList.toggle("hidden");
    });

    document.addEventListener("click", (event) => {

    if (
        !menuButton.contains(event.target) &&
        !menu.contains(event.target)
    ) {
        menu.classList.add("hidden");
    }

    });
};


const logout = document.getElementById("logout");
if (logout !== null) {
    logout.addEventListener("click", async (event) => {
        const response = await fetch("http://localhost:8080/users/logout", {
            method: "POST",
            credentials: "include",
        })

        if (response.ok) {
            window.location = "login.html";
        } else {

        }
    });
}
