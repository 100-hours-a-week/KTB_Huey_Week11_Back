//회원 생성

const signupForm = document.getElementById("signup-form");
signupForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const formData = new FormData(signupForm);
    console.log(formData);

    const response = await fetch("http://localhost:8080/users", {
        method: "POST",
        body: formData,
    });

    if (response.ok) {
        const json = await response.json();
        window.location = "login.html";
    } else {

    }
})