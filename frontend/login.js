let email = document.getElementById("email");
email.addEventListener("blur", async (event) => {
    document.getElementById("helper-text-email").textContent = validateEmail(email.value);
});

function validateEmail(input) {
    if (input === "") {
        return "*이메일을 입력해주세요.";
    } else if ("") {
        return "*올바른 이메일 주소 형식을 입력해주세요. (예: example@example.com)";
    } else if ("") {
        return "*중복된 이메일입니다.";
    } else {
        return "";
    }
}

const password = document.getElementById("password");
password.addEventListener("blur", async (event) => {
    document.getElementById("helper-text-password").textContent = validatePassword(password.value);
});

function validatePassword(input) {

}

//로그인
const loginForm = document.getElementById("login-form");
loginForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const formData = new FormData(loginForm);
    console.log(formData);

    const response = await fetch("http://localhost:8080/users/login", {
        method: "POST",
        body: formData,
        credentials: "include",
    });

    if (response.ok) {
        const json = await response.json();
        const data = json.data;
        localStorage.setItem("user_id", data.userId);
        localStorage.setItem("user_email", data.email);
        localStorage.setItem("user_nickname", data.nickname);
        localStorage.setItem("user_profileImageUrl", "http://localhost:8080" + data.profileImage);
        window.location = "index.html";
    } else {

    }
});