var express = require('express');
var mongoose = require('mongoose');

var UserSchema = new mongoose.Schema({
    name: String,
    user: String,
    pass: String
});

var User = mongoose.model('User', UserSchema);

// Retrieves login creds
retrievedLogin(function(cred) {
    var instance = new User();

    instance.name = cred[0];
    instance.user = cred[1];
    instance.pass = cred[2];

    instance.save();
});

var app = express();

app.set('views', __dirname);
app.set('view engine', 'jade');

app.use(require('body-parser').urlencoded({
    extended: true
}));

app.get('/', function(req, res) {
    res.render('index', {});
});

app.post('/', function(req, res) {
    // Validate and Sanitize Untrusted Inputs
    if(isTrustedUser(req.body.user) == false){
        message: 'Invalid username or password!'
    }else if (isTrustedPass(req.body.pass) == false) {
        message: 'Invalid username or password!'
    }

    User.findOne({
        user: req.body.user,
        pass: req.body.pass
    }, function(err, user) {
        if (err) {
            return res.render('index', {
                message: "error"
            });
        }

        if (!user) {
            return res.render('index', {
                message: 'Sorry!'
            });
        }

        if (isTrustedName(user.name) == false) {
            message: 'Invalid username or password!'
        }

        return res.render('index', {
            message: 'Welcome back ' + user.name + '!!!'
        });
    });
});
