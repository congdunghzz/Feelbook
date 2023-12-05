// common

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
function openEditModal() {
    document.getElementById('editModal').style.display = 'block';
}

// Function to handle the share icon click
function handleShareClick() {
    console.log('Share icon clicked');
}

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
    fetch(`http://localhost:8080/Feelbook/api/user/friend?user_id=${user_id}`,)
        .then(response => response.json())
        .then(data => {
            console.log(data)
            let htmlCode = '';
            var i = 1;
            data.forEach(friend => {
                htmlCode += `<div class="images-div">
                                <img id="image-${i}" src="${friend.avatar}">
                                <p>
                                    <a href="/Feelbook/profile?user_id=${friend.user_id}">${friend.name}</a>
                                </p>
                            </div>`;
                i++;
            })
            document.getElementById('list-friend').innerHTML = htmlCode;
        })
        .catch(err => console.log(err));
}
window.onload = function (){
    getFriendList(userId);
}