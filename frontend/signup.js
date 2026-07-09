//비밀번호 유효성 검사

function createValidations() {
    const validations = {
        profileImage: {
            existence: false,
            state: "",
            setExistence: function(b) {
                this.existence = b;
                this.state = b ? "valid" : "inexistence";
                renderProfileImageHelperText;
            }
        },
        email: {
            existence: false,
            validity: false,
            uniqueness: false,
            state: "",
            setExistence: function(b) {
                this.existence = b;
                if (!b) {
                    this.state = "inexistence";
                } else if (!this.validity) {
                    this.state = "invalidity";
                } else if (!validations.equality) {
                    this.state = "duplicate";
                } else {
                    this.state = "valid";
                }
                renderEmailHelperText();
            },
            setValidity: function(b) {
                this.validity = b;
                if (!this.existence) {
                    return;
                } else if (!b) {
                    this.state = "invalidity";
                } else if (this.uniqueness) {
                    //비유효 <-> 통과
                    this.state = "valid";
                } else {
                    //비유효 <-> 비고유
                    this.state = "duplicate";
                }
                renderEmailHelperText();
            },
            setUniqueness: function(b) {
                this.uniqueness = b;
                if (this.existence && this.validity) {
                    //비고유 <-> 통과
                    this.state = b ? "valid" : "duplicate";
                    renderEmailHelperText();
                }
            },
        },
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
        },
        nickname: {
            existence: false,
            compactness: false,
            uniqueness: false,
            length: false,
            state: "",
            setExistence: function(b) {
                this.existence = b;
                if (!b) {
                    this.state = "inexistence";
                } else if (!this.compactness) {
                    this.state = "have space";
                } else if (!this.length) {
                    this.state = "length limit exceed";
                } else if (!this.uniqueness) {
                    this.state = "duplicate";
                } else {
                    this.state = "valid";
                }
                renderNicknameHelperText();
            },
            setCompactness: function(b) {
                this.compactness = b;
                if (!this.existence) {
                    return;
                } else if (!b) {
                    this.state = "have space";
                } else if (!this.length) {
                    this.state = "length limit exceed";
                } else if (!this.uniqueness) {
                    this.state = "duplicate";
                } else {
                    this.state = "valid";
                }
                renderNicknameHelperText();
            },
            setLength: function(b) {
                this.length = b;
                if (!(this.existence && this.compactness)) {
                    return;
                } else if (!b) {
                    this.state = "length limit exceed";
                } else if (!this.uniqueness) {
                    this.state = "duplicate";
                } else {
                    this.state = "valid";
                }
                renderNicknameHelperText();
            }, 
            setUniqueness: function(b) {
                this.uniqueness = b;
                if (!(this.existence && this.compactness && this.length)) {
                    return;
                } else if (!b) {
                    this.state = "duplicate";
                } else {
                    this.state = "valid";
                }
                renderNicknameHelperText();
            },
        },
    }

    return validations;
}

const validations = createValidations();

//프로필 사진 유효성 검사
const profileImage = document.getElementById("image");
profileImage.addEventListener("change", (event) => {
    validations.profileImage.setExistence(profileImage.files !== "");
    validate();
});

//이메일 유효성 검사
const email = document.getElementById("email");
email.addEventListener("focusout", async (event) => {
    const emailRegex = /^\D+@\D+\.\D+$/;

    validations.email.setExistence(email.value !== "");
    validations.email.setValidity(emailRegex.test(email.value));

    const response = await fetch(`http://localhost:8080/users/dup/email?email=${email.value}`, {
        method: "GET",
        credentials: "include",
    });

    if (response.ok) {
        validations.email.setUniqueness(true);
    } else if (response.status === 409) {
        validations.email.setUniqueness(false);
    }

    validate();
});

//비밀번호 유효성 검사
const password = document.getElementById("password");
const passwordConfirm = document.getElementById("password-confirm");

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
    validate();
}

//닉네임 유효성 검사
const nickname = document.getElementById("nickname");
nickname.addEventListener("focusout", async (event) => {
    const nickNameRegex = /^\S+$/;

    validations.nickname.setExistence(nickname.value !== "");
    validations.nickname.setCompactness(nickNameRegex.test(nickname.value));
    validations.nickname.setLength(nickname.value.length <= 10);

    const response = await fetch(`http://localhost:8080/users/dup/nickname?nickname=${nickname.value}`, {
        method: "GET",
        credentials: "include",
    });

    if (response.ok) {
        validations.nickname.setUniqueness(true);
    } else if (response.status === 409) {
        validations.nickname.setUniqueness(false);
    }

    validate();
});

//최종 Validation
function validate() {
    document.getElementById("signup-submit").disabled = !(validations.profileImage.state === "valid" && validations.email.state === "valid" && validations.password.state === "valid" && validations.passwordConfirm.state === "valid" && validations.nickname.state === "valid");
}

function renderProfileImageHelperText() {
    const profileImageHelperText = document.getElementById("helper-text-profile-image");

    switch(validations.profileImage.state) {
        case "inexistence":
            profileImageHelperText.textContent = "*프로필 사진을 추가해주세요.";
            break;
        case "valid":
            profileImageHelperText.textContent = "";
            break;
        default:
            break;
    }

}

function renderEmailHelperText() {
    const emailHelperText = document.getElementById("helper-text-email");

    switch (validations.email.state) {
        case "inexistence":
            emailHelperText.textContent = "*이메일을 입력해 주세요.";
            break;
        case "invalidity":
            emailHelperText.textContent = "*올바른 이메일 주소 형식을 입력해 주세요. (예: example@example.com)";
            break;
        case "duplicate":
            emailHelperText.textContent = "*중복된 이메일 입니다.";
            break;
        case "valid":
            emailHelperText.textContent = "";
            break;
        default:
            break;
    }
}

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

function renderNicknameHelperText() {
    const nicknameHelperText = document.getElementById("helper-text-nickname");

    switch (validations.nickname.state) {
        case "inexistence":
            nicknameHelperText.textContent = "*닉네임을 입력해주세요.";
            break;
        case "have space":
            nicknameHelperText.textContent = "*띄어쓰기를 없애주세요.";
            break;
        case "length limit exceed":
            nicknameHelperText.textContent = "*닉네임은 최대 10자 까지 작성 가능합니다.";
            break;
        case "duplicate":
            nicknameHelperText.textContent = "*중복된 닉네임 입니다.";
            break;
        case "valid":
            nicknameHelperText.textContent = "";
            break;
        default:
            break;
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