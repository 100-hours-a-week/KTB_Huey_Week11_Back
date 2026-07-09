const validations = {
    title: false,
    content: true,
}

//게시글 작성

const postForm = document.getElementById("post-form");
postForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    if (!(validations.title && validations.content)) {
        document.getElementById("helper-text-post").textContent = "*제목, 내용을 모두 작성해주세요.";
        return;
    } else {
        document.getElementById("helper-text-post").textContent = "";
    }

    const formData = new FormData(postForm);
    console.log(formData);

    const response = await fetch("http://localhost:8080/posts", {
        method: "POST",
        body: formData,
        credentials: "include",
    })

    if (response.ok) {
        const json = await response.json();
        console.log(json.data);
        window.location = "view.html?postId=" + json.data.postId;
    } else if (response.status === 401) {
        window.location = "login.html";
    }
})

document.getElementById("title").addEventListener("focusout", (event) => {
    const title = document.getElementById("title");
    validations.title = title.value !== "";
    validate();
});

document.getElementById("content").addEventListener("focusout", (event) => {
    const content = document.getElementById("content");
    validations.content = content.value !== "";
    validate();
});

async function validate() {
    document.getElementById("post-submit").disabled = !(validations.title && validations.content);
}