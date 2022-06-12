// let newToken = localStorage.getItem('storeToken');
localStorage.getItem('storeToken') == null ? function () { alert("請先登入"); window.location.href = "login.html" }() : newToken = localStorage.getItem('storeToken');
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
function ingredientsDelBtn() {
    var rows = $('#addIngredients li');
    if (rows.length > 1) {
        // change: work on filtered jQuery object
        rows.filter(":last").html('');
        $('#addIngredients :last').remove();
    } else {
        alert('Cannot remove any more rows');
    }

}




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
        url: `http://localhost:${localhost}/api/${storeId}/ingredients?token=${newToken}`,
        method: 'GET',
        success: function (res, status) {
        },
        error: function () { console.log("ingredientsOPA() fail"); }
    }).done(function (index) {

        var calorietot = 0;
        var carbtot = 0;
        var fattot = 0;
        var proteintot = 0;
        var gramstot = 0;

        for (var i = 0; i < ingredientsname.length; i++) {
            if (ingredientsname[i].value != "" && ingredientsgrams[i].value != "") {
                let calorie;
                let carb;
                let fat;
                let protein;
                let inputgrams;


                var a = index.filter(function (item, index, array) {//
                    return item.ingredientname == ingredientsname[i].value;
                });


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
                alert("請輸入完整資訊");
                $('#nutritionOperation').html(
                    "<hr>");
                break;
            }
        }
        ingredientjson = index;

    })
        .fail(function () {
            alert("error");
        })


}



//菜單管理GET創建菜單 -嘉彬
var vmealjson = {};
runmenu();
function runmenu() {
    $.ajax({
        url: `http://localhost:${localhost}/api/${storeId}/vmealbom?token=${newToken}`,
        method: 'GET',
        success: function (res, status) {
            {//res json
                $('#tableMenubody').empty();
                for (var menu of res) //menu obj
                    $('#tableMenubody').append(
                        '<tr>' +
                        '<td class="my-td-width14">' + `${menu.mealname}` + '</td>' +
                        `<td class="my-td-width4">` + `</td>` +
                        '<td class="my-td-width10">' + `${menu.mealcategoryname}` + '</td>' +
                        '<td class="my-td-width10">' + `xxxxx` + '</td>' +
                        '<td class="my-td-width22 textBlock">' + `${menu.ingredient}` + 'kcal</td>' +
                        '<td class="my-td-width10">' + `${menu.mealprice}` + ' TWD' + '</td>' +
                        '<td class="my-td-width22 textBlock">' + `${menu.mealdesc}` + '</td>' +
                        `<td class="my-td-width4">` + "<input type='button' value='修改' onclick='javascript:modifyMenuBtn(this)' data-bs-toggle='modal' data-bs-target='#exampleModal'>" + `</td>` +
                        `<td class="my-td-width4">` + "<input type='button' value='刪除' onclick='javascript:delField(this);'>" + `</td>` +
                        '</tr>'
                    )

            }
            vmealjson = res;


            $('tr').on('click', function () {
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

    $.ajax({
        url: `http://localhost:${localhost}/api/${storeId}/meals?token=${newToken}`,
        method: 'GET',
        success: (mealid, status) => {
            var selectmealname = mealid.filter(function (item, index, array) {//
                return item.MEAL_NAME == row.cells[0].textContent && item.MEAL_DESC == row.cells[6].textContent;
            });

            selectmealname[0].MEAL_STATUS = false;

            $.ajax({
                url: `http://localhost:${localhost}/api/${storeId}/meal?token=${newToken}`,
                method: 'PUT',
                contentType: "application/json",
                data: JSON.stringify(
                    selectmealname[0]
                    // {
                    //     "MEAL_ID": `M202206_cf3d24cae82a11ecaa9a708bcd207f49`,
                    //     "MEAL_NAME": `${selectmealname[0].MEAL_NAME}`,
                    //     "MEAL_CATEGORY_ID":`${selectmealname[0].MEAL_CATEGORY_ID}`,
                    //     "MEAL_CATEGORY_NAME": `${selectmealname[0].MEAL_CATEGORY_NAME}`,
                    //     "STORE_ID": `${selectmealname[0].STORE_ID}`,
                    //     "MEAL_IMAGE": `${selectmealname[0].MEAL_IMAGE}`,
                    //     "MEAL_DESC": `${selectmealname[0].MEAL_DESC}`,
                    //     "MEAL_PRICE": `${selectmealname[0].MEAL_PRICE}`,
                    //     "MEAL_CALORIE": `${selectmealname[0].MEAL_CALORIE}`,
                    //     "MEAL_CARB": `${selectmealname[0].MEAL_CARB}`,
                    //     "MEAL_FAT": `${selectmealname[0].MEAL_FAT}`,
                    //     "MEAL_PROTEIN": `${selectmealname[0].MEAL_PROTEIN}`,
                    //     "MEAL_VEGAN": `${selectmealname[0].MEAL_VEGAN}`,
                    //     "MEAL_HOT": `${selectmealname[0].MEAL_HOT}`,
                    //     "MEAL_STATUS": false
                    // }
                ),
                success: function () {
                    runmenu();


                },
                error: function () { }
            })
        },
        error: err => {

        },
    })




}

//GET預設修改菜單
var unitmealid;
function modifyMenuBtn(obj) {
    ingredientsOPA();
    var row = obj.closest('tr');
    var selectmealname = vmealjson.filter(function (item, index, array) {//
        return item.mealname == row.cells[0].textContent;
    });
    $.ajax({
        url: `http://localhost:${localhost}/api/${storeId}/vrevisemealdisplay?token=${newToken}&mealid=${selectmealname[0].mealid}`,
        method: 'GET',
        success: function (res, status) {
            document.getElementById('nutritionOperation').innerHTML = "";
            ingredientsDelAuto();
            var ingredientnamearray = [];
            var mealingredientweightarray = [];

            for (var nutrient of res) {
                ingredientnamearray.push(nutrient.ingredientname);
                mealingredientweightarray.push(nutrient.mealingredientweight);


                for (var i = 0; i < ingredientnamearray.length; i++) {
                    ingredientsname[i].value = ingredientnamearray[i];
                    ingredientsgrams[i].value = mealingredientweightarray[i];
                }
                $('#addIngredients').append(
                    `<li class="dele">` +
                    `${document.getElementById('examIngredients').innerHTML}` +
                    `</li>`
                );
            }
            var rows = $('#addIngredients li');
            if (rows.length > ingredientnamearray.length) {
                // change: work on filtered jQuery object
                rows.filter(":last").html('');
                $('#addIngredients :last').remove();
            }


        },
        error: function () { }
    })
        .fail(function () {
            // alert("請先登入/資料庫OR後端有誤");
            // window.location.href = "login.html"
        })
    // var ingredientarray = new Array();
    // var ingredientarray = selectmealname[0].ingredient.split(";");
    document.getElementById("exampleModalLabel").innerText = "修改菜單";
    document.getElementById('inputMealName').value = selectmealname[0].mealname;
    document.getElementById('inputMealType').value = selectmealname[0].mealcategoryname;
    document.getElementById('inputMealPrice').value = selectmealname[0].mealprice;
    document.getElementById('inputMealDesc').value = selectmealname[0].mealdesc;
    unitmealid = selectmealname[0].mealid;

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
    ingredientsname[0].value = "";
    ingredientsgrams[0].value = "";
    unitmealid = "";
    document.getElementById('nutritionOperation').innerHTML = "";

    const reloadEle = document.querySelectorAll('.reload');
    function successAction() {
        for (var i = 0; i < reloadEle.length; i++) {
            reloadEle[i].value = '';

        }
    }
    successAction();
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
var ismealhot = $(`#mealveganCheck`).is(':checked').toString();


function saveField() {
    ingredientsOPA();


    //如果有MEALID 做PUT更改
    if (unitmealid != "") {
        $.ajax({
            url: `http://localhost:${localhost}/api/${storeId}/meal?token=${newToken}`,
            method: 'PUT',
            contentType: "application/json",
            data: JSON.stringify(
                {
                    "MEAL_ID": `${unitmealid}`,
                    "MEAL_NAME": `${document.getElementById('inputMealName').value}`,
                    "MEAL_CATEGORY_ID": "HEALTHMEAL",
                    "MEAL_CATEGORY_NAME": `${document.getElementById('inputMealType').value}`,
                    "STORE_ID": "l3rH7uT47PTrQSteWO2V9XqbpRn1",
                    "MEAL_IMAGE": "https://i.imgur.com/lUrEcXU.jpg",
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

            },
            error: function () { }
        })

        $.ajax({
            url: `http://localhost:${localhost}/api/${storeId}/mealbom?token=${newToken}&mealid=${unitmealid}`,
            method: 'DELETE',
            success: function (delesuccess) {
                if (delesuccess) {

                    $.ajax({
                        url: `http://localhost:${localhost}/api/${storeId}/ingredients?token=${newToken}`,
                        method: 'GET',
                        success: function (ingredientvalue, status) {


                            for (var i = 0; i < ingredientsname.length; i++) {
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
                                    $.ajax({
                                        url: `http://localhost:${localhost}/api/${storeId}/mealbom?token=${newToken}`,
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
                                    alert("請輸入完整資訊");
                                    $('#nutritionOperation').html(
                                        "<hr>");
                                    break;
                                }

                            }


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
            url: `http://localhost:${localhost}/api/${storeId}/meal?token=${newToken}`,
            method: 'POST',
            contentType: "application/json",
            data: JSON.stringify(
                {

                    "MEAL_NAME": `${document.getElementById('inputMealName').value}`,
                    "MEAL_CATEGORY_ID": "HEALTHMEAL",
                    "MEAL_CATEGORY_NAME": `${document.getElementById('inputMealType').value}`,
                    "STORE_ID": "l3rH7uT47PTrQSteWO2V9XqbpRn1",
                    "MEAL_IMAGE": "https://i.imgur.com/lUrEcXU.jpg",
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

                $.ajax({
                    url: `http://localhost:${localhost}/api/${storeId}/meals?token=${newToken}`,
                    method: 'GET',
                    success: (mealid, status) => {


                        $.ajax({
                            url: `http://localhost:${localhost}/api/${storeId}/ingredients?token=${newToken}`,
                            method: 'GET',
                            success: function (ingredientvalue, status) {
                                console.log(ingredientvalue);


                                for (var i = 0; i < ingredientsname.length; i++) {
                                    if (ingredientsname[i].value != "" && ingredientsgrams[i].value != "") {
                                        var ingredientfilter = ingredientvalue.filter(function (item, index, array) {//
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


                                        $.ajax({
                                            url: `http://localhost:${localhost}/api/${storeId}/mealbom?token=${newToken}`,
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
                                            error: function () { }
                                        })

                                    } else {
                                        alert("請輸入完整資訊");
                                        $('#nutritionOperation').html(
                                            "<hr>");
                                        break;
                                    }

                                }


                            },
                            error: function () { console.log("ingredientsOPA() fail"); }
                        }).done(function (index) { })

                            .fail(function () {
                                alert("error");
                            })

                    },
                    error: err => {

                    },
                })

            },
            error: function () { }
        })
    }

}


//餐點種類  
var mealcategoryid = [];
$.ajax({
    url: `http://localhost:${localhost}/api/${storeId}/mealcategorys?token=${newToken}`,
    method: 'GET',
    success: function (res, status) {
        console.log(res)
        var a = res.filter(function (item, index, array) {//
            // console.log(item);
            return item.MEAL_CATEGORY_NAME == document.getElementById('inputMealType').value;
        });
        console.log(a[0]);
        for (var meal of res) {

            $('#inputMealType').append(
                `<option value = "${meal.MEAL_CATEGORY_NAME}">` + `${meal.MEAL_CATEGORY_NAME}` + `</option>`
            )

            console.log(meal.MEAL_CATEGORY_ID);
            console.log(document.getElementById('inputMealType').value);


        }
    },
    error: function () { }
}).done()
    .fail(function () {
        // alert("請先登入/資料庫OR後端有誤");
        // window.location.href = "login.html"
    })








