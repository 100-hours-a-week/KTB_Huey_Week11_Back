//비밀번호 유효성 검사
const password = document.getElementById("newPassword");
password.addEventListener("blur", async (event) => {
    document.getElementById("helper-text-password").textContent = validatePassword(password.value);
});

function validatePassword(input) {

}

//비밀번호 확인 유효성 검사
const passwordConfirm = document.getElementById("newPassword-confirm");
passwordConfirm.addEventListener("blur", async (event) => {
    document.getElementById("helper-text-password-confirm").textContent = validatePasswordConfirm(passwordConfirm.value);
});

function validatePasswordConfirm(input) {

}

//비밀번호 수정
const passwordForm = document.getElementById("password-form");
passwordForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    
    const formData = new FormData(passwordForm);

    console.log(formData);

    const response = await fetch("http://localhost:8080/users/me/password", {
        method: "PATCH",
        body: formData,
        credentials: "include",
    });

    if (response.ok) {
        const json = await response.json();
    } else {

    }
})