/*----------------------challengeCommu------------------------*/
function connectCommunityFileUploadEvent() {
    let fileInputs = [
        "imgTemp2",
        "imgTemp3",
        "imgTemp4",
        "imgTemp5",
        "imgTemp6"
    ];

    for (let i = 1; i < fileInputs.length; i++) {
        $(input[name='${fileInputs[i]}']).parent().hide();
    }

	    for (let i = 0; i < fileInputs.length - 1; i++) {
        $(input[name='${fileInputs[i]}']).on("change", function () {
            if (this.files.length > 0) {
                $(input[name='${fileInputs[i + 1]}']).parent().fadeIn();
            }
        });
    }
}

function previewImage(input) {
	
    var file = input.files[0];
    var previewImg = input.closest('td').querySelector('.preview-img');

    if (file) {
        var reader = new FileReader();
        reader.onload = function(e) {
            previewImg.src = e.target.result;
            previewImg.style.display = 'block';
        };
        reader.readAsDataURL(file);
    } else {
        previewImg.style.display = 'none';
    }
}

function connectCommunityRreplyInputEvent() {
	var rReplyInputBtn = $(".showcprInputTr");

	rReplyInputBtn.click(function() {
		var replyNo = $(this).data("reply-no");
		$("#cprInputTr-" + replyNo).toggle();
	});
}

function connectChallengeCommuAreaEvent() {
	var reg = $("#challengeCommunityWriteArea");
	$("#challengeCommuSearchArea #challengePostRegGo").click(function() {
		if (reg.css("top") == "0px") {
			reg.css("top", "-100%");
		} else {
			reg.css("top", "0px");
		}
	});
	$("#challengeCommunityWriteArea #challengeCommuRegCloseBtn").click(function() {
		if (reg.css("top") == "0px") {
			reg.css("top", "-100%");
		} else {
			reg.css("top", "0px");
		}
	});
	
	var update = $("#challengeCommuDetailArea #challengePostUpdateArea");
	$("#challengeCommuDetailArea #commuPostUpdateGo").click(function() {
		if (update.css("top") == "0px") {
			update.css("top", "-100%");
		} else {
			update.css("top", "0px");
		}
	});
	$("#challengePostUpdateArea #challengeclosePopup").click(function() {
		if (update.css("top") == "0px") {
			update.css("top", "-100%");
		} else {
			update.css("top", "0px");
		}
	});
	
}
function updateChallengeCommunityPost() {
	var socket = io.connect("http://ip:port");
	$("#challengeCommuUpdateFormBtn").click(function() {
		var f = $("#challengeUpdateForm")[0];
		if (challengeRegCheck(f)) {
			var data = new FormData(f);
			$.ajax({
				url: "challenge.community.update.do",
				type: "POST",
				data: data,
				contentType: false,
				processData: false,
				success: function(result) {
					alert(result.result);
					socket.emit("challengeMsg", "success");
				}
			});
			socket.on("srvchallengeMsg", function(_) {
				location.reload(true);
			});
			}
	});
}
function regChallengeCommunityPost() {
	var socket = io.connect("http://ip:port");
	$("#challengeCommuRegFormBtn").click(function() {
		var f = $("#challengeCommuRegForm")[0];
		if (challengeRegCheck(f)) {
			var data = new FormData(f);
			$.ajax({
				url: "challenge.community.post.write",
				type: "POST",
				data: data,
				contentType: false,
				processData: false,
				success: function(result) {
					alert(result.result);
					socket.emit("challengeMsg", "success");
				}
			});
			socket.on("srvchallengeMsg", function(_) {
				location.href = "challenge.community.go";
			});
		}
	});
}
/*----------------------foodGuideCommu------------------------*/
function connectFGUploadBtnEvent() {
	var fgPlusBtn = $("#fgPlusBtn");
	var btnCount = 2;
	fgPlusBtn.click(function() {
		var inputName = "#fgCommuRegArea #file".concat(btnCount);
		$(inputName).removeClass("fgImgHide");
		btnCount++;
		if (btnCount > 5) {
			fgPlusBtn.css("color", "#9fa1a0");
		}
	});
}

function connectFoodGuideCommuAreaEvent() {
	var reg = $("#foodGuideArea #fgCommuRegArea");
	$("#foodGuidePostRegGo").on('click', function() {
		if (reg.css("right") == "-650px") {
			reg.css("right", "30px")
		} else {
			reg.css("right", "-650px")
		}
	});
	$("#fgCommuRegArea #closePopup").click(function() {
		if (reg.css("right") == "-650px") {
			reg.css("right", "30px")
		} else {
			reg.css("right", "-650px")
		}
	});
}

function connectFoodGuideCommuAreaUpdateEvent() {
	var update = $("#foodGuidePostDetailArea #fgCommuUpdateArea");
	$("#foodGuidePostDetailArea #foodGuidePostUpdateGo").click(function() {
		if (update.css("right") == "-650px") {
			update.css("right", "30px")
		} else {
			update.css("right", "-650px")
		}
	});
	$("#fgCommuUpdateArea #closePopup").click(function() {
		if (update.css("right") == "-650px") {
			update.css("right", "30px")
		} else {
			update.css("right", "-650px")
		}
	});
}

function connectFoodGuideCommuCommentEvent() {
	var text = $("#foodGuidePostTextorComment #foodGuidePostText");
	var reply = $("#foodGuidePostTextorComment #foodGuidePostComments");
	$("#commentOpenArea button").click(function() {
		if (reply.is(":hidden")) {
			text.hide();
			reply.show();
		} else {
			text.show();
			reply.hide();
		}
	});
}

function deleteFoodGuideCommunityPost(no) {
	var socket = io.connect("http:/ip:port");
	var q = confirm("삭제하시겠습니까?");
	if (q) {
		$.getJSON("foodguide.community.post.delete?no=" + no, function(_) {
			socket.emit("foodGuideMsg", "success");
		});
	}
	socket.on("srvFoodGuideMsg", function(_) {
		location.href = "foodguide.community.go";
		getFoodGuideCommunityPosts();
	});
}

function foodGuidePostBxslider() {
	if ($('#foodGuideBxslider').length > 0) {
		var datas = $("#foodGuideImgData div");
		for (var i = 1; i <= 5; i++) {
			var imgSrc = datas.attr("data-img" + i);
			if (imgSrc) {
				var img = $("<img>").attr("src", "community.photo/" + imgSrc);
				$("#foodGuidePostDetailTbl #foodGuideBxslider").append($("<div></div>").append(img));
			}
		}
		$('#foodGuideBxslider').bxSlider({
			mode: 'fade',
			captions: true,
			slideWidth: 600
		});
	}
}

var foodGuideState = {
    currentPage: 1,
    totalPages: null,
    isLoading: false,
    searchMode: false,
    searchQuery: "",
    searchPage: 1,
    totalSearchPages: null,
    isSearching: false
};

function getFoodGuideCommunityPosts() {
    if (foodGuideState.isLoading) return;
    foodGuideState.isLoading = true;

    $.getJSON("foodguide.community.post.get?page=" + foodGuideState.currentPage, 
			function (foodPosts) {
        if (!foodPosts.foodguideCommuList) return;
        if (foodGuideState.totalPages === null) {
            foodGuideState.totalPages = foodPosts.pageCount;
        }
        $.each(foodPosts.foodguideCommuList, function (_, b) {
            appendFoodGuidePost(b);
        });
        foodGuideState.isLoading = false;
    });
}
function getSearchFoodGuideCommunityPosts() {
    if (foodGuideState.isSearching) return;
    foodGuideState.isSearching = true;
    $.getJSON("foodguide.community.search?search=" + foodGuideState.searchQuery + "&page=" + foodGuideState.searchPage, function (foodPosts) {
        if (!foodPosts.foodguideCommuList) return;
        if (foodGuideState.totalSearchPages === null) {
            foodGuideState.totalSearchPages = foodPosts.pageCount;
        }
        $.each(foodPosts.foodguideCommuList, function (_, b) {
            appendFoodGuidePost(b);
        });
        foodGuideState.isSearching = false;
    });
}

function appendFoodGuidePost(post) {
	var img = $("<img>").attr("src", "community.photo/" + post.img1);
	    var imgDiv = $("<div></div>").prepend(img).attr("class", "imgDiv");
	    var titleDiv = $("<div></div>").text(post.title).attr("class", "title");

	    var date = new Date(post.date);
	    var formattedDate = date.getFullYear() + "-" +
	        String(date.getMonth() + 1).padStart(2, '0') + "-" +
	        String(date.getDate()).padStart(2, '0');
	    var dateDiv = $("<div></div>").text(formattedDate).attr("class", "date");

	    var bigDiv = $("<div></div>").append(imgDiv, titleDiv, dateDiv)
	        .attr("class", "foodGuideTbl")
	        .attr("onclick", "foodGuidePostDetail(" + post.no + ");");
	    $("#foodGuidePosts").append(bigDiv);
}

function attachFoodGuideCommunityScrollEvent() {
    $(window).on("scroll", function () {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            if (foodGuideState.searchMode) {
                if (foodGuideState.searchPage < foodGuideState.totalSearchPages) {
                    foodGuideState.searchPage++;
                    getSearchFoodGuideCommunityPosts();
                }
            } else {
                if (foodGuideState.currentPage < foodGuideState.totalPages) {
                    foodGuideState.currentPage++;
                    getFoodGuideCommunityPosts();
                }
            }
        }
    });
}

function attachFoodGuideCommunitySearchEvent() {
    $("#foodGuideArea #searchBtn").click(function () {
        foodGuideState.searchQuery = $("#foodGuideArea #searchInput").val().trim();
        if (foodGuideState.searchQuery === "") return;
        $("#foodGuidePosts").empty(); // 기존 게시글 삭제
        foodGuideState.searchPage = 1;
        foodGuideState.totalSearchPages = null;
        foodGuideState.searchMode = true;
        getSearchFoodGuideCommunityPosts();
    });
}

function regFoodGuideCommunityPost() {
	var socket = io.connect("http://ip:port");
	$("#fgCommuUploadForm #fgCommuUploadBtn").click(function() {
		var f = $("#fgCommuUploadForm")[0];
		if (foodGuideRegCheck(f)) {
			var data = new FormData(f);
			$.ajax({
				url: "foodguide.community.post.write",
				type: "POST",
				data: data,
				contentType: false,
				processData: false,
				success: function(result) {
					alert(result.result);
					getFoodGuideCommunityPosts();
					socket.emit("foodGuideMsg", "success");
				}
			});
		}
	});
	socket.on("srvFoodGuideMsg", function(_) {
		getFoodGuideCommunityPosts();
	});
}

function regFoodGuideCommunityPostComment(postNo, postType, token) {
	var socket = io.connect("ip:port");
	var text = $("#foodGuidePostCommentText input").val();
	$.getJSON("f.community.comment.write?text=" + text + "&post=" + postNo + "&postType=" + postType + "&token=" + token, function(result) {
		alert(result.result);
		socket.emit("foodGuideMsg", "success");
	});
	socket.on("srvFoodGuideMsg", function(_) {
		location.reload(true);
	});
}

function regFoodGuideCommunityPostReply(parNo, token, button) {
	var socket = io.connect("http://ip:port");
	var text = $(button).closest('tr').find('input').val();
	$.getJSON("f.community.reply.write?text=" + text + "&parNo=" + parNo + "&token=" + token, function(result) {
		alert(result.result);
		socket.emit("foodGuideMsg", "success");
	});
	socket.on("srvFoodGuideMsg", function(_) {
		location.reload(true);
	});
}

function updateFoodGuideCommunityPost() {
	var socket = io.connect("http://ip:port");
	$("#fgCommuUpdateArea #fgCommuUpdateBtn").click(function() {
		var f = $("#fgCommuUpdateForm")[0];
		if (foodGuideUpdateCheck(f)) {
			var data = new FormData(f);
			$.ajax({
				url: "foodguide.community.update",
				type: "POST",
				data: data,
				contentType: false,
				processData: false,
				success: function(result) {
					alert(result.result);
					getFoodGuideCommunityPosts();
					socket.emit("foodGuideMsg", "success");
				}
			});
		}
	});
	socket.on("srvFoodGuideMsg", function(_) {
		location.reload(true);
	});
}

function deleteFCommunityPostComment(no) {
	var socket = io.connect("http://ip:port");
	var q = confirm("삭제하시겠습니까?");
	if (q) {
		$.getJSON("f.community.post.comment.delete?no=" + no, function(result) {
			alert(result.result);
			socket.emit("foodGuideMsg", "success");
		});
	}
	socket.on("srvFoodGuideMsg", function(_) {
		location.reload(true);
	});
}

function deleteFCommunityPostReply(no) {
	var socket = io.connect("http://ip:port");
	var q = confirm("삭제하시겠습니까?");
	if (q) {
		$.getJSON("f.community.post.reply.delete?no=" + no, function(result) {
			alert(result.result);
			socket.emit("foodGuideMsg", "success");
		});
	}
	socket.on("srvFoodGuideMsg", function(_) {
		location.reload(true);
	});
}
/*------------------------freeCommu--------------------------*/

function connectFreeCommuAreaEvent() {
	var reg = $("#freeCommuArea #freePostRegArea");
	$("#freeCommuArea #freePostRegGo").click(function() {
		if (reg.css("top") == "0px") {
			reg.css("top", "-100%")
		} else {
			reg.css("top", "0px")
		}
	});
	$("#freePostRegArea #closePopup").click(function() {
		if (reg.css("top") == "0px") {
			reg.css("top", "-100%")
		} else {
			reg.css("top", "0px")
		}
	});

	var update = $("#freeCommuDetailArea #freePostUpdateArea");
	$("#freeCommuDetailArea #freePostUpdateGo").click(function() {
		if (update.css("top") == "0px") {
			update.css("top", "-100%")
		} else {
			update.css("top", "0px")
		}
	});
	$("#freePostUpdateArea #closePopup").click(function() {
		if (update.css("top") == "0px") {
			update.css("top", "-100%")
		} else {
			update.css("top", "0px")
		}
	});
}

function connectFreeCommuFilePlusBtnEvent() {
	var fPlusBtn = $("#freePostRegArea #fileSection button");
	var btnCount = 1;
	fPlusBtn.click(function() {
		var inputName = "#freePostRegArea #fileSection #file".concat(btnCount);
		$(inputName).removeClass("freeFileHide");
		btnCount++;
		if (btnCount > 5) {
			fPlusBtn.addClass("freeFileHide");
		}
	});
}

function connectFreeCommuPostLike(postNo, postType, token) {
	var socket = io.connect("http://ip:port");
	$.getJSON("free.community.post.like?post=" + postNo + "&postType=" + postType + "&token=" + token, function(result) {
		alert(result.result);
		socket.emit("freeMsg", "success");
	});
	socket.on("srvfreeMsg", function() {
		location.reload(true);
	});
}

function connectFreeCommuReplyEvent() {
	var reply = $("#freeCommuDetailArea #commentSection #freeCommuCommentArea");
	$("#freeCommuDetailArea #commentSection #showComment").click(function() {
		if (reply.is(":hidden")) {
			reply.show();
		} else {
			reply.hide();
		}
	});
}

function regFreeCommunityPostComment(post, postType, token) {
	var socket = io.connect("http://ip:port");
	var text = $("#regFreeRegCommentArea #text").val();
	if (text.trim() === "") {
		alert("댓글을 입력해주세요");
		return;
	}
	$.getJSON("f.community.comment.write?text=" + text + "&post=" + post + "&postType=" + postType + "&token=" + token, function(result) {
		alert(result.result);
		socket.emit("freeMsg", "success");
	});

	socket.off("srvfreeMsg").on("srvfreeMsg", function() {
		location.reload(true);
	});
}

function regFreeCommunityPostReply(parNo, token, button) {
	var socket = io.connect("http://ip:port");
	var text = $(button).closest('tr').find('input').val();
	if (text.trim() === "") {
		alert("댓글을 입력해주세요");
		return;
	}
	$.getJSON("f.community.reply.write?text=" + text + "&parNo=" + parNo + "&token=" + token, function(result) {
		alert(result.result);
		socket.emit("freeMsg", "success");
	});
	socket.off("srvfreeMsg").on("srvfreeMsg", function() {
		location.reload(true);
	});
}

function updateFreeCommunityPost() {
	var socket = io.connect("http://ip:port");
	$("#freePostUpdateArea #freeCommuPostUpdateBtn").click(function() {
		var f = $("#freePostUpdateArea #freeCommuUpdateForm")[0];
		if (freeRegOrUpdateCheck(f)) {
			var data = new FormData(f);
			$.ajax({
				url: "free.community.update",
				type: "POST",
				data: data,
				contentType: false,
				processData: false,
				success: function(result) {
					alert(result.result);
					socket.emit("freeMsg", "success");
				}
			});
		}
	});
	socket.on("srvfreeMsg", function(_) {
		location.reload(true);
	});
}

function regFreeCommunityPost() {
	var socket = io.connect("http://ip:port");
	$("#freePostRegArea #freeCommuPostregBtn").click(function() {
		var f = $("#freePostRegArea #freeCommuRegForm")[0];
		if (freeRegOrUpdateCheck(f)) {
			var data = new FormData(f);
			$.ajax({
				url: "free.community.post.write",
				type: "POST",
				data: data,
				contentType: false,
				processData: false,
				success: function(result) {
					alert(result.result);
					socket.emit("freeMsg", "success");
				}
			});
		}
	});
	socket.on("srvfreeMsg", function(_) {
		location.reload(true);
	});
}