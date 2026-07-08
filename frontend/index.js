//게시글 목록 조회
//스크롤이 맨 끝일 때마다 추가적인 요청

let page = 0;

async function loadPage(page) {
    const response = await fetch(`http://localhost:8080/posts?page=${page}`, {
        method: "GET",
        credentials: "include",
    });

    if (response.ok) {
        const json = await response.json();
        const data = json.data;
        const posts = document.getElementById("posts");
        for (post of data.posts) {
            const card = document.createElement("div");
            card.id = "post_" + post.postId;
            card.innerHTML = renderPost(post);
            posts.appendChild(card);
        }
    } else {

    }
};

document.addEventListener("DOMContentLoaded", loadPage(page));

window.addEventListener("scroll", () => {
    const threshold = 100;

    if (
        window.innerHeight + window.scrollY >=
        document.documentElement.scrollHeight - threshold
    ) {
        page++;
        loadPage(page);
    }
});

function format(number) {
    if (number >= 1000) {
        return (number / 1000) + "k";
    } else {
        return number;
    }
}

function parseImageUrl(url) {
    return "http://localhost:8080" + url;
}

function renderPost(post) {
    return `
    <div id=${post.postId}>
        <a href="view.html?postId=${post.postId}">
            <div>
                <header>
                    <div class="post-title">
                        <h2>${post.title.substr(0, 26)}</h2>
                    </div>
                    <div class="vert between">
                        <div class="vert">
                            <p>좋아요 ${post.likes}</p>
                            <p>댓글 ${post.comments}</p>
                            <p>조회수 ${post.views}</p>
                        </div>
                        <div>
                            <time>${post.postedTime}</time>
                        </div>
                    </div>
                </header>

                <footer class="vert">
                    <img class="user-profile-small" src=${parseImageUrl(post.userProfileImageUrl)}>
                    <p>${post.userNickname}</p>
                </footer>
            </div>
        </a >
    </div>
    `;
}
          