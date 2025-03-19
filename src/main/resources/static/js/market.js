function searchiconclicker() {
	$(document).ready(function() {
		$("#selectionsearcher").change(function() {
			var selectedOption = $(this).val();
			if (selectedOption === "가격검색") {
				$('.searchform').show();
				$('#priceform').hide();
				// 가격검색이 선택되면 바로 dataresultch로 이동
				window.location.href = "dataresultch";
			} else if (selectedOption === "상품검색") {
				$('.searchform').show();
				$('#priceform').hide();
			}
		});
	});
	$('.searchform').show();
	$('#priceform').hide();
}



function sidemenuinout() {
	var clck = false;
	$(".inandout").click(function() {
		if (clck) {
			$(".marketmaindiv").css("overflow", "hidden")
			$(".pagination").css("margin-top", "-64.75px")
			$(".sidebar_table").css("display", "inline-block")
			$(".sidebar_table td").css("width", "150px")
				.css("height", "25px")
		}
		else {
			$(".marketmaindiv").css("overflow", "auto")
			$(".pagination").css("margin-top", "-9.75px")
			$(".sidebar_table").css("display", "none")
		}
		clck = !clck;
	});
}

function categorychecker() {
	$("#nsc_farm").click(function() {
		$("#cate_searchingarea").val("농산물")
	});
	$("#nsc_seafood").click(function() {
		$("#cate_searchingarea").val("수산물")
	});
	$("#nsc_meat").click(function() {
		$("#cate_searchingarea").val("축산물")
	});
	$("#nsc_scning").click(function() {
		$("#cate_searchingarea").val("조미료")
	});
	$("#nsc_baking").click(function() {
		$("#cate_searchingarea").val("제과/제빵재료")
	});
	$("#nsc_drink").click(function() {
		$("#cate_searchingarea").val("음료")
	});
	$("#nsc_milky").click(function() {
		$("#cate_searchingarea").val("유가공품")
	});
	$("#nsc_snack").click(function() {
		$("#cate_searchingarea").val("과자/베이커리")
	});
	$("#nsc_frozen").click(function() {
		$("#cate_searchingarea").val("냉동/간편조리식품")
	});
	$("#nsc_diet").click(function() {
		$("#cate_searchingarea").val("다이어트식품")
	});
	$("#nsc_can").click(function() {
		$("#cate_searchingarea").val("통조림/캔")
	});
	$("#nsc_meal").click(function() {
		$("#cate_searchingarea").val("밀키트")
	});
	$("#nsc_side").click(function() {
		$("#cate_searchingarea").val("반찬")
	});
	$("#nsc_instantnoodle").click(function() {
		$("#cate_searchingarea").val("라면/면류")
	});
	$("#nsc_oil").click(function() {
		$("#cate_searchingarea").val("식용유/오일")
	});
	$("#nsc_cheese").click(function() {
		$("#cate_searchingarea").val("치즈")
	});
	$("#nsc_butter").click(function() {
		$("#cate_searchingarea").val("버터")
	});
	$("#nsc_whipcream").click(function() {
		$("#cate_searchingarea").val("휘핑크림")
	});
	$("#nsc_namacream").click(function() {
		$("#cate_searchingarea").val("생크림")
	});
	$("#nsc_rowmilk").click(function() {
		$("#cate_searchingarea").val("연유")
	});
	$("#nsc_magarin").click(function() {
		$("#cate_searchingarea").val("마가린")
	});
	$("#nsc_psobject").click(function() {
		$("#cate_searchingarea").val("파우더/스무디")
	});
	$("#nsc_tea").click(function() {
		$("#cate_searchingarea").val("차")
	});
	$("#nsc_coffee").click(function() {
		$("#cate_searchingarea").val("커피")
	});
	$("#nsc_sparkle").click(function() {
		$("#cate_searchingarea").val("청량/탄산음료")
	});
	$("#nsc_juice").click(function() {
		$("#cate_searchingarea").val("주스/과즙음료")
	});
	$("#nsc_healty").click(function() {
		$("#cate_searchingarea").val("건강/기능성음료")
	});
	$("#nsc_water").click(function() {
		$("#cate_searchingarea").val("생수")
	});
	$("#nsc_oldtea").click(function() {
		$("#cate_searchingarea").val("전통/차음료")
	});
	$("#nsc_milyo").click(function() {
		$("#cate_searchingarea").val("우유/요거트")
	});
	$("#nsc_beanmil").click(function() {
		$("#cate_searchingarea").val("두유")
	});
	$("#nsc_sparklewater").click(function() {
		$("#cate_searchingarea").val("탄산수")
	});
	$("#nsc_cocoa").click(function() {
		$("#cate_searchingarea").val("코코아")
	});
	$("#nsc_etcbaking").click(function() {
		$("#cate_searchingarea").val("기타제과/제빵재료")
	});
	$("#nsc_mckbk").click(function() {
		$("#cate_searchingarea").val("제과/제빵믹스")
	});
	$("#nsc_bkpd").click(function() {
		$("#cate_searchingarea").val("베이킹파우더")
	});
	$("#nsc_hddmix").click(function() {
		$("#cate_searchingarea").val("호떡믹스")
	});
	$("#nsc_etcscn").click(function() {
		$("#cate_searchingarea").val("기타조미료")
	});
	$("#nsc_salt").click(function() {
		$("#cate_searchingarea").val("소금")
	});
	$("#nsc_vineger").click(function() {
		$("#cate_searchingarea").val("식초")
	});
	$("#nsc_sugar").click(function() {
		$("#cate_searchingarea").val("설탕")
	});
	$("#nsc_pepper").click(function() {
		$("#cate_searchingarea").val("후추")
	});
	$("#nsc_oli").click(function() {
		$("#cate_searchingarea").val("물엿/올리고당")
	});
	$("#nsc_redpepp").click(function() {
		$("#cate_searchingarea").val("고춧가루")
	});
	$("#nsc_msgar").click(function() {
		$("#cate_searchingarea").val("천연감미료")
	});
	$("#nsc_soisource").click(function() {
		$("#cate_searchingarea").val("액젓")
	});
	$("#nsc_redwasabi").click(function() {
		$("#cate_searchingarea").val("고추냉이")
	});
	$("#nsc_msg").click(function() {
		$("#cate_searchingarea").val("맛가루/후리카케")
	});
	$("#nsc_wasabi").click(function() {
		$("#cate_searchingarea").val("겨자")
	});
	$("#nsc_meatmil").click(function() {
		$("#cate_searchingarea").val("축산가공식품")
	});
	$("#nsc_cow").click(function() {
		$("#cate_searchingarea").val("쇠고기")
	});
	$("#nsc_chicken").click(function() {
		$("#cate_searchingarea").val("닭고기")
	});
	$("#nsc_pig").click(function() {
		$("#cate_searchingarea").val("돼지고기")
	});
	$("#nsc_duck").click(function() {
		$("#cate_searchingarea").val("오리고기")
	});
	$("#nsc_egg").click(function() {
		$("#cate_searchingarea").val("알류")
	});
	$("#nsc_sheep").click(function() {
		$("#cate_searchingarea").val("양고기")
	});
	$("#nsc_etcmeat").click(function() {
		$("#cate_searchingarea").val("기타육류")
	});
	$("#nsc_seaweed").click(function() {
		$("#cate_searchingarea").val("김/해초")
	});
	$("#nsc_dryfish").click(function() {
		$("#cate_searchingarea").val("건어물")
	});
	$("#nsc_fish").click(function() {
		$("#cate_searchingarea").val("생선")
	});
	$("#nsc_seashell").click(function() {
		$("#cate_searchingarea").val("해산물/어패류")
	});
	$("#nsc_soi").click(function() {
		$("#cate_searchingarea").val("젓갈/장류")
	});
	$("#nsc_seamil").click(function() {
		$("#cate_searchingarea").val("수산가공식품")
	});
	$("#nsc_nut").click(function() {
		$("#cate_searchingarea").val("견과류")
	});
	$("#nsc_vege").click(function() {
		$("#cate_searchingarea").val("채소")
	});
	$("#nsc_fruit").click(function() {
		$("#cate_searchingarea").val("과일")
	});
	$("#nsc_etcfarm").click(function() {
		$("#cate_searchingarea").val("잡곡/혼합곡")
	});
	$("#nsc_rice").click(function() {
		$("#cate_searchingarea").val("쌀")
	});
	$("#nsc_dryfruit").click(function() {
		$("#cate_searchingarea").val("건과류")
	});
	$("#nsc_frofruit").click(function() {
		$("#cate_searchingarea").val("냉동과일")
	});
}
function sidecatecon_etc() {
	$("#nsc_etc, .etccontainer").mouseenter(function() {
		$("body").css({
			"overflow": "hidden",
		});
		$(".etccontainer").css({
			"top": "260px",
		});
	});
	$("#nsc_etc, .etccontainer").mouseleave(function() {
		$("body").css({
			"overflow": "scroll",
		});
		$(".etccontainer").css({
			"top": "-500px",
		});
	});
}

function sidecatecon_milk() {
	$("#nsc_milky, .milkcontainer").mouseenter(function() {
		$("body").css({
			"overflow": "hidden",
		});
		$(".milkcontainer").css({
			"top": "260px",
		});
	});
	$("#nsc_milky, .milkcontainer").mouseleave(function() {
		$("body").css({
			"overflow": "scroll",
		});
		$(".milkcontainer").css({
			"top": "-500px",
		});
	});
}

function sidecatecon_drink() {
	$("#nsc_drink, .drinkcontainer").mouseenter(function() {
		$("body").css({
			"overflow": "hidden",
		});
		$(".drinkcontainer").css({
			"top": "260px",
		});
	});
	$("#nsc_drink, .drinkcontainer").mouseleave(function() {
		$("body").css({
			"overflow": "scroll",
		});
		$(".drinkcontainer").css({
			"top": "-500px",
		});
	});
}

function sidecatecon_baking() {
	$("#nsc_baking, .breadcontainer").mouseenter(function() {
		$("body").css({
			"overflow": "hidden",
		});
		$(".breadcontainer").css({
			"top": "260px",
		});
	});
	$("#nsc_baking, .breadcontainer").mouseleave(function() {
		$("body").css({
			"overflow": "scroll",
		});
		$(".breadcontainer").css({
			"top": "-500px",
		});
	});
}

function sidecatecon_scning() {
	$("#nsc_scning, .scncontainer").mouseenter(function() {
		$("body").css({
			"overflow": "hidden",
		});
		$(".scncontainer").css({
			"top": "260px",
		});
	});
	$("#nsc_scning, .scncontainer").mouseleave(function() {
		$("body").css({
			"overflow": "scroll",
		});
		$(".scncontainer").css({
			"top": "-500px",
		});
	});
}

function sidecatecon_meat() {
	$("#nsc_meat, .meatcontainer").mouseenter(function() {
		$("body").css({
			"overflow": "hidden",
		});
		$(".meatcontainer").css({
			"top": "260px",
		});
	});
	$("#nsc_meating, .meatcontainer").mouseleave(function() {
		$("body").css({
			"overflow": "scroll",
		});
		$(".meatcontainer").css({
			"top": "-500px",
		});
	});
}

function sidecatecon_seafood() {
	$("#nsc_seafood, .seafoodcontainer").mouseenter(function() {
		$("body").css({
			"overflow": "hidden",
		});
		$(".seafoodcontainer").css({
			"top": "260px",
		});
	});
	$("#nsc_seafood, .seafoodcontainer").mouseleave(function() {
		$("body").css({
			"overflow": "scroll",
		});
		$(".seafoodcontainer").css({
			"top": "-500px",
		});
	});
}

function sidecatecon_farm() {
	$("#nsc_farm, .farmcontainer").mouseenter(function() {
		$("body").css({
			"overflow": "hidden",
		});

		$(".farmcontainer").css({
			"top": "260px",
		});
	});

	$("#nsc_farming, .farmcontainer").mouseleave(function() {
		$("body").css({
			"overflow": "scroll",
		});
		$(".farmcontainer").css({
			"top": "-500px",
		});
	});
}
/*
function sidecatecon_etc() {
	$("#nsc_etc, .etccontainer").mouseenter(function() {
		$(".etccontainer").css({
			"top": "250px",
		});
	});
	$("#nsc_etc, .etccontainer").mouseleave(function() {
		$(".etccontainer").css({
			"top": "-500px",
		});
	});
}

function sidecatecon_milk() {
	$("#nsc_milky, .milkcontainer").mouseenter(function() {
		$(".milkcontainer").css({
			"top": "250px",
		});
	});
	
	$("#nsc_milky, .milkcontainer").mouseleave(function() {
		$(".milkcontainer").css({
			"top": "-500px",
		});
	});
}

function sidecatecon_drink() {
	$("#nsc_drink, .drinkcontainer").mouseenter(function() {
		$(".drinkcontainer").css({
			"top": "250px",
		});
	});
	$("#nsc_drink, .drinkcontainer").mouseleave(function() {
		$(".drinkcontainer").css({
			"top": "-500px",
		});
	});
}
function sidecatecon_baking() {
	$("#nsc_baking, .breadcontainer").mouseenter(function() {
		$(".breadcontainer").css({
			"top": "250px",
		});
	});
	$("#nsc_baking, .breadcontainer").mouseleave(function() {
		$(".breadcontainer").css({
			"top": "-500px",
		});
	});
}

function sidecatecon_scning() {
	$("#nsc_scning, .scncontainer").mouseenter(function() {
		$(".scncontainer").css({
			"top": "250px",
		});
	});
	$("#nsc_scning, .scncontainer").mouseleave(function() {
		$(".scncontainer").css({
			"top": "-500px",
		});
	});
}

function sidecatecon_meat() {
	$("#nsc_meat, .meatcontainer").mouseenter(function() {
		$(".meatcontainer").css({
			"top": "250px",
		});
	});
	$("#nsc_meating, .meatcontainer").mouseleave(function() {
		$(".meatcontainer").css({
			"top": "-500px",
		});
	});
}

function sidecatecon_seafood() {
	$("#nsc_seafood, .seafoodcontainer").mouseenter(function() {
		$(".seafoodcontainer").css({
			"top": "250px",
		});
	});
	$("#nsc_seafood, .seafoodcontainer").mouseleave(function() {
		$(".seafoodcontainer").css({
			"top": "-500px",
		});
	});
}

function sidecatecon_farm() {
	$("#nsc_farm, .farmcontainer").mouseenter(function() {
		$(".farmcontainer").css({
			"top": "265px",
		});
	});

	$("#nsc_farming, .farmcontainer").mouseleave(function() {
		$(".farmcontainer").css({
			"top": "-500px",
		});
	});
}*/

function categorychanger() {
	var clck = localStorage.getItem('viewMode') === 'pan' ? 1 : 0;

	function updateView() {
		if (clck % 2 == 0) {
			$(".bar").css("display", "flex");
			$(".pan").css("display", "none");
			$("#seechanger").attr("src", "img/pan.png");
			localStorage.setItem('viewMode', 'bar');
		} else {
			$(".bar").css("display", "none");
			$(".pan").css("display", "flex");
			$("#seechanger").attr("src", "img/icon/market_menu_icon.png");
			localStorage.setItem('viewMode', 'pan');
		}
	}
	updateView();

	$(".catechanger").click(function() {
		clck++;
		updateView();
	});
}

function adescgotable() {
	$("#sim_button").click(function() {
		$("#adesc_input_area").val("sim")
	});
	$("#date_button").click(function() {
		$("#adesc_input_area").val("date")
	});
	$("#asc_button").click(function() {
		$("#adesc_input_area").val("asc")
	});
	$("#desc_button").click(function() {
		$("#adesc_input_area").val("dsc")
	});
}

function RSDR() {
	$.getJSON("searchdata.get", function(searchData) {
		$("#searchcontainer").empty();
		var br2 = $("<div></div>").text("검색어 순위").attr("id", "searchcontainerdiv");
		$("#searchcontainer").append(br2);
		$.each(searchData.lll, function(i, c) {
			var aa1 = $("<a></a>").append(c[0]);
			var br1 = $("<button></button>").append(i + 1 + ".", aa1);
			$("#searchcontainer").append(br1);
		});

		$("#searchcontainer button").click(function() {
			var searchTerm = $(this).find("a:last").text();
			$("#SR_inputarea").val(searchTerm);
		});
	});
}

function RealtimeSDR() {
	var socket = io.connect("http://ip:port");
	RSDR();
	$(".searchBut, .searchBut2").click(function() {
		var searchInput = $(this).hasClass("searchBut") ? $(".sec_searcharea_main") : $(".sec_searcharea_sub");
		var searchTerm = searchInput.val();

		$.getJSON("searchdata.reg", {
			marketsearchingarea: searchTerm,
			searchCount: 1
		}, function(regResult) {
			socket.emit("searchDataMsg", "access");
		});
	});

	socket.on("srvsearchData", function() {
		RSDR();
	});
	setInterval(RSDR, 10000);
}

function isEmpty(input) {
	return !input.trim();
}

function searchkeyword() {
	$(".searchBut").click(function() {
		if (isEmpty($('.sec_searcharea_main').val())) {
			return false;
		}
		return true;
	});
}

function searchkeyword2() {
	$(".searchBut2").click(function() {
		if (isEmpty($('.sec_searcharea_sub').val())) {
			return false;
		}
		return true;
	});
}

function pricesearchclicker() {
	$(".pricesearchBut").click(function() {
		$("#resultshowDIV2").css("display", "none")
		$("#resultshowDIV").css("display", "inline-block")
	});
}
function trcpshowing() {
	$.ajax({
		url: "marketpage.recipe.get",
		success: function(post) {
			$("#trcptable").empty();
			$.each(post.recipes, function(_, b) {
				var img = $("<img>").attr("src", b.rcpMainphoto).attr("class", "homeRecipeImg");
				var title = $("<h3></h3>").text(b.rcpName);
				var parts = $("<span></span>");
				var ndiv = $("<div></div>");
				if (b.subRecipes && b.subRecipes.length > 0 && b.subRecipes[0].rcpParts) {
					parts.html(b.subRecipes[0].rcpParts);
				} else {
					parts.text("재료 정보가 없습니다.");
				}
				var tdiv = $("<div></div>").append(title)
				var ttd1 = $("<td></td>").append(ndiv, img, tdiv);
				var ttd2 = $("<td></td>").append(parts).attr("class", "partrcptd")
				var a = $("<a></a>").append(ttd1, ttd2).attr("href", "recipe.go." + b.rcpId);
				var ttr = $("<tr></tr>").append(a).attr("class", "trcptabletr");
				$("#trcptable").append(ttr);
			});
		}
	});
}

function pageloader() {
	let currentPage = 1;
	const pageSize = 100;
	let isLoading = false;
	let query = '';

	function initializeMarketSearch() {
		const productContainer = document.querySelector('.divscroll');
		query = new URLSearchParams(window.location.search).get('marketsearchingarea') || '';

		productContainer.addEventListener('scroll', () => {
			if (isLoading) return;

			const scrollPosition = productContainer.scrollTop;
			const containerHeight = productContainer.clientHeight;
			const contentHeight = productContainer.scrollHeight;

			if (scrollPosition + containerHeight >= contentHeight - 100) {
				loadMoreItems();
			}
		});

		loadMoreItems();
	}

	function loadMoreItems() {
		if (isLoading) return;
		isLoading = true;

		const loadingIndicator = document.createElement('div');
		loadingIndicator.id = 'loading-indicator';
		loadingIndicator.textContent = 'Loading...';
		document.querySelector('.divscroll').appendChild(loadingIndicator);

		fetch(`marketsearchdata.go?marketsearchingarea=${encodeURIComponent(query)}&page=${currentPage}&size=${pageSize}`)
			.then(response => response.text())
			.then(html => {
				const tempDiv = document.createElement('div');
				tempDiv.innerHTML = html;
				const newItems = tempDiv.querySelector('.divscroll').innerHTML;
				document.querySelector('.divscroll').insertAdjacentHTML('beforeend', newItems);
				currentPage++;
				isLoading = false;

				document.getElementById('loading-indicator').remove();
			})
			.catch(error => {
				console.error('Error loading more items:', error);
				isLoading = false;
				document.getElementById('loading-indicator').remove();
			});
	}
	document.addEventListener('DOMContentLoaded', initializeMarketSearch);
}
