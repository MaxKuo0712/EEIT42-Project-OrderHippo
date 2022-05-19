        $("#nutrientContentsearch").keyup(function () {
            var value = this.value.toLowerCase().trim();

            $("#nutrientContenttable tr").each(function (index) {
                if (!index) return;
                $(this).find("td").each(function () {
                    var id = $(this).text().toLowerCase().trim();
                    var unfound = (id.indexOf(value) == -1);
                    $(this).closest('tr').toggle(!unfound);
                    return unfound;
                });
            });
        });
        var eleval = document.getElementById("searchMenu");
        $("#menuBtn").on("click",function () {
                    console.log(eleval.value);
                    //將輸入值轉為小寫去除前後空格
                    var keyword = eleval.value.toLowerCase().trim();
                    
                    $("#tableMenu tr").each(function (index) {
                        if (!index) return;
                        $(this).find("td").each(function () {
                            var id = $(this).text().toLowerCase().trim();
                            var unfound = (id.indexOf(keyword) == -1);
                            //.closest()由tr開始搜尋/tr結束
                            //.toggle()=>hide()、show切換
                            $(this).closest('tr').toggle(!unfound);
                            return unfound;
                        });
                    });
                });
                var eleval1 = document.getElementById("searchMember");
        $("#memberBtn").on("click",function () {
                    console.log(eleval1.value);
                    //將輸入值轉為小寫去除前後空格
                    var keyword = eleval1.value.toLowerCase().trim();
                    
                    $("#tableMember tr").each(function (index) {
                        if (!index) return;
                        $(this).find("td").each(function () {
                            var id = $(this).text().toLowerCase().trim();
                            var unfound = (id.indexOf(keyword) == -1);
                            //.closest()由tr開始搜尋/tr結束
                            //.toggle()=>hide()、show切換
                            $(this).closest('tr').toggle(!unfound);
                            return unfound;
                        });
                    });
                });
                