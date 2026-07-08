const profileImage = document.getElementById("image");
profileImage.addEventListener("input", async (event) => {
    console.log("event");
});

function validateProfileImage() {

}

//이메일 유효성 검사
const email = document.getElementById("email");
email.addEventListener("blur", async (event) => {
    console.log("event");
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

//비밀번호 유효성 검사
const password = document.getElementById("password");
password.addEventListener("blur", async (event) => {
    document.getElementById("helper-text-password").textContent = validatePassword(password.value);
});

function validatePassword(input) {
    if (input === "") {
        return "*비밀번호를 입력해주세요.";
    } else if ("") {
        return "*비밀번호가 다릅니다.";
    } else if ("") {
        return "*비밀번호는 8자 이상, 20자 이하이며, 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 이상 포함해야 합니다.";
    } else{
        return "";
    }
}

//비밀번호 확인 유효성 검사
const passwordConfirm = document.getElementById("password-confirm");
passwordConfirm.addEventListener("blur", async (event) => {
    document.getElementById("helper-text-password-confirm").textContent = validatePasswordConfirm(passwordConfirm.value);
});

function validatePasswordConfirm(input) {
    if (input === "") {
        return "*비밀번호를 한번더 입력해주세요.";
    } else if ("") {
        return "*비밀번호가 다릅니다.";
    } else {
        return "";
    }
}

//닉네임 유효성 검사
const nickname = document.getElementById("nickname");
nickname.addEventListener("blur", async (event) => {
    document.getElementById("helper-text-nickname").textContent = validateNickname(nickname.value);
});

function validateNickname(input) {
    if (input === "") {
        return "*닉네임을 입력해주세요.";
    } else if ("") {
        return "*띄어쓰기를 없애주세요.";
    } else if ("") {
        return "*중복된 닉네임입니다.";
    } else if ("") {
        return "*닉네임은 최대 10자까지 작성 가능합니다.";
    } else {
        return "";
    }
}

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