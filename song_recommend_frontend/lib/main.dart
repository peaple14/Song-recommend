// main.dart

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String fruitName = "Loading...";

  @override
  void initState() {
    super.initState();
    getFruitNameFromServer();
  }

  Future<void> getFruitNameFromServer() async {
    final response = await http.get(Uri.parse("http://localhost:8080/api/fruits/getFruitName"));

    if (response.statusCode == 200) {
      setState(() {
        fruitName = response.body;
      });
    } else {
      setState(() {
        fruitName = "Failed to load";
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Fruit App"),
      ),
      body: Center(
        child: Text(
          'Fruit Name: $fruitName',
          style: TextStyle(fontSize: 24),
        ),
      ),
    );
  }
}
