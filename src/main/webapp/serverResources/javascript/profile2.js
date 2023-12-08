// common
const api ='http://localhost:8080/Feelbook';
const root= '/Feelbook';
let autho = document.getElementById('autho').value;

let userId = document.getElementById("user-id").value;

function handleSearch(){
    var searchInput = document.getElementById('seachInput').ariaValueMax;
    console.log('Search: ' + searchInput);
}
function handleLogout(){
    console.log('Logout botton clicked');
}
// Function to handle the edit icon click


// Function to handle the Edit icon click




// Function to submit the edit form
function submitEditForm() {
    var username = document.getElementById('username').value;
    var email = document.getElementById('email').value;
    var work = document.getElementById('work').value;

    console.log('Username:', username);
    console.log('Email:', email);
    console.log('Work: ', work)

    closeEditModal();
}

// Function to close the edit modal
function closeEditModal() {
    document.getElementById('editModal').style.display = 'none';
}

// get friend list
function getFriendList(user_id){
    fetch(`${api}/api/user/friend?user_id=${user_id}`,)
        .then(response => response.json())
        .then(data => {

            let htmlCode = '';
            var i = 1;
            data.forEach(friend => {
                htmlCode += `<div class="images-div">
                                <img id="image-${i}" src="${friend.avatar}">
                                <p>
                                    <a href="${root}/profile?user_id=${friend.user_id}">${friend.name}</a>
                                </p>
                            </div>`;
                i++;
            })
            document.getElementById('list-friend').innerHTML = htmlCode;
        })
        .catch(err => console.log(err));
}

function editPost(post_id, autho){
    if (autho == 'true'){
        return ` <i class="fa-solid fa-ellipsis-vertical"><img src="/Feelbook/server-resources/img/image/dot.svg" alt="">
                             <ul class="subnapEditPost" style="display: inline-block"> 
                             <li><a onclick="openEditPostModal(${post_id})">Edit</a></li>
                             <li><a href="${root}/post/delete?post_id=${post_id}">Delete</a></li>
                             </ul>
                             </i>`;
    }else return '';

}
function loadPostByUser(user_id){

    let postContainer = document.getElementById('posts');

    fetch(`${api}/api/post/user-post?user_id=${user_id}`)
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
                    ${editPost(object.post.post_id, autho)}
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

                Like(img, post_id,'unlike');
            }
        })
        .catch(err => console.log(err))
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

function Cancel(friend_id){
    let statusFriend = document.getElementById('friend-status');

    fetch(`${api}/api/friend/cancel?friend_id=${friend_id}`, {
        method: 'post',
    })
        .then(response => response.json())
        .then(data =>{

            if (data.message == "successfully"){
                statusFriend.innerHTML = `<button id="send" class="send-request" onclick="AddFriend(${friend_id})">Add friend</button>`;
            }else {
                alert("Something went wrong");
            }
        })
}

function Accept(friend_id){
    let statusFriend = document.getElementById('friend-status');

    fetch(`${api}/api/friend/accept?friend_id=${friend_id}`, {
        method: 'post',
    })
        .then(response => response.json())
        .then(data =>{

            if (data.message == "successfully"){
                statusFriend.innerHTML = `<button id="unfriend" class="send-request" onclick="UnFriend(${friend_id})">Unfriend</button>`;
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
                statusFriend.innerHTML = `<button id="send" class="send-request" onclick="AddFriend(${friend_id})">Add friend</button>`;
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
                statusFriend.innerHTML = `<button id="cancel" class="btn1" onclick="Cancel(${friend_id})">Cancel</button>`;
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
                statusFriend.innerHTML = `<button id="send" class="send-request" onclick="AddFriend(${friend_id})">Add friend</button>`;
            }else {
                alert("Something went wrong");
            }
        })
}


// comment modal
function openCommentModal(post_id) {
    document.getElementById('commentModal').style.display = 'flex';
    loadComments(post_id)
}


function closeCommentModal() {
    document.getElementById('commentModal').style.display = 'none';
}
// edit modal
function handleEditClick () {
    openEditInfoModal();
}
function openEditInfoModal(){
    document.getElementById('editInfoModal').style.display = 'flex';
}

function getEditPost(post_id) {
    let postContainer = document.getElementById('postEditing');
    let htmlCode ='';
    fetch(`${api}/api/post/edit?post_id=${post_id}`)
        .then(response => response.json())
        .then(data =>{
            let miliseconds = data.post.create_at;
            let currentTime = new Date().getTime();
            let hoursDistance = Math.floor((currentTime - miliseconds) / (1000 * 60 * 60));
            if (hoursDistance > 72) {
                hoursDistance = parseInt(hoursDistance / 24) + " days ago";
            } else {
                hoursDistance += " hours ago";
            }
            let fileType = ``;
            let mediaHTML = ``;
            if (data.post.post_img !== null && data.post.post_img !== undefined) {
                fileType = data.post.post_img.split('.').pop().toLowerCase();
                if (fileType == 'mp4') {

                    mediaHTML = `<video class="post-img" controls style="width: 100%;">
                                    <source src="${data.post.post_img}" type="video/mp4">
                                    Cant play this video.
                                  </video>`;
                } else {
                    mediaHTML = `<img class="post-img" src="${data.post.post_img}" alt="image" style="width: 100%;">`;
                }
            }
            let avatar = `<img src="${data.user.avatar}">`;
            if (data.user.avatar == null) avatar = '';

            htmlCode += `<div class="post-container">
                <div class="post-row">
                    <div class="user-profile Peniel">
                        ${avatar}
                        <div>
                            <a>${data.user.name}</a>
                            <br>
                            <span>${hoursDistance}</span>
                        </div>
                    </div>
                </div>
                <form action="${root}/post/edit?post_id=${data.post.post_id}" method="post">
                <input name ="content" class="post-text" value="${data.post.content}" style="width: 100%;">
                </p>
                ${mediaHTML}
                <div class="requests-btns">
                     <button type="submit" class="btn1">Confirm</button>
                </div>
                </form>`
            postContainer.innerHTML = htmlCode;
        })
}
function openEditPostModal(post_id) {
    document.getElementById('editPostModal').style.display = 'flex';
    getEditPost(post_id);
}
function closeEditPostModal() {
    document.getElementById('editPostModal').style.display = 'none';
}
function closeEditInfoModal() {
    document.getElementById('editInfoModal').style.display = 'none';
}
window.onload = function (){
    getFriendList(userId);
    loadPostByUser(userId);
}