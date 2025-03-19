function logOut() {
	var really = confirm("로그아웃하시겠습니까?");
	if (really) {
		location.href = "logout.do";
	}
}

function CSQuizGo(){
	location.href="arcade.commonsense.quiz.do";
}
function foodQuizGo(){
	location.href="arcade.food.quiz.do";
}

function memberInfoGoCheck() {
	location.href = "member.info.check.go";
}

function byeDo() {
	location.href = "member.bye.go";
}
function goChallenge() {
	location.href = "challenge.community.go";
}

function challengeCommunityDetailGo(post, type, curPage) {
	location.href = "challenge.community.post.detail?post=" + post + "&type=" + type + "&curPage=" + curPage;
}
function communityPageChange(page) {
	location.href = "challenge.community.page.change?page=" + page;
}
function communityPostUpdate(post) {
	location.href = "challenge.community.update.go?post=" + post;
}

function communityPostDelete(no, curPage) {
    var really = confirm("삭제하시겠습니까?");
    if (really) {
        location.href = "challenge.community.post.delete?no=" + no + "&curPage=" + curPage;
    }
}

function communityCommentDelete(no, post, curPage) {
	location.href = "challenge.community.comment.delete?no=" + no + "&post=" + post + "&curPage=" + curPage;
}

function communityReplyDelete(no, post, curPage) {
	location.href= "challenge.community.reply.delete?no=" + no + "&post=" + post + "&curPage=" + curPage;
}

function challengeCommunityLike(post, token, curPage) {
	location.href = "challenge.community.post.like?post=" + post + "&token=" + token + "&curPage=" + curPage;
}

function challengeCommunityUnLike(post, token, curPage) {
	location.href = "challenge.community.post.unlike?post=" + post + "&token=" + token + "&curPage=" + curPage;
}

function foodGuidePostDetail(post) {
	location.href = "foodguide.community.post.detail?post=" + post;
}
function foodGuidePostDelGo(no) {
	location.href = "foodguide.community.post.delete?no=" + no;
}

function freeCommunityPageChange(page) {
	location.href = "free.community.page.change?page=" + page;
}
function freeCommuPostDetail(post, curPage) {
	location.href = "free.community.post.detail?post=" + post + "&curPage=" + curPage;
}

function getFreeCommuCatePost(cate) {
	location.href = "free.community.post.category?cate=" + cate;
}
function clearCate() {
	location.href = "free.community.go";
}

function freeCommunityPostDelete(no) {
	var really = confirm("삭제하시겠습니까?");
	if (really) {
		location.href = "free.community.post.delete?no=" + no;
	}
}
