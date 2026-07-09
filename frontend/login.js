const validations = {
    email: false,
    password: false
};

function validate() {
    const validation = validations.email && validations.password;
    const submit = document.getElementById("login-submit");
    if (validation) {
        submit.disabled = false;
    } else {
        submit.disabled = true;
    }
};

//이메일 유효성 검사
const email = document.getElementById("email");
email.addEventListener("focusout", async (event) => {
    document.getElementById("helper-text-email").textContent = await validateEmail(email.value);
    validate();
});

async function validateEmail(input) {
    const emailRegex = /^\D+@\D+\.\D+$/;
    validations.email = false;

    if (input === "") {
        return "*이메일을 입력해주세요.";
    } else if (!emailRegex.test(input)) {
        return "*올바른 이메일 주소 형식을 입력해주세요. (예: example@example.com)";
    } else {
        validations.email = true;
        return "";
    }
}

//비밀번호 유효성 검사
const password = document.getElementById("password");

password.addEventListener("focusout", async (event) => {
    const passwordHelperText = document.getElementById("helper-text-password");
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,20}$/;
    validations.password = false;

    if (password.value === "") {
        passwordHelperText.textContent = "*비밀번호를 입력해주세요.";
    } else if (!passwordRegex.test(password.value)) {
        passwordHelperText.textContent = "*비밀번호는 8자 이상, 20자 이하이며, 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 이상 포함해야 합니다.";
    } else {
        validations.password = true;
        validate();
    }
});

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
        const passwordHelperText = document.getElementById("helper-text-password");
        passwordHelperText.textContent = "*아이디 또는 비밀번호를 확인해주세요.";
    }
});