import 'package:flutter/material.dart';

import '../utils/ApiService.dart';

class RegisterScreen extends StatefulWidget {
  const RegisterScreen({Key? key}) : super(key: key);

  @override
  State<RegisterScreen> createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  TextEditingController _usernameController = TextEditingController();
  TextEditingController _passwordController = TextEditingController();

  final ApiService apiService = ApiService();

  Future<void> invalid() async {
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Sorry :('),
          content: const SingleChildScrollView(
            child: ListBody(
              children: <Widget>[
                Text('Something went wrong :('),
                Text('May be username already used from someone else. '),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: const Text('Try Again'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
  Future<void> _login() async {
    final username = _usernameController.text;
    final password = _passwordController.text;
    Map<String, dynamic> body = {
      'username': username,
      'pass': password,
    };
    apiService.registerRequest(body).then((value) => {
      if (value)
        {Navigator.pushReplacementNamed(context, '/login')}
      else
        {invalid()}
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      body: Container(
        color: Colors.white,
        constraints: BoxConstraints.expand(),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            Container(
              color: Colors.yellow,
              constraints: BoxConstraints.expand(height: 75),
              child: Center(
                child: Image.asset('assets/images/logo.png'),
              ),
            ),
            SizedBox(height: 65),
            Text(
              "Sign Up for Snapchat",
              style: TextStyle(
                color: Colors.black,
                fontSize: 25.0,
                fontWeight: FontWeight.bold,
              ),
            ),
            SizedBox(
              height: 25,
            ),
            Container(
              padding: EdgeInsets.symmetric(horizontal: 40.0, vertical: 8.0),
              child: TextField(
                controller: _usernameController,
                cursorColor: Colors.green,
                cursorWidth: 3.0,
                decoration: InputDecoration(
                  labelText: "USER NAME OR EMAIL",
                  floatingLabelBehavior: FloatingLabelBehavior.always,
                  labelStyle: TextStyle(color: Colors.grey, fontSize: 13.0),
                  focusColor: Colors.black,
                  focusedBorder: UnderlineInputBorder(
                    borderSide: BorderSide(color: Colors.grey),
                  ),
                ),
              ),
            ),
            SizedBox(
              height: 25,
            ),
            Container(
              padding: EdgeInsets.symmetric(horizontal: 40.0, vertical: 10.0),
              child: TextField(
                controller: _passwordController,
                obscureText: true,
                cursorColor: Colors.green,
                cursorWidth: 3.0,
                decoration: InputDecoration(
                  labelText: "PASSWORD",
                  floatingLabelBehavior: FloatingLabelBehavior.always,
                  labelStyle: TextStyle(color: Colors.grey, fontSize: 13.0),
                  focusColor: Colors.grey,
                  focusedBorder: UnderlineInputBorder(
                    borderSide: BorderSide(color: Colors.grey),
                  ),
                ),
              ),
            ),
            SizedBox(
              height: 25,
            ),
            GestureDetector(
              onTap: _login,
              child: Container(
                  width: 200,
                  height: 50,
                  padding: EdgeInsets.symmetric(horizontal: 25),
                  decoration: BoxDecoration(
                    color: Colors.blue,
                    borderRadius: BorderRadius.circular(25.0),
                  ),
                  child: Center(
                      child: Text(
                        "Sign Up",
                        style: TextStyle(
                          color: Colors.white,
                          fontSize: 25.0,
                          fontWeight: FontWeight.bold,
                        ),
                      ))),

            ),
          ],
        ),
      ),
    );
  }
}