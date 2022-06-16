
// const token = '22e7ba553d6239bc5de2bf1520c9187b611a760d'; // 填入 token

// const actionBtn = document.getElementById("myBtn"); // 送出按鈕
// const uploadInput = document.getElementById("upload"); // upload上傳物件的地方
// const showImg = document.getElementById("showImg");
// const url = document.getElementById("url");

// // 建立file class物件
// class file {
//     // 建構式
//     constructor() {
//         this.uploadFile = null;
//         this.fileName = null;
//         this.fileSize = null;
//         this.fileThumbnail = null;
//         this.fileTitle = null;
//         this.fileDesc = null;
//     }

//     // setter
//     setuploadFile(uploadFile) {
//         this.uploadFile = uploadFile;
//     }
//     setfileName(fileName) {
//         this.fileName = fileName;
//     }
//     setfileSize(fileSize) {
//         this.fileSize = fileSize;
//     }
//     setfileThumbnail(fileThumbnail) {
//         this.fileThumbnail = fileThumbnail;
//     }
//     setfileTitle(fileTitle) {
//         this.fileTitle = fileTitle;
//     }
//     setfileDesc(fileDesc) {
//         this.fileDesc = fileDesc;
//     }

//     // getter
//     getuploadFile() {
//         return this.uploadFile;
//     }
//     getfileName() {
//         return this.fileName;
//     }
//     getfileSize() {
//         return this.fileSize;
//     }
//     getfileThumbnail() {
//         return this.fileThumbnail;
//     }
//     getfileTitle() {
//         return this.fileTitle;
//     }
//     getfileDesc() {
//         return this.fileDesc;
//     }
// }

// function addImg(imgURL) {
//     showImg.src = imgURL;
//     url.innerText = '圖檔網址：' + imgURL;
// }

// // 上傳的function
// function submit() {
//     // api
//     let settings = {
//         async: true,
//         crossDomain: true,
//         processData: false,
//         contentType: false,
//         type: 'POST',
//         url: 'https://api.imgur.com/3/image',
//         headers: {
//             Authorization: 'Bearer ' + token
//         },
//         mimeType: 'multipart/form-data'
//     };

//     // data傳遞
//     let form = new FormData();
//     form.append('image', inputFile.getuploadFile());
//     form.append('title', inputFile.getfileTitle());
//     form.append('description', inputFile.getfileDesc());

//     settings.data = form;

//     // 傳遞資料
//     $.ajax(settings).done(function (res) {
//         const imgURL = JSON.parse(res).data.link
//         addImg(imgURL);
//         console.log(JSON.parse(res)); // 可以看見上傳成功後回傳的URL
//         console.log(JSON.parse(res).data.link)
//         alert('上傳完成');
//     });
//     document.getElementById("preview-img").src="";
// }

// // 建議物件
// const inputFile = new file();

// // input:file的監聽器
// uploadInput.addEventListener("change", (e) => {
//     inputFile.setuploadFile(uploadInput.files[0]); // input type="file" 的值
//     inputFile.setfileName(inputFile.uploadFile.name); // input的圖檔名稱
//     inputFile.setfileSize(Math.floor(inputFile.uploadFile.size * 0.001) + 'KB'); // input的圖片大小
//     inputFile.setfileThumbnail(window.URL.createObjectURL(inputFile.uploadFile)); // input的圖片縮圖
//     inputFile.setfileTitle(inputFile.uploadFile.name); // 預設 input 的圖檔名稱為圖片上傳時的圖片標題
//     inputFile.setfileDesc(inputFile.uploadFile.name); // 圖片描述
//     var file = uploadInput.files[0],imageType = /^image\//,reader = '';
//     // 檔案是否為圖片
//     if (!imageType.test(file.type)) {
//         alert("請選擇圖片！");
//         return;
//     }
//     // 判斷是否支援FileReader  // IE9及以下不支援FileReader
//     if (window.FileReader) {
//         reader = new FileReader();
//     }    else {
//         alert("您的瀏覽器不支援圖片預覽功能，如需該功能請升級您的瀏覽器！");
//         return;
//     }
//      // 讀取完成    
//     reader.onload = function (event) {
//         // 獲取圖片DOM
//         var img = document.getElementById("preview-img");
//         // 圖片路徑設定為讀取的圖片    
//         img.src = event.target.result;
//     };
//     reader.readAsDataURL(file);
//     document.getElementById("showImg").src=""
// });

// Botton的監聽器
// actionBtn.addEventListener("click", () => {
//     submit();
// });

