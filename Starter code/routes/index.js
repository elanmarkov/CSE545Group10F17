var express = require('express');
var router = express.Router();
//var currentUser;

// Get Homepage
router.get('/', ensureAuthenticated, function(req, res){
//	currentUser=req.user;
	res.render('index'); // index.handlebars

});

//
function ensureAuthenticated(req,res,next){
	if(req.isAuthenticated()){
		return next();
	} else{
		req.flash('error_msg','you are not logged in');
		res.redirect('/users/login');
	}
}
module.exports = router;
//module.exports = currentUser;
