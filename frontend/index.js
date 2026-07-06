//게시글 목록 조회
//스크롤이 맨 끝일 때마다 추가적인 요청

let page = 0;

async function loadPage(page) {
    const response = await fetch(`http://localhost:8080/posts?page=${page}`, {
        method: "GET",
    });

    if (response.ok) {
        const json = response.json();
        const posts = document.getElementById("posts");
        json.data().array.forEach(element => {
            posts.innerHTML = renderPost(element);
        });
    } else {

    }
};

document.addEventListener("DOMContentLoaded", loadPage(page));

//추가 구현 필요
document.addEventListener("스크롤이 밑에 닿을 때", (event) => {
    page++;
    loadPage(page);
})

function renderPost(post) {
    return `
    <div>
        <button>
            <div>
                <header>
                    <div class="post-title">
                        <h2>${post.title}</h2>
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
                    <img>
                    <p>${post.author}</p>
                </footer>
            </div>
        </button>
    </div>
    `;
}
          