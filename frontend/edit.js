const params = new URLSearchParams(document.location.search);
const postId = params.get("postId");

//게시글 수정

const editForm = document.getElementById("edit-form");
editForm.addEventListener("submit", async (event, postId) => {
    event.preventDefault();
    const formData = new FormData(editForm);
    
    const response = await fetch(`http://localhost:8080/post/${postId}`, {
        method: "PATCH",
        body: formData,
    })

    if (response.ok) {
        const json = await response.json();
    } else {

    }
});