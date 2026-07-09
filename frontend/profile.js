//프로필 사진 업로드 (회원 가입, 회원 정보 수정)

let input = document.getElementById("image");
input.style.opacity = 0;
let imageUrl = document.getElementById("imageUrl");
imageUrl.style.opacity = 0;
let profileImageForm = document.getElementById("profile-image");
profileImageForm.addEventListener("change", async (event) => {
    const formData = new FormData(profileImageForm);

    const response = await fetch("http://localhost:8080/users/me/profile-image", {
        method: "POST",
        body: formData,
    });

    if (response.ok) {
        const json = await response.json();
        const preview = document.getElementById("preview");
        preview.src = json.data.fileUrl;
        imageUrl.value = json.data.fileUrl;
    } else {
        
    }
})