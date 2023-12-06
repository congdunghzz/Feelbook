let liked = false;
let likeCount = 316;
let likeImage = document.getElementById('likeImage1');
let likeCountElement = document.getElementById('likeCount1');

function Like(img, post_id, isLike) {
    let count = 1;
    if(isLike == 'unlike') count = -1;
    fetch(`http://localhost:8080/Feelbook/post-like/${isLike}?post_id=${post_id}`)
        .then(response => response.json())
        .then(date => {
            if (date.message === "successfully") {
                img.nextElementSibling.textContent = parseInt(img.nextElementSibling.textContent) + count;
                return;

            } else {
                console.log('sẽ unlike');
                Like(img, post_id,'unlike');
            }
        })
        .catch(err => console.log(err))
}


function loadRequestList() {
    let container = document.getElementById('request-list');
    fetch('http://localhost:8080/Feelbook/api/user/requestFriend')
        .then(response => response.json())
        .then(data => {
            var htmlCode = '';
            data.forEach(item => {


                htmlCode += `<div class="requests">
                    <div class="left-side">
                        <img src="${item.avatar}">
                    </div>
                    <div class="right-side">
                        <div class="name">
                            <h4><a href="/Feelbook/profile?user_id=${item.user_id}">${item.name}</a></h4>
                        </div>
                        <div class="overlap-img">
                            <small>${item.user_email}</small>
                        </div>
                        <div class="requests-btns">
                            <button type="submit" class="btn1">Accept</button>
                            <button type="submit" class="btn2">Reject</button>
                        </div>
                    </div>
                </div>`;
            })
            container.innerHTML = htmlCode;
        })
}

let keyword = '';
let currentPage = 1;

function loadPosts(key, page) {
    let postContainer = document.getElementById('posts');
    if (key === null)
        key = '';
    fetch(`http://localhost:8080/Feelbook/api/post/post_per_page?key=${key}&page=${page}`)
        .then(response => response.json())
        .then(data => {
            let htmlCode = '';
            data.forEach(object => {

                let miliseconds = object.post.create_at;
                let currentTime = new Date().getTime();
                let hoursDistance = Math.floor((currentTime - miliseconds) / (1000 * 60 * 60));
                if (hoursDistance > 72) {
                    hoursDistance = parseInt(hoursDistance / 24) + " days ago";
                } else {
                    hoursDistance += " hours ago";
                }
                let fileType = ``;
                let mediaHTML = ``;
                if (object.post.post_img !== null && object.post.post_img !== undefined) {
                    fileType = object.post.post_img.split('.').pop().toLowerCase();
                    if (fileType == 'mp4') {

                        mediaHTML = `<video class="post-img" controls>
                                    <source src="${object.post.post_img}" type="video/mp4">
                                    Cant play this video.
                                  </video>`;
                    } else {
                        mediaHTML = `<img class="post-img" src="${object.post.post_img}" alt="image">`;
                    }
                }
                let avatar = `<img src="${object.user.avatar}">`;
                if (object.user.avatar == null) avatar = '';

                htmlCode += `<div class="post-container">
                <div class="post-row">
                    <div class="user-profile Peniel">
                        ${avatar}
                        <div>
                            <a href="http://localhost:8080/Feelbook/profile?user_id=${object.user.user_id}">${object.user.name}</a>
                            <br>
                            <span>${hoursDistance}</span>
                        </div>
                    </div>
                </div>

                <p class="post-text">${object.post.content}
                </p>
                ${mediaHTML}

                <div class="post-row">
                    <div class="activity-icons">
                        <div>
                            <div id="likeButton">
                                <img id="likeImage" src="/Feelbook/server-resources/img/image/like-gray.png" onclick="Like(this, ${object.post.post_id},'like')">                   
                                <span id="likeCount">${object.post.likes}</span>
                            </div>
                        </div>
                        <div id="commentButton">
                            <img src="/Feelbook/server-resources/img/image/comments.png"> ${object.post.comments}
                        </div>
                    </div>
                </div>
                <div>
                </div>
            </div>`
            })
            postContainer.insertAdjacentHTML('beforeend', htmlCode);
        })
}

function Search() {
    keyword = document.querySelector('input[name="key"]').value;
    console.log(keyword);
    currPage = 1
    loadPosts(keyword, currPage);
}

// Load initial data when the page loads
window.onload = function () {
    fetchData(keyword, 1);
};

window.onscroll = function (ev) {
    if ((window.innerHeight + Math.round(window.scrollY)) >= document.body.offsetHeight * 0.99) {
        currentPage++;
        console.log('Scrolling...' + currentPage);

        loadPosts(keyword, currentPage);
    }
};
window.onload = function () {
    loadRequestList();
    loadPosts(keyword, currentPage);
}

