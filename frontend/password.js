//비밀번호 수정

const passwordForm = document.getElementById("password-form");
passwordForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    
    const formData = new FormData(passwordForm);

    const response = await fetch("http://localhost:8080/users/me/password", {
        method: "PATCH",
    });

    if (response.ok) {
        const json = await response.json();
    } else {

    }
})