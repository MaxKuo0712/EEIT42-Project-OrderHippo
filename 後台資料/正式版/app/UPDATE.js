// let postName = 'localhost:8080';
let postName = 'eeit42-order-hippo.herokuapp.com';

// let newToken = localStorage.getItem('storeToken');
localStorage.getItem('storeToken') == null ? function () { Swal.fire("請先登入"); window.location.href = "login.html" }() : newToken = localStorage.getItem('storeToken');
let storeId = JSON.parse(localStorage.getItem('storeinfo')).STORE_ID;
let localhost = 8080;
/*---------------嘉彬------------*/
$('img[src="./img/group1.png"]').on('click', function () {
    $('#padleft .active').removeClass('active');
    $('#pills-tabContent>div').removeClass('active');
    $('#pills-tabContent>div').removeClass('show');
    $('a[data-bs-target="#pills-menu"]').attr('class', 'nav-link text-white bg-transparent text-start');
    $('#pills-menu').attr('class', 'show active container tab-pane ');
    runmenu();
});

/**圖檔--------------------------------------------------------------------------------------------------------------------------- */

const tokenimage = '22e7ba553d6239bc5de2bf1520c9187b611a760d'; // 填入 token

const actionBtn = document.getElementById("saveImageBtn"); // 送出按鈕
const uploadInput = document.getElementById("uploadImage"); // upload上傳物件的地方
const showImg = document.getElementById("showImage");
const url = document.getElementById("makerUrl");

// 建立file class物件
class file {
    // 建構式
    constructor() {
        this.uploadFile = null;
        this.fileName = null;
        this.fileSize = null;
        this.fileThumbnail = null;
        this.fileTitle = null;
        this.fileDesc = null;
    }

    // setter
    setuploadFile(uploadFile) {
        this.uploadFile = uploadFile;
    }
    setfileName(fileName) {
        this.fileName = fileName;
    }
    setfileSize(fileSize) {
        this.fileSize = fileSize;
    }
    setfileThumbnail(fileThumbnail) {
        this.fileThumbnail = fileThumbnail;
    }
    setfileTitle(fileTitle) {
        this.fileTitle = fileTitle;
    }
    setfileDesc(fileDesc) {
        this.fileDesc = fileDesc;
    }

    // getter
    getuploadFile() {
        return this.uploadFile;
    }
    getfileName() {
        return this.fileName;
    }
    getfileSize() {
        return this.fileSize;
    }
    getfileThumbnail() {
        return this.fileThumbnail;
    }
    getfileTitle() {
        return this.fileTitle;
    }
    getfileDesc() {
        return this.fileDesc;
    }
}

function addImg(imgURL) {
    // document.getElementById("preview-image").src = imgURL;
    showImg.src = imgURL;
    url.innerText = imgURL;
}

// 上傳的function
function submitPic() {
    // api
    let settings = {
        async: true,
        crossDomain: true,
        processData: false,
        contentType: false,
        type: 'POST',
        url: 'https://api.imgur.com/3/image',
        headers: {
            Authorization: 'Bearer ' + tokenimage
        },
        mimeType: 'multipart/form-data'
    };

    // data傳遞
    let form = new FormData();
    form.append('image', inputFile.getuploadFile());
    form.append('title', inputFile.getfileTitle());
    form.append('description', inputFile.getfileDesc());

    settings.data = form;

    // 傳遞資料
    $.ajax(settings).done(function (res) {
        const imgURL = JSON.parse(res).data.link
        addImg(imgURL);
        // console.log(JSON.parse(res)); // 可以看見上傳成功後回傳的URL
        // console.log(JSON.parse(res).data.link)
        Swal.fire('上傳完成');
    });
    document.getElementById("preview-image").src = "";
}

// 建議物件
const inputFile = new file();

// input:file的監聽器
uploadInput.addEventListener("change", (e) => {
    inputFile.setuploadFile(uploadInput.files[0]); // input type="file" 的值
    inputFile.setfileName(inputFile.uploadFile.name); // input的圖檔名稱
    inputFile.setfileSize(Math.floor(inputFile.uploadFile.size * 0.001) + 'KB'); // input的圖片大小
    inputFile.setfileThumbnail(window.URL.createObjectURL(inputFile.uploadFile)); // input的圖片縮圖
    inputFile.setfileTitle(inputFile.uploadFile.name); // 預設 input 的圖檔名稱為圖片上傳時的圖片標題
    inputFile.setfileDesc(inputFile.uploadFile.name); // 圖片描述
    var file = uploadInput.files[0], imageType = /^image\//, reader = '';
    document.getElementById('makerUrl').innerText = "";

    // 檔案是否為圖片
    if (!imageType.test(file.type)) {
        Swal.fire("請選擇圖片！");
        return;
    }
    // 判斷是否支援FileReader  // IE9及以下不支援FileReader
    if (window.FileReader) {
        reader = new FileReader();
    } else {
        Swal.fire("您的瀏覽器不支援圖片預覽功能，如需該功能請升級您的瀏覽器！");
        return;
    }
    // 讀取完成    
    reader.onload = function (event) {
        // 獲取圖片DOM
        var img = document.getElementById("preview-image");
        // 圖片路徑設定為讀取的圖片    
        img.src = event.target.result;
    };
    reader.readAsDataURL(file);
    document.getElementById("showImage").src = "";
});

// Botton的監聽器
actionBtn.addEventListener("click", () => {

    submitPic();
});


/**圖檔--------------------------------------------------------------------------------------------------------------------------- */



//新增欄位
function ingredientsAppendBtn() {
    $('#addIngredients').append(
        `<li class="dele">` +
        `${document.getElementById('examIngredients').innerHTML}` +
        `</li>`
    );
}


//刪除欄位
// var rows = $('#addIngredients li');
function ingredientsDelBtn(obj) {
    var row = obj.closest('li');
    var rows = $('#addIngredients li');
    // console.log(rows);
    if (rows.length > 1) {
        // change: work on filtered jQuery object
        // rows.filter(":last").html('');
        row.remove();
    } else {
        Swal.fire('Cannot remove any more rows');
    }

}




//未給蓉庭

//計算營養成份表(要覆蓋)
//
var ingredientsname = document.getElementsByName('ingredientsName');
var ingredientsgrams = document.getElementsByName('ingredientsGrams');
var mealtypevalue = document.getElementsByName('mealtypevalue');
var caloriefloat;
var carbfloat;
var fatfloat;
var proteintfloat;


var ingredientjson = {};
//
function ingredientsOPA() {
    $.ajax({
        url: `https://${postName}/api/${storeId}/ingredients?token=${newToken}`,
        method: 'GET',
        success: function (res, status) {
            console.log("ingredientsOPA() success");
        },
        error: function () { console.log("ingredientsOPA() fail"); }
    }).done(function (index) {

        // var ingredientsname = document.getElementsByName('ingredientsName');
        // var ingredientsgrams = document.getElementsByName('ingredientsGrams');
        var calorietot = 0;
        var carbtot = 0;
        var fattot = 0;
        var proteintot = 0;
        var gramstot = 0;

        for (var i = 1; i < ingredientsname.length; i++) {
            if (ingredientsname[i].value != "" && ingredientsgrams[i].value != "") {
                let calorie;
                let carb;
                let fat;
                let protein;
                let inputgrams;


                // for (var b of index) {
                var a = index.filter(function (item, index, array) {//
                    // console.log(item);
                    return item.ingredientname == ingredientsname[i].value;
                });
                // a.push(b[0]);
                // console.log(a);

                // if (ingredientsname[i].value == a.ingredientname) {

                calorie = Number(a[0].calorie);
                carb = Number(a[0].carb);
                fat = Number(a[0].fat);
                protein = Number(a[0].protein);
                inputgrams = Number(ingredientsgrams[i].value);

                calorietot += parseFloat(calorie * Number(ingredientsgrams[i].value));
                carbtot += parseFloat(carb * Number(ingredientsgrams[i].value));
                fattot += parseFloat(fat * Number(ingredientsgrams[i].value));
                proteintot += parseFloat(protein * Number(ingredientsgrams[i].value));
                gramstot += inputgrams;

                caloriefloat = (calorietot).toFixed(2);
                carbfloat = (carbtot).toFixed(2);
                fatfloat = (fattot).toFixed(2);
                proteintfloat = (proteintot).toFixed(2);

                $('#nutritionOperation').html(
                    "<hr>熱量:" + `${caloriefloat}` +
                    "(kcal)<br>碳水化合物:" + `${carbfloat}` +
                    "(g)<br>脂肪:" + `${fatfloat}` +
                    "(g)<br>蛋白質:" + `${proteintfloat}` +
                    "(g)<br>餐點總克數" + `${gramstot}` + "(g)");


            } else {
                Swal.fire("食材輸入不完整");
                $('#nutritionOperation').html(
                    "<hr>");
                break;
            }
        }
        // console.log(calorietot + ":" + carbtot)
        ingredientjson = index;

    })
        .fail(function () {
            Swal.fire("error");
        })


}



//菜單管理GET創建菜單 -嘉彬
var vmealjson = {};
var mealjson = {};
// runmenu();
$("a[data-bs-target='#pills-menu']").on('click', runmenu);
function runmenu() {
    $.ajax({
        url: `https://${postName}/api/${storeId}/vmealbom?token=${newToken}`,
        method: 'GET',
        success: function (res, status) {
            {//res json
                $('#tableMenubody').empty();
                console.log("menuTable ok")
                for (var menu of res) //menu obj

                    $('#tableMenubody').append(
                        '<tr>' +
                        `<td style="display:none">` + `${menu.mealid}` + '</td>' +
                        '<td class="my-td-width14">' + `${menu.mealname}` + `${menu.mealvegan ? "(素)" : ""}` + '</td>' +
                        `<td class="my-td-width4" style="padding: 10px;">` + `${menu.mealhot ? '<i class="fa-solid fa-fire"></i>' : ''}` + `</td>` +
                        '<td class="my-td-width10">' + `${menu.mealcategoryname}` + '</td>' +
                        '<td class="my-td-width10 textBlock">' + `<img referrerpolicy="no-referrer" style="height:50px" src="${menu.mealimage}">` + '</td>' +
                        '<td class="my-td-width22 textBlock" style="padding: 10px;">' + `${menu.ingredient}` + '</td>' +
                        '<td class="my-td-width10" style="padding: 10px;">' + `${menu.mealprice}` + '</td>' +
                        '<td class="my-td-width22 textBlock">' + `${menu.mealdesc}` + '</td>' +
                        `<td class="my-td-width4" style="padding: 10px;">` + `<i class="fa-solid fa-pencil" onclick='javascript:modifyMenuBtn(this)' data-bs-toggle='modal' data-bs-target='#exampleModal'></i>` + `</td>` +
                        `<td class="my-td-width4" style="padding: 10px;">` + `<i class="fa-solid fa-trash" onclick='javascript:delField(this);'></i>` + `</td>` +
                        '</tr>'
                    )

            }
            // console.log(res);
            vmealjson = res;


            $('#tableMenubody').find('tr').on('click', function () {
                if ($(this).find('td').css('word-break') == 'normal') {
                    $(this).find('td').css({//可以改變多個css
                        "word-break": "break-all",
                        'white-space': 'normal'

                    })
                } else {
                    $(this).find('td').css({
                        "word-break": "normal",
                        'white-space': 'nowrap'
                    })
                }
            })
        },
        error: function () { }
    })
}



//菜單管理的刪除鍵
function delField(obj) {
    var row = obj.closest('tr');



    // function deleteMeal(){
    $.ajax({
        url: `https://${postName}/api/${storeId}/meals?token=${newToken}`,
        method: 'GET',
        success: (mealid, status) => {
            var selectmealname = mealid.filter(function (item, index, array) {//
                // console.log(item);
                return item.MEAL_ID == row.cells[0].textContent;
            });

            // console.log(selectmealname[0]);
            // console.log(row.cells[6].textContent);
            // console.log(selectmealname[0].MEAL_DESC);
            selectmealname[0].MEAL_STATUS = false;

            Swal.fire({
                title: "是否要刪除",
                text: `${row.cells[1].textContent}`,
                imageUrl: `${selectmealname[0].MEAL_IMAGE}`,
                icon: 'warning',
                showCancelButton: true
            }).then(function (result) {
                if (result.value) {
                    Swal.fire("刪除成功");
                    // $("#exampleModal").modal('hide');
                    // deleteMeal();
                    $.ajax({
                        url: `https://${postName}/api/${storeId}/meal?token=${newToken}`,
                        method: 'PUT',
                        contentType: "application/json",
                        data: JSON.stringify(
                            selectmealname[0]

                        ),
                        success: function () {

                            $('#tableMenubody').empty();
                            runmenu();

                        },
                        error: function () { }
                    })
                }
                else {
                    Swal.fire("請繼續編輯菜單");
                }
            });

        },
        error: err => {

        },
    })
    // }





}

//GET預設修改菜單
var unitmealid;
// var defaultvegan = "";
function modifyMenuBtn(obj) {
    var row = obj.closest('tr');

    $.ajax({
        url: `https://${postName}/api/${storeId}/meals?token=${newToken}`,
        method: 'GET',
        success: (mealid, status) => {

            var selectmealname = mealid.filter(function (item, index, array) {//
                // console.log(item);
                return item.MEAL_ID == row.cells[0].textContent;
            });
            ingredientsOPA();

            $.ajax({
                url: `https://${postName}/api/${storeId}/vrevisemealdisplay?token=${newToken}&mealid=${selectmealname[0].MEAL_ID}`,
                method: 'GET',
                success: function (res, status) {
                    document.getElementById('nutritionOperation').innerHTML = "";
                    ingredientsDelAuto();
                    var ingredientnamearray = [];
                    var mealingredientweightarray = [];

                    for (var nutrient of res) {
                        ingredientnamearray.push(nutrient.ingredientname);
                        mealingredientweightarray.push(nutrient.mealingredientweight);
                        $('#addIngredients').append(
                            `<li class="dele">` +
                            `${document.getElementById('examIngredients').innerHTML}` +
                            `</li>`
                        );

                        for (var i = 0; i < ingredientnamearray.length; i++) {
                            var j = i + 1;
                            ingredientsname[j].value = ingredientnamearray[i];
                            ingredientsgrams[j].value = mealingredientweightarray[i];
                        }

                        // console.log(nutrient.ingredientname);
                        // console.log(nutrient.mealingredientweight);
                    }
                    var rows = $('#addIngredients li');
                    // if (rows.length > ingredientnamearray.length) {
                    //     // change: work on filtered jQuery object
                    //     rows.filter(":last").html('');
                    //     $('#addIngredients :last').remove();
                    // }
                    // ingredientjson = res;
                    // console.log(ingredientnamearray[0]);
                    // console.log(mealingredientweightarray);

                },
                error: function () { }
            })
                .fail(function () {
                    Swal.fire("請先登入/資料庫OR後端有誤");
                    window.location.href = "login.html"
                })
            // var ingredientarray = new Array();
            // var ingredientarray = selectmealname[0].ingredient.split(";");

            // if (selectmealname[0].MEAL_VEGAN == true) {
            //     defaultvegan = selectmealname[0].MEAL_NAME.replace("(素)","");
            // }


            document.getElementById("exampleModalLabel").innerText = "修改菜單";
            document.getElementById('inputMealName').value = selectmealname[0].MEAL_NAME.replace("(素)", "");
            document.getElementById('inputMealType').value = selectmealname[0].MEAL_CATEGORY_NAME;
            document.getElementById('inputMealPrice').value = selectmealname[0].MEAL_PRICE;
            document.getElementById('inputMealDesc').value = selectmealname[0].MEAL_DESC;
            // console.log(selectmealname[0]);
            $(`#mealHotCheck`)[0].checked = selectmealname[0].MEAL_HOT;
            $(`#mealveganCheck`)[0].checked = selectmealname[0].MEAL_VEGAN;
            document.getElementById('uploadImage').value = "";
            document.getElementById('makerUrl').innerText = selectmealname[0].MEAL_IMAGE;
            document.getElementById('showImage').src = selectmealname[0].MEAL_IMAGE;


            unitmealid = selectmealname[0].MEAL_ID;
            // console.log(row.cells[0].textContent);
            // console.log(unitmealid);

        },
        error: err => {

        },
    })






}

//default新增菜單
var appendMenuBtnEle = document.querySelector('.appendMenu');
appendMenuBtnEle.addEventListener('click', () => {
    ingredientsDelAuto();
    document.getElementById("exampleModalLabel").innerText = "新增菜單";
    document.getElementById('inputMealName').value = "";
    document.getElementById('inputMealType').value = "";
    document.getElementById('inputMealPrice').value = "";
    document.getElementById('inputMealDesc').value = "";
    document.getElementById('preview-image').src = "";
    document.getElementById('uploadImage').value = "";
    document.getElementById('makerUrl').innerText = "";
    document.getElementById('showImage').src = "";

    ingredientsname[0].value = "";
    ingredientsgrams[0].value = "";
    unitmealid = "";
    document.getElementById('nutritionOperation').innerHTML = "";
    // console.log($(`#mealHotCheck`));
    $(`#mealHotCheck`)[0].checked = false;
    $(`#mealveganCheck`)[0].checked = false;

    // console.log($(`#mealveganCheck`).is(':checked').toString());


    // const reloadEle = document.querySelectorAll('.reload');
    // function successAction() {
    //     for (var i = 0; i < reloadEle.length; i++) {
    //         reloadEle[i].value = '';
    //         // reloadEle.value = ''

    //         // console.log(reloadEle[i].value);
    //     }
    // }
    // successAction();
})



//修改鍵時自動刪除
function ingredientsDelAuto() {
    var rows = $('#addIngredients li');
    if (rows.length > 1) {
        // var elem = document.getElementById('addIngredients');
        // elem.parentNode.removeChild(elem);
        rows.filter(".dele").html('');
        $('#addIngredients .dele').remove();
    } else {
    }
}


//POST 修改菜單主程式
var ismealhot = $(`#mealHotCheck`).is(':checked').toString();
var ismealvegan = $(`#mealveganCheck`).is(':checked').toString();


function saveField() {

    // console.log(document.getElementById('inputMealType').value);

    ingredientsOPA();


    Swal.fire({
        title: "是否要儲存",
        text: "",
        imageUrl: `${document.getElementById('makerUrl').innerText}`,
        // icon: 'warning',
        showCancelButton: true
    }).then(function (result) {

        if (result.value) {
            if (document.getElementById('inputMealName').value == ""
                || document.getElementById('inputMealType').value == ""
                || document.getElementById('inputMealPrice').value == "") {
                Swal.fire("菜單資訊不完整，請繼續編輯");

            } else {
                if (document.getElementById('makerUrl').innerText == "") {
                    Swal.fire("餐點圖檔未上傳");

                } else {
                    Swal.fire("儲存成功");
                    $("#exampleModal").modal('hide');
                    saveAppendMenu();
                }


            }
        }
        else {
            Swal.fire("請繼續編輯菜單");
        }
    });
    // Swal.fire("圖片未上傳");


}


//GET餐點種類  
$.ajax({
    url: `https://${postName}/api/${storeId}/mealcategorys?token=${newToken}`,
    method: 'GET',
    success: function (res, status) {

        for (var meal of res) {


            $('#inputMealType').append(
                `<option value = "${meal.MEAL_CATEGORY_NAME}">` + `${meal.MEAL_CATEGORY_NAME}` + `</option>`
            )

            // console.log(meal.MEAL_CATEGORY_ID);
            // console.log(document.getElementById('inputMealType').value);


        }
    },
    error: function () {
        console.log("餐點種類讀取錯誤");
    }
})

// $(`#mealHotCheck`).on(`change`, function () {
//     if ($(this).is(':checked')) {
//         $(this).attr('value', 'true');
//       } else {
//         $(this).attr('value', 'false');
//       }
//       console.log($('#mealHotCheck').val());
//     //   ismealhot = $('#mealHotCheck').val();
//     var ismealhot =$(`#mealHotCheck`).is(':checked').toString();
//     })



// $(`#check1`).on(`click`,function(){
//    $(`#check1 input`).on(`change`, function () {

//     console.log($(`#mealHotCheck`).is(':checked').toString());
//     console.log(typeof($(`#mealHotCheck`).is(':checked')));
//    })

// })




$('#myBtn').on('click', function () {
    // console.log($('#url').innerText);
    // console.log(document.getElementById('url').innerText);

    // console.log($('#myBtn')); // 可以看見上傳成功後回傳的URL
    // console.log($('#showImg'));
    // console.log($('#preview-img'));
    // console.log($('#showImg')[0].src);
    // console.log(obj);

})
$('#xxx').on('click', function () {
    // console.log(document.getElementById('url').innerText);




})




function saveAppendMenu() {
    // 如果有MEALID 做PUT更改
    if (unitmealid != "") {

        $.ajax({
            url: `https://${postName}/api/${storeId}/mealcategorys?token=${newToken}`,
            method: 'GET',
            success: function (res, status) {

                for (var meal of res) {
                    if (meal.MEAL_CATEGORY_NAME == document.getElementById('inputMealType').value) {
                        mealcategoryid = meal.MEAL_CATEGORY_ID;
                        // console.log(mealcategoryid);

                        $.ajax({
                            url: `https://${postName}/api/${storeId}/meal?token=${newToken}`,
                            method: 'PUT',
                            contentType: "application/json",
                            data: JSON.stringify(
                                {
                                    "MEAL_ID": `${unitmealid}`,
                                    "MEAL_NAME": `${document.getElementById('inputMealName').value}` + `${$(`#mealveganCheck`)[0].checked ? "(素)" : ""}`,
                                    "MEAL_CATEGORY_ID": `${mealcategoryid}`,
                                    "MEAL_CATEGORY_NAME": `${document.getElementById('inputMealType').value}`,
                                    "STORE_ID": "l3rH7uT47PTrQSteWO2V9XqbpRn1",
                                    "MEAL_IMAGE": `${document.getElementById('makerUrl').innerText}`,
                                    "MEAL_DESC": `${document.getElementById('inputMealDesc').value}`,
                                    "MEAL_PRICE": `${document.getElementById('inputMealPrice').value}`,
                                    "MEAL_CALORIE": `${caloriefloat}`,
                                    "MEAL_CARB": `${carbfloat}`,
                                    "MEAL_FAT": `${fatfloat}`,
                                    "MEAL_PROTEIN": `${proteintfloat}`,
                                    "MEAL_VEGAN": `${$("#mealveganCheck").is(":checked").toString()}`,
                                    "MEAL_HOT": `${$("#mealHotCheck").is(":checked").toString()}`,
                                    "MEAL_STATUS": true

                                }
                            ),
                            dataType: 'json',
                            success: function (a) {
                                // console.log(a);

                            },
                            error: function () { }
                        })


                    }


                }
            },
            error: function () { }
        }).done()
            .fail(function () {
                Swal.fire("請先登入/資料庫OR後端有誤");
                window.location.href = "login.html"
            })



        // console.log(unitmealid);

        $.ajax({
            url: `https://${postName}/api/${storeId}/mealbom?token=${newToken}&mealid=${unitmealid}`,
            method: 'DELETE',
            success: function (delesuccess) {
                if (delesuccess) {

                    $.ajax({
                        url: `https://${postName}/api/${storeId}/ingredients?token=${newToken}`,
                        method: 'GET',
                        success: function (ingredientvalue, status) {
                            // console.log(ingredientvalue);


                            for (var i = 1; i < ingredientsname.length; i++) {
                                if (ingredientsname[i].value != "" && ingredientsgrams[i].value != "") {
                                    var ingredientfilter = ingredientvalue.filter(function (item, index, array) {//
                                        // console.log(item);
                                        return item.ingredientname == ingredientsname[i].value;
                                    });
                                    var mealingredientcaloriefloat = parseFloat(ingredientfilter[0].calorie * ingredientsgrams[i].value);//未計算
                                    var mealingredientcarbfloat = parseFloat(ingredientfilter[0].carb * ingredientsgrams[i].value);//未計算
                                    var mealingredientfatfloat = parseFloat(ingredientfilter[0].fat * ingredientsgrams[i].value);//未計算  
                                    var mealingredientproteinfloat = parseFloat(ingredientfilter[0].protein * ingredientsgrams[i].value);//未計算

                                    var mealingredientcalorie = (mealingredientcaloriefloat).toFixed(2);
                                    var mealingredientcarb = (mealingredientcarbfloat).toFixed(2);
                                    var mealingredientfat = (mealingredientfatfloat).toFixed(2);
                                    var mealingredientprotein = (mealingredientproteinfloat).toFixed(2);

                                    // console.log(ingredientfilter[0].ingredientid);
                                    // console.log(ingredientfilter[0].ingredientname);
                                    // console.log(mealingredientcalorie);//計算
                                    // console.log(mealingredientcarb);//計算
                                    // console.log(mealingredientfat);//計算
                                    // console.log(mealingredientprotein);//計算

                                    $.ajax({
                                        url: `https://${postName}/api/${storeId}/mealbom?token=${newToken}`,
                                        method: 'POST',
                                        contentType: "application/json",
                                        data: JSON.stringify(
                                            [
                                                {
                                                    "INGREDIENT_ID": `${ingredientfilter[0].ingredientid}`,
                                                    "INGREDIENT_NAME": `${ingredientfilter[0].ingredientname}`,
                                                    "MEAL_ID": `${unitmealid}`,
                                                    "MEAL_INGREDIENT_CALORIE": `${mealingredientcalorie}`,
                                                    "MEAL_INGREDIENT_CARB": `${mealingredientcarb}`,
                                                    "MEAL_INGREDIENT_FAT": `${mealingredientfat}`,
                                                    "MEAL_INGREDIENT_PROTEIN": `${mealingredientprotein}`,
                                                    "MEAL_INGREDIENT_WEIGHT": `${ingredientsgrams[i].value}`
                                                }
                                            ]
                                        ),
                                        dataType: 'json',
                                        success: function () {
                                            
                                            runmenu();
                                        },
                                        error: function () { }
                                    })

                                } else {
                                    Swal.fire("食材輸入不完整");
                                    $('#nutritionOperation').html(
                                        "<hr>");
                                    break;
                                }

                            }
                            // runmenu();

                        },
                        error: function () { console.log("ingredientsOPA() fail"); }
                    }).done(function (index) { })
                }

            },
            error: function () { }
        })



        //如果沒有MealID 做POST新增菜單
    } else {

        $.ajax({
            url: `https://${postName}/api/${storeId}/mealcategorys?token=${newToken}`,
            method: 'GET',
            success: function (res, status) {

                var mealcategoryid = "";
                for (var meal of res) {
                    if (meal.MEAL_CATEGORY_NAME == document.getElementById('inputMealType').value) {
                        mealcategoryid = meal.MEAL_CATEGORY_ID;
                        // console.log(mealcategoryid);

                        $.ajax({
                            url: `https://${postName}/api/${storeId}/meal?token=${newToken}`,
                            method: 'POST',
                            contentType: "application/json",
                            data: JSON.stringify(
                                {

                                    "MEAL_NAME": `${document.getElementById('inputMealName').value}` + `${$(`#mealveganCheck`)[0].checked ? "(素)" : ""}`,
                                    "MEAL_CATEGORY_ID": `${mealcategoryid}`,
                                    "MEAL_CATEGORY_NAME": `${document.getElementById('inputMealType').value}`,
                                    "STORE_ID": "l3rH7uT47PTrQSteWO2V9XqbpRn1",
                                    "MEAL_IMAGE": `${document.getElementById('makerUrl').innerText}`,
                                    "MEAL_DESC": `${document.getElementById('inputMealDesc').value}`,
                                    "MEAL_PRICE": `${document.getElementById('inputMealPrice').value}`,
                                    "MEAL_CALORIE": `${caloriefloat}`,
                                    "MEAL_CARB": `${carbfloat}`,
                                    "MEAL_FAT": `${fatfloat}`,
                                    "MEAL_PROTEIN": `${proteintfloat}`,
                                    "MEAL_VEGAN": `${$("#mealveganCheck").is(":checked").toString()}`,
                                    "MEAL_HOT": `${$("#mealHotCheck").is(":checked").toString()}`,
                                    "MEAL_STATUS": true

                                }
                            ),
                            dataType: 'json',
                            success: function (a) {
                                // console.log(a);

                                $.ajax({
                                    url: `https://${postName}/api/${storeId}/meals?token=${newToken}`,
                                    method: 'GET',
                                    success: (mealid, status) => {

                                        // console.log(mealid[(mealid.length) - 1].MEAL_ID);


                                        $.ajax({
                                            url: `https://${postName}/api/${storeId}/ingredients?token=${newToken}`,
                                            method: 'GET',
                                            success: function (ingredientvalue, status) {
                                                // console.log(ingredientvalue);


                                                for (var i = 1; i < ingredientsname.length; i++) {
                                                    if (ingredientsname[i].value != "" && ingredientsgrams[i].value != "") {
                                                        var ingredientfilter = ingredientvalue.filter(function (item, index, array) {//
                                                            // console.log(item);
                                                            return item.ingredientname == ingredientsname[i].value;
                                                        });
                                                        var mealingredientcaloriefloat = parseFloat(ingredientfilter[0].calorie * ingredientsgrams[i].value);//未計算
                                                        var mealingredientcarbfloat = parseFloat(ingredientfilter[0].carb * ingredientsgrams[i].value);//未計算
                                                        var mealingredientfatfloat = parseFloat(ingredientfilter[0].fat * ingredientsgrams[i].value);//未計算  
                                                        var mealingredientproteinfloat = parseFloat(ingredientfilter[0].protein * ingredientsgrams[i].value);//未計算

                                                        var mealingredientcalorie = (mealingredientcaloriefloat).toFixed(2);
                                                        var mealingredientcarb = (mealingredientcarbfloat).toFixed(2);
                                                        var mealingredientfat = (mealingredientfatfloat).toFixed(2);
                                                        var mealingredientprotein = (mealingredientproteinfloat).toFixed(2);

                                                        // console.log(ingredientfilter[0].ingredientid);
                                                        // console.log(ingredientfilter[0].ingredientname);
                                                        // console.log(mealingredientcalorie);//計算
                                                        // console.log(mealingredientcarb);//計算
                                                        // console.log(mealingredientfat);//計算
                                                        // console.log(mealingredientprotein);//計算

                                                        $.ajax({
                                                            url: `https://${postName}/api/${storeId}/mealbom?token=${newToken}`,
                                                            method: 'POST',
                                                            contentType: "application/json",
                                                            data: JSON.stringify(
                                                                [
                                                                    {
                                                                        "INGREDIENT_ID": `${ingredientfilter[0].ingredientid}`,
                                                                        "INGREDIENT_NAME": `${ingredientfilter[0].ingredientname}`,
                                                                        "MEAL_ID": `${mealid[(mealid.length) - 1].MEAL_ID}`,
                                                                        "MEAL_INGREDIENT_CALORIE": `${mealingredientcalorie}`,
                                                                        "MEAL_INGREDIENT_CARB": `${mealingredientcarb}`,
                                                                        "MEAL_INGREDIENT_FAT": `${mealingredientfat}`,
                                                                        "MEAL_INGREDIENT_PROTEIN": `${mealingredientprotein}`,
                                                                        "MEAL_INGREDIENT_WEIGHT": `${ingredientsgrams[i].value}`
                                                                    }
                                                                ]
                                                            ),
                                                            dataType: 'json',
                                                            success: function () {
                                                                runmenu();


                                                            },
                                                            error: function () {
                                                                console.log("新增餐點的食材表時，出現錯誤");

                                                            }
                                                        })

                                                    } else {
                                                        Swal.fire("食材輸入不完整");
                                                        $('#nutritionOperation').html(
                                                            "<hr>");
                                                        break;
                                                    }

                                                }
                                                // runmenu();

                                            },
                                            error: function () { console.log("ingredientsOPA() fail"); }
                                        }).done(function (index) { })

                                            .fail(function () {
                                                Swal.fire("error");
                                            })

                                    },
                                    error: err => {

                                    },
                                })

                            },
                            error: function () { }
                        })
                    };

                    // console.log(meal.MEAL_CATEGORY_ID);
                    // console.log(document.getElementById('inputMealType').value);


                }
                // runmenu();
            },
            error: function () {
                console.log("在POST新增的時候餐點種類讀取錯誤");

            }
        })



    }

}






