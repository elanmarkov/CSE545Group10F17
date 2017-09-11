var express = require('express');
var router = express.Router();
var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;

//importing Mongodb database schema
var User= require('../models/user');
let currentUser;

// Register
router.get('/register', function(req, res){
	res.render('register');
});

// Login
router.get('/login', function(req, res){
	res.render('login');
});

//login history
router.get('/loginhistory', function(req, res){
	res.render('index');
});

//behavioral logs
router.get('/logs', function(req, res){
	res.render('logs');
});

// Register
router.post('/register', function(req, res){
	var name = req.body.name;
	var email = req.body.email;
	var username = req.body.username;
	var password = req.body.password;
	var password2 = req.body.password2;

	//Validation
	req.checkBody('name', 'Name is required').notEmpty();
	req.checkBody('email', 'Email is required').notEmpty();
	req.checkBody('email', 'Email is Not valid').isEmail();

	req.checkBody('username', 'username required').notEmpty();	
	req.checkBody('password', 'Name is required').notEmpty();
	req.checkBody('password2', 'Name is required').equals(req.body.password);	

	var errors = req.validationErrors();
	if(errors){
		res.render('register',{errors:errors});
	}
	else{
		var newUser = new User({
			name: name,
			email:email,
			username: username,
			password: password,
		})
		User.createUser(newUser, function(err, user){
			if(err) throw err;
		});

		req.flash('success_msg', 'You are registered and can now login');
		res.redirect('/users/login');	
	}
});

//login authenticate
passport.use(new LocalStrategy(
  function(username, password, done) {
   User.getUserByUsername(username, function(err, user){
   	currentUser = user;
   	if(err) throw err;
   	if(!user){
   		return done(null, false, {message: 'Unknown User'});
   	}

   	User.comparePassword(password, user.password, function(err, isMatch){
   		if(err) throw err;
   		if(isMatch){
   			user.history.addToSet(new Date());		
			user.save(function(err) {
			if(err) throw err;
			});
   			return done(null, user);
   		} else {
   			return done(null, false, {message: 'Invalid password'});
   		}
   	});
   });
}));


passport.serializeUser(function(user, done) {
  done(null, user.id);
});


passport.deserializeUser(function(id, done) {
  User.getUserById(id, function(err, user) {
    done(err, user);
  });
});

router.post('/login',
  passport.authenticate('local', {successRedirect:'/', failureRedirect:'/users/login',failureFlash: true}),
  function(req, res) {
    res.redirect('/');
});

//logout 
router.get('/logout', function(req,res){
	req.logout();
	req.flash('success_msg', 'you are logged out');
	res.redirect('/users/login');
})
  
module.exports = router;