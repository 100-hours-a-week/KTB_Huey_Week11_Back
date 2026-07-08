//첨부파일 업로드 (게시글 작성, 게시글 수정)

let imageUrl = document.getElementById("imageUrl");
imageUrl.style.opacity = 0;
const attach = document.getElementById("attach");
attach.addEventListener("change", async (event) => {
    event.preventDefault();
    const formData = new FormData(attach);

    console.log(formData);

    const response = await fetch("http://localhost:8080/public/attachments", {
        method: "POST",
        body: formData,
        credentials: "include",
    })

    if (response.status === 201) {
        console.log("created");
        const json = await response.json();
        console.log(json);
        console.log(json.data);
        console.log(json.data.fileUrl);
        console.log("imageUrl's value:");
        console.log(imageUrl.value);
        imageUrl.value = json.data.fileUrl;
    } else {

    }
});