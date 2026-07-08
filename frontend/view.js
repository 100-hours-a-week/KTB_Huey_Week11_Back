const params = new URLSearchParams(document.location.search);
const postId = params.get("postId");

const commentContent = document.getElementById("comment-content");
commentContent.addEventListener("input", async (event) => {
    console.log("event");
    let submit = document.getElementById("comment-submit");
    if (commentContent.value === "") {
        console.log("empty");
        submit.disabled = true;
    } else {
        console.log("not empty");
        submit.disabled = false;
    }
})

//게시글 상세 조회
document.addEventListener("DOMContentLoaded", async (event) => {
    const response = await fetch(`http://localhost:8080/posts/view?postId=${postId}`, {
        method: "GET",
        credentials: "include",
    })

    if (response.ok) {
        const json = await response.json();
        const data = await json.data;
        
        localStorage.setItem("postId", postId);
        localStorage.setItem("postTitle", data.title);
        localStorage.setItem("postContent", data.content);
        localStorage.setItem("postImageUrl", data.imageUrl);

        sessionStorage.setItem("likes", data.likes);
        sessionStorage.setItem("views", data.views);
        sessionStorage.setItem("comments", data.comments);

        renderPost(data);  
        renderPostMetadata();
    } else if (response.status === 401) {
        window.location = "login.html";
    }
});

function parseImageUrl(url) {
    return "http://localhost:8080" + url;
}

function renderPost(data) {
    document.getElementById("title").innerText = data.title;
    document.getElementById("author").innerText = data.userNickname;
    document.getElementById("author-profile-image").src = parseImageUrl(data.userProfileImageUrl);
    document.getElementById("posted-time").innerText = data.postedTime;
    document.getElementById("content").innerText = data.content;
    document.getElementById("image").src = parseImageUrl(data.imageUrl);
}

function renderPostMetadata() {
    document.getElementById("likes").innerText = numberToK(sessionStorage.getItem("likes"));
    document.getElementById("views").innerText = numberToK(sessionStorage.getItem("views"));
    document.getElementById("comments").innerText = numberToK(sessionStorage.getItem("comments"));
}

function numberToK(number) {
    if (number < 1000) return number;
    else {
        return (number / 1000) + "K";
    }
}

const postDelete = document.getElementById("post-delete");
postDelete.addEventListener("click", (event) => {
    const postDeleteModal = document.getElementById("post-delete-modal");
    postDeleteModal.classList.remove("hidden");

    document.body.style.overflow = "hidden";
})

document.getElementById("post-delete-modal-cancel").addEventListener("click", (event) => {
    const postDeleteModal = document.getElementById("post-delete-modal");
    postDeleteModal.classList.add("hidden");
    document.body.style.overflow = "";
})

document.getElementById("comment-delete-modal-cancel").addEventListener("click", (event) => {
    const commentDeleteModal = document.getElementById("comment-delete-modal");
    commentDeleteModal.classList.add("hidden");
})

//게시글 댓글 조회
document.addEventListener("DOMContentLoaded", async (event) => {
    const response = await fetch(`http://localhost:8080/posts/${postId}/comments`, {
        method: "GET",
        credentials: "include",
    })

    if (response.ok) {
        const json = await response.json();
        const data = json.data;

        for (const comment of data) {
            createComment(comment);
        }

    } else {

    }
});

function renderComment(data) {
    return `
        <article id=${data.commentId}>
            <div>
                <header class="vert between">
                    <div class="vert">
                        <img class="user-profile-small" src=${parseImageUrl(data.userProfileImageUrl)}>
                        <p class="author">${data.userNickname}</p>
                        <time>${data.postedTime}</time>
                    </div>
                    <div class="vert">
                        <button class="comment-update">수정</button>
                        <button class="comment-delete-modal">삭제</button>
                    </div>
                </header>

                <p class="comment-content">
                    ${data.content}
                </p>
            </div>
        </article>
    `;
}

function createComment(data) {
    const comments = document.getElementById("comment-list");
    const card = document.createElement("div");
    card.id = "comment_" + data.commentId;
    card.innerHTML = renderComment(data);
    
    card.querySelector(".comment-update").addEventListener("click", (event) => {
        const commentForm = document.getElementById("comment-form");
        const commentUpdateForm = document.getElementById("comment-update-form");
        commentForm.classList.add("hidden");
        commentUpdateForm.classList.remove("hidden");
        document.getElementById("update-comment-content").value = card.querySelector(".comment-content").innerText;
        sessionStorage.setItem("comment_id", data.commentId);
    });

    card.querySelector(".comment-delete-modal").addEventListener("click", (event) => {
        const commentDeleteModal = document.getElementById("comment-delete-modal");
        commentDeleteModal.classList.remove("hidden");
    });

    comments.appendChild(card);
}

//댓글 수정

const commentUpdate = document.getElementById("comment-update-form");
commentUpdate.addEventListener("submit", async (event) => {
    event.preventDefault();
    const formData = new FormData(commentUpdate);
    const commentId = sessionStorage.getItem("comment_id");

    const response = await fetch(`http://localhost:8080/posts/${postId}/comments?commentId=${commentId}`, {
        method: "PATCH",
        body: formData,
        credentials: "include",
    });

    if (response.ok) {
        const json = await response.json();

        const commentList = document.getElementById("comment-list");
        const comment = commentList.querySelector("#comment_" + commentId);
        comment.querySelector(".comment-content").innerText = formData.get("content");

        const commentForm = document.getElementById("comment-form");
        const commentUpdateForm = document.getElementById("comment-update-form");
        commentForm.classList.remove("hidden");
        commentUpdateForm.classList.add("hidden");
    } else {

    }
})

//댓글 삭제
document.getElementById("delete-comment").addEventListener("click", async (event) => {
    const commentId = sessionStorage.getItem("comment_id");
    const response = await fetch(`http://localhost:8080/posts/${postId}/comments?commentId=${commentId}`, {
        method: "DELETE",
        credentials: "include",
    });

    if (response.ok) {
        const json = await response.json();
        
        const commentList = document.getElementById("comment-list");
        const comment = commentList.querySelector("#comment_" + commentId);
        comment.remove();

        const commentDeleteModal = document.getElementById("comment-delete-modal");
        commentDeleteModal.classList.add("hidden");

        document.body.style.overflow = "";
    } else {

    }
})


//좋아요
const like = document.getElementById("like");
like.addEventListener("click", async (event) => {
    const response = await fetch(`http://localhost:8080/posts/${postId}/likes`, {
        method: "PATCH",
        credentials: "include",
    })

    if (response.ok) {
        const json = await response.json();

        let likes = parseInt(sessionStorage.getItem("likes"));
        sessionStorage.setItem("likes", likes + 1);
        renderPostMetadata();
    } else {

    }
});

//댓글 작성 이벤트 리스너
const commentForm = document.getElementById("comment-form");
commentForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const formData = new FormData(commentForm);

    const response = await fetch(`http://localhost:8080/posts/${postId}/comments`, {
        method: "POST",
        body: formData,
        credentials: "include",
    });

    if (response.ok) {
        const json = await response.json();
        const data = json.data;
        createComment(data);

        let commentCount = parseInt(sessionStorage.getItem("comments"));
        sessionStorage.setItem("comments", commentCount + 1);
        renderPostMetadata();

        document.getElementById("comment-content").value = null;
    } else {

    }
})

//게시글 삭제 (모달 구현 필요)
const deletePost = document.getElementById("delete-post");
deletePost.addEventListener("click", async (event) => {

    const response = await fetch(`http://localhost:8080/posts/${postId}`, {
        method: "DELETE",
        credentials: "include",
    });

    if (response.ok) {
        const json = await response.json();
        window.location = "index.html";
    } else {

    }
})

//게시글 신고 (구현 후순위)
/*
const reportPost = document.getElementById("report-post");
reportPost.addEventListener("click", async (event, postId) => {
    const response = await fetch(`http://localhost:8080/posts/${postId}/reports`, {
        method: "PATCH",
        credentials: "include",
    });

    if (response.ok) {
        const json = await response.json();
        console.log("게시글 신고. 2단계");
    } else {

    }
})
    */