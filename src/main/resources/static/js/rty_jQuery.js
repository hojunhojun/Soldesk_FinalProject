function connectJoinInputEvent() {
	$("#joinIdInput").keyup(function() {
		var id = $(this).val();

		$.getJSON("member.id.get?id=" + id, function(memberData) {
			if (memberData.member[0] == null) {
				$("#joinIdInput").css("color", "black");
			} else {
				$("#joinIdInput").css("color", "red");
			}
		});
	});
	$("#joinNickInput").keyup(function() {
		var nick = $(this).val();

		$.getJSON("member.nick.get?nick=" + nick, function(memberData) {
			if (memberData.member[0] == null) {
				$("#joinNickInput").css("color", "black");
			} else {
				$("#joinNickInput").css("color", "red");
			}
		});
	});
	$("#updateNickInput").keyup(function() {
		var nick = $(this).val();

		$.getJSON("member.nick.get?nick=" + nick, function(memberData) {
			if (memberData.member[0] == null) {
				$("#updateNickInput").css("color", "black");
			} else {
				$("#updateNickInput").css("color", "red");
			}
		});
	});
}

function mainbxslider() {
	if ($("#mainImgArea .bxslider").length > 0) {
		var s = $('#mainImgArea .bxslider').bxSlider({
			auto: true,
			autoControls: true,
			stopAutoOnClick: true,
			pager: true,
			slideWidth: 600,
			adaptiveHeight: false,
			responsive: false
		});

		$.ajax({
			url: "mainpage.recipe.get",
			success: function(post) {
				$("#mainImgArea .bxslider").empty();
				$.each(post.recipes, function(_, b) {
					var img = $("<img>").attr("src", b.rcpMainphoto).attr("class", "homeRecipeImg");
					var title = $("<h3></h3>").text(b.rcpName).attr("class", "homeRecipeTitle");
					var a = $("<a></a>").append(img, title).attr("href", "recipe.go." + b.rcpId);
					var imgDiv = $("<div></div>").append(a);
					$("#mainImgArea .bxslider").append(imgDiv);
				});
				s.reloadSlider();
			}
		});
	}
}
function connectLoginAreaEvent() {
	var login = $("#loginTbl");
	$("#login button").click(function() {
		if (login.css("right") == "-400px") {
			login.css("right", "25px")
		} else {
			login.css("right", "-400px")
		}
	});
}
function connectMemberAddrInputEvent() {
	$(".memberAddr1Input, .memberAddr2Input").click(function() {
		new daum.Postcode({
			oncomplete: function(data) {
				$(".memberAddr1Input").val(data.zonecode);
				$(".memberAddr2Input").val(data.roadAddress);
			}
		}).open();
	});
}
// 메뉴 컨트롤러
function connectMenuAreaEvent() {
	$(".menu").mouseenter(function() {
		$(".smallMenusArea").css("top", "-180px");
		$(this).css("background-color", "#f9faebE6");
		$(this).find(".smallMenusArea").css("top", "120px");
	});

	$(".smallMenusArea").mouseleave(function() {
		$(this).css("top", "-180px");
	});
	$(".menu").mouseleave(function() {
		$(this).css("background-color", "transparent");
	});
}

function showJoinImageInputValue() {
	$(document).ready(function() {
		$("input[name='photoTemp']").change(function(event) {
			var file = event.target.files[0];

			if (file) {
				var reader = new FileReader();
				reader.onload = function(e) {
					// 해당하는 tr이 없으면 동적으로 추가
					if ($("#profileRow").length === 0) {
						$("input[name='photoTemp']").closest("tr").after(`
	                            <tr id="profileRow">
	                                <td align="center" style="padding-top:5px; padding-bottom:5px; border-bottom: #FFCC80 solid 2px;">
	                                    <img id="profilePreview" src="" alt="프로필 이미지" style="max-width: 150px; display: none;"/>
	                                </td>
	                            </tr>
	                        `);
					}
					$("#profilePreview").attr("src", e.target.result).show();
				};
				reader.readAsDataURL(file);
			} else {
				$("#profileRow").remove(); // 파일이 없으면 tr 제거
			}
		});
	});
}

function showUpdateImageInputValue() {
	$(document).ready(function() {
		$("input[name='photo2']").change(function(event) {
			var file = event.target.files[0];

			if (file) {
				var reader = new FileReader();
				reader.onload = function(e) {
					// "photo2" input의 다음 tr을 삭제
					$("input[name='photo2']").closest("tr").next("tr").remove();

					// 새로운 tr을 추가하여 업로드된 이미지를 표시
					$("input[name='photo2']").closest("tr").after(`
                        <tr id="profileRow">
                            <td align="center" style="padding-top:5px; padding-bottom:5px; border-bottom: #FFCC80 solid 2px;">
                                <img id="profilePreview" src="${e.target.result}" alt="프로필 이미지" style="max-width: 150px; display: block;"/>
                            </td>
                        </tr>
                    `);
				};
				reader.readAsDataURL(file); // 파일을 DataURL로 읽어들임
			}
		});
	});
}


$(function() {
	/*-----------market.js-----------*/
	connectCommunityRreplyInputEvent();
	sidecatecon_farm();
	sidecatecon_seafood();
	sidecatecon_meat();
	sidecatecon_scning();
	sidecatecon_baking();
	sidecatecon_drink();
	sidecatecon_milk();
	sidecatecon_etc();
	categorychecker();
	searchiconclicker();
	sidemenuinout();
	categorychanger();
	adescgotable();
	RealtimeSDR();
	searchkeyword();
	searchkeyword2();
	pricesearchclicker();
	trcpshowing();
	pageloader();
	/*------------arcade.js--------------*/
	commonSenseQuizEventController();
	connectQuizAreaEvent();
	connectRouletteController();
	foodQuizEventController();
	regQuizPost();
	quizAllToggleInput();
	quizRegToggleInput();

	/*----------foodGuideCommu------------*/
	attachFoodGuideCommunityScrollEvent(); 
	attachFoodGuideCommunitySearchEvent();
	connectFGUploadBtnEvent();
	connectFoodGuideCommuAreaEvent();
	connectFoodGuideCommuAreaUpdateEvent();
	connectFoodGuideCommuCommentEvent();
	foodGuidePostBxslider();
	getFoodGuideCommunityPosts(); 
	regFoodGuideCommunityPost();
	updateFoodGuideCommunityPost();

	/*------------freeCommu--------------*/
	connectFreeCommuAreaEvent();
	connectFreeCommuFilePlusBtnEvent();
	connectFreeCommuReplyEvent();
	updateFreeCommunityPost();
	regFreeCommunityPost();

	/*---------------Main----------------*/
	mainbxslider();
	connectLoginAreaEvent();
	connectMemberAddrInputEvent();
	connectMenuAreaEvent();

	/*---------------Challenge-------------*/
	updateChallengeCommunityPost();
	connectChallengeCommuAreaEvent();
	regChallengeCommunityPost();
	connectCommunityFileUploadEvent();
	showUpdateImageInputValue();
	showJoinImageInputValue();
	connectJoinInputEvent();
	changeImgEvent();
});