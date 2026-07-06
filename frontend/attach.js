//첨부파일 업로드 (게시글 작성, 게시글 수정)

const attach = document.getElementById("attach");
attach.addEventListener("change", async (event) => {
    event.preventDefault();
    const formData = new FormData(attach);

    const response = await fetch("http://localhost:8080/public/attachments", {
        method: "POST",
        headers: {
            "Content-Type": "multipart/form-data",
        },
        body: formData,
    })

    if (response.ok) {
        const json = await response.json();
        const imageUrl = document.getElementById("imageUrl");
        imageUrl.value = json.fileUrl;
    } else {

    }
});