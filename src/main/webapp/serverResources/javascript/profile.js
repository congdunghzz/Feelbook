
let autho = document.getElementById('autho').value;

let userId = document.getElementById("user-id").value;

let btn_submitAvatar = document.getElementById('btn-submitAvatar');
function fetchData(user_id) {
    console.log(user_id)

    fetch(`http://localhost:8080/Feelbook/api/post/user-post?user_id=${user_id}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            let htmlCode = '';
            if (autho == "true"){
                data.forEach(object => {
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
                                    Your browser does not support the video tag.
                                  </video>`;
                        } else {
                            mediaHTML = `<img class="img" src="${object.post.post_img}" alt="image">`;
                        }
                    }
                    htmlCode += `<div>
                           <a href = "/Feelbook/profile?user_id=${object.user.user_id}">${object.user.name}</a>
                           <br>
                           <span>${hoursDistance} hours ago</span>
                           <br>
                           <span>${object.post.content}</span>
                           <br>
                           ${mediaHTML}
                           <br>
                           <button class="like">${object.post.likes} Likes</button>
                           <button class="comment">${object.post.comments} Comments</button>
                           <form action="/Feelbook/post/delete?post_id=${object.post.post_id}" method="post">
                                <button class="delete"> Delete</button>
                           </form>
                                <button class="edit"> <a href="/Feelbook/post/edit?post_id=${object.post.post_id}">Edit post</a></button>                           
                           <hr>
                           </div>`;
                })
            }else{
                data.forEach(object => {
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
                                    Your browser does not support the video tag.
                                  </video>`;
                        } else {
                            mediaHTML = `<img class="img" src="${object.post.post_img}" alt="image">`;
                        }
                    }
                    htmlCode += `<div>
                           <a href = "/Feelbook/profile?user_id=${object.user.user_id}">${object.user.name}</a>
                           <br>
                           <span>${hoursDistance} hours ago</span>
                           <br>
                           <span>${object.post.content}</span>
                           <br>
                           ${mediaHTML}
                           <br>
                           <button class="like">${object.post.likes} Likes</button>
                           <button class="comment">${object.post.comments} Comments</button>
                           <hr>
                           </div>`;
                })
            }

            document.getElementById('posts').innerHTML = htmlCode;

        })
        .catch(err => console.log(err));

}


window.onload = function () {
    fetchData( userId);
};