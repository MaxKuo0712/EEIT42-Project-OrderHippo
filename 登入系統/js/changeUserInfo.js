
setUserInfo();






function setUserInfo() {
    let USER_ID = "vLehfWsCmfNDybZgrxiEzBSQCwt1";

    fetch(`http://localhost:8080/api/users?userid=${USER_ID}`, {
        method: "GET"
    }).then(res => {
        return res.json();
    }).then(result => {
        console.log(result[0]);

        let queryResult = result[0];

        let USER_NAME  = queryResult.USER_NAME ;
        let USER_GENDER = queryResult.USER_GENDER;
        let USER_PHONE = queryResult.USER_PHONE;
        let USER_MAIL = queryResult.USER_MAIL;
        let USER_BIRTH = queryResult.USER_BIRTH;
        let USER_AGE = queryResult.USER_AGE;
        let USER_ADDRESS = queryResult.USER_ADDRESS;

        document.getElementById("name").value = USER_NAME;
        document.getElementById("gender").value = USER_GENDER;
        document.getElementById("phone").value = USER_PHONE;
        document.getElementById("email").value = USER_MAIL;
        document.getElementById("birth").value = USER_BIRTH;
        document.getElementById("age").value = USER_AGE;
        document.getElementById("address").value = USER_ADDRESS;
    })
}