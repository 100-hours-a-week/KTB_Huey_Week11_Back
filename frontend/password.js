//비밀번호 유효성 검사
const password = document.getElementById("newPassword");
const passwordConfirm = document.getElementById("newPassword-confirm");

function createValidations() {
    const validations = {
        password: {
            existence: false,
            validity: false,
            state: "",
            setExistence: function(b) {
                this.existence = b;
                if (!b) {
                    this.state = "inexistence";
                } else if (!this.validity) {
                    //비존재 <-> 비유효
                    this.state = "invalidity";
                } else if (!validations.equality) {
                    //비존재 <-> 비일치
                    this.state = "inequality";
                } else {
                    //비존재 <-> 통과
                    this.state = "valid";
                }
                renderPasswordHelperText();
            },
            setValidity: function(b) {
                this.validity = b;
                if (!this.existence) {
                    return;
                } else if (!b) {
                    this.state = "invalidity";
                } else if (validations.equality) {
                    //비유효 <-> 통과
                    this.state = "valid";
                } else {
                    //비유효 <-> 비일치
                    this.state = "inequality";
                }
                renderPasswordHelperText();
            }
        },
        passwordConfirm: {
            existence: false,
            state: "",
            setExistence: function(b) {
                this.existence = b;
                if (validations.equality) {
                    //비존재 <-> 통과
                    this.state = b ? "valid" : "inexistence";
                } else {
                    //비존재 <-> 비일치
                    this.state = b ? "inequality" : "inexistence";
                }
                renderPasswordConfirmHelperText();
            }
        },
        equality: false,
        setEquality: function(b) {
            this.equality = b;
            if (this.password.existence && this.password.validity) {
                //비일치 <-> 통과
                this.password.state = b ? "valid" : "inequality";
                renderPasswordHelperText();
            }

            if (this.passwordConfirm.existence) {
                //비일치 <-> 통과
                this.passwordConfirm.state = b ? "valid" : "inequality";
                renderPasswordConfirmHelperText();
            }
        }
    }

    return validations;
}

const validations = createValidations();

function renderPasswordHelperText() {
    const passwordHelperText = document.getElementById("helper-text-password");

    switch (validations.password.state) {
        case "inexistence":
            passwordHelperText.textContent = "*비밀번호를 입력해 주세요.";
            break;
        case "invalidity":
            passwordHelperText.textContent = "*비밀번호는 8자 이상, 20자 이하이며, 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 이상 포함해야 합니다.";
            break;
        case "inequality":
            passwordHelperText.textContent = "*비밀번호 확인과 다릅니다.";
            break;
        case "valid":
            passwordHelperText.textContent = "";
            break;
        default:
            break;
    }
}

function renderPasswordConfirmHelperText() {
    const passwordConfirmHelperText = document.getElementById("helper-text-password-confirm");

    switch (validations.passwordConfirm.state) {
        case "inexistence":
            passwordConfirmHelperText.textContent = "*비밀번호를 다시 한번 입력해 주세요.";
            break;
        case "inequality":
            passwordConfirmHelperText.textContent = "*비밀번호와 다릅니다.";
            break;
        case "valid":
            passwordConfirmHelperText.textContent = "";
            break;
        default:
            break;
    }
}

password.addEventListener("focusout", async (event) => {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,20}$/;
    //존재성 검사
    validations.password.setExistence(password.value !== "");

    //유효성 검사
    validations.password.setValidity(passwordRegex.test(password.value));

    //일치 검사
    equalityCheck();
});

passwordConfirm.addEventListener("focusout", async (event) => {
    //존재성 검사
    validations.passwordConfirm.setExistence(passwordConfirm.value !== "");

    //일치 검사
    equalityCheck();
});

function equalityCheck() {
    validations.setEquality(password.value === passwordConfirm.value);

    document.getElementById("password-submit").disabled = !(validations.password.state === "valid" && validations.passwordConfirm.state === "valid")
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
        const toast = document.getElementById("toast");
        toast.classList.remove("hidden");
        
        setTimeout(() => {
            toast.classList.add("hidden");
        }, 5000);
    } else {

    }
})