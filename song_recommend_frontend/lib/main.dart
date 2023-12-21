import 'package:flutter/material.dart';
import 'package:flutter_sound/flutter_sound.dart';
import 'package:http/http.dart' as http;
import 'package:path_provider/path_provider.dart';


void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Sound Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  late FlutterSoundPlayer _player;
  late FlutterSoundRecorder _recorder;
  bool _isRecording = false;
  bool _isPlaying = false;

  @override
  void initState() {
    super.initState();
    _player = FlutterSoundPlayer();
    _recorder = FlutterSoundRecorder();
  }

  // 녹음 시작
  Future<void> _startRecording() async {
    try {
      await _recorder.openRecorder();
      await _recorder.startRecorder(
        toFile: 'example.aac',
        codec: Codec.aacADTS,
      );
      setState(() {
        _isRecording = true;
      });
    } catch (err) {
      print('Error: $err');
    }
  }

  // 녹음 중지
  Future<void> _stopRecording() async {
    await _recorder.stopRecorder();
    await _recorder.closeRecorder();
    setState(() {
      _isRecording = false;
    });
  }

  // 재생 시작
  Future<void> _startPlaying() async {
    await _player.startPlayer(
      fromURI: 'example.aac',
      codec: Codec.aacADTS,
      whenFinished: () {
        setState(() {
          _isPlaying = false;
        });
      },
    );
    setState(() {
      _isPlaying = true;
    });
  }

  // 재생 중지
  Future<void> _stopPlaying() async {
    await _player.stopPlayer();
    setState(() {
      _isPlaying = false;
    });
  }

  @override
  void dispose() {
    _player.closePlayer();
    _recorder.closeRecorder();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Flutter Sound Demo'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            if (_isRecording)
              ElevatedButton(
                onPressed: _stopRecording,
                child: Text('Stop Recording'),
              )
            else
              ElevatedButton(
                onPressed: _startRecording,
                child: Text('Start Recording'),
              ),
            SizedBox(height: 16),
            if (_isPlaying)
              ElevatedButton(
                onPressed: _stopPlaying,
                child: Text('Stop Playing'),
              )
            else
              ElevatedButton(
                onPressed: _startPlaying,
                child: Text('Start Playing'),
              ),
          ],
        ),
      ),
    );
  }
}
