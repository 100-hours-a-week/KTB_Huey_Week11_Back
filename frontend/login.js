//로그인

const loginForm = document.getElementById("login-form");
loginForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const formData = new FormData(loginForm);
    console.log(formData);

    const response = await fetch("http://localhost:8080/users/login", {
        method: "POST",
        body: formData,
    });

    if (response.ok) {
        const json = await response.json();
        localStorage.setItem("userId", json.data.userId);
        localStorage.setItem("email", json.data.email);
        localStorage.setItem("nickname", json.data.nickname);
        localStorage.setItem("profileImage", json.data.profileImage);
        window.location = "index.html";
    } else {

    }
});