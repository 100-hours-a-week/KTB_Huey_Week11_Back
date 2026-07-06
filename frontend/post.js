//게시글 작성

const postForm = document.getElementById("post-form");
postForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const formData = new FormData(postForm);
    console.log(formData);

    const response = await fetch("http://localhost:8080/posts", {
        method: "POST",
        body: formData,
    })

    if (response.ok) {
        const json = await response.json();
        console.log(json.data);
    } else {

    }
})