const params = new URLSearchParams(document.location.search);
const postId = params.get("postId");

document.addEventListener("DOMContentLoaded", (event) => {
    document.getElementById("go-back").href = "view.html?postId=" + localStorage.getItem("postId");
    document.getElementById("title").value = localStorage.getItem("postTitle");
    document.getElementById("content").value = localStorage.getItem("postContent");
    document.getElementById("image").src = localStorage.getItem("postImageUrl");
})

//게시글 수정

const editForm = document.getElementById("edit-form");
editForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const formData = new FormData(editForm);
    
    console.log(formData);

    const response = await fetch(`http://localhost:8080/posts/${localStorage.getItem("postId")}`, {
        method: "PATCH",
        body: formData,
        credentials: "include",
    })

    if (response.ok) {
        const json = await response.json();
        window.location = `view.html?postId=${localStorage.getItem("postId")}`;
    } else {

    }
});