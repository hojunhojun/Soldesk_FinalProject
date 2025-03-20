var createError = require("http-errors");
var express = require("express");
var path = require("path");
var cookieParser = require("cookie-parser");
var logger = require("morgan");

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');

var app = express();


app.set("views", path.join(__dirname, "views"));
app.set("view engine", "jade");

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, "public")));

var io = require("socket.io")();
io.listen([port]);

io.sockets.on("connection", function(socket){
    console.log("access");
    socket.on("searchDataMsg", function(){
        io.sockets.emit("srvsearchData", "access");
    });
    socket.on("challengeMsg", function(){
       io.sockets.emit("srvchallengeMsg", "access");
    });
    socket.on("foodGuideMsg", function(txt){
      io.sockets.emit("srvFoodGuideMsg", txt); 
    });
    socket.on("freeMsg", function(txt){
       io.sockets.emit("srvfreeMsg", txt);
    });
});

app.use(function (req, res, next) {
    next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
    // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get('env') === 'development' ? err : {};
  
    // render the error page
    res.status(err.status || 500);
    res.render('error');
  });
  
  module.exports = app;
  