const api ='http://localhost:8080/Feelbook';
const root= '/Feelbook';

function Like(img, post_id, isLike) {
    let count = 1;
    if(isLike == 'unlike') count = -1;
    fetch(`${api}/post-like/${isLike}?post_id=${post_id}`)
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

function submitComment(){
    let numComment = document.getElementById('commentButton');
    const content = document.getElementById('commentText').value;
    const post_id = document.getElementById('hiredPostId').value;
    let commentContainer = document.getElementById('comments');
    var formData = new FormData();

    formData.append("content", content) ;
    formData.append("post_id", post_id);

    console.log(formData.content);
    fetch(`${api}/api/comment/create`, {
        method: 'post',
        body: formData,
    })
        .then(response => response.json())
        .then(data => {
            let miliseconds = data.comment.create_at;
            let currentTime = new Date().getTime();
            let hoursDistance = Math.floor((currentTime - miliseconds) / (1000 * 60 * 60));
            if (hoursDistance > 72) {
                hoursDistance = parseInt(hoursDistance / 24) + " days ago";
            } else {
                hoursDistance += " hours ago";
            }


            console.log(data);
            htmlCode = `<div class="comments-container" id="commentsContainer">
                    <!-- Comments will be added here -->
                    <div class="profile-img">
                        <img id="Avatar" src="${data.user.avatar}">
                        <div class="wrapper-comment">
                            <div class="content">
                                <a href="${root}/profile?user_id=${data.user.user_id}">${data.user.name}</a>
                                <span style="font-size: 13px">${hoursDistance}</span>                                
                                <p class="comment-content">${data.comment.content}</p>
                            </div>
                        </div>

                    </div>
                </div>`;
            commentContainer.insertAdjacentHTML('beforeend', htmlCode);
        })
}


function loadRequestList() {
    let container = document.getElementById('request-list');
    fetch(`${api}/api/user/requestFriend`)
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
                            <h4><a href="${root}/profile?user_id=${item.user_id}">${item.name}</a></h4>
                        </div>
                        <div class="overlap-img">
                            <small>${item.user_email}</small>
                        </div>
                        <div class="requests-btns" id="friend-status">
                            <button type="submit" class="btn1" onclick="Accept(${item.user_id})">Accept</button>
                            <button type="submit" class="btn2" onclick="Reject(${item.user_id})">Reject</button>
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
    fetch(`${api}/api/post/post_per_page?key=${key}&page=${page}`)
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
                            <a href="${api}/profile?user_id=${object.user.user_id}">${object.user.name}</a>
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
                                <img id="likeImage" src="${root}/server-resources/img/image/like-gray.png" onclick="Like(this, ${object.post.post_id},'like')">                   
                                <span id="likeCount">${object.post.likes}</span>
                            </div>
                        </div>
                        <div id="commentButton" onclick="openCommentModal(${object.post.post_id})">
                            <img src="${root}/server-resources/img/image/comments.png"> ${object.post.comments}
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


function loadComments(post_id){
    let commentContainer = document.getElementById('comments');
    fetch(`${api}/api/comment?post_id=${post_id}`)
        .then(response => response.json())
        .then(data => {
            var htmlCode = '';
            data.forEach(item => {
                let miliseconds = item.comment.create_at;
                let currentTime = new Date().getTime();
                let hoursDistance = Math.floor((currentTime - miliseconds) / (1000 * 60 * 60));
                if (hoursDistance > 72) {
                    hoursDistance = parseInt(hoursDistance / 24) + " days ago";
                } else {
                    hoursDistance += " hours ago";
                }
                htmlCode += `<div class="comments-container" id="commentsContainer">
                    <!-- Comments will be added here -->
                    <div class="profile-img">
                        <img id="Avatar" src="${item.user.avatar}">
                        <div class="wrapper-comment">
                            <div class="content">
                                <a href="${root}/profile?user_id=${item.user.user_id}">${item.user.name}</a>
                                <span style="font-size: 13px">${hoursDistance}</span>                                
                                <p class="comment-content">${item.comment.content}</p>
                            </div>
                        </div>

                    </div>
                </div>`;
            })
            htmlCode+= `<button id="hiredPostId" style="display: none" value="${post_id}"></button>`;
            commentContainer.innerHTML = htmlCode;
        })
}

function openCommentModal(post_id) {
    document.getElementById('commentModal').style.display = 'flex';
    loadComments(post_id);
}


function closeCommentModal() {
    document.getElementById('commentModal').style.display = 'none';
}
function Search() {
    keyword = document.querySelector('input[name="key"]').value;
    document.getElementById('posts').innerHTML ='';
    currentPage = 1;
    loadPosts(keyword, currentPage);
}

// Load initial data when the page loads
window.onload = function () {
    fetchData(keyword, 1);
};


 // Friend ship action
function Accept(friend_id){
    let statusFriend = document.getElementById('friend-status');

    fetch(`${api}/api/friend/accept?friend_id=${friend_id}`, {
        method: 'post',
    })
        .then(response => response.json())
        .then(data =>{

            if (data.message == "successfully"){
                statusFriend.innerHTML = `<button id="unfriend" class="btn1"">Accepted</button>`;
            }else {
                alert("Something went wrong");
            }
        })
}

function UnFriend(friend_id){
    let statusFriend = document.getElementById('friend-status');

    fetch(`${api}/api/friend/unFriend?friend_id=${friend_id}`, {
        method: 'post',
    })
        .then(response => response.json())
        .then(data =>{

            if (data.message == "successfully"){
                statusFriend.innerHTML = `<button id="send" class="btn1" onclick="AddFriend(${friend_id})">Add friend</button>`;
            }else {
                alert("Something went wrong");
            }
        })
}

function AddFriend(friend_id){
    let statusFriend = document.getElementById('friend-status');

    fetch(`${api}/api/friend/send?friend_id=${friend_id}`, {
        method: 'post',
    })
        .then(response => response.json())
        .then(data =>{

            if (data.message == "successfully"){
                statusFriend.innerHTML = `<button id="cancel" class="btn2" onclick="Cancel(${friend_id})">Cancel</button>`;
            }else {
                alert("Something went wrong");
            }
        })
}

function Reject(friend_id){
    let statusFriend = document.getElementById('friend-status');

    fetch(`${api}/api/friend/reject?friend_id=${friend_id}`, {
        method: 'post',
    })
        .then(response => response.json())
        .then(data =>{

            if (data.message == "successfully"){
                statusFriend.innerHTML = `<button id="send" class="btn2"">Have rejected</button>`;
            }else {
                alert("Something went wrong");
            }
        })
}

function Cancel(friend_id){
    let statusFriend = document.getElementById('friend-status');

    fetch(`${api}/api/friend/cancel?friend_id=${friend_id}`, {
        method: 'post',
    })
        .then(response => response.json())
        .then(data =>{

            if (data.message == "successfully"){
                statusFriend.innerHTML = `<button id="send" class="btn2"">Have canceled</button>`;
            }else {
                alert("Something went wrong");
            }
        })
}

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

