let btnSearch = document.querySelector('.btn-search');
let keyword = '';
let numOfBtn = document.getElementById('get-btn');
let currPage = 1;
let post = document.getElementById('posts')

function fetchData(key,page) {

    if(key === null)
        key = '';

    $.ajax({
        url: `http://localhost:8080/Feelbook/api/post/post_per_page?key=${key}&page=${page}`,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.log(data)
            let htmlCode = '';
            data.forEach(object => {

                let likes = getLikesOfPost(object.post.post_id)
                    .then(result => {return result})
                    .then(like => {

                        let miliseconds = object.post.create_at;

                        let currentTime = new Date().getTime();
                        let hoursDistance = Math.floor((currentTime - miliseconds)/(1000*60*60));
                        let fileType = ``;
                        let mediaHTML = ``;
                        if (object.post.post_img !== null && object.post.post_img !== undefined) {
                            fileType = object.post.post_img.split('.').pop().toLowerCase();
                            if (fileType === 'mp4') {

                                mediaHTML = `<video class="img" controls>
                                    <source src="${object.post.post_img}" type="video/mp4">
                                    Cant play this video.
                                  </video>`;
                            } else {
                                mediaHTML = `<img class="img" src="${object.post.post_img}" alt="image">`;
                            }
                        }

                       const markup = `<div>
                           <a href = "/Feelbook/profile?user_id=${object.user.user_id}">${object.user.name}</a>
                           <br>
                           <span>${hoursDistance} hours ago</span>
                           <br>
                           <span>${object.post.content}</span>
                           <br>
                           ${mediaHTML}
                           <br>
                           
                           <button class="like" value="${like}" onclick="Like(this, ${object.post.post_id})">${like} Likes</button>
                           
                           <button class="comment" value="${object.post.comments}">${object.post.comments} Comments</button>
                           <hr>
                           </div>`;

                        post.insertAdjacentHTML('beforeend',markup);
                    })
            });
        },
        error: function(xhr, status, error) {
            console.error('AJAX Error:', error);
        }
    });
}

async function getLikesOfPost(post_id){
    let result = 0;
    await fetch(`http://localhost:8080/Feelbook/post-like/get-num-likes?post-id=${post_id}`)
        .then(response => response.json())
        .then(data => {

            result = data.likes;
        })
        .catch(err => console.error(err));
    return result;

}
let btnLike = document.querySelector('.like');
function Like (btn, post_id){
    fetch(`http://localhost:8080/Feelbook/post-like/like?post_id=${post_id}`)
        .then(response => response.json())
        .then(date => {
            if (date.message === "successfully"){

                btn.value = parseInt(btn.value) + 1;
                btn.textContent = btn.value + ' Likes';
            }else{
                alert('Something went wrong');
            }
        })
        .catch(err => console.log(err))
}

function Search(){
    keyword = document.querySelector('input[name="key"]').value;
    console.log(keyword);
    currPage = 1
    fetchData(keyword, currPage);
}
let pagingButtons = document.querySelectorAll('.paging');
pagingButtons.forEach(function(button) {
    button.addEventListener('click', function() {
        currPage++;
        fetchData(keyword, currPage);
    });
});
// Load initial data when the page loads
window.onload = function() {
    fetchData(keyword, 1);
};

window.onscroll = function(ev) {
    if ((window.innerHeight + Math.round(window.scrollY)) >= document.body.offsetHeight) {
        document.querySelector(".paging").click();
    }
};