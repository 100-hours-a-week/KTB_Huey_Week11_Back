//회원 정보 수정

const userForm = document.getElementById("user-form");
userForm.addEventListener("user-form", async (event) => {
    event.preventDefault();
    const formData = new FormData(userForm);

    const response = await fetch("http://localhost:8080/users/me", {
        method: "PUT",
        body: formData,
    })

    if (response.ok) {
        const json = await response.json();
    } else {

    }
});

//회원 탈퇴

const withdraw = document.getElementById("withdraw");
withdraw.addEventListener("onClick", async (event) => {
    const response = await fetch("http://localhost:8080/users/me", {
        method: "DELETE",
    });

    if (response.ok) {
        const json = await response.json();
    } else {

    }
})