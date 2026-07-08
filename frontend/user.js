document.addEventListener("DOMContentLoaded", (event) => {
    renderUserInfo();
})

function renderUserInfo() {
    document.getElementById("email").textContent = localStorage.getItem("user_email");
    document.getElementById("nickname").value = localStorage.getItem("user_nickname");
    document.getElementById("imageUrl").value = localStorage.getItem("user_profileImageUrl");
    document.getElementById("preview").src = localStorage.getItem("user_profileImageUrl");
}

function parseImageUrl(url) {
    return "http://localhost:8080" + url;
}

//회원 정보 수정

const userForm = document.getElementById("user-form");
userForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const formData = new FormData(userForm);
    
    const response = await fetch("http://localhost:8080/users/me", {
        method: "PUT",
        body: formData,
        credentials: "include",
    })

    if (response.ok) {
        const json = await response.json();
        localStorage.setItem("user_nickname", formData.get("nickname"));
        localStorage.setItem("user_profileImageUrl", formData.get("profileImageUrl"));
        renderUserInfo();

        const toast = document.getElementById("toast");
        toast.classList.remove("hidden");
        
        setTimeout(() => {
            toast.classList.add("hidden");
        }, 5000);

    } else {

    }
    
});


//회원 탈퇴

const withdraw = document.getElementById("withdraw");
withdraw.addEventListener("click", async (event) => {
    const response = await fetch("http://localhost:8080/users/me", {
        method: "DELETE",
        credentials: "include",
    });

    if (response.ok) {
        const json = await response.json();
        window.location = "login.html";
    } else {

    }
})

const userDelete = document.getElementById("user-delete");
userDelete.addEventListener("click", (event) => {
    const userDeleteModal = document.getElementById("user-delete-modal");

    userDeleteModal.classList.remove("hidden");
})

document.getElementById("user-delete-modal-cancel").addEventListener("click", (event) => {
    const userDeleteModal = document.getElementById("user-delete-modal");
    userDeleteModal.classList.add("hidden");
})

function setUser(data) {
    localStorage.setItem("user_id", "");
    localStorage.setItem("user_email", "");
    localStorage.setItem("user_nickname", "");
    localStorage.setItem("user_profileImageUrl", "");
}

function updateUser(data) {
    localStorage.setItem("user_nickname", data.get("nickname"));
    localStorage.setItem("user_profileImageUrl", data.get("profileImageUrl"));
}