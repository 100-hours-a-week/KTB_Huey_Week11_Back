const params = new URLSearchParams(document.location.search);
const postId = params.get("postId");

//게시글 상세 조회
document.addEventListener("DOMContentLoaded", async (event, postId) => {
    const response = await fetch(`http://localhost:8080/posts/view?postId=${postId}`, {
        method: "GET",
    })

    if (response.ok) {
        const json = await response.json();
        console.log("게시글 상세 조회. 1단계");
    } else {

    }
});

//게시글 댓글 조회
document.addEventListener("DOMContentLoaded", async (event, postId) => {
    const response = await fetch(`http://localhost:8080/posts/${postId}/comments`, {
        method: "GET",
    })

    if (response.ok) {
        const json = await response.json();
        console.log("게시글 댓글 조회. 1단계");
    } else {

    }
});

//좋아요
const like = document.getElementById("like");
like.addEventListener("onClick", async (event, postId) => {
    const response = await fetch(`http://localhost:8080/posts/${postId}/likes`, {
        method: "PATCH",
    })

    if (response.ok) {
        const json = await response.json();
        console.log("게시글 좋아요. 2단계");
    } else {

    }
});

//댓글 작성 이벤트 리스너
const commentForm = document.getElementById("comment-form");
commentForm.addEventListener("submit", async (event, postId) => {
    event.preventDefault();
    const formData = new FormData(commentForm);

    const response = await fetch(`http://localhost:8080/posts/${postId}/comments`, {
        method: "POST",
        body: formData,
    });

    if (response.ok) {
        const json = await response.json();
        console.log("댓글 작성. 3단계");
    } else {

    }
})

//댓글 수정 (댓글 리스트 구현 필요)
const updateComment = document.getElementById("update-comment");
updateComment.addEventListener("onClick", async (event, postId) => {
    const response = await fetch(`http://localhost:8080/posts/${postId}/comments`, {
        method: "PATCH",

    });

    if (response.ok) {
        const json = await response.json();
        console.log("댓글 수정. 3단계");
    } else {

    }
})

//댓글 삭제 (댓글 리스트 구현 필요)
const deleteComment = document.getElementById("delete-comment");
deleteComment.addEventListener("onclick", async (event, postId) => {
    const response = await fetch(`http://localhost:8080/posts/${postId}/comments`, {
        method: "DELETE",
    });

    if (response.ok) {
        const json = await response.json();
        console.log("댓글 삭제. 3단계");
    } else {

    }
})

//게시글 삭제 (모달 구현 필요)
const deletePost = document.getElementById("delete-post");
deletePost.addEventListener("onClick", async (event, postId) => {
    const response = await fetch(`http://localhost:8080/posts/${postId}`, {
        method: "DELETE",
    });

    if (response.ok) {
        const json = await response.json();
        console.log("게시글 삭제. 2단계");
    } else {

    }
})

//게시글 신고 (구현 후순위)
const reportPost = document.getElementById("report-post");
reportPost.addEventListener("onClick", async (event, postId) => {
    const response = await fetch(`http://localhost:8080/posts/${postId}/reports`, {
        method: "PATCH",
    });

    if (response.ok) {
        const json = await response.json();
        console.log("게시글 신고. 2단계");
    } else {

    }
})